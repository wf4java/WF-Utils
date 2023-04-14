package main.main50;

public class Main26 {


    public static void main(String[] args) {

        System.out.println(Math.sqrt(Double.MAX_VALUE - 1000));
        System.out.println();
        System.out.println(sqrt(Double.MAX_VALUE - 1000));


    }



    public static double sqrt(double n){

        int size = 64;

        String bits = multipleString("0", size - 1);

        for (int i = 0; i < size - 1; i++) {
            Double number = parseDouble(setCharByIndex(bits, i,"1"),2);
            if(number < 1.3407807929942596E154 && number * number <= n) bits = setCharByIndex(bits, i, "1");
        }

        return parseDouble(bits,2);
    }

    public static double sqrt2(double a) {
        double x = a;
        double y = 1;
        double e = 0.00000001;
        while (x - y > e) {
            x = (x + y) / 2;
            y = a / x;
        }
        return x;
    }

    public static String setCharByIndex(String s, int index, String to){
        return (s.substring(0, index) + to + s.substring(index + 1));
    }

    public static double parseDouble(String s, int radix){
        return Double.longBitsToDouble(Long.parseLong(s, radix));
    }

    public static String multipleString(String s, int count){
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < count; i++) builder.append(s);
        return builder.toString();
    }

}
