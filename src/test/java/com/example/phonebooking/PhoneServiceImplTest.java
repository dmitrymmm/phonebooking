package com.example.phonebooking;

import com.example.phonebooking.model.Phone;
import com.example.phonebooking.service.PhoneServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PhoneServiceImplTest {

    //private ConcurrentHashMap<String, Phone> phonesTestData = new ConcurrentHashMap<>();

    private PhoneServiceImpl phoneService;

    @BeforeEach
    void setUp() {
        phoneService = new PhoneServiceImpl();
        //phonesTestData.clear();
        //Phone samplePhone = new Phone("Samsung Galaxy S9", true, null, null, "4G", "GSM", "HSPA", "LTE");
        //phonesTestData.put("Samsung Galaxy S9", samplePhone);
    }

    @Test
    void bookPhoneSuccessfully() {
        String model = "Samsung Galaxy S9";
        String user = "user1";

        Phone bookedPhone = phoneService.bookPhone(model, user);

        assertFalse(bookedPhone.isAvailable());
        assertEquals(user, bookedPhone.bookedBy());
    }

    @Test
    void bookPhoneFailureAlreadyBooked() {
        String model = "Samsung Galaxy S9";
        String user = "user1";

        phoneService.bookPhone(model, user);
        assertThrows(RuntimeException.class, () -> phoneService.bookPhone(model, user));
    }

    @Test
    void returnPhoneSuccessfully() {
        String model = "Samsung Galaxy S9";
        String user = "user1";

        phoneService.bookPhone(model, user);
        Phone returnedPhone = phoneService.returnPhone(model);

        assertTrue(returnedPhone.isAvailable());
        assertNull(returnedPhone.bookedBy());
        assertNull(returnedPhone.bookedTime());
    }

    @Test
    void getPhoneDetailsSuccessfully() {
        String model = "Samsung Galaxy S9";
        Phone phone = phoneService.getPhoneDetails(model);

        assertNotNull(phone);
        assertEquals("Samsung Galaxy S9", phone.model());
    }
}
