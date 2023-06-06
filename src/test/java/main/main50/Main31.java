package main.main50;

import org.bukkit.entity.Player;

public class Main31 {


    public static void main(String[] args) {

        System.out.println(tableSizeFor(3));
        print((Object) null);

    }

    public static final int tableSizeFor(int cap) {
        int n = -1 >>> Integer.numberOfLeadingZeros(cap - 1);
        return (n < 0) ? 1 : (n >= (1 << 30)) ? (1 << 30) : n + 1;
    }

    public static void print(Player s){
        System.out.println("Str: " + s);
    }

    public static void print(String s){
        System.out.println("Str: " + s);
    }

    public static void print(Object i){
        System.out.println("Obj: " + i);
    }

}
