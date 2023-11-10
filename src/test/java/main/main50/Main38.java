package main.main50;


import wf.utils.java.file.yamlconfiguration.configuration.Config;
import wf.utils.java.file.yamlconfiguration.utils.ConfigBuilder;


public class Main38 {


    public static void main(String[] args) {

        Config config = new ConfigBuilder()
                .setPath("test.yml")
                .setAutoSave(true)
                .setAutoSaveSeconds(5)
                .setAutoSaveUnique(true)
                .build();



//        BukkitConfig bukkitConfig = new BukkitConfigBuilder()
//                .setPlugin(null)
//                .setConfigName("test")
//                .setAutoSave(true)
//                .setSaveType(BukkitConfig.SaveType.BUKKIT_ASYNC)
//                .setAutoSaveSeconds(400)
//                .setAutoSaveUnique(true)
//                .build();



        new Thread(() -> {
            try {Thread.sleep(2000);} catch (InterruptedException e) {throw new RuntimeException(e);}
            config.set("test.hdasdas", 23123123);
            try {Thread.sleep(20000);} catch (InterruptedException e) {throw new RuntimeException(e);}
        }).start();


    }


}
