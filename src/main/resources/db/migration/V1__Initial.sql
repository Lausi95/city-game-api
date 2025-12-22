CREATE TABLE game
(
    id    VARCHAR(255) NOT NULL PRIMARY KEY,
    title VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE agent
(
    id           VARCHAR(255) NOT NULL PRIMARY KEY,
    game_id      VARCHAR(255) NOT NULL REFERENCES game (id),
    first_name   VARCHAR(255) NOT NULL,
    last_name    VARCHAR(255) NOT NULL,
    phone_number VARCHAR(255) NOT NULL
);