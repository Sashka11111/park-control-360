package com.parkcontrol.service.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JsonDataWriter {

  // Запис даних у JSON файл
  public static <T> void writeToJsonFile(String filePath, List<T> data) {
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      // Записуємо список об'єктів у файл
      objectMapper.writeValue(new File(filePath), data);
    } catch (IOException e) {
      e.printStackTrace();
      System.err.println("Помилка при записі в JSON файл: " + e.getMessage());
    }
  }

  // Запис одного об'єкта в JSON файл
  public static <T> void writeToJsonFile(String filePath, T data) {
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      // Записуємо один об'єкт у файл
      objectMapper.writeValue(new File(filePath), data);
    } catch (IOException e) {
      e.printStackTrace();
      System.err.println("Помилка при записі в JSON файл: " + e.getMessage());
    }
  }
}
