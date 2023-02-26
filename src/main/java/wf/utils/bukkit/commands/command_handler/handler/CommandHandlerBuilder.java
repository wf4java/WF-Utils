package wf.utils.bukkit.commands.command_handler.handler;

import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;
import wf.utils.bukkit.config.language.GeneralLanguage;
import wf.utils.bukkit.config.language.Language;
import wf.utils.bukkit.config.language.LanguageType;
import wf.utils.bukkit.config.language.PlayerLanguage;

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

    public CommandHandlerBuilder addCommand(String command) {
        commands.add(command);
        return this;
    }

    public CommandHandlerBuilder addDefaultLanguage(String language) {
        defaultLanguages.add(language);
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
        if(languageType == LanguageType.GENERAL){return new GeneralLanguage(plugin, languagePath, defaultLanguages.toArray(new String[0]));}
        if(languageType == LanguageType.PERSONAL){
            return new PlayerLanguage(null, null, null);
        }
        return null;
    }

}

