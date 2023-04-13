package wf.utils.java.file.yamlconfiguration.utils;


import wf.utils.java.file.yamlconfiguration.configuration.Configuration;
import wf.utils.java.file.yamlconfiguration.configuration.ConfigurationSection;

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
