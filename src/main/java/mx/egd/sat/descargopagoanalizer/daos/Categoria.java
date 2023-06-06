package mx.egd.sat.descargopagoanalizer.daos;

import java.util.Date;

import lombok.Data;

@Data
public class Categoria {
	private long encontrados;
	private Date masAntiguo;
	private Date masReciente;
}
