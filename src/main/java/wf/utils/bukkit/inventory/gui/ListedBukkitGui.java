package wf.utils.bukkit.inventory.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import wf.utils.bukkit.data.PersistDataUtils;
import wf.utils.bukkit.inventory.gui.BukkitGui;
import wf.utils.bukkit.inventory.gui.item.Item;
import wf.utils.bukkit.inventory.gui.item.ItemList;
import wf.utils.bukkit.inventory.gui.item.ItemSlot;

import java.util.HashMap;
import java.util.List;

public class ListedBukkitGui extends BukkitGui {

    private int startIndex;
    private int maxOnPage;
    private Item listedItem;
    private ItemList list;

    public ListedBukkitGui() {
    }

    public ListedBukkitGui(int startIndex, int maxOnPage, Item listedItem, ItemList list) {
        this.startIndex = startIndex;
        this.maxOnPage = maxOnPage;
        this.listedItem = listedItem;
        this.list = list;
    }

    public ListedBukkitGui(Plugin plugin, String name, int startIndex, int maxOnPage, Item listedItem, ItemList list) {
        super(plugin, name);
        this.startIndex = startIndex;
        this.maxOnPage = maxOnPage;
        this.listedItem = listedItem;
        this.list = list;
    }

    @Override
    public Inventory getInventory(Player player){
        return getInventory(player,0);
    }


    public Inventory getInventory(Player player, int page){

        if(page < 0) page = 0;

        int count = list.getCount(player,null);
        if(maxOnPage * page > count) return getInventory(player,(int) count / maxOnPage);

        Inventory inv = createEmptyInventory();

        HashMap<String, String> arguments = new HashMap<>(3);

        arguments.put("page", String.valueOf(page));
        arguments.put("has_next_page", String.valueOf(maxOnPage * (page + 1) < count));
        arguments.put("has_prev_page", String.valueOf(page != 0));



        for(ItemSlot slot : getSlots()){
            Item item = slot.getItem();
            if(item.isCondition(player, arguments)) {
                ItemStack itemStack = item.getReplacedItemStack(player, arguments);
                PersistDataUtils.set(getPlugin(),"PAGE",itemStack, String.valueOf(page));
                PersistDataUtils.set(getPlugin(),"GUI",itemStack, getName());
                inv.setItem(slot.getIndex(), itemStack);
            }
        }



        int finalPage = page;
        new Thread(() -> {
            List<String> entities = list.get(player,null, finalPage * maxOnPage, Math.min((finalPage + 1) * (maxOnPage) - 1, count - 1));
            long start = System.currentTimeMillis();

            for(int i = 0, slot = startIndex; i < entities.size(); i++, slot++){
                if(slot >= getSize()) break;
                if(inv.getItem(slot) != null) { i--; continue; }

                arguments.clear();
                arguments.put("entity", entities.get(i));

                if(listedItem.isCondition(player, arguments)) {
                    ItemStack itemStack = listedItem.getReplacedItemStackAsListedItem(player, arguments);
                    PersistDataUtils.set(getPlugin(),"ENTITY", itemStack, entities.get(i));
                    inv.setItem(slot, itemStack);
                }
                if(start + 1000 < System.currentTimeMillis()){
                    if(inv.getViewers().isEmpty()) break;
                    start = System.currentTimeMillis();
                }
            }
        }).start();;

        return inv;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public int getMaxOnPage() {
        return maxOnPage;
    }

    public void setMaxOnPage(int maxOnPage) {
        this.maxOnPage = maxOnPage;
    }

    public Item getListedItem() {
        return listedItem;
    }

    public void setListedItem(Item listedItem) {
        this.listedItem = listedItem;
    }

    public ItemList getList() {
        return list;
    }

    public void setList(ItemList list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "ListedBukkitGui{" +
                "startIndex=" + startIndex +
                ", maxOnPage=" + maxOnPage +
                ", listedItem=" + listedItem +
                ", list=" + list +
                '}';
    }
}
