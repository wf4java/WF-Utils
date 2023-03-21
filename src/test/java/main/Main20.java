package main;


import org.bukkit.inventory.ItemStack;
import wf.utils.java.file.yamlconfiguration.configuration.Config;
import wf.utils.java.thread.queue.Queue;
import wf.utils.java.time.cooldown.CoolDown;


public class Main20 {


    public static void main(String[] args) {

        Config config = new Config("config.yml");

        System.out.println(config.getIntegerRandom("b1").get());
        System.out.println(config.getIntegerRandom("b2").get());
        System.out.println(config.getIntegerRandom("b3").get());
        System.out.println(config.getIntegerRandom("b4").get());
        System.out.println(config.getIntegerRandom("b5").get());


        System.out.println(config.getIntegerInRange("v1").inRange(10));
        System.out.println(config.getIntegerInRange("v2").inRange(120));
        System.out.println(config.getIntegerInRange("v3").inRange(-124512895));
        System.out.println(config.getIntegerInRange("v4").inRange(499));
        System.out.println(config.getIntegerInRange("v4").inRange(501));
        System.out.println(config.getIntegerInRange("v5").inRange(125182581));



    }


}
