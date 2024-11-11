package com.parkcontrol.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.parkcontrol.domain.model.ParkingSpot;
import com.parkcontrol.service.util.JsonDataReader;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class ParkingSpotService {

  private static final String PARKING_SPOT_FILE_PATH = "data/parking_spots.json";
  private static List<ParkingSpot> parkingSpots;

  // Метод для відображення всіх паркувальних місць
  public static void main(String[] args) {
    parkingSpots = JsonDataReader.modelDataJsonReader(PARKING_SPOT_FILE_PATH, ParkingSpot[].class);
    displayParkingSpots(parkingSpots);
  }

  // Метод для відображення всіх паркувальних місць
  public static void displayParkingSpots(List<ParkingSpot> parkingSpotList) {
    if (parkingSpotList.isEmpty()) {
      System.out.println("Список паркувальних місць порожній.");
    } else {
      System.out.println("Список паркувальних місць:");
      for (ParkingSpot parkingSpot : parkingSpotList) {
        System.out.println("ID місця: " + parkingSpot.getSpotId());
        System.out.println("Номер місця: " + parkingSpot.getSpotNumber());
        System.out.println("Чи зайняте: " + (parkingSpot.isOccupied() ? "Так" : "Ні"));
        System.out.println("Ставка за годину: " + parkingSpot.getRatePerHour());
        System.out.println("ID категорії: " + parkingSpot.getCategoryId()); // Додано відображення ID категорії
        System.out.println(); // Для розділення між записами
      }
    }
  }

  // Метод для додавання нового паркувального місця
  public static void addParkingSpot() {
    Scanner scanner = new Scanner(System.in);
    parkingSpots = JsonDataReader.modelDataJsonReader(PARKING_SPOT_FILE_PATH, ParkingSpot[].class);

    System.out.println("Додавання нового паркувального місця:");

    System.out.println("Введіть номер паркувального місця:");
    int spotNumber = scanner.nextInt();

    System.out.println("Введіть ставку за годину:");
    double ratePerHour = scanner.nextDouble();
    scanner.nextLine(); // Споживаємо залишок рядка

    System.out.println("Введіть ID категорії:");
    String categoryIdInput = scanner.nextLine();
    UUID categoryId = UUID.fromString(categoryIdInput); // Преобразовуємо введений рядок в UUID

    // Створення нового паркувального місця з переданим ID категорії
    ParkingSpot newParkingSpot = new ParkingSpot(spotNumber, ratePerHour, categoryId);

    parkingSpots.add(newParkingSpot);

    try {
      ObjectMapper objectMapper = new ObjectMapper();
      objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
      objectMapper.writeValue(new File(PARKING_SPOT_FILE_PATH), parkingSpots);
      System.out.println("Нову паркувальну місце додано успішно.");
    } catch (IOException e) {
      System.out.println("Помилка при додаванні нового паркувального місця: " + e.getMessage());
    }
  }
}
