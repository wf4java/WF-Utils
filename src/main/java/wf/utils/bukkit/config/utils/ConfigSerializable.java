package wf.utils.bukkit.config.utils;


import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;

public interface ConfigSerializable<T> {

    public default T getSerializableObject(ConfigurationSection section){
        return null;
    };


    public default ConfigurationSection setSerializableObject(ConfigurationSection section){
        return section;
    };

    public default ConfigurationSection setSerializableObject(String path, Configuration configuration){
        return setSerializableObject(configuration.getConfigurationSection(path));
    };

    public default T getSerializableObject(String path, Configuration configuration){
        return getSerializableObject(configuration.getConfigurationSection(path));
    };

}
