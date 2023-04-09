package wf.utils.java.math.smooth;


import wf.utils.java.math.smooth.fucntions.EaseIn;
import wf.utils.java.math.smooth.fucntions.EaseOut;

public class SmoothTransformFunctionType {

    public static final SmoothTransform.SmoothTransformFunction LINEAR = new SmoothTransform.SmoothTransformFunction() {
        @Override
        public double get(double percent) {
            return percent;
        }
    };

    public static final SmoothTransform.SmoothTransformFunction SIN = new SmoothTransform.SmoothTransformFunction() {
        @Override
        public double get(double percent) {
            return Math.sin(Math.toRadians(percent * 90d));
        }
    };

    public static final SmoothTransform.SmoothTransformFunction COS = new SmoothTransform.SmoothTransformFunction() {
        @Override
        public double get(double percent) {
            return (1 - Math.cos(Math.toRadians(percent * 90d)));
        }
    };

    public static final SmoothTransform.SmoothTransformFunction TAN = new SmoothTransform.SmoothTransformFunction() {
        @Override
        public double get(double percent) {
            return Math.tan(Math.toRadians(percent * 45d));
        }
    };


    public static final SmoothTransform.SmoothTransformFunction SQRT = new SmoothTransform.SmoothTransformFunction() {
        @Override
        public double get(double percent) {
            return Math.sqrt(percent);
        }
    };

    public static final SmoothTransform.SmoothTransformFunction QUADRATIC = new SmoothTransform.SmoothTransformFunction() {
        @Override
        public double get(double percent) {
            return (percent * percent);
        }
        
    };

    public static final SmoothTransform.SmoothTransformFunction EASE_IN = new EaseIn();

    public static final SmoothTransform.SmoothTransformFunction EASE_OUT = new EaseOut();

    public static final SmoothTransform.SmoothTransformFunction EASE_BOTH = new SmoothTransform.SmoothTransformFunction() {
        @Override
        public double get(double percent) {
            return clamp((percent < 0.2) ? 3.125 * percent * percent
                    : (percent > 0.8) ? -3.125 * percent * percent + 6.25 * percent - 2.125
                    : 1.25 * percent - 0.125);
        }
    };


    public static final SmoothTransform.SmoothTransformFunction DISCRETE = new SmoothTransform.SmoothTransformFunction() {
        @Override
        public double get(double percent) {
            return percent < 0.5 ? 0 : 1;
        }
    };


    public static final SmoothTransform.SmoothTransformFunction EXPONITIAL = new SmoothTransform.SmoothTransformFunction() {
        @Override
        public double get(double percent) {
            return percent < 0.5 ? 0 : 1;
        }
    };




    public static double clamp(double t) {
        return (t < 0.0) ? 0.0 : Math.min(t, 1.0);
    }

}
