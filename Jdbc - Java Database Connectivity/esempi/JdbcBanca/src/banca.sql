CREATE DATABASE `banca` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

-- banca.cliente definition

CREATE TABLE `cliente` (
  `codice_fiscale` char(16) NOT NULL,
  `nominativo` varchar(50) NOT NULL,
  `indirizzo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`codice_fiscale`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


-- banca.movimento definition

CREATE TABLE `movimento` (
  `id_movimento` int(11) NOT NULL AUTO_INCREMENT COMMENT 'numero progressivo auto incrementatne del movimento',
  `data_ora_operazione` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT 'data ed ora dell''operazione',
  `importo` float unsigned NOT NULL DEFAULT 0 COMMENT 'importo del movimento',
  `tipo_operazione` char(1) NOT NULL COMMENT 'tipo di movimento: ''v''= versamento ; ''p'' = prelievo',
  `iban` char(32) NOT NULL COMMENT 'definito nella tabella conto',
  PRIMARY KEY (`id_movimento`) USING BTREE,
  CONSTRAINT `CheckTipoOperazione` CHECK (`tipo_operazione` = 'P' or `tipo_operazione` = 'V')
) ENGINE=InnoDB AUTO_INCREMENT=333 DEFAULT CHARSET=latin1;


-- banca.conto definition

CREATE TABLE `conto` (
  `valuta` char(3) NOT NULL DEFAULT 'EUR' COMMENT 'valuta del conto: euro o dollari (EUR o USD)',
  `scoperto` float NOT NULL DEFAULT 0 COMMENT 'massimo importo di scoperto concesso dalla banca quando il conto va in negativo: 500 significa fino a -500 euro',
  `codice_fiscale` char(16) NOT NULL COMMENT 'codice fiscale del cliente proprietrario del conto',
  `data_ora_intestazione` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT 'data ed ora in cui il conto è stato registrato in banca (data ora di creazione del conto)',
  `iban` char(32) NOT NULL COMMENT 'la banca ha filiali in Spagna ed in Italia: ''ES'' ed ''IT''. Il codice bancario internazionale: inizia con IT per l''italia. https://it.wikipedia.org/wiki/International_Bank_Account_Number',
  `saldo` float NOT NULL DEFAULT 0,
  PRIMARY KEY (`iban`) USING BTREE,
  KEY `FK_conto_cliente` (`codice_fiscale`),
  CONSTRAINT `FK_conto_cliente` FOREIGN KEY (`codice_fiscale`) REFERENCES `cliente` (`codice_fiscale`),
  CONSTRAINT `conto_check_valuta` CHECK (`valuta` = 'EUR' or `valuta` = 'USD' or `valuta` = 'GBP')
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
INSERT INTO banca.cliente (codice_fiscale,nominativo,indirizzo) VALUES
	 ('BNCFRL0123456789','Bianca Fiorelli',NULL),
	 ('GTNBGD0123456789','Gaetano Badalamenti',NULL),
	 ('MRRGVN0123456789','Giovanni Marrone',NULL),
	 ('SVCNGB0123456789','Anselmo Persichetti',NULL),
	 ('VLDBCC0123456789','Vladimiro Bocci',NULL);

INSERT INTO banca.conto (valuta,scoperto,codice_fiscale,data_ora_intestazione,iban) VALUES
	 ('GBP',345.0,'MRRGVN0123456789','2023-01-10 09:13:14','DEaa0123456789012345678901234567'),
	 ('GBP',523.0,'MRRGVN0123456789','2023-07-25 12:06:15','DEbb0123456789012345678901234567'),
	 ('EUR',255.0,'VLDBCC0123456789','2023-11-24 12:33:42','ESaa0123456789012345678901234567'),
	 ('EUR',150.0,'GTNBGD0123456789','2023-11-24 12:33:42','EScc0123456789012345678901234567'),
	 ('EUR',345.0,'SVCNGB0123456789','2023-11-24 12:36:19','ESdd0123456789012345678901234567'),
	 ('USD',150.0,'SVCNGB0123456789','2023-11-24 12:36:19','ESee0123456789012345678901234567'),
	 ('GBP',987.0,'MRRGVN0123456789','2023-12-31 14:39:47','FRaa0123456789012345678901234567'),
	 ('EUR',500.0,'VLDBCC0123456789','2023-11-24 12:33:42','ITbb0123456789012345678901234567'),
	 ('EUR',955.0,'VLDBCC0123456789','2023-12-24 23:59:59','ITdd0123456789012345678901234567'),
	 ('EUR',0.0,'GTNBGD0123456789','2023-11-24 12:33:42','ITee0123456789012345678901234567'),
	 ('EUR',1000.0,'SVCNGB0123456789','2023-12-24 23:59:59','ITff0123456789012345678901234567');





INSERT INTO banca.movimento (data_ora_operazione,importo,tipo_operazione,iban) VALUES
	 ('2023-11-24 12:42:50',450.0,'v','ESaa0123456789012345678901234567'),
	 ('2023-11-24 12:48:36',555.0,'V','ITff0123456789012345678901234567'),
	 ('2023-11-24 12:53:28',555.0,'V','ITff0123456789012345678901234567'),
	 ('2023-11-24 12:53:28',3650.0,'P','ESee0123456789012345678901234567'),
	 ('2023-11-24 12:53:28',9807.0,'V','ITff0123456789012345678901234567'),
	 ('2023-11-24 12:53:28',1200.0,'P','ESaa0123456789012345678901234567'),
	 ('2023-11-24 12:53:28',789.0,'V','ITbb0123456789012345678901234567'),
	 ('2023-11-24 12:53:28',660.0,'P','ESdd0123456789012345678901234567'),
	 ('2023-11-24 12:53:28',1345.0,'V','ESaa0123456789012345678901234567'),
	 ('2023-11-24 12:53:28',9800.0,'P','ESee0123456789012345678901234567');

INSERT INTO banca.movimento (data_ora_operazione,importo,tipo_operazione,iban) VALUES
	 ('2023-11-24 12:53:28',65.0,'V','ESaa0123456789012345678901234567'),
	 ('2023-11-24 12:53:28',1876.0,'P','ITee0123456789012345678901234567'),
	 ('2023-11-24 12:53:28',598.0,'V','ESaa0123456789012345678901234567'),
	 ('2023-11-24 12:53:28',1234.0,'P','ESaa0123456789012345678901234567'),
	 ('2023-11-24 12:53:28',110.0,'V','ITff0123456789012345678901234567'),
	 ('2023-11-24 12:53:28',2400.0,'P','ESee0123456789012345678901234567'),
	 ('2023-11-24 12:42:50',450.0,'v','ESaa0123456789012345678901234567'),
	 ('2023-11-24 12:48:36',555.0,'V','ITff0123456789012345678901234567'),
	 ('2023-11-24 12:53:28',555.0,'V','ITff0123456789012345678901234567'),
	 ('2023-11-24 12:53:28',3650.0,'P','ESee0123456789012345678901234567');

INSERT INTO banca.movimento (data_ora_operazione,importo,tipo_operazione,iban) VALUES
	 ('2023-11-24 12:53:28',9807.0,'V','ITff0123456789012345678901234567'),
	 ('2023-11-24 12:53:28',1200.0,'P','ESaa0123456789012345678901234567'),
	 ('2023-11-24 12:53:28',789.0,'V','ITbb0123456789012345678901234567'),
	 ('2023-11-24 12:53:28',660.0,'P','ESdd0123456789012345678901234567'),
	 ('2023-11-24 12:53:28',1345.0,'V','ESaa0123456789012345678901234567'),
	 ('2023-11-24 12:53:28',9800.0,'P','ESee0123456789012345678901234567'),
	 ('2023-11-24 12:53:28',65.0,'V','ESaa0123456789012345678901234567'),
	 ('2023-11-24 12:53:28',1876.0,'P','ITee0123456789012345678901234567'),
	 ('2023-11-24 12:53:28',598.0,'V','ESaa0123456789012345678901234567'),
	 ('2023-11-24 12:53:28',1234.0,'P','ESaa0123456789012345678901234567');

INSERT INTO banca.movimento (data_ora_operazione,importo,tipo_operazione,iban) VALUES
	 ('2023-11-24 12:53:28',110.0,'V','ITff0123456789012345678901234567'),
	 ('2023-11-24 12:53:28',2400.0,'P','ESee0123456789012345678901234567'),
	 ('2023-11-30 12:42:50',225.0,'v','ESaa0123456789012345678901234567'),
	 ('2023-11-24 12:42:50',678.0,'V','DEaa0123456789012345678901234567'),
	 ('2023-11-25 14:48:36',543.0,'V','DEaa0123456789012345678901234567'),
	 ('2023-11-26 13:48:36',963.0,'V','DEaa0123456789012345678901234567'),
	 ('2023-11-27 02:48:36',367.0,'P','DEaa0123456789012345678901234567'),
	 ('2023-11-28 03:48:36',1543.0,'P','DEaa0123456789012345678901234567'),
	 ('2023-10-12 12:42:55',78.0,'V','DEbb0123456789012345678901234567'),
	 ('2023-09-05 14:48:36',133.0,'V','DEbb0123456789012345678901234567');

INSERT INTO banca.movimento (data_ora_operazione,importo,tipo_operazione,iban) VALUES
	 ('2023-11-16 13:48:16',6923.0,'V','DEbb0123456789012345678901234567'),
	 ('2023-12-07 02:08:11',635.0,'P','DEbb0123456789012345678901234567'),
	 ('2023-01-28 03:04:09',4990.0,'P','DEbb0123456789012345678901234567');
