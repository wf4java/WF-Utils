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
import wf.utils.bukkit.misc.chat_color.ListColored;


import java.util.*;

public class BukkitGuiController implements Listener {

    private final Plugin plugin;
    private final BukkitConfig config;

    private HashMap<String, BukkitGui> guis = new HashMap<>();
    private HashMap<String, Item> items = new HashMap<>();
    private HashMap<String, Function> functions = new HashMap<>();
    private HashMap<String, Condition> conditions = new HashMap<>();
    private HashMap<String, ItemReplacer> replaces = new HashMap<>();
    private HashMap<String, ItemList> itemLists = new HashMap<>();


    public BukkitGuiController(Plugin plugin, String configName){
        this.plugin = plugin;
        config = new BukkitConfig(plugin, configName);

        addDefaultFunctions();
        addDefaultReplaces();
        addDefaultConditions();
    }

    public void load(){
        loadItems();
        loadGuis();
        registerListeners();
    }


    @EventHandler
    public void inventoryClickEvent(InventoryClickEvent event){
        ItemStack itemStack = event.getCurrentItem();
        if(itemStack == null) return;
        if(!(event.getWhoClicked() instanceof Player)) return;
        Player player = (Player) event.getWhoClicked();
        if(!PersistDataUtils.hasString(plugin,"WF_BUKKIT_GUI_ITEM_NAME", itemStack)) return;

        event.setCancelled(true);

        try {

            HashMap<String, String> arguments = new HashMap<>();

            String name = PersistDataUtils.getString(plugin,"WF_BUKKIT_GUI_ITEM_NAME", itemStack);
            Item item = items.get(name);

            if(PersistDataUtils.hasString(getPlugin(),"ENTITY", itemStack)){
                arguments.put("entity", PersistDataUtils.getString(getPlugin(),"ENTITY", itemStack));
            }

            if(PersistDataUtils.hasString(getPlugin(),"PAGE", itemStack)){
                arguments.put("page", PersistDataUtils.getString(getPlugin(),"PAGE", itemStack));
            }

            if(PersistDataUtils.hasString(getPlugin(),"GUI", itemStack)){
                arguments.put("gui", PersistDataUtils.getString(getPlugin(),"GUI", itemStack));
            }


            item.applyFunctions(player, arguments);

        }catch (Exception e) { e.printStackTrace(); }

    }


    private void loadItems(){
        config.forEach("items", (s) -> {
            Item item = new Item();

            item.setName(s);

            ItemStack itemStack = new ItemStack(Objects.requireNonNull(Material.getMaterial(config.getString("items." + s + ".item.material"))));
            ItemMeta meta = itemStack.getItemMeta();

            if(config.contains("items." + s + ".item.name")){
                meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', config.getString("items." + s + ".item.name")));
            }
            if(config.contains("items." + s + ".item.lore")){
                meta.setLore(ListColored.translate('&', config.getStringList("items." + s + ".item.lore")));
            }
            if(config.contains("items." + s + ".item.model_data")){
                meta.setCustomModelData(config.getInt("items." + s + ".item.model_data"));
            }


            itemStack.setItemMeta(meta);

            item.setItemStack(itemStack, plugin);

            item.setReplaceDisplayName(config.getBoolean("items." + s + ".replace_name",false));
            item.setReplaceLore(config.getBoolean("items." + s + ".replace_lore",false));
            item.setReplaceFunction(config.getBoolean("items." + s + ".replace_function",false));

            List<ItemFunction> itemFunctions = new ArrayList<>();
            if(config.contains("items." + s + ".functions")){
                config.forEach("items." + s + ".functions", (f) -> {
                    ItemFunction function = new ItemFunction();
                    function.setName(f);
                    function.setFunction(functions.get(f));
                    HashMap<String, String> arguments = new HashMap<>();
                    config.forEach("items." + s + ".functions." + f, (a) -> {
                        arguments.put(a, config.getString("items." + s + ".functions." + f + "." + a));
                    });
                    function.setArguments(arguments);
                    itemFunctions.add(function);
                });
            }
            item.setFunctions(itemFunctions.toArray(new ItemFunction[0]));


            List<ItemReplacer> itemReplaces = new ArrayList<>();
            if(config.contains("items." + s + ".replaces")){
                for(String r : config.getStringList("items." + s + ".replaces")){
                    itemReplaces.add(replaces.get(r));
                }
            }
            item.setReplaces(itemReplaces.toArray(new ItemReplacer[0]));


            List<ItemCondition> itemConditions = new ArrayList<>();

            if(config.contains("items." + s + ".conditions")){
                config.getStringList("items." + s + ".conditions").forEach((f) -> {
                    ItemCondition condition = new ItemCondition();
                    condition.setName(f);
                    condition.setCondition(conditions.get(f));
                    itemConditions.add(condition);
                });
            }

            item.setConditions(itemConditions.toArray(new ItemCondition[0]));


            items.put(s, item);
        });
    }

