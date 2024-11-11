package com.parkcontrol.service.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JsonDataReader {

  public JsonDataReader() {
  }

  public static <T> List<T> modelDataJsonReader(String filePath, Class<T[]> clazz) {
    ObjectMapper objectMapper = new ObjectMapper();
    List<T> dataList = new ArrayList<>();

    try {
      T[] data = objectMapper.readValue(new File(filePath), clazz);
      dataList.addAll(Arrays.asList(data));
    } catch (IOException e) {
      e.printStackTrace();
    }
    return dataList;
  }
}
