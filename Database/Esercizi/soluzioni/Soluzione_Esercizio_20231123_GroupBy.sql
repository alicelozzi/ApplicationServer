PREMESSA: dato il database magazzino di cui all'esempio (vedi CANVAS).

1) calcolare per ogni prodotto il totale fatturato
select prod.*, SUM(ordin.quantita_ordine * ordin.prezzo_acquisto) as fatturato
  from prodotto as prod left join ordinazione as ordin on prod.codice_prodotto = ordin.codice_prodotto 
 group by prod.codice_prodotto  

2) calcolare per ogni cliente che ha fatto ordini, quanti prodotti ha ordinato (anche se di tipo differente)
select cl.*, SUM(ordin.quantita_ordine) as quantita_ordinata
  from cliente as cl left join ordinazione as ordin on cl.codice_fiscale = ordin.codice_fiscale 
 group by cl.codice_fiscale 

3) calcolare per ogni prodotto in ordine il prezzo medio di vendita
select ordin.codice_prodotto, AVG(ordin.prezzo_acquisto) as prezzo_medio_vendita
  from ordinazione as ordin
 group by ordin.codice_prodotto 