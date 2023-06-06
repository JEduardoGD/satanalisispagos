package mx.egd.sat.descargopagoanalizer.daos;

import lombok.Data;

@Data
public class ResultadosAnalisis extends Categoria {
	private Categoria efectivo;
	private Categoria virtuales;
	private Categoria noEfectivoNoVirtuales;
	private Categoria dobleTransacBaja;
	private Categoria reclamados;
	private Categoria todos;
	
	public ResultadosAnalisis() {
		this.efectivo = new Categoria();
		this.virtuales = new Categoria();
		this.noEfectivoNoVirtuales = new Categoria();
		this.dobleTransacBaja = new Categoria();
		this.reclamados = new Categoria();
		this.todos = new Categoria();
	}
}
