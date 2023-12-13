package com.example.phonebooking.controller;

import com.example.phonebooking.model.Phone;
import com.example.phonebooking.service.PhoneService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/phones")
public class PhoneController {
    private final PhoneService phoneService;

    public PhoneController(PhoneService phoneService) {
        this.phoneService = phoneService;
    }

    @PostMapping("/book/{model}")
    public ResponseEntity<String> bookPhone(@PathVariable String model, @RequestParam String user) {
        try {
            phoneService.bookPhone(model, user);
            return ResponseEntity.ok("Phone successfully booked by " + user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error booking phone: " + e.getMessage());
        }
    }

    @PostMapping("/return/{model}")
    public ResponseEntity<String> returnPhone(@PathVariable String model) {
        try {
            phoneService.returnPhone(model);
            return ResponseEntity.ok("Phone successfully returned");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error returning phone: " + e.getMessage());
        }
    }

    @GetMapping("/{model}")
    public ResponseEntity<?> getPhoneDetails(@PathVariable String model) {
        try {
            Phone phone = phoneService.getPhoneDetails(model);
            return ResponseEntity.ok(phone);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}