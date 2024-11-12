package com.parkcontrol.service.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.parkcontrol.domain.model.Category;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JsonDataReader {
  // Статичний список категорій, якщо потрібно кешування
  private static List<Category> categories;

  // Порожній конструктор
  public JsonDataReader() {
  }

  /**
   * Універсальний метод для зчитування даних із JSON-файлу.
   *
   * @param filePath Шлях до файлу.
   * @param clazz Клас масиву об'єктів, які зчитуються.
   * @param <T> Тип об'єктів, що зчитуються.
   * @return Список об'єктів типу T.
   */
  public static <T> List<T> modelDataJsonReader(String filePath, Class<T[]> clazz) {
    // Створення екземпляра ObjectMapper для роботи з JSON
    ObjectMapper objectMapper = new ObjectMapper();

    // Реєстрація JavaTimeModule для підтримки Java 8 LocalDateTime та інших типів
    objectMapper.registerModule(new JavaTimeModule());
    objectMapper.disable(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES); // Не зупиняти на невідомих властивостях

    List<T> dataList = new ArrayList<>();

    try {
      // Зчитування масиву об'єктів із файлу
      T[] data = objectMapper.readValue(new File(filePath), clazz);
      // Додавання об'єктів до списку
      dataList.addAll(Arrays.asList(data));
    } catch (IOException e) {
      // Обробка винятків, що виникають при зчитуванні
      e.printStackTrace();
    }

    return dataList;
  }
}
