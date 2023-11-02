package main.main25;


import java.util.Arrays;

public class Main19 {


    public static void main(String[] args) {

        //System.out.println(format(1236.5,2,2));

    }




//    public static String format(double value, int step, int max){
//        String[] form = String.format("%,.2f", value).replaceFirst(",",".").split("\\.");
//        StringBuilder builder = new StringBuilder();
//
//        if(form[1].length() == 2 && form[1].charAt(0) == '0') form[1] = String.valueOf(form[1].charAt(1));
//
//        builder.append(form[1]).append(getMonetType(0));
//
//        if(form[0].equals("0")) return builder.toString();
//
//        String[] values = splitOnEveryCharAmount(form[0], step);
//        if(values.length > max){
//            values = (String[]) ArrayUtils.addAll(new String[]{String.join("",
//                    Arrays.copyOfRange(values,0,values.length-max+1))},
//                    Arrays.copyOfRange(values,values.length-max+1, values.length));
//        }
//
//
//        for(int i = 0; i < values.length; i++){
//            builder.insert(0,values[values.length - 1 - i].replaceFirst("^0+(?!$)", "") + getMonetType(i > max ? max : i + 1));
//        }
//
//        return builder.toString().replace(" ","");
//    }
//
//    public static String[] splitOnEveryCharAmount(String s, int amount){
//        String[] split = new StringBuilder(s).reverse().toString().split("(?<=\\G.{" + amount + "})");
//        ArrayUtils.reverse(split);
//        for(int i = 0; i < split.length; i++){
//            split[i] = new StringBuilder(split[i]).reverse().toString();
//        }
//        return split;
//    }
//
//    private static String getMonetType(int type){
//        switch (type){
//            case 0: return "медь";
//            case 1: return "серебро";
//            case 2: return "золота";
//            default: return "";
//        }
//    }



}