    private void loadGuis(){
        config.forEach("guis", (g) -> {
            String guiType = config.getString("guis." + g + ".gui_type","default");

            BukkitGui gui;
            if(guis.containsKey(g)) {
                gui = guis.get(g);
            }else {
                if(guiType.equalsIgnoreCase("listed")){
                    gui = new ListedBukkitGui(
                            null,null,
                            config.getInt("guis." + g + ".start_index",0),
                            config.getInt("guis." + g + ".count",9),
                            items.get(config.getString("guis." + g + ".list_item")),
                            itemLists.get(config.getString("guis." + g + ".list")));
                }else if(guiType.equalsIgnoreCase("entity")){
                    gui = new EntityBukkitGui();
                }else{
                    gui = new BukkitGui();
                }
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

            if(config.contains("guis." + g + ".display_name")){
                gui.setDisplayName(ChatColor.translateAlternateColorCodes('&',config.getString("guis." + g + ".display_name")));
            }

            if(config.contains("guis." + g + ".inventory_type")){
                gui.setType(InventoryType.valueOf(config.getString("guis." + g + ".inventory_type")));
            }

            if(config.contains("guis." + g + ".items")){
                List<ItemSlot> itemSlots = new ArrayList<>();
                config.forEach("guis." + g + ".items", (i) -> {
                    ItemSlot itemSlot = new ItemSlot();
                    itemSlot.setItem(items.get(config.getString("guis." + g + ".items." + i + ".name")));
                    itemSlot.setIndex(config.getInt("guis." + g + ".items." + i + ".slot"));
                    itemSlots.add(itemSlot);
                });
                gui.setSlots(itemSlots.toArray(new ItemSlot[0]));
            }


        });
    }


    private void addDefaultFunctions(){
        functions.put("open_gui", new Function((p, m) -> {
            if(m == null) return;
            if(!m.containsKey("gui")) return;
            if(!guis.containsKey(m.get("gui"))) return;

            p.openInventory(guis.get(m.get("gui")).getInventory(p));
        }));

        functions.put("open_entity_gui", new Function((p, m) -> {
            if(m == null) return;
            if(!m.containsKey("gui")) return;
            if(!m.containsKey("entity")) return;

            BukkitGui gui = guis.get(m.get("gui"));
            if(gui == null) return;
            if(!(gui instanceof EntityBukkitGui)) return;

            p.openInventory(((EntityBukkitGui) gui).getInventory(p, m.get("entity")));
        }));

        functions.put("next_page", new Function((p, m) -> {
            if(m == null) return;
            if(!m.containsKey("gui")) return;
            if(!m.containsKey("page")) return;

            BukkitGui gui = guis.get(m.get("gui"));
            if(gui == null) return;
            if(!(gui instanceof ListedBukkitGui)) return;

            p.openInventory(((ListedBukkitGui) gui).getInventory(p, Integer.parseInt(m.get("page")) + 1));
        }));

        functions.put("prev_page", new Function((p, m) -> {
            if(m == null) return;
            if(!m.containsKey("gui")) return;
            if(!m.containsKey("page")) return;

            BukkitGui gui = guis.get(m.get("gui"));
            if(gui == null) return;
            if(!(gui instanceof ListedBukkitGui)) return;

            p.openInventory(((ListedBukkitGui) gui).getInventory(p, Integer.parseInt(m.get("page")) - 1));
        }));

        functions.put("run_command", new Function((p, m) -> {
            if(m == null) return;
            if(!m.containsKey("command")) return;

            Bukkit.getServer().dispatchCommand(p, m.get("command"));
        }));

        functions.put("close_gui", new Function((p, m) -> {
            p.closeInventory();
        }));

    }

    private void addDefaultReplaces(){
        replaces.put("{player_name}", new ItemReplacer("{player_name}", (p, m) -> p.getName()));
        replaces.put("{player_display_name}", new ItemReplacer("{player_display_name}", (p, m) -> p.getDisplayName()));
        replaces.put("{player_uuid}", new ItemReplacer("{player_uuid}", (p, m) -> p.getUniqueId().toString()));

        replaces.put("{entity}", new ItemReplacer("{entity}", (p, m) -> String.valueOf(m.get("entity"))));
    }

    private void addDefaultConditions(){
        conditions.put("has_next_page", new Condition((p, m) -> {
            String b = m.get("has_next_page");
            if(b == null) return false;
            return Boolean.parseBoolean(b);
        }));

        conditions.put("has_prev_page", new Condition((p, m) -> {
            String b = m.get("has_prev_page");
            if(b == null) return false;
            return Boolean.parseBoolean(b);
        }));
    }


    private void registerListeners(){
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }


    public Plugin getPlugin() {
        return plugin;
    }

    public BukkitConfig getConfig() {
        return config;
    }

    public HashMap<String, BukkitGui> getGuis() {
        return guis;
    }

    public void setGuis(HashMap<String, BukkitGui> guis) {
        this.guis = guis;
    }

    public HashMap<String, Item> getItems() {
        return items;
    }

    public void setItems(HashMap<String, Item> items) {
        this.items = items;
    }

    public HashMap<String, Function> getFunctions() {
        return functions;
    }

    public void setFunctions(HashMap<String, Function> functions) {
        this.functions = functions;
    }

    public HashMap<String, Condition> getConditions() {
        return conditions;
    }

    public void setConditions(HashMap<String, Condition> conditions) {
        this.conditions = conditions;
    }

    public HashMap<String, ItemReplacer> getReplaces() {
        return replaces;
    }

    public void setReplaces(HashMap<String, ItemReplacer> replaces) {
        this.replaces = replaces;
    }

    public HashMap<String, ItemList> getItemLists() {
        return itemLists;
    }

    public void setItemLists(HashMap<String, ItemList> itemLists) {
        this.itemLists = itemLists;
    }





    @Override
    public String toString() {
        return "BukkitGuiController{" +
                "plugin=" + plugin +
                ", config=" + config +
                ", guis=" + guis +
                ", items=" + items +
                ", functions=" + functions +
                ", conditions=" + conditions +
                ", replaces=" + replaces +
                ", itemLists=" + itemLists +
                '}';
    }
}
