package wf.utils.bukkit.inventory.gui.item;

import java.util.HashSet;

public class ItemSlot {

    private Item item;
    public int index;

    public ItemSlot() {

    }

    public ItemSlot(Item item, int index) {
        this.item = item;
        this.index = index;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return "ItemSlot{" +
                "item=" + item +
                ", index=" + index +
                '}';
    }
}
