package main;

import java.util.HashMap;

public class Main8 {


    public static void main(String[] args) {

        new HashMap<>();

        System.out.println(1 << 30);

        System.out.println(tableSizeFor(100));

    }

    static final int tableSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= 1 << 30) ? 1 << 30 : n + 1;
    }


}
