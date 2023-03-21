package main;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import wf.utils.bukkit.data.PersistDataUtils;

public class Main18 {


    private static PersistDataUtils utils = new PersistDataUtils(null);

    public static void main(String[] args) {

        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        Entity entity = null;

        utils.set(null, "hook", item,"");

        PersistDataUtils.getString(null, "hook", item);



    }



}
