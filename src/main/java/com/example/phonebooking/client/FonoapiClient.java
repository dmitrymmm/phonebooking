package com.example.phonebooking.client;

import com.example.phonebooking.dto.PhoneSpecsDto;
import com.example.phonebooking.model.Phone;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class FonoapiClient {
    private final RestTemplate restTemplate = new RestTemplate();
    private final String apiUrl = "https://fonoapi.freshpixl.com/v1/getdevice";

    // TODO: Refactoring, extensive testing, improved error handling
    public Phone updatePhoneSpecs(Phone phone) {
        try {
            PhoneSpecsDto specs = restTemplate.getForObject(apiUrl + "?device=" + phone.model(), PhoneSpecsDto.class);
            return new Phone(phone.model(), phone.isAvailable(), phone.bookedTime(), phone.bookedBy(),
                    specs.getTechnology(), specs.getBands2g(), specs.getBands3g(), specs.getBands4g());
        } catch (Exception e) {
            return phone; // Return the original phone on failure at this stage
        }
    }
}
