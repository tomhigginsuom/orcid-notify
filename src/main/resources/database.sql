-- ALTER DATABASE orcid CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

drop table if exists orcid;
drop table if exists works;
drop table if exists works_groups;
drop table if exists statistics;

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
  work_type VARCHAR(100),
  group_id INT,
  title VARCHAR(1000),
  created TIMESTAMP NULL,
  publication_day SMALLINT,
  publication_month SMALLINT,
  publication_year SMALLINT,
  PRIMARY KEY (orcid_id, put_code, identifier_type, identifier)
);

ALTER TABLE works ADD INDEX (orcid_id);
ALTER TABLE works ADD INDEX (put_code);
ALTER TABLE works ADD INDEX (identifier_type, identifier);
ALTER TABLE works ADD INDEX (group_id);

CREATE TABLE works_groups (
  group_id INT NOT NULL AUTO_INCREMENT,
  orcid_id VARCHAR(19) NOT NULL,
  PRIMARY KEY (group_id)
);

ALTER TABLE works_groups ADD INDEX (orcid_id);

CREATE TABLE statistics (
  api_requests INT NOT NULL,
  api_data_bytes BIGINT NOT NULL
);
