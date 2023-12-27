CREATE DATABASE universita /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

CREATE TABLE esame (

	  codice_esame CHAR(8) NOT NULL
	, materia VARCHAR(50) NOT NULL
	, data_esame DATE NOT NULL DEFAULT CURDATE()
	, numero_crediti TINYINT UNSIGNED NOT NULL DEFAULT 0
	, scritto_orale ENUM('s','o','e') NOT NULL DEFAULT 's'
	, PRIMARY KEY (codice_esame)
	
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
-- universita.esame definition

CREATE TABLE studente (

	  matricola CHAR(10) NOT NULL
	, nome VARCHAR(20) NOT NULL
	, cognome VARCHAR(50) NOT NULL
	, corso_di_laurea VARCHAR(100) NOT NULL
	, PRIMARY KEY (matricola)
	
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE sostenimento (

	  voto TINYINT UNSIGNED NOT NULL 
	, codice_esame CHAR(8) NOT NULL
	, matricola CHAR(10) NOT NULL 
	, KEY FK_CODICE_ESAME(codice_esame)
	, CONSTRAINT FK_CODICE_ESAME FOREIGN KEY (codice_esame) REFERENCES ESAME (codice_esame)
	, KEY FK_MATRICOLA(matricola)
	, CONSTRAINT FK_MATRICOLA FOREIGN KEY (matricola) REFERENCES STUDENTE (MATRICOLA)
	, PRIMARY KEY (matricola, codice_esame)

);


INSERT INTO universita.studente
	(matricola, nome, cognome, corso_di_laurea)
VALUES
		('RM3-000001',	'Anselmo',	'Persichetti',	'LET-OBC')
	,	('RM3-000002',	'Iolanda', 	'Lucente',		'ING-INF')
	,	('RM3-000003', 'Francesco',	'Santini',		'ING-INF')
	,	('RM3-000004', 'Teresa', 	'Salvi',		'LET-OBC')
	,	('RM3-000005', 'Rodolfo', 	'Cavour',		'LET-OBC')
	,	('RM3-000006', 'Florenzio', 'Nargisi',		'LET-OBC')
	,	('RM3-000007', 'Tarcisio', 	'Burgnich',		'SDC-CDI')
	,	('RM3-000008', 'Giacinto', 	'Romagnoli',	'SDC-CDI')
	,	('RM3-000009', 'Alda', 		'Merini',		'SDC-CDI')
	,	('RM3-000010', 'Vladimiro', 'Boccia',		'LET-OBC')
	


INSERT INTO universita.esame
	(codice_esame, materia, data_esame, numero_crediti, scritto_orale)
VALUES
		('20230001',	'Database',				'2023-11-12',	9, 	's')
	,	('20230002',	'Analisi 1', 			'2023-05-04',	12,	's')
	,	('20220008', 	'Archeologia',			'2022-12-23',	12,	'e')
	,	('20210005', 	'Antropologia', 		'2021-08-12',	9,	'e')
	,	('20230006', 	'Informatica di base', 	'2023-06-11',	6,	'o')
	,	('20210006',  	'Giornalismo',			'2021-02-22', 	9,	's')
	,	('20220009', 	'Psicotecnologie ',		'2022-04-06',	6,	'o')
	,	('20230007', 	'Antropologia',			'2023-11-12', 	9,	'e')
	,	('20230011', 	'Database',				'2023-11-12', 	12,	's')
	,	('20220006', 	'Archeologia',			'2022-11-12',	12,	'e')
	

INSERT INTO universita.sostenimento
	(voto, codice_esame, matricola)
VALUES 
		(18, '20220008', 'RM3-000001')
	,	(28, '20220008', 'RM3-000005')
	,	(28, '20220008', 'RM3-000010')
	,	(19, '20230002', 'RM3-000002')
	,	(27, '20230002', 'RM3-000003')
	,	(23, '20210006', 'RM3-000007')

	
-- ASC NON È OBBLIGATORIO SPECIFICARLO E LADDOVE NON SPECIFICATO VIENE ASSUNTA 
-- COME IMPOSTAZIONE VALORE PREDEFINTO DAL DBMS
SELECT *
  FROM studente
ORDER BY cognome ASC  

SELECT *
  FROM studente
ORDER BY cognome DESC
	  
SELECT *
  FROM studente
ORDER BY cognome ASC, nome DESC
	
	
SELECT *
  FROM studente
ORDER BY cognome ASC, nome DESC  	
	
SELECT COUNT(matricola) AS numero_studenti_universita
  FROM studente
 WHERE cognome='Persichetti' 	
 
 SELECT AVG(voto) AS media_voto
   FROM sostenimento 
  WHERE matricola='RM3-000007'  
   
 SELECT SUM(voto) AS media_voto
   from sostenimento 
	
   
-- seleziona tutti gli esami sotenuti dagli studenti con cognome 'Persichetti'	
select * 
  from sostenimento	
  where matricola='RM3-000001'
  
select * 
  from studente 
 where cognome like '%persichetti%'  

select studente.*, sostenimento.* -- OUTPUT 
  from studente, sostenimento -- INPUT formato da due tabelle che hanno una foreign-key tra loro (sostenimento dipende da studente per il vincolo FK)
 where studente.matricola=sostenimento.matricola -- nella condizione imposto l'uguaglianza, riprononendo un legame tra le tabelle (da non confondere con il vincolo FK che agisce su INSERT/UPDATE/DELETE)
 
select studente.*, sostenimento.* -- OUTPUT 
  from sostenimento inner join studente on studente.matricola=sostenimento.matricola  
 
select studente.*, sostenimento.* -- OUTPUT 
  from sostenimento left join studente on studente.matricola=sostenimento.matricola

select studente.*, sostenimento.* -- OUTPUT 
  from studente left join sostenimento on studente.matricola=sostenimento.matricola
  
  
select studente.*, sostenimento.* -- OUTPUT 
  from sostenimento right join studente on studente.matricola=sostenimento.matricola

select studente.*, sostenimento.* -- OUTPUT 
  from studente right join sostenimento on studente.matricola=sostenimento.matricola
  
  
  
select st.*
  from sostenimento so inner join studente st on st.matricola = so.matricola  
                       inner join esame es on es.codice_esame  = so.codice_esame 
 where es.materia = 'antropologia'                      
	
	
-- tutti gli studenti che non hanno dato alcun esame
select st.* 
  from studente st left join sostenimento so on so.matricola = st.matricola   
 where so.matricola is null 
   and so.codice_esame is null

-- tutti gli esami che non hanno avuto alcuno studente a sostenerli
select es.* 
  from esame es left join sostenimento so on es.codice_esame = so.codice_esame   
 where so.matricola is null 
   and so.codice_esame is null  
   
 
   
-- numero_crediti per ogni studente   
select so.matricola as matricola, SUM(es.numero_crediti) as numero_crediti
  from sostenimento so inner join esame es on so.codice_esame = es.codice_esame 
group by so.matricola


-- media voto per ogni studente   
select so.matricola as matricola, AVG(so.voto) as media_voto
  from sostenimento so  
group by so.matricola


-- tutti gli studenti che possono laurearsi  ???
select so.matricola as matricola, SUM(es.numero_crediti) as numero_crediti
  from sostenimento so inner join esame es on so.codice_esame = es.codice_esame 
group by so.matricola

-- where numero_crediti > 10 
-- query select annidata
select studente.*, tabella_matricola_con_crediti.*
  from studente inner join 
       (select so.matricola as matricola, SUM(es.numero_crediti) as numero_crediti
		  from sostenimento so inner join esame es on so.codice_esame = es.codice_esame 
		 group by so.matricola) as tabella_matricola_con_crediti
		on studente.matricola = tabella_matricola_con_crediti.matricola 
where tabella_matricola_con_crediti.numero_crediti > 10  

  
  
  
from studente st
 where st.numero_crediti >= 20   
   
	
	
	
-- selezionare gli studenti che hanno matricola 'RM3-000001' o matricola 'RM3-000006' o matricola 'RM3-000007'
select  * 
  from studente
 where matricola = 'RM3-000001'  
    or matricola = 'RM3-000006'
    or matricola = 'RM3-000007'
  
 select  * 
  from studente
 where matricola in ('RM3-000001', 'RM3-000006', 'RM3-000007')
 
 select studente.*
   from studente 
  where matricola in (select sostenimento.matricola from sostenimento where voto < 25)  
   
 
call registrazione_voto_esame('RM3-000001', '20230002', 29);  
	
	
insert into sostenimento(matricola, codice_esame, voto) values ('RM3-000001', '20230002', 29);
	

update studente set numero_crediti =    (select SUM(es.numero_crediti) as numero_crediti
										  from sostenimento so inner join esame es on so.codice_esame = es.codice_esame 
										 where so.matricola = 'RM3-000002'   
										 group by so.matricola)
 where studente.matricola = 'RM3-000004'   
				 
