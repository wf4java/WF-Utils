package wf.utils.java.math;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

public class MathEval {


    private static final HashMap<String, ExpressFunction> FUNCTIONS = new HashMap<String, ExpressFunction>() {{
        put("sqrt", new ExpressFunction(1, (nums) -> Math.sqrt(nums.get(0))));
        put("sin", new ExpressFunction(1, (nums) -> Math.sin(Math.toRadians(nums.get(0)))));
        put("cos", new ExpressFunction(1, (nums) -> Math.cos(Math.toRadians(nums.get(0)))));
        put("tan", new ExpressFunction(1, (nums) -> Math.tan(Math.toRadians(nums.get(0)))));
        put("ceil", new ExpressFunction(1, (nums) -> Math.ceil(nums.get(0))));
        put("floor", new ExpressFunction(1, (nums) -> Math.floor(nums.get(0))));
        put("asin", new ExpressFunction(1, (nums) -> Math.toDegrees(Math.asin(nums.get(0)))));
        put("acos", new ExpressFunction(1, (nums) -> Math.toDegrees(Math.acos(nums.get(0)))));
        put("round", new ExpressFunction(1, (nums) -> (double) Math.round(nums.get(0))));


        put("min", new ExpressFunction(-1, (nums) -> MathUtils.findMin(nums.stream().mapToDouble(d -> d).toArray())));
        put("max", new ExpressFunction(-1, (nums) -> MathUtils.findMax(nums.stream().mapToDouble(d -> d).toArray())));
        put("pow", new ExpressFunction(2, (nums) -> Math.pow(nums.get(0), nums.get(1))));
    }};


    private static class ExpressFunction {
        private final int argsCount;
        private final Function<List<Double>, Double> function;
        public ExpressFunction(int argsCount, Function<List<Double>, Double> function) {
            this.argsCount = argsCount;
            this.function = function;
        }
        public int getArgsCount() { return argsCount; }
        public double execute(List<Double> args) { return function.apply(args); }
    }



    public static double eval(String expression){
        return eval(expression, true);
    }


    public static double eval(String expression, boolean floatFix) {
        final String str = expression.toLowerCase();
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < str.length()) ? str.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char) ch);
                return x;
            }


            // Grammar:
            // expression = term | expression `+` term | expression `-` term
            // term = factor | term `*` factor | term `/` factor
            // factor = `+` factor | `-` factor | `(` expression `)` | number
            //        | functionName `(` expression `)` | functionName factor
            //        | factor `^` factor

            double parseExpression() {
                double x = parseTerm();
                for (;;) {
                    if (eat('+')) x += parseTerm();
                    else if (eat('-')) x -= parseTerm();
                    else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (;;) {
                    if (eat('*')) x *= parseFactor();
                    else if (eat('/')) x /= parseFactor();
                    else return x;
                }
            }

            double parseFactor() {
                if (eat('+')) return +parseFactor();
                if (eat('-')) return -parseFactor();

                double x;
                int startPos = this.pos;

                if (eat('(')) {
                    x = parseExpression();
                    if (!eat(')')) throw new RuntimeException("Missing ')'");
                }

                else if ((ch >= '0' && ch <= '9') || ch == '.') {
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(str.substring(startPos, this.pos));
                }

                else if (ch >= 'a' && ch <= 'z') {
                    while (ch >= 'a' && ch <= 'z') nextChar();
                    String func = str.substring(startPos, this.pos);

                    ExpressFunction function = FUNCTIONS.get(func);
                    if (function == null)
                        throw new RuntimeException("Unknown function: " + func);


                    if (eat('(')) {
                        List<Double> args = new ArrayList<>(2);
                        args.add(parseExpression());
                        while (eat(','))
                            args.add(parseExpression());

                        if (!eat(')'))
                            throw new RuntimeException("Missing ')' after arguments to " + func);

                        if (function.getArgsCount() != -1 && function.getArgsCount() != args.size())
                            throw new RuntimeException("Invalid number of arguments for function " + func);


                        x = function.execute(args);
                    } else
                        throw new RuntimeException("Missing '(' after function " + func);

                } else
                    throw new RuntimeException("Unexpected: " + (char) ch);

                if (eat('^')) x = Math.pow(x, parseFactor());

                return floatFix ? MathUtils.floatFix(x) : x;
            }
        }.parse();
    }

}
