package com.parkcontrol.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.UUID;

public class ParkingTicket {

  private UUID ticketId; // Унікальний ID паркувального квитка
  private String vehicleLicensePlate; // Номер транспортного засобу
  private String parkingSpotId; // ID паркувального місця

  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
  private LocalDateTime startTime; // Час початку парковки

  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
  private LocalDateTime expirationTime; // Час закінчення парковки

  private UUID userId; // ID користувача, який запаркував транспортний засіб
  private double ratePerHour; // Ставка за годину парковки
  private Boolean expired;

  // Конструктор для ініціалізації парковочного квитка
  public ParkingTicket(String vehicleLicensePlate, String parkingSpotId, LocalDateTime startTime, UUID userId,
      double hours, double ratePerHour, boolean expired) {
    this.ticketId = UUID.randomUUID(); // Генерація унікального ID для квитка
    this.vehicleLicensePlate = vehicleLicensePlate;
    this.parkingSpotId = parkingSpotId;
    this.startTime = startTime;
    this.userId = userId;
    this.expirationTime = startTime.plusHours((long) hours); // Розрахунок часу закінчення парковки
    this.ratePerHour = ratePerHour; // Встановлення ставки за годину
    this.expired = expired; // Встановлення статусу "прострочено"
  }

  // Конструктор без параметрів (для десеріалізації JSON)
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

  public double getRatePerHour() {
    return ratePerHour;
  }

  public void setRatePerHour(double ratePerHour) {
    this.ratePerHour = ratePerHour;
  }
  public Boolean getExpired() {
    return expired;
  }

  public void setExpired(Boolean expired) {
    this.expired = expired;
  }

  // Метод для виведення інформації про квиток
  @Override
  public String toString() {
    return "ParkingTicket{" +
        "ticketId=" + ticketId +
        ", vehicleLicensePlate='" + vehicleLicensePlate + '\'' +
        ", parkingSpotId='" + parkingSpotId + '\'' +
        ", startTime=" + startTime +
        ", expirationTime=" + expirationTime +
        ", userId=" + userId +
        ", ratePerHour=" + ratePerHour +
        ", expired=" + expired +
        '}';
  }
}
