ALTER TABLE country
  ALTER COLUMN country_code SET NOT NULL ,
  ADD CONSTRAINT country_code_unique UNIQUE (country_code)
  ;