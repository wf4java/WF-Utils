package wf.utils.bukkit.command.handler.subcommand.executor.types.standart;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import wf.utils.bukkit.command.handler.subcommand.executor.types.ArgumentType;
import wf.utils.java.values.TypeUtils;

import java.util.Arrays;
import java.util.List;


public class DoubleArgument implements ArgumentType {

    @Override
    public String getMessage() {
        return "This argument is not valid, enter an number!";
    }

    @Override
    public String getMessageCode() {
        return "COMMAND.DEFAULT.ARGUMENT.DOUBLE_ARGUMENT_WRONG";
    }

    @Override
    public String getName() {
        return "double";
    }

    @Override
    public boolean isIt(CommandSender sender, String argument) {
        return TypeUtils.isDouble(argument);
    }

    @Override
    public Object get(CommandSender sender, String argument) {
        return Double.parseDouble(argument);
    }

    @Override
    public List<String> tabulation(CommandSender sender, String arg) {
        return Arrays.asList("0.0", "1.0", "5.0", "10.0");
    }

}
