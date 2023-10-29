package main.main50;

import com.sk89q.worldedit.event.platform.Interaction;
import org.bukkit.entity.Player;
import wf.utils.bukkit.command.handler.handler.CommandHandler;
import wf.utils.bukkit.command.handler.handler.CommandHandlerBuilder;
import wf.utils.bukkit.command.handler.subcommand.SubCommandBuilder;
import wf.utils.bukkit.command.handler.subcommand.executor.Argument;
import wf.utils.bukkit.command.handler.subcommand.executor.types.ArgumentType;
import wf.utils.bukkit.command.handler.subcommand.executor.types.bukkit.BukkitArgumentType;
import wf.utils.bukkit.config.language.models.LanguageType;
import wf.utils.bukkit.data.PersistDataUtils;
import wf.utils.bukkit.entity.EntityUtils;
public class Main33 {


    public static void main(String[] args) {

        CommandHandler commandHandler = new CommandHandlerBuilder()
                .setPlugin(null)
                .setCommands("home")
                .setLanguagePath("/languages")
                .setLanguageType(LanguageType.PERSONAL)
                .setDefaultLanguages("en", "ru")
                .build();


        commandHandler.addSubcommand(new SubCommandBuilder()
                .setCommand("set")
                .setPermission("set.home")
                .setOnlyPlayer(true)
                .setArguments(
                        new Argument("name", ArgumentType.STRING),
                        new Argument("player", BukkitArgumentType.ONLINE_PLAYER),
                        new Argument("integer", ArgumentType.INTEGER)
                ).setRunnable((sender, command, arg) -> {
                    String name = (String) arg[0];
                    Player player = (Player) arg[1];

                    Integer integer = (Integer) arg[2];

                    player.sendMessage(commandHandler.getMess(sender, "SET.HOME"));

                }).build());

    }


}
