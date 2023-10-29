package main.main50;

import wf.utils.bukkit.config.BukkitConfig;
import wf.utils.bukkit.config.utils.BukkitConfigBuilder;
import wf.utils.java.file.yamlconfiguration.configuration.Config;
import wf.utils.java.file.yamlconfiguration.configuration.ConfigDefaultValue;
import wf.utils.java.file.yamlconfiguration.utils.ConfigBuilder;

import java.util.Arrays;
import java.util.Collections;


public class Main38 {


    public static void main(String[] args) {

        Config config = new ConfigBuilder()
                .setPath("test1.yml")
                .setAutoSave(true)
                .setAutoSaveSeconds(5)
                .setAutoSaveUnique(true)
                .build();



        BukkitConfig bukkitConfig = new BukkitConfigBuilder()
                .setConfigName("test")
                .setAutoSave(true)
                .setSaveType(BukkitConfig.SaveType.BUKKIT_ASYNC)
                .setAutoSaveSeconds(400)
                .setAutoSaveUnique(true)
                .build();


        new Thread(() -> {
            try {Thread.sleep(2000);} catch (InterruptedException e) {throw new RuntimeException(e);}
            config.set("test.hdasdas", 23123123);
            try {Thread.sleep(20000);} catch (InterruptedException e) {throw new RuntimeException(e);}
        }).start();


    }


}
