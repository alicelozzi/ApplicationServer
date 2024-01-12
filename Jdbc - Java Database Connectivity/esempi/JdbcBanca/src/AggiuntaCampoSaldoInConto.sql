-- Aggiunta dell'atributo calcolabile saldo:
-- il saldo è dato dalla differenza tra la somma dei prelievi e la somma dei versamenti

ALTER TABLE conto
ADD COLUMN saldo FLOAT NOT NULL DEFAULT 0;

-- La seguente query di SELECT serve a calcolare (ricavare) il dato del saldo 
-- a partire dalla differenza tra la somma dei prelievi e la somma dei versamenti.
--
-- La parola chiave UNION unisce i risultati delle due select, 
-- quella dei prelievi con quella dei versamenti.
-- Per usare UNION è necessario che le due query da unire abbiano gli stessi campi 
-- e la stessa sequenza di output nella SELECT: è il motivo per cui
-- nella prima query torviamo il totale_prelevato a 0 e 
-- nella seconda query  il totale versato a 0
-- Da notare che le due query restituiranno sempre e solo un unico record ciascuna.

--
-- L'esecuzione della query derivante dalla UNION delle due la usiamo come query annidata nel FROM,
-- come se avessimo quindi una nuova tabella in cui il risutlato finale 
-- è la differenza della somma di tutti i totali versati e la somma di quelli prelevati

-- NOTA: la funzione IFNULL è propria di MYSQL ed indica che, 
-- laddove il primo parametro è NULL viene restituito il secondo
-- altrimenti il valore stesso del primo parametro.
-- Ad esempio: IFNULL(SUM(mo.importo), 0) => se la somma dell'importo è NULL allora viene restituito 0, altrimenti il valore della somma

SELECT SUM(totale_versato) - SUM(totale_prelevato) as saldo
  FROM
  	(
	SELECT IFNULL(SUM(mo.importo), 0) as totale_versato, 0 as totale_prelevato  
	  FROM movimento mo
	 WHERE mo.iban = 'DEbb0123456789012345678901234567'
	   AND mo.tipo_operazione = 'V'

	UNION   
	
	SELECT 0 as totale_versato, IFNULL(SUM(mo.importo), 0) as totale_prelevato
	  FROM movimento mo
	 WHERE mo.iban = 'DEbb0123456789012345678901234567'
	   AND mo.tipo_operazione = 'P'
	) as prelevato_versato_su_conto
	
	
-- La seguente query calcola il saldo per ogni conto su cui ci sono movimenti
	
SELECT iban, SUM(totale_versato) - SUM(totale_prelevato) as saldo 
  FROM
	(SELECT mo.iban, IFNULL(SUM(mo.importo), 0) as totale_versato, 0 as totale_prelevato  
		  FROM movimento mo
		 WHERE mo.tipo_operazione = 'V'
		 GROUP BY mo.iban
		 
	union 
	
	SELECT mo.iban, 0 as totale_versato, IFNULL(SUM(mo.importo), 0) as totale_prelevato  
		  FROM movimento mo
		 WHERE mo.tipo_operazione = 'P'
		 GROUP BY mo.iban) as prelevato_versato_su_conto 
		 
GROUP BY prelevato_versato_su_conto.iban	

--- Esempio di update del saldo calcolato per ogni conto che ha dei movimenti
update conto set saldo = 274.0 where iban = 'DEaa0123456789012345678901234567';
update conto set saldo = 273.0 where iban = 'ESaa0123456789012345678901234567';
update conto set saldo = -1320.0 where iban = 'ESdd0123456789012345678901234567';
update conto set saldo = -31700.0 where iban = 'ESee0123456789012345678901234567';
update conto set saldo = 1578.0 where iban = 'ITbb0123456789012345678901234567';
update conto set saldo = -3752.0 where iban = 'ITee0123456789012345678901234567';
update conto set saldo = 22054.0 where iban = 'ITff0123456789012345678901234567';
