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
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

        mockMvc.perform(post("/phones/return/{model}", model)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("successfully returned")));

        verify(phoneService).returnPhone(model);
    }

    @Test
    void getPhoneDetailsTest() throws Exception {
        String model = "Samsung Galaxy S9";
        Phone mockPhone = new Phone(model, true, null, null, "4G", "GSM", "HSPA", "LTE");

        when(phoneService.getPhoneDetails(model)).thenReturn(mockPhone);

        mockMvc.perform(get("/phones/{model}", model)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.model", is(model)));

        verify(phoneService).getPhoneDetails(model);
    }
}