package wf.utils.bukkit.item;

import org.bukkit.inventory.ItemStack;

public class ItemUtils {

    public static ItemStack removeAmount(ItemStack item, int amount){
        if(item.getAmount() - amount <= 0) return null;
        item.setAmount(item.getAmount() - amount);
        return item;
    }

    public static ItemStack removeOne(ItemStack item){
        return removeAmount(item,1);
    }

}
