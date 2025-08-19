package com.boaDispensarySystem.data.models;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Drug {
    private int id;
    private String name;
    private Type type;
    private Category category;
    private LocalDate expiryDate;
    private LocalDate manufactureDate;
    private LocalDateTime dateAdded = LocalDateTime.now();
    private int quantity;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public LocalDate getManufactureDate() {
        return manufactureDate;
    }

    public void setManufactureDate(LocalDate manufactureDate) {
        this.manufactureDate = manufactureDate;
    }

    public LocalDateTime getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(LocalDateTime dateAdded) {
        this.dateAdded = dateAdded;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Drug{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", category=" + category +
                ", expiryDate=" + expiryDate +
                ", manufactureDate=" + manufactureDate +
                ", dateAdded=" + dateAdded +
                '}';
    }
}
