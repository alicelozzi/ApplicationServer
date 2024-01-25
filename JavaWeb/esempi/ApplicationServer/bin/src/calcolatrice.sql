-- --------------------------------------------------------
-- Host:                         127.0.0.1
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


-- Dump della struttura del database calcolatrice
CREATE DATABASE IF NOT EXISTS `calcolatrice` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;
USE `calcolatrice`;

-- Dump della struttura di tabella calcolatrice.calcolo
CREATE TABLE IF NOT EXISTS `calcolo` (
  `idCalcolo` int(11) NOT NULL AUTO_INCREMENT,
  `idUtente` int(11) NOT NULL,
  `dataOraCalcolo` timestamp NOT NULL DEFAULT current_timestamp(),
  `operando1` decimal(20,2) NOT NULL DEFAULT 0.00,
  `operando2` decimal(20,2) NOT NULL DEFAULT 0.00,
  `operazione` char(1) NOT NULL DEFAULT '0',
  `risultato` decimal(20,2) NOT NULL,
  PRIMARY KEY (`idCalcolo`),
  CONSTRAINT `CC1` CHECK (`operazione` = '+' or `operazione` = '-' or `operazione` = '*' or `operazione` = '/')
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;

-- Dump dei dati della tabella calcolatrice.calcolo: ~2 rows (circa)
/*!40000 ALTER TABLE `calcolo` DISABLE KEYS */;
INSERT INTO `calcolo` (`idCalcolo`, `idUtente`, `dataOraCalcolo`, `operando1`, `operando2`, `operazione`, `risultato`) VALUES
	(2, 2, '2021-12-14 19:59:34', 1.00, 2.00, '+', 3.00),
	(3, 3, '2022-04-28 15:33:44', 4.00, 3.00, '*', 12.00);
/*!40000 ALTER TABLE `calcolo` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
