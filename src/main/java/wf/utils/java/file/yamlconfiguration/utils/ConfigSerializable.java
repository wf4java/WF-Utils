package wf.utils.java.file.yamlconfiguration.utils;


import wf.utils.java.file.yamlconfiguration.configuration.ConfigurationSection;

public interface ConfigSerializable<T> {

    public default T getSerializableObject(ConfigurationSection section){
        return null;
    };
    public default ConfigurationSection setSerializableObject(ConfigurationSection section){
        return section;
    };


}
