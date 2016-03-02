-- ALTER DATABASE orcid CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

CREATE TABLE orcid (
  orcid_id VARCHAR(19) NOT NULL,
  family_name VARCHAR(255),
  given_names VARCHAR(255),
  last_modified TIMESTAMP NULL,
  initial_load TIMESTAMP NULL,
  needs_update BOOLEAN DEFAULT NULL,
  error BOOLEAN DEFAULT NULL,
  PRIMARY KEY (orcid_id)
);

CREATE TABLE works (
  orcid_id VARCHAR(19) NOT NULL,
  put_code INT NOT NULL,
  identifier_type VARCHAR(20) NOT NULL,
  identifier VARCHAR(140) NOT NULL,
  title VARCHAR(1000),
  created TIMESTAMP,
  publication_day SMALLINT,
  publication_month SMALLINT,
  publication_year SMALLINT,
  PRIMARY KEY (orcid_id, put_code, identifier_type, identifier)
);

CREATE TABLE statistics (
  api_requests INT NOT NULL,
  api_data_bytes BIGINT NOT NULL
);
