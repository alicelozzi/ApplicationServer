-- PREMESSA: 
-- fare riferimento allo schema fisico di cui all'esempio denominato 'biblioteca' (valutare anche lo schema concettuale) nella cartella omonima, alla sezione 'esempi' dei materiali didattici dell'argomento 'Database...' 
-- 
-- 
-- 1) Implementare lo schema fisico, definendo per ciascuna tabella, nell'ordine:
-- 
-- 	a) la tipologia del campo
-- 	b) eventuali campi NULL 
-- 	c) eventuali campi auto-incrementanti
-- 	c) la chiave primaria (primary-key)
-- 	d) le eventuali chiavi correlate (foreign-key)
-- 	e) i campi indice (si può anche fare in un secondo momento, dopo avere implementato le query di tipo select capendo quali condizioni ci sono in WHERE)
-- 
-- NOTA: tenere presente che il codice tessera è così composto 23-001 (tesseramento del 2023 con progressivo 001) 
--       il codice biblioteca è codificato analogamente ma si riferisce al libro (23-099 99-esimo libro posto nel catalogo della biblioteca nel 2023)
-- 
-- 
-- 2) Popolare le tabelle con almeno 5 libri e 4 utenti ed inserire almeno 20 prestiti di cui 18 sono stati restituiti (hanno sia data_prestito che data_restituzione) e 2 ancora no (hanno solo la data del prestito). 
--    Inoltre, se un libro è in prestito e non è ancora stato restituito, lo stato del libro passa da libero ad occupato.

INSERT INTO biblioteca_generation.prestito 
 		(data_prestito,codice_biblioteca,codice_tessera,data_restituzione) 
  VALUES 
		('2023-12-01','23-001','22-001','2023-12-30')
	,	('2022-08-02','23-001','22-002','2022-08-30')
	, 	('2023-10-14','23-001','23-001','2023-11-25')
	, 	('2022-07-15','23-001','23-002','2022-11-12')
	,	('2022-03-11','23-002','22-001','2022-04-31')
	,	('2022-04-02','23-002','22-002','2022-05-27')
	, 	('2023-05-01','23-002','23-001','2023-06-05')
	, 	('2023-06-08','23-002','23-002','2023-07-18')
 	,	('2023-12-01','22-001','22-001','2023-12-30')
 	,	('2022-08-02','22-001','22-002','2022-08-30')
 	, 	('2023-10-14','22-001','23-001','2023-11-25')
 	, 	('2022-07-15','22-001','23-002','2022-11-12')
 	,	('2022-03-11','22-002','22-001','2022-04-31')
 	,	('2022-04-02','22-002','22-002','2022-05-27')
 	, 	('2023-05-01','22-002','23-001','2023-06-05')
 	, 	('2023-06-08','22-002','23-002','2023-07-18')
 	,	('2023-09-11','23-003','22-001','2023-09-30')
 	,	('2022-10-11','23-003','23-002','2022-11-19')
 	, 	('2023-12-01','23-001','22-001', NULL)
 	, 	('2023-12-02','23-003','23-002', NULL)


-- 3) Rispondere alle seguenti query:
--    
-- 	a) selezionare dal catalogo tutti i libri attualmente in prestito
	SELECT libro.* 
	  FROM libro
	 WHERE libro.stato = 'P'
	 
-- 	b) selezionare dal catalogo tutti i libri editi in un determinato anno
	SELECT libro.* 
	  FROM libro
	 WHERE libro.anno_edizione = 2001
	
	
-- 	c) selezionare tutti gli utenti tesserati in un determinato mese-anno (dipende da come avete popolato il db, ad esempio quelli di novembre 2023 vanno dal 1 novembre al 30 novembre del 2023....BETWEEN DATE)
	SELECT utente.* 
	  FROM utente
	 WHERE utente.data_tesseramento BETWEEN '2023-11-01' AND '2023-11-30'
	 
-- 	d) selezionare tutti i libri letti da un determinato utente di cui è noto il codice_tessera
	 
	SELECT lb.*
	  FROM prestito pr INNER JOIN utente ut ON ut.codice_tessera = pr.codice_tessera 
		  			   INNER JOIN libro lb ON lb.codice_biblioteca = pr.codice_biblioteca 
	 WHERE ut.codice_tessera = '23-002'
	 
	 
