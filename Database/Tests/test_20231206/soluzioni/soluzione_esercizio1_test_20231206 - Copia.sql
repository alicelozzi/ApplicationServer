-- **************************************************************************************
-- **************************************************************************************
-- **************************************************************************************
--        SOLUZIONI ALL'ESERCIZIO NUMERO 1 DEL TEST DEL 6 dicembre 2023 (DATABASE) 
-- **************************************************************************************
-- **************************************************************************************
-- **************************************************************************************


-- **************************************************************************************
--                                    PARTE 1
-- **************************************************************************************
CREATE DATABASE `trasporti` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

CREATE TABLE `cittadino` (
  `codice_fiscale` char(16) NOT NULL,
  `nominativo` varchar(50) NOT NULL DEFAULT '',
  `zona_residenza` char(1) NOT NULL,
  PRIMARY KEY (`codice_fiscale`),
  CONSTRAINT `cittadino_check_zona_residenza` CHECK (`zona_residenza` = 'A' or `zona_residenza` = 'B' or `zona_residenza` = 'C' or `zona_residenza` = 'D')
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `mezzo` (
  `codice_mezzo` char(6) NOT NULL,
  `anno_produzione` smallint(5) unsigned NOT NULL,
  `limite_km_percorribili` mediumint(8) unsigned NOT NULL,
  `capienza_massima` mediumint(8) unsigned NOT NULL,
  `descrizione` varchar(100) DEFAULT NULL,
  `indice_ecologico` tinyint(4) NOT NULL,
  PRIMARY KEY (`codice_mezzo`),
  CONSTRAINT `mezzo_check_indice_ecologico` CHECK (`indice_ecologico` >= 0 and `indice_ecologico` <= 5)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `spostamento` (
  `id_spostamento` int(11) NOT NULL AUTO_INCREMENT,
  `data_spostamento` date NOT NULL,
  `codice_fiscale` char(16) NOT NULL,
  `codice_mezzo` char(6) NOT NULL,
  `km_percorsi` tinyint(3) unsigned NOT NULL,
  PRIMARY KEY (`id_spostamento`),
  KEY `spostamento_mezzo_FK` (`codice_mezzo`),
  KEY `spostamento_cittadino_FK` (`codice_fiscale`),
  CONSTRAINT `spostamento_cittadino_FK` FOREIGN KEY (`codice_fiscale`) REFERENCES `cittadino` (`codice_fiscale`),
  CONSTRAINT `spostamento_mezzo_FK` FOREIGN KEY (`codice_mezzo`) REFERENCES `mezzo` (`codice_mezzo`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4;


-- **************************************************************************************
--                                    PARTE 2
-- **************************************************************************************
INSERT INTO trasporti.cittadino (codice_fiscale,nominativo,zona_residenza) VALUES
	 ('ANSPRS0123456789','Anselmo Persichetti','B'),
	 ('BNCFRL0123456789','Bianca Fiorelli','A'),
	 ('DNLVRD0123456789','Danilo Verdi','A'),
	 ('EDRFRS0123456789','Edoardo Fraiese','C'),
	 ('GNNDSS0123456789','Gianni Di Sisto','D'),
	 ('MRRGVN0123456789','Giovanni Marrone','B'),
	 ('SLVFRM0123456789','Salvo Fermini','D'),
	 ('VLDBCC0123456789','Vladimiro Bocci','C');

INSERT INTO trasporti.mezzo (codice_mezzo,anno_produzione,limite_km_percorribili,capienza_massima,descrizione,indice_ecologico) VALUES
	 ('BS-001',2008,1500000,45,'Bus numero 001',1),
	 ('BS-002',2019,2205000,55,'Bus numero 002',3),
	 ('FN-001',2001,1225000,40,'Funicolare numero 001',5),
	 ('FN-002',2014,1350000,55,'Funicolare numero 002',5),
	 ('MT-001',1980,5200000,500,'Metro numero 001',4),
	 ('MT-002',1998,6500000,700,'Metro numero 002',5),
	 ('TM-001',1996,1567000,105,'Tram numero 001',3),
	 ('TM-002',1975,1320000,98,'Tram numero 002',2),
	 ('TN-001',2022,4900000,450,'Treno numero 001',4),
	 ('TN-002',2015,3788000,300,'Treno numero 002',3);


INSERT INTO trasporti.spostamento (data_spostamento,codice_fiscale,codice_mezzo,km_percorsi) VALUES
	 ('2022-01-23','BNCFRL0123456789','TM-001',12),
	 ('2023-11-20','VLDBCC0123456789','TM-001',10),
	 ('2023-05-12','EDRFRS0123456789','TN-001',27),
	 ('2022-10-27','GNNDSS0123456789','TN-001',34),
	 ('2022-10-27','DNLVRD0123456789','MT-001',41),
	 ('2023-05-12','BNCFRL0123456789','MT-001',44),
	 ('2023-11-20','DNLVRD0123456789','FN-001',6),
	 ('2022-01-23','SLVFRM0123456789','FN-001',7),
	 ('2023-05-12','SLVFRM0123456789','BS-001',15),
	 ('2023-11-20','VLDBCC0123456789','BS-001',19);
INSERT INTO trasporti.spostamento (data_spostamento,codice_fiscale,codice_mezzo,km_percorsi) VALUES
	 ('2023-05-12','VLDBCC0123456789','TM-001',8),
	 ('2022-10-27','VLDBCC0123456789','TM-001',11),
	 ('2023-05-12','GNNDSS0123456789','TN-001',23),
	 ('2023-11-20','SLVFRM0123456789','TN-001',37),
	 ('2022-10-27','EDRFRS0123456789','MT-001',60),
	 ('2023-05-12','SLVFRM0123456789','MT-001',45),
	 ('2023-11-20','DNLVRD0123456789','FN-001',5),
	 ('2023-11-20','DNLVRD0123456789','FN-001',9),
	 ('2022-10-27','BNCFRL0123456789','BS-001',12),
	 ('2023-05-12','BNCFRL0123456789','BS-001',21);


-- **************************************************************************************
--                                    PARTE 3
-- **************************************************************************************

-- a) selezionare tutti i mezzi di trasporto di tipo tram
SELECT mezzo.*
  FROM mezzo 
 WHERE mezzo.codice_mezzo like 'TN%'
 
-- b) selezionare tutti i mezzi di trasporto che sono bus o metro
SELECT mezzo.*
  FROM mezzo 
 WHERE mezzo.codice_mezzo like 'BS%'
    OR mezzo.codice_mezzo like 'MT%'
  
-- c) selezionare tutti i cittadini che risiedono o nella zona 'A' o nella zona 'D' 
SELECT cittadino.*
  FROM cittadino 
 WHERE cittadino.zona_residenza IN ('A', 'D')
    OR mezzo.codice_mezzo like 'MT%'
     
-- d) selezionare tutti gli spostamenti effettuati in una determinata data 
SELECT spostamento.*
  FROM spostamento 
 WHERE spostamento.data_spostamento = '2023-05-12'
    
