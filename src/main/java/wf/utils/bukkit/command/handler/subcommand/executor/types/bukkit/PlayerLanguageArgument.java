package wf.utils.bukkit.command.handler.subcommand.executor.types.bukkit;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import wf.utils.bukkit.command.handler.subcommand.executor.types.ArgumentType;

import java.util.List;

public class PlayerLanguageArgument implements ArgumentType {
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
        return "languages";
    }

    @Override
    public boolean isIt(CommandSender sender, String argument) {
       // for(String language : PlayerLanguage.getLoadedLanguages().keySet()) if(language.equalsIgnoreCase(argument)) return true;
        return false;
    }

    @Override
    public Object get(CommandSender sender,String argument) {
        //for(String language : PlayerLanguage.getLoadedLanguages().keySet()) if(language.equalsIgnoreCase(argument)) return language;
        return argument;
    }

    @Override
    public List<String> tabulation(CommandSender sender, String arg) {
        //return PlayerLanguage.getLoadedLanguages().keySet().stream().collect(Collectors.toList());
        return null;
    }
}
