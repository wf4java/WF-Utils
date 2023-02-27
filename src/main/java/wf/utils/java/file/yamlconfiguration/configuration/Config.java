package wf.utils.java.file.yamlconfiguration.configuration;


import wf.utils.java.file.yamlconfiguration.file.FileConfiguration;
import wf.utils.java.file.yamlconfiguration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;


public class Config {

    private File file;
    private FileConfiguration config;


    public Config(String path, String resourcePath){
        try {
            file = new File(path);
            if (!file.exists()) {
                InputStream link = (Config.class.getResourceAsStream(resourcePath));
                Files.copy(link, file.getAbsoluteFile().toPath());
            }
            config = YamlConfiguration.loadConfiguration(file);
        }catch (IOException e) { e.printStackTrace(); }
    }

    public Config(String path){
        try {
            file = new File(path);
            if (!file.exists()) {
                InputStream link = (Config.class.getResourceAsStream(path));
                if(link == null) file.createNewFile();
                else Files.copy(link, file.getAbsoluteFile().toPath());
            }
            config = YamlConfiguration.loadConfiguration(file);
        }catch (IOException e) { e.printStackTrace(); }
    }

    public Config(String path, String resourcePath, ConfigDefaultValue... values){
        try {
            file = new File(path);
            if (!file.exists()) {
                InputStream link = (Config.class.getResourceAsStream(resourcePath));
                Files.copy(link, file.getAbsoluteFile().toPath());
            }
            config = YamlConfiguration.loadConfiguration(file);
        }catch (IOException e) { e.printStackTrace(); }
        setDefaultValues(values);
    }

    public Config(String path, ConfigDefaultValue... values){
        try {
            file = new File(path);
            if (!file.exists()) {
                InputStream link = (Config.class.getResourceAsStream(path));
                if(link == null) file.createNewFile();
                else Files.copy(link, file.getAbsoluteFile().toPath());
            }
            config = YamlConfiguration.loadConfiguration(file);
        }catch (IOException e) { e.printStackTrace(); }
        setDefaultValues(values);
    }


    public void setDefaultValues(boolean replace, ConfigDefaultValue... values){
        for(ConfigDefaultValue value : values){
            if(replace && config.contains(value.getPath())) continue;
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
    public List<Long> getLongList(String path){ return config.getLongList(path); }
    public List<Double> getDoubleList(String path){ return config.getDoubleList(path); }
    public List<Boolean> getBooleanList(String path){ return config.getBooleanList(path); }

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




    public ConfigurationSection getConfigurationSection(String path){ return config.getConfigurationSection(path); }

}
