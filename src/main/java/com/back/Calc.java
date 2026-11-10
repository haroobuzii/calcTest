package com.back;

public class Calc {
    private static final String PLUS = " + ";
    private static final String MINUS = " - ";
    private static final String MULTIPLY = " * ";

    public static int run(String expression) {
        // 식 전체가 괄호로 감싸져 있으면 괄호를 벗기고 안쪽 식을 계산한다
        if (expression.startsWith("(") && expression.endsWith(")")) {
            return run(expression.substring(1, expression.length() - 1));
        }

        // 우선순위가 가장 낮은 덧셈·뺄셈부터 찾아서 나눈다
        // → 곱셈은 나뉜 조각 안에 남아 먼저 계산된다
        int addSubIndex = Math.max(expression.lastIndexOf(PLUS), expression.lastIndexOf(MINUS));

        if (addSubIndex != -1) {
            return calcAddSub(expression, addSubIndex);
        }

        int multiplyIndex = expression.lastIndexOf(MULTIPLY);

        if (multiplyIndex != -1) {
            return calcMultiply(expression, multiplyIndex);
        }

        // 연산자가 하나도 없으면 숫자 하나뿐이다 (재귀 종료 조건)
        // "-10" 처럼 음수도 그대로 변환된다
        return Integer.parseInt(expression);
    }

    // 가장 오른쪽 덧셈/뺄셈을 기준으로 "앞쪽 식"과 "마지막 항"으로 나눈다
    // 예) "10 - 20 + 30" → run("10 - 20") + run("30")
    // 앞쪽 식이 먼저 계산되므로 왼쪽부터 차례대로 계산한 것과 같다
    private static int calcAddSub(String expression, int index) {
        int left = run(expression.substring(0, index));
        int right = run(expression.substring(index + 3));

        if (expression.charAt(index + 1) == '+') {
            return left + right;
        }

        return left - right;
    }

    // 예) "10 * 10 * 10" → run("10 * 10") * run("10")
    private static int calcMultiply(String expression, int index) {
        int left = run(expression.substring(0, index));
        int right = run(expression.substring(index + 3));

        return left * right;
    }
}
