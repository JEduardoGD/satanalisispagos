package mx.egd.sat.descargopagoanalizer.daos;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@XmlRootElement(name = "CreditosFiscales")
public class CreditosFiscales {
	
	@JsonProperty("DatosGenerales")
	private DatosGenerales datosGenerales;

	@JsonProperty("Resoluciones")
	private Resoluciones resoluciones;

	public DatosGenerales getDatosGenerales() {
		return datosGenerales;
	}

}
