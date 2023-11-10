package wf.utils.java.algoritms.random;

import java.util.Random;

public class RandomStringGenerator {

    private static final String DIGITS = "0123456789";
    private static final String LOWER_LETTERS = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPER_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String ALL = DIGITS + LOWER_LETTERS + UPPER_LETTERS;

    private static final Random random = new Random();


    public static String generate(String symbols, int length){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++)
            stringBuilder.append(symbols.charAt(random.nextInt(symbols.length())));
        return stringBuilder.toString();
    }

    public static String generate(int length){
        return generate(ALL, length);
    }

    public static String generateFromDigits(int length){
        return generate(DIGITS, length);
    }

    public static String generateFromLowerLetters(int length){
        return generate(LOWER_LETTERS, length);
    }

    public static String generateFromUpperLetters(int length){
        return generate(UPPER_LETTERS, length);
    }



}
