package wf.utils.java.math;

import java.util.Arrays;

public class MathUtils {


    public static double[] getConfusion(double x, double y, double z){

        double max = Math.max(Math.abs(x),Math.max(Math.abs(y),Math.abs(z)));

        return new double[]{x / max, y / max, z / max, max};
    }

    public static double[] getConfusion(double x1, double y1, double z1, double x2, double y2, double z2){
        return getConfusion(x1 - x2,y1 - y2,z1 - z2);
    }

    public static double getDistance(double x1, double y1, double z1, double x2, double y2, double z2){
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2) + Math.pow(z2 - z1, 2));
    }

    public static double sin(double angle){
        return Math.sin(Math.toRadians(angle));
    }

    public static double cos(double angle){
        return Math.cos(Math.toRadians(angle));
    }

    public static long factorial(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("number is negative");
        }
        return n == 0 || n == 1 ? 1 : n * factorial(n - 1);
    }

    public static long combinations(int n, int k) {
        return factorial(n) / (factorial(k) * factorial(n - k));
    }

    public static long combinationsOptimized(int n, int k) {
        if (n < 0 || k < 0) {
            throw new IllegalArgumentException("n or k can't be negative");
        }
        if (n < k) {
            throw new IllegalArgumentException("n can't be smaller than k");
        }
        // nC0 is always 1
        long solution = 1;
        for (int i = 0; i < k; i++) {
            long next = (n - i) * solution / (i + 1);
            solution = next;
        }
        return solution;
    }

    public static int findMax(int... array) {
        int max = array[0];
        for (int i = 1; i < array.length; ++i) {
            if (array[i] > max) {
                max = array[i];
            }
        }
        return max;
    }

    public static double findMax(double... array) {
        double max = array[0];
        for (int i = 1; i < array.length; ++i) {
            if (array[i] > max) {
                max = array[i];
            }
        }
        return max;
    }

    public static int findMin(int... array) {
        int min = array[0];
        for (int i = 1; i < array.length; ++i) {
            if (array[i] < min) {
                min = array[i];
            }
        }
        return min;
    }

    public static double findMin(double... array) {
        double min = array[0];
        for (int i = 1; i < array.length; ++i) {
            if (array[i] < min) {
                min = array[i];
            }
        }
        return min;
    }



}
