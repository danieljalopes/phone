
CREATE TABLE country (
    id BIGSERIAL PRIMARY KEY ,
    country_code varchar(4),
    name varchar(24)
);

CREATE TABLE phone_number (
    id BIGSERIAL PRIMARY KEY ,
    country_code varchar(4),
    number varchar(16)
);



CREATE TABLE phone_validator (
    id BIGSERIAL PRIMARY KEY ,
    country_code varchar(4),
    regex varchar(46)
);