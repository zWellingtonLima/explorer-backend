CREATE TABLE places
(
    id           BIGSERIAL PRIMARY KEY,
    name         VARCHAR(100)     NOT NULL,
    description  TEXT,
    address      VARCHAR(150),
    city         VARCHAR(100)     NOT NULL,
    country_code VARCHAR(2)       NOT NULL,
    latitude     DOUBLE PRECISION NOT NULL,
    longitude    DOUBLE PRECISION NOT NULL,
    created_at   TIMESTAMPTZ      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at   TIMESTAMPTZ      NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT chk_places_name_not_blank CHECK (btrim(name) <> ''),
    CONSTRAINT chk_places_city_not_blank CHECK (btrim(city) <> ''),
    CONSTRAINT chk_places_country_code_format CHECK (country_code ~ '^[A-Z]{2}$'
) ,
    CONSTRAINT chk_places_latitude_range CHECK (latitude BETWEEN -90 AND 90),
    CONSTRAINT chk_places_longitude_range CHECK (longitude BETWEEN -180 AND 180)
);