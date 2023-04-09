package wf.utils.bukkit.command.handler;

import wf.utils.java.file.yamlconfiguration.configuration.ConfigDefaultValue;

public class DefaultCommandHandlerMessages {

    private static ConfigDefaultValue[] values = new ConfigDefaultValue[14];

    static {

        values[0] = new ConfigDefaultValue("COMMAND.DEFAULT.WRITE_ALL_ARGUMENTS","Write all arguments!");
        values[1] = new ConfigDefaultValue("COMMAND.DEFAULT.COMMAND_NOT_FOUND","Command not found!");
        values[2] = new ConfigDefaultValue("COMMAND.DEFAULT.NOT_FOUND_AVAILABLE_COMMANDS","Not found available commands!");
        values[3] = new ConfigDefaultValue("COMMAND.DEFAULT.YOU_NOT_HAVE_PERMISSION","You not have permission!");
        values[4] = new ConfigDefaultValue("COMMAND.DEFAULT.LANGUAGE_CHANGE", "You change language on: %{lang}");
        values[5] = new ConfigDefaultValue("COMMAND.DEFAULT.ARGUMENT.BLOCK_ARGUMENT_WRONG","This argument is not valid, enter minecraft block name!");
        values[6] = new ConfigDefaultValue("COMMAND.DEFAULT.ARGUMENT.ITEM_ARGUMENT_WRONG","This argument is not valid, enter minecraft item name!");
        values[7] = new ConfigDefaultValue("COMMAND.DEFAULT.ARGUMENT.LANGUAGE_ARGUMENT_WRONG","This argument is not valid, enter available language!");
        values[8] = new ConfigDefaultValue("COMMAND.DEFAULT.ARGUMENT.MATERIAL_ARGUMENT_WRONG","This argument is not valid, enter minecraft material name!");
        values[9] = new ConfigDefaultValue("COMMAND.DEFAULT.ARGUMENT.ONLINE_PLAYER_ARGUMENT_WRONG","This argument is not valid, enter online player name!");
        values[10] = new ConfigDefaultValue("COMMAND.DEFAULT.ARGUMENT.BOOLEAN_ARGUMENT_WRONG","This argument is not valid, enter true/false!");
        values[11] = new ConfigDefaultValue("COMMAND.DEFAULT.ARGUMENT.DOUBLE_ARGUMENT_WRONG","This argument is not valid, enter a number!");
        values[12] = new ConfigDefaultValue("COMMAND.DEFAULT.ARGUMENT.INTEGER_ARGUMENT_WRONG","This argument is not valid, enter a number(integer)!");
        values[13] = new ConfigDefaultValue("COMMAND.DEFAULT.ARGUMENT.CONFIGURATION_SECTION_ARGUMENT_WRONG","This argument is not valid, enter valid name!");


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


    public static ConfigDefaultValue[] getValues() {
        return values;
    }
}
