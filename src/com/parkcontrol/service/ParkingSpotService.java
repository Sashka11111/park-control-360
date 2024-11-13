package com.parkcontrol.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.parkcontrol.domain.model.ParkingSpot;
import com.parkcontrol.domain.model.Category;
import com.parkcontrol.domain.validation.ParkingSpotValidationService;
import com.parkcontrol.service.util.JsonDataReader;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import java.util.stream.Collectors;

public class ParkingSpotService {

  private static final String PARKING_SPOTS_FILE_PATH = "data/parking_spots.json";
  private static final String CATEGORIES_FILE_PATH = "data/categories.json";
  private static List<ParkingSpot> parkingSpots;
  private static List<Category> categories;

  static {
    parkingSpots = JsonDataReader.modelDataJsonReader(PARKING_SPOTS_FILE_PATH, ParkingSpot[].class);
    categories = JsonDataReader.modelDataJsonReader(CATEGORIES_FILE_PATH, Category[].class);
  }

  public static void main(String[] args) {
    displayParkingSpots(parkingSpots);
  }

  public static void displayParkingSpots(List<ParkingSpot> parkingSpots) {
    if (!ParkingSpotValidationService.isParkingSpotsListValid(parkingSpots)) {
      System.out.println("Список паркувальних місць порожній.");
      return;
    }

    parkingSpots.forEach(parkingSpot -> {
      System.out.println("ID: " + parkingSpot.getSpotId());
      System.out.println("Номер місця: " + parkingSpot.getSpotNumber());
      System.out.println("Ставка за годину: " + parkingSpot.getRatePerHour());
      System.out.println("Чи зайняте: " + (parkingSpot.isOccupied() ? "Так" : "Ні"));
      Category category = findCategoryById(parkingSpot.getCategoryId());
      System.out.println("Категорія: " + (category != null ? category.getName() : "Категорія не знайдена"));
      System.out.println(); // Порожній рядок для кращої читабельності
    });
  }

  public static Category findCategoryById(UUID categoryId) {
    return categories.stream()
        .filter(category -> category.getId().equals(categoryId))
        .findFirst()
        .orElse(null);
  }

  public static void displayAvailableParkingSpots() {
    List<ParkingSpot> availableSpots = parkingSpots.stream()
        .filter(spot -> !spot.isOccupied())
        .collect(Collectors.toList());

    if (availableSpots.isEmpty()) {
      System.out.println("Немає вільних паркувальних місць.");
      return;
    }

    System.out.println("Вільні паркувальні місця:");
    displayParkingSpots(availableSpots);
  }

  public static void addParkingSpot() {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Додавання нового паркувального місця");

    UUID newParkingSpotId = UUID.randomUUID();
    int spotNumber = getValidatedSpotNumber(scanner);
    double ratePerHour = getValidatedRatePerHour(scanner);
    UUID categoryId = getValidatedCategoryId(scanner);

    ParkingSpot newParkingSpot = new ParkingSpot(spotNumber, ratePerHour, categoryId);
    newParkingSpot.setSpotId(newParkingSpotId);
    newParkingSpot.setOccupied(false);

    parkingSpots.add(newParkingSpot);
    saveParkingSpotsToJson();

    System.out.println("Нове паркувальне місце додано успішно.");
  }

  private static int getValidatedSpotNumber(Scanner scanner) {
    Integer spotNumber;
    while (true) {
      System.out.print("Введіть номер паркувального місця: ");
      String input = scanner.next();
      if (!ParkingSpotValidationService.isUUIDValid(input)) {
        System.out.println("Некоректний номер місця.");
        continue;
      }

      spotNumber = Integer.parseInt(input);
      if (!ParkingSpotValidationService.isSpotNumberValid(spotNumber)) {
        System.out.println("Номер місця повинен бути більшим за нуль.");
      } else if (!ParkingSpotValidationService.isSpotNumberUnique(parkingSpots, spotNumber)) {
        System.out.println("Паркувальне місце з таким номером вже існує.");
      } else {
        break;
      }
    }
    return spotNumber;
  }

  private static double getValidatedRatePerHour(Scanner scanner) {
    Double ratePerHour;
    while (true) {
      System.out.print("Введіть ставку за годину: ");
      String input = scanner.next();
      if (!ParkingSpotValidationService.isUUIDValid(input)) {
        System.out.println("Некоректна ставка за годину");
        continue;
      }

      ratePerHour = Double.parseDouble(input);
      if (!ParkingSpotValidationService.isRatePerHourValid(ratePerHour)) {
        System.out.println("Ставка повинна бути більшою за нуль.");
      } else {
        break;
      }
    }
    return ratePerHour;
  }

  private static UUID getValidatedCategoryId(Scanner scanner) {
    UUID categoryId;
    while (true) {
      System.out.print("Введіть ID категорії паркувального місця: ");
      String input = scanner.next();
      if (!ParkingSpotValidationService.isUUIDValid(input)) {
        System.out.println("Некоректний UUID. Спробуйте ще раз.");
        continue;
      }

      categoryId = UUID.fromString(input);
      if (!ParkingSpotValidationService.isCategoryValid(categories, categoryId)) {
        System.out.println("Категорія з таким ID не знайдена.");
      } else {
        break;
      }
    }
    return categoryId;
  }

  private static void saveParkingSpotsToJson() {
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
      objectMapper.writeValue(new File(PARKING_SPOTS_FILE_PATH), parkingSpots);
    } catch (IOException e) {
      System.err.println("Помилка при збереженні паркувальних місць у файл JSON: " + e.getMessage());
    }
  }
}

