package org.daniel.phone.domain;

import org.daniel.phone.dto.CountryDto;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "country",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "country_code"),
                @UniqueConstraint(columnNames = "name")
        }
)
public class Country implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 24, nullable = false)
    private String name;


    @Column(name = "country_code", length = 4, nullable = false)
    private String countryCode;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "country_code", referencedColumnName = "country_code_fk", insertable = false, updatable = false)
    private PhoneValidator phoneValidator;

    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL)
    private List<PhoneNumber> phoneNumbers = new ArrayList<>();

    public Country() {
    }

    public static Builder from(Country country) {
        return new Builder(country);
    }

    public static Builder from(CountryDto country) {
        return new Builder(country);
    }

    public static Builder empty() {
        return new Builder();
    }

    private Country(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.countryCode = builder.countryCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public PhoneValidator getPhoneValidator() {
        return phoneValidator;
    }

    public void setPhoneValidator(PhoneValidator phoneValidator) {
        this.phoneValidator = phoneValidator;
    }

    public List<PhoneNumber> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(List<PhoneNumber> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public static class Builder {

        private Long id;
        private String name;
        private String countryCode;
        private PhoneValidator phoneValidator;
        private List<PhoneNumber> phoneNumbers = new ArrayList<>();

        private Builder() {
        }

        private Builder(Country country) {
            this.id = country.id;
            this.name = country.name;
            this.countryCode = country.countryCode;
            this.phoneValidator = country.phoneValidator;
            this.phoneNumbers = country.phoneNumbers;
        }

        private Builder(CountryDto country) {
            this.id = country.getId();
            this.name = country.getName();
            this.countryCode = country.getCountryCode();
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }


        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder countryCode(String countryCode) {
            this.countryCode = countryCode;
            return this;
        }

        public Builder phoneValidator(PhoneValidator phoneValidator) {
            this.phoneValidator = phoneValidator;
            return this;
        }


        public Country build() {
            return new Country(this);
        }
    }
}
