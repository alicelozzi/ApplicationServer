-- PREMESSA: 
-- il presente esercizio è il prosieguo di quello di lavoro automnomo del 24 novembre 2023 di cui si dispongono le soluzioni.
--
-- 15) a) aggiungere un nuovo proprietario,
INSERT INTO banca.cliente (cod_fiscale,nominativo, indirizzo) 
     VALUES ('MRRGVN0123456789','Giovanni Marrone', NULL) 

-- 15) b) di tre conti con valuta GBP (sterlina britannica): 
--        due aperti in Germania (DE) e l'altro aperto in Francia (FR), 
--        con scoperto rispettivamente di 345, 523 e 987,

-- 		 considerazione: viene eliminato il constraint esistente sulla valtua e ricreato di nuovo tenendo conto anche della verifica su GBP

ALTER TABLE banca.conto DROP CONSTRAINT conto_check_valuta;
ALTER TABLE banca.conto ADD CONSTRAINT conto_check_valuta CHECK (`valuta` = 'EUR' or `valuta` = 'USD' or `valuta` = 'GBP');

INSERT INTO banca.conto (valuta,scoperto,codice_fiscale,data_ora_intestazione,iban) 
	 VALUES ('GBP',345.0,'MRRGVN0123456789','2023-01-10 09:13:14','DEaa0123456789012345678901234567')
	      , ('GBP',523.0,'MRRGVN0123456789','2023-07-25 12:06:15','DEbb0123456789012345678901234567') 
	      , ('GBP',987.0,'MRRGVN0123456789','2023-12-31 14:39:47','FRaa0123456789012345678901234567') 


-- 15) c) su cui, per ciascun conto, vengono effettuati 3 versamenti e 2 prelievi
-- versamenti su conto con iban 'DEaa0123456789012345678901234567' 	      
INSERT INTO banca.movimento (data_ora_operazione,importo,tipo_operazione,iban) 
     VALUES ('2023-11-24 12:42:50',678.0,'V','DEaa0123456789012345678901234567')
	 	  ,	('2023-11-25 14:48:36',543.0,'V','DEaa0123456789012345678901234567')
	 	  ,	('2023-11-26 13:48:36',963.0,'V','DEaa0123456789012345678901234567')
	 	  ,	('2023-11-27 02:48:36',367.0,'P','DEaa0123456789012345678901234567')
	 	  ,	('2023-11-28 03:48:36',1543.0,'P','DEaa0123456789012345678901234567')

-- versamenti su conto con iban 'DEbb0123456789012345678901234567' 	      
INSERT INTO banca.movimento (data_ora_operazione,importo,tipo_operazione,iban) 
     VALUES ('2023-10-12 12:42:55',78.0,'V','DEbb0123456789012345678901234567')
	 	  ,	('2023-09-05 14:48:36',133.0,'V','DEbb0123456789012345678901234567')
	 	  ,	('2023-11-16 13:48:16',6923.0,'V','DEbb0123456789012345678901234567')
	 	  ,	('2023-12-07 02:08:11',635.0,'P','DEbb0123456789012345678901234567')
	 	  ,	('2023-01-28 03:04:09',4990.0,'P','DEbb0123456789012345678901234567')


-- versamenti su conto con iban 'FRaa0123456789012345678901234567' 	      
INSERT INTO banca.movimento (data_ora_operazione,importo,tipo_operazione,iban) 
     VALUES ('2023-10-12 21:20:55',781.0,'V','FRaa0123456789012345678901234567')
	 	  ,	('2023-09-05 23:06:36',331.0,'V','FRaa0123456789012345678901234567')
	 	  ,	('2023-11-16 11:11:16',2963.0,'V','FRaa0123456789012345678901234567')
	 	  ,	('2023-12-07 08:16:11',3560.0,'P','FRaa0123456789012345678901234567')
	 	  ,	('2023-01-28 23:44:09',998.0,'P','FRaa0123456789012345678901234567')	 
	 
-- 16) aggiornare tutti i conti che hanno uno scoperto superiore a 500 euro, 
--     ponendo la data ed ora di intestazione alle 23:59:59 del 24 dicembre 2023
UPDATE banca.conto 
   SET data_ora_intestazione = '2023-12-24 23:59:59'
 WHERE scoperto > 500
   AND valuta = 'EUR'



