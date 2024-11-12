package com.parkcontrol.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.parkcontrol.domain.model.ParkingSpot;
import com.parkcontrol.domain.model.ParkingTicket;
import com.parkcontrol.service.util.JsonDataReader;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
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
    service.displayParkingTickets();
    service.addParkingTicket();
  }

  // Метод для відображення парковочних квитків
  public void displayParkingTickets() {
    if (parkingTickets.isEmpty()) {
      System.out.println("Список парковочних квитків порожній.");
    } else {
      System.out.println("Список парковочних квитків:");
      for (ParkingTicket ticket : parkingTickets) {
        System.out.println("Номер транспортного засобу: " + ticket.getVehicleLicensePlate());
        System.out.println("Номер місця: " + ticket.getParkingSpotId());
        System.out.println("Час створення: " + ticket.getStartTime());
        System.out.println("Час закінчення: " + ticket.getExpirationTime());
        System.out.println(); // Для розділення між записами
      }
    }
  }

  // Метод для додавання нового парковочного квитка
  public void addParkingTicket() {
    Scanner scanner = new Scanner(System.in);

    // Запитати користувача про дані нового квитка
    System.out.println("Додавання нового парковочного квитка");

    // Запит на номер паркувального місця
    System.out.print("Введіть номер паркувального місця: ");
    int spotNumber = scanner.nextInt();
    ParkingSpot parkingSpot = findParkingSpotByNumber(spotNumber);

    if (parkingSpot == null) {
      System.out.println("Паркувальне місце не знайдено.");
      return;
    }

    // Перевірка, чи місце вже зайняте
    if (parkingSpot.isOccupied()) {
      System.out.println("Паркувальне місце вже зайняте.");
      return;
    }

    // Запит на номер транспортного засобу
    scanner.nextLine(); // Споживаємо залишок рядка
    System.out.print("Введіть номер транспортного засобу: ");
    String vehicleLicensePlate = scanner.nextLine();

    // Запит на кількість годин
    System.out.print("Введіть кількість годин для паркування: ");
    double hours = scanner.nextDouble();

    // Створення нового парковочного квитка з використанням UUID
    UUID newTicketId = UUID.randomUUID(); // Генерація нового унікального ID для квитка
    // Додавання параметра для кількості годин (наприклад, 1 година)
    ParkingTicket newTicket = new ParkingTicket(vehicleLicensePlate, parkingSpot.getSpotId().toString(), LocalDateTime.now(), newTicketId, hours);
    newTicket.setExpirationTime(LocalDateTime.now().plusHours((long) hours));

    // Зміна статусу місця на "зайнято"
    parkingSpot.setOccupied(true);

    // Додаємо новий квиток до списку
    parkingTickets.add(newTicket);

    // Зберігаємо оновлені дані у файл JSON
    saveParkingTicketsToJson();

    System.out.println("Парковочний квиток додано успішно.");

    // Закриваємо ресурс scanner
    scanner.close();
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
      objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
      objectMapper.writeValue(new File(PARKING_TICKET_FILE_PATH), parkingTickets);
    } catch (IOException e) {
      System.out.println("Помилка при збереженні парковочних квитків у файл JSON: " + e.getMessage());
    }
  }
}
