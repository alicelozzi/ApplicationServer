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
SELECT spostamento.codice_mezzo, SUM(spostamento.km_percorsi) / mezzo.capienza_massima AS totale_chilometri_persorsi_dal_mezzo
  FROM spostamento INNER JOIN mezzo ON spostamento.codice_mezzo = mezzo.codice_mezzo  
 GROUP BY spostamento.codice_mezzo  

-- n) avendo effettuato il calcolo di cui al punto m): 
--    calcolare il totale dei chilometri percorsi dai tram
--    (SUM con query annidata...con formula fittizia)
  SELECT 
		(
		SELECT spostamento.codice_mezzo, SUM(spostamento.km_percorsi) / mezzo.capienza_massima AS totale_chilometri_persorsi_dal_mezzo
		  FROM spostamento INNER JOIN mezzo ON spostamento.codice_mezzo = mezzo.codice_mezzo  
		 GROUP BY spostamento.codice_mezzo  
		) as codice_mezzo_con_chilometri_percorsi 
 
 
-- o) selezionare tutti i mezzi con cui sono stai percorsi percorso più di Y chilometri (con Y valore a vostra
-- scelta)
-- p) selezionare tutti i mezzi che hanno superato il limite di chilometri percorribili 
-- q)
-- r) eliminare tutti gli spostamenti effettuati dai cittadini della zona 'A' (query annidata in condizione
-- 'WHERE')
-- s) aggiornare al valore 45 i chilometri percorsi negli spostamenti per i cittadini della zona 'C' (query
-- annidata in condizione 'WHERE').