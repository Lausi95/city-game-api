CREATE TABLE agent
(
    id           VARCHAR(255) NOT NULL,
    first_name   VARCHAR(255) NOT NULL,
    last_name    VARCHAR(255) NOT NULL,
    phone_number VARCHAR(255) NOT NULL,
    CONSTRAINT pk_agent PRIMARY KEY (id)
);