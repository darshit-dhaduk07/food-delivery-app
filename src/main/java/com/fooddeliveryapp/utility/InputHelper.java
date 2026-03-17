package com.fooddeliveryapp.utility;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class InputHelper {

    private static final Scanner scanner = new Scanner(System.in);

    public static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("⚠ Please enter a number.");
            }
        }
    }

    public static int readPositiveInt(String prompt) {
        int value = readInt(prompt);
        if (value <= 0)
            throw new IllegalArgumentException("ID must be a positive number, got: " + value);
        return value;
    }

    public static int readIdFromList(String prompt, List<Integer> validIds) {
        int value = readInt(prompt);
        if (!validIds.contains(value))
            throw new IllegalArgumentException("Invalid selection: " + value
                    + ". Please choose from the list.");
        return value;
    }

    public static String readString(String prompt) {
        System.out.print(prompt);
        String value = scanner.nextLine().trim();
        if (value.isEmpty())
            throw new IllegalArgumentException("Input cannot be empty.");
        return value;
    }

    public static BigDecimal readBigDecimal(String prompt) {
        System.out.print(prompt);
        try {
            BigDecimal value = new BigDecimal(scanner.nextLine().trim());
            if (value.compareTo(BigDecimal.ZERO) <= 0)
                throw new IllegalArgumentException("Value must be greater than 0.");
            return value;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid number format.");
        }
    }
}