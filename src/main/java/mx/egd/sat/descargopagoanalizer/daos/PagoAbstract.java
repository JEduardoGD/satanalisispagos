package mx.egd.sat.descargopagoanalizer.daos;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public abstract class PagoAbstract {
	@JsonProperty("Importe")
	private Long importe;

	@JsonProperty("FechaPago")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd")
	private Date fechaPago;
}