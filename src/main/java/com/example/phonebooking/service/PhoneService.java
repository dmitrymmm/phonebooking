package com.example.phonebooking.service;

import com.example.phonebooking.model.Phone;

import java.util.List;

public interface PhoneService {
    Phone bookPhone(String model, String user);
    Phone returnPhone(String model, String user);
    Phone getPhoneDetails(String model);
    List<Phone> getAllPhones();
    List<Phone> getBookedPhones();
    List<Phone> getAvailablePhones();
}