-- e) selezionare tutti gli spostamenti effettuati in un intervallo di date
SELECT spostamento.*
  FROM spostamento 
 WHERE spostamento.data_spostamento BETWEEN '2022-10-27'AND '2023-11-20'
 
 -- f) calcolare il totale di chilometri percorsi da un determinato cittadino di cui è noto il codice fiscale,
 --   indipendentemente dal mezzo di trasporto usato
SELECT spostamento.codice_fiscale, SUM(spostamento.km_percorsi)
  FROM spostamento 
 WHERE spostamento.codice_fiscale = 'EDRFRS0123456789'
 GROUP BY spostamento.codice_fiscale 
 
-- oppure, con tutti i dati del cittadino (con uso di INNER JOIN), ugualmente efficace ma meno efficiente   

SELECT cittadino.*, SUM(spostamento.km_percorsi)
  FROM spostamento inner join cittadino on spostamento.codice_fiscale = cittadino.codice_fiscale  
 WHERE spostamento.codice_fiscale = 'EDRFRS0123456789'
 GROUP BY spostamento.codice_fiscale 
 
-- g) calcolare il totale di chilometri percorsi dai cittadini della zona 'B' o 'C', 
--    indipendentemente dal mezzo di trasporto usato
SELECT SUM(spostamento.km_percorsi) AS totale_chilometri_persorsi_da_residenti_B_C
  FROM spostamento INNER JOIN cittadino ON spostamento.codice_fiscale = cittadino.codice_fiscale  
 WHERE cittadino.zona_residenza IN ('B', 'C')
 
