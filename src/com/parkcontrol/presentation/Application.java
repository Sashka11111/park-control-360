package com.parkcontrol.presentation;

import com.parkcontrol.domain.model.User;

import com.parkcontrol.service.operations.AuthorizationService;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Application {
  public static List<User> adminsList = new ArrayList<>();
  public static List<User> usersList = new ArrayList<>();
  public static User currentUser;

  static {
    // Додавання адміна
    adminsList.add(new User(UUID.randomUUID(), "Admin", "Admin123456", "Admin@gmail.com", "Admin"));
    // Додавання користувача
    usersList.add(new User(UUID.randomUUID(), "User", "User123789", "User3224@gmail.com", "User"));
  }

  public static void runner() throws IllegalAccessException {
    Menu.show();
    // Авторизація користувача
    AuthorizationService.authorization();

    if (currentUser != null) {
      System.out.println("Поточний користувач: " + currentUser.getUsername());
    }
  }

  public static void main(String[] args) throws IllegalAccessException {
    runner();
  }
}
