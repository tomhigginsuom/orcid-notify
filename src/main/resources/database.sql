CREATE TABLE orcid (
  orcid_id VARCHAR(19) NOT NULL,
  family_name VARCHAR(255),
  given_names VARCHAR(255),
  updated TIMESTAMP NULL,
  needs_update BOOLEAN DEFAULT NULL,
  PRIMARY KEY (orcid_id)
);

CREATE TABLE works (
  orcid_id VARCHAR(19) NOT NULL,
  put_code INT NOT NULL,
  identifier_type VARCHAR(50) NOT NULL,
  identifier VARCHAR(255) NOT NULL,
  title VARCHAR(1000),
  created TIMESTAMP,
  publication_day SMALLINT,
  publication_month SMALLINT,
  publication_year SMALLINT,
  PRIMARY KEY (orcid_id, put_code, identifier_type, identifier)
);