-- h) selezionare tutti i cittadini senza spostamenti
SELECT cittadino.* -- , spostamento.id_spostamento (usare questo campo in output per la controprova)
  FROM cittadino LEFT JOIN spostamento ON spostamento.codice_fiscale = cittadino.codice_fiscale  
 WHERE spostamento.id_spostamento is NULL
 
-- i) selezionare tutti i mezzi senza spostamenti
SELECT mezzo.* -- , spostamento.id_spostamento (usare questo campo in output per la controprova)
  FROM mezzo LEFT JOIN spostamento ON spostamento.codice_mezzo = mezzo.codice_mezzo  
 WHERE spostamento.id_spostamento is NULL
 
 
-- j) selezionare tutti i mezzi, con indice ecologico superiore al valore 2, usati da cittadini il cui
--    nominativo contiene tre caratteri a scelta
SELECT DISTINCT mezzo.*
  FROM spostamento INNER JOIN cittadino ON spostamento.codice_fiscale = cittadino.codice_fiscale  
                   INNER JOIN mezzo ON spostamento.codice_mezzo = mezzo.codice_mezzo
 WHERE mezzo.indice_ecologico > 2
   AND cittadino.nominativo LIKE '%nni%'
 
-- k) selezionare tutti i cittadini che con i mezzi hanno percorso più di X chilometri (con X valore a scelta)
SELECT cittadino.*, SUM(spostamento.km_percorsi) AS totale_chilometri_persorsi_dal_cittadino
  FROM spostamento INNER JOIN cittadino ON spostamento.codice_fiscale = cittadino.codice_fiscale  
 GROUP BY spostamento.codice_fiscale  
HAVING totale_chilometri_persorsi_dal_cittadino > 63 
   
-- l) calcolare il totale di chilometri percorsi da un determinato mezzo (vedi formula fittizia di calcolo sui
--    chilometri percorsi da un mezzo. NOTA: usare l'operatore SQL "/" per la divisione, per confronto
--    consultare anche la documentazione online)
SELECT mezzo.*, SUM(spostamento.km_percorsi) / mezzo.capienza_massima AS totale_chilometri_persorsi_dal_mezzo
  FROM spostamento INNER JOIN mezzo ON spostamento.codice_mezzo = mezzo.codice_mezzo  
 WHERE mezzo.codice_mezzo = 'BS-001' 
 GROUP BY spostamento.codice_mezzo  


-- m) calcolare il totale di chilometri percorsi da ciascun mezzo (riferimento codice_mezzo)
SELECT spostamento.codice_mezzo, (SUM(spostamento.km_percorsi) / mezzo.capienza_massima) AS totale_chilometri_persorsi_dal_mezzo
  FROM spostamento INNER JOIN mezzo ON spostamento.codice_mezzo = mezzo.codice_mezzo  
 GROUP BY spostamento.codice_mezzo  

-- n) avendo effettuato il calcolo di cui al punto m): 
--    calcolare il totale dei chilometri percorsi dai tram
--    (SUM con query annidata...con formula fittizia)
SELECT SUM(totale_chilometri_persorsi_dal_mezzo) AS totale_chilometri_persorsi_dai_tram
  FROM mezzo LEFT JOIN (
						SELECT spostamento.codice_mezzo, SUM(spostamento.km_percorsi) / mezzo.capienza_massima AS totale_chilometri_persorsi_dal_mezzo
						  FROM spostamento INNER JOIN mezzo ON spostamento.codice_mezzo = mezzo.codice_mezzo  
						 GROUP BY spostamento.codice_mezzo  
					   ) as codice_mezzo_con_chilometri_percorsi 
				ON mezzo.codice_mezzo = codice_mezzo_con_chilometri_percorsi.codice_mezzo
 WHERE mezzo.codice_mezzo LIKE 'TM%' 				
   
