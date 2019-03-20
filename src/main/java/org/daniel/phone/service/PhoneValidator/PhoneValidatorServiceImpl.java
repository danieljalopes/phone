package org.daniel.phone.service.PhoneValidator;

import org.daniel.phone.domain.Country;
import org.daniel.phone.domain.PhoneValidator;
import org.daniel.phone.dto.PhoneValidatorDto;
import org.daniel.phone.repository.PhoneValidatorRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PhoneValidatorServiceImpl implements PhoneValidatorService {

    private final PhoneValidatorRepository phoneValidatorRepository;

    public PhoneValidatorServiceImpl(PhoneValidatorRepository phoneValidatorRepository) {
        this.phoneValidatorRepository = phoneValidatorRepository;
    }

    public PhoneValidatorOperationResult<List<PhoneValidatorDto>> findAll() {
        PhoneValidatorOperationResult.Builder<List<PhoneValidatorDto>> phoneValidatorOperationResultBuilder = PhoneValidatorOperationResult.empty();

        List<PhoneValidator> phoneValidators = this.phoneValidatorRepository.findAll();

        List<PhoneValidatorDto> phoneValidatorDtos = new LinkedList<>();
        phoneValidators.stream().forEach(p -> phoneValidatorDtos.add(PhoneValidatorDto.from(p).build()));

        phoneValidatorOperationResultBuilder.result(phoneValidatorDtos);

        return phoneValidatorOperationResultBuilder.build();
    }

    public PhoneValidatorOperationResult<PhoneValidatorDto> createNewPhoneValidator(PhoneValidatorDto phoneValidatorDto) {
        if(Objects.isNull(phoneValidatorDto)){
            throw new IllegalArgumentException("phoneValidator is null");
        }

        PhoneValidatorOperationResult.Builder<PhoneValidatorDto>operationResult = PhoneValidatorOperationResult.empty();
        PhoneValidator phoneValidator = PhoneValidator.from(phoneValidatorDto).build();

        PhoneValidator savedPhoneValidator = this.phoneValidatorRepository.save(phoneValidator);

        PhoneValidatorDto savedPhoneValidatorDto = PhoneValidatorDto.from(savedPhoneValidator).build();


        return operationResult.result(savedPhoneValidatorDto).build();
    }


    public PhoneValidatorOperationResult<PhoneValidatorDto> findPhoneValidatorById(Long id) throws Exception {
        if (Objects.isNull(id)) {
            throw new Exception("Id is null");
        }

        PhoneValidatorOperationResult.Builder<PhoneValidatorDto>operationResult = PhoneValidatorOperationResult.empty();
        PhoneValidatorDto phoneValidatorDto = PhoneValidatorDto.empty().build();
        Optional<PhoneValidator> phoneValidatorOptional = this.phoneValidatorRepository.findById(id);

        if (!phoneValidatorOptional.isPresent()) {
            return operationResult.addError(PhoneValidatorOperationResult.Error.PHONE_VALIDATOR_NOT_FOUND).build();
        }

        phoneValidatorDto = PhoneValidatorDto.from(phoneValidatorOptional.get()).build();
        operationResult.result(phoneValidatorDto);
        return operationResult.build();
    }


    public PhoneValidatorOperationResult<PhoneValidatorDto> updatePhoneValidatorWithId(Long id, PhoneValidatorDto phoneValidator) throws Exception {
        if (Objects.isNull(id)) {
            throw new Exception("Id is null");
        }

        if (Objects.isNull(phoneValidator)) {
            throw new Exception("PhoneValidator is null");
        }

        PhoneValidatorOperationResult.Builder<PhoneValidatorDto> operationResult = PhoneValidatorOperationResult.empty();
        PhoneValidatorDto phoneValidatorDtoSaved = PhoneValidatorDto.empty().build();
        Optional<PhoneValidator> phoneValidatorOptional = this.phoneValidatorRepository.findById(id);

        if (Objects.isNull(phoneValidatorOptional)) {
            throw new Exception("PhoneValidator not found");
        }

        PhoneValidator phoneValidatorToSave = PhoneValidator.from(phoneValidator)
                .id(phoneValidatorOptional.get().getId())
                .build();


        PhoneValidator phoneValidatorSaved = this.phoneValidatorRepository.save(phoneValidatorToSave);
        phoneValidatorDtoSaved = PhoneValidatorDto.from(phoneValidatorSaved).build();

        return operationResult.result(phoneValidatorDtoSaved).build();
    }


    public Country countryOfPhoneValidatorOfId(Long id) {
        Optional<PhoneValidator> phoneValidatorOptional = this.phoneValidatorRepository.findById(id);

        if (phoneValidatorOptional.isPresent()) {
            return phoneValidatorOptional.get().getCountry();
        }

        return null;
    }
}
