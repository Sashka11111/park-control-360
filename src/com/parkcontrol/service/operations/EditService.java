package com.parkcontrol.service.operations;

import com.parkcontrol.domain.model.Category;
import com.parkcontrol.domain.model.ParkingTicket;
import com.parkcontrol.domain.model.ParkingSpot;
import com.parkcontrol.service.util.JsonDataReader;
import com.parkcontrol.service.util.FileUtil;
import com.parkcontrol.domain.validation.UserValidationService;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class EditService {

  private static final String PARKING_SPOT_FILE_PATH = "data/parking_spots.json";
  private static final String CATEGORIES_FILE_PATH = "data/categories.json";

  // Метод редагування паркувального місця
  public static void editParkingSpot() {
    List<ParkingSpot> spots = JsonDataReader.modelDataJsonReader(PARKING_SPOT_FILE_PATH, ParkingSpot[].class);

    if (spots.isEmpty()) {
      System.out.println("Список паркувальних місць порожній.");
      return;
    }

    Scanner scanner = new Scanner(System.in);
    ParkingSpot spot = null;

    // Поки користувач не введе правильний ID
    while (spot == null) {
      System.out.print("Введіть ID паркувального місця для редагування: ");
      String inputId = scanner.nextLine();

      if (!UserValidationService.isValidUUID(inputId)) {
        System.out.println("Некоректний формат ID. Спробуйте знову.");
        continue;  // Запитуємо ID знову
      }

      UUID spotId = UUID.fromString(inputId);
      spot = spots.stream()
          .filter(s -> s.getSpotId().equals(spotId))
          .findFirst()
          .orElse(null);

      if (spot == null) {
        System.out.println("Паркувальне місце з таким ID не знайдено. Спробуйте ще раз.");
      }
    }

    // Редагуємо номер місця
    String newSpotNumber;
    while (true) {
      System.out.print("Введіть новий номер місця: ");
      newSpotNumber = scanner.nextLine();

      if (UserValidationService.isValidNumber(newSpotNumber)) {
        spot.setSpotNumber(Integer.parseInt(newSpotNumber));
        break;  // Виходимо з циклу, якщо ввід валідний
      } else {
        System.out.println("Некоректний номер місця. Спробуйте знову.");
      }
    }

    // Редагуємо ставку за годину
    String newRate;
    while (true) {
      System.out.print("Введіть нову ставку за годину: ");
      newRate = scanner.nextLine();

      if (UserValidationService.isValidNumber(newRate)) {
        spot.setRatePerHour(Double.parseDouble(newRate));
        break;  // Виходимо з циклу, якщо ввід валідний
      } else {
        System.out.println("Некоректна ставка. Спробуйте знову.");
      }
    }

    FileUtil.saveToFile(PARKING_SPOT_FILE_PATH, spots);
    System.out.println("Паркувальне місце успішно оновлено.");
  }

  // Метод редагування категорії паркування
  public static void editParkingCategory() {
    List<Category> categories = JsonDataReader.modelDataJsonReader(CATEGORIES_FILE_PATH, Category[].class);

    if (categories.isEmpty()) {
      System.out.println("Список категорій порожній.");
      return;
    }

    Scanner scanner = new Scanner(System.in);
    Category category = null;

    // Поки користувач не введе правильний ID
    while (category == null) {
      System.out.print("Введіть ID категорії для редагування: ");
      String inputId = scanner.nextLine();

      if (!UserValidationService.isValidUUID(inputId)) {
        System.out.println("Некоректний формат ID. Спробуйте знову.");
        continue;  // Запитуємо ID знову
      }

      UUID categoryId = UUID.fromString(inputId);
      category = categories.stream()
          .filter(c -> c.getId().equals(categoryId))
          .findFirst()
          .orElse(null);

      if (category == null) {
        System.out.println("Категорія з таким ID не знайдена. Спробуйте ще раз.");
      }
    }

    // Редагуємо назву категорії
    String newName;
    while (true) {
      System.out.print("Введіть нову назву категорії: ");
      newName = scanner.nextLine();

      if (UserValidationService.isValidCategoryName(newName)) {
        category.setName(newName);
        break;  // Виходимо з циклу, якщо ввід валідний
      } else {
        System.out.println("Некоректна назва категорії. Спробуйте знову.");
      }
    }

    FileUtil.saveToFile(CATEGORIES_FILE_PATH, categories);
    System.out.println("Категорію успішно оновлено.");
  }
}
