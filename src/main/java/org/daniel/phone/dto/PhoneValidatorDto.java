package org.daniel.phone.dto;

import org.daniel.phone.domain.PhoneValidator;

public class PhoneValidatorDto {


    private Long id;
    private String countryCodeFk;
    private String regex;

    public Long getId() {
        return id;
    }

    public String getCountryCodeFk() {
        return countryCodeFk;
    }

    public String getRegex() {
        return regex;
    }

    PhoneValidatorDto(){

    }
    private PhoneValidatorDto(Builder builder) {
        this.id = builder.id;
        this.countryCodeFk = builder.countryCodeFk;
        this.regex = builder.regex;
    }


    public static Builder from(PhoneValidator phoneValidator) {
        return new Builder(phoneValidator);
    }

    public static Builder empty() {
        return new Builder();
    }


    public static class Builder {
        private Long id;
        private String countryCodeFk;
        private String regex;

        private Builder() {
        }

        private Builder(PhoneValidator phoneValidator) {
            this.id = phoneValidator.getId();
            this.countryCodeFk = phoneValidator.getCountryCodeFk();
            this.regex = phoneValidator.getRegex();
        }


        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder countryCodeFk(String countryCodeFk) {
            this.countryCodeFk = countryCodeFk;
            return this;
        }

        public Builder regex(String regex) {
            this.regex = regex;
            return this;
        }

        public PhoneValidatorDto build() {
            return new PhoneValidatorDto(this);
        }
    }

}
