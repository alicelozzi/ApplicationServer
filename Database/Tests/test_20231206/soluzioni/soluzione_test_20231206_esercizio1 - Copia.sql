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
 
-- g) calcolare il totale di chilometri percorsi dai cittadini della zona 'B' o 'C', indipendentemente dal
-- mezzo di trasporto usato
-- h) selezionare tutti i cittadini senza spostamenti
-- i) selezionare tutti i mezzi senza spostamenti
-- j) selezionare tutti i mezzi, con indice ecologico superiore al valore 2, usati da cittadini il cui
-- nominativo contiene tre caratteri a scelta
-- k) selezionare tutti i cittadini che con i mezzi hanno percorso più di X chilometri (con X valore a scelta)
-- l) calcolare il totale di chilometri percorsi da un determinato mezzo (vedi formula fittizia di calcolo sui
-- chilometri percorsi da un mezzo. NOTA: usare l'operatore SQL "/" per la divisione, per confronto
-- consultare anche la documentazione online)
-- m) calcolare il totale di chilometri percorsi da ciascun mezzo (riferimento codice_mezzo)
-- n) avendo effettuato il calcolo di cui al punto k): calcolare il totale dei chilometri percorsi dai tram
-- (SUM con query annidata...con formula fittizia)
-- o) selezionare tutti i mezzi con cui sono stai percorsi percorso più di Y chilometri (con Y valore a vostra
-- scelta)
-- p) selezionare tutti i mezzi che hanno superato il limite di chilometri percorribili 
-- q)
-- r) eliminare tutti gli spostamenti effettuati dai cittadini della zona 'A' (query annidata in condizione
-- 'WHERE')
-- s) aggiornare al valore 45 i chilometri percorsi negli spostamenti per i cittadini della zona 'C' (query
-- annidata in condizione 'WHERE').