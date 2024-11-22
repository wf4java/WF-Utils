package main.main50;


import wf.utils.java.math.MathEval;


public class Main30{

    public static void main(String[] args) {

        System.out.println(MathEval.eval("cos(asin(0.5) + acos(0.5)) + 1",true));
        System.out.println(MathEval.eval("max(5, 4)",true));
        System.out.println(MathEval.eval("min(5, 4, 4, 2, 1, 4  ,   12  ,  123, 44)",true));
        System.out.println(MathEval.eval("pow(5, 4) - max(500, 400) - 25 * 4",true));


    }

}
