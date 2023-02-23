package wf.utils.bukkit.commands.command_builder.types.bukkit;


import wf.utils.bukkit.commands.command_builder.types.ArgumentType;
import wf.utils.bukkit.commands.command_builder.types.bukkit.target_block.XTargetBlockArgument;
import wf.utils.bukkit.commands.command_builder.types.bukkit.target_block.YTargetBlockArgument;
import wf.utils.bukkit.commands.command_builder.types.bukkit.target_block.ZTargetBlockArgument;

public interface BukkitArgumentType {

    public static final ArgumentType ONLINE_PLAYER = new OnlinePlayerArgument(true);
    public static final ArgumentType MATERIAL = new MaterialArgument();
    public static final ArgumentType BLOCK = new BlockArgument();
    public static final ArgumentType ITEM = new ItemArgument();

    public static final ArgumentType X_TARGET_BLOCK = new XTargetBlockArgument(25,true);
    public static final ArgumentType Y_TARGET_BLOCK = new YTargetBlockArgument(25,true);
    public static final ArgumentType Z_TARGET_BLOCK = new ZTargetBlockArgument(25,true);

    public static final ArgumentType LANGUAGE = new LanguageArgument();
    public static final ArgumentType PLAYER_LANGUAGE = new LanguageArgument();


}
