package com.ll;

public class Calc {
    public static int run(String expression) {
        String[] tokens = expression.split(" ");

        int result = switch (tokens[1]) {
            case "+" -> Integer.parseInt(tokens[0]) + Integer.parseInt(tokens[2]);
            case "-" -> Integer.parseInt(tokens[0]) - Integer.parseInt(tokens[2]);

            default -> 0;
        };
        return result;
    }
}
