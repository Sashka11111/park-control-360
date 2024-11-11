package com.parkcontrol.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.parkcontrol.domain.model.Category;
import com.parkcontrol.domain.validation.ValidationService;
import com.parkcontrol.service.util.JsonDataReader;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class CategoryService {

  private static final String CATEGORIES_FILE_PATH = "data/categories.json";
  private static List<Category> categories;

  static {
    // Ініціалізація списку категорій при запуску програми
    categories = JsonDataReader.modelDataJsonReader(CATEGORIES_FILE_PATH, Category[].class);
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
        System.out.println("Назва: " + category.getName());
        System.out.println(); // Для розділення між записами
      }
    }
  }

  // Метод для додавання нової категорії
  public static void addCategory() {
    Scanner scanner = new Scanner(System.in);

    // Генерація нового унікального ID для категорії
    UUID newCategoryId = UUID.randomUUID();

    // Запитати користувача про дані нової категорії
    System.out.println("Додавання нової категорії");

    String name;
    do {
      System.out.print("Введіть назву категорії: ");
      name = scanner.nextLine();

      if (!ValidationService.isNotEmpty(name)) {
        System.out.println("Категорія не може бути порожньою.");
      } else if (!ValidationService.isValidCategoryName(name)) {
        System.out.println("Категорія повинна бути до 255 символів.");
      } else if (!ValidationService.isCategoryNameUnique(categories, name)) {
        System.out.println("Категорія з такою назвою вже існує.");
      } else {
        break;  // Якщо всі умови виконані, виходимо з циклу
      }
    } while (true);

    // Створюємо новий об'єкт категорії з унікальним ID
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
