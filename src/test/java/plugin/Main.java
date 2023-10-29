package plugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import wf.utils.bukkit.command.handler.handler.CommandHandler;
import wf.utils.bukkit.command.handler.handler.CommandHandlerBuilder;
import wf.utils.bukkit.command.handler.subcommand.SubCommandBuilder;
import wf.utils.bukkit.command.handler.subcommand.executor.Argument;
import wf.utils.bukkit.command.handler.subcommand.executor.types.ArgumentType;
import wf.utils.bukkit.config.BukkitConfig;
import wf.utils.bukkit.config.language.models.LanguageType;
import wf.utils.bukkit.entity.PlayerUtils;
import wf.utils.bukkit.inventory.gui.BukkitGuiController;
import wf.utils.bukkit.inventory.gui.item.ItemList;
import wf.utils.java.data.list.ListUtils;
import wf.utils.java.data.number.NumberUtils;
import wf.utils.java.math.smooth.SmoothTransform;
import wf.utils.java.math.smooth.SmoothTransformFunctionType;
import wf.utils.java.math.smooth.controller.SmoothTransformEntity;
import wf.utils.java.math.smooth.controller.SmoothTransformEntityController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;


public class Main extends JavaPlugin implements Listener {


    private static SmoothTransformEntity<String> controller = new SmoothTransformEntity<>();


    private static final SmoothTransform.SmoothTransformFunction FUNCTION_TYPE = SmoothTransformFunctionType.EASE_OUT;
    private static int DURATION = 4000;

    private static CommandHandler languageCommand;


    @Override
    public void onEnable() {




        languageCommand = new CommandHandlerBuilder()
                .setPlugin(this)
                .setCommands("language")
                .setAutoAddDefaultCommands(true)
                .setLanguagePath("languages")
                .setLanguageType(LanguageType.GENERAL)
                .setDefaultLanguages("ru", "en")
                .build();

       languageCommand.getMess(null, "VILLAGER.SET");



        CommandHandler handler = new CommandHandler(this, "transform");

        handler.addSubcommand(new SubCommandBuilder()
                .setCommand("duration")
                .setArguments(new Argument("duration", ArgumentType.INTEGER))
                .setRunnable((sender, command, args) -> {
                    DURATION = (Integer) args[0];
                }).build());

        handler.addSubcommand(new SubCommandBuilder()
                .setCommand("add")
                .setArguments(new Argument("count", ArgumentType.DOUBLE))
                .setRunnable((sender, command, args) -> {
                    controller.addOrUpdateItem(sender.getName(), new SmoothTransform(0, (Double) args[0], DURATION, FUNCTION_TYPE));
                }).build());

        Bukkit.getOnlinePlayers().forEach((p) -> {
            controller.setOrUpdateItem(p.getName(), new SmoothTransform(0,0, DURATION, FUNCTION_TYPE));
        });

        Bukkit.getPluginManager().registerEvents(this,this);

        Bukkit.getScheduler().runTaskTimer(this, (t) -> {

            controller.getItems().entrySet().forEach((e) -> {
                if(Bukkit.getPlayer(e.getKey()) == null) return;
                PlayerUtils.sendActionbar(Bukkit.getPlayer(e.getKey()),ChatColor.YELLOW + NumberUtils.formatNumber(e.getValue().get(),1) + ChatColor.AQUA + "$");
            });

        },1,1);

    }

    @EventHandler
    public void playerJoin(PlayerJoinEvent event){

        controller.setOrUpdateItem(event.getPlayer().getName(), new SmoothTransform(0,0, DURATION, FUNCTION_TYPE));

    }

    @EventHandler
    public void pickUpItem(EntityPickupItemEvent event){
        if(!(event.getEntity() instanceof Player)) return;

        controller.addOrUpdateItem(event.getEntity().getName(), new SmoothTransform(0,1000, DURATION, FUNCTION_TYPE));

    }




}
