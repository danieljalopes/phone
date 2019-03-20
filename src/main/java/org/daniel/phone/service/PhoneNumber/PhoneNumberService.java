package org.daniel.phone.service.PhoneNumber;

import org.daniel.phone.dto.PhoneNumberDto;

import java.util.List;

public interface PhoneNumberService {

    /**
     * All PhoneNumbers
     * @return
     */
    PhoneNumberOperationResult<List<PhoneNumberDto>> findAll();

    /**
     * Creates a new PhoneNumber
     * @param phoneNumberDto
     * @return
     */
    PhoneNumberOperationResult<PhoneNumberDto> createNewPhoneNumber(PhoneNumberDto phoneNumberDto) throws IllegalArgumentException;

    /**
     * Find PhoneNumber by Id
     * @param id
     * @return
     * @throws Exception
     */
    PhoneNumberOperationResult<PhoneNumberDto> findPhoneNumberById(Long id) throws NullPointerException;

    /**
     * Update PhoneNumber with Id
     * @param id
     * @return
     * @throws Exception
     */
    PhoneNumberOperationResult<PhoneNumberDto> updatePhoneNumberWithId(Long id, PhoneNumberDto phoneNumberdto) throws NullPointerException ;
}
