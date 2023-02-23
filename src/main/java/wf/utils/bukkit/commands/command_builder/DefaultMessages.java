package wf.utils.bukkit.commands.command_builder;

import org.bukkit.configuration.file.FileConfiguration;

public class DefaultMessages {

    private static boolean isEdited;

    public static boolean load(FileConfiguration config, boolean empty){
        isEdited = false;
        set(config, empty,"DEFAULT.COMMAND.WRITE_ALL_ARGUMENTS","Write all arguments!");
        set(config, empty,"DEFAULT.COMMAND.COMMAND_NOT_FOUND","Command not found!");
        set(config, empty,"DEFAULT.COMMAND.NOT_FOUND_AVAILABLE_COMMANDS","Not found available commands!");
        set(config, empty,"DEFAULT.COMMAND.YOU_NOT_HAVE_PERMISSION","You not have permission!");
        set(config, empty,"DEFAULT.COMMAND.ARGUMENT.BLOCK_ARGUMENT_WRONG","This argument is not valid, enter minecraft block name!");
        set(config, empty,"DEFAULT.COMMAND.ARGUMENT.ITEM_ARGUMENT_WRONG","This argument is not valid, enter minecraft item name!");
        set(config, empty,"DEFAULT.COMMAND.ARGUMENT.LANGUAGE_ARGUMENT_WRONG","This argument is not valid, enter available language!");
        set(config, empty,"DEFAULT.COMMAND.ARGUMENT.MATERIAL_ARGUMENT_WRONG","This argument is not valid, enter minecraft material name!");
        set(config, empty,"DEFAULT.COMMAND.ARGUMENT.ONLINE_PLAYER_ARGUMENT_WRONG","This argument is not valid, enter online player name!");
        set(config, empty,"DEFAULT.COMMAND.ARGUMENT.BOOLEAN_ARGUMENT_WRONG","This argument is not valid, enter true/false!");
        set(config, empty,"DEFAULT.COMMAND.ARGUMENT.DOUBLE_ARGUMENT_WRONG","This argument is not valid, enter an number!");
        set(config, empty,"DEFAULT.COMMAND.ARGUMENT.INTEGER_ARGUMENT_WRONG","This argument is not valid, enter an number(integer)!");
        return isEdited;
    }

    private static void set(FileConfiguration config, boolean empty, String path, String mess){
        if(!config.contains(path)) {
            config.set(path, empty ? "Empty: " + path : mess);
            isEdited = true;
        }
    }

}
