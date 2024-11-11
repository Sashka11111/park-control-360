package com.parkcontrol.service.operations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parkcontrol.domain.model.User;
import com.parkcontrol.domain.validation.UserInputHandler;
import com.parkcontrol.presentation.Application;
import com.parkcontrol.presentation.Menu;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class AuthorizationService {

  private static final String USERS_FILE_PATH = "Data/users.json";
  private static final UserInputHandler inputHandler = new UserInputHandler();

  // Метод для авторизації користувача
  public static void authorization() {
    String role = getRoleFromUser();
    String username = getUsernameFromUser();
    String password = getPasswordFromUser();

    List<User> users = readUsersFromJson(USERS_FILE_PATH);
    User user = findUserByUsernameAndRole(users, username, role);

    if (user != null && user.getPassword().equals(password)) {
      Application.currentUser = user;
      System.out.println("Авторизація пройшла успішно.");
      navigateToMenu();
    } else {
      handleAuthorizationFailure(user);
    }
  }

  // Метод для отримання ролі користувача
  private static String getRoleFromUser() {
    String role;
    while (true) {
      System.out.println("Оберіть роль:");
      System.out.println("1) Admin");
      System.out.println("2) User");

      int roleChoice = inputHandler.getIntInput("Ваш вибір: ");

      if (roleChoice == 1) {
        role = "Admin";
        break;
      } else if (roleChoice == 2) {
        role = "User";
        break;
      } else {
        System.out.println("Невірний вибір ролі. Спробуйте ще раз.");
      }
    }
    return role;
  }

  // Метод для отримання логіну користувача
  private static String getUsernameFromUser() {
    return inputHandler.getStringInput("Введіть логін:");
  }

  // Метод для отримання паролю користувача
  private static String getPasswordFromUser() {
    return inputHandler.getStringInput("Введіть пароль:");
  }


  // Метод для пошуку користувача за логіном та роллю
  private static User findUserByUsernameAndRole(List<User> users, String username, String role) {
    return users.stream()
        .filter(user -> user.getUsername().equals(username) && user.getRole().equals(role))
        .findFirst()
        .orElse(null);
  }

  // Метод для читання списку користувачів з JSON файлу
  private static List<User> readUsersFromJson(String filePath) {
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      return Arrays.asList(objectMapper.readValue(new File(filePath), User[].class));
    } catch (IOException e) {
      System.err.println("Помилка при читанні користувачів з файлу: " + e.getMessage());
      return List.of();
    }
  }

  // Метод для обробки помилок авторизації
  private static void handleAuthorizationFailure(User user) {
    if (user == null) {
      System.out.println("Це не ваша роль, спробуйте авторизуватися знову.");
    } else {
      System.out.println("Помилка авторизації. Перевірте логін та пароль.");
    }
  }

  // Метод для навігації до головного меню
  private static void navigateToMenu() {
    try {
      Menu.show();
    } catch (IllegalAccessException e) {
      System.err.println("Помилка при відкритті меню: " + e.getMessage());
      e.printStackTrace();
    }
  }
}
