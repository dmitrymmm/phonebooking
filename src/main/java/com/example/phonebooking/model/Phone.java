package com.example.phonebooking.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Phone {
    private String model;
    private boolean isAvailable;
    private LocalDateTime bookedTime;
    private String bookedBy;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public LocalDateTime getBookedTime() {
        return bookedTime;
    }

    public void setBookedTime(LocalDateTime bookedTime) {
        this.bookedTime = bookedTime;
    }

    public String getBookedBy() {
        return bookedBy;
    }

    public void setBookedBy(String bookedBy) {
        this.bookedBy = bookedBy;
    }

    public String getTechnology() {
        return technology;
    }

    public void setTechnology(String technology) {
        this.technology = technology;
    }

    public String getBands2g() {
        return bands2g;
    }

    public void setBands2g(String bands2g) {
        this.bands2g = bands2g;
    }

    public String getBands3g() {
        return bands3g;
    }

    public void setBands3g(String bands3g) {
        this.bands3g = bands3g;
    }

    public String getBands4g() {
        return bands4g;
    }

    public void setBands4g(String bands4g) {
        this.bands4g = bands4g;
    }

    private String technology;
    private String bands2g;
    private String bands3g;
    private String bands4g;

    // All-args constructor
    public Phone(String model, boolean isAvailable, LocalDateTime bookedTime,
                 String bookedBy, String technology, String bands2g,
                 String bands3g, String bands4g) {
        this.model = model;
        this.isAvailable = isAvailable;
        this.bookedTime = bookedTime;
        this.bookedBy = bookedBy;
        this.technology = technology;
        this.bands2g = bands2g;
        this.bands3g = bands3g;
        this.bands4g = bands4g;
    }

    // No-args constructor
    public Phone() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Phone phone = (Phone) o;
        return isAvailable == phone.isAvailable &&
                Objects.equals(model, phone.model) &&
                Objects.equals(bookedTime, phone.bookedTime) &&
                Objects.equals(bookedBy, phone.bookedBy) &&
                Objects.equals(technology, phone.technology) &&
                Objects.equals(bands2g, phone.bands2g) &&
                Objects.equals(bands3g, phone.bands3g) &&
                Objects.equals(bands4g, phone.bands4g);
    }

    @Override
    public int hashCode() {
        return Objects.hash(model, isAvailable, bookedTime, bookedBy, technology, bands2g, bands3g, bands4g);
    }

    @Override
    public String toString() {
        return "Phone{" +
                "model='" + model + '\'' +
                ", isAvailable=" + isAvailable +
                ", bookedTime=" + bookedTime +
                ", bookedBy='" + bookedBy + '\'' +
                ", technology='" + technology + '\'' +
                ", bands2g='" + bands2g + '\'' +
                ", bands3g='" + bands3g + '\'' +
                ", bands4g='" + bands4g + '\'' +
                '}';
    }

    // Implement getters and setters here
    // ...
}
