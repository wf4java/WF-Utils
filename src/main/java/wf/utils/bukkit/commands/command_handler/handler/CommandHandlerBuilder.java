package wf.utils.bukkit.commands.command_handler.handler;

import org.bukkit.plugin.java.JavaPlugin;
import wf.utils.bukkit.config.language.GeneralLanguage;
import wf.utils.bukkit.config.language.models.Language;
import wf.utils.bukkit.config.language.models.LanguageType;
import wf.utils.bukkit.config.language.PersonalLanguage;
import wf.utils.java.file.yamlconfiguration.configuration.ConfigDefaultValue;

import java.util.ArrayList;
import java.util.Arrays;


public class CommandHandlerBuilder {


    private JavaPlugin plugin;
    private ArrayList<String> commands = new ArrayList<>();
    private ArrayList<String> defaultLanguages = new ArrayList<>();
    private boolean autoAddDefaultCommands = true;
    private String languagePath;
    private LanguageType languageType = LanguageType.GENERAL;
    private ConfigDefaultValue[] values;


    public CommandHandlerBuilder setPlugin(JavaPlugin plugin) {
        this.plugin = plugin;
        return this;
    }

    public CommandHandlerBuilder setCommands(String... command) {
        if(!commands.isEmpty()) commands.clear();
        for(String s : command){commands.add(s);}
        return this;
    }


    public CommandHandlerBuilder setDefaultLanguages(String... language) {
        if(!defaultLanguages.isEmpty()) defaultLanguages.clear();
        for(String s : language){defaultLanguages.add(s);}
        return this;
    }

    public CommandHandlerBuilder setAutoAddDefaultCommands(boolean autoAddDefaultCommands) {
        this.autoAddDefaultCommands = autoAddDefaultCommands;
        return this;
    }

    public CommandHandlerBuilder setLanguagePath(String languagePath) {
        this.languagePath = languagePath;
        return this;
    }

    public CommandHandlerBuilder setLanguageType(LanguageType languageType) {
        this.languageType = languageType;
        return this;
    }


    public CommandHandler build(){
        return new CommandHandler(plugin, commands.toArray(new String[0]), createLanguage(), autoAddDefaultCommands);
    }

    private Language createLanguage(){
        if(languagePath == null) return null;
        if(languageType == LanguageType.GENERAL){return new GeneralLanguage(plugin, languagePath, defaultLanguages.toArray(new String[0]), getValues());}
        if(languageType == LanguageType.PERSONAL){return new PersonalLanguage(plugin, languagePath, defaultLanguages.toArray(new String[0]), getValues());}
        return null;
    }

    private ConfigDefaultValue[] getValues(){
        if(values == null) return CommandHandler.getLanguageDefaultValues();
        return concatenateValues(CommandHandler.getLanguageDefaultValues(), values);
    }

    private static ConfigDefaultValue[] concatenateValues(ConfigDefaultValue[] a, ConfigDefaultValue[] b) {
        ConfigDefaultValue[] result = new ConfigDefaultValue[a.length + b.length];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }

}

