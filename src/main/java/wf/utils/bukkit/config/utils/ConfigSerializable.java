package wf.utils.bukkit.config.utils;


import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import wf.utils.bukkit.config.BukkitConfig;

public interface ConfigSerializable<T> {

    public default T getSerializableObject(String path, BukkitConfig config){
        return null;
    };


    public default void setSerializableObject(String path, BukkitConfig config){

    };



}
