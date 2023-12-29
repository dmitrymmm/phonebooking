package com.example.phonebooking.controller;

import com.example.phonebooking.model.Phone;
import com.example.phonebooking.service.PhoneService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/phones")
public class PhoneController {
    private final PhoneService phoneService;

    public PhoneController(PhoneService phoneService) {
        this.phoneService = phoneService;
    }

    @RequestMapping("/book/{model}")
    public ResponseEntity<String> bookPhone(@PathVariable String model, @RequestParam String user) {
        try {
            phoneService.bookPhone(model, user);
            return ResponseEntity.ok("Phone successfully booked by " + user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error booking phone: " + e.getMessage());
        }
    }

    @RequestMapping("/return/{model}")
    public ResponseEntity<String> returnPhone(@PathVariable String model, @RequestParam String user) {
        try {
            phoneService.returnPhone(model, user);
            return ResponseEntity.ok("Phone successfully returned by " + user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error returning phone: " + e.getMessage());
        }
    }

    @GetMapping("/{model}")
    //TOTO: This is to be enhanced
    public ResponseEntity<?> getPhoneDetails(@PathVariable String model) {
        try {
            Phone phoneForModel = new Phone();
            phoneForModel.setModel(model);
            Phone phone = phoneService.getPhoneDetails(phoneForModel);
            return ResponseEntity.ok(phone);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Phone>> getAllPhones() {
        List<Phone> phones = phoneService.getAllPhones();
        return ResponseEntity.ok(phones);
    }

    @GetMapping("/booked")
    public ResponseEntity<List<Phone>> getBookedPhones() {
        List<Phone> phones = phoneService.getBookedPhones();
        return ResponseEntity.ok(phones);
    }

    @GetMapping("/available")
    public ResponseEntity<List<Phone>> getAvailablePhones() {
        List<Phone> phones = phoneService.getAvailablePhones();
        return ResponseEntity.ok(phones);
    }
}