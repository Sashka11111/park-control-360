package com.parkcontrol.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class ParkingTicket {

  private UUID ticketId; // Унікальний ID паркувального квитка
  private String vehicleLicensePlate; // Номер транспортного засобу
  private String parkingSpotId; // ID паркувального місця
  private LocalDateTime startTime; // Час початку парковки
  private LocalDateTime expirationTime; // Час закінчення парковки
  private UUID userId; // ID користувача, який запаркував транспортний засіб

  // Конструктор
  public ParkingTicket(String vehicleLicensePlate, String parkingSpotId, LocalDateTime startTime, UUID userId, double hours) {
    this.ticketId = UUID.randomUUID(); // Генерація унікального ID для квитка
    this.vehicleLicensePlate = vehicleLicensePlate;
    this.parkingSpotId = parkingSpotId;
    this.startTime = startTime;
    this.userId = userId; // Ідентифікатор користувача
    this.expirationTime = startTime.plusHours((long) hours); // Розрахунок часу закінчення парковки
  }
  // Конструктор без параметрів
  public ParkingTicket() {
  }
  // Геттери та сеттери
  public UUID getTicketId() {
    return ticketId;
  }

  public void setTicketId(UUID ticketId) {
    this.ticketId = ticketId;
  }

  public String getVehicleLicensePlate() {
    return vehicleLicensePlate;
  }

  public void setVehicleLicensePlate(String vehicleLicensePlate) {
    this.vehicleLicensePlate = vehicleLicensePlate;
  }

  public String getParkingSpotId() {
    return parkingSpotId;
  }

  public void setParkingSpotId(String parkingSpotId) {
    this.parkingSpotId = parkingSpotId;
  }

  public LocalDateTime getStartTime() {
    return startTime;
  }

  public void setStartTime(LocalDateTime startTime) {
    this.startTime = startTime;
  }

  public LocalDateTime getExpirationTime() {
    return expirationTime;
  }

  public void setExpirationTime(LocalDateTime expirationTime) {
    this.expirationTime = expirationTime;
  }

  public UUID getUserId() {
    return userId;
  }

  public void setUserId(UUID userId) {
    this.userId = userId;
  }

  // Метод для перевірки, чи закінчився час парковки
  public boolean isExpired() {
    return expirationTime.isBefore(LocalDateTime.now());
  }

  @Override
  public String toString() {
    return "ParkingTicket{" +
        "ticketId=" + ticketId +
        ", vehicleLicensePlate='" + vehicleLicensePlate + '\'' +
        ", parkingSpotId='" + parkingSpotId + '\'' +
        ", startTime=" + startTime +
        ", expirationTime=" + expirationTime +
        ", userId=" + userId +
        '}';
  }
}
