package org.daniel.phone.dto;

import org.daniel.phone.domain.PhoneNumber;

import java.util.ArrayList;
import java.util.List;

public class PhoneNumberDto {

    private Long id;
    private String countryCode;
    private String number;
    private List<CountryDto> countries = new ArrayList<>();

    private PhoneNumberDto(){}

    private PhoneNumberDto(Builder builder) {
        this.countryCode = builder.countryCode;
        this.number = builder.number;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Long getId() {
        return id;
    }

    public static Builder from(PhoneNumber phoneNumber) {
        return new Builder(phoneNumber);
    }

    public static Builder empty() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String countryCode;
        private String number;
        private List<CountryDto> countries = new ArrayList<>();


        private Builder() {
        }

        private Builder(String countryCode, String number) {
            this.countryCode = countryCode;
            this.number = number;

        }

        private Builder(PhoneNumber phoneNumber) {
            this.countryCode = phoneNumber.getCountryCode();
            this.number = phoneNumber.getNumber();

        }

        public Builder countryCode(String countryCode) {
            this.countryCode = countryCode;
            return this;
        }

        public Builder number(String number) {
            this.number = number;
            return this;
        }

        public PhoneNumberDto build() {
            return new PhoneNumberDto(this);
        }
    }
}
