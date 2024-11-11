package com.parkcontrol.service.operations;

import com.parkcontrol.domain.model.ParkingSpot;
import com.parkcontrol.domain.model.Category; // Якщо у вас є клас Category
import com.parkcontrol.service.util.JsonDataReader;

import java.util.List;
import java.util.stream.Collectors;

public class SearchService {
  private static final String PARKING_SPOT_FILE_PATH = "data/parking_spots.json";
  private static final String CATEGORY_FILE_PATH = "data/categories.json"; // Файл для категорій

  // Пошук паркувальних місць за назвою категорії
  public List<ParkingSpot> searchParkingSpotsByCategoryName(String categoryName) {
    // Завантаження категорій
    List<Category> categories = JsonDataReader.modelDataJsonReader(CATEGORY_FILE_PATH, Category[].class);

    // Пошук ID категорії за її назвою
    Category category = categories.stream()
        .filter(c -> c.getName().equalsIgnoreCase(categoryName)) // Пошук за назвою категорії
        .findFirst()
        .orElse(null);

    if (category == null) {
      System.out.println("Категорія не знайдена!");
      return List.of(); // Повертаємо порожній список, якщо категорію не знайдено
    }

    // Завантаження паркувальних місць
    List<ParkingSpot> parkingSpots = JsonDataReader.modelDataJsonReader(PARKING_SPOT_FILE_PATH, ParkingSpot[].class);

    // Фільтрація паркувальних місць за ID категорії
    return parkingSpots.stream()
        .filter(spot -> spot.getCategoryId().equals(category.getId())) // Порівнюємо за ID категорії
        .collect(Collectors.toList());
  }

  // Пошук паркувальних місць за номером місця
  public List<ParkingSpot> searchParkingSpotsByNumber(int spotNumber) {
    List<ParkingSpot> parkingSpots = JsonDataReader.modelDataJsonReader(PARKING_SPOT_FILE_PATH, ParkingSpot[].class);
    return parkingSpots.stream()
        .filter(spot -> spot.getSpotNumber() == spotNumber) // Пошук за номером паркувального місця
        .collect(Collectors.toList());
  }
}
