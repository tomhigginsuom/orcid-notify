CREATE TABLE orcid (
  orcid_id varchar(19) NOT NULL,
  updated TIMESTAMP NULL,
  PRIMARY KEY (orcid_id)
);

-- CREATE USER 'orcid'@'localhost' IDENTIFIED BY 'orcid';
-- CREATE USER 'orcid'@'%' IDENTIFIED BY 'orcid';
-- GRANT ALL PRIVILEGES ON *.* TO 'orcid'@'localhost';
-- GRANT ALL PRIVILEGES ON *.* TO 'orcid'@'%';