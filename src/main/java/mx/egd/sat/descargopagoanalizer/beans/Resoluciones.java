package mx.egd.sat.descargopagoanalizer.beans;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Resoluciones {
	@JsonProperty("Resolucion")
	private Resolucion resolucion;

	public Resolucion getResolucion() {
		return resolucion;
	}

	public void setResolucion(Resolucion resolucion) {
		this.resolucion = resolucion;
	}
}
