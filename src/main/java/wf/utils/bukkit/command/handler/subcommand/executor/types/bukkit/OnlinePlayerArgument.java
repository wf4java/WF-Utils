package wf.utils.bukkit.command.handler.subcommand.executor.types.bukkit;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import wf.utils.bukkit.command.handler.subcommand.executor.types.ArgumentType;

import java.util.List;
import java.util.stream.Collectors;

public class OnlinePlayerArgument implements ArgumentType {

    boolean fullName = true;

    public OnlinePlayerArgument() { }

    public OnlinePlayerArgument(boolean fullName) {
        this.fullName = fullName;
    }

    @Override
    public String getMessage() {
        return "This argument is not valid, enter online " + (fullName ? "player full name!" : "player name!");
    }

    @Override
    public String getMessageCode() {
        return "COMMAND.DEFAULT.ARGUMENT.ONLINE_PLAYER_ARGUMENT_WRONG";
    }

    @Override
    public String getName() {
        return "online_player";
    }

    @Override
    public boolean isIt(CommandSender sender, String argument) {
        Player player = Bukkit.getPlayer(argument);
        return player == null ? false : (fullName ? (player.getName().equalsIgnoreCase(argument)) : true);
    }

    @Override
    public Object get(CommandSender sender,String argument) {
        return Bukkit.getPlayer(argument);
    }

    @Override
    public List<String> tabulation(CommandSender sender, String arg) {
        return Bukkit.getOnlinePlayers().stream().map((p) -> p.getName()).collect(Collectors.toList());
    }





}
