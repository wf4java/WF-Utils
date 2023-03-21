package wf.utils.bukkit.item;

import org.bukkit.inventory.ItemStack;

public class ItemUtils {

    public static ItemStack removeAmount(ItemStack item, int amount){
        item.setAmount(item.getAmount() - amount);
        return item.getAmount() <= 0 ? null : item;
    }

    public static ItemStack removeOne(ItemStack item){
        return removeAmount(item,1);
    }

}
