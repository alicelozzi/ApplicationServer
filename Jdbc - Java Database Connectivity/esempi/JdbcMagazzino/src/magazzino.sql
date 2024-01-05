-- --------------------------------------------------------
-- Host:                         localhost
-- Versione server:              10.4.17-MariaDB - mariadb.org binary distribution
-- S.O. server:                  Win64
-- HeidiSQL Versione:            11.2.0.6213
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dump della struttura del database magazzino
CREATE DATABASE IF NOT EXISTS `magazzino` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;
USE `magazzino`;

-- Dump della struttura di tabella magazzino.cliente
CREATE TABLE IF NOT EXISTS `cliente` (
  `codice_fiscale` char(16) NOT NULL,
  `nominativo` varchar(50) NOT NULL DEFAULT '',
  PRIMARY KEY (`codice_fiscale`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Dump dei dati della tabella magazzino.cliente: ~1 rows (circa)
/*!40000 ALTER TABLE `cliente` DISABLE KEYS */;
INSERT INTO `cliente` (`codice_fiscale`, `nominativo`) VALUES
	('BVEGTA0123456789', 'Gaetano Bove'),
	('FDERMN0123456789', 'Ramona Fede'),
	('PRSANS0123456789', 'Anselmo Persichetti'),
	('PSQFNC0123456789', 'Pasquini Francesco');
/*!40000 ALTER TABLE `cliente` ENABLE KEYS */;

-- Dump della struttura di tabella magazzino.ordinazione
CREATE TABLE IF NOT EXISTS `ordinazione` (
  `codice_fiscale` char(16) NOT NULL,
  `codice_prodotto` char(8) NOT NULL,
  `data_ordine` date NOT NULL DEFAULT curdate(),
  `quantita_ordine` smallint(5) unsigned NOT NULL DEFAULT 0,
  `prezzo_acquisto` float unsigned NOT NULL DEFAULT 0,
  PRIMARY KEY (`codice_fiscale`,`codice_prodotto`,`data_ordine`),
  KEY `FK_ordinazione_prodotto` (`codice_prodotto`),
  CONSTRAINT `FK_ordinazione_cliente` FOREIGN KEY (`codice_fiscale`) REFERENCES `cliente` (`codice_fiscale`),
  CONSTRAINT `FK_ordinazione_prodotto` FOREIGN KEY (`codice_prodotto`) REFERENCES `prodotto` (`codice_prodotto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Dump dei dati della tabella magazzino.ordinazione: ~2 rows (circa)
/*!40000 ALTER TABLE `ordinazione` DISABLE KEYS */;
INSERT INTO `ordinazione` (`codice_fiscale`, `codice_prodotto`, `data_ordine`, `quantita_ordine`, `prezzo_acquisto`) VALUES
	('BVEGTA0123456789', 'LVDR1500', '2022-04-30', 3, 624),
	('BVEGTA0123456789', 'TVLGX400', '2022-04-30', 4, 475),
	('FDERMN0123456789', 'CPHPR456', '2022-04-30', 9, 367),
	('FDERMN0123456789', 'LVZPXS54', '2022-04-30', 2, 526),
	('PRSANS0123456789', 'CPAPMB20', '2022-04-30', 1, 1123),
	('PRSANS0123456789', 'LVZPXS54', '2022-04-30', 2, 526),
	('PSQFNC0123456789', 'TVLGX400', '2022-04-30', 5, 475),
	('PSQFNC0123456789', 'TVSMR230', '2022-04-30', 3, 982);
/*!40000 ALTER TABLE `ordinazione` ENABLE KEYS */;

-- Dump della struttura di tabella magazzino.prodotto
CREATE TABLE IF NOT EXISTS `prodotto` (
  `codice_prodotto` char(8) NOT NULL,
  `descrizione` varchar(255) NOT NULL,
  `quantita_disponibile` smallint(5) unsigned NOT NULL,
  `prezzo` float unsigned NOT NULL DEFAULT 0,
  PRIMARY KEY (`codice_prodotto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Dump dei dati della tabella magazzino.prodotto: ~3 rows (circa)
/*!40000 ALTER TABLE `prodotto` DISABLE KEYS */;
INSERT INTO `prodotto` (`codice_prodotto`, `descrizione`, `quantita_disponibile`, `prezzo`) VALUES
	('CPAPMB20', 'Computer Apple Mini Book 20', 4, 1123),
	('CPHPR456', 'Computer HP Real 456', 4, 367),
	('LVDR1500', 'Lavatrice Dragon 1500', 4, 624),
	('LVZPXS54', 'Lavatrice Zoppas XS54', 2, 526),
	('TVLGX400', 'Televisore LG X400', 3, 475),
	('TVSMR230', 'Televisore Samsung Root 2300', 0, 982);
/*!40000 ALTER TABLE `prodotto` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
