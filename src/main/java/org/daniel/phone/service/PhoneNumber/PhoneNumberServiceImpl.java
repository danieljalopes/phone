package org.daniel.phone.service.PhoneNumber;

import org.daniel.phone.domain.Country;
import org.daniel.phone.dto.PhoneNumberDto;
import org.daniel.phone.domain.PhoneNumber;
import org.daniel.phone.repository.CountryRepository;
import org.daniel.phone.repository.PhoneNumberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PhoneNumberServiceImpl implements PhoneNumberService {

    public final PhoneNumberRepository phoneNumberRepository;
    public final CountryRepository countryRepository;

    @Autowired
    public PhoneNumberServiceImpl(PhoneNumberRepository phoneNumberRepository, CountryRepository countryRepository) {
        this.phoneNumberRepository = phoneNumberRepository;
        this.countryRepository = countryRepository;
    }

    @Override
    public PhoneNumberOperationResult<List<PhoneNumberDto>> findAll() {
        List<PhoneNumberDto> phoneNumberDtos = new ArrayList<>();
        PhoneNumberOperationResult.Builder<List<PhoneNumberDto>> operationResultBuilder = PhoneNumberOperationResult.empty();

        List<PhoneNumber> phoneNumbers = this.phoneNumberRepository.findAll();

        for (PhoneNumber phoneNumber : phoneNumbers) {
            phoneNumberDtos.add(PhoneNumberDto.from(phoneNumber).build());
        }
        operationResultBuilder.result(phoneNumberDtos);
        return operationResultBuilder.build();
    }

    @Override
    public PhoneNumberOperationResult<PhoneNumberDto> createNewPhoneNumber(PhoneNumberDto phoneNumberDto) throws IllegalArgumentException {
        if (Objects.isNull(phoneNumberDto)) {
            throw new IllegalArgumentException("phoneNumber is null");
        }

        PhoneNumberOperationResult.Builder<PhoneNumberDto> operationResultBuilder = PhoneNumberOperationResult.empty();

        PhoneNumber phoneNumber = PhoneNumber.from(phoneNumberDto).build();
        Country country = this.countryRepository.findCountryByCountryCode(phoneNumberDto.getCountryCode());

        if(Objects.isNull(country)){
            operationResultBuilder.addError(PhoneNumberOperationResult.Error.COUNTRY_NOT_FOUND);
            return operationResultBuilder.build();
        }

        phoneNumber.setCountry(country);
        if(!phoneNumber.isValid()){
            operationResultBuilder.addError(PhoneNumberOperationResult.Error.PHONE_NUMBER_NOT_VALID);
            return operationResultBuilder.build();
        }
        PhoneNumber savedPhoneNumber = this.phoneNumberRepository.save(phoneNumber);


        return operationResultBuilder.result(PhoneNumberDto.from(savedPhoneNumber).build()).build();
    }

    @Override
    public PhoneNumberOperationResult<PhoneNumberDto> findPhoneNumberById(Long id) throws NullPointerException {
        if (Objects.isNull(id)) {
            throw new NullPointerException("Id is null");
        }

        PhoneNumberOperationResult.Builder<PhoneNumberDto> operationResultBuilder = PhoneNumberOperationResult.empty();
        PhoneNumberDto phoneNumberDto = PhoneNumberDto.empty().build();

        Optional<PhoneNumber> phoneNumberOptional = this.phoneNumberRepository.findById(id);

        if (!phoneNumberOptional.isPresent()) {
            operationResultBuilder.addError(PhoneNumberOperationResult.Error.PHONE_NUMBER_NOT_FOUND);
            return operationResultBuilder.build();
        }

        phoneNumberDto = PhoneNumberDto.from(phoneNumberOptional.get()).build();

        return operationResultBuilder.result(phoneNumberDto).build();
    }

    @Override
    public PhoneNumberOperationResult<PhoneNumberDto> updatePhoneNumberWithId(Long id, PhoneNumberDto phoneNumberdto) throws NullPointerException {
        if (Objects.isNull(id)) {
            throw new NullPointerException("Id is null");
        }

        if (Objects.isNull(phoneNumberdto)) {
            throw new NullPointerException("phoneNumber is null");
        }

        PhoneNumberOperationResult.Builder<PhoneNumberDto> operationResultBuilder = PhoneNumberOperationResult.empty();
        PhoneNumber phoneNumberToUpdate = PhoneNumber.empty().build();
        PhoneNumber phoneNumberUpdated = PhoneNumber.empty().build();
        PhoneNumberDto phoneNumberDtoUpdated = PhoneNumberDto.empty().build();

        Optional<PhoneNumber> phoneNumberOptional = this.phoneNumberRepository.findById(id);

        if (!phoneNumberOptional.isPresent()) {
            return operationResultBuilder.addError(PhoneNumberOperationResult.Error.PHONE_NUMBER_NOT_FOUND).build();
        }

        phoneNumberToUpdate = phoneNumberOptional.get();

        phoneNumberToUpdate.updateFrom(phoneNumberdto);

        phoneNumberUpdated = this.phoneNumberRepository.save(phoneNumberToUpdate);
        phoneNumberDtoUpdated = PhoneNumberDto.from(phoneNumberUpdated).build();
        operationResultBuilder.result(phoneNumberDtoUpdated);

        return operationResultBuilder.build();
    }
}

