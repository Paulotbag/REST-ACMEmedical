-- DROP SCHEMA IF EXISTS `acmemedical`;
-- CREATE SCHEMA IF NOT EXISTS `acmemedical` DEFAULT CHARACTER SET utf8mb4;
-- DROP USER IF EXISTS `cst8277`@`localhost`;
-- CREATE USER IF NOT EXISTS 'cst8277'@'localhost' IDENTIFIED BY '8277';
-- GRANT ALL ON `databank`.* TO 'cst8277'@'localhost';


USE `acmemedical`;
-- Table: medical_certificate
CREATE TABLE medical_certificate (
    certificate_id INT PRIMARY KEY,
    physician_id INT,
    training_id INT,
    signed BIT(1),
    created DATETIME,
    updated DATETIME,
    version BIGINT,
    FOREIGN KEY (physician_id) REFERENCES physician(id),
    FOREIGN KEY (training_id) REFERENCES medical_training(training_id)
);

-- Table: medical_school
CREATE TABLE medical_school (
    school_id INT PRIMARY KEY,
    name VARCHAR(100),
    public BIT(1),
    created DATETIME,
    updated DATETIME,
    version BIGINT
);

-- Table: medical_training
CREATE TABLE medical_training (
    training_id INT PRIMARY KEY,
    school_id INT,
    start_date DATETIME,
    end_date DATETIME,
    active BIT(1),
    created DATETIME,
    updated DATETIME,
    version BIGINT,
    FOREIGN KEY (school_id) REFERENCES medical_school(school_id)
);

-- Table: medicine
CREATE TABLE medicine (
    medicine_id INT PRIMARY KEY,
    drug_name VARCHAR(50),
    manufacturer_name VARCHAR(50),
    dosage_information VARCHAR(100),
    created DATETIME,
    updated DATETIME,
    version BIGINT
);

-- Table: patient
CREATE TABLE patient (
    patient_id INT PRIMARY KEY,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    year_of_birth INT,
    home_address VARCHAR(100),
    height_cm INT,
    weight_kg INT,
    smoker BIT(1),
    created DATETIME,
    updated DATETIME,
    version BIGINT
);

-- Table: physician
CREATE TABLE physician (
    id INT PRIMARY KEY,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    created DATETIME,
    updated DATETIME,
    version BIGINT
);

-- Table: prescription
CREATE TABLE prescription (
    physician_id INT,
    patient_id INT,
    number_of_refills INT,
    prescription_information VARCHAR(100),
    medicine_id INT,
    created DATETIME,
    updated DATETIME,
    version BIGINT,
    PRIMARY KEY (physician_id, patient_id, medicine_id),
    FOREIGN KEY (physician_id) REFERENCES physician(id),
    FOREIGN KEY (patient_id) REFERENCES patient(patient_id),
    FOREIGN KEY (medicine_id) REFERENCES medicine(medicine_id)
);

-- Table: security_role
CREATE TABLE security_role (
    role_id INT PRIMARY KEY,
    name VARCHAR(45)
);

-- Table: security_user
CREATE TABLE security_user (
    user_id INT PRIMARY KEY,
    password_hash VARCHAR(256),
    username VARCHAR(100),
    physician_id INT,
    FOREIGN KEY (physician_id) REFERENCES physician(id)
);

-- Table: user_has_role
CREATE TABLE user_has_role (
    user_id INT,
    role_id INT,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES security_user(user_id),
    FOREIGN KEY (role_id) REFERENCES security_role(role_id)
);
