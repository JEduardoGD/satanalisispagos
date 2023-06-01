package mx.egd.sat.descargopagoanalizer.beans;

import com.fasterxml.jackson.annotation.JsonProperty;

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

	public int getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(int tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public int getTipoLinea() {
		return tipoLinea;
	}

	public void setTipoLinea(int tipoLinea) {
		this.tipoLinea = tipoLinea;
	}

	public String getRfc() {
		return rfc;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	public int getAlr() {
		return alr;
	}

	public void setAlr(int alr) {
		this.alr = alr;
	}

	public int getResultado() {
		return resultado;
	}

	public void setResultado(int resultado) {
		this.resultado = resultado;
	}

	public int getNumParcialidad() {
		return numParcialidad;
	}

	public void setNumParcialidad(int numParcialidad) {
		this.numParcialidad = numParcialidad;
	}

	public PagoEfectivo getPagoEfectivo() {
		return pagoEfectivo;
	}

	public void setPagoEfectivo(PagoEfectivo pagoEfectivo) {
		this.pagoEfectivo = pagoEfectivo;
	}

	public String getTransaccion() {
		return transaccion;
	}

	public void setTransaccion(String transaccion) {
		this.transaccion = transaccion;
	}

	public String getLineaCaptura() {
		return lineaCaptura;
	}

	public void setLineaCaptura(String lineaCaptura) {
		this.lineaCaptura = lineaCaptura;
	}

	public Long getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(Long numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public String getCconvenio() {
		return cconvenio;
	}

	public void setCconvenio(String cconvenio) {
		this.cconvenio = cconvenio;
	}

	public int getTipoPago() {
		return tipoPago;
	}

	public void setTipoPago(int tipoPago) {
		this.tipoPago = tipoPago;
	}

}
