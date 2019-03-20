package org.daniel.phone.repository;

import org.daniel.phone.domain.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {

    @Query("Select c from Country c where c.countryCode = (:countryCode)")
     Country findCountryByCountryCode(@Param(value = "countryCode") String countryCode);
}
