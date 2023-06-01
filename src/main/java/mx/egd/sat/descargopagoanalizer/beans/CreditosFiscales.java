package mx.egd.sat.descargopagoanalizer.beans;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;

@XmlRootElement(name = "CreditosFiscales")
public class CreditosFiscales {

	@JsonProperty("DatosGenerales")
	private DatosGenerales datosGenerales;

	@JsonProperty("Resoluciones")
	private Resoluciones resoluciones;

	public DatosGenerales getDatosGenerales() {
		return datosGenerales;
	}

	public void setDatosGenerales(DatosGenerales datosGenerales) {
		this.datosGenerales = datosGenerales;
	}

	public Resoluciones getResoluciones() {
		return resoluciones;
	}

	public void setResoluciones(Resoluciones resoluciones) {
		this.resoluciones = resoluciones;
	}

}
