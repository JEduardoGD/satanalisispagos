package mx.egd.sat.descargopagoanalizer.daos.xml;

import java.util.Date;

import lombok.Data;

@Data
public class ObjReporte {
	private Date fecha;
	private ObjReporteDetalle efectivo = new ObjReporteDetalle();
	private ObjReporteDetalle virtual = new ObjReporteDetalle();
	private ObjReporteDetalle diferente = new ObjReporteDetalle();
}
