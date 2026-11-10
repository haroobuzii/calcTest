package com.back;

public class Calc {
    public static int run(String expression) {
        // 가장 오른쪽에 있는 " + " 또는 " - " 의 위치를 찾는다
        int operatorIndex = Math.max(expression.lastIndexOf(" + "), expression.lastIndexOf(" - "));

        // 더 이상 연산자가 없으면 숫자 하나뿐이다 (재귀 종료 조건)
        if (operatorIndex == -1) {
            return Integer.parseInt(expression);
        }

        // 가장 오른쪽 연산자를 기준으로 "앞쪽 식"과 "마지막 수"로 나눈다
        // 예) "10 - 20 + 30" → "10 - 20" 과 "30"
        // 앞쪽 식을 먼저 계산하므로 왼쪽부터 차례대로 계산한 것과 같다
        String left = expression.substring(0, operatorIndex);
        char operator = expression.charAt(operatorIndex + 1);
        String right = expression.substring(operatorIndex + 3);

        if (operator == '+') {
            return run(left) + run(right);
        }

        return run(left) - run(right);
    }
}
