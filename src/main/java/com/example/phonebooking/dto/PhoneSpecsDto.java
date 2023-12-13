package com.example.phonebooking.dto;

public class PhoneSpecsDto {
    private String technology;
    private String bands2g;
    private String bands3g;
    private String bands4g;
    // Other fields as per API response

    // Constructors
    public PhoneSpecsDto() {
    }

    public PhoneSpecsDto(String technology, String bands2g, String bands3g, String bands4g) {
        this.technology = technology;
        this.bands2g = bands2g;
        this.bands3g = bands3g;
        this.bands4g = bands4g;
    }

    // Getters and Setters
    public String getTechnology() {
        return technology;
    }

    public void setTechnology(String technology) {
        this.technology = technology;
    }

    public String getBands2g() {
        return bands2g;
    }

    public void setBands2g(String bands2g) {
        this.bands2g = bands2g;
    }

    public String getBands3g() {
        return bands3g;
    }

    public void setBands3g(String bands3g) {
        this.bands3g = bands3g;
    }

    public String getBands4g() {
        return bands4g;
    }

    public void setBands4g(String bands4g) {
        this.bands4g = bands4g;
    }

}
