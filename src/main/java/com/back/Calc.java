package com.back;

public class Calc {
    public static int run(String expression) {
        if (expression.contains(" - ")) {
            String[] expressionBits = expression.split(" - ");

            int num1 = Integer.parseInt(expressionBits[0]);
            int num2 = Integer.parseInt(expressionBits[1]);

            return num1 - num2;
        }

        int plusIndex = expression.indexOf(" + ");

        // 더 이상 연산자가 없으면 숫자 하나뿐이다 (재귀 종료 조건)
        if (plusIndex == -1) {
            return Integer.parseInt(expression);
        }

        // "첫 번째 수" + "나머지 식"으로 나누고, 나머지 식은 다시 run 으로 계산한다
        String left = expression.substring(0, plusIndex);
        String right = expression.substring(plusIndex + 3);

        return run(left) + run(right);
    }
}
