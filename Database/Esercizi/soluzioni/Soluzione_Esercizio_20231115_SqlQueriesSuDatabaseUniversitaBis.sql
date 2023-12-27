-- 1) Installare lo script-sql del database università di cui l'esempio nella cartella 'esempi'.
--    In alternativa usare il proprio database universita realizzato ieri, purchè opportunamente popolato.

use universita

-- 2) Eseguire una query di update che aggiorna i voti di un determinato studente, assegnado a tutti il voto 19.
update sostenimento 
   set voto = 19
 where matricola = 'RM3-000007'  

-- 3) Eseguire una query di update che sostituisce il cognome di uno studente con altro cognome (a propria scelta)
update studente 
   set cognome = 'Persicucci'
 where matricola = 'RM3-000001'  
 
-- 4) Eseguire una query di update che assegna tutti i sostenimenti di una matricola ad altra matricola.
update sostenimento 
   set matricola = 'RM3-000001'
 where matricola = 'RM3-000007'  
 
-- 4) Eliminare tutti i sostenimenti di un determinato studente
delete 
  from sostenimento 
 where matricola = 'RM3-000010'
 
 
-- 5) Eliminare tutti i sostenimenti degli studenti che hanno avuto un voto inferiore a 25
delete 
  from sostenimento 
 where voto < 25
