package wf.utils.java.data_utils;

import java.util.HashMap;
import java.util.Map;

public class StringUtils {

    public static boolean isAllCharactersSame(String s) {
        for (int i = 1, length = s.length(); i < length; ++i) {
            if (s.charAt(i) != s.charAt(0)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isAnagrams(String s1, String s2) {
        int l1 = s1.length();
        int l2 = s2.length();
        s1 = s1.toLowerCase();
        s2 = s2.toLowerCase();
        Map<Character, Integer> charAppearances = new HashMap<>();

        for (int i = 0; i < l1; i++) {
            char c = s1.charAt(i);
            int numOfAppearances = charAppearances.getOrDefault(c, 0);
            charAppearances.put(c, numOfAppearances + 1);
        }

        for (int i = 0; i < l2; i++) {
            char c = s2.charAt(i);
            if (!charAppearances.containsKey(c)) {
                return false;
            }
            charAppearances.put(c, charAppearances.get(c) - 1);
        }

        for (int cnt : charAppearances.values()) {
            if (cnt != 0) {
                return false;
            }
        }
        return true;
    }

    public static int calculateHammingDistance(String s1, String s2)
            throws Exception {
        if (s1.length() != s2.length()) {
            throw new Exception("String lengths must be equal");
        }

        int stringLength = s1.length();
        int counter = 0;

        for (int i = 0; i < stringLength; i++) {
            if (s1.charAt(i) != s2.charAt(i)) {
                counter++;
            }
        }
        return counter;
    }

    public static int stringSame(String str1, String str2) {
        int m = str1.length();
        int n = str2.length();

        int[][] T = new int[m + 1][n + 1];
        for (int i = 1; i <= m; i++) {
            T[i][0] = i;
        }
        for (int j = 1; j <= n; j++) {
            T[0][j] = j;
        }

        int cost;
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                cost = str1.charAt(i - 1) == str2.charAt(j - 1) ? 0: 1;
                T[i][j] = Integer.min(Integer.min(T[i - 1][j] + 1, T[i][j - 1] + 1),
                        T[i - 1][j - 1] + cost);
            }
        }

        return T[m][n];
    }

    public static double getSimilarity(String str1, String str2) {
        if (str1 == null || str2 == null) {
            throw new IllegalArgumentException("Strings must not be null");
        }
        double maxLength = Double.max(str1.length(), str2.length());
        if (maxLength > 0) {
            return (maxLength - stringSame(str1, str2)) / maxLength;
        }
        return 1.0;
    }

    public static String reverse(String str) {
        return new StringBuilder(str).reverse().toString();
    }

    public static boolean isValidParentheses(String s) {
        char[] stack = new char[s.length()];
        int head = 0;
        for(char c : s.toCharArray()) {
            switch(c) {
                case '{':
                case '[':
                case '(':
                    stack[head++] = c;
                    break;
                case '}':
                    if(head == 0 || stack[--head] != '{') return false;
                    break;
                case ')':
                    if(head == 0 || stack[--head] != '(') return false;
                    break;
                case ']':
                    if(head == 0 || stack[--head] != '[') return false;
                    break;
            }
        }
        return head == 0;
    }

}
