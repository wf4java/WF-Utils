package wf.utils.bukkit.inventory.gui.item;


import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import wf.utils.bukkit.data.PersistDataUtils;
import wf.utils.bukkit.item.ItemUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Item {

    private String name;
    private ItemStack itemStack;

    private boolean replaceDisplayName;
    private boolean replaceLore;
    private boolean replaceFunction;

    private ItemCondition[] conditions;
    private ItemFunction[] functions;
    private ItemReplacer[] replaces;


    public Item() { }





    public boolean isCondition(Player player, Map<String, String> arguments){
        for(ItemCondition itemCondition : conditions) if(!itemCondition.getCondition().isCondited(player, arguments)) return false;
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

    public ItemStack getReplacedItemStack(Player player, HashMap<String, String> arguments) {
        ItemStack item = this.itemStack.clone();
        ItemMeta meta = item.getItemMeta();


        if(replaceDisplayName && meta.hasDisplayName()){
            meta.setDisplayName(ItemReplacerUtils.replace(replaces, meta.getDisplayName(), player, arguments));
        }
        if(replaceLore && meta.hasLore()) {
            meta.setLore(ItemReplacerUtils.replace(replaces, meta.getLore(), player, arguments));
        }

        item.setItemMeta(meta);


        return item;
    }

    public ItemStack getReplacedItemStackAsListedItem(Player player, HashMap<String, String> arguments){
        ItemStack item = getReplacedItemStack(player, arguments);

        if(item.getType() != Material.PLAYER_HEAD) return item;
        if(!arguments.containsKey("entity")) return item;

        return ItemUtils.setHead(item, arguments.get("entity"));
    }


    public void setItemStack(ItemStack itemStack, Plugin plugin) {
        ItemStack cloned = itemStack.clone();

        PersistDataUtils.set(plugin,"WF_BUKKIT_GUI_ITEM_NAME", cloned, name);

        this.itemStack = cloned;
    }


    public void applyFunctions(Player player, HashMap<String, String> arguments){
        if(replaceFunction) for(ItemFunction function : functions){
            function.replaceAndApply(player, replaces, arguments);
        }
        else for(ItemFunction function : functions) function.apply(player, arguments);
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

    public ItemReplacer[] getReplaces() {
        return replaces;
    }

    public void setReplaces(ItemReplacer[] replaces) {
        this.replaces = replaces;
    }

}
