package com.parkcontrol.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class ParkingTicket {
  private String ticketId;
  private String userId;
  private String parkingSpotId;
  private LocalDateTime issueTime;
  private LocalDateTime expirationTime;
  private double amountDue;

  public ParkingTicket() {
  }
  public ParkingTicket(String userId, String parkingSpotId, LocalDateTime issueTime) {
    this.ticketId = UUID.randomUUID().toString();
    this.userId = userId;
    this.parkingSpotId = parkingSpotId;
    this.issueTime = issueTime;
    this.expirationTime = null; // Квиток ще дійсний
    this.amountDue = 0.0;
  }

  public String getTicketId() { return ticketId; }

  public String getUserId() { return userId; }
  public void setUserId(String userId) { this.userId = userId; }

  public String getParkingSpotId() { return parkingSpotId; }
  public void setParkingSpotId(String parkingSpotId) { this.parkingSpotId = parkingSpotId; }

  public LocalDateTime getIssueTime() { return issueTime; }
  public void setIssueTime(LocalDateTime issueTime) { this.issueTime = issueTime; }

  public LocalDateTime getExpirationTime() { return expirationTime; }
  public void setExpirationTime(LocalDateTime expirationTime) {
    if (expirationTime.isBefore(issueTime)) {
      throw new IllegalArgumentException("Expiration time cannot be before issue time.");
    }
    this.expirationTime = expirationTime;
    calculateAmountDue();
  }

  public double getAmountDue() { return amountDue; }

  private void calculateAmountDue() {
    if (expirationTime != null) {
      long hours = java.time.Duration.between(issueTime, expirationTime).toHours();
      this.amountDue = hours * getHourlyRate();
    }
  }

  private double getHourlyRate() {
    return 5.0; // Задайте ставку, наприклад, 5.0 одиниць валюти за годину
  }

  @Override
  public String toString() {
    return "ParkingTicket{" +
        "ticketId='" + ticketId + '\'' +
        ", userId='" + userId + '\'' +
        ", parkingSpotId='" + parkingSpotId + '\'' +
        ", issueTime=" + issueTime +
        ", expirationTime=" + expirationTime +
        ", amountDue=" + amountDue +
        '}';
  }
}
