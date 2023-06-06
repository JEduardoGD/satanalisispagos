package mx.egd.sat.descargopagoanalizer.daos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Resolucion {
	@JsonProperty("IdResolucion")
	private int idResolucion;

	@JsonProperty("Conceptos")
	private Conceptos conceptos;

	public int getIdResolucion() {
		return idResolucion;
	}

	public void setIdResolucion(int idResolucion) {
		this.idResolucion = idResolucion;
	}

	public Conceptos getConceptos() {
		return conceptos;
	}

	public void setConceptos(Conceptos conceptos) {
		this.conceptos = conceptos;
	}
	
	

}
