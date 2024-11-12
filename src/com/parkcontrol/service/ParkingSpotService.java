package com.parkcontrol.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.parkcontrol.domain.model.ParkingSpot;
import com.parkcontrol.domain.model.Category;
import com.parkcontrol.service.util.JsonDataReader;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import java.util.stream.Collectors;

public class ParkingSpotService {

  private static final String PARKING_SPOTS_FILE_PATH = "data/parking_spots.json";
  private static final String CATEGORIES_FILE_PATH = "data/categories.json"; // Файл з категоріями
  private static List<ParkingSpot> parkingSpots;
  private static List<Category> categories; // Зберігаємо список категорій

  static {
    // Ініціалізація списку паркувальних місць і категорій при запуску програми
    parkingSpots = JsonDataReader.modelDataJsonReader(PARKING_SPOTS_FILE_PATH, ParkingSpot[].class);
    categories = JsonDataReader.modelDataJsonReader(CATEGORIES_FILE_PATH, Category[].class);
  }

  public static void main(String[] args) {
    displayParkingSpots(parkingSpots);
  }

  // Метод для відображення паркувальних місць
  public static void displayParkingSpots(List<ParkingSpot> parkingSpots) {
    if (parkingSpots.isEmpty()) {
      System.out.println("Список паркувальних місць порожній.");
    } else {
      System.out.println("Список паркувальних місць:");
      for (ParkingSpot parkingSpot : parkingSpots) {
        System.out.println("Номер місця: " + parkingSpot.getSpotNumber());
        System.out.println("Ставка за годину: " + parkingSpot.getRatePerHour());
        System.out.println("Чи зайняте: " + (parkingSpot.isOccupied() ? "Так" : "Ні"));
        // Замість ID категорії виводимо назву
        Category category = findCategoryById(parkingSpot.getCategoryId());
        if (category != null) {
          System.out.println("Категорія: " + category.getName());
        } else {
          System.out.println("Категорія не знайдена");
        }
        System.out.println(); // Для розділення між записами
      }
    }
  }

  // Метод для перегляду вільних паркувальних місць
  public static void displayAvailableParkingSpots() {
    List<ParkingSpot> availableSpots = parkingSpots.stream()
        .filter(parkingSpot -> !parkingSpot.isOccupied()) // Фільтруємо по вільним місцям
        .collect(Collectors.toList());

    if (availableSpots.isEmpty()) {
      System.out.println("Немає вільних паркувальних місць.");
    } else {
      System.out.println("Вільні паркувальні місця:");
      for (ParkingSpot parkingSpot : availableSpots) {
        System.out.println("Номер місця: " + parkingSpot.getSpotNumber());
        System.out.println("Ставка за годину: " + parkingSpot.getRatePerHour());
        // Замість ID категорії виводимо назву
        Category category = findCategoryById(parkingSpot.getCategoryId());
        if (category != null) {
          System.out.println("Категорія: " + category.getName());
        } else {
          System.out.println("Категорія не знайдена");
        }
        System.out.println(); // Для розділення між записами
      }
    }
  }

  // Метод для додавання нового паркувального місця
  public static void addParkingSpot() {
    Scanner scanner = new Scanner(System.in);

    // Генерація нового унікального ID для паркувального місця
    UUID newParkingSpotId = UUID.randomUUID();

    // Запитати користувача про дані нового паркувального місця
    System.out.println("Додавання нового паркувального місця");

    int spotNumber;
    double ratePerHour;
    UUID categoryId;

    // Запит номеру місця
    do {
      System.out.print("Введіть номер паркувального місця: ");
      spotNumber = scanner.nextInt();

      if (spotNumber <= 0) {
        System.out.println("Номер місця повинен бути більшим за нуль.");
      } else {
        break;
      }
    } while (true);

    // Запит ставки за годину
    do {
      System.out.print("Введіть ставку за годину: ");
      ratePerHour = scanner.nextDouble();

      if (ratePerHour <= 0) {
        System.out.println("Ставка повинна бути більшою за нуль.");
      } else {
        break;
      }
    } while (true);

    // Запит ID категорії
    System.out.print("Введіть ID категорії паркувального місця: ");
    categoryId = UUID.fromString(scanner.next());

    // Генерація випадкового значення для статусу зайнятості місця
    boolean isOccupied = false;

    // Створення нового паркувального місця
    ParkingSpot newParkingSpot = new ParkingSpot(spotNumber, ratePerHour, categoryId);
    newParkingSpot.setSpotId(newParkingSpotId);
    newParkingSpot.setOccupied(isOccupied);

    // Додаємо нове паркувальне місце до списку
    parkingSpots.add(newParkingSpot);

    // Зберегти оновлені дані у файлі JSON
    saveParkingSpotsToJson();

    System.out.println("Нове паркувальне місце додано успішно.");
  }

  // Метод для пошуку категорії за ID
  public static Category findCategoryById(UUID categoryId) {
    for (Category category : categories) {
      if (category.getId().equals(categoryId)) {
        return category;
      }
    }
    return null;
  }

  // Метод для збереження паркувальних місць у файл JSON
  private static void saveParkingSpotsToJson() {
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
      objectMapper.writeValue(new File(PARKING_SPOTS_FILE_PATH), parkingSpots);
    } catch (IOException e) {
      System.out.println("Помилка при збереженні паркувальних місць у файл JSON: " + e.getMessage());
    }
  }
}
