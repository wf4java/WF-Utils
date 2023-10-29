package wf.utils.bukkit.command.handler.handler;

import org.bukkit.plugin.java.JavaPlugin;
import wf.utils.bukkit.command.handler.DefaultCommandHandlerMessages;
import wf.utils.bukkit.config.language.GeneralLanguage;
import wf.utils.bukkit.config.language.models.Language;
import wf.utils.bukkit.config.language.models.LanguageType;
import wf.utils.bukkit.config.language.PersonalLanguage;
import wf.utils.java.file.yamlconfiguration.configuration.ConfigDefaultValue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;


public class CommandHandlerBuilder {


    private JavaPlugin plugin;
    private ArrayList<String> commands = new ArrayList<>();
    private ArrayList<String> defaultLanguages = new ArrayList<>();
    private boolean autoAddDefaultCommands = true;
    private String languagePath;
    private LanguageType languageType = LanguageType.GENERAL;
    private Collection<ConfigDefaultValue> values;


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

    public CommandHandlerBuilder setValues(Collection<ConfigDefaultValue> values) {
        this.values = values;
        return this;
    }

    public CommandHandlerBuilder setDefaultValues(ConfigDefaultValue... configDefaultValues) {
        values = Arrays.asList(configDefaultValues);
        return this;
    }

    private Language createLanguage(){
        if(languagePath == null) return null;
        if(languageType == LanguageType.GENERAL){return new GeneralLanguage(plugin, languagePath, defaultLanguages.toArray(new String[0]), getValues());}
        if(languageType == LanguageType.PERSONAL){return new PersonalLanguage(plugin, languagePath, defaultLanguages.toArray(new String[0]), getValues());}
        return null;
    }

    private Collection<ConfigDefaultValue> getValues(){
        if(values == null) return DefaultCommandHandlerMessages.getInstance().getValues();
        return concatenateValues(DefaultCommandHandlerMessages.getInstance().getValues(), values);
    }

    private static Collection<ConfigDefaultValue> concatenateValues(Collection<ConfigDefaultValue> a, Collection<ConfigDefaultValue> b) {
        a.addAll(b);
        return a.stream().distinct().collect(Collectors.toList());
    }

    public CommandHandler build(){
        return new CommandHandler(plugin, commands.toArray(new String[0]), createLanguage(), autoAddDefaultCommands);
    }

}

