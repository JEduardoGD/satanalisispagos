package mx.egd.sat.descargopagoanalizer.daos.xml;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class Concepto {
	@JsonProperty("IdConcepto")
	private String idConcepto;

	@JacksonXmlElementWrapper(useWrapping = false)
	@JacksonXmlProperty(localName = "TransaccionBaja")
	List<Long> transaccionBajaList = new ArrayList<>();

	public String getIdConcepto() {
		return idConcepto;
	}

	public void setIdConcepto(String idConcepto) {
		this.idConcepto = idConcepto;
	}

	public List<Long> getTransaccionBajaList() {
		return transaccionBajaList;
	}

	public void setTransaccionBajaList(List<Long> transaccionBajaList) {
		this.transaccionBajaList = transaccionBajaList;
	}

}
