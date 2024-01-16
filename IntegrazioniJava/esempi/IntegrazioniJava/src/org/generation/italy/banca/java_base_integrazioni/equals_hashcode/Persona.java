package org.generation.italy.banca.java_base_integrazioni.equals_hashcode;

import java.time.LocalDate;

public class Persona {
	private final String codiceFiscale;
	private final String nominativo;

	public Persona(String codiceFiscale, String nominativo) {
		this.codiceFiscale = codiceFiscale;
		this.nominativo = nominativo;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public String getNominativo() {
		return nominativo;
	}

	@Override
	public String toString() {
		String personaStringa = this.getCodiceFiscale() + "\n" + this.getNominativo();
		return personaStringa;
	}

	@Override
	public int hashCode() {
		if(codiceFiscale == null) return 0;

		int code = Integer.valueOf(codiceFiscale.substring(6, 8));
		code = (code < LocalDate.now().getYear()-2000) ? 2000+code : 1900+code;
		return code;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj == null) {
			return false;
		}

		if (this.getClass() != obj.getClass()) {
			return false;
		}

		Persona persona = (Persona) obj;

		if(this.getNominativo().equals(persona.getNominativo()) && 
				this.getCodiceFiscale().equals(persona.getCodiceFiscale())) {
			return true;
		}
		return false;
	}
}


