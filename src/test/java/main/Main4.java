package main;


import java.io.File;
import java.net.URISyntaxException;
import java.util.Arrays;

public class Main4 {


    public static void main(String[] args) {

        try {

            File file = new File(Main4.class.getResource("/languages").toURI());

            System.out.println(Arrays.toString(file.list()));

        } catch (URISyntaxException e) {throw new RuntimeException(e);}

    }


}
