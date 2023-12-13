package com.example.phonebooking.service;

import com.example.phonebooking.client.FonoapiClient;
import com.example.phonebooking.exception.PhoneNotAvailableException;
import com.example.phonebooking.exception.PhoneNotFoundException;
import com.example.phonebooking.model.Phone;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class PhoneServiceImpl implements PhoneService {
    private final ConcurrentHashMap<String, Phone> phones = new ConcurrentHashMap<>();

    private FonoapiClient fonoapiClient = new FonoapiClient();

    public PhoneServiceImpl() {
        phones.put("Samsung Galaxy S9", new Phone("Samsung Galaxy S9", true, null, null, "4G", "GSM", "HSPA", "LTE"));
        phones.put("Samsung Galaxy S8_1", new Phone("Samsung Galaxy S8", true, null, null, "4G", "GSM", "HSPA", "LTE"));
        phones.put("Samsung Galaxy S8_2", new Phone("Samsung Galaxy S8", true, null, null, "4G", "GSM", "HSPA", "LTE"));
        phones.put("Motorola Nexus 6", new Phone("Samsung Galaxy S8", true, null, null, "4G", "GSM", "HSPA", "LTE"));
        phones.put("Oneplus 9", new Phone("Samsung Galaxy S8", true, null, null, "4G", "GSM", "HSPA", "LTE"));
        phones.put("Apple iPhone 13", new Phone("Samsung Galaxy S8", true, null, null, "4G", "GSM", "HSPA", "LTE"));
        phones.put("Apple iPhone 12", new Phone("Samsung Galaxy S8", true, null, null, "4G", "GSM", "HSPA", "LTE"));
        phones.put("Apple iPhone 11", new Phone("Samsung Galaxy S8", true, null, null, "4G", "GSM", "HSPA", "LTE"));
        phones.put("iPhone X", new Phone("Samsung Galaxy S8", true, null, null, "4G", "GSM", "HSPA", "LTE"));
        phones.put("Nokia 3310", new Phone("Samsung Galaxy S8", true, null, null, "2G", "GSM", "HSPA", "LTE"));
    }

    @Override
    public Phone bookPhone(String model, String user) {
        synchronized (this) {
            Phone phone = phones.get(model);
            if (phone == null) {
                throw new PhoneNotFoundException("Phone not found.");
            }
            if (!phone.isAvailable()) {
                throw new PhoneNotAvailableException("Phone is already booked.");
            }
            Phone phoneResult = new Phone(model, false, LocalDateTime.now(), user,
                    phone.technology(), phone.bands2g(), phone.bands3g(), phone.bands4g());
            phones.put(model, phoneResult);
            return phoneResult;

        }
    }

    @Override
    public Phone returnPhone(String model) {
        synchronized (this) {
            Phone phone = phones.get(model);
            if (phone == null || phone.isAvailable()) {
                throw new PhoneNotAvailableException("Phone is not booked or does not exist.");
            }
            Phone resultPhone =  new Phone(model, true, null, null,
                    phone.technology(), phone.bands2g(), phone.bands3g(), phone.bands4g());
            phones.put(model,resultPhone);
            return resultPhone;
        }
    }

    @Override
    public Phone getPhoneDetails(String model) {
        Phone phone = phones.get(model);
        if (phone == null) {
            throw new PhoneNotFoundException("Phone not found.");
        }
        // Update phone specs per fonapi
        phone = fonoapiClient.updatePhoneSpecs(phone);
        return phone;
    }
}