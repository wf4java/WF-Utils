package wf.utils.bukkit.config.utils;


import org.bukkit.configuration.ConfigurationSection;

public interface ConfigSerializable<T> {

    public default T getSerializableObject(ConfigurationSection section){
        return null;
    };
    public default ConfigurationSection setSerializableObject(ConfigurationSection section){
        return section;
    };


}
