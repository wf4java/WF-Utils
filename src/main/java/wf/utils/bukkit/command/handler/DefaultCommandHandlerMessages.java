package wf.utils.bukkit.command.handler;

import wf.utils.java.file.yamlconfiguration.configuration.ConfigDefaultValue;
import wf.utils.java.file.yamlconfiguration.configuration.ConfigDefaultValues;

import java.util.Arrays;
import java.util.Collection;

public class DefaultCommandHandlerMessages implements ConfigDefaultValues {


    private final Collection<ConfigDefaultValue> values;

    private static DefaultCommandHandlerMessages instance;

    private DefaultCommandHandlerMessages() {
        values = Arrays.asList(
                new ConfigDefaultValue("COMMAND.DEFAULT.WRITE_ALL_ARGUMENTS","Write all arguments!"),
                new ConfigDefaultValue("COMMAND.DEFAULT.COMMAND_NOT_FOUND","Command not found!"),
                new ConfigDefaultValue("COMMAND.DEFAULT.NOT_FOUND_AVAILABLE_COMMANDS","Not found available commands!"),
                new ConfigDefaultValue("COMMAND.DEFAULT.YOU_NOT_HAVE_PERMISSION","You not have permission!"),
                new ConfigDefaultValue("COMMAND.DEFAULT.LANGUAGE_CHANGE", "You change language on: %{lang}"),
                new ConfigDefaultValue("COMMAND.DEFAULT.ARGUMENT.BLOCK_ARGUMENT_WRONG","This argument is not valid, enter minecraft block name!"),
                new ConfigDefaultValue("COMMAND.DEFAULT.ARGUMENT.ITEM_ARGUMENT_WRONG","This argument is not valid, enter minecraft item name!"),
                new ConfigDefaultValue("COMMAND.DEFAULT.ARGUMENT.LANGUAGE_ARGUMENT_WRONG","This argument is not valid, enter available language!"),
                new ConfigDefaultValue("COMMAND.DEFAULT.ARGUMENT.MATERIAL_ARGUMENT_WRONG","This argument is not valid, enter minecraft material name!"),
                new ConfigDefaultValue("COMMAND.DEFAULT.ARGUMENT.ONLINE_PLAYER_ARGUMENT_WRONG","This argument is not valid, enter online player name!"),
                new ConfigDefaultValue("COMMAND.DEFAULT.ARGUMENT.BOOLEAN_ARGUMENT_WRONG","This argument is not valid, enter true/false!"),
                new ConfigDefaultValue("COMMAND.DEFAULT.ARGUMENT.DOUBLE_ARGUMENT_WRONG","This argument is not valid, enter a number!"),
                new ConfigDefaultValue("COMMAND.DEFAULT.ARGUMENT.CONFIGURATION_SECTION_ARGUMENT_WRONG","This argument is not valid, enter valid name!"));
    }



//CONFIG FORMAT:
/*
COMMAND:
    DEFAULT:
        WRITE_ALL_ARGUMENTS: Write all arguments!
        COMMAND_NOT_FOUND: Command not found!
        NOT_FOUND_AVAILABLE_COMMANDS: Not found available commands!
        YOU_NOT_HAVE_PERMISSION: You not have permission!
        LANGUAGE_CHANGE: 'You change language on: %{lang}'
        ARGUMENT:
            BLOCK_ARGUMENT_WRONG: This argument is not valid, enter minecraft block name!
            ITEM_ARGUMENT_WRONG: This argument is not valid, enter minecraft item name!
            LANGUAGE_ARGUMENT_WRONG: This argument is not valid, enter available language!
            MATERIAL_ARGUMENT_WRONG: This argument is not valid, enter minecraft material name!
            ONLINE_PLAYER_ARGUMENT_WRONG: This argument is not valid, enter online player name!
            BOOLEAN_ARGUMENT_WRONG: This argument is not valid, enter true/false!
            DOUBLE_ARGUMENT_WRONG: This argument is not valid, enter a number!
            INTEGER_ARGUMENT_WRONG: This argument is not valid, enter a number(integer)!
*/


    public static DefaultCommandHandlerMessages getInstance() {
        if(instance == null) instance = new DefaultCommandHandlerMessages();
        return instance;
    }


    public Collection<ConfigDefaultValue> getValues() {
        return values;
    }


}
