package com.example.phonebooking.exception;

public class PhoneNotAvailableException extends RuntimeException {
    public PhoneNotAvailableException(String message) {
        super(message);
    }
}