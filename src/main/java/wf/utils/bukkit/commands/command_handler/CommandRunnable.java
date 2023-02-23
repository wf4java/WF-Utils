package wf.utils.bukkit.commands.command_handler;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public interface CommandRunnable {

    public void run(CommandSender sender, Command command, Object[] args);

}
