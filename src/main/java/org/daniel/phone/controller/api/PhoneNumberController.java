package org.daniel.phone.controller.api;

import org.daniel.phone.dto.PhoneNumberDto;
import org.daniel.phone.domain.PhoneNumber;
import org.daniel.phone.service.OperationResult;
import org.daniel.phone.service.PhoneNumber.PhoneNumberOperationResult;
import org.daniel.phone.service.PhoneNumber.PhoneNumberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@RestController("apiPhoneNumberController")
@RequestMapping("api")
public class PhoneNumberController {

    public final PhoneNumberService phoneNumberService;

    private Logger logger = LoggerFactory.getLogger(PhoneNumberController.class);

    @Autowired
    public PhoneNumberController(PhoneNumberService phoneNumberService) {
        this.phoneNumberService = phoneNumberService;
    }


    @GetMapping("/phone-number")
    public ResponseEntity getAll() {
        OperationResult<List<PhoneNumberDto>> operationResult = PhoneNumberOperationResult.empty().build();

        try {
            operationResult = phoneNumberService.findAll();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        if (operationResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(operationResult.getErrors().toArray());
        }

        return ResponseEntity.ok(operationResult.getResult());
    }

    @PostMapping("/phone-number")
    public ResponseEntity newPhonenumber(@RequestBody PhoneNumberDto phoneNumber) {
        if (Objects.isNull(phoneNumber)) {
            return ResponseEntity.badRequest().build();
        }
        OperationResult<PhoneNumberDto> operationResult = PhoneNumberOperationResult.empty().build();
        PhoneNumber newPhoneNumber = null;
        try {
            operationResult = this.phoneNumberService.createNewPhoneNumber(phoneNumber);

        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        if (operationResult.hasErrors()) {

            if (Arrays.stream(operationResult.getErrors().toArray()).anyMatch(PhoneNumberOperationResult.Error.PHONE_NUMBER_NOT_VALID::equals)) {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
            }

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(operationResult.getErrors().toArray());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(operationResult.getResult());
    }

    @GetMapping("/phone-number/{id}")
    public ResponseEntity findPhoneNumberById(@PathVariable("id") Long id) {
        if (Objects.isNull(id)) {
            return ResponseEntity.badRequest().build();
        }

        OperationResult<PhoneNumberDto> operationResult = PhoneNumberOperationResult.empty().build();
        PhoneNumber phoneNumber = null;
        try {
            operationResult = this.phoneNumberService.findPhoneNumberById(id);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }


        if (operationResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(operationResult.getErrors().toArray());
        }


        return ResponseEntity.status(HttpStatus.FOUND).body(operationResult.getResult());
    }


    @PutMapping("/phone-number/{id}")
    public ResponseEntity updatePhoneNumber(@PathVariable("id") Long id, @RequestBody PhoneNumberDto phoneNumber) {
        if (Objects.isNull(id) || Objects.isNull(phoneNumber)) {
            return ResponseEntity.badRequest().build();
        }
        PhoneNumberOperationResult<PhoneNumberDto> operationResult = PhoneNumberOperationResult.empty().build();
        PhoneNumber updatedPhoneNumber = null;


        try {
            operationResult = this.phoneNumberService.updatePhoneNumberWithId(id, phoneNumber);
        } catch (NullPointerException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        if (operationResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(operationResult.getErrors().toArray());
        }

        return ResponseEntity.status(HttpStatus.OK).body(operationResult.getResult());
    }

}
