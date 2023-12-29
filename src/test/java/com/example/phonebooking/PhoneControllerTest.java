package com.example.phonebooking;

import com.example.phonebooking.controller.PhoneController;
import com.example.phonebooking.model.Phone;
import com.example.phonebooking.service.PhoneService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PhoneController.class)
class PhoneControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PhoneService phoneService;

    @BeforeEach
    void setUp() {
        Phone phone1 = new Phone("Samsung Galaxy S9", true, null, null, "4G", "GSM", "HSPA", "LTE");
        Phone phone2 = new Phone("iPhone X", false, null, "user1", "4G", "GSM", "HSPA", "LTE");
        List<Phone> allPhones = Arrays.asList(phone1, phone2);
        List<Phone> bookedPhones = Arrays.asList(phone2);
        List<Phone> availablePhones = Arrays.asList(phone1);

        when(phoneService.getAllPhones()).thenReturn(allPhones);
        when(phoneService.getBookedPhones()).thenReturn(bookedPhones);
        when(phoneService.getAvailablePhones()).thenReturn(availablePhones);

    }

    @Test
    void bookPhoneTest() throws Exception {
        String model = "Samsung Galaxy S9";
        String user = "user1";

        mockMvc.perform(post("/phones/book/{model}", model)
                        .param("user", user)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("successfully booked")));

        verify(phoneService).bookPhone(model, user);
    }

    @Test
    void returnPhoneTest() throws Exception {
        String model = "Samsung Galaxy S9";
        String user = "user1";

        mockMvc.perform(post("/phones/return/{model}", model)
                        .param("user", user)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("successfully returned")));

        verify(phoneService).returnPhone(model, user);
    }

    @Test
    void shouldListAllPhones() throws Exception {
        mockMvc.perform(get("/phones/all").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void shouldListBookedPhones() throws Exception {
        mockMvc.perform(get("/phones/booked").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].model", is("iPhone X")));
    }

    @Test
    void shouldListAvailablePhones() throws Exception {
        mockMvc.perform(get("/phones/available").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].model", is("Samsung Galaxy S9")));
    }
}