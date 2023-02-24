package wf.utils.java.values;

import java.util.regex.Pattern;

public class TypeUtils {

    private static Pattern integerRegex = Pattern.compile("-?\\d+");
    private static Pattern doubleRegex = Pattern.compile("-?\\d+(\\.\\d+)?");
    private static Pattern linkRegex = Pattern.compile("^https?:\\/\\/(?:www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b(?:[-a-zA-Z0-9()@:%_\\+.~#?&\\/=]*)$");



    public static boolean isDouble(String arg) {
        if(arg.length() == 0) return false;
        return doubleRegex.matcher(arg).matches();
    }

    public static boolean isInteger(String arg) {
        if(arg.length() == 0) return false;
        return integerRegex.matcher(arg).matches();
    }

    public static boolean isLink(String arg){
        if(arg.length() == 0) return false;
        return linkRegex.matcher(arg).matches();
    }

    public static boolean isBoolean(String arg){
        if(arg.length() == 0) return false;
        return arg.equalsIgnoreCase("true") || arg.equalsIgnoreCase("false");
    }





}
