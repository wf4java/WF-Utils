package wf.utils.bukkit.commands.command_builder.types.bukkit;

import org.bukkit.entity.Player;
import wf.utils.bukkit.commands.command_builder.types.ArgumentType;
import wf.utils.bukkit.config.language.GeneralLanguage;

import java.util.List;

public class LanguageArgument implements ArgumentType {
    @Override
    public String getMessage() {
        return "This argument is not valid, enter available language!";
    }

    @Override
    public String getMessageCode() {
        return "LANGUAGE_ARGUMENT_WRONG";
    }

    @Override
    public String getName() {
        return "languages";
    }

    @Override
    public boolean isIt(String argument) {
        //for(String language : GeneralLanguage.getAvailableLanguages()) if(language.equalsIgnoreCase(argument)) return true;
        return false;
    }

    @Override
    public Object get(String argument) {
        //for(String language : GeneralLanguage.getAvailableLanguages()) if(language.equalsIgnoreCase(argument)) return language;
        return argument;
    }

    @Override
    public List<String> tabulation(Player player, String arg) {
        //return GeneralLanguage.getAvailableLanguages();
        return null;
    }
}
