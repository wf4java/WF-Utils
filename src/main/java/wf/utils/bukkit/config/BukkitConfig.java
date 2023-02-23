package wf.utils.bukkit.config;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;


import java.io.File;
import java.io.IOException;
import java.util.List;

public class BukkitConfig {


    public File file;
    public FileConfiguration config;


    public BukkitConfig(Plugin plugin, String configName){
        file = new File(plugin.getDataFolder(),configName + ".yml");
        if (!file.exists()) {
            plugin.saveResource(configName + ".yml", true);
        }
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




    public ConfigurationSection getConfigurationSection(String path){ return config.getConfigurationSection(path); }


}