-- 	e) selezionare tutti i libri letti da 3 utenti ciascuno col suo codice_tessere (clausola IN...)
--	l'uso della parola chiave DISTINCT nell'output di select consente di non presentare i record duplicati	 
--  infatti gli stessi libri sono stati letti dai vari utenti col codice_tessera indicato in condizione WHERE 	 
--  per capirne beme il funzionamento: rimuovere il DISTINCT eseguendo la query e verificando il risultato per 	 
--  poi reinserirlo e vedere il cambiamento nell'output.	 
	SELECT DISTINCT lb.*
	  FROM prestito pr INNER JOIN utente ut ON ut.codice_tessera = pr.codice_tessera 
		  			   INNER JOIN libro lb ON lb.codice_biblioteca = pr.codice_biblioteca 
	 WHERE ut.codice_tessera IN ('23-001', '23-002', '22-001')
	 
	 
-- 	f) selezionare tutti i libri letti da utenti con nominativo che inizia con (indicare tre caratteri)
	SELECT DISTINCT lb.*
	  FROM prestito pr INNER JOIN utente ut ON ut.codice_tessera = pr.codice_tessera 
		  			   INNER JOIN libro lb ON lb.codice_biblioteca = pr.codice_biblioteca 
	 WHERE ut.nominativo LIKE 'Ans%'

	 
-- 	g) calcolare quanti libri ha letto un determinato utente con nominativo esattamente uguale a...
	SELECT COUNT(pr.codice_biblioteca) as numero_libri_letti
	  FROM utente ut INNER JOIN prestito pr ON ut.codice_tessera = pr.codice_tessera 
	 WHERE ut.nominativo = 'Rosa Fiorelli'
	 
-- 	h) seleziona tutti gli utenti che non hanno letto libri
	SELECT ut.*, COUNT(pr.id_prestito) as numero_libri_letti
	  FROM utente ut LEFT JOIN prestito pr ON ut.codice_tessera = pr.codice_tessera 
	 GROUP BY pr.codice_tessera 
    HAVING numero_libri_letti = 0
    
    oppure
    
	SELECT ut.*, pr.*
	  FROM utente ut LEFT JOIN prestito pr ON ut.codice_tessera = pr.codice_tessera 
     WHERE pr.id_prestito IS NULL
	 
-- 	i) seleziona tutti i libri che non sono mai stati letti
	SELECT lb.*, COUNT(pr.id_prestito) as numero_prestiti
	  FROM libro lb LEFT JOIN prestito pr ON lb.codice_biblioteca = pr.codice_biblioteca  
	 GROUP BY pr.codice_biblioteca 
    HAVING numero_prestiti = 0
    
    oppure
    
	SELECT lb.*, pr.*
	  FROM libro lb LEFT JOIN prestito pr ON lb.codice_biblioteca = pr.codice_biblioteca  
     WHERE pr.id_prestito  IS NULL 
     
-- 	j) raggruppa per codice_tessera, indicando per ogni utente (tutti i dati dell'utente) quante volte ha letto libri (ogni prestito comporta una lettura)
	SELECT ut.*, COUNT(pr.id_prestito) as numero_libri_letti
	  FROM utente ut LEFT JOIN prestito pr ON ut.codice_tessera = pr.codice_tessera 
	 GROUP BY pr.codice_tessera      
	 
-- 	k) eliminare tutti gli utenti che non hanno mai letto libri
DELETE 
  FROM utente 
 WHERE utente.codice_tessera IN
	 		(
				SELECT utenti_con_nessun_libro_letto.codice_tessera 
				  FROM 
					(SELECT ut.*, COUNT(pr.id_prestito) as numero_libri_letti
					  FROM utente ut LEFT JOIN prestito pr ON ut.codice_tessera = pr.codice_tessera 
					 GROUP BY pr.codice_tessera 
				    HAVING numero_libri_letti = 0) as utenti_con_nessun_libro_letto		 
			)

