package wf.utils.bukkit.commands.command_handler.subcommand.executor.types.standart;


import org.bukkit.entity.Player;
import wf.utils.bukkit.commands.command_handler.subcommand.executor.types.ArgumentType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StringArgument implements ArgumentType {

    private String name;

    public StringArgument() { }

    public StringArgument(String name) {
        this.name = name;
    }


    @Override
    public String getMessage() {
        return null;
    }

    @Override
    public String getMessageCode() {
        return null;
    }

    @Override
    public String getName() {
        return "string";
    }

    @Override
    public boolean isIt(String argument) {
        return true;
    }

    @Override
    public Object get(String argument) {
        return argument;
    }

    @Override
    public List<String> tabulation(Player player, String arg) {
        if(name == null) return new ArrayList<>(0);
        return Arrays.asList(name);
    }


}
