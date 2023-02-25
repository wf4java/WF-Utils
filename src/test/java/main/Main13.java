package main;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import wf.utils.bukkit.commands.command_builder.Argument;
import wf.utils.bukkit.commands.command_builder.types.ArgumentType;
import wf.utils.bukkit.commands.command_handler.SubCommandBuilder;
import wf.utils.bukkit.commands.command_handler.Subcommand;
import wf.utils.java.thread.queue.Queue;
import wf.utils.java.time.cooldown.CoolDown;

public class Main13 {

    public static CoolDown coolDown = new CoolDown<>();

    public static void main(String[] args) {

        Subcommand subcommand = new SubCommandBuilder()
                .setCommand("invite")
                .setPermission("moderator")
                .setOnlyPlayer(true)
                .setArguments(
                        new Argument("player", ArgumentType.STRING),
                        new Argument("player", ArgumentType.STRING))
                .setRunnable(Main13::test)
                .build();

    }

    private static void test(CommandSender commandSender, Command command, Object[] objects) {

    }



}
