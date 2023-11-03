package wf.utils.java.values;

import java.util.regex.Pattern;

public class TypeUtils {

    private static final Pattern INTEGER_REGEX = Pattern.compile("-?\\d+");

    private static final Pattern DOUBLE_REGEX = Pattern.compile("-?\\d+(\\.\\d+)?");

    private static final Pattern LINK_REGEX = Pattern.compile("(https?:\\/\\/(?:www\\.|(?!www))[a-zA-Z0-9][a-zA-Z0-9-]+[a-zA-Z0-9]\\." +
            "\\S{2,}|[a-zA-Z0-9][a-zA-Z0-9-]+[a-zA-Z0-9]\\.[^\\s]{2,}|https?:\\/\\/(?:www\\.|(?!www))[a-zA-Z0-9]+\\." +
            "\\S{2,}|www\\.[a-zA-Z0-9]+\\.\\S{2,})");



    public static boolean isDouble(String arg) {
        if(arg.isEmpty()) return false;
        return DOUBLE_REGEX.matcher(arg).matches();
    }

    public static boolean isInteger(String arg) {
        if(arg.isEmpty()) return false;
        return INTEGER_REGEX.matcher(arg).matches();
    }

    public static boolean isLink(String arg){
        if(arg.isEmpty()) return false;
        return LINK_REGEX.matcher(arg).matches();
    }

    public static boolean isBoolean(String arg){
        if(arg.isEmpty()) return false;
        return arg.equalsIgnoreCase("true") || arg.equalsIgnoreCase("false");
    }





}
