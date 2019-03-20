package org.daniel.phone.service.Country;

import org.daniel.phone.dto.CountryDto;
import org.daniel.phone.domain.Country;
import org.daniel.phone.domain.PhoneValidator;
import org.daniel.phone.dto.PhoneValidatorDto;
import org.daniel.phone.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;


    @Autowired
    public CountryServiceImpl(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public CountryOperationResult<List<CountryDto>> getAll() {
        CountryOperationResult.Builder<List<CountryDto>> operationResult = CountryOperationResult.empty();

        List<Country> countries = countryRepository.findAll();

        List<CountryDto> countriesDto = new ArrayList<>();

        for (Country c : countries) {

            countriesDto.add(CountryDto.from(c).build());
        }

        operationResult.result(countriesDto);
        return operationResult.build();
    }

    public CountryOperationResult<CountryDto> newCountry(CountryDto country) {
        CountryOperationResult.Builder<CountryDto> operationResult = CountryOperationResult.empty();

        if (Objects.isNull(country)) {
            operationResult.addError(CountryOperationResult.Error.INPUT_IS_NULL);
            return operationResult.build();
        }

        Country newCountry = Country.from(country).build();
        Country saved = this.countryRepository.save(newCountry);

        operationResult.result(CountryDto.from(saved).build());
        return operationResult.build();
    }

    public CountryOperationResult<CountryDto> updateCountryWithCountryCode(String countryCode, CountryDto country) {
        CountryOperationResult.Builder<CountryDto> operationResult = CountryOperationResult.empty();

        Country countryFound = this.countryRepository.findCountryByCountryCode(countryCode);


        countryFound.setName(country.getName());
        countryFound.setCountryCode(country.getCountryCode());
        Country countryUpdated = this.countryRepository.save(countryFound);

        if (Objects.isNull(countryUpdated)) {
            operationResult.addError(CountryOperationResult.Error.COUNTRY_NOT_UPDATED);
            operationResult.result(CountryDto.empty().build());
        }

        return operationResult.result(CountryDto.from(countryUpdated).build()).build();


    }

    public CountryOperationResult<CountryDto> findCountryById(Long id) {
        CountryOperationResult.Builder<CountryDto> operationResult = CountryOperationResult.empty();

        Optional<Country> countryOptional = this.countryRepository.findById(id);
        if (!countryOptional.isPresent()) {
            return operationResult.addError(CountryOperationResult.Error.COUNTRY_NOT_FOUND).build();
        }
        Country country = countryOptional.get();
        CountryDto countryDto = CountryDto.from(country).build();

        return operationResult.result(countryDto).build();
    }


    public CountryOperationResult<PhoneValidatorDto> phoneValidatorOfCountryWithCountryCode(String countryCode) {

        if (Objects.isNull(countryCode)) {
            throw new IllegalArgumentException("countryCode is null");
        }

        CountryOperationResult.Builder<PhoneValidatorDto> operationResult = CountryOperationResult.empty();
        PhoneValidator phoneValidator = PhoneValidator.empty().build();

        Country country = this.countryRepository.findCountryByCountryCode(countryCode);

        if (Objects.isNull(country)) {
            return operationResult.addError(CountryOperationResult.Error.COUNTRY_NOT_FOUND).build();
        }

        phoneValidator = country.getPhoneValidator();

        if(Objects.isNull(phoneValidator)){
            return operationResult.addError(CountryOperationResult.Error.COUNTRY_HAS_NO_PHONE_VALIDATOR).build();
        }

        operationResult.result(PhoneValidatorDto.from(phoneValidator).build());
        return operationResult.build();
    }


}
