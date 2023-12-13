package com.example.phonebooking.service;

import com.example.phonebooking.model.Phone;

public interface PhoneService {
    Phone bookPhone(String model, String user);
    Phone returnPhone(String model);
    Phone getPhoneDetails(String model);
}