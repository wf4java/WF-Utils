package wf.utils.bukkit.misc.chat_color;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class ListColored {

    public static List<String> translate(char c, List<String> list){
        list.replaceAll(textToTranslate -> ChatColor.translateAlternateColorCodes(c, textToTranslate));
        return list;
    }

}
