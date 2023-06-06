package mx.egd.sat.descargopagoanalizer.daos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Resoluciones {
	@JsonProperty("Resolucion")
	private Resolucion resolucion;
}
