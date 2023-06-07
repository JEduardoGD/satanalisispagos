package mx.egd.sat.descargopagoanalizer.daos.xml;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class DatosGenerales {
	@JsonProperty("Transaccion")
	private String transaccion;

	@JsonProperty("NumeroDocumento")
	private Long numeroDocumento;

	@JsonProperty("LineaCaptura")
	private String lineaCaptura;

	@JsonProperty("Convenio")
	private String cconvenio;

	@JsonProperty("TipoPago")
	private int tipoPago;

	@JsonProperty("TipoDocumento")
	private int tipoDocumento;

	@JsonProperty("TipoLinea")
	private int tipoLinea;

	@JsonProperty("RFC")
	private String rfc;

	@JsonProperty("ALR")
	private int alr;

	@JsonProperty("Resultado")
	private int resultado;

	@JsonProperty("NumParcialidad")
	private int numParcialidad;

	@JsonProperty("PagoEfectivo")
	private PagoEfectivo pagoEfectivo;

	@JsonProperty("PagoVirtual")
	private PagoVirtual pagoVirtual;
}
