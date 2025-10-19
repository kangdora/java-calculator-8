package calculator;

import camp.nextstep.edu.missionutils.Console;

public class StringCalculator {

    private String getUserInput() {
        System.out.println("덧셈할 문자열을 입력해 주세요.");
        String input = Console.readLine();
        if (input == null) {
            throw new IllegalArgumentException();
        }
        return input;
    }

    private int[] splitString(String input) {
        String[] basicSeparators = {":", ","};
        String extraSeparator = addCustomSeparator(input);

        String regex = String.join("|", basicSeparators);

        if (extraSeparator != null) {
            if (!"-".equals(extraSeparator)) {
                regex += "|" + extraSeparator;
            }
            int endIndex = input.indexOf("\\n");
            input = input.substring(endIndex + 2);
        }

        if ("-".equals(extraSeparator)) {
            if (input.contains("---")) {
                throw new IllegalArgumentException();
            }
            input = input.replaceAll("--", ",-");
            if (input.contains(",,") || input.contains("::") || input.contains(",:") || input.contains(":,")) {
                throw new IllegalArgumentException();
            }
        } else {
            if (input.contains(",,") || input.contains("::") || input.contains(",:") || input.contains(":,")
                    || (extraSeparator != null && input.contains(extraSeparator + extraSeparator))) {
                throw new IllegalArgumentException();
            }
        }

        String[] tokens = input.split(regex);
        int[] charCount = new int[tokens.length];
        int index = 0;
        for (String token : tokens) {
            String t = token.trim();
            if (t.isEmpty() || !t.matches("-?\\d+")) {
                throw new IllegalArgumentException();
            }
            charCount[index++] = Integer.parseInt(t);
        }

        int[] result = new int[index];
        System.arraycopy(charCount, 0, result, 0, index);
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
        int startIndex = input.indexOf("//");
        int endIndex = input.indexOf("\\n");
        if ((startIndex != -1 && endIndex == -1) || (startIndex == -1 && endIndex != -1)) {
            throw new IllegalArgumentException();
        }
        if (startIndex != -1) {
            separator = input.substring(startIndex + 2, endIndex);
        }
        if (separator == null) {
            return null;
        }
        if (separator.matches("\\d+")) {
            throw new IllegalArgumentException();
        }
        if (separator.equals(":") || separator.equals(",")) {
            throw new IllegalArgumentException();
        }
        return separator;
    }

    public void calculateString() {
        String userInput = getUserInput();
        int sum = getSum(splitString(userInput));
        System.out.println("결과 : " + sum);
    }
}
