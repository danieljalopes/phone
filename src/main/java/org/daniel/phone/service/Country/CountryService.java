package org.daniel.phone.service.Country;

import org.daniel.phone.dto.CountryDto;
import org.daniel.phone.dto.PhoneValidatorDto;

import java.util.List;

public interface CountryService {

    /**
     * Retrieves all Countries
     * @return
     */
    public CountryOperationResult<List<CountryDto>> getAll();

    /**
     * Creates a new Country
     * @param country
     * @return
     */
    public CountryOperationResult<CountryDto> newCountry(CountryDto country);


    /**
     * Update an existing country given its country code
     * @param countryCode
     * @param country
     * @return
     */
    CountryOperationResult<CountryDto> updateCountryWithCountryCode(String countryCode, CountryDto country);

    /**
     * Finds a Country given with an id
     * @param id
     * @return
     */
    CountryOperationResult<CountryDto> findCountryById(Long id);


    /**
     * Finds PhoneValidators of a Country with a given country code
     * @param countryCode
     * @return
     */
    CountryOperationResult<PhoneValidatorDto> phoneValidatorOfCountryWithCountryCode(String countryCode);
}
