CREATE DATABASE `biblioteca` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

CREATE TABLE `libro` (
  `titolo` varchar(50) NOT NULL,
  `autore` varchar(30) NOT NULL,
  `editore` varchar(40) NOT NULL,
  `anno_edizione` mediumint(9) NOT NULL,
  `note` varchar(100) DEFAULT NULL,
  `codice_biblioteca` char(6) NOT NULL,
  `stato` char(1) NOT NULL DEFAULT 'L',
  PRIMARY KEY (`codice_biblioteca`),
  KEY `libro_stato_IDX` (`stato`) USING BTREE,
  CONSTRAINT `libro_check` CHECK (`stato` = 'L' or `stato` = 'P')
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `utente` (
  `codice_tessera` char(6) NOT NULL,
  `nominativo` varchar(80) NOT NULL,
  `data_tesseramento` date NOT NULL,
  PRIMARY KEY (`codice_tessera`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `prestito` (
  `id_prestito` mediumint(9) NOT NULL AUTO_INCREMENT,
  `data_prestito` date NOT NULL,
  `codice_biblioteca` char(6) DEFAULT NULL,
  `codice_tessera` char(6) NOT NULL,
  `data_restituzione` date DEFAULT NULL,
  PRIMARY KEY (`id_prestito`),
  KEY `prestito_FK` (`codice_tessera`),
  KEY `prestito_FK_1` (`codice_biblioteca`),
  KEY `prestito_data_prestito_IDX` (`data_prestito`) USING BTREE,
  KEY `prestito_data_restituzione_IDX` (`data_restituzione`) USING BTREE,
  CONSTRAINT `prestito_FK` FOREIGN KEY (`codice_tessera`) REFERENCES `utente` (`codice_tessera`),
  CONSTRAINT `prestito_FK_1` FOREIGN KEY (`codice_biblioteca`) REFERENCES `libro` (`codice_biblioteca`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4;



INSERT INTO biblioteca.utente (codice_tessera,nominativo,data_tesseramento) VALUES
	 ('22-001','Anselmo Persichetti','2022-03-12'),
	 ('22-002','Vladimiro Bocci','2022-07-27'),
	 ('23-001','Rosa Fiorelli','2023-04-24'),
	 ('23-002','Osvaldo Bevilacqua','2023-11-03');

INSERT INTO biblioteca.libro (titolo,autore,editore,anno_edizione,note,codice_biblioteca,stato) VALUES
	 ('Il nome della rosa','Umberto Eco','Mondadori',2004,'NULL','22-001','L'),
	 ('Patagonia Express','Luis Sepulveda','Guanda',2008,'Il diario di viaggio di Sepúlveda in Patagonia e nella Terra del Fuoco: riflessioni, racconti, legge','22-002','L'),
	 ('Cent''anni di solitudine','Gabriel Garcia Marquez','Mondadori',1987,NULL,'23-001','P'),
	 ('Bar Sport','Stefano Benni','Feltrinelli',2001,'NULL','23-002','L'),
	 ('Io non ho paura','Niccolò Ammaniti','Einaudi',2001,NULL,'23-003','P');
	
INSERT INTO biblioteca.prestito (data_prestito,codice_biblioteca,codice_tessera,data_restituzione) VALUES
	 ('2023-12-01','23-001','23-001','2023-12-30'),
	 ('2022-08-02','23-001','22-001','2022-08-30'),
	 ('2023-10-14','23-001','22-002','2023-11-25'),
	 ('2022-07-15','23-001','22-001','2022-11-12'),
	 ('2022-03-11','23-002','22-001','2023-04-11'),
	 ('2022-04-02','23-002','22-002','2022-05-27'),
	 ('2023-05-01','23-002','23-001','2023-06-05'),
	 ('2023-06-08','23-002','23-001','2023-07-18'),
	 ('2023-12-01','22-001','22-001','2023-12-30'),
	 ('2022-08-02','22-001','22-002','2022-08-30');

INSERT INTO biblioteca.prestito (data_prestito,codice_biblioteca,codice_tessera,data_restituzione) VALUES
	 ('2023-10-14','22-001','23-001','2023-11-25'),
	 ('2022-07-15','22-001','22-002','2022-11-12'),
	 ('2022-03-11','22-002','22-001','2022-04-01'),
	 ('2022-04-02','22-002','22-002','2022-05-27'),
	 ('2023-05-01','22-002','23-001','2023-06-05'),
	 ('2023-06-08','22-002','23-001','2023-07-18'),
	 ('2023-09-11','23-003','22-001','2023-09-30'),
	 ('2022-10-11','23-003','22-002','2022-11-19'),
	 ('2023-12-01','23-001','22-001',NULL),
	 ('2023-12-02','23-003','22-002',NULL);
	
	