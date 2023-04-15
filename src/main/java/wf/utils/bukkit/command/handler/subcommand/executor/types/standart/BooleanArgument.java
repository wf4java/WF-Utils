package wf.utils.bukkit.command.handler.subcommand.executor.types.standart;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import wf.utils.bukkit.command.handler.subcommand.executor.types.ArgumentType;
import wf.utils.java.values.TypeUtils;

import java.util.Arrays;
import java.util.List;


public class BooleanArgument implements ArgumentType {


    @Override
    public String getMessage() {
        return "This argument is not valid, enter true/false!";
    }

    @Override
    public String getMessageCode() {
        return "COMMAND.DEFAULT.ARGUMENT.BOOLEAN_ARGUMENT_WRONG";
    }

    @Override
    public String getName() {
        return "boolean";
    }

    @Override
    public boolean isIt(CommandSender sender, String argument) {
        return TypeUtils.isBoolean(argument);
    }

    @Override
    public Object get(CommandSender sender,String argument) {
        return Boolean.parseBoolean(argument);
    }

    @Override
    public List<String> tabulation(CommandSender sender, String arg) {
        return Arrays.asList("true", "false");
    }
}