-- 17) selezionare tutti i dati del conto 
--   (o dei conti se più di uno con pari valore nel versamento) 
--   in EUR ed aperti in Italia, 
--   su cui sono stati effettuati più versamenti (calcolo ammontare con funzione MAX) 
   
--    NOTA: escludere i conti che non hanno alcun versamento (possibile presenza del valore NULL in totale versato)

SELECT co.*, SUM(mo.importo) as totale_versato  
  FROM conto co LEFT join movimento mo on mo.iban = co.iban
 WHERE co.valuta = 'EUR'
   AND co.iban like 'IT%'
   AND mo.tipo_operazione = 'V'
 GROUP BY mo.iban
HAVING totale_versato = 
		(	
			-- query annidata: calcola il massimo importo versato su uno o più conti (si possono avere più conti con la stessa somma per il totale versato)
			SELECT MAX(totale_versato) as max_totale_versato   
			  FROM (SELECT SUM(mo.importo) as totale_versato  
					  FROM conto co LEFT join movimento mo on mo.iban = co.iban
					 WHERE co.valuta = 'EUR'
					   AND co.iban like 'IT%'
					   AND mo.tipo_operazione = 'V'
					 GROUP BY mo.iban) as conto_italiano_in_euro_con_totale_versato
		 )
   AND totale_versato IS NOT NULL


   
-- 18) selezionare tutti i dati del conto 
--    (o dei conti se più di uno con pari valore nel prelievo) in GBP ed aperti in Germania o Spagna, 
--    su cui sono stati effettuati meno prelievi (calcolo ammontare con funzione MIN) 
--    NOTA: escludere i conti che non hanno alcun prelievo (possibile presenza del valore NULL in totale prelevato)

SELECT co.*, SUM(mo.importo) as totale_prelevato  
  FROM conto co LEFT join movimento mo on mo.iban = co.iban
 WHERE co.valuta = 'GPB'
   AND (co.iban like 'DE%' OR co.iban like 'ES%')
   AND mo.tipo_operazione = 'P'
 GROUP BY mo.iban
HAVING totale_versato = 
		(	
			-- query annidata: calcola il minimo importo versato su uno o più conti (si possono avere più conti con la stessa somma minima per il totale versato)
			SELECT MIN(totale_prelevato) as min_totale_prelevato   
			  FROM (SELECT SUM(mo.importo) as totale_prelevato  
					  FROM conto co LEFT join movimento mo on mo.iban = co.iban
					 WHERE co.valuta = 'GBP'
					   AND (co.iban like 'DE%' OR co.iban like 'ES%')
					   AND mo.tipo_operazione = 'P'
					 GROUP BY mo.iban) AS conto_italiano_in_euro_con_totale_versato
		 )
   AND totale_versato IS NOT NULL

   

-- 19) eliminare tutti i movimenti dei conti francesi
DELETE 
  FROM movimento    
 WHERE movimento.iban LIKE 'FR%'   
   
-- 20) usando una query di select annidata nella condizione where: 
--     eliminare tutti i movimenti di versamento, 
--     (query-annidata)-> per i conti in EUR che hanno 
--     un importo versato superiore a 1600 euro (query di SELECT con solo output iban)  
--     Si consiglia prima di usare il DELETE, 
--     di verificare la validità della query usando l'output della SELECT  
--     così da non eliminare subito i record mobvimento realtivi
SELECT *  -- DELETE
  FROM movimento    
 WHERE movimento.iban IN
			(
				SELECT iban
				  FROM (
						SELECT co.iban, SUM(mo.importo) as totale_versato  
						  FROM conto co LEFT join movimento mo on mo.iban = co.iban
						 WHERE co.valuta = 'EUR'
						   AND mo.tipo_operazione = 'V'
						 GROUP BY mo.iban
						HAVING totale_versato > 1600
					  ) AS conto_in_euro_con_totale_versato_superiore_1600 					
			)
  AND movimento.tipo_operazione = 'V'