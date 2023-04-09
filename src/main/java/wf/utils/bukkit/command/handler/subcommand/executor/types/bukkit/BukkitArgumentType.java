package wf.utils.bukkit.command.handler.subcommand.executor.types.bukkit;


import org.bukkit.configuration.Configuration;
import wf.utils.bukkit.command.handler.subcommand.executor.types.bukkit.target_block.XTargetBlockArgument;
import wf.utils.bukkit.command.handler.subcommand.executor.types.bukkit.target_block.YTargetBlockArgument;
import wf.utils.bukkit.command.handler.subcommand.executor.types.bukkit.target_block.ZTargetBlockArgument;
import wf.utils.bukkit.command.handler.subcommand.executor.types.ArgumentType;
import wf.utils.bukkit.config.language.models.Language;

public class BukkitArgumentType {

    public static final ArgumentType ONLINE_PLAYER = new OnlinePlayerArgument(true);
    public static final ArgumentType MATERIAL = new MaterialArgument();
    public static final ArgumentType BLOCK = new BlockArgument();
    public static final ArgumentType ITEM = new ItemArgument();

    public static final ArgumentType X_TARGET_BLOCK = new XTargetBlockArgument(25,true);
    public static final ArgumentType Y_TARGET_BLOCK = new YTargetBlockArgument(25,true);
    public static final ArgumentType Z_TARGET_BLOCK = new ZTargetBlockArgument(25,true);


    public static ArgumentType LANGUAGE(Language language){return new LanguageArgument(language);}
    public static ArgumentType CONFIGURATION(Configuration configuration){return new ConfigurationArgument(configuration);}
    public static ArgumentType CONFIGURATION(Configuration configuration, String path){
        return new ConfigurationArgument(configuration, path);
    }

}
