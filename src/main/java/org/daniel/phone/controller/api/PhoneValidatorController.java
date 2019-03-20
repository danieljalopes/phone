package org.daniel.phone.controller.api;

import org.daniel.phone.dto.CountryDto;
import org.daniel.phone.domain.Country;
import org.daniel.phone.dto.PhoneValidatorDto;
import org.daniel.phone.service.PhoneValidator.PhoneValidatorOperationResult;
import org.daniel.phone.service.PhoneValidator.PhoneValidatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@RestController("apiPhoneValidatorController")
@RequestMapping("api")
public class PhoneValidatorController {


    private final PhoneValidatorService phoneValidatorService;

    Logger logger = LoggerFactory.getLogger(PhoneValidatorController.class);

    @Autowired
    public PhoneValidatorController(PhoneValidatorService phoneValidatorService) {
        this.phoneValidatorService = phoneValidatorService;
    }


    @GetMapping("/phone-validator")
    public ResponseEntity getAll() {
        PhoneValidatorOperationResult<List<PhoneValidatorDto>> operationResult = PhoneValidatorOperationResult.empty().build();
        try {
            operationResult = phoneValidatorService.findAll();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        if (operationResult.hasErrors()) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(operationResult.getErrors().toArray());
        }

        return ResponseEntity.ok(operationResult.getResult());
    }

    @PostMapping("/phone-validator")
    public ResponseEntity newPhoneValidator(@RequestBody PhoneValidatorDto phoneValidator) {
        if (Objects.isNull(phoneValidator)) {
            return ResponseEntity.badRequest().build();
        }
        PhoneValidatorOperationResult operationResult = PhoneValidatorOperationResult.empty().build();

        try {
            operationResult = this.phoneValidatorService.createNewPhoneValidator(phoneValidator);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }


        if (operationResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(operationResult.getErrors().toArray());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(operationResult.getResult());
    }

    @GetMapping("/phone-validator/{id}")
    public ResponseEntity findPhoneValidatorById(@PathVariable("id") Long id) {
        if (Objects.isNull(id)) {
            return ResponseEntity.badRequest().build();
        }

        PhoneValidatorOperationResult operationResult = PhoneValidatorOperationResult.empty().build();

        try {
            operationResult = this.phoneValidatorService.findPhoneValidatorById(id);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }


        if (Objects.isNull(operationResult)) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        if (operationResult.hasErrors()) {

            if (Arrays.stream(operationResult.getErrors().toArray())
                    .anyMatch(PhoneValidatorOperationResult.Error.PHONE_VALIDATOR_NOT_FOUND::equals)) {

                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(operationResult.getErrors().toArray());
        }
        return ResponseEntity.status(HttpStatus.FOUND).body(operationResult.getResult());
    }

    @PutMapping("/phone-validator/{id}")
    public ResponseEntity updatePhoneValidator(@PathVariable("id") Long id, @RequestBody PhoneValidatorDto phoneValidator) {
        if (Objects.isNull(id) || Objects.isNull(phoneValidator)) {
            return ResponseEntity.badRequest().build();
        }

        PhoneValidatorOperationResult operationResult = PhoneValidatorOperationResult.empty().build();


        try {
            operationResult = this.phoneValidatorService.updatePhoneValidatorWithId(id, phoneValidator);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        if (operationResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(operationResult.getErrors().toArray());
        }

        return ResponseEntity.status(HttpStatus.OK).body(operationResult.getResult());
    }

    @GetMapping("/phone-validator/{id}/country")
    public ResponseEntity PhoneValidatorCountry(@PathVariable("id") Long id) {
        if (Objects.isNull(id)) {
            return ResponseEntity.badRequest().build();
        }

        Country country = this.phoneValidatorService.countryOfPhoneValidatorOfId(id);

        if (Objects.isNull(country)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(CountryDto.from(country).build());
    }
}
