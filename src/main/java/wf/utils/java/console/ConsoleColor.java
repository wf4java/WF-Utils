package wf.utils.java.console;

import org.bukkit.ChatColor;


public class ConsoleColor {
	

    public static String colored(String text, ChatColor color, boolean bold) {
        StringBuilder builder = new StringBuilder("\033[");

        switch(color){
            case RED : { builder.append("31"); }
            case DARK_RED : { builder.append("31"); }
            case GREEN : { builder.append("32"); }
            case YELLOW : { builder.append("33"); }
            case GOLD : { builder.append("33"); }
            case BLUE : { builder.append("34"); }
            case DARK_PURPLE : { builder.append("35"); }
            case AQUA : { builder.append("36"); }
            case GRAY : { builder.append("37"); }
            default : { builder.append("31"); }
        }
        builder.append(";1;" + (bold ? "1" : "2") + "m" + text + "\033[0m");
        return builder.toString();
    }



    public static String translateAlternateColorCodes(String text, char key, boolean bold) {
        StringBuilder builder = new StringBuilder();

        for(String s : text.split(String.valueOf(key))){
            if(s.length() == 0) continue;
            builder.append(colored(s.substring(1),ChatColor.getByChar(s.substring(0, 1)), bold));
        }

        return builder.toString();
    }

}
