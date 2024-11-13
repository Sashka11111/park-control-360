package com.parkcontrol.service.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class FileUtil {

  // Загальний метод для збереження даних у файл
  public static <T> void saveToFile(String filePath, List<T> dataList) {
    try {
      // Використовуємо відформатований ObjectMapper
      ObjectMapper objectMapper = ObjectMapperConfig.getConfiguredObjectMapper();

      // Зберігаємо список об'єктів у файл у красивому форматі
      objectMapper.writeValue(new File(filePath), dataList);

    } catch (IOException e) {
      System.out.println("Помилка при збереженні даних у файл: " + e.getMessage());
    }
  }
}
