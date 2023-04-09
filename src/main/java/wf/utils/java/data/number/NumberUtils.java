package wf.utils.java.data.number;

public class NumberUtils {


    public static String formatNumber(double num, int count){
        return String.format("%." + count + "f", num);
    }

    public static String formatNumber(double num){
        return String.format("%.1f", num);
    }

    public static String formatNumber(float num, int count){
        return String.format("%." + count + "f", num);
    }

    public static String formatNumber(float num){
        return String.format("%.1f", num);
    }


}
