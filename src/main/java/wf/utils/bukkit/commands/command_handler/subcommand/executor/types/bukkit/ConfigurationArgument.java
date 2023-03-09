package wf.utils.bukkit.commands.command_handler.subcommand.executor.types.bukkit;

import org.bukkit.Material;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import wf.utils.bukkit.commands.command_handler.subcommand.executor.types.ArgumentType;
import wf.utils.java.file.yamlconfiguration.file.FileConfiguration;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ConfigurationArgument implements ArgumentType {

    private Configuration configuration;
    private String path;

    public ConfigurationArgument(Configuration configuration, String path) {
        this.configuration = configuration;
        this.path = path;
    }

    public ConfigurationArgument(Configuration configuration) {
        this.configuration = configuration;
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
        if(path == null) return configuration.contains(argument);
        else return configuration.contains(path + "." + argument);
    }

    @Override
    public Object get(String argument) {
        return argument;
    }

    @Override
    public List<String> tabulation(Player player, String arg) {
        if(path == null) return new ArrayList<>(configuration.getKeys(false));
        else return new ArrayList<>(configuration.getConfigurationSection(path).getKeys(false));

    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
