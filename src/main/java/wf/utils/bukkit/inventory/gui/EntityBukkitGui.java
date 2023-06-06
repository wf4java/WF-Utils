package wf.utils.bukkit.inventory.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import wf.utils.bukkit.data.PersistDataUtils;
import wf.utils.bukkit.inventory.gui.item.Item;
import wf.utils.bukkit.inventory.gui.item.ItemSlot;

import java.util.HashMap;

public class EntityBukkitGui extends BukkitGui{

    public EntityBukkitGui() {
    }

    public EntityBukkitGui(Plugin plugin, String name) {
        super(plugin, name);
    }

    @Override
    public Inventory getInventory(Player player) {
        return null;
    }


    public Inventory getInventory(Player player, String entity) {
        Inventory inv = createEmptyInventory();

        HashMap<String, String> arguments = new HashMap<>(1);
        arguments.put("entity", entity);

        for(ItemSlot slot : getSlots()){
            Item item = slot.getItem();
            if(item.isCondition(player, arguments)) {
                ItemStack itemStack = item.getReplacedItemStack(player, arguments);
                PersistDataUtils.set(getPlugin(),"entity", itemStack, entity);
                inv.setItem(slot.getIndex(), itemStack);
            }
        }
        return inv;
    }
}
