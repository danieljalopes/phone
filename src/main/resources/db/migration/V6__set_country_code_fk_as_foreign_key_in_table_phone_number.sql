ALTER TABLE phone_number
  ADD CONSTRAINT phone_number_country_code_fk FOREIGN KEY (country_code_fk) REFERENCES country (country_code) ON DELETE CASCADE ON UPDATE CASCADE;