package com.parkcontrol.domain.model;

import java.util.UUID;

public class Category {

  private UUID id;
  private String name;

  // Конструктор за замовчуванням
  public Category() {
  }
  public Category(UUID id, String name) {
    this.id = id;
    this.name = name;
  }

  // Гетери та сетери

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "Category{" +
        "id=" + id +
        ", name='" + name + '\'' +
        '}';
  }
}
