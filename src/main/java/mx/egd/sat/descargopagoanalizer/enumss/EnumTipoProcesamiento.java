package mx.egd.sat.descargopagoanalizer.enumss;

import lombok.Getter;

public enum EnumTipoProcesamiento {
	POR_ARCHIVO("porarchivo"), POR_CARPETA("porcarpeta");

	@Getter
	private String tipo;

	EnumTipoProcesamiento(String tipo) {
		this.tipo = tipo;
	}

	public static EnumTipoProcesamiento parse(String str) {
		if (EnumTipoProcesamiento.POR_ARCHIVO.compare(str)) {
			return EnumTipoProcesamiento.POR_ARCHIVO;
		}
		if (EnumTipoProcesamiento.POR_CARPETA.compare(str)) {
			return EnumTipoProcesamiento.POR_CARPETA;
		}
		return null;
	}

	public boolean compare(Object obj) {
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof String)) {
			return false;
		}
		return ((String) obj).equals(this.tipo);
	}
}
