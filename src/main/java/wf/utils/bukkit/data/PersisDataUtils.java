package wf.utils.bukkit.data;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataHolder;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

public class PersisDataUtils {

    private Plugin plugin;

    public PersisDataUtils(Plugin plugin) {
        this.plugin = plugin;
    }

    public <T extends PersistentDataHolder> void set(String key, T item, int value){
        set(plugin, key, item, value);
    }

    public <T extends PersistentDataHolder> void set(String key, T item, double value){
        set(plugin, key, item, value);
    }

    public <T extends PersistentDataHolder> void set(String key, T item, byte value){
        set(plugin, key, item, value);
    }

    public <T extends PersistentDataHolder> void set(String key, T item, long value){
        set(plugin, key, item, value);
    }

    public <T extends PersistentDataHolder> void set(String key, T item, String value){
        set(plugin, key, item, value);
    }

    public <T extends PersistentDataHolder> void set(String key, T item, short value){
        set(plugin, key, item, value);
    }

    public <T extends PersistentDataHolder> void set(String key, T item, float value){
        set(plugin, key, item, value);
    }

    public <T extends PersistentDataHolder> void set(String key, T item, byte[] value){
        set(plugin, key, item, value);
    }

    public <T extends PersistentDataHolder> void set(String key, T item, long[] value){
        set(plugin, key, item, value);
    }

    public <T extends PersistentDataHolder> void set(String key, T item, int[] value){
        set(plugin, key, item, value);
    }

    public <T extends PersistentDataHolder> void set(String key, T item, PersistentDataContainer value){
        set(plugin, key, item, value);
    }








    public <T extends PersistentDataHolder> int getInt(String key, T item){
        return getInt(plugin, key, item);
    }

    public <T extends PersistentDataHolder> double getDouble(String key, T item){
        return getDouble(plugin, key, item);
    }

    public <T extends PersistentDataHolder> byte getByte(String key, T item){
        return getByte(plugin, key, item);
    }

    public <T extends PersistentDataHolder> long getLong(String key, T item){
        return getLong(plugin, key, item);
    }

    public <T extends PersistentDataHolder> String getString(String key, T item){
        return getString(plugin, key, item);
    }

    public <T extends PersistentDataHolder> short getShort(String key, T item){
        return getShort(plugin, key, item);
    }

    public <T extends PersistentDataHolder> float getFloat(String key, T item){
        return getFloat(plugin, key, item);
    }

    public <T extends PersistentDataHolder> byte[] getByteArray(String key, T item){
        return getByteArray(plugin, key, item);
    }

    public <T extends PersistentDataHolder> long[] getLongArray(String key, T item){
        return getLongArray(plugin, key, item);
    }

    public <T extends PersistentDataHolder> int[] getIntArray(String key, T item){
        return getIntArray(plugin, key, item);
    }

    public <T extends PersistentDataHolder> PersistentDataContainer getDataContainer(String key, T item){
        return getDataContainer(plugin, key, item);
    }











    public static <T extends PersistentDataHolder> void set(Plugin plugin, String key, T item, int value){
        item.getPersistentDataContainer().set(new NamespacedKey(plugin, key), PersistentDataType.INTEGER, value);
    }

    public static <T extends PersistentDataHolder> void set(Plugin plugin, String key, T item, double value){
        item.getPersistentDataContainer().set(new NamespacedKey(plugin, key), PersistentDataType.DOUBLE, value);
    }

    public static <T extends PersistentDataHolder> void set(Plugin plugin, String key, T item, byte value){
        item.getPersistentDataContainer().set(new NamespacedKey(plugin, key), PersistentDataType.BYTE, value);
    }

    public static <T extends PersistentDataHolder> void set(Plugin plugin, String key, T item, long value){
        item.getPersistentDataContainer().set(new NamespacedKey(plugin, key), PersistentDataType.LONG, value);
    }

    public static <T extends PersistentDataHolder> void set(Plugin plugin, String key, T item, String value){
        item.getPersistentDataContainer().set(new NamespacedKey(plugin, key), PersistentDataType.STRING, value);
    }

