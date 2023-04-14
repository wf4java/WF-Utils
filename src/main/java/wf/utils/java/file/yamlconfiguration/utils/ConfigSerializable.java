package wf.utils.java.file.yamlconfiguration.utils;


import wf.utils.java.file.yamlconfiguration.configuration.Configuration;
import wf.utils.java.file.yamlconfiguration.configuration.ConfigurationSection;

public interface ConfigSerializable<T> {

    public default T getSerializableObject(ConfigurationSection section){
        return null;
    };
    public default void setSerializableObject(ConfigurationSection section){

    };

    public default void setSerializableObject(String path, Configuration configuration){
        setSerializableObject(configuration.getConfigurationSection(path));
    };

    public default T getSerializableObject(String path, Configuration configuration){
        return getSerializableObject(configuration.getConfigurationSection(path));
    };
}
