package org.generation.italy.banca.java_base_integrazioni.equals_hashcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

public class MainHashing {
	
	public static void main(String[] args) {
		
		Persona persona1 = new Persona("TTTFNC80A01H501K", "Francesco Totti");
		Persona persona2 = new Persona("DLPLSN88A01L219T", "Alessandro Del Piero");
		Persona persona3 = new Persona("CHLGRG81A01L219R", "Giorgio Chiellini");
		Persona persona4 = new Persona("BNCLRD80A01L219U", "Leonardo Bonucci");

		List<Persona> persone = new ArrayList<Persona>();
		persone.add(persona1);
		persone.add(persona2);
		persone.add(persona3);
		persone.add(persona4);

		HashingPersona hasher = new HashingPersona(persone);
		 
		HashMap<Integer, List<Persona>> hashMap = hasher.getHashMap();

		printHashMap(hashMap);
	}

	public static void printHashMap(HashMap<Integer, List<Persona>> hashMap) {
		System.out.println("Chiave\tValore");
		Iterator<Integer> iteratorHash = new TreeSet<Integer>(hashMap.keySet()).iterator();					//restituisce un iterator ordinato per chiave

		while(iteratorHash.hasNext()) {
			int key = iteratorHash.next();
			List<Persona> persone = hashMap.get(key);
			System.out.print(key + "\t{" + persone.get(0).getNominativo());
			for(int i=1; i<persone.size(); i++) {
				System.out.print(", " + persone.get(i).getNominativo());
			}
			System.out.println("}");
		}
	}
}
