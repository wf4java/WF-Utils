package wf.utils.bukkit.config;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.FileConfigurationOptions;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;
import wf.utils.bukkit.config.utils.ConfigSerializable;


import wf.utils.bukkit.misc.while_runnable.BukkitMultipleLoopTask;
import wf.utils.java.file.yamlconfiguration.utils.StringSerializable;
import wf.utils.java.file.yamlconfiguration.utils.types.IntegerInRange;
import wf.utils.java.file.yamlconfiguration.utils.types.IntegerRandom;
import wf.utils.java.file.yamlconfiguration.configuration.ConfigDefaultValue;
import wf.utils.java.thread.loop.MultipleLoopTask;
import wf.utils.java.thread.loop.ThreadMultipleLoopTask;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.util.*;
import java.util.function.Consumer;

public class BukkitConfig {


    private final File file;
    private FileConfiguration config;
    private MultipleLoopTask autoSaveLoopTask;

    private static HashMap<UniquenessLoopTaskKey, MultipleLoopTask> autoSaveLoopTaskMap;



    public BukkitConfig(Plugin plugin, String configName){
        this(plugin, configName, true);
    }


    public BukkitConfig(Plugin plugin, String configName, boolean autoCopy){
        file = new File(plugin.getDataFolder(),configName + ".yml");
        if (!file.exists()) {
            if(autoCopy) plugin.saveResource(configName + ".yml", true);
            else try {
                new File(file.getParent()).mkdirs();
                file.createNewFile();
            } catch (IOException e) {throw new RuntimeException(e);}
        }
        config = YamlConfiguration.loadConfiguration(file);
    }

    public BukkitConfig(Plugin plugin, String configName, boolean autoCopy, SaveType saveType, int autoSaveSeconds, boolean autoSaveUnique){
        this(plugin, configName, autoCopy);
        autoSaveInit(plugin, saveType, autoSaveSeconds, autoSaveUnique);
    }

    public BukkitConfig(Plugin plugin, String configName, boolean autoCopy, Collection<ConfigDefaultValue> defaultValues, SaveType saveType, int autoSaveSeconds, boolean autoSaveUnique){
        this(plugin, configName, autoCopy);
        autoSaveInit(plugin, saveType, autoSaveSeconds, autoSaveUnique);

        if(defaultValues != null && !defaultValues.isEmpty()) {
            setDefaultValues(defaultValues);
            save();
        }
    }

    private void autoSaveInit(Plugin plugin, SaveType saveType, int seconds, boolean autoSaveUnique) {
        if(!autoSaveUnique) {
            if(autoSaveLoopTaskMap == null) autoSaveLoopTaskMap = new HashMap<>();

            MultipleLoopTask task = autoSaveLoopTaskMap.get(new UniquenessLoopTaskKey(saveType, seconds));
            if(task == null){
                task = createMultipleLoopTask(plugin, saveType, seconds);
                autoSaveLoopTaskMap.put(new UniquenessLoopTaskKey(saveType, seconds), task);
            }
            autoSaveLoopTask = task;
            autoSaveLoopTask.addRunnable(file.getAbsolutePath(), this::save);
            autoSaveLoopTask.start();
        }else {
            autoSaveLoopTask = createMultipleLoopTask(plugin, saveType, seconds);
            autoSaveLoopTask.addRunnable(file.getAbsolutePath(), this::save);
            autoSaveLoopTask.start();
        }
    }

    public void stopAutoSave(){
        if(autoSaveLoopTask != null) autoSaveLoopTask.stop();
    }

    public void startAutoSave(){
        if(autoSaveLoopTask != null) autoSaveLoopTask.start();
    }


    private MultipleLoopTask createMultipleLoopTask(Plugin plugin, SaveType saveType, int seconds) {
        if(saveType == SaveType.BUKKIT_SYNC) return new BukkitMultipleLoopTask(plugin, seconds * 20L,seconds * 20L, true);
        if(saveType == SaveType.BUKKIT_ASYNC) return new BukkitMultipleLoopTask(plugin, seconds * 20L,seconds * 20L, false);
        if(saveType == SaveType.THREAD) return new ThreadMultipleLoopTask(seconds * 1000L, seconds * 1000L);
        throw new RuntimeException("Save type invalid!");
    }

    private static class UniquenessLoopTaskKey {

        private SaveType saveType;
        private int seconds;

        public UniquenessLoopTaskKey(SaveType saveType, int seconds) {
            this.saveType = saveType;
            this.seconds = seconds;
        }

        public SaveType getSaveType() {
            return saveType;
        }

        public void setSaveType(SaveType saveType) {
            this.saveType = saveType;
        }

