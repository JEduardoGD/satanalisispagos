package mx.egd.sat.descargopagoanalizer.daos.xml;

import lombok.Data;

@Data
public class ObjReporteDetalle {
	private long count;
	private long noEncontrados;
	private long registrados;
	private long noAplicados;
	private long aplicados;
	private long otroEstatus;
}
