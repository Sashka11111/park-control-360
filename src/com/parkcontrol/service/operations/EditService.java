package com.parkcontrol.service.operations;

import com.parkcontrol.domain.model.Category;
import com.parkcontrol.domain.model.ParkingTicket;
import com.parkcontrol.domain.model.ParkingSpot;
import com.parkcontrol.service.util.JsonDataReader;
import com.parkcontrol.service.util.FileUtil;
import com.parkcontrol.domain.validation.ValidationService;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class EditService {

  private static final String PARKING_TICKETS_FILE_PATH = "data/parking_tickets.json";
  private static final String PARKING_SPOT_FILE_PATH = "data/parking_spots.json";
  private static final String CATEGORIES_FILE_PATH = "data/categories.json";

  public static void editParkingTicket() {
    List<ParkingTicket> tickets = JsonDataReader.modelDataJsonReader(PARKING_TICKETS_FILE_PATH, ParkingTicket[].class);

    if (tickets.isEmpty()) {
      System.out.println("Список паркувальних квитків порожній.");
      return;
    }

    Scanner scanner = new Scanner(System.in);
    System.out.print("Введіть ID квитка для редагування: ");
    String inputId = scanner.nextLine();

    if (!ValidationService.isValidUUID(inputId)) {
      System.out.println("Некоректний формат ID.");
      return;
    }

    UUID ticketId = UUID.fromString(inputId);
    ParkingTicket ticket = tickets.stream()
        .filter(t -> t.getTicketId().equals(ticketId))
        .findFirst()
        .orElse(null);

    if (ticket == null) {
      System.out.println("Квиток з таким ID не знайдено.");
      return;
    }

    System.out.print("Введіть новий номер транспортного засобу: ");
    String newLicensePlate = scanner.nextLine();

    if (ValidationService.isNotEmpty(newLicensePlate)) {
      ticket.setVehicleLicensePlate(newLicensePlate);
    } else {
      System.out.println("Номер транспортного засобу не може бути порожнім.");
    }

    FileUtil.saveToFile(PARKING_TICKETS_FILE_PATH, tickets);
    System.out.println("Квиток успішно оновлено.");
  }

  public static void editParkingSpot() {
    List<ParkingSpot> spots = JsonDataReader.modelDataJsonReader(PARKING_SPOT_FILE_PATH, ParkingSpot[].class);

    if (spots.isEmpty()) {
      System.out.println("Список паркувальних місць порожній.");
      return;
    }

    Scanner scanner = new Scanner(System.in);
    System.out.print("Введіть ID паркувального місця для редагування: ");
    String inputId = scanner.nextLine();

    if (!ValidationService.isValidUUID(inputId)) {
      System.out.println("Некоректний формат ID.");
      return;
    }

    UUID spotId = UUID.fromString(inputId);
    ParkingSpot spot = spots.stream()
        .filter(s -> s.getSpotId().equals(spotId))
        .findFirst()
        .orElse(null);

    if (spot == null) {
      System.out.println("Паркувальне місце з таким ID не знайдено.");
      return;
    }

    System.out.print("Введіть новий номер місця: ");
    String newSpotNumber = scanner.nextLine();

    if (ValidationService.isValidNumber(newSpotNumber)) {
      spot.setSpotNumber(Integer.parseInt(newSpotNumber));
    } else {
      System.out.println("Некоректний номер місця.");
    }

    System.out.print("Введіть нову ставку за годину: ");
    String newRate = scanner.nextLine();

    if (ValidationService.isValidNumber(newRate)) {
      spot.setRatePerHour(Double.parseDouble(newRate));
    } else {
      System.out.println("Некоректна ставка.");
    }

    FileUtil.saveToFile(PARKING_SPOT_FILE_PATH, spots);
    System.out.println("Паркувальне місце успішно оновлено.");
  }

  public static void editParkingCategory() {
    List<Category> categories = JsonDataReader.modelDataJsonReader(CATEGORIES_FILE_PATH, Category[].class);

    if (categories.isEmpty()) {
      System.out.println("Список категорій порожній.");
      return;
    }

    Scanner scanner = new Scanner(System.in);
    System.out.print("Введіть ID категорії для редагування: ");
    String inputId = scanner.nextLine();

    if (!ValidationService.isValidUUID(inputId)) {
      System.out.println("Некоректний формат ID.");
      return;
    }

    UUID categoryId = UUID.fromString(inputId);
    Category category = categories.stream()
        .filter(c -> c.getId().equals(categoryId))
        .findFirst()
        .orElse(null);

    if (category == null) {
      System.out.println("Категорія з таким ID не знайдена.");
      return;
    }

    System.out.print("Введіть нову назву категорії: ");
    String newName = scanner.nextLine();

    if (!ValidationService.isValidCategoryName(newName)) {
      System.out.println("Некоректна назва категорії.");
      return;
    }

    category.setName(newName);

    FileUtil.saveToFile(CATEGORIES_FILE_PATH, categories);
    System.out.println("Категорію успішно оновлено.");
  }
}
