package wf.utils.bukkit.inventory.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import wf.utils.bukkit.inventory.gui.item.Item;
import wf.utils.bukkit.inventory.gui.item.ItemSlot;

import java.util.Arrays;


public class BukkitGui {

    private String name;
    private String displayName;

    private Plugin plugin;
    private int size = 27;
    private InventoryType type = InventoryType.CHEST;

    private ItemSlot[] slots;


    public BukkitGui() {

    }

    public BukkitGui(Plugin plugin, String name) {
        this.plugin = plugin;
        this.name = name;
    }

    public Inventory getInventory(Player player){

        Inventory inv = createEmptyInventory();

        for(ItemSlot slot : slots){
            Item item = slot.getItem();
            if(item.isCondition(player,null)) {
                ItemStack itemStack = item.getReplacedItemStack(player,null);
                inv.setItem(slot.getIndex(), itemStack);
            }
        }
        return inv;
    }

    public Inventory createEmptyInventory() {
        if(type == InventoryType.CHEST) return Bukkit.createInventory(null, size, displayName == null ? type.getDefaultTitle() : displayName);
        else return Bukkit.createInventory(null, type, displayName == null ? type.getDefaultTitle() : displayName);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Plugin getPlugin() {
        return plugin;
    }

    public void setPlugin(Plugin plugin) {
        this.plugin = plugin;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public InventoryType getType() {
        return type;
    }

    public void setType(InventoryType type) {
        this.type = type;
    }

    public ItemSlot[] getSlots() {
        return slots;
    }

    public void setSlots(ItemSlot[] slots) {
        this.slots = slots;
    }



}
