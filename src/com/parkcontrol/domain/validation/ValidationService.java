package com.parkcontrol.domain.validation;

import com.parkcontrol.domain.model.Category;
import com.parkcontrol.domain.model.User;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ValidationService {

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

  // Валідація для перевірки правильності формату дати
  public static boolean isValidDateFormat(String date, String format) {
    if (date == null || format == null) return false;
    SimpleDateFormat sdf = new SimpleDateFormat(format);
    sdf.setLenient(false);  // Вимикає "м'яке" парсинг (наприклад, 31 квітня не стане 1 травня)
    try {
      sdf.parse(date); // Спроба розпарсити рядок як дату
      return true; // Якщо дата правильна, повертаємо true
    } catch (ParseException e) {
      return false; // Якщо виникає помилка парсингу, значить дата неправильна
    }
  }
  // Метод для перевірки, чи є логін унікальним
  public static boolean isLoginUnique(List<User> users, String login) {
    return users.stream().noneMatch(user -> user.getUsername().equalsIgnoreCase(login));
  }
  // Валідація для перевірки правильності формату категорії
  public static boolean isValidCategoryName(String name) {
    return isNotEmpty(name) && name.length() <= 255;
  }

  // Метод для перевірки, чи є категорія з такою назвою
  public static boolean isCategoryNameUnique(List<Category> categories, String name) {
    return categories.stream().noneMatch(category -> category.getName().equalsIgnoreCase(name));
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
