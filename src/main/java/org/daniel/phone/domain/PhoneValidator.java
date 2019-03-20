package org.daniel.phone.domain;

import org.daniel.phone.dto.PhoneValidatorDto;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "phone_validator")
public class PhoneValidator implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "country_code_fk", length = 4)
    private String countryCodeFk;

    @Column(name = "regex", length = 46)
    private String regex;


    @OneToOne
    @JoinColumn(name = "country_code_fk", referencedColumnName = "country_code", insertable = false, updatable = false)
    private Country country;

    public Country getCountry() {
        return country;
    }

    public PhoneValidator() {
    }


    public static Builder empty() {
        return new Builder();
    }

    private PhoneValidator(Builder builder) {
        this.id = builder.id;
        this.countryCodeFk = builder.countryCodeFk;
        this.regex = builder.regex;
    }

    public Long getId() {
        return id;
    }

    public String getCountryCodeFk() {
        return countryCodeFk;
    }

    public String getRegex() {
        return regex;
    }


    public static Builder from(PhoneValidatorDto phoneValidator) {
        return new Builder(phoneValidator);
    }

    public static Builder from(PhoneValidator phoneValidator) {
        return new Builder(phoneValidator);
    }
    public static class Builder {

        private Long id;
        private String countryCodeFk;
        private String regex;

        private Builder() {
        }

        private Builder(PhoneValidator phoneValidator) {
            this.id = phoneValidator.id;
            this.countryCodeFk = phoneValidator.countryCodeFk;
            this.regex = phoneValidator.regex;
        }

        private Builder(PhoneValidatorDto phoneValidator) {
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

        public PhoneValidator build() {
            return new PhoneValidator(this);
        }
    }
}
