package com.drbaltar.springplayground.textprocessing;

import java.util.List;

public class MathService {
    public static String addAll(List<String> qString) {
        StringBuilder output = new StringBuilder();
        int total = 0;
        for (int i = 0; i < qString.size(); i++) {
            total += Integer.parseInt(qString.get(i));
            output.append(qString.get(i));
            if (i == qString.size() - 1) {
                output.append(" = ").append(total);
            } else {
                output.append(" + ");
            }
        }
        return output.toString();
    }

    public static String getCalculate(String operation, int x, int y) {
        return switch (operation) {
            case "subtract" -> String.format("%s - %s = %s", x, y, x - y);
            case "multiply" -> String.format("%s * %s = %s", x, y, x * y);
            case "divide" -> String.format("%s / %s = %s", x, y, x / y);
            default -> String.format("%s + %s = %s", x, y, x + y);
        };
    }

}
