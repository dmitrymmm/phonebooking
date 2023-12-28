package com.example.phonebooking;

import com.example.phonebooking.model.Phone;
import com.example.phonebooking.service.PhoneServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PhoneServiceImplTest {

    private PhoneServiceImpl phoneService;

    @BeforeEach
    void setUp() {
        phoneService = new PhoneServiceImpl();
    }

    @Test
    void bookPhoneSuccessfullyS9() {
        String model = "Samsung Galaxy S9";
        String user = "user1";

        Phone bookedPhone = phoneService.bookPhone(model, user);

        assertFalse(bookedPhone.isAvailable());
        assertEquals(user, bookedPhone.getBookedBy());
    }

    @Test
    void bookPhoneFailureAlreadyBookedS9() {
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
        Phone returnedPhone = phoneService.returnPhone(model, user);

        assertTrue(returnedPhone.isAvailable());
        assertNull(returnedPhone.getBookedBy());
        assertNull(returnedPhone.getBookedTime());
    }

    @Test
    void getPhoneDetailsSuccessfully() {
        String model = "Samsung Galaxy S9";
        Phone phone = phoneService.getPhoneDetails(model);

        assertNotNull(phone);
        assertEquals("Samsung Galaxy S9", phone.getModel());
    }

    @Test
    void bookPhoneSuccessfullyS8() {
        String model = "Samsung Galaxy S8";
        String user = "user1";

        Phone bookedPhone1 = phoneService.bookPhone(model, user);
        assertFalse(bookedPhone1.isAvailable());
        assertEquals(user, bookedPhone1.getBookedBy());

        Phone bookedPhone2 = phoneService.bookPhone(model, user);
        assertFalse(bookedPhone2.isAvailable());
        assertEquals(user, bookedPhone2.getBookedBy());
    }

    @Test
    void getAllPhones() {
        List<Phone> allPhones = phoneService.getAllPhones();
        assertNotNull(allPhones);
    }

    @Test
    void getBookedPhones() {
        List<Phone> bookedPhones = phoneService.getBookedPhones();
    }

    @Test
    void getAvailablePhones() {
        List<Phone> availablePhones = phoneService.getAvailablePhones();
    }
}