-- o) selezionare tutti i mezzi con cui sono stati percorsi percorso più di Y chilometri 
--    (con Y valore a vostra scelta)
SELECT mezzo.*, (SUM(spostamento.km_percorsi) / mezzo.capienza_massima) AS totale_chilometri_persorsi_dal_mezzo
  FROM spostamento INNER JOIN mezzo ON spostamento.codice_mezzo = mezzo.codice_mezzo  
 GROUP BY spostamento.codice_mezzo  
HAVING totale_chilometri_persorsi_dal_mezzo > 0.5 
 
-- p) selezionare tutti i mezzi che hanno superato il limite di chilometri percorribili 
SELECT mezzo.*, (SUM(spostamento.km_percorsi) / mezzo.capienza_massima) AS totale_chilometri_persorsi_dal_mezzo
  FROM spostamento INNER JOIN mezzo ON spostamento.codice_mezzo = mezzo.codice_mezzo  
 GROUP BY spostamento.codice_mezzo  
HAVING totale_chilometri_persorsi_dal_mezzo > mezzo.limite_km_percorribili  

-- r) eliminare tutti gli spostamenti effettuati dai cittadini della zona 'A' 
--    (query annidata in condizione 'WHERE')
DELETE -- usare SELECT * per il test della query prima di eseguire il comando SQL DELETE
  FROM spostamento
 WHERE spostamento.id_spostamento IN (
 										SELECT id_spostamento 
                                          FROM spostamento INNER JOIN cittadino on spostamento.codice_fiscale = cittadino.codice_fiscale
                                         WHERE cittadino.zona_residenza = 'A'
                                	 )

-- s) aggiornare al valore 45 i chilometri percorsi negli spostamenti per i cittadini della zona 'C' (query
--    annidata in condizione 'WHERE').
UPDATE spostamento -- usare SELECT * per il test della query prima di eseguire il comando SQL UPDATE
   SET km_percorsi = 45
 WHERE spostamento.id_spostamento IN (
 										SELECT id_spostamento 
                                          FROM spostamento INNER JOIN cittadino on spostamento.codice_fiscale = cittadino.codice_fiscale
                                         WHERE cittadino.zona_residenza = 'C'
                                	 )

                                	 
                                	 
                                	 
-- **************************************************************************************
--                                    PARTE 4
-- **************************************************************************************

-- ATTENZIONE: i trigger vanno inseriti e testati uno per volta, nell'ordine a), b), c)
                                	 
-- a) impedire l'inserimento di uno spostamento per un cittadino della zona 'A' 
CREATE DEFINER=`root`@`localhost` TRIGGER `no_insert_spostamento_per_cittadini_zona_a`
BEFORE INSERT ON spostamento FOR EACH ROW
BEGIN
	
	DECLARE zona_residenza_cittadino CHAR(1) DEFAULT '';
	-- residenza cittadino da leggere tramite SELECT (vedi cursore)

	-- il cursore rappresenta una variabile tabella in memoria RAM
	DECLARE cursore_cittadino_zona_a CURSOR FOR   
		SELECT cittadino.zona_residenza
		  FROM cittadino 
		 WHERE cittadino.codice_fiscale = new.codice_fiscale;  -- valore codice_fiscale del nuovo record spostamento che si sta inserendo
				
 	OPEN cursore_cittadino_zona_a; 
 	FETCH cursore_cittadino_zona_a into zona_residenza_cittadino; 
 	CLOSE cursore_cittadino_zona_a; 
	
	IF (zona_residenza_cittadino = 'A') THEN
		SIGNAL SQLSTATE '75000' SET MESSAGE_TEXT="impossibile inserire lo spostamento per il cittadino: è della zona 'A'!!!";
	END IF;

END                                	 

-- query di insert usate per il test, cittadino della zona a e cittadino di altra zona:
-- inserimento spostamento da impedire
INSERT INTO trasporti.spostamento 
     (data_spostamento,codice_fiscale,codice_mezzo,km_percorsi) VALUES
	 ('2022-01-23','BNCFRL0123456789','TM-002',11)

-- inserimento spostamento ammesso
INSERT INTO trasporti.spostamento 
     (data_spostamento,codice_fiscale,codice_mezzo,km_percorsi) VALUES
	 ('2022-01-23','EDRFRS0123456789','TM-002',11)
	 
