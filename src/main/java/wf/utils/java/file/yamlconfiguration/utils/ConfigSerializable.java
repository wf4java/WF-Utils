package wf.utils.java.file.yamlconfiguration.utils;


import wf.utils.java.file.yamlconfiguration.configuration.Config;
import wf.utils.java.file.yamlconfiguration.configuration.Configuration;
import wf.utils.java.file.yamlconfiguration.configuration.ConfigurationSection;

public interface ConfigSerializable<T> {

    public default T getSerializableObject(String path, Config config){
        return null;
    };
    public default void setSerializableObject(String path, Config config){

    };

}
