CREATE TABLE providers
(
    id            BIGSERIAL PRIMARY KEY,
    provider_type VARCHAR(20) NOT NULL CHECK (provider_type IN ('AGENCY', 'DRIVER')),
    created_at    TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at    TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE agencies
(
    provider_id BIGINT,
    public_name VARCHAR(100) NOT NULL,
    legal_name  VARCHAR(100) NOT NULL,
    tax_number  VARCHAR(30)  NOT NULL UNIQUE,
    email       VARCHAR(120) NOT NULL,
    phone       VARCHAR(20)  NOT NULL,
    description TEXT,

    CONSTRAINT pk_agencies PRIMARY KEY (provider_id),
    CONSTRAINT fk_agencies_provider
        FOREIGN KEY (provider_id) REFERENCES providers (id)
);

CREATE TABLE drivers
(
    provider_id    BIGINT,
    full_name      VARCHAR(100) NOT NULL,
    tax_number     VARCHAR(30)  NOT NULL UNIQUE,
    email          VARCHAR(120) NOT NULL,
    phone          VARCHAR(20)  NOT NULL,
    license_number VARCHAR(30)  NOT NULL UNIQUE,

    CONSTRAINT pk_drivers PRIMARY KEY (provider_id),
    CONSTRAINT fk_drivers_provider
        FOREIGN KEY (provider_id) REFERENCES providers (id)
);