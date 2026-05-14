CREATE DATABASE IF NOT EXISTS votingDb;

USE votingDb;

-- -------------------------------------------------------------
-- 1. constituency
-- -------------------------------------------------------------
CREATE TABLE constituency (
  constituency_id   INT          NOT NULL AUTO_INCREMENT,
  constituency_name VARCHAR(120) NOT NULL,
  council_name      VARCHAR(120) NOT NULL,
  PRIMARY KEY (constituency_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- -------------------------------------------------------------
-- 2. party
-- -------------------------------------------------------------
CREATE TABLE party (
  party_id   INT          NOT NULL AUTO_INCREMENT,
  party_name VARCHAR(100) NOT NULL,
  PRIMARY KEY (party_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- -------------------------------------------------------------
-- 3. constituency_party  (@ManyToMany join table)
--    JPA: @JoinTable(name = "constituency_party",
--           joinColumns = @JoinColumn(name = "constituency_id"),
--           inverseJoinColumns = @JoinColumn(name = "party_id"))
-- -------------------------------------------------------------
CREATE TABLE constituency_party (
  constituency_id INT NOT NULL,
  party_id        INT NOT NULL,
  PRIMARY KEY (constituency_id, party_id),
  CONSTRAINT fk_cp_constituency FOREIGN KEY (constituency_id) REFERENCES constituency (constituency_id),
  CONSTRAINT fk_cp_party        FOREIGN KEY (party_id)        REFERENCES party        (party_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- -------------------------------------------------------------
-- 4. voter_address
--    ('address' is a reserved word in some JPA/HQL contexts)
-- -------------------------------------------------------------
CREATE TABLE voter_address (
  address_id       INT          NOT NULL AUTO_INCREMENT,
  address_line_one VARCHAR(150) NOT NULL,
  address_line_two VARCHAR(150)     NULL,
  town_city        VARCHAR(100) NOT NULL,
  postcode         VARCHAR(10)  NOT NULL,
  constituency_id  INT          NOT NULL,
  PRIMARY KEY (address_id),
  CONSTRAINT fk_addr_constituency FOREIGN KEY (constituency_id) REFERENCES constituency (constituency_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- -------------------------------------------------------------
-- 5. voter_account
--    ('account' is a reserved word in MariaDB/MySQL)
-- -------------------------------------------------------------
CREATE TABLE voter_account (
  account_id                INT         NOT NULL AUTO_INCREMENT,
  national_insurance_number VARCHAR(9)  NOT NULL,
  date_of_birth             DATE        NOT NULL,
  first_name                VARCHAR(80) NOT NULL,
  last_name                 VARCHAR(80) NOT NULL,
  address_id                INT         NOT NULL,
  PRIMARY KEY (account_id),
  UNIQUE KEY uq_account_ni (national_insurance_number),
  CONSTRAINT fk_account_address FOREIGN KEY (address_id) REFERENCES voter_address (address_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- -------------------------------------------------------------
-- 6. pollcard_reference
-- -------------------------------------------------------------
CREATE TABLE pollcard_reference (
  pollcard_id         VARCHAR(36)  NOT NULL,
  pollcard_ref_number VARCHAR(20)  NOT NULL,
  account_id          INT          NOT NULL,
  PRIMARY KEY (pollcard_id),
  UNIQUE KEY uq_pollcard_ref (pollcard_ref_number),
  CONSTRAINT fk_pollcard_account FOREIGN KEY (account_id) REFERENCES voter_account (account_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- -------------------------------------------------------------
-- 7. vote_record
--    UNIQUE on account_id = one vote per voter enforced at DB level
-- -------------------------------------------------------------
CREATE TABLE vote_record (
  vote_id         VARCHAR(36) NOT NULL,
  account_id      INT         NOT NULL,
  party_id        INT         NOT NULL,
  constituency_id INT         NOT NULL,
  date_voted      TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (vote_id),
  UNIQUE KEY uq_vote_account (account_id),
  CONSTRAINT fk_vote_account      FOREIGN KEY (account_id)      REFERENCES voter_account (account_id),
  CONSTRAINT fk_vote_party        FOREIGN KEY (party_id)        REFERENCES party         (party_id),
  CONSTRAINT fk_vote_constituency FOREIGN KEY (constituency_id) REFERENCES constituency  (constituency_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- -------------------------------------------------------------
-- 8. vote_confirmation_receipt
-- -------------------------------------------------------------
CREATE TABLE vote_confirmation_receipt (
  receipt_id             VARCHAR(36)  NOT NULL,
  vote_id                VARCHAR(36)  NOT NULL,
  confirmation_reference VARCHAR(30)  NOT NULL,
  issued_at              TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (receipt_id),
  UNIQUE KEY uq_receipt_vote      (vote_id),
  UNIQUE KEY uq_receipt_reference (confirmation_reference),
  CONSTRAINT fk_receipt_vote FOREIGN KEY (vote_id) REFERENCES vote_record (vote_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


-- =============================================================
--  SAMPLE DATA
-- =============================================================

INSERT INTO constituency (constituency_name, council_name) VALUES
  ('Hackney Central Ward', 'London Borough of Hackney'),
  ('Hackney Downs Ward',   'London Borough of Hackney'),
  ('Stoke Newington Ward', 'London Borough of Hackney');

INSERT INTO party (party_name) VALUES
  ('Labour Party'),
  ('Green Party'),
  ('Liberal Democrats'),
  ('Conservative Party'),
  ('Hackney Independents');

INSERT INTO constituency_party (constituency_id, party_id) VALUES
  (1, 1), (1, 2), (1, 3), (1, 5),
  (2, 1), (2, 2), (2, 4),
  (3, 1), (3, 2), (3, 3);

INSERT INTO voter_address (address_line_one, address_line_two, town_city, postcode, constituency_id) VALUES
  ('12 Mare Street',  'Flat 2',  'London', 'E8 4RP',  1),
  ('34 Downs Road',   NULL,      'London', 'E5 8QT',  2),
  ('7 Church Street', 'Floor 1', 'London', 'N16 0AR', 3);

INSERT INTO voter_account (national_insurance_number, date_of_birth, first_name, last_name, address_id) VALUES
  ('AB123456C', '1990-04-15', 'Alice', 'Okafor',   1),
  ('CD789012E', '1985-11-22', 'Ben',   'Patel',    2),
  ('EF345678G', '2000-07-08', 'Chloe', 'Williams', 3);

INSERT INTO pollcard_reference (pollcard_id, pollcard_ref_number, account_id) VALUES
  (UUID(), 'PCR-2024-000001', 1),
  (UUID(), 'PCR-2024-000002', 2),
  (UUID(), 'PCR-2024-000003', 3);

-- Alice voted Green in Hackney Central; Ben voted Labour in Hackney Downs
INSERT INTO vote_record (vote_id, account_id, party_id, constituency_id) VALUES
  (UUID(), 1, 2, 1),
  (UUID(), 2, 1, 2);

INSERT INTO vote_confirmation_receipt (receipt_id, vote_id, confirmation_reference)
SELECT UUID(), v.vote_id, CONCAT('CONF-', UPPER(LEFT(v.vote_id, 8)))
FROM vote_record v;