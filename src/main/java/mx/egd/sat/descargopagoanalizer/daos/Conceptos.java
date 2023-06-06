package mx.egd.sat.descargopagoanalizer.daos;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import mx.egd.sat.descargopagoanalizer.deserializer.DietDeserializer;

public class Conceptos {

	@JacksonXmlElementWrapper(useWrapping = false)
	@JacksonXmlProperty(localName = "Concepto")
	List<Concepto> conceptoList = new ArrayList<>();

	public List<Concepto> getConceptoList() {
		return conceptoList;
	}

	public void setConceptoList(List<Concepto> conceptoList) {
		this.conceptoList = conceptoList;
	}

}
