package org.daniel.phone.service.PhoneValidator;

import org.daniel.phone.domain.Country;
import org.daniel.phone.dto.PhoneValidatorDto;

import java.util.List;

public interface PhoneValidatorService {

    /**
     * All PhoneValidators
     * @return
     */
    PhoneValidatorOperationResult<List<PhoneValidatorDto>> findAll();

    /**
     * Creates a new PhoneValidator
     * @param phoneValidatorDto
     * @return
     */
    PhoneValidatorOperationResult<PhoneValidatorDto> createNewPhoneValidator(PhoneValidatorDto phoneValidatorDto);

    /**
     * Find PhoneValidator by Id
     * @param id
     * @return
     * @throws Exception
     */
    PhoneValidatorOperationResult<PhoneValidatorDto> findPhoneValidatorById(Long id) throws Exception;

    /**
     * Update PhoneValidator with Id
     * @param id
     * @return
     * @throws Exception
     */
    PhoneValidatorOperationResult<PhoneValidatorDto> updatePhoneValidatorWithId(Long id, PhoneValidatorDto phoneValidator) throws Exception;

    /**
     * Gives the Country of a PhoneValidator given the id
     * @param id
     * @return
     */
     Country countryOfPhoneValidatorOfId(Long id);
}
