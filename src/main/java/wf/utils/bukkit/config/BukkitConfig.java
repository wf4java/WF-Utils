package wf.utils.bukkit.config;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import wf.utils.bukkit.config.utils.ConfigSerializable;
import wf.utils.java.file.yamlconfiguration.utils.StringSerializable;
import wf.utils.java.file.yamlconfiguration.utils.types.IntegerInRange;
import wf.utils.java.file.yamlconfiguration.utils.types.IntegerRandom;
import wf.utils.java.file.yamlconfiguration.configuration.ConfigDefaultValue;


import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

public class BukkitConfig {


    private File file;
    private FileConfiguration config;


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

    public BukkitConfig(Plugin plugin, String configName, boolean autoCopy, ConfigDefaultValue... values){
        this(plugin, configName, autoCopy);
        setDefaultValues(values);
        save();
    }

    public BukkitConfig(Plugin plugin, String configName, ConfigDefaultValue... values){
        this(plugin, configName);
        setDefaultValues(values);
        save();
    }

    public void setDefaultValues(boolean replace, ConfigDefaultValue... values){
        for(ConfigDefaultValue value : values){
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

    public void setDefaultValues(ConfigDefaultValue... values){
        setDefaultValues(false, values);
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
    public <T> T getObject(String path, Class<T> type){ return config.getObject(path, type); }

    public String getString(String path, String def){ return config.getString(path, def); }
    public int getInt(String path, int def){ return config.getInt(path, def); }
    public long getLong(String path, long def){ return config.getLong(path, def); }
    public double getDouble(String path, double def){ return config.getDouble(path, def); }
    public boolean getBoolean(String path, boolean def){ return config.getBoolean(path, def); }


    public Object get(String path, Object def){ return config.get(path, def); }
    public <T> T getObject(String path, Class<T> type, T def){ return config.getObject(path, type, def); }


    public void set(String path, StringSerializable value){
        set(path, value.getSerializableString());
    }

    public <T extends StringSerializable> T get(String path, T value){
        return (T) value.getSerializableObject(getString(path));
    }


    public <T extends ConfigSerializable> T get(String path, T value){
        return (T) value.getSerializableObject(getConfigurationSection(path));
    }

    public void set(String path, ConfigSerializable value){
        set(path, value.setSerializableObject(getConfigurationSection(path)));
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
