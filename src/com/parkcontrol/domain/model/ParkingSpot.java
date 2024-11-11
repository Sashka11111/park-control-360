package com.parkcontrol.domain.model;

import java.util.UUID;

public class ParkingSpot {
  private UUID spotId;         // UUID для ідентифікації місця
  private int spotNumber;      // Номер паркувального місця
  private boolean isOccupied;  // Чи зайняте місце
  private double ratePerHour;  // Ставка за годину
  private UUID categoryId;     // ID категорії паркувального місця
  // Конструктор за замовчуванням

  public ParkingSpot() {
  }

  // Конструктор, який генерує UUID, приймає номер місця, ставку та ID категорії
  public ParkingSpot(int spotNumber, double ratePerHour, UUID categoryId) {
    this.spotId = UUID.randomUUID(); // Генерація нового UUID для місця
    this.spotNumber = spotNumber;
    this.isOccupied = false; // За замовчуванням місце вільне
    this.ratePerHour = ratePerHour;
    this.categoryId = categoryId; // Встановлення ID категорії
  }

  public UUID getSpotId() {
    return spotId;
  }

  public void setSpotId(UUID spotId) {
    this.spotId = spotId;
  }

  public int getSpotNumber() {
    return spotNumber;
  }

  public void setSpotNumber(int spotNumber) {
    this.spotNumber = spotNumber;
  }

  public boolean isOccupied() {
    return isOccupied;
  }

  public void setOccupied(boolean occupied) {
    isOccupied = occupied;
  }

  public double getRatePerHour() {
    return ratePerHour;
  }

  public void setRatePerHour(double ratePerHour) {
    this.ratePerHour = ratePerHour;
  }

  public UUID getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(UUID categoryId) {
    this.categoryId = categoryId;
  }

  @Override
  public String toString() {
    return "ParkingSpot{" +
        "spotId=" + spotId +
        ", spotNumber=" + spotNumber +
        ", isOccupied=" + isOccupied +
        ", ratePerHour=" + ratePerHour +
        ", categoryId=" + categoryId +
        '}';
  }
}