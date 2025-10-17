package calculator;

import camp.nextstep.edu.missionutils.Console;

public class StringCalculator {
    private String getUserInput() {
        System.out.println("덧셈할 문자열을 입력해 주세요.");
        return Console.readLine();
    }

    private int[] StringSplit(String input) {
        String[] separators = {";", ","};
        String extraSeparator = addCustomSeparator(input);

        String regex = String.join("|", separators);

        if (extraSeparator != null) {
            regex += "|" + extraSeparator;

            int ed = input.indexOf("\\n");
            input = input.substring(ed + 2);
        }

        String[] chars = input.split(regex);

        int[] charCnt = new int[chars.length];
        int idx = 0;
        for (String ch : chars) {
            String t = ch.trim();
            if (t.isEmpty()) continue;
            charCnt[idx++] = Integer.parseInt(t);
        }

        // 실제 사용 길이만큼 배열 복사
        int[] result = new int[idx];
        for (int i = 0; i < idx; i++) {
            result[i] = charCnt[i];
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

    private String addCustomSeparator(String input) {
        String separator = null;
        int st, ed;
        st = input.indexOf("//");
        ed = input.indexOf("\\n");
        if (st != -1 && ed != -1) {
            separator = input.substring(st + 2, ed);
        }
        return separator;
    }

    public void calculateString() {
        String userInput = getUserInput();
        int sum = getSum(StringSplit(userInput));

        System.out.println("결과 : " + sum);
    }
}
