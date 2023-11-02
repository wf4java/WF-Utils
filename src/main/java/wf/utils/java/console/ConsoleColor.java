package wf.utils.java.console;


import java.util.Random;

public class ConsoleColor {
	

    public static String colored(String text, Color color, boolean bold) {
        StringBuilder builder = new StringBuilder("\033[");

        switch(color){
            case RED:
            case DARK_RED : { builder.append("31"); break; }
            case GREEN : { builder.append("32"); break; }
            case YELLOW :
            case GOLD : { builder.append("33"); break; }
            case BLUE : { builder.append("34"); break; }
            case DARK_PURPLE : { builder.append("35"); break; }
            case AQUA : { builder.append("36"); break; }
            case GRAY :
            default : { builder.append("37"); break; }
        }
        builder.append(";1;").append(bold ? "1" : "2").append("m").append(text).append("\033[0m");
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

        for(String s : text.split("\\" + key)){
            if(s.isEmpty()) continue;

            builder.append(colored(s.substring(1),getColorByChar(s.substring(0, 1)), bold));
        }

        return builder.toString();
    }

    public static String rainbow(String text) {
        return rainbow(text, false, true);
    }

    public static String rainbow(String text, boolean round) {
        return rainbow(text, false, round);
    }

    public static String rainbow(String text, boolean bold, boolean round) {
        StringBuilder builder = new StringBuilder();
        if(round){
            char[] letters = text.toCharArray();
            for (int i = 0; i < letters.length; i++)
                builder.append(colored(String.valueOf(letters[i]), Color.values()[i % Color.values().length], bold));

        }else{
            Random random = new Random();
            for (char letter : text.toCharArray())
                builder.append(colored(String.valueOf(letter), Color.values()[random.nextInt(Color.values().length)], bold));
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
