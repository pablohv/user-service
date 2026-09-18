CREATE TABLE Employees (
    id            BINARY(16)    NOT NULL,
    first_name    VARCHAR(100)  NOT NULL,
    last_name     VARCHAR(100)  NOT NULL,
    email         VARCHAR(255)  NOT NULL,
    password      VARCHAR(255)  NOT NULL,
    phone         VARCHAR(10),
    state         VARCHAR(100),
    city          VARCHAR(100),
    cp            VARCHAR(5),
    address       VARCHAR(255),
    country       VARCHAR(100),
    nationality   VARCHAR(100),
    PRIMARY KEY (id),
    CONSTRAINT uk_employees_email UNIQUE (email)
);
