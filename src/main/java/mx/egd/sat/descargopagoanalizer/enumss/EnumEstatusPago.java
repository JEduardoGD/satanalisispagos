package mx.egd.sat.descargopagoanalizer.enumss;

import lombok.Getter;

public enum EnumEstatusPago {
	REGISTRADO(50001), APLICADO(50003), NO_APLICADO(50002);

	@Getter
	private long estatusNum;

	EnumEstatusPago(int estatusNum) {
		this.estatusNum = estatusNum;
	}

	public static EnumEstatusPago valueOfLabel(long value) {
		for (EnumEstatusPago e : values()) {
			if (e.estatusNum == value) {
				return e;
			}
		}
		return null;
	}
	
	
}
