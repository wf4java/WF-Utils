package wf.utils.bukkit.entity;


import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;


public class PlayerUtils {

    public static void sendActionbar(Player player, String message){
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(message));
    }



}
