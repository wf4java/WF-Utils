package wf.utils.java.math.smooth.fucntions;

import wf.utils.java.math.smooth.SmoothTransform;
import static wf.utils.java.math.smooth.SmoothTransformFunctionType.clamp;

public class EaseIn implements SmoothTransform.SmoothTransformFunction {

    private double S1 = 25.0 / 9.0;
    private double S3 = 10.0 / 9.0;
    private double S4 = 1.0 / 9.0;

    public EaseIn() {
    }

    public EaseIn(double s1, double s3, double s4) {
        S1 = s1;
        S3 = s3;
        S4 = s4;
    }

    @Override
    public double get(double percent) {
        return clamp((percent < 0.2) ? S1 * percent * percent : S3 * percent - S4);
    }

    public double getS1() {
        return S1;
    }

    public void setS1(double s1) {
        S1 = s1;
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
        return "EaseIn{" +
                "S1=" + S1 +
                ", S3=" + S3 +
                ", S4=" + S4 +
                '}';
    }
}
