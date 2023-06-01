package mx.egd.sat.descargopagoanalizer.beans;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PagoVirtual {
	@JsonProperty("Importe")
	private long importe;

	@JsonProperty("FechaPago")
	private long fechaPago;

	public long getImporte() {
		return importe;
	}

	public void setImporte(long importe) {
		this.importe = importe;
	}

	public long getFechaPago() {
		return fechaPago;
	}

	public void setFechaPago(long fechaPago) {
		this.fechaPago = fechaPago;
	}

}
