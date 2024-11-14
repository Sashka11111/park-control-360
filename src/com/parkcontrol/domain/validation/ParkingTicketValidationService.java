package com.parkcontrol.domain.validation;

import com.parkcontrol.domain.model.ParkingSpot;
import java.time.LocalDateTime;


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
}
