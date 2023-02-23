package wf.utils.java.math;

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


}
