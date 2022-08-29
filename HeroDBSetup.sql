DROP DATABASE IF EXISTS heroDB;
CREATE DATABASE heroDB;

USE heroDB;

CREATE TABLE hero(
    heroId INT PRIMARY KEY AUTO_INCREMENT,
    heroName VARCHAR(30) NOT NULL,
    heroDescription VARCHAR(200) NOT NULL,
    power VARCHAR(30) NOT NULL
);

CREATE TABLE organization(
	orgId INT PRIMARY KEY AUTO_INCREMENT,
    orgName VARCHAR(50) NOT NULL,
    orgDescription VARCHAR(200) NOT NULL,
    contactInfo VARCHAR(100) NOT NULL
);

CREATE TABLE location(
	locId INT PRIMARY KEY AUTO_INCREMENT,
    locName VARCHAR(30) NOT NULL,
    locDescription VARCHAR(200) NOT NULL,
    locAddress VARCHAR(100) NOT NULL,
    locLat FLOAT(10,8) NOT NULL,
    locLong FLOAT(10,8) NOT NULL
);

CREATE TABLE orgMembers(
    heroId INT NOT NULL,
    orgId INT NOT NULL,
    PRIMARY KEY(heroId, orgId),
    FOREIGN KEY (heroId) REFERENCES hero(heroId),
    FOREIGN KEY (orgId) REFERENCES organization(orgId)
);

CREATE TABLE sighting(
	heroId INT NOT NULL,
    locId INT NOT NULL,
    sightingDate DATETIME NOT NULL,
    PRIMARY KEY(heroId, locId, sightingDate),
    FOREIGN KEY (heroId) REFERENCES hero(heroId),
    FOREIGN KEY (locId) REFERENCES location(locId)
);