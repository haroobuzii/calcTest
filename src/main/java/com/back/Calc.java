package com.back;

public class Calc {
    public static int run(String expression) {
        if (expression.contains(" - ")) {
            String[] expressionBits = expression.split(" - ");

            int num1 = Integer.parseInt(expressionBits[0]);
            int num2 = Integer.parseInt(expressionBits[1]);

            return num1 - num2;
        }

        String[] expressionBits = expression.split(" \\+ ");

        int num1 = Integer.parseInt(expressionBits[0]);
        int num2 = Integer.parseInt(expressionBits[1]);

        return num1 + num2;
    }
}
