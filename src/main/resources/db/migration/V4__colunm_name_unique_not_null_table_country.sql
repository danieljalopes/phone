ALTER TABLE country
  ALTER COLUMN "name" SET NOT NULL ,
  ADD CONSTRAINT country_name_unique UNIQUE ("name")
;