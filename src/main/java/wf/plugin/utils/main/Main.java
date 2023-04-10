package wf.plugin.utils.main;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import wf.utils.bukkit.command.handler.handler.CommandHandler;
import wf.utils.bukkit.command.handler.subcommand.SubCommandBuilder;
import wf.utils.bukkit.inventory.gui.BukkitGuiController;
import wf.utils.bukkit.inventory.gui.item.ItemList;
import wf.utils.java.data.list.ListUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class Main extends JavaPlugin {

    @Override
    public void onEnable() {




        BukkitGuiController guiController = new BukkitGuiController(this,"gui");
        guiController.getItemLists().put("players", new ItemList() {

            List<String> list = new ArrayList<>(150);

            {
                for(int i = 1; i < 136; i++){
                    list.add(String.valueOf(i));
                }
                list.add("_wf_nik_");
            }
            @Override
            public int getCount(Player player, Map<String, String> arguments) {
                return list.size();
            }

            @Override
            public String get(Player player, Map<String, String> arguments, int index) {
                return list.get(index);
            }

            @Override
            public List<String> get(Player player, Map<String, String> arguments, int start, int end) {
                return ListUtils.copyOfRange(list, start, end);
            }
        });

        guiController.load();


        CommandHandler handler = new CommandHandler(this,"open");

        handler.addSubcommand(new SubCommandBuilder()
                .setCommand("inv")
                .setOnlyPlayer(true)
                .setRunnable((sender, command, args) ->{
                    Player player = (Player) sender;
                    player.openInventory(guiController.getGuis().get("gui_1").getInventory(player));
                }).build());


    }


    @Override
    public void onDisable() {



    }

}
