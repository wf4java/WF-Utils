package wf.utils.bukkit.command.handler.subcommand.executor.types.bukkit;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import wf.utils.bukkit.command.handler.subcommand.executor.types.ArgumentType;
import wf.utils.bukkit.config.language.models.Language;

import java.util.List;

public class LanguageArgument implements ArgumentType {

    private Language language;

    public LanguageArgument(Language language) {
        this.language = language;
    }

    @Override
    public String getMessage() {
        return "This argument is not valid, enter available language!";
    }

    @Override
    public String getMessageCode() {
        return "COMMAND.DEFAULT.ARGUMENT.LANGUAGE_ARGUMENT_WRONG";
    }

    @Override
    public String getName() {
        return "language";
    }

    @Override
    public boolean isIt(CommandSender sender, String argument) {
        for(String language : language.getAvailableLanguages()) if(language.equalsIgnoreCase(argument)) return true;
        return false;
    }

    @Override
    public Object get(CommandSender sender,String argument) {
        for(String language : language.getAvailableLanguages()) if(language.equalsIgnoreCase(argument)) return language;
        return argument;
    }

    @Override
    public List<String> tabulation(CommandSender sender, String arg) {
        return language.getAvailableLanguages();
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }
}
