package com.parkcontrol.domain.validation;

import java.util.Scanner;

public class UserInputHandler {
  private static Scanner scanner = new Scanner(System.in);

  // Введення рядка
  public static String getStringInput(String prompt) {
    System.out.println(prompt);
    return scanner.nextLine();
  }
  public static int getIntInput(String prompt) {
    int input = -1;
    boolean valid = false;
    while (!valid) {
      try {
        System.out.println(prompt);
        input = Integer.parseInt(scanner.nextLine()); // Читаємо введене число як рядок
        valid = true;
      } catch (NumberFormatException e) {
        System.out.println("Введено некоректне число. Спробуйте ще раз.");
      }
    }
    return input;
  }
}
