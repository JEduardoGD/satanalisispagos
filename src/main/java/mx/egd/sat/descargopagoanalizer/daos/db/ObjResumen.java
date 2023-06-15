package mx.egd.sat.descargopagoanalizer.daos.db;

import lombok.Data;

@Data
public class ObjResumen {
	private int todos;
	private int registrado;
	private int noAplicado;
	private int noEncontrado;
}
