package com.parkcontrol.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.parkcontrol.domain.model.ParkingSpot;
import com.parkcontrol.domain.model.ParkingTicket;
import com.parkcontrol.service.util.JsonDataReader;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class ParkingTicketService {

  private static final String PARKING_TICKET_FILE_PATH = "data/parking_tickets.json";
  private static List<ParkingTicket> parkingTickets;
  private static List<ParkingSpot> parkingSpots;

  // Створення нового парковочного квитка
  public static void createTicket() {
    Scanner scanner = new Scanner(System.in);

    // Завантажуємо паркувальні місця з файлу
    parkingSpots = JsonDataReader.modelDataJsonReader("data/parking_spots.json", ParkingSpot[].class);

    System.out.println("Введіть номер паркувального місця:");
    int spotNumber = scanner.nextInt();
    scanner.nextLine(); // Споживаємо залишок рядка

    ParkingSpot parkingSpot = findParkingSpotByNumber(spotNumber);

    if (parkingSpot == null) {
      System.out.println("Паркувальне місце не знайдено!");
      return;
    }

    if (parkingSpot.isOccupied()) {
      System.out.println("Це місце вже зайняте!");
      return;
    }

    System.out.println("Введіть номер транспортного засобу:");
    String vehicleLicensePlate = scanner.nextLine();

    System.out.println("Введіть кількість годин для паркування:");
    double hours = scanner.nextDouble();
    double amount = parkingSpot.getRatePerHour() * hours;

    // Створення нового парковочного квитка
    ParkingTicket newTicket = new ParkingTicket(vehicleLicensePlate, parkingSpot.getSpotId().toString(), LocalDateTime.now());

    // Встановлення часу закінчення парковки
    newTicket.setExpirationTime(LocalDateTime.now().plusHours((long) hours));

    // Завантаження існуючих квитків або ініціалізація списку, якщо файл не існує
    parkingTickets = JsonDataReader.modelDataJsonReader(PARKING_TICKET_FILE_PATH, ParkingTicket[].class);
    if (parkingTickets == null) {
      parkingTickets = new ArrayList<>();
    }
    parkingTickets.add(newTicket);

    // Збереження списку квитків у файл
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
      objectMapper.writeValue(new File(PARKING_TICKET_FILE_PATH), parkingTickets);
      System.out.println("Парковочний квиток створено успішно.");
    } catch (IOException e) {
      System.out.println("Помилка при створенні квитка: " + e.getMessage());
    }
  }

  // Метод для пошуку паркувального місця за номером
  private static ParkingSpot findParkingSpotByNumber(int spotNumber) {
    for (ParkingSpot spot : parkingSpots) {
      if (spot.getSpotNumber() == spotNumber) {
        return spot;
      }
    }
    return null;
  }
}
