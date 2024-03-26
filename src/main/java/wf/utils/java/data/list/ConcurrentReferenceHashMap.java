package wf.utils.java.data.list;


import wf.utils.java.misc.Assert;
import wf.utils.java.misc.annotation.Nullable;
import wf.utils.java.object.ObjectUtils;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class ConcurrentReferenceHashMap<K, V> extends AbstractMap<K, V> implements ConcurrentMap<K, V> {

    private static final int DEFAULT_INITIAL_CAPACITY = 16;
    private static final float DEFAULT_LOAD_FACTOR = 0.75F;
    private static final int DEFAULT_CONCURRENCY_LEVEL = 16;
    private static final ReferenceType DEFAULT_REFERENCE_TYPE;
    private static final int MAXIMUM_CONCURRENCY_LEVEL = 65536;
    private static final int MAXIMUM_SEGMENT_SIZE = 1073741824;
    private final ConcurrentReferenceHashMap<K, V>.Segment[] segments;
    private final float loadFactor;
    private final ReferenceType referenceType;
    private final int shift;
    @Nullable
    private volatile Set<Map.Entry<K, V>> entrySet;

    public ConcurrentReferenceHashMap() {
        this(16, 0.75F, 16, DEFAULT_REFERENCE_TYPE);
    }

    public ConcurrentReferenceHashMap(int initialCapacity) {
        this(initialCapacity, 0.75F, 16, DEFAULT_REFERENCE_TYPE);
    }

    public ConcurrentReferenceHashMap(int initialCapacity, float loadFactor) {
        this(initialCapacity, loadFactor, 16, DEFAULT_REFERENCE_TYPE);
    }

    public ConcurrentReferenceHashMap(int initialCapacity, int concurrencyLevel) {
        this(initialCapacity, 0.75F, concurrencyLevel, DEFAULT_REFERENCE_TYPE);
    }

    public ConcurrentReferenceHashMap(int initialCapacity, ReferenceType referenceType) {
        this(initialCapacity, 0.75F, 16, referenceType);
    }

    public ConcurrentReferenceHashMap(int initialCapacity, float loadFactor, int concurrencyLevel) {
        this(initialCapacity, loadFactor, concurrencyLevel, DEFAULT_REFERENCE_TYPE);
    }

    public ConcurrentReferenceHashMap(int initialCapacity, float loadFactor, int concurrencyLevel, ReferenceType referenceType) {
        Assert.isTrue(initialCapacity >= 0, "Initial capacity must not be negative");
        Assert.isTrue(loadFactor > 0.0F, "Load factor must be positive");
        Assert.isTrue(concurrencyLevel > 0, "Concurrency level must be positive");
        Assert.notNull(referenceType, "Reference type must not be null");
        this.loadFactor = loadFactor;
        this.shift = calculateShift(concurrencyLevel, 65536);
        int size = 1 << this.shift;
        this.referenceType = referenceType;
        int roundedUpSegmentCapacity = (int)(((long)(initialCapacity + size) - 1L) / (long)size);
        int initialSize = 1 << calculateShift(roundedUpSegmentCapacity, 1073741824);
        ConcurrentReferenceHashMap<K, V>.Segment[] segments = (Segment[]) Array.newInstance(Segment.class, size);
        int resizeThreshold = (int)((float)initialSize * this.getLoadFactor());

        for(int i = 0; i < segments.length; ++i) {
            segments[i] = new Segment(initialSize, resizeThreshold);
        }

        this.segments = segments;
    }

    protected final float getLoadFactor() {
        return this.loadFactor;
    }

    protected final int getSegmentsSize() {
        return this.segments.length;
    }

    protected final ConcurrentReferenceHashMap<K, V>.Segment getSegment(int index) {
        return this.segments[index];
    }

    protected ConcurrentReferenceHashMap<K, V>.ReferenceManager createReferenceManager() {
        return new ReferenceManager();
    }

    protected int getHash(@Nullable Object o) {
        int hash = o != null ? o.hashCode() : 0;
        hash += hash << 15 ^ -12931;
        hash ^= hash >>> 10;
        hash += hash << 3;
        hash ^= hash >>> 6;
        hash += (hash << 2) + (hash << 14);
        hash ^= hash >>> 16;
        return hash;
    }

    @Nullable
    public V get(@Nullable Object key) {
        Reference<K, V> ref = this.getReference(key, ConcurrentReferenceHashMap.Restructure.WHEN_NECESSARY);
        Entry<K, V> entry = ref != null ? ref.get() : null;
        return entry != null ? entry.getValue() : null;
    }

    @Nullable
    public V getOrDefault(@Nullable Object key, @Nullable V defaultValue) {
        Reference<K, V> ref = this.getReference(key, ConcurrentReferenceHashMap.Restructure.WHEN_NECESSARY);
        Entry<K, V> entry = ref != null ? ref.get() : null;
        return entry != null ? entry.getValue() : defaultValue;
    }

    public boolean containsKey(@Nullable Object key) {
        Reference<K, V> ref = this.getReference(key, ConcurrentReferenceHashMap.Restructure.WHEN_NECESSARY);
        Entry<K, V> entry = ref != null ? ref.get() : null;
        return entry != null && ObjectUtils.nullSafeEquals(entry.getKey(), key);
    }

    @Nullable
    protected final Reference<K, V> getReference(@Nullable Object key, Restructure restructure) {
        int hash = this.getHash(key);
        return this.getSegmentForHash(hash).getReference(key, hash, restructure);
    }

    @Nullable
    public V put(@Nullable K key, @Nullable V value) {
        return this.put(key, value, true);
    }

    @Nullable
    public V putIfAbsent(@Nullable K key, @Nullable V value) {
        return this.put(key, value, false);
    }

    @Nullable
    private V put(@Nullable final K key, @Nullable final V value, final boolean overwriteExisting) {
        return this.doTask(key, new ConcurrentReferenceHashMap<K, V>.Task<V>(new TaskOption[]{ConcurrentReferenceHashMap.TaskOption.RESTRUCTURE_BEFORE, ConcurrentReferenceHashMap.TaskOption.RESIZE}) {
            @Nullable
            protected V execute(@Nullable Reference<K, V> ref, @Nullable Entry<K, V> entry, @Nullable Entries<V> entries) {
                if (entry != null) {
                    V oldValue = entry.getValue();
                    if (overwriteExisting) {
                        entry.setValue(value);
                    }

                    return oldValue;
                } else {
                    Assert.state(entries != null, "No entries segment");
                    entries.add(value);
                    return null;
                }
            }
        });
    }

    @Nullable
    public V remove(@Nullable Object key) {
        return this.doTask(key, new ConcurrentReferenceHashMap<K, V>.Task<V>(new TaskOption[]{ConcurrentReferenceHashMap.TaskOption.RESTRUCTURE_AFTER, ConcurrentReferenceHashMap.TaskOption.SKIP_IF_EMPTY}) {
            @Nullable
            protected V execute(@Nullable Reference<K, V> ref, @Nullable Entry<K, V> entry) {
                if (entry != null) {
                    if (ref != null) {
                        ref.release();
                    }

                    return entry.value;
                } else {
                    return null;
                }
            }
        });
    }

    public boolean remove(@Nullable Object key, @Nullable final Object value) {
        Boolean result = (Boolean)this.doTask(key, new ConcurrentReferenceHashMap<K, V>.Task<Boolean>(new TaskOption[]{ConcurrentReferenceHashMap.TaskOption.RESTRUCTURE_AFTER, ConcurrentReferenceHashMap.TaskOption.SKIP_IF_EMPTY}) {
            protected Boolean execute(@Nullable Reference<K, V> ref, @Nullable Entry<K, V> entry) {
                if (entry != null && ObjectUtils.nullSafeEquals(entry.getValue(), value)) {
                    if (ref != null) {
                        ref.release();
                    }

                    return true;
                } else {
                    return false;
                }
            }
        });
        return Boolean.TRUE.equals(result);
    }

    public boolean replace(@Nullable K key, @Nullable final V oldValue, @Nullable final V newValue) {
        Boolean result = (Boolean)this.doTask(key, new ConcurrentReferenceHashMap<K, V>.Task<Boolean>(new TaskOption[]{ConcurrentReferenceHashMap.TaskOption.RESTRUCTURE_BEFORE, ConcurrentReferenceHashMap.TaskOption.SKIP_IF_EMPTY}) {
            protected Boolean execute(@Nullable Reference<K, V> ref, @Nullable Entry<K, V> entry) {
                if (entry != null && ObjectUtils.nullSafeEquals(entry.getValue(), oldValue)) {
                    entry.setValue(newValue);
                    return true;
                } else {
                    return false;
                }
            }
        });
        return Boolean.TRUE.equals(result);
    }

    @Nullable
    public V replace(@Nullable K key, @Nullable final V value) {
        return this.doTask(key, new ConcurrentReferenceHashMap<K, V>.Task<V>(new TaskOption[]{ConcurrentReferenceHashMap.TaskOption.RESTRUCTURE_BEFORE, ConcurrentReferenceHashMap.TaskOption.SKIP_IF_EMPTY}) {
            @Nullable
            protected V execute(@Nullable Reference<K, V> ref, @Nullable Entry<K, V> entry) {
                if (entry != null) {
                    V oldValue = entry.getValue();
                    entry.setValue(value);
                    return oldValue;
                } else {
                    return null;
                }
            }
        });
    }

    public void clear() {
        Segment[] var1 = this.segments;
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            ConcurrentReferenceHashMap<K, V>.Segment segment = var1[var3];
            segment.clear();
        }

    }

    public void purgeUnreferencedEntries() {
        Segment[] var1 = this.segments;
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            ConcurrentReferenceHashMap<K, V>.Segment segment = var1[var3];
            segment.restructureIfNecessary(false);
        }

    }

    public int size() {
        int size = 0;
        Segment[] var2 = this.segments;
        int var3 = var2.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            ConcurrentReferenceHashMap<K, V>.Segment segment = var2[var4];
            size += segment.getCount();
        }

        return size;
    }

    public boolean isEmpty() {
        Segment[] var1 = this.segments;
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            ConcurrentReferenceHashMap<K, V>.Segment segment = var1[var3];
            if (segment.getCount() > 0) {
                return false;
            }
        }

        return true;
    }

    public Set<Map.Entry<K, V>> entrySet() {
        Set<Map.Entry<K, V>> entrySet = this.entrySet;
        if (entrySet == null) {
            entrySet = new EntrySet();
            this.entrySet = (Set)entrySet;
        }

        return (Set)entrySet;
    }

    @Nullable
    private <T> T doTask(@Nullable Object key, ConcurrentReferenceHashMap<K, V>.Task<T> task) {
        int hash = this.getHash(key);
        return this.getSegmentForHash(hash).doTask(hash, key, task);
    }

    private ConcurrentReferenceHashMap<K, V>.Segment getSegmentForHash(int hash) {
        return this.segments[hash >>> 32 - this.shift & this.segments.length - 1];
    }

    protected static int calculateShift(int minimumValue, int maximumValue) {
        int shift = 0;

        for(int value = 1; value < minimumValue && value < maximumValue; ++shift) {
            value <<= 1;
        }

        return shift;
    }

    static {
        DEFAULT_REFERENCE_TYPE = ConcurrentReferenceHashMap.ReferenceType.SOFT;
    }

    public static enum ReferenceType {
        SOFT,
        WEAK;

        private ReferenceType() {
        }
    }

    protected final class Segment extends ReentrantLock {
        private final ConcurrentReferenceHashMap<K, V>.ReferenceManager referenceManager = ConcurrentReferenceHashMap.this.createReferenceManager();
        private final int initialSize;
        private volatile Reference<K, V>[] references;
        private final AtomicInteger count = new AtomicInteger();
        private int resizeThreshold;

        public Segment(int initialSize, int resizeThreshold) {
            this.initialSize = initialSize;
            this.references = this.createReferenceArray(initialSize);
            this.resizeThreshold = resizeThreshold;
        }

        @Nullable
        public Reference<K, V> getReference(@Nullable Object key, int hash, Restructure restructure) {
            if (restructure == ConcurrentReferenceHashMap.Restructure.WHEN_NECESSARY) {
                this.restructureIfNecessary(false);
            }

            if (this.count.get() == 0) {
                return null;
            } else {
                Reference<K, V>[] references = this.references;
                int index = this.getIndex(hash, references);
                Reference<K, V> head = references[index];
                return this.findInChain(head, key, hash);
            }
        }

        @Nullable
        public <T> T doTask(final int hash, @Nullable final Object key, final ConcurrentReferenceHashMap<K, V>.Task<T> task) {
            boolean resize = task.hasOption(ConcurrentReferenceHashMap.TaskOption.RESIZE);
            if (task.hasOption(ConcurrentReferenceHashMap.TaskOption.RESTRUCTURE_BEFORE)) {
                this.restructureIfNecessary(resize);
            }

            if (task.hasOption(ConcurrentReferenceHashMap.TaskOption.SKIP_IF_EMPTY) && this.count.get() == 0) {
                return (T) task.execute((Reference)null, (Entry)null, (Entries)null);
            } else {
                this.lock();

                Object var10;
                try {
                    int index = this.getIndex(hash, this.references);
                    Reference<K, V> head = this.references[index];
                    Reference<K, V> ref = this.findInChain(head, key, hash);
                    Entry<K, V> entry = ref != null ? ref.get() : null;
                    Entries<V> entries = (value) -> {
                        Entry<K, V> newEntry = new Entry(key, value);
                        Reference<K, V> newReference = this.referenceManager.createReference(newEntry, hash, head);
                        this.references[index] = newReference;
                        this.count.incrementAndGet();
                    };
                    var10 = task.execute(ref, entry, entries);
                } finally {
                    this.unlock();
                    if (task.hasOption(ConcurrentReferenceHashMap.TaskOption.RESTRUCTURE_AFTER)) {
                        this.restructureIfNecessary(resize);
                    }

                }

                return (T) var10;
            }
        }

        public void clear() {
            if (this.count.get() != 0) {
                this.lock();

                try {
                    this.references = this.createReferenceArray(this.initialSize);
                    this.resizeThreshold = (int)((float)this.references.length * ConcurrentReferenceHashMap.this.getLoadFactor());
                    this.count.set(0);
                } finally {
                    this.unlock();
                }

            }
        }

        protected final void restructureIfNecessary(boolean allowResize) {
            int currCount = this.count.get();
            boolean needsResize = allowResize && currCount > 0 && currCount >= this.resizeThreshold;
            Reference<K, V> ref = this.referenceManager.pollForPurge();
            if (ref != null || needsResize) {
                this.restructure(allowResize, ref);
            }

        }

        private void restructure(boolean allowResize, @Nullable Reference<K, V> ref) {
            this.lock();

            try {
                int countAfterRestructure = this.count.get();
                Set<Reference<K, V>> toPurge = Collections.emptySet();
                if (ref != null) {
                    for(toPurge = new HashSet(); ref != null; ref = this.referenceManager.pollForPurge()) {
                        ((Set)toPurge).add(ref);
                    }
                }

                countAfterRestructure -= ((Set)toPurge).size();
                boolean needsResize = countAfterRestructure > 0 && countAfterRestructure >= this.resizeThreshold;
                boolean resizing = false;
                int restructureSize = this.references.length;
                if (allowResize && needsResize && restructureSize < 1073741824) {
                    restructureSize <<= 1;
                    resizing = true;
                }

                Reference<K, V>[] restructured = resizing ? this.createReferenceArray(restructureSize) : this.references;

                for(int i = 0; i < this.references.length; ++i) {
                    ref = this.references[i];
                    if (!resizing) {
                        restructured[i] = null;
                    }

                    for(; ref != null; ref = ref.getNext()) {
                        if (!((Set)toPurge).contains(ref)) {
                            Entry<K, V> entry = ref.get();
                            if (entry != null) {
                                int index = this.getIndex(ref.getHash(), restructured);
                                restructured[index] = this.referenceManager.createReference(entry, ref.getHash(), restructured[index]);
                            }
                        }
                    }
                }

                if (resizing) {
                    this.references = restructured;
                    this.resizeThreshold = (int)((float)this.references.length * ConcurrentReferenceHashMap.this.getLoadFactor());
                }

                this.count.set(Math.max(countAfterRestructure, 0));
            } finally {
                this.unlock();
            }

        }

        @Nullable
        private Reference<K, V> findInChain(Reference<K, V> ref, @Nullable Object key, int hash) {
            for(Reference<K, V> currRef = ref; currRef != null; currRef = currRef.getNext()) {
                if (currRef.getHash() == hash) {
                    Entry<K, V> entry = currRef.get();
                    if (entry != null) {
                        K entryKey = entry.getKey();
                        if (ObjectUtils.nullSafeEquals(entryKey, key)) {
                            return currRef;
                        }
                    }
                }
            }

            return null;
        }

        private Reference<K, V>[] createReferenceArray(int size) {
            return new Reference[size];
        }

        private int getIndex(int hash, Reference<K, V>[] references) {
            return hash & references.length - 1;
        }

        public final int getSize() {
            return this.references.length;
        }

        public final int getCount() {
            return this.count.get();
        }
    }

    protected class ReferenceManager {
        private final ReferenceQueue<Entry<K, V>> queue = new ReferenceQueue();

        protected ReferenceManager() {
        }

        public Reference<K, V> createReference(Entry<K, V> entry, int hash, @Nullable Reference<K, V> next) {
            return (Reference)(ConcurrentReferenceHashMap.this.referenceType == ConcurrentReferenceHashMap.ReferenceType.WEAK ? new WeakEntryReference(entry, hash, next, this.queue) : new SoftEntryReference(entry, hash, next, this.queue));
        }

        @Nullable
        public Reference<K, V> pollForPurge() {
            return (Reference)this.queue.poll();
        }
    }

    protected static enum Restructure {
        WHEN_NECESSARY,
        NEVER;

        private Restructure() {
        }
    }

    protected interface Reference<K, V> {
        @Nullable
        Entry<K, V> get();

        int getHash();

        @Nullable
        Reference<K, V> getNext();

        void release();
    }

    protected static final class Entry<K, V> implements Map.Entry<K, V> {
        @Nullable
        private final K key;
        @Nullable
        private volatile V value;

        public Entry(@Nullable K key, @Nullable V value) {
            this.key = key;
            this.value = value;
        }

        @Nullable
        public K getKey() {
            return this.key;
        }

        @Nullable
        public V getValue() {
            return this.value;
        }

        @Nullable
        public V setValue(@Nullable V value) {
            V previous = this.value;
            this.value = value;
            return previous;
        }

        public boolean equals(@Nullable Object other) {
            boolean var10000;
            if (this != other) {
                label28: {
                    if (other instanceof Map.Entry) {
                        Map.Entry<?, ?> that = (Map.Entry)other;
                        if (ObjectUtils.nullSafeEquals(this.getKey(), that.getKey()) && ObjectUtils.nullSafeEquals(this.getValue(), that.getValue())) {
                            break label28;
                        }
                    }

                    var10000 = false;
                    return var10000;
                }
            }

            var10000 = true;
            return var10000;
        }

        public int hashCode() {
            return ObjectUtils.nullSafeHashCode(this.key) ^ ObjectUtils.nullSafeHashCode(this.value);
        }

        public String toString() {
            return this.key + "=" + this.value;
        }
    }

    private static enum TaskOption {
        RESTRUCTURE_BEFORE,
        RESTRUCTURE_AFTER,
        SKIP_IF_EMPTY,
        RESIZE;

        private TaskOption() {
        }
    }

    private abstract class Task<T> {
        private final EnumSet<TaskOption> options;

        public Task(TaskOption... options) {
            this.options = options.length == 0 ? EnumSet.noneOf(TaskOption.class) : EnumSet.of(options[0], options);
        }

        public boolean hasOption(TaskOption option) {
            return this.options.contains(option);
        }

        @Nullable
        protected T execute(@Nullable Reference<K, V> ref, @Nullable Entry<K, V> entry, @Nullable Entries<V> entries) {
            return this.execute(ref, entry);
        }

        @Nullable
        protected T execute(@Nullable Reference<K, V> ref, @Nullable Entry<K, V> entry) {
            return null;
        }
    }

    private class EntrySet extends AbstractSet<Map.Entry<K, V>> {
        private EntrySet() {
        }

        public Iterator<Map.Entry<K, V>> iterator() {
            return ConcurrentReferenceHashMap.this.new EntryIterator();
        }

        public boolean contains(@Nullable Object o) {
            if (o instanceof Map.Entry<?, ?>) {
                Reference<K, V> ref = ConcurrentReferenceHashMap.this.getReference(((Map.Entry<?, ?>) o).getKey(), ConcurrentReferenceHashMap.Restructure.NEVER);
                Entry<K, V> otherEntry = ref != null ? ref.get() : null;
                if (otherEntry != null) {
                    return ObjectUtils.nullSafeEquals(((Map.Entry<?, ?>) o).getValue(), otherEntry.getValue());
                }
            }

            return false;
        }

        public boolean remove(Object o) {
            if (o instanceof Map.Entry<?, ?>) {
                return ConcurrentReferenceHashMap.this.remove(((Map.Entry<?, ?>)o).getKey(), ((Map.Entry<?, ?>)o).getValue());
            } else {
                return false;
            }
        }

        public int size() {
            return ConcurrentReferenceHashMap.this.size();
        }

        public void clear() {
            ConcurrentReferenceHashMap.this.clear();
        }
    }

    private static final class WeakEntryReference<K, V> extends WeakReference<Entry<K, V>> implements Reference<K, V> {
        private final int hash;
        @Nullable
        private final Reference<K, V> nextReference;

        public WeakEntryReference(Entry<K, V> entry, int hash, @Nullable Reference<K, V> next, ReferenceQueue<Entry<K, V>> queue) {
            super(entry, queue);
            this.hash = hash;
            this.nextReference = next;
        }

        public int getHash() {
            return this.hash;
        }

        @Nullable
        public Reference<K, V> getNext() {
            return this.nextReference;
        }

        public void release() {
            this.enqueue();
        }
    }

    private static final class SoftEntryReference<K, V> extends SoftReference<Entry<K, V>> implements Reference<K, V> {
        private final int hash;
        @Nullable
        private final Reference<K, V> nextReference;

        public SoftEntryReference(Entry<K, V> entry, int hash, @Nullable Reference<K, V> next, ReferenceQueue<Entry<K, V>> queue) {
            super(entry, queue);
            this.hash = hash;
            this.nextReference = next;
        }

        public int getHash() {
            return this.hash;
        }

        @Nullable
        public Reference<K, V> getNext() {
            return this.nextReference;
        }

        public void release() {
            this.enqueue();
        }
    }

    private class EntryIterator implements Iterator<Map.Entry<K, V>> {
        private int segmentIndex;
        private int referenceIndex;
        @Nullable
        private Reference<K, V>[] references;
        @Nullable
        private Reference<K, V> reference;
        @Nullable
        private Entry<K, V> next;
        @Nullable
        private Entry<K, V> last;

        public EntryIterator() {
            this.moveToNextSegment();
        }

        public boolean hasNext() {
            this.getNextIfNecessary();
            return this.next != null;
        }

        public Entry<K, V> next() {
            this.getNextIfNecessary();
            if (this.next == null) {
                throw new NoSuchElementException();
            } else {
                this.last = this.next;
                this.next = null;
                return this.last;
            }
        }

        private void getNextIfNecessary() {
            while(this.next == null) {
                this.moveToNextReference();
                if (this.reference == null) {
                    return;
                }

                this.next = this.reference.get();
            }

        }

        private void moveToNextReference() {
            if (this.reference != null) {
                this.reference = this.reference.getNext();
            }

            while(this.reference == null && this.references != null) {
                if (this.referenceIndex >= this.references.length) {
                    this.moveToNextSegment();
                    this.referenceIndex = 0;
                } else {
                    this.reference = this.references[this.referenceIndex];
                    ++this.referenceIndex;
                }
            }

        }

        private void moveToNextSegment() {
            this.reference = null;
            this.references = null;
            if (this.segmentIndex < ConcurrentReferenceHashMap.this.segments.length) {
                this.references = ConcurrentReferenceHashMap.this.segments[this.segmentIndex].references;
                ++this.segmentIndex;
            }

        }

        public void remove() {
            Assert.state(this.last != null, "No element to remove");
            ConcurrentReferenceHashMap.this.remove(this.last.getKey());
            this.last = null;
        }
    }

    private interface Entries<V> {
        void add(@Nullable V value);
    }

}
