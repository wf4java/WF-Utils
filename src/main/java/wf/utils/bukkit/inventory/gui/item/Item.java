package wf.utils.bukkit.inventory.gui.item;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import wf.utils.bukkit.data.PersistDataUtils;

public class Item {

    private String name;
    private ItemStack itemStack;

    private boolean replaceDisplayName;
    private boolean replaceLore;
    private boolean replaceFunction;

    private ItemCondition[] conditions;
    private ItemFunction[] functions;


    public Item() { }


    public ItemStack getReplacedItemStack(Player player){
        return null;
    }

    public boolean isCondition(){
        return true;
    }




    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public void setItemStack(ItemStack itemStack, Plugin plugin) {
        ItemStack cloned = itemStack.clone();

        PersistDataUtils.set(plugin,"WF_BUKKIT_GUI_ITEM_NAME", cloned, name);

        this.itemStack = cloned;
    }

    public boolean isReplaceDisplayName() {
        return replaceDisplayName;
    }

    public void setReplaceDisplayName(boolean replaceDisplayName) {
        this.replaceDisplayName = replaceDisplayName;
    }

    public boolean isReplaceLore() {
        return replaceLore;
    }

    public void setReplaceLore(boolean replaceLore) {
        this.replaceLore = replaceLore;
    }

    public boolean isReplaceFunction() {
        return replaceFunction;
    }

    public void setReplaceFunction(boolean replaceFunction) {
        this.replaceFunction = replaceFunction;
    }

    public ItemCondition[] getConditions() {
        return conditions;
    }

    public void setConditions(ItemCondition[] conditions) {
        this.conditions = conditions;
    }

    public ItemFunction[] getFunctions() {
        return functions;
    }

    public void setFunctions(ItemFunction[] functions) {
        this.functions = functions;
    }
}
