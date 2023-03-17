package main;

import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import wf.utils.bukkit.data.PersisDataUtils;

public class Main18 {


    public static void main(String[] args) {

        ItemStack item = null;
        Entity entity = null;

        PersisDataUtils.set(null, "hook", item.getItemMeta(), 1l);

    }


}
