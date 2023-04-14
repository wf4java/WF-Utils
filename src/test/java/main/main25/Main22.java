package main.main25;

import java.math.BigInteger;
import java.util.Arrays;

public class Main22 {


    public static void main(String[] args) {

        BigInteger b1 = new BigInteger("3");
        BigInteger b2 = new BigInteger("6");

        BigInteger v = (new BigInteger("6").pow(2007).
                subtract(new BigInteger("6").pow(2006)))
                .divide(new BigInteger("30"));

        b1 = b1.pow(2006);
        b2 = b2.pow(2005);


        System.out.println(b1.equals(v));
        System.out.println(b2.equals(v));

        System.out.println(v.toString());

        System.out.println(b1.toString());
        System.out.println(b2.toString());


        System.out.println(Arrays.toString("".split(":")));

    }


}
