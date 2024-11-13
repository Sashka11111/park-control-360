package com.parkcontrol.service.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonDataReader {
  public static <T> List<T> modelDataJsonReader(String filePath, Class<T[]> clazz) {
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      objectMapper.registerModule(new JavaTimeModule()); // Реєстрація модуля для підтримки LocalDateTime
      T[] dataArray = objectMapper.readValue(new File(filePath), clazz);
      return new ArrayList<>(List.of(dataArray));  // Перетворюємо на змінюваний список
    } catch (IOException e) {
      System.out.println("Помилка при читанні JSON: " + e.getMessage());
      return new ArrayList<>(); // Повертаємо порожній список у разі помилки
    }
  }
}