        public int getSeconds() {
            return seconds;
        }

        public void setSeconds(int seconds) {
            this.seconds = seconds;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof UniquenessLoopTaskKey)) return false;

            UniquenessLoopTaskKey that = (UniquenessLoopTaskKey) o;

            if (getSeconds() != that.getSeconds()) return false;
            return getSaveType() == that.getSaveType();
        }

        @Override
        public int hashCode() {
            int result = getSaveType() != null ? getSaveType().hashCode() : 0;
            result = 31 * result + getSeconds();
            return result;
        }
    }

    public enum SaveType {
        BUKKIT_ASYNC,
        BUKKIT_SYNC,
        THREAD;
    }

    public BukkitConfig(Plugin plugin, String configName, boolean autoCopy, Collection<ConfigDefaultValue> defaultValues){
        this(plugin, configName, autoCopy);
        if(defaultValues != null && !defaultValues.isEmpty()) {
            setDefaultValues(defaultValues);
            save();
        }
    }

    public BukkitConfig(Plugin plugin, String configName, Collection<ConfigDefaultValue> defaultValues){
        this(plugin, configName);
        setDefaultValues(defaultValues);
        save();
    }

    public void setDefaultValues(boolean replace, Collection<ConfigDefaultValue> defaultValues){
        for(ConfigDefaultValue value : defaultValues){
            if(!replace && config.contains(value.getPath())) continue;
            config.set(value.getPath(), value.getValue());
        }
    }

    public void set(String path, Object value){
        config.set(path, value);
    }

    public boolean contains(String path){
        return config.contains(path);
    }

    public void setDefaultValues(Collection<ConfigDefaultValue> defaultValues){
        setDefaultValues(false, defaultValues);
    }

    public void reloadConfig(){
        config = YamlConfiguration.loadConfiguration(file);
    }

    public void save(){
        try { config.save(file); } catch (IOException e) { throw new RuntimeException(e); }
    }

    public FileConfiguration getConfig(){ return config; }

    public String getString(String path){ return config.getString(path); }
    public int getInt(String path){ return config.getInt(path); }
    public long getLong(String path){ return config.getLong(path); }
    public double getDouble(String path){ return config.getDouble(path); }
    public boolean getBoolean(String path){ return config.getBoolean(path); }
    public List<String> getStringList(String path){ return config.getStringList(path); }
    public List<Integer> getIntegerList(String path){ return config.getIntegerList(path); }
    public List<Long> getLongList(String path){ return config.getLongList(path); }
    public List<Double> getDoubleList(String path){ return config.getDoubleList(path); }
    public List<Boolean> getBooleanList(String path){ return config.getBooleanList(path); }

    public List<Character> getCharacterList(String path){ return config.getCharacterList(path); }
    public List<Byte> getByteList(String path){ return config.getByteList(path); }
    public List<Float> getFloatList(String path){ return config.getFloatList(path); }

    public Object get(String path){ return config.get(path); }

    public String getString(String path, String def){ return config.getString(path, def); }
    public int getInt(String path, int def){ return config.getInt(path, def); }
    public long getLong(String path, long def){ return config.getLong(path, def); }
    public double getDouble(String path, double def){ return config.getDouble(path, def); }
    public boolean getBoolean(String path, boolean def){ return config.getBoolean(path, def); }

    public <T> T getObject(String path, Class<T> type){ return config.getObject(path, type); }

    public <T> T getObject(String path, Class<T> type, T def){ return config.getObject(path, type, def); }

    public Object get(String path, Object def){ return config.get(path, def); }

    public void save(File file) throws IOException {
        config.save(file);
    }

    public void save(String file) throws IOException {
        config.save(file);
    }

    public String saveToString() {
        return config.saveToString();
    }

    public void load(File file) throws FileNotFoundException, IOException, InvalidConfigurationException {
        config.load(file);
    }

    public void load(Reader reader) throws IOException, InvalidConfigurationException {
        config.load(reader);
    }

    public void load(String file) throws FileNotFoundException, IOException, InvalidConfigurationException {
        config.load(file);
    }

    public void loadFromString(String contents) throws InvalidConfigurationException {
        config.loadFromString(contents);
    }

    public FileConfigurationOptions options() {
        return config.options();
    }

    public void addDefault(String path, Object value) {
        config.addDefault(path, value);
    }

    public void addDefaults(Map<String, Object> defaults) {
        config.addDefaults(defaults);
    }

    public void addDefaults(Configuration defaults) {
        config.addDefaults(defaults);
    }

    public void setDefaults(Configuration defaults) {
        config.setDefaults(defaults);
    }

    public Configuration getDefaults() {
        return config.getDefaults();
    }

    public ConfigurationSection getParent() {
        return config.getParent();
    }

    public Set<String> getKeys(boolean deep) {
        return config.getKeys(deep);
    }

    public Map<String, Object> getValues(boolean deep) {
        return config.getValues(deep);
    }

    public boolean contains(String path, boolean ignoreDefault) {
        return config.contains(path, ignoreDefault);
    }

    public boolean isSet(String path) {
        return config.isSet(path);
    }

    public String getCurrentPath() {
        return config.getCurrentPath();
    }

    public String getName() {
        return config.getName();
    }

    public Configuration getRoot() {
        return config.getRoot();
    }

    public ConfigurationSection getDefaultSection() {
        return config.getDefaultSection();
    }

    public ConfigurationSection createSection(String path) {
        return config.createSection(path);
    }

    public ConfigurationSection createSection(String path, Map<?, ?> map) {
        return config.createSection(path, map);
    }

    public boolean isString(String path) {
        return config.isString(path);
    }

    public boolean isInt(String path) {
        return config.isInt(path);
    }

    public boolean isBoolean(String path) {
        return config.isBoolean(path);
    }

    public boolean isDouble(String path) {
        return config.isDouble(path);
    }

    public boolean isLong(String path) {
        return config.isLong(path);
    }

    public List<?> getList(String path) {
        return config.getList(path);
    }

    public List<?> getList(String path, List<?> def) {
        return config.getList(path, def);
    }

    public boolean isList(String path) {
        return config.isList(path);
    }

    public List<Short> getShortList(String path) {
        return config.getShortList(path);
    }

    public List<Map<?, ?>> getMapList(String path) {
        return config.getMapList(path);
    }

    public <T extends ConfigurationSerializable> T getSerializable(String path, Class<T> clazz) {
        return config.getSerializable(path, clazz);
    }

    public <T extends ConfigurationSerializable> T getSerializable(String path, Class<T> clazz, T def) {
        return config.getSerializable(path, clazz, def);
    }

    public Vector getVector(String path) {
        return config.getVector(path);
    }

    public Vector getVector(String path, Vector def) {
        return config.getVector(path, def);
    }

    public boolean isVector(String path) {
        return config.isVector(path);
    }

    public OfflinePlayer getOfflinePlayer(String path) {
        return config.getOfflinePlayer(path);
    }

    public OfflinePlayer getOfflinePlayer(String path, OfflinePlayer def) {
        return config.getOfflinePlayer(path, def);
    }

    public boolean isOfflinePlayer(String path) {
        return config.isOfflinePlayer(path);
    }

    public ItemStack getItemStack(String path) {
        return config.getItemStack(path);
    }

    public ItemStack getItemStack(String path, ItemStack def) {
        return config.getItemStack(path, def);
    }

    public boolean isItemStack(String path) {
        return config.isItemStack(path);
    }

    public Color getColor(String path) {
        return config.getColor(path);
    }

    public Color getColor(String path, Color def) {
        return config.getColor(path, def);
    }

    public boolean isColor(String path) {
        return config.isColor(path);
    }

    public Location getLocation(String path, Location def) {
        return config.getLocation(path, def);
    }

    public boolean isLocation(String path) {
        return config.isLocation(path);
    }

    public boolean isConfigurationSection(String path) {
        return config.isConfigurationSection(path);
    }

    public Location getLocation(String path) {
        return config.getLocation(path);
    }

    public void set(String path, StringSerializable value){
        set(path, value.getSerializableString());
    }

    public <T extends StringSerializable> T get(String path, T value){
        return (T) value.getSerializableObject(getString(path));
    }


    public <T extends ConfigSerializable> T get(String path, T value){
        return (T) value.getSerializableObject(path,this);
    }

    public void set(String path, ConfigSerializable value){
        value.setSerializableObject(path,this);
    }


    public void remove(String path){
        config.set(path, null);
    }



    public ConfigurationSection getConfigurationSection(String path){ return config.getConfigurationSection(path); }




    public void forEach(String path, Consumer<String> consumer){
        forEach(path,false, consumer);
    }

    public void forEach(String path, boolean deap, Consumer<String> consumer){
        for(String s : getConfigurationSection(path).getKeys(deap)) consumer.accept(s);
    }



    public void set(String path, IntegerRandom value){
        set(path, value.getSerializableString());
    }
    public IntegerRandom getIntegerRandom(String path){
        return new IntegerRandom().getSerializableObject(config.getString(path));
    }

    public void set(String path, IntegerInRange value){
        set(path, value.getSerializableString());
    }
    public IntegerInRange getIntegerInRange(String path){
        return new IntegerInRange().getSerializableObject(config.getString(path));
    }



}
