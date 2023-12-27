-- Dopo avere eseguito lo script sql del database 'magazzino' (vedi cartella 'magazzino' in 'esempi'),
-- effettuare le seguenti query sul database 'magazzino':

use magazzino

-- * seleziona tutti i clienti il cui codice fiscale è 'BVEGTA0123456789'
select * 
  from cliente 
 where codice_fiscale = 'BVEGTA0123456789'

-- * seleziona tutti i clienti il cui nominativo contiene il termine 'mo'
select * 
  from cliente 
 where nominativo like '%mo%'

-- * seleziona tutti i prodotti con quantità disponibile maggiore di tre unita
select * 
  from prodotto 
 where quantita_disponibile > 3

-- * seleziona tutti i prodotti con quantità disponibile tra due e tre unità
select * 
  from prodotto 
 where quantita_disponibile >= 2 
   and quantita_disponibile <= 3

-- * seleziona tutti i prodotti con prezzo inferiore a 600 euro.
select * 
  from prodotto 
 where prezzo < 600  
   
-- * seleziona tutti gli ordini effettuati dal cliente il cui codice fiscale è: 'BVEGTA0123456789'
select ordinazione.*, cliente.* 
  from ordinazione inner join cliente on ordinazione.codice_fiscale = cliente.codice_fiscale  
 where cliente.codice_fiscale = 'BVEGTA0123456789'
 
-- * inserisci il prodotto 'Lavatrice Candy 360', codificandola, con quantità 6 unità ed un prezzo di 839 euro. 
insert into prodotto
			(codice_prodotto, descrizione, quantita_disponibile, prezzo)
     values	('LVCD0360', 'Lavatrice Candy 360', 6, 839);
 
-- * inserisci il cliente 'Giordano Bruno' con codice fiscale 'BRNGRD0123456789'
insert into magazzino.cliente
			(codice_fiscale, nominativo)
	 values	('BRNGRD0123456789', 'Giordano Bruno');

-- * inserisci l'ordine in data odierna per cliente e prodotto di cui sopra (Giordano Bruno e Lavatrice Candy) 
--   per un totale di unità 2
insert into magazzino.ordinazione
			(codice_fiscale, codice_prodotto, data_ordine, quantita_ordine, prezzo_acquisto)
	 values ('BRNGRD0123456789', 'LVCD0360', curdate(), 2, 380); -- curdate(): funzione sql che restituisce la data corrente del sistema 

-- * aggiorna la quantità disponibile del prodotto in base all'ordine effettuato di cui al punto precedente
update magazzino.prodotto
   set quantita_disponibile = quantita_disponibile - 2
 where codice_prodotto='LVCD0360'
	 
-- * elimina l'ordine precedentemente inserito
delete 
  from ordinazione
 where codice_fiscale='BRNGRD0123456789' 
   and codice_prodotto='LVCD0360' 
   and data_ordine=curdate();
  
-- * aggiorna la quantità disponibile del prodotto in base all'ordine eliminato
update magazzino.prodotto
   set quantita_disponibile = quantita_disponibile + 2
 where codice_prodotto='LVCD0360'
 