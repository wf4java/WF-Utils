package main.main50;






public class Main27 {


    public static void main(String[] args) {

        System.out.println(compareVersions("1.1","2.1"));
        System.out.println(compareVersions("2.2.2.2","2.2.2.3"));
        System.out.println(compareVersions("2.2.8","2.3.1"));

    }

    public static int compareVersions(String v1, String v2) {
        String[] v1Parts = v1.split("\\.");
        String[] v2Parts = v2.split("\\.");

        for (int i = 0; i < Math.max(v1Parts.length, v2Parts.length); i++) {
            int v1Part = i < v1Parts.length ? Integer.parseInt(v1Parts[i]) : 0;
            int v2Part = i < v2Parts.length ? Integer.parseInt(v2Parts[i]) : 0;

            if (v1Part < v2Part) {
                return -1;
            } else if (v1Part > v2Part) {
                return 1;
            }
        }
        return 0;
    }

}
