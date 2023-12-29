package com.example.phonebooking.service;

import com.example.phonebooking.client.FonoApiClient;
import com.example.phonebooking.exception.PhoneNotFoundException;
import com.example.phonebooking.model.Phone;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class PhoneServiceImpl implements PhoneService {
    private final ConcurrentHashMap<String, List<Phone>> phoneInventory = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Lock> modelLocks = new ConcurrentHashMap<>();

    private final FonoApiClient fonoApiClient = new FonoApiClient();

    public PhoneServiceImpl() {
        addPhoneModel("Samsung Galaxy S9", new Phone("Samsung Galaxy S9", true, null, null, "4G", "GSM", "HSPA", "LTE"));
        addPhoneModel("Samsung Galaxy S8", new Phone("Samsung Galaxy S8", true, null, null, "4G", "GSM", "HSPA", "LTE"));
        addPhoneModel("Samsung Galaxy S8", new Phone("Samsung Galaxy S8", true, null, null, "4G", "GSM", "HSPA", "LTE"));
        addPhoneModel("Motorola Nexus 6", new Phone("Motorola Nexus 6", true, null, null, "4G", "GSM", "HSPA", "LTE"));
        addPhoneModel("Oneplus 9", new Phone("Oneplus 9", true, null, null, "4G", "GSM", "HSPA", "LTE"));
        addPhoneModel("Apple iPhone 13", new Phone("Apple iPhone 13", true, null, null, "4G", "GSM", "HSPA", "LTE"));
        addPhoneModel("Apple iPhone 12", new Phone("Apple iPhone 12", true, null, null, "4G", "GSM", "HSPA", "LTE"));
        addPhoneModel("Apple iPhone 11", new Phone("Apple iPhone 11", true, null, null, "4G", "GSM", "HSPA", "LTE"));
        addPhoneModel("iPhone X", new Phone("iPhone X", true, null, null, "4G", "GSM", "HSPA", "LTE"));
        addPhoneModel("Nokia 3310", new Phone("Nokia 3310", true, null, null, "2G", "GSM", "HSPA", "LTE"));
    }

    private void addPhoneModel(String model, Phone phone) {
        phoneInventory.computeIfAbsent(model, k -> new ArrayList<>()).add(phone);
        modelLocks.computeIfAbsent(model, k -> new ReentrantLock());
    }


    @Override
    public Phone bookPhone(String model, String user) {
        Lock lock = modelLocks.computeIfAbsent(model, k -> new ReentrantLock());
        lock.lock();
        try {
            List<Phone> phones = phoneInventory.get(model);
            if (phones == null || phones.isEmpty()) {
                throw new RuntimeException("No phones available for this model.");
            }
            Phone phone = phones.stream().filter(Phone::isAvailable).findFirst()
                    .orElseThrow(() -> new RuntimeException("No available phones for this model."));
            phone.setAvailable(false);
            phone.setBookedBy(user);
            phone.setBookedTime(LocalDateTime.now());
            // Update phone specs upon booking
            fonoApiClient.updatePhoneSpecs(phone);
            return phone;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public Phone returnPhone(String model, String user) {
        Lock lock = modelLocks.computeIfAbsent(model, k -> new ReentrantLock());
        lock.lock();
        try {
            List<Phone> phones = phoneInventory.get(model);
            if (phones == null) {
                throw new RuntimeException("Phone model not found.");
            }
            Phone phone = phones.stream()
                    .filter(p -> !p.isAvailable() && p.getModel().equals(model)
                    && p.getBookedBy().equals(user))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("No booked phones for this model and user."));
            phone.setAvailable(true);
            phone.setBookedBy(null);
            phone.setBookedTime(null);
            return phone;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public List<Phone> getAllPhones() {
        return collectPhonesUnderLock(phone -> true);
    }

    @Override
    public List<Phone> getBookedPhones() {
        return collectPhonesUnderLock(phone -> !phone.isAvailable());
    }

    @Override
    public List<Phone> getAvailablePhones() {
        return collectPhonesUnderLock(phone -> phone.isAvailable());
    }

    private List<Phone> collectPhonesUnderLock(Predicate<Phone> filterCondition) {
        List<Phone> result = new ArrayList<>();
        phoneInventory.forEach((model, phones) -> {
            Lock lock = modelLocks.get(model);
            lock.lock();
            try {
                result.addAll(phones.stream().filter(filterCondition).collect(Collectors.toList()));
            } finally {
                lock.unlock();
            }
        });
        return result;
    }

    @Override
    public Phone getPhoneDetails(Phone phone) {
        fonoApiClient.updatePhoneSpecs(phone);
        return phone;
    }
}
