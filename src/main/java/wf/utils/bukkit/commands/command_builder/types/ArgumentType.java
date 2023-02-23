package wf.utils.bukkit.commands.command_builder.types;


import org.bukkit.entity.Player;
import wf.utils.bukkit.commands.command_builder.types.standart.IntegerArgument;
import wf.utils.bukkit.commands.command_builder.types.standart.BooleanArgument;
import wf.utils.bukkit.commands.command_builder.types.standart.DoubleArgument;
import wf.utils.bukkit.commands.command_builder.types.standart.StringArgument;

import java.util.List;

public interface ArgumentType {

    public static final ArgumentType STRING = new StringArgument();
    public static final ArgumentType DOUBLE = new DoubleArgument();
    public static final ArgumentType INTEGER = new IntegerArgument();
    public static final ArgumentType BOOLEAN = new BooleanArgument();


    public String getMessage();
    public String getMessageCode();
    public String getName();
    public boolean isIt(String argument);
    public Object get(String argument);
    public List<String> tabulation(Player player, String arg);


}


