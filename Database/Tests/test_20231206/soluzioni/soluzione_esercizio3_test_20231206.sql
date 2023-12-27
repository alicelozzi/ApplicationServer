-- **************************************************************************************
-- **************************************************************************************
-- **************************************************************************************
--        SOLUZIONI ALL'ESERCIZIO NUMERO 3 DEL TEST DEL 6 dicembre 2023 (DATABASE) 
-- **************************************************************************************
-- **************************************************************************************
-- **************************************************************************************


-- **************************************************************************************
--                                    PARTE 1
-- **************************************************************************************

CREATE DATABASE `competizioni` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

CREATE TABLE `gara` (
  `id_gara` int(11) NOT NULL AUTO_INCREMENT,
  `data_gara` date NOT NULL,
  `luogo` varchar(20) NOT NULL,
  `tipologia` char(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id_gara`),
  CONSTRAINT `gara_check` CHECK (`tipologia` = '1' or `tipologia` = '2' or `tipologia` = '4')
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `iscrizione` (
  `id_gara` int(11) NOT NULL,
  `codice_fiscale` char(16) CHARACTER SET latin1 NOT NULL,
  `data_iscrizione` date DEFAULT NULL,
  PRIMARY KEY (`codice_fiscale`,`id_gara`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `partecipazione` (
  `id_gara` int(11) NOT NULL,
  `codice_fiscale` char(16) CHARACTER SET latin1 NOT NULL,
  `tempo` float NOT NULL,
  PRIMARY KEY (`id_gara`,`codice_fiscale`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `velocista` (
  `codice_fiscale` char(16) CHARACTER SET latin1 NOT NULL,
  `nominativo` varchar(50) CHARACTER SET latin1 NOT NULL,
  PRIMARY KEY (`codice_fiscale`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;