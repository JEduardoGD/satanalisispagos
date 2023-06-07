package mx.egd.sat.descargopagoanalizer.daos.xml;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public abstract class PagoAbstract {
	@JsonProperty("Importe")
	private Long importe;

	@JsonProperty("FechaPago")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd", timezone="America/Mexico_City")
	private Date fechaPago;
}