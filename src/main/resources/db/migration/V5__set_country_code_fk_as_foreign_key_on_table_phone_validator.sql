
ALTER TABLE phone_validator
  ADD CONSTRAINT phone_validator_country_code_fk FOREIGN KEY (country_code_fk) REFERENCES country (country_code) ON DELETE CASCADE ON UPDATE CASCADE;

