package wf.utils.bukkit.commands.command_handler.subcommand.executor.types.bukkit;

import org.bukkit.Material;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import wf.utils.bukkit.commands.command_handler.subcommand.executor.types.ArgumentType;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ConfigurationSectionArgument implements ArgumentType {

    private ConfigurationSection section;

    public ConfigurationSectionArgument(ConfigurationSection section) {
        this.section = section;
    }
    public ConfigurationSectionArgument(Configuration configuration) {
        this(configuration.getDefaultSection());
    }

    @Override
    public String getMessage() {
        return "This argument is not valid, enter valid name!";
    }

    @Override
    public String getMessageCode() {
        return "COMMAND.DEFAULT.ARGUMENT.CONFIGURATION_SECTION_ARGUMENT_WRONG";
    }

    @Override
    public String getName() {
        return "configuration_section";
    }

    @Override
    public boolean isIt(String argument) {
        return section.contains(argument);
    }

    @Override
    public Object get(String argument) {
        return argument;
    }

    @Override
    public List<String> tabulation(Player player, String arg) {
        return new ArrayList<>(section.getKeys(false));
    }

}
