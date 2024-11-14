package com.parkcontrol.service.operations;

import com.parkcontrol.domain.model.ParkingSpot;
import com.parkcontrol.domain.model.Category;
import com.parkcontrol.domain.model.User;
import com.parkcontrol.service.util.JsonDataReader;
import com.parkcontrol.service.util.FileUtil;
import com.parkcontrol.domain.validation.UserInputHandler;
import java.util.List;

public class DeleteService {

  private static final String CATEGORY_FILE_PATH = "data/categories.json";
  private static final String PARKING_SPOT_FILE_PATH = "data/parking_spots.json";
  private static final String USER_FILE_PATH = "data/users.json";

  public static void deleteParkingCategory() {
    List<Category> categories = JsonDataReader.modelDataJsonReader(CATEGORY_FILE_PATH, Category[].class);

    System.out.println("Список доступних категорій:");
    for (Category category : categories) {
      System.out.println("ID категорії: " + category.getId() + ", Назва: " + category.getName());
    }

    String categoryId = UserInputHandler.getStringInput("Введіть ID категорії, яку хочете видалити:");

    Category selectedCategory = categories.stream()
        .filter(category -> category.getId().toString().equals(categoryId))
        .findFirst()
        .orElse(null);

    if (selectedCategory != null) {
      categories.remove(selectedCategory);
      FileUtil.saveToFile(CATEGORY_FILE_PATH, categories);
      System.out.println("Категорію успішно видалено.");
    } else {
      System.out.println("Категорію з введеним ID не знайдено.");
    }
  }

  public static void deleteParkingSpot() {
    List<ParkingSpot> parkingSpots = JsonDataReader.modelDataJsonReader(PARKING_SPOT_FILE_PATH, ParkingSpot[].class);

    if (parkingSpots.isEmpty()) {
      System.out.println("Список паркувальних місць порожній.");
      return;
    }

    System.out.println("Список доступних паркувальних місць:");
    for (ParkingSpot spot : parkingSpots) {
      System.out.println("ID місця: " + spot.getSpotId() + ", Номер місця: " + spot.getSpotNumber());
    }

    String spotId = UserInputHandler.getStringInput("Введіть ID паркувального місця для видалення:");

    ParkingSpot selectedSpot = parkingSpots.stream()
        .filter(spot -> spot.getSpotId().toString().equals(spotId))
        .findFirst()
        .orElse(null);

    if (selectedSpot != null) {
      parkingSpots.remove(selectedSpot);
      FileUtil.saveToFile(PARKING_SPOT_FILE_PATH, parkingSpots);
      System.out.println("Паркувальне місце успішно видалено.");
    } else {
      System.out.println("Паркувальне місце з таким ID не знайдено.");
    }
  }


  public static void deleteUser() {
    List<User> users = JsonDataReader.modelDataJsonReader(USER_FILE_PATH, User[].class);

    if (users.isEmpty()) {
      System.out.println("Список користувачів порожній.");
      return;
    }

    System.out.println("Список користувачів:");
    for (User user : users) {
      System.out.println("ID користувача: " + user.getUserId() + ", Ім'я: " + user.getUsername());
    }

    String userId = UserInputHandler.getStringInput("Введіть ID користувача для видалення:");

    User selectedUser = users.stream()
        .filter(user -> user.getUserId().toString().equals(userId))
        .findFirst()
        .orElse(null);

    if (selectedUser != null) {
      users.remove(selectedUser);
      FileUtil.saveToFile(USER_FILE_PATH, users);
      System.out.println("Користувача успішно видалено.");
    } else {
      System.out.println("Користувача з таким ID не знайдено.");
    }
  }

}
