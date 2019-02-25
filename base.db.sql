BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS `Scientific_work` (
	`id`	INTEGER NOT NULL,
	`Title`	TEXT NOT NULL,
	`Author`	TEXT,
	`FieldOfStudy`	INTEGER,
	`Journal`	TEXT,
	`PublicationType`	INTEGER,
	`YearOfIssue`	INTEGER,
	`Citations`	INTEGER,
	`Affiliation`	TEXT,
	PRIMARY KEY(`id`),
	FOREIGN KEY(`FieldOfStudy`) REFERENCES `Field_of_study`(`id`)
);
INSERT INTO `Scientific_work` VALUES (1,'Coevolutionary fuzzy modeling','Pena Reyes, Carlos Andres',8,NULL,4,2004,55,'Stanford University');
INSERT INTO `Scientific_work` VALUES (2,'Item-based top- N recommendation algorithms','Mukund Deshpande, George Karypis',6,'ACM Transactions on Information Systems',1,2004,2209,'University of Minnesota');
INSERT INTO `Scientific_work` VALUES (3,'Quantum Phase Transitions','Subir Sachdev',7,NULL,4,2000,6746,'Hardvard University');
INSERT INTO `Scientific_work` VALUES (4,'Manufacture of semiconductor device','Akio Shima',5,NULL,5,1986,55519,'Renesas Electronics');
INSERT INTO `Scientific_work` VALUES (5,'Battery performance models in ADVISOR','V.H.Johnson',5,'Jornual of Power Sources',1,2002,466,'National Renewable Energy Laboratory');
INSERT INTO `Scientific_work` VALUES (6,'Treatment for insomnia','Neil B. Kavey',1,'The Lancet',5,1993,30,'KAVEY');
INSERT INTO `Scientific_work` VALUES (7,'Introduction to mediation, moderation, and conditional process analysis : a regression-based approach','Andrew F. Hayes',9,NULL,3,2013,18379,'Ohio State University');
INSERT INTO `Scientific_work` VALUES (8,'The Rat Brain in Stereotaxic Coordinates','George Paxinos',2,NULL,4,2013,81937,NULL);
INSERT INTO `Scientific_work` VALUES (9,'The Scientific Wealth of Nations','Robert M. May',10,'Science',1,1997,621,'Office of Science and Technology');
INSERT INTO `Scientific_work` VALUES (10,'The Science of Art','Richard M. Schultz',11,'Science',1,2002,306,'University of Pennsylvania');
CREATE TABLE IF NOT EXISTS `Publication_Type` (
	`id`	INTEGER NOT NULL,
	`Typee`	TEXT,
	PRIMARY KEY(`id`)
);
INSERT INTO `Publication_Type` VALUES (1,'Journal publications');
INSERT INTO `Publication_Type` VALUES (2,'Conference publications');
INSERT INTO `Publication_Type` VALUES (3,'Articles');
INSERT INTO `Publication_Type` VALUES (4,'Books');
INSERT INTO `Publication_Type` VALUES (5,'Patents');
INSERT INTO `Publication_Type` VALUES (6,'Book chapters');
INSERT INTO `Publication_Type` VALUES (7,'Other');
CREATE TABLE IF NOT EXISTS `Field_of_study` (
	`id`	INTEGER NOT NULL,
	`Title`	TEXT,
	PRIMARY KEY(`id`)
);
INSERT INTO `Field_of_study` VALUES (1,'Medicine');
INSERT INTO `Field_of_study` VALUES (2,'Biology');
INSERT INTO `Field_of_study` VALUES (3,'Materials science');
INSERT INTO `Field_of_study` VALUES (4,'Chemistry');
INSERT INTO `Field_of_study` VALUES (5,'Engineering');
INSERT INTO `Field_of_study` VALUES (6,'Computer science');
INSERT INTO `Field_of_study` VALUES (7,'Physics');
INSERT INTO `Field_of_study` VALUES (8,'Mathematics');
INSERT INTO `Field_of_study` VALUES (9,'Psychology');
INSERT INTO `Field_of_study` VALUES (10,'History');
INSERT INTO `Field_of_study` VALUES (11,'Art');
INSERT INTO `Field_of_study` VALUES (12,'Sociology');
INSERT INTO `Field_of_study` VALUES (13,'Geology');
INSERT INTO `Field_of_study` VALUES (14,'Political science');
INSERT INTO `Field_of_study` VALUES (15,'Geography');
INSERT INTO `Field_of_study` VALUES (16,'Economics');
INSERT INTO `Field_of_study` VALUES (17,'Philosophy');
INSERT INTO `Field_of_study` VALUES (18,'Business');
INSERT INTO `Field_of_study` VALUES (19,'Enviromental science');
INSERT INTO `Field_of_study` VALUES (20,'Other');
COMMIT;
