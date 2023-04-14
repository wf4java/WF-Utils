package main.main25;

import wf.utils.java.data.string.StringUtils;

public class Main23 {


    public static void main(String[] args) {

        System.out.println("абв105.99".replaceAll("\\D+",""));
        System.out.println("абв105.99".replaceAll("[^0-9.]+",""));
        System.out.println("абв105.99".replaceAll("[^\\d.]",""));

        System.out.println(StringUtils.stringSame("од", "ок"));

    }


}
