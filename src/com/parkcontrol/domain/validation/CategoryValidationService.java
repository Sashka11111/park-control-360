package com.parkcontrol.domain.validation;

import com.parkcontrol.domain.model.Category;
import java.util.List;
import java.util.UUID;

public class CategoryValidationService {

  // Валідація для перевірки правильності формату категорії
  public static boolean isValidCategoryName(String name) {
    return UserValidationService.isNotEmpty(name) && name.length() <= 50;
  }

  // Метод для перевірки, чи є категорія з такою назвою
  public static boolean isCategoryNameUnique(List<Category> categories, String name) {
    return categories.stream().noneMatch(category -> category.getName().equalsIgnoreCase(name));
  }

  // Метод для перевірки унікальності ID категорії
  public static boolean isCategoryIdUnique(List<Category> categories, UUID id) {
    return categories.stream().noneMatch(category -> category.getId().equals(id));
  }
}
