package util;

import java.util.Scanner;

public class InputValidator {

    private Scanner scanner;

    public InputValidator(Scanner scanner) {
        this.scanner = scanner;
    }

    public int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                String line = scanner.nextLine().trim();
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("  Invalid input. Please enter a whole number.");
            }
        }
    }

    public int readIntInRange(String prompt, int min, int max) {
        while (true) {
            int val = readInt(prompt);
            if (val >= min && val <= max)
                return val;
            System.out.printf("  Please enter a number between %d and %d.%n", min, max);
        }
    }

    public double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                String line = scanner.nextLine().trim();
                double val = Double.parseDouble(line);
                if (val < 0)
                    throw new NumberFormatException();
                return val;
            } catch (NumberFormatException e) {
                System.out.println("  Invalid input. Please enter a valid positive number.");
            }
        }
    }

    public String readString(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            if (!line.isEmpty())
                return line;
            System.out.println("  Input cannot be empty.");
        }
    }

    public String readOptional(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    public boolean readYesNo(String prompt) {
        while (true) {
            System.out.print(prompt + " (y/n): ");
            String line = scanner.nextLine().trim().toLowerCase();
            if (line.equals("y") || line.equals("yes"))
                return true;
            if (line.equals("n") || line.equals("no"))
                return false;
            System.out.println("  Please enter y or n.");
        }
    }

    public boolean isValidEmail(String email) {
        return email.contains("@") && email.contains(".");
    }

    public boolean isValidPhone(String phone) {
        return phone.matches("\\d{10}");
    }
}
