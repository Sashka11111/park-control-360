package com.parkcontrol.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule; // Потрібен для роботи з LocalDateTime
import com.parkcontrol.domain.model.ParkingSpot;
import com.parkcontrol.domain.model.ParkingTicket;
import com.parkcontrol.domain.validation.ParkingTicketValidationService;
import com.parkcontrol.presentation.Application;
import com.parkcontrol.service.util.JsonDataReader;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class ParkingTicketService {

  private static final String PARKING_TICKET_FILE_PATH = "data/parking_tickets.json";
  private static final String PARKING_SPOTS_FILE_PATH = "data/parking_spots.json";
  private List<ParkingTicket> parkingTickets;
  private List<ParkingSpot> parkingSpots;

  // Конструктор для ініціалізації даних
  public ParkingTicketService() {
    parkingTickets = JsonDataReader.modelDataJsonReader(PARKING_TICKET_FILE_PATH, ParkingTicket[].class);
    parkingSpots = JsonDataReader.modelDataJsonReader(PARKING_SPOTS_FILE_PATH, ParkingSpot[].class);
  }

  public static void main(String[] args) {
    ParkingTicketService service = new ParkingTicketService();
    // Відображення квитків для конкретного користувача
    service.displayParkingTicketsForUser();
  }

  public void displayParkingTicketsForUser() {
    UUID userId = Application.currentUser.getUserId(); // Отримуємо ID поточного користувача
    List<ParkingTicket> userTickets = new ArrayList<>();

    // Фільтрація квитків для конкретного користувача
    for (ParkingTicket ticket : parkingTickets) {
      if (ticket.getUserId().equals(userId)) {
        userTickets.add(ticket);
      }
    }

    if (userTickets.isEmpty()) {
      System.out.println("У користувача з ID " + userId + " немає активних паркувальних квитків.");
    } else {
      System.out.println("Мої паркувальні квитки:");
      for (ParkingTicket ticket : userTickets) {
        System.out.println("Номер транспортного засобу: " + ticket.getVehicleLicensePlate());
        System.out.println("Номер місця: " + ticket.getParkingSpotId());
        System.out.println("Час створення: " + ticket.getStartTime());
        System.out.println("Час закінчення: " + ticket.getExpirationTime());
        System.out.println(); // Для розділення між записами
      }
    }
  }
  public void addParkingTicket() {
    Scanner scanner = new Scanner(System.in);
    ParkingSpot parkingSpot = null;
    String vehicleLicensePlate;
    double hours;
    boolean validInput;

    System.out.println("Додавання нового паркувального квитка (бронювання місця)");

    // Цикл для введення номера паркувального місця з валідацією
    do {
      System.out.print("Введіть номер паркувального місця: ");
      int spotNumber = scanner.nextInt();
      parkingSpot = findParkingSpotByNumber(spotNumber);

      if (!ParkingTicketValidationService.isParkingSpotValid(parkingSpot)) {
        System.out.println("Паркувальне місце не знайдено.");
        validInput = false;
      } else if (ParkingTicketValidationService.isParkingSpotOccupied(parkingSpot)) {
        System.out.println("Паркувальне місце вже зайняте.");
        validInput = false;
      } else {
        validInput = true;
      }
    } while (!validInput);

    // Цикл для введення номера транспортного засобу з валідацією
    do {
      scanner.nextLine(); // Споживаємо залишок рядка
      System.out.print("Введіть номер транспортного засобу: ");
      vehicleLicensePlate = scanner.nextLine();

      if (!ParkingTicketValidationService.isValidLicensePlate(vehicleLicensePlate)) {
        System.out.println("Некоректний номер транспортного засобу.");
        validInput = false;
      } else {
        validInput = true;
      }
    } while (!validInput);

    // Цикл для введення кількості годин з валідацією
    do {
      System.out.print("Введіть кількість годин для паркування: ");
      hours = scanner.nextDouble();

      if (!ParkingTicketValidationService.isValidParkingDuration(LocalDateTime.now(), hours)) {
        System.out.println("Некоректна кількість годин.");
        validInput = false;
      } else {
        validInput = true;
      }
    } while (!validInput);

    // Створення нового квитка після успішного введення всіх даних
    LocalDateTime startTime = LocalDateTime.now();
    LocalDateTime expirationTime = startTime.plusHours((long) hours);
    double ratePerHour = parkingSpot.getRatePerHour(); // Отримуємо ставку за годину з паркувального місця
    boolean expired = expirationTime.isBefore(LocalDateTime.now()); // Встановлюємо статус 'expired'
    ParkingTicket newTicket = new ParkingTicket(
        vehicleLicensePlate,
        parkingSpot.getSpotId().toString(),
        startTime,
        Application.currentUser.getUserId(),
        hours,
        ratePerHour,
        expired
    );
    // Зміна статусу місця на "зайнято"
    parkingSpot.setOccupied(true);

    // Додаємо новий квиток до списку
    parkingTickets.add(newTicket);

    // Зберігаємо оновлені дані у файл JSON
    saveParkingTicketsToJson();

    System.out.println("Паркувальний квиток додано успішно:");
    System.out.println("Номер транспортного засобу: " + newTicket.getVehicleLicensePlate());
    System.out.println("Час закінчення: " + expirationTime);
  }

  // Пошук паркувального місця за номером
  private ParkingSpot findParkingSpotByNumber(int spotNumber) {
    for (ParkingSpot spot : parkingSpots) {
      if (spot.getSpotNumber() == spotNumber) {
        return spot;
      }
    }
    return null;
  }
  // Метод для збереження парковочних квитків у файл JSON
  private void saveParkingTicketsToJson() {
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      objectMapper.registerModule(new JavaTimeModule()); // Реєстрація модуля для LocalDateTime
      objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
      objectMapper.writeValue(new File(PARKING_TICKET_FILE_PATH), parkingTickets);
    } catch (IOException e) {
      System.out.println("Помилка при збереженні парковочних квитків у файл JSON: " + e.getMessage());
    }
  }
}
