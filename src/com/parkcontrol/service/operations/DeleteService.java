package com.parkcontrol.service.operations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.parkcontrol.service.util.JsonDataReader;
import com.parkcontrol.domain.model.ParkingTicket;
import com.parkcontrol.domain.model.ParkingSpot;
import com.parkcontrol.domain.model.User;
import com.parkcontrol.domain.validation.UserInputHandler;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class DeleteService {

  private static final String PARKING_TICKET_FILE_PATH = "data/parking_tickets.json";
  private static final String PARKING_SPOT_FILE_PATH = "data/parking_spots.json";
  private static final String USER_FILE_PATH = "data/users.json";

  // Видалення сесії паркування
  public static void deleteParkingSession() {
    List<ParkingTicket> parkingTickets = JsonDataReader.modelDataJsonReader(PARKING_TICKET_FILE_PATH, ParkingTicket[].class);

    System.out.println("Список доступних сесій паркування:");
    for (ParkingTicket ticket : parkingTickets) {
      System.out.println("ID сесії: " + ticket.getTicketId() + ", Користувач: " + ticket.getUserId());
    }

    String ticketId = UserInputHandler.getStringInput("Введіть ID сесії, яку хочете видалити:");

    ParkingTicket selectedTicket = parkingTickets.stream()
        .filter(ticket -> ticket.getTicketId().toString().equals(ticketId))
        .findFirst()
        .orElse(null);

    if (selectedTicket != null) {
      parkingTickets.remove(selectedTicket);
      saveToFile(PARKING_TICKET_FILE_PATH, parkingTickets);
      System.out.println("Сесію паркування успішно видалено.");
    } else {
      System.out.println("Сесію паркування з введеним ID не знайдено.");
    }
  }

  // Видалення паркувального місця
  public static void deleteParkingSpot() {
    List<ParkingSpot> parkingSpots = JsonDataReader.modelDataJsonReader(PARKING_SPOT_FILE_PATH, ParkingSpot[].class);

    System.out.println("Список доступних паркувальних місць:");
    for (ParkingSpot spot : parkingSpots) {
      System.out.println("ID місця: " + spot.getSpotId() + ", Номер: " + spot.getSpotNumber());
    }

    String spotId = UserInputHandler.getStringInput("Введіть ID місця, яке хочете видалити:");

    ParkingSpot selectedSpot = parkingSpots.stream()
        .filter(spot -> spot.getSpotId().toString().equals(spotId))
        .findFirst()
        .orElse(null);

    if (selectedSpot != null) {
      parkingSpots.remove(selectedSpot);
      saveToFile(PARKING_SPOT_FILE_PATH, parkingSpots);
      System.out.println("Паркувальне місце успішно видалено.");
    } else {
      System.out.println("Паркувальне місце з введеним ID не знайдено.");
    }
  }

  // Видалення користувача
  public static void deleteUser() {
    List<User> users = JsonDataReader.modelDataJsonReader(USER_FILE_PATH, User[].class);

    System.out.println("Список користувачів:");
    for (User user : users) {
      System.out.println("ID користувача: " + user.getUserId() + ", Ім'я: " + user.getUsername());
    }

    String userId = UserInputHandler.getStringInput("Введіть ID користувача, якого хочете видалити:");

    User selectedUser = users.stream()
        .filter(user -> user.getUserId().toString().equals(userId))
        .findFirst()
        .orElse(null);

    if (selectedUser != null) {
      users.remove(selectedUser);
      saveToFile(USER_FILE_PATH, users);
      System.out.println("Користувача успішно видалено.");
    } else {
      System.out.println("Користувача з введеним ID не знайдено.");
    }
  }

  // Загальний метод для збереження даних у файл
  private static <T> void saveToFile(String filePath, List<T> dataList) {
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
      objectMapper.writeValue(new File(filePath), dataList);
    } catch (IOException e) {
      System.out.println("Помилка при збереженні оновлених даних: " + e.getMessage());
    }
  }
}
