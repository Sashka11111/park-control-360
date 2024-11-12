package com.parkcontrol.service.operations;

import com.parkcontrol.domain.model.Category;
import com.parkcontrol.domain.model.ParkingSpot;
import com.parkcontrol.service.ParkingSpotService;
import com.parkcontrol.service.util.JsonDataReader;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class SearchService {

  private static final String PARKING_SPOT_FILE_PATH = "data/parking_spots.json";
  private static final String CATEGORY_FILE_PATH = "data/categories.json"; // Файл для категорій

  // Пошук паркувальних місць за назвою категорії
  public List<ParkingSpot> searchParkingSpotsByCategoryName(String categoryName) {
    List<Category> categories = loadCategories();

    Category category = findCategoryByName(categoryName, categories);
    if (category == null) {
      System.out.println("Категорія не знайдена!");
      return List.of();
    }

    List<ParkingSpot> parkingSpots = loadParkingSpots();
    List<ParkingSpot> result = filterParkingSpotsByCategory(parkingSpots, category.getId());

    displayParkingSpotsResult(result, categoryName);
    return result;
  }

  // Пошук паркувальних місць за номером місця
  public List<ParkingSpot> searchParkingSpotsByNumber(int spotNumber) {
    List<ParkingSpot> parkingSpots = loadParkingSpots();

    List<ParkingSpot> result = parkingSpots.stream()
        .filter(spot -> spot.getSpotNumber() == spotNumber)
        .collect(Collectors.toList());

    displayParkingSpotsResult(result, String.valueOf(spotNumber));
    return result;
  }

  // Завантаження категорій з JSON
  private List<Category> loadCategories() {
    return JsonDataReader.modelDataJsonReader(CATEGORY_FILE_PATH, Category[].class);
  }

  // Завантаження паркувальних місць з JSON
  private List<ParkingSpot> loadParkingSpots() {
    return JsonDataReader.modelDataJsonReader(PARKING_SPOT_FILE_PATH, ParkingSpot[].class);
  }

  // Пошук категорії за назвою
  private Category findCategoryByName(String categoryName, List<Category> categories) {
    return categories.stream()
        .filter(c -> c.getName().equalsIgnoreCase(categoryName))
        .findFirst()
        .orElse(null);
  }

  // Фільтрація паркувальних місць за ID категорії
  private List<ParkingSpot> filterParkingSpotsByCategory(List<ParkingSpot> parkingSpots, UUID categoryId) {
    return parkingSpots.stream()
        .filter(spot -> spot.getCategoryId().equals(categoryId)) // Порівнюємо UUID
        .collect(Collectors.toList());
  }

  // Виведення результатів пошуку
  private void displayParkingSpotsResult(List<ParkingSpot> result, String searchCriteria) {
    if (result.isEmpty()) {
      System.out.println("Паркувальні місця для '" + searchCriteria + "' не знайдені.");
    } else {
      ParkingSpotService.displayParkingSpots(result);
    }
  }
}
