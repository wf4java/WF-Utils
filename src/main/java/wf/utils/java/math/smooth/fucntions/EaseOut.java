package wf.utils.java.math.smooth.fucntions;

import wf.utils.java.math.smooth.SmoothTransform;

import static wf.utils.java.math.smooth.SmoothTransformFunctionType.clamp;

public class EaseOut implements SmoothTransform.SmoothTransformFunction {

    private double S1 = -25.0 / 9.0;
    private double S2 = 50.0 / 9.0;
    private double S3 = -16.0 / 9.0;
    private double S4 = 10.0 / 9.0;

    public EaseOut() {
    }

    public EaseOut(double s1, double s2, double s3, double s4) {
        S1 = s1;
        S2 = s2;
        S3 = s3;
        S4 = s4;
    }

    @Override
    public double get(double percent) {
        return clamp((percent > 0.8) ? S1 * percent * percent + S2 * percent + S3 : S4 * percent);
    }

    public double getS1() {
        return S1;
    }

    public void setS1(double s1) {
        S1 = s1;
    }

    public double getS2() {
        return S2;
    }

    public void setS2(double s2) {
        S2 = s2;
    }

    public double getS3() {
        return S3;
    }

    public void setS3(double s3) {
        S3 = s3;
    }

    public double getS4() {
        return S4;
    }

    public void setS4(double s4) {
        S4 = s4;
    }

    @Override
    public String toString() {
        return "EaseOut{" +
                "S1=" + S1 +
                ", S2=" + S2 +
                ", S3=" + S3 +
                ", S4=" + S4 +
                '}';
    }
}
