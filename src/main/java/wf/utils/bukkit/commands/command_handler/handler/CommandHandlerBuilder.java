package wf.utils.bukkit.commands.command_handler.handler;

import org.bukkit.plugin.java.JavaPlugin;
import wf.utils.bukkit.config.language.GeneralLanguage;
import wf.utils.bukkit.config.language.models.Language;
import wf.utils.bukkit.config.language.models.LanguageType;
import wf.utils.bukkit.config.language.PersonalLanguage;

import java.util.ArrayList;


public class CommandHandlerBuilder {


    private JavaPlugin plugin;
    private ArrayList<String> commands = new ArrayList<>();
    private ArrayList<String> defaultLanguages = new ArrayList<>();
    private boolean autoAddDefaultCommands = true;
    private String languagePath;
    private LanguageType languageType = LanguageType.GENERAL;


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
        if(languageType == LanguageType.GENERAL){return new GeneralLanguage(plugin, languagePath, defaultLanguages.toArray(new String[0]), CommandHandler.getLanguageDefaultValues());}
        if(languageType == LanguageType.PERSONAL){return new PersonalLanguage(plugin, languagePath, defaultLanguages.toArray(new String[0]),CommandHandler.getLanguageDefaultValues());}
        return null;
    }

}

