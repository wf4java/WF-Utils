package wf.utils.bukkit.commands.command_handler.subcommand.executor.types.standart;

import org.bukkit.entity.Player;
import wf.utils.bukkit.commands.command_handler.subcommand.executor.types.ArgumentType;
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
        return "BOOLEAN_ARGUMENT_WRONG";
    }

    @Override
    public String getName() {
        return "boolean";
    }

    @Override
    public boolean isIt(String argument) {
        return TypeUtils.isBoolean(argument);
    }

    @Override
    public Object get(String argument) {
        return Boolean.parseBoolean(argument);
    }

    @Override
    public List<String> tabulation(Player player, String arg) {
        return Arrays.asList("true", "false");
    }
}
