package com.parkcontrol.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.parkcontrol.domain.model.Category;
import com.parkcontrol.domain.validation.CategoryValidationService;
import com.parkcontrol.domain.validation.UserValidationService;
import com.parkcontrol.service.util.JsonDataReader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class CategoryService {

  private static final String CATEGORIES_FILE_PATH = "data/categories.json";
  private static List<Category> categories;

  static {
    // Ініціалізація списку категорій при запуску програми
    categories = JsonDataReader.modelDataJsonReader(CATEGORIES_FILE_PATH, Category[].class);
    if (categories == null) {
      categories = new ArrayList<>(); // У разі помилки читання створюємо порожній список
    }
  }

  public static void main(String[] args) {
    displayCategories(categories);
  }

  // Метод для відображення категорій
  public static void displayCategories(List<Category> categories) {
    if (categories.isEmpty()) {
      System.out.println("Список категорій порожній.");
    } else {
      System.out.println("Список категорій:");
      for (Category category : categories) {
        System.out.println("ID: " + category.getId());
        System.out.println("Назва: " + category.getName());
        System.out.println(); // Для розділення між записами
      }
    }
  }

  // Метод для додавання нової категорії
  public static void addCategory() {
    Scanner scanner = new Scanner(System.in);

    UUID newCategoryId;
    String name;

    // Генерація нового унікального ID для категорії
    do {
      newCategoryId = UUID.randomUUID();
      if (!CategoryValidationService.isCategoryIdUnique(categories, newCategoryId)) {
        System.out.println("Згенеровано повторюваний ID. Спроба знову...");
      } else {
        break;
      }
    } while (true);

    // Запитати користувача про назву нової категорії
    System.out.println("Додавання нової категорії");
    do {
      System.out.print("Введіть назву категорії: ");
      name = scanner.nextLine();

      if (!UserValidationService.isNotEmpty(name)) {
        System.out.println("Категорія не може бути порожньою.");
      } else if (!CategoryValidationService.isValidCategoryName(name)) {
        System.out.println("Категорія повинна бути до 50 символів.");
      } else if (!CategoryValidationService.isCategoryNameUnique(categories, name)) {
        System.out.println("Категорія з такою назвою вже існує.");
      } else {
        break;  // Якщо всі умови виконані, виходимо з циклу
      }
    } while (true);

    // Створюємо новий об'єкт категорії
    Category newCategory = new Category(newCategoryId, name);

    // Додаємо нову категорію до списку
    categories.add(newCategory);

    // Зберегти оновлені дані у файлі JSON
    saveCategoriesToJson();

    System.out.println("Нову категорію додано успішно.");
  }

  // Метод для збереження категорій у файл JSON
  private static void saveCategoriesToJson() {
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
      objectMapper.writeValue(new File(CATEGORIES_FILE_PATH), categories);
    } catch (IOException e) {
      System.out.println("Помилка при збереженні категорій у файл JSON: " + e.getMessage());
    }
  }
}
