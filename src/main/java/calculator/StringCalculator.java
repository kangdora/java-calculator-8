package calculator;

import camp.nextstep.edu.missionutils.Console;

public class StringCalculator {
    private String getUserInput() {
        System.out.println("덧셈할 문자열을 입력해 주세요.");
        return Console.readLine();
    }

    private int[] StringSplit(String input) {
        String[] separators = {":", ","};
        String regex = String.join("|", separators);

        String[] tokens = input.split(regex);

        int[] result = new int[tokens.length];
        for (int i = 0; i < tokens.length; i++) {
            result[i] = Integer.parseInt(tokens[i]);
        }

        return result;
    }

    private int getSum(int[] numbers) {
        int sum = 0;
        for (int number : numbers) {
            sum += number;
        }
        return sum;
    }

    public void calculateString() {
        String userInput = getUserInput();
        int sum = getSum(StringSplit(userInput));

        System.out.println("결과 : " + sum);
    }
}
