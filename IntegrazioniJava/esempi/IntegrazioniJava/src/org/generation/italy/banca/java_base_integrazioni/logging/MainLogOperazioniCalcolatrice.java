package org.generation.italy.banca.java_base_integrazioni.logging;

public class MainLogOperazioniCalcolatrice {
	
	public static void main(String[] args) {
		Calcolatrice.calcolo(2, 3, '+');
		Calcolatrice.calcolo(2, 3, '*');
		Calcolatrice.calcolo(2, 0, '/');
		Calcolatrice.calcolo(2, 3, '-');
	}
	
}
