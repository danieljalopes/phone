package org.daniel.phone.controller.api;

import org.daniel.phone.dto.CountryDto;
import org.daniel.phone.dto.PhoneValidatorDto;
import org.daniel.phone.service.Country.CountryOperationResult;
import org.daniel.phone.service.Country.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController("apiCountryController")
@RequestMapping("api")
public class CountryController {

    private final CountryService countryService;

    @Autowired
    CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping("/country")
    public ResponseEntity getAll() {
        CountryOperationResult<List<CountryDto>> operationResult = countryService.getAll();


        if (operationResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(operationResult.getErrors().get(0).name());
        }


        return ResponseEntity.ok(operationResult.getResult());
    }

    @PostMapping("/country")
    public ResponseEntity newCountry(@Valid @RequestBody CountryDto country) {
        if (Objects.isNull(country)) {
            return ResponseEntity.badRequest().build();
        }
        CountryOperationResult<CountryDto> operationResult = CountryOperationResult.empty().build();
        CountryDto savedCountry = CountryDto.empty().build();

        try {
            operationResult = this.countryService.newCountry(country);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        if (operationResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(operationResult.getErrors().get(0));
        }

        savedCountry = operationResult.getResult();
        return ResponseEntity.ok(savedCountry);
    }

    @PutMapping("/country/country-code/{countryCode}")
    public ResponseEntity updateCountryWithCountryCode(@PathVariable("countryCode") String countryCode, @RequestBody CountryDto country) {
        if (Objects.isNull(countryCode) || Objects.isNull(country)) {
            return ResponseEntity.badRequest().build();
        }
        CountryOperationResult<CountryDto> operationResult = CountryOperationResult.empty().build();
        CountryDto updatedCountry = CountryDto.empty().build();

        try {
            operationResult = this.countryService.updateCountryWithCountryCode(countryCode, country);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        if (operationResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(operationResult.getErrors().get(0));
        }

        updatedCountry = operationResult.getResult();
        return ResponseEntity.ok(updatedCountry);
    }

    @GetMapping("/country/{id}")
    public ResponseEntity findCountryById(@PathVariable("id") Long id) {
        if (Objects.isNull(id)) {
            return ResponseEntity.badRequest().build();
        }
        CountryOperationResult<CountryDto> operationResult = CountryOperationResult.empty().build();
        CountryDto country = CountryDto.empty().build();

        try {
            operationResult = this.countryService.findCountryById(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        if (Objects.isNull(country)) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        if (operationResult.hasErrors()) {
            if (operationResult.getErrors().get(0) == CountryOperationResult.Error.COUNTRY_NOT_FOUND) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(operationResult.getErrors().toArray());
        }

        country = operationResult.getResult();
        return ResponseEntity.ok(country);
    }

    @GetMapping("/country/{countryCode}/phone-validator")
    public ResponseEntity PhoneValidatorOfCountryWithCountryCode(@PathVariable("countryCode") String countryCode) {
        if (Objects.isNull(countryCode)) {
            return ResponseEntity.badRequest().build();
        }
        CountryOperationResult<PhoneValidatorDto> operationResult = CountryOperationResult.empty().build();

        try {
            operationResult = this.countryService.phoneValidatorOfCountryWithCountryCode(countryCode);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        if(operationResult.hasErrors()){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(operationResult.getErrors().toArray());
        }

        return ResponseEntity.status(HttpStatus.OK).body(operationResult.getResult());
    }
}
