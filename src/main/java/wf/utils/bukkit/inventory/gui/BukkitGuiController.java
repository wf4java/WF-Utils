package wf.utils.bukkit.inventory.gui;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import wf.utils.bukkit.config.BukkitConfig;
import wf.utils.bukkit.data.PersistDataUtils;
import wf.utils.bukkit.inventory.gui.item.*;
import wf.utils.bukkit.misc.ListColored;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BukkitGuiController implements Listener {

    private final Plugin plugin;
    private final BukkitConfig config;

    private HashMap<String, BukkitGui> guis = new HashMap<>();
    private HashMap<String, Item> items = new HashMap<>();
    private HashMap<String, Function> functions = new HashMap<>();
    private HashMap<String, ItemCondition> conditions = new HashMap<>();


    public BukkitGuiController(Plugin plugin, String configName){
        this.plugin = plugin;
        config = new BukkitConfig(plugin, configName);

        loadItems();
        loadGuis();
        registerListeners();
    }


    @EventHandler
    public void inventoryClickEvent(InventoryClickEvent event){
        ItemStack itemStack = event.getCurrentItem();
        if(itemStack == null) return;
        if(!(event.getWhoClicked() instanceof Player)) return;
        if(!PersistDataUtils.hasString(plugin,"WF_BUKKIT_GUI_ITEM_NAME", itemStack)) return;

        event.setCancelled(true);

        try {

            String name = PersistDataUtils.getString(plugin,"WF_BUKKIT_GUI_ITEM_NAME", itemStack);
            Item item = items.get(name);


        }catch (Exception e) { e.printStackTrace(); }

    }


    private void loadItems(){
        config.forEach("items", (s) -> {
            Item item = new Item();

            item.setName(s);

            ItemStack itemStack = new ItemStack(Material.getMaterial(config.getString("items." + s + ".item.material")));
            ItemMeta meta = itemStack.getItemMeta();

            if(config.contains("items." + s + ".item.name")){
                meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', config.getString("items." + s + ".item.name")));
            }
            if(config.contains("items." + s + ".item.lore")){
                meta.setLore(ListColored.chatColored(config.getStringList("items." + s + ".item.lore"),'&'));
            }
            if(config.contains("items." + s + ".item.modelData")){
                meta.setCustomModelData(config.getInt("items." + s + ".item.modelData"));
            }
            itemStack.setItemMeta(meta);

            item.setItemStack(itemStack, plugin);

            item.setReplaceDisplayName(config.getBoolean("items." + s + ".replaceDisplayName", false));
            item.setReplaceLore(config.getBoolean("items." + s + ".replaceLore", false));
            item.setReplaceFunction(config.getBoolean("items." + s + ".replaceFunction", false));

            List<ItemFunction> itemFunctions = new ArrayList<>();
            if(config.contains("items." + s + ".functions")){
                config.forEach("items." + s + ".functions", (f) -> {
                    ItemFunction function = new ItemFunction();
                    function.setName(f);
                    function.setFunction(functions.get(f));
                    HashMap<String, String> arguments = new HashMap<>();
                    config.forEach("items." + s + ".functions." + f, (a) -> {
                        arguments.put(s, config.getString("items." + s + ".functions." + f + "." + a));
                    });
                    function.setArguments(arguments);
                    itemFunctions.add(function);
                });
                item.setFunctions(itemFunctions.toArray(new ItemFunction[0]));
            }
            item.setFunctions(itemFunctions.toArray(new ItemFunction[0]));


            List<ItemCondition> itemConditions = new ArrayList<>();


            item.setConditions(itemConditions.toArray(new ItemCondition[0]));


            items.put(s, item);
        });
    }

    private void loadGuis(){
        config.forEach("guis", (g) -> {
            BukkitGui gui;
            if(guis.containsKey(g)) {
                gui = guis.get(g);
            }else {
                gui = new BukkitGui();
                guis.put(g, gui);
            }

            gui.setPlugin(plugin);
            gui.setName(g);

            if(config.contains("guis." + g + ".size")){
                gui.setSize(config.getInt("guis." + g + ".size"));
            }

            if(config.contains("guis." + g + ".type")){
                gui.setType(InventoryType.valueOf(config.getString("guis." + g + ".type")));
            }

            if(config.contains("guis." + g + ".displayName")){
                gui.setDisplayName(config.getString("guis." + g + ".displayName"));
            }

            if(config.contains("guis." + g + ".items")){
                List<ItemSlot> itemSlots = new ArrayList<>();
                config.forEach("guis." + g + ".items", (i) -> {
                    ItemSlot itemSlot = new ItemSlot();
                    itemSlot.setItem(items.get(config.getString("guis." + g + ".items." + i + ".name")));
                    itemSlot.setIndex(config.getInt("guis." + g + ".items." + i + ".slot"));
                });
                gui.setSlots(itemSlots.toArray(new ItemSlot[0]));
            }


        });
    }

    private void registerListeners(){
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

















}
