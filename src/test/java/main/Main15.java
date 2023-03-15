package main;



import wf.utils.java.console.ConsoleColor;
import wf.utils.java.math.MathEval;

import java.math.BigInteger;


public class Main15 {


    public static void main(String[] args) {

        //System.out.println(MathEval.eval("2^256"));

        BigInteger b = new BigInteger("2");

        b = b.pow(100000000);

        System.out.println(b.toString().length());

    }


}