    public static <T extends PersistentDataHolder> void set(Plugin plugin, String key, T item, short value){
        item.getPersistentDataContainer().set(new NamespacedKey(plugin, key), PersistentDataType.SHORT, value);
    }

    public static <T extends PersistentDataHolder> void set(Plugin plugin, String key, T item, float value){
        item.getPersistentDataContainer().set(new NamespacedKey(plugin, key), PersistentDataType.FLOAT, value);
    }

    public static <T extends PersistentDataHolder> void set(Plugin plugin, String key, T item, byte[] value){
        item.getPersistentDataContainer().set(new NamespacedKey(plugin, key), PersistentDataType.BYTE_ARRAY, value);
    }

    public static <T extends PersistentDataHolder> void set(Plugin plugin, String key, T item, long[] value){
        item.getPersistentDataContainer().set(new NamespacedKey(plugin, key), PersistentDataType.LONG_ARRAY, value);
    }

    public static <T extends PersistentDataHolder> void set(Plugin plugin, String key, T item, int[] value){
        item.getPersistentDataContainer().set(new NamespacedKey(plugin, key), PersistentDataType.INTEGER_ARRAY, value);
    }

    public static <T extends PersistentDataHolder> void set(Plugin plugin, String key, T item, PersistentDataContainer value){
        item.getPersistentDataContainer().set(new NamespacedKey(plugin, key), PersistentDataType.TAG_CONTAINER, value);
    }








    public static <T extends PersistentDataHolder> int getInt(Plugin plugin, String key, T item){
        return item.getPersistentDataContainer().get(new NamespacedKey(plugin, key), PersistentDataType.INTEGER);
    }

    public static <T extends PersistentDataHolder> double getDouble(Plugin plugin, String key, T item){
        return item.getPersistentDataContainer().get(new NamespacedKey(plugin, key), PersistentDataType.DOUBLE);
    }

    public static <T extends PersistentDataHolder> byte getByte(Plugin plugin, String key, T item){
        return item.getPersistentDataContainer().get(new NamespacedKey(plugin, key), PersistentDataType.BYTE);
    }

    public static <T extends PersistentDataHolder> long getLong(Plugin plugin, String key, T item){
        return item.getPersistentDataContainer().get(new NamespacedKey(plugin, key), PersistentDataType.LONG);
    }

    public static <T extends PersistentDataHolder> String getString(Plugin plugin, String key, T item){
        return item.getPersistentDataContainer().get(new NamespacedKey(plugin, key), PersistentDataType.STRING);
    }

    public static <T extends PersistentDataHolder> short getShort(Plugin plugin, String key, T item){
        return item.getPersistentDataContainer().get(new NamespacedKey(plugin, key), PersistentDataType.SHORT);
    }

    public static <T extends PersistentDataHolder> float getFloat(Plugin plugin, String key, T item){
        return item.getPersistentDataContainer().get(new NamespacedKey(plugin, key), PersistentDataType.FLOAT);
    }

    public static <T extends PersistentDataHolder> byte[] getByteArray(Plugin plugin, String key, T item){
        return item.getPersistentDataContainer().get(new NamespacedKey(plugin, key), PersistentDataType.BYTE_ARRAY);
    }

    public static <T extends PersistentDataHolder> long[] getLongArray(Plugin plugin, String key, T item){
        return item.getPersistentDataContainer().get(new NamespacedKey(plugin, key), PersistentDataType.LONG_ARRAY);
    }

    public static <T extends PersistentDataHolder> int[] getIntArray(Plugin plugin, String key, T item){
        return item.getPersistentDataContainer().get(new NamespacedKey(plugin, key), PersistentDataType.INTEGER_ARRAY);
    }

    public static <T extends PersistentDataHolder> PersistentDataContainer getDataContainer(Plugin plugin, String key, T item){
        return item.getPersistentDataContainer().get(new NamespacedKey(plugin, key), PersistentDataType.TAG_CONTAINER);
    }


    public Plugin getPlugin() {
        return plugin;
    }

    public void setPlugin(Plugin plugin) {
        this.plugin = plugin;
    }

}
