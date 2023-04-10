package wf.utils.bukkit.item;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class ItemUtils {

    public static ItemStack removeAmount(ItemStack item, int amount){
        if(item.getAmount() - amount <= 0) return null;
        item.setAmount(item.getAmount() - amount);
        return item;
    }

    public static ItemStack removeOne(ItemStack item){
        return removeAmount(item,1);
    }

    public static ItemStack setHead(ItemStack item, String name){
        SkullMeta sm = (SkullMeta) item.getItemMeta();
        sm.setOwner(name);
        item.setItemMeta(sm);
        return item;
    }

    public static ItemStack getHead(String name){
        return setHead(new ItemStack(Material.PLAYER_HEAD, 1), name);
    }
}
