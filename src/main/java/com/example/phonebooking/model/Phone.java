package com.example.phonebooking.model;

import java.time.LocalDateTime;

public record Phone(String model, boolean isAvailable, LocalDateTime bookedTime,
                    String bookedBy, String technology, String bands2g,
                    String bands3g, String bands4g) {
}