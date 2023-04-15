package wf.utils.bukkit.command.handler.subcommand.executor.types.standart;


import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import wf.utils.bukkit.command.handler.subcommand.executor.types.ArgumentType;

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
    public boolean isIt(CommandSender sender, String argument) {
        return true;
    }

    @Override
    public Object get(CommandSender sender,String argument) {
        return argument;
    }

    @Override
    public List<String> tabulation(CommandSender sender, String arg) {
        if(name == null) return new ArrayList<>(0);
        return Arrays.asList(name);
    }


}
