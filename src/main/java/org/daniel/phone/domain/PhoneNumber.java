package org.daniel.phone.domain;


import org.daniel.phone.dto.PhoneNumberDto;

import javax.persistence.*;
import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Entity
@Table(name = "phone_number")
public class PhoneNumber implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "number", length = 16)
    private String number;

    @Column(name = "country_code_fk", length = 4, insertable = false, updatable = false)
    private String countryCode;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_code_fk", referencedColumnName = "country_code")
    private Country country;

    private PhoneNumber() {
    }

    private PhoneNumber(Builder builder) {
        this.id = builder.id;
        this.number = builder.number;
        this.countryCode = builder.countryCode;
    }


    public Long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public void updateFrom(PhoneNumberDto phoneNumber) {
        this.countryCode = phoneNumber.getCountryCode();
        this.number = phoneNumber.getNumber();
    }

    public boolean isValid() {
        Pattern pattern = Pattern.compile(this.country.getPhoneValidator().getRegex());
        Matcher matcher = pattern.matcher(this.countryCode + this.number);
        return matcher.matches();
    }

    public static Builder from(PhoneNumber phoneNumber) {
        return new Builder(phoneNumber);
    }

    public static Builder from(PhoneNumberDto phoneNumberDto) {
        return new Builder(phoneNumberDto);
    }

    public static Builder empty() {
        return new Builder();
    }

    public static class Builder {

        private Long id;
        private String number;
        private String countryCode;

        public Builder() {

        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder(PhoneNumberDto phoneNumberDto) {
            this.id = phoneNumberDto.getId();
            this.number = phoneNumberDto.getNumber();
            this.countryCode = phoneNumberDto.getCountryCode();
        }

        public Builder(PhoneNumber phoneNumber) {
            this.id = phoneNumber.id;
            this.number = phoneNumber.number;
            this.countryCode = phoneNumber.countryCode;
        }

        public Builder number(String number) {
            this.number = number;
            return this;
        }

        public Builder countryCode(String countryCode) {
            this.countryCode = countryCode;
            return this;
        }

        public PhoneNumber build() {
            return new PhoneNumber(this);
        }
    }

}
