package com.example.phonebooking.client;

import com.example.phonebooking.dto.PhoneSpecsDto;
import com.example.phonebooking.model.Phone;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class FonoApiClient {
    private final RestTemplate restTemplate = new RestTemplate();
    private final String apiUrl = "https://fonoapi.freshpixl.com/v1/getdevice";

    // TODO: Refactoring, extensive testing, improved error handling
    public Phone updatePhoneSpecs(Phone phone) {
        try {
            PhoneSpecsDto specs = restTemplate.getForObject(apiUrl + "?device=" + phone.getModel(), PhoneSpecsDto.class);
            phone.setTechnology(specs.getTechnology());
            phone.setBands2g(specs.getBands2g());
            phone.setBands3g(specs.getBands3g());
            phone.setBands4g(specs.getBands4g());
            return phone;
        } catch (Exception e) {
            return phone; // Return the original phone on failure at this stage
        }
    }
}
