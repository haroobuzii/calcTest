package com.back;

public class Calc {
    public static int run(String expression) {
        // 괄호 "밖"에 있는 연산자만 찾는다. 우선순위가 가장 낮은 덧셈·뺄셈부터 찾아서 나눈다
        // → 곱셈과 괄호는 나뉜 조각 안에 남아 먼저 계산된다
        int addSubIndex = findLastOperatorOutsideParens(expression, "+-");

        if (addSubIndex != -1) {
            return calcAddSub(expression, addSubIndex);
        }

        int multiplyIndex = findLastOperatorOutsideParens(expression, "*");

        if (multiplyIndex != -1) {
            return calcMultiply(expression, multiplyIndex);
        }

        // 괄호 밖에 연산자가 없고 식 전체가 괄호로 감싸져 있으면, 괄호를 벗기고 안쪽 식을 계산한다
        if (expression.startsWith("(") && expression.endsWith(")")) {
            return run(expression.substring(1, expression.length() - 1));
        }

        // 연산자가 하나도 없으면 숫자 하나뿐이다 (재귀 종료 조건)
        // "-10" 처럼 음수도 그대로 변환된다
        return Integer.parseInt(expression);
    }

    // 오른쪽에서 왼쪽으로 훑으며 괄호 깊이를 센다
    // 깊이가 0일 때(= 괄호 밖) 만나는 " 연산자 " 중 가장 오른쪽 것의 위치를 돌려준다. 없으면 -1
    // 반환값은 연산자 앞 공백의 위치다 (예: " + " 의 첫 글자)
    private static int findLastOperatorOutsideParens(String expression, String operators) {
        int depth = 0;

        for (int i = expression.length() - 1; i >= 0; i--) {
            char c = expression.charAt(i);

            if (c == ')') {
                depth++;
            } else if (c == '(') {
                depth--;
            } else if (depth == 0 && operators.indexOf(c) != -1 && isSurroundedBySpace(expression, i)) {
                return i - 1;
            }
        }

        return -1;
    }

    // 연산자 양옆이 공백인지 확인한다. "10 * -10" 의 '-' 처럼 음수 부호는 연산자가 아니다
    private static boolean isSurroundedBySpace(String expression, int index) {
        return index > 0
                && index < expression.length() - 1
                && expression.charAt(index - 1) == ' '
                && expression.charAt(index + 1) == ' ';
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
