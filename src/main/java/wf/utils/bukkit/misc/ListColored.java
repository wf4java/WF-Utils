package wf.utils.bukkit.misc;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class ListColored {

    public static List<String> chatColored(List<String> list, char c){
        List<String> ret = new ArrayList<>();
        for(String s : list) ret.add(ChatColor.translateAlternateColorCodes(c, s));
        return ret;
    }

}
