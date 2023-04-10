package wf.utils.bukkit.inventory.gui.item;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemReplacerUtils {

    public static String replace(ItemReplacer[] replaces, String s, Player player, Map<String, String> arguments){
        for(ItemReplacer replacer : replaces) s = replacer.replace(s, player, arguments);
        return s;
    }

    public static List<String> replace(ItemReplacer[] replaces, List<String> list, Player player, Map<String, String> arguments){
        for(ItemReplacer replacer : replaces) list = replacer.replace(list, player, arguments);
        return list;
    }

    public static Map<String, String> replace(ItemReplacer[] replaces, Map<String, String> map, Player player, Map<String, String> arguments){
        for(ItemReplacer replacer : replaces) map = replacer.replace(map, player, arguments);
        return map;
    }

}
