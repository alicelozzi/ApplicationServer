-- PREMESSA: 
-- fare riferimento allo script sql presente all'esempio della banca
-- e consultare tutte le lavagne finora pubblicate
-- 
-- Attenzione:
--  * la banca ha due filiali, una in Italia ed una in Spagna 
--  * per un movimento sul conto, 
--    il prelievo ha valore 'P' maiuscolo ed il versamento ha valore 'V' maiuscolo
-- 
-- 
--  1) seleziona tutti i clienti con nominativo che contiene 'an'

select * 
  from cliente 
 where nominativo like '%an%'

--  2) calcola quanti clienti sono presenti nel database
select count(*) as numero_clienti
  from cliente 
 
--  3) seleziona tutti i conti della filiale spagnola della banca
select c.*
  from conto c
 where c.iban like 'ES%'
  
--  4) seleziona tutti i conti in dollari (USD) presenti nella filiale italiana (IT)
select c.*
  from conto c
 where c.iban like 'IT%'
   and c.valuta = 'USD'
 
--  5) seleziona tutti i conti del cliente con nominativo che contiene 'Persichetti'
select cl.*, co.*
  from conto co inner join cliente cl on co.codice_fiscale = cl.cod_fiscale 
 where cl.nominativo like '%Persichetti%'

   
--  6) seleziona tutti i movimenti effettuati sul conto con iban 'ITff0123456789012345678901234567'
select mov.*
  from movimento mov
 where mov.iban = 'ITff0123456789012345678901234567'
  
--  7) seleziona i clienti che non hanno un conto
select cl.*
  from cliente cl left join conto co on cl.cod_fiscale = co.codice_fiscale 
 where co.iban is null 
 
--  8) seleziona i conti per cui non vi è alcun movimento
select co.*
  from conto co left join movimento mov on co.iban = mov.iban 
 where mov.id_movimento is null
 
--  9) selezionare i conti dei clienti con codice_fiscale: 'BNCFRL0123456789' o 'GTNBGD0123456789' o 'VLDBCC0123456789'
select co.*
  from conto co
 where codice_fiscale in ('BNCFRL0123456789', 'GTNBGD0123456789', 'VLDBCC0123456789')

-- 10) calcola l'ammontare dei prelievi fatti sul conto con iban 'ITff0123456789012345678901234567'
select mov.iban, SUM(mov.importo) as importo_prelevato
  from movimento mov
 where mov.iban = 'ITff0123456789012345678901234567'
   and mov.tipo_operazione = 'P' 
 group by (mov.iban)
  
 
-- 11) calcola l'ammontare medio dei versamenti per ciascun conto in EUR
select mov.iban, AVG(mov.importo) as importo_medio_versato
  from movimento mov inner join conto co on co.iban = mov.iban
 where co.valuta = 'EUR'
   and mov.tipo_operazione = 'V' 
 group by (mov.iban) 
 
-- 12) per ogni conto, raggruppa i movimenti di tipo versamento calcolandone l'importo totale
select mov.iban, SUM(mov.importo) as importo_totale_versato
  from movimento mov
 where mov.tipo_operazione = 'V'
 group by (mov.iban) 
 
-- 13) per ogni cliente, raggruppa l'importo di tutti i versamenti fatti sui conti di sua proprietà in dollari
select cl.*, SUM (mov.importo) as importo_versato_sui_conti_con_valuta_usd
  from cliente cl left join conto co on co.codice_fiscale = cl.cod_fiscale 
                  left join movimento mov on co.iban = mov.iban 
 where co.valuta = 'USD'
   and mov.tipo_operazione = 'V'
 group by cl.cod_fiscale   
 

-- 14) selezionare tutti i conti con un totale versato inferiore a 1457 euro
--     esplicitato=> ossia, per ogni conto, calcola il totale versato e verifica che sia inferiore a 1457 euro.
--     conseguenza=> raggruppamento su campo 'iban' nella tabella movimento (tabella su cui si svolge la funzione SUM, campo 'importo'), 
--                   e necessità del join per due ragioni:
--				     1) la condizione where sul campo 'valuta' della tabella 'conto'
--                   2)	l'output in select in cui presentare tutti i dati del conto	 
--
--                   Inoltre, la parole chiave 'having' ha lo stesso significato di verifica condizione come nel caso della 'where',
--                   ma a differenza della 'where' che filtra i dati ponendo condizioni sui campi delle tabelle indicate nel 'from', 
--                   'having' pone una condizione di filtro sui campi in output nella select ('having' sul campo 'totale_versato' < 1457
--                   ossia dopo che è stato effettuato il calcolo di somma). In relazione a ciò, sempre in 'having', viene posta una 
--                   seconda condizione 'is not null' per escludere quei conti che non hanno alcun movimento, conti per cui l'importo_versato è NULL
--					 
--					 Per verificare la 'bontà' della query provate a togliere le condizioni sia in 'having' che in 'where', aggiungendo poi quelle in 'where' ed infine quelle in 'having' 
select co.*, SUM(mo.importo) as importo_versato
  from conto co left join movimento mo on co.iban = mo.iban 
 where mo.tipo_operazione = 'V'
   and co.valuta='EUR'
 group by mo.iban
having importo_versato < 1457
   and importo_versato is not null

