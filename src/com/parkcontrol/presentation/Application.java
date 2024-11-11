package com.parkcontrol.presentation;

import com.parkcontrol.domain.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Application {
  public static List<User> adminsList = new ArrayList<>();
  public static List<User> usersList = new ArrayList<>();
  public static User currentUser;

  static {
    // Додавання адміна
    adminsList.add(new User(UUID.randomUUID(), "Admin", "Admin123", "Admin@gmail.com", "Admin"));
    // Додавання користувача
    usersList.add(new User(UUID.randomUUID(), "User", "User123", "User3224@gmail.com", "User"));
  }

  public static void runner() throws IllegalAccessException {
    Menu.show();
  }

  public static void main(String[] args) throws IllegalAccessException {
    runner();
  }
}
