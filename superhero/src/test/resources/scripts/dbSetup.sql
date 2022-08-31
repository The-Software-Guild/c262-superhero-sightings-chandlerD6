DROP DATABASE IF EXISTS heroDBTest;
CREATE DATABASE heroDBTest;

USE heroDBTest;

CREATE TABLE power(
	powerId INT PRIMARY KEY AUTO_INCREMENT,
    powerName VARCHAR(30) NOT NULL,
    powerDescription VARCHAR(200) NOT NULL
);

CREATE TABLE hero(
    heroId INT PRIMARY KEY AUTO_INCREMENT,
    heroName VARCHAR(30) NOT NULL,
    heroDescription VARCHAR(200) NOT NULL
);

CREATE TABLE heroPowers(
	powerId INT NOT NULL,
    heroId INT NOT NULL,
    PRIMARY KEY(powerId, heroId),
	FOREIGN KEY (heroId) REFERENCES hero(heroId),
    FOREIGN KEY (powerId) REFERENCES power(powerId)
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
    locLat FLOAT(11,8) NOT NULL,
    locLong FLOAT(11,8) NOT NULL
);

CREATE TABLE orgMembers(
    heroId INT NOT NULL,
    orgId INT NOT NULL,
    PRIMARY KEY(heroId, orgId),
    FOREIGN KEY (heroId) REFERENCES hero(heroId),
    FOREIGN KEY (orgId) REFERENCES organization(orgId)
);

CREATE TABLE sighting(
	sightingId INT PRIMARY KEY AUTO_INCREMENT,
	heroId INT NOT NULL,
    locId INT NOT NULL,
    sightingDate TIMESTAMP NOT NULL,
    FOREIGN KEY (heroId) REFERENCES hero(heroId),
    FOREIGN KEY (locId) REFERENCES location(locId)
);


INSERT INTO hero(heroName, heroDescription) VALUES("Thor", "The god of thunder.");
INSERT INTO hero(heroName, heroDescription) VALUES("Captain America", "America's first hero.");
INSERT INTO hero(heroName, heroDescription) VALUES("Iron Man", "Billionaire, playboy, philanthropist");
INSERT INTO hero(heroName, heroDescription) VALUES("Thanos", "Inevtiable");

INSERT INTO power(powerName, powerDescription) VALUES("Super Strength", "Ability to lift heavy objects.");
INSERT INTO power(powerName, powerDescription) VALUES("Flight", "Ability to fly");
INSERT INTO power(powerName, powerDescription) VALUES("Electrokinetics", "Ability to control electricity");


INSERT INTO heroPowers(powerId, heroId) VALUES(1,1);
INSERT INTO heroPowers(powerId, heroId) VALUES(1,2);
INSERT INTO heroPowers(powerId, heroId) VALUES(1,4);
INSERT INTO heroPowers(powerId, heroId) VALUES(2,1);
INSERT INTO heroPowers(powerId, heroId) VALUES(2,3);
INSERT INTO heroPowers(powerId, heroId) VALUES(3,1);


INSERT INTO organization(orgName, orgDescription, contactInfo) VALUES("Avengers", "Protectors of earth." ,"New York, Stark Tower.");
INSERT INTO organization(orgName, orgDescription, contactInfo) VALUES("League of Evil", "Where the bad guys hangout.", "Detroit");

INSERT INTO orgMembers(heroId, orgId) VALUES(1,1);
INSERT INTO orgMembers(heroId, orgId) VALUES(2,1);
INSERT INTO orgMembers(heroId, orgId) VALUES(3,1);
INSERT INTO orgMembers(heroId, orgId) VALUES(4,2);


INSERT INTO location(locName, locDescription, locAddress, locLat, locLong) VALUES("Stark Tower", "Where the avengers are stationed.", "New York", 22.0365, 137.9876);
INSERT INTO location(locName, locDescription, locAddress, locLat, locLong) VALUES("Evil HQ", "Where the Villains are stationed.", "Detroit", 32.0365, 107.9876);

INSERT INTO sighting(heroId, locId, sightingDate) VALUES(1, 1, "2012-10-02 00:00:00.000");
INSERT INTO sighting(heroId, locId, sightingDate) VALUES(4, 2, "2017-07-02 00:00:00.000");