-- b) impedire l'inserimento di uno spostamento per un cittadino 
--    che ha già percorso X chilometri (X valore a scelta) 
CREATE DEFINER=`root`@`localhost` TRIGGER `no_insert_spostamento_per_cittadino_oltre_x_km`
BEFORE INSERT ON spostamento FOR EACH ROW
BEGIN
	
	DECLARE km_percorsi_cittadino SMALLINT UNSIGNED;
	-- km percorsi dal cittadino

	-- il cursore rappresenta una variabile tabella in memoria RAM
	DECLARE cursore_calcolo_km_percorsi_cittadino CURSOR FOR   
		SELECT SUM(spostamento.km_percorsi) as km_percorsi_cittadino_calcolati
		  FROM spostamento   
		 WHERE spostamento.codice_fiscale = new.codice_fiscale;
		 
				
 	OPEN cursore_calcolo_km_percorsi_cittadino; 
 	FETCH cursore_calcolo_km_percorsi_cittadino into km_percorsi_cittadino; 
 	CLOSE cursore_calcolo_km_percorsi_cittadino; 
	
	IF (km_percorsi_cittadino > 50) THEN
		SIGNAL SQLSTATE '75000' SET MESSAGE_TEXT="impossibile inserire lo spostamento per il cittadino: il cittadino si è già spostato per un totale di 50 chilometri!!!";
	END IF;

END                                      	 
                                	 
-- ai fini del test:
--		1) il valore 50 come limite è stato scelto verificando i dati disponibili (vedi punto f senza condizione WHERE) )
--		2) le query di INSERT per il test sono le seguenti 
--		   (caso di spsotamento per cittadino con oltre 50 km percorsi, prima query e 
--			caso caso di spsotamento per cittadino al di sotto od uguale a 50 km, seconda query)

-- inserimento spostamento da impedire: cittadino oltre 50 km percorsi
INSERT INTO trasporti.spostamento 
     (data_spostamento,codice_fiscale,codice_mezzo,km_percorsi) VALUES
	 ('2023-07-03','GNNDSS0123456789','MT-001',31)

-- inserimento spostamento ammesso: cittadino al di sotto o uguale a 50 km percorsi
INSERT INTO trasporti.spostamento 
     (data_spostamento,codice_fiscale,codice_mezzo,km_percorsi) VALUES
	 ('2023-10-08','VLDBCC0123456789','FN-001',4)


-- c) impedire l'inserimento di uno spostamento per un mezzo oltre il suo limite di chilometri percorribili.
CREATE DEFINER=`root`@`localhost` TRIGGER `no_insert_spostamento_per_mezzo_oltre_limite_km_percorribili`
BEFORE INSERT ON spostamento FOR EACH ROW
BEGIN
	
	DECLARE km_percorsi_mezzo SMALLINT UNSIGNED DEFAULT 0;
	DECLARE limite_km_percorribili_mezzo SMALLINT UNSIGNED DEFAULT 0;
	-- km percorsi dal cittadino

	-- il cursore rappresenta una variabile tabella in memoria RAM
	DECLARE cursore_calcolo_km_percorsi_cittadino CURSOR FOR   
		SELECT mezzo.limite_km_percorribili , (SUM(spostamento.km_percorsi) / mezzo.capienza_massima)  as km_percorsi_dal_mezzo
		  FROM mezzo left join spostamento on spostamento.codice_mezzo = mezzo.codice_mezzo  
		 WHERE spostamento.codice_mezzo = new.codice_mezzo;
		 
				
 	OPEN cursore_calcolo_km_percorsi_cittadino; 
 	FETCH cursore_calcolo_km_percorsi_cittadino into km_percorsi_cittadino; 
 	CLOSE cursore_calcolo_km_percorsi_cittadino; 
	
	IF (km_percorsi_cittadino > 50) THEN
		SIGNAL SQLSTATE '75000' SET MESSAGE_TEXT="impossibile inserire lo spostamento per il cittadino: il cittadino si è già spostato per un totale di 50 chilometri!!!";
	END IF;

END 
	 
	 