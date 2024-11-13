package com.parkcontrol.domain.validation;

import com.parkcontrol.domain.model.User;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

public class UserValidationService {

  // Валідація для перевірки, чи не є рядок порожнім
  public static boolean isNotEmpty(String value) {
    return value != null && !value.trim().isEmpty();
  }

  // Валідація для перевірки, чи є рядок валідною електронною поштою
  public static boolean isValidEmail(String email) {
    if (email == null) return false;
    String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    return Pattern.matches(emailRegex, email);
  }
  // Метод для перевірки, чи є логін унікальним
  public static boolean isLoginUnique(List<User> users, String login) {
    return users.stream().noneMatch(user -> user.getUsername().equalsIgnoreCase(login));
  }
  // Валідація для перевірки правильності формату категорії
  public static boolean isValidCategoryName(String name) {
    return isNotEmpty(name) && name.length() <= 255;
  }

  // Валідація для перевірки правильності введення імені (не порожній рядок та містить хоча б одну букву)
  public static boolean isValidName(String name) {
    return isNotEmpty(name) && name.matches(".*[a-zA-Zа-яА-Я].*");
  }

  // Валідація для перевірки, чи є рядок числом
  public static boolean isValidNumber(String value) {
    if (value == null) return false;
    try {
      Double.parseDouble(value);
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }

  // Валідація для перевірки формату пароля
  public static boolean isValidPassword(String password) {
    if (password == null) return false;
    // Регулярний вираз без спеціальних символів
    String passwordRegex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).{8,}$";
    return Pattern.matches(passwordRegex, password);
  }

  // Валідація для перевірки, чи є рядок коректним UUID
  public static boolean isValidUUID(String uuid) {
    if (uuid == null) return false;
    try {
      UUID.fromString(uuid);
      return true;
    } catch (IllegalArgumentException e) {
      return false;
    }
  }
}
