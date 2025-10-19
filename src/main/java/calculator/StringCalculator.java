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

        String regex = buildRegex(basicSeparators, "-".equals(extraSeparator) ? null : extraSeparator);

        if (extraSeparator != null) {
            if (!"-".equals(extraSeparator)) {
                regex += "|" + extraSeparator;
            }
            int endIndex = input.indexOf("\\n");
            input = input.substring(endIndex + 2);
        }

        if ("-".equals(extraSeparator)) {
            if (input.indexOf("---") >= 0) {
                throw new IllegalArgumentException();
            }
            input = input.replaceAll("--", ",-");
            if (hasOverlappingSeparator(input)) {
                throw new IllegalArgumentException();
            }
        } else {
            if (hasOverlappingSeparator(input)
                    || (extraSeparator != null && input.contains(extraSeparator + extraSeparator))) {
                throw new IllegalArgumentException();
            }
        }

        String[] tokens = input.split(regex, -1);
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

    private boolean hasOverlappingSeparator(String input) {
        return input.indexOf(",,") >= 0 || input.indexOf("::") >= 0 || input.indexOf(",:") >= 0 || input.indexOf(":,") >= 0;
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
        if (separator.length() == 0) {
            throw new IllegalArgumentException();
        }
        if (separator.matches("\\d+")) {
            throw new IllegalArgumentException();
        }
        if (separator.equals(":") || separator.equals(",")) {
            throw new IllegalArgumentException();
        }
        return separator;
    }

    private String escapeRegex(String s) {
        if (s == null || s.isEmpty()) {
            return s;
        }

        String specials = "\\.^$|?*+()[]{}";
        String result = "";

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (specials.indexOf(c) != -1) {
                result += "\\";
            }
            result += c;
        }

        return result;
    }

    private String buildRegex(String[] baseSeparators, String extraSeparator) {
        String regex = String.join("|", baseSeparators);

        if (extraSeparator != null) {
            if (extraSeparator.isEmpty()) {
                throw new IllegalArgumentException();
            }
            if (extraSeparator.endsWith("|")) {
                throw new IllegalArgumentException();
            }

            regex = regex + "|" + escapeRegex(extraSeparator);
        }

        if (regex.endsWith("|")) {
            throw new IllegalArgumentException();
        }

        return regex;
    }

    public void calculateString() {
        String userInput = getUserInput();
        int sum = getSum(splitString(userInput));
        System.out.println("결과 : " + sum);
    }
}
