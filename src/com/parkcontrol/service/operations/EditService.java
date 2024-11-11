package com.parkcontrol.service.operations;

import com.parkcontrol.domain.model.ParkingTicket;
import com.parkcontrol.domain.model.ParkingSpot;
import com.parkcontrol.service.util.JsonDataReader;
import com.parkcontrol.service.util.JsonDataWriter;

import java.util.List;

public class EditService {

  private static final String PARKING_SESSION_FILE_PATH = "data/parking_sessions.json";
  private static final String PARKING_SPOT_FILE_PATH = "data/parking_spots.json";

  // Оновлення паркувальної сесії
  public boolean updateParkingSession(String sessionId, ParkingTicket updatedSession) {
    List<ParkingTicket> parkingTickets = JsonDataReader.modelDataJsonReader(PARKING_SESSION_FILE_PATH, ParkingTicket[].class);

    // Знаходимо сесію за sessionId
    for (int i = 0; i < parkingTickets.size(); i++) {
      if (parkingTickets.get(i).getTicketId().equals(sessionId)) {
        parkingTickets.set(i, updatedSession); // Заміняємо стару сесію на нову
        JsonDataWriter.writeToJsonFile(PARKING_SESSION_FILE_PATH, parkingTickets);
        return true; // Оновлення успішне
      }
    }
    return false; // Сесія не знайдена
  }

  // Оновлення паркувального місця
  public boolean updateParkingSpot(String spotId, ParkingSpot updatedSpot) {
    List<ParkingSpot> parkingSpots = JsonDataReader.modelDataJsonReader(PARKING_SPOT_FILE_PATH, ParkingSpot[].class);

    // Знаходимо паркувальне місце за spotId
    for (int i = 0; i < parkingSpots.size(); i++) {
      if (parkingSpots.get(i).getSpotId().equals(spotId)) {
        parkingSpots.set(i, updatedSpot); // Заміняємо старе паркувальне місце на нове
        JsonDataWriter.writeToJsonFile(PARKING_SPOT_FILE_PATH, parkingSpots);
        return true; // Оновлення успішне
      }
    }
    return false; // Паркувальне місце не знайдено
  }
}
