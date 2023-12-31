
----------------------------------------------------------------
-- CREAZIONE DEL DATABASE MAGAZZINO                           --
-- CONTENITORE DI TABELLE CHE DERIVANO DA ENTITA' E RELAZIONI --
----------------------------------------------------------------
CREATE DATABASE `magazzino` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

------------------------------------------------------------------------
-- LA TABELLA 'cliente' CONTIENE GLI ATTRIBUTI DELL'ENTITA' 'CLIENTE' -- 
-- TALI ATTRIBUTI RAPPRESENTANO I CAMPI DELLA TABELLA                 --
-- 																	  --
-- SI DEFINISCE INOLTRE IL VINCOLO DI CHIAVE PRIMARIA (PRIAMRY KEY)							 		--
-- LA PRIMARY KEY DELLA TABELLA 'cliente' E' IL 'codice_fiscale'      --
-- NON SONO AMMESSE DUPLICAZIONI DI ISTANZE RECORD cliente    		  --
-- CHE ABBIANO LO STESSO 'codice_fiscale' 							  --
------------------------------------------------------------------------
CREATE TABLE `cliente` (
  `codice_fiscale` char(16) NOT NULL,
  `nominativo` varchar(50) NOT NULL DEFAULT '',
  PRIMARY KEY (`codice_fiscale`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--------------------------------------------------------------------------
-- LA TABELLA 'prodotto' CONTIENE GLI ATTRIBUTI DELL'ENTITA' 'PRODOTTO' -- 
-- TALI ATTRIBUTI RAPPRESENTANO I CAMPI DELLA TABELLA 'prodotto'        --
-- 																		--
-- LA PRIMARY KEY DELLA TABELLA 'prodotto' E' IL 'codice_prodotto'      --
-- NON SONO AMMESSE DUPLICAZIONI DI ISTANZE RECORD prodotto    		    --
-- CHE ABBIANO LO STESSO 'codice_prodotto' 							    --
--------------------------------------------------------------------------
CREATE TABLE `prodotto` (
  `codice_prodotto` char(8) NOT NULL,
  `descrizione` varchar(255) NOT NULL,
  `quantita_disponibile` smallint(5) unsigned NOT NULL,
  `prezzo` float unsigned NOT NULL DEFAULT 0,
  PRIMARY KEY (`codice_prodotto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


------------------------------------------------------------------------------------------------------
-- LA TABELLA 'prodotto' CONTIENE GLI ATTRIBUTI DELLA RELAZIONE' 'ORDINAZIONE'               		-- 
-- TALI ATTRIBUTI RAPPRESENTANO I CAMPI DELLA TABELLA                          			     		--
-- 																							 		--
-- SI DEFINISCE INOLTRE IL VINCOLO DI CHIAVE PRIMARIA (PRIAMRY KEY)							 		--
-- LA PRIMARY KEY DELLA TABELLA 'ordinazione' E' COMPOSTA DALLA COMBINAZIONE DEI TRE CAMPI:  		--
-- 'codice_fiscale', 'codice_prodotto', 'data_ordine'. 											    --
-- NOTA IN MERTIO: questo tipo di impostazione della primary key fa sì che non possa effettuare più --
-- EFFETTUARE PIU' DI UN ORDINAZIONE AL GIORNO DELLOS TESSO PRODOTTO 								--
-- VI SONO ANCHE DUE CHIAVI CORRELATE (FOREIGN KEY): IL LORO SIGNIFICATO E' CHE NON POSSO 	 		--
-- AVERE UN ISTANZA-RECORD ORDINAZIONE SE IL SUO 'codice_fiscale' od il 'codice_prodotto'	 		--
-- NON SONO PRESENTI CON UN ISTANZA-RECORD CHE CONTENGA TALE VALORE: per il 'codice_fiscale' 		--
-- nella tabella 'clienteì e pèer il 'codice_prodotto' nella tabella 'prodotto' 		 			--
------------------------------------------------------------------------------------------------------
CREATE TABLE `ordinazione` (
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



---------------------------------------------------------------
-- INSERIMENTO DELLE ISTANZE-RECORD NELLA TABELLA 'prodotto' --
---------------------------------------------------------------

INSERT INTO magazzino.prodotto (codice_prodotto,descrizione,quantita_disponibile,prezzo) VALUES
	 ('CPAPMB20','Computer Apple Mini Book 20',4,1123.0),
	 ('CPHPR456','Computer HP Real 456',4,367.0),
	 ('LVDR1500','Lavatrice Dragon 1500',4,624.0),
	 ('LVZPXS54','Lavatrice Zoppas XS54',2,526.0),
	 ('TVLGX400','Televisore LG X400',3,475.0),
	 ('TVSMR230','Televisore Samsung Root 2300',0,982.0);

	
---------------------------------------------------------------
-- INSERIMENTO DELLE ISTANZE-RECORD NELLA TABELLA 'cliente' --
---------------------------------------------------------------
INSERT INTO magazzino.cliente (codice_fiscale,nominativo) VALUES
	 ('BVEGTA0123456789','Gaetano Bove'),
	 ('FDERMN0123456789','Ramona Fede'),
	 ('PRSANS0123456789','Anselmo Persichetti'),
	 ('PSQFNC0123456789','Pasquini Francesco');

------------------------------------------------------------------------
-- INSERIMENTO DELLE ISTANZE-RECORD NELLA TABELLA 'ordinazione'       --
-- QUESTE INSERT-SQL VENOGNO EFFETTUATE DOPO AVER POPOLATO LE         --
-- TABELLE 'cliente' e 'prodotto' PER LE RAGIONI LEGATE A NON VIOLARE --
-- IL VINCOLO DI CHIAVE CORRELATA (FOREIGN KEY)
------------------------------------------------------------------------
INSERT INTO magazzino.ordinazione (codice_fiscale,codice_prodotto,data_ordine,quantita_ordine,prezzo_acquisto) VALUES
	 ('BVEGTA0123456789','LVDR1500','2022-05-03',3,624.0),
	 ('BVEGTA0123456789','TVLGX400','2022-05-03',4,475.0),
	 ('FDERMN0123456789','CPHPR456','2022-05-03',9,367.0),
	 ('FDERMN0123456789','LVZPXS54','2022-05-03',2,526.0),
	 ('PRSANS0123456789','CPAPMB20','2022-05-03',1,1123.0),
	 ('PRSANS0123456789','LVZPXS54','2022-05-03',2,526.0),
	 ('PSQFNC0123456789','TVLGX400','2022-05-03',5,475.0),
	 ('PSQFNC0123456789','TVSMR230','2022-05-03',3,982.0);


