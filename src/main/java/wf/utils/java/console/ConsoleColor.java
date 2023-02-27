package wf.utils.java.console;

import org.bukkit.ChatColor;


public class ConsoleColor {
	

    public static String colored(String text, Color color, boolean bold) {
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
            default : { builder.append("37"); }
        }
        builder.append(";1;" + (bold ? "1" : "2") + "m" + text + "\033[0m");
        return builder.toString();
    }

    public static String colored(String text, Color color) {
        return colored(text, color, false);
    }


    public enum Color{
        RED,
        DARK_RED,
        GREEN,
        YELLOW,
        GOLD,
        BLUE,
        DARK_PURPLE,
        AQUA,
        GRAY
    }


    public static String translateAlternateColorCodes(String text, char key) {
        return translateAlternateColorCodes(text, key,false);
    }

    public static String translateAlternateColorCodes(String text, char key, boolean bold) {
        StringBuilder builder = new StringBuilder();

        for(String s : text.split(String.valueOf(key))){
            if(s.length() == 0) continue;
            builder.append(colored(s.substring(1),getColorByChar(s.substring(0, 1)), bold));
        }

        return builder.toString();
    }

    public static Color getColorByChar(String s){
        switch(s.toLowerCase()){
            case "c" : { return Color.RED; }
            case "4" : { return Color.DARK_RED; }
            case "a" : { return Color.GREEN; }
            case "e" : { return Color.YELLOW; }
            case "6" : {return Color.GOLD; }
            case "9" : { return Color.BLUE; }
            case "5" : { return Color.DARK_PURPLE;}
            case "b" : { return Color.AQUA; }
            case "7" :
            default : { return Color.GRAY; }
        }
    }

}
