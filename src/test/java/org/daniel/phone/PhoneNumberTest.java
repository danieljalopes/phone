package org.daniel.phone;

import org.daniel.phone.repository.CountryRepository;
import org.daniel.phone.repository.PhoneNumberRepository;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class PhoneNumberTest {



    @Autowired
    private PhoneNumberRepository phoneRepository;

    @Autowired
    private CountryRepository countryRepository;

    /*
    @Transactional
    @Test
    public void savePhoneNumber(){
        Country country = new Country();
        country.setName("Portugal");
        country.setPhoneCountryCode("+351");
        this.countryRepository.save(country);


        PhoneNumber phoneNumber = new PhoneNumber();
        phoneNumber.setCountry(country);
        phoneNumber.setNumber("933477724");

        this.phoneRepository.save(phoneNumber);

        Assert.assertTrue(2L == phoneNumber.getId());
    }

    */
}
