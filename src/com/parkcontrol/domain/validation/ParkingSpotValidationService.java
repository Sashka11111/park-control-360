package com.parkcontrol.domain.validation;

import com.parkcontrol.domain.model.ParkingSpot;
import com.parkcontrol.domain.model.Category;

import java.util.List;
import java.util.UUID;

public class ParkingSpotValidationService {

  // Перевірка унікальності номера паркувального місця
  public static boolean isSpotNumberUnique(List<ParkingSpot> parkingSpots, int spotNumber) {
    return parkingSpots.stream().noneMatch(spot -> spot.getSpotNumber() == spotNumber);
  }

  // Перевірка коректності ставки за годину (має бути більше 0)
  public static boolean isValidRatePerHour(double ratePerHour) {
    return ratePerHour > 0;
  }

  // Перевірка, чи існує категорія з вказаним ID
  public static boolean isCategoryValid(List<Category> categories, UUID categoryId) {
    return categories != null && categoryId != null &&
        categories.stream().anyMatch(category -> category.getId().equals(categoryId));
  }

  // Перевірка, чи список паркувальних місць не порожній
  public static boolean isParkingSpotsListValid(List<ParkingSpot> parkingSpots) {
    return parkingSpots != null && !parkingSpots.isEmpty();
  }

  // Перевірка, чи список категорій не порожній
  public static boolean isCategoriesListValid(List<Category> categories) {
    return categories != null && !categories.isEmpty();
  }

  // Перевірка, чи номер місця є коректним
  public static boolean isSpotNumberValid(Integer spotNumber) {
    return spotNumber != null && spotNumber > 0;
  }

  // Перевірка, чи ставка за годину є коректною
  public static boolean isRatePerHourValid(Double ratePerHour) {
    return ratePerHour != null && ratePerHour > 0;
  }

  // Перевірка, чи UUID є коректним
  public static boolean isUUIDValid(String uuid) {
    try {
      UUID.fromString(uuid);
      return true;
    } catch (IllegalArgumentException | NullPointerException e) {
      return false;
    }
  }
}
