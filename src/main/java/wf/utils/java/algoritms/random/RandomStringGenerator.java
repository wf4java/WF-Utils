package wf.utils.java.algoritms.random;

import java.util.Random;

public class RandomStringGenerator {

    private static final String symbols = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "abcdefghijklmnopqrstuvwxyz" + "0123456789";
    private static final Random random = new Random();

    public static String getRandomString(int length){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++)
            stringBuilder.append(symbols.charAt(random.nextInt(symbols.length())));
        return stringBuilder.toString();
    }

    public static String getRandomString(String symbols, int length){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++)
            stringBuilder.append(symbols.charAt(random.nextInt(symbols.length())));
        return stringBuilder.toString();
    }



}
