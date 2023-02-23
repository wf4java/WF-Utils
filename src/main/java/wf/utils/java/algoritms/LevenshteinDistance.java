package wf.utils.java.algoritms;

public class LevenshteinDistance {

    public static int getDistance(String str1, String str2)
    {
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
            return (maxLength - getLevenshteinDistance(str1, str2)) / maxLength;
        }
        return 1.0;
    }

}
