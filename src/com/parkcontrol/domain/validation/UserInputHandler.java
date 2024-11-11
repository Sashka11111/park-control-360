package com.parkcontrol.domain.validation;

import java.util.Scanner;

public class UserInputHandler {
  private static Scanner scanner = new Scanner(System.in);

  // Введення рядка
  public static String getStringInput(String prompt) {
    System.out.println(prompt);
    return scanner.nextLine();
  }

  // Введення числа (int)
  public static int getIntInput(String prompt) {
    int input = -1;
    boolean valid = false;
    while (!valid) {
      try {
        System.out.println(prompt);
        input = Integer.parseInt(scanner.nextLine());
        valid = true;
      } catch (NumberFormatException e) {
        System.out.println("Введено некоректне число. Спробуйте ще раз.");
      }
    }
    return input;
  }

  // Введення числа (double)
  public static double getDoubleInput(String prompt) {
    double input = -1.0;
    boolean valid = false;
    while (!valid) {
      try {
        System.out.println(prompt);
        input = Double.parseDouble(scanner.nextLine());
        valid = true;
      } catch (NumberFormatException e) {
        System.out.println("Введено некоректне число. Спробуйте ще раз.");
      }
    }
    return input;
  }
  // Введення рядка з перевіркою на порожність
  public static String getValidStringInput(Scanner scanner) {
    String input;
    while (true) {
      input = scanner.nextLine().trim();
      if (input.isEmpty()) {
        System.out.println("Введення не може бути порожнім. Спробуйте ще раз.");
      } else {
        break;
      }
    }
    return input;
  }

  // Введення підтвердження (Yes/No)
  public static boolean getYesNoInput(String prompt) {
    String input;
    while (true) {
      System.out.println(prompt);
      input = scanner.nextLine().trim().toLowerCase();
      if (input.equals("yes") || input.equals("y")) {
        return true;
      } else if (input.equals("no") || input.equals("n")) {
        return false;
      } else {
        System.out.println("Введено некоректну відповідь. Введіть 'yes' або 'no'.");
      }
    }
  }

  // Закриття сканера (коли програма завершується)
  public static void closeScanner() {
    scanner.close();
  }
}
