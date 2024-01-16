package org.generation.italy.banca.java_base_integrazioni.equals_hashcode;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

public class HashingPersona {
	private HashMap<Integer, List<Persona>> hashMap;

	public HashingPersona(List<Persona> persone) {
		
		persone = selectionSort(persone);
		
		hashMap = new HashMap<Integer, List<Persona>>();

		for(int i=0; i<persone.size(); i++) {
			
			int hashCode = persone.get(i).hashCode();
			
			List<Persona> entry = new ArrayList<Persona>();
			
			for(int j=i; j<persone.size(); j++) {
				if(persone.get(j).hashCode() != hashCode) {
					i = j-1;
					break;
				} else {
					entry.add(persone.get(j));
				}
			}
			
			hashMap.put(persone.get(i).hashCode(), entry);
		}
	}

	private static List<Persona> selectionSort(List<Persona> persone) {
		
		for(int i=1; i<persone.size(); i++) {
			int minimo = i;
			for(int j=i+1; j<persone.size(); j++) {
				if(persone.get(minimo).hashCode() > persone.get(j).hashCode()) {
					minimo = j;
				}
			}

			if(minimo != i) {
				Persona k = persone.get(minimo);
				persone.set(minimo, persone.get(i));
				persone.set(i, k);
			}
		}
		return persone;
	}

	public HashMap<Integer, List<Persona>> getHashMap() {
		return hashMap;
	}	
}
