package com.parkcontrol.domain.validation;

import com.parkcontrol.domain.model.ParkingSpot;
import com.parkcontrol.domain.model.ParkingTicket;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class ParkingTicketValidationService {

  // Перевірка, чи паркувальне місце існує за номером
  public static boolean isParkingSpotValid(ParkingSpot parkingSpot) {
    return parkingSpot != null;
  }

  // Перевірка, чи місце зайняте
  public static boolean isParkingSpotOccupied(ParkingSpot parkingSpot) {
    return parkingSpot != null && parkingSpot.isOccupied();
  }

  // Перевірка валідності номера транспортного засобу
  public static boolean isValidLicensePlate(String vehicleLicensePlate) {
    return vehicleLicensePlate != null && !vehicleLicensePlate.trim().isEmpty();
  }

  // Перевірка валідності часу початку та тривалості
  public static boolean isValidParkingDuration(LocalDateTime startTime, double hours) {
    return startTime != null && hours > 0;
  }

  // Перевірка унікальності квитка для користувача
  public static boolean isUniqueTicketForUser(List<ParkingTicket> tickets, UUID userId, String parkingSpotId) {
    if (tickets == null || userId == null || parkingSpotId == null || parkingSpotId.trim().isEmpty()) {
      return false;
    }
    return tickets.stream()
        .noneMatch(ticket -> ticket.getUserId().equals(userId) && ticket.getParkingSpotId().equals(parkingSpotId));
  }

  // Перевірка валідності UUID
  public static boolean isValidUUID(String uuidString) {
    if (uuidString == null || uuidString.trim().isEmpty()) {
      return false;
    }
    try {
      UUID.fromString(uuidString);
      return true;
    } catch (IllegalArgumentException e) {
      return false;
    }
  }

  // Перевірка списків на порожні значення
  public static boolean isNonEmptyList(List<?> list) {
    return list != null && !list.isEmpty();
  }
}
