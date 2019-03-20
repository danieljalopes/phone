package org.daniel.phone.dto;

import org.daniel.phone.domain.Country;

public class CountryDto {
    private Long id;
    private String countryCode;
    private String name;


    CountryDto() {

    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private CountryDto(Builder builder) {
        this.id = builder.id;
        this.countryCode = builder.countryCode;
        this.name = builder.name;


    }


    public static Builder from(Country country) {
        return new CountryDto.Builder(country);
    }

    public static Builder empty(){
        return new Builder();
    }
    public static class Builder {
        private Long id;
        private String countryCode;
        private String name;

        private Builder(){

        }
        private Builder(Country country) {
            this.id = country.getId();
            this.countryCode = country.getCountryCode();
            this.name = country.getName();

        }

        private Builder(CountryDto country) {
            this.id = country.id;
            this.countryCode = country.countryCode;
            this.name = country.name;

        }

        private Builder(String countryCode, String name) {
            this.countryCode = countryCode;
            this.name = name;
        }

        public Builder code(String code) {
            this.countryCode = code;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }


        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public CountryDto build() {
            return new CountryDto(this);
        }
    }
}
