package mx.egd.sat.descargopagoanalizer.daos.db;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import mx.egd.sat.descargopagoanalizer.enumss.EnumTipoPago;

public class Registro {
	@Getter
	@Setter
	private Long idpago;
	@Getter
	@Setter
	private String numlinea;
	@Getter
	@Setter
	private String numdocto;
	@Getter
	@Setter
	private Date fecpago;
	@Getter
	@Setter
	private BigDecimal importepagado;
	@Getter
	@Setter
	private BigDecimal importeVirtual;
	@Getter
	@Setter
	private BigDecimal remanente;
	@Getter
	@Setter
	private Long idregistropago;
	@Getter
	@Setter
	private Long idestatus;
	@Getter
	@Setter
	private Long idtransacbaja;
	@Getter
	@Setter
	private Long idlineatipo;
	@Getter
	@Setter
	private Long idbatchcorrida;
	@Getter
	@Setter
	private Long idbatchincidencia;
	@Getter
	@Setter
	private Long idtipopago;
	@Getter
	@Setter
	private Long version;
	@Getter
	@Setter
	private Boolean origendyp;
	@Getter
	@Setter
	private String nombrearchivo;
	@Getter
	@Setter
	private int encontrados = 0;
	@Getter
	@Setter
	private EnumTipoPago tipoPago;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idpago == null) ? 0 : idpago.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Registro other = (Registro) obj;
		if (idpago == null) {
			if (other.idpago != null)
				return false;
		} else if (!idpago.equals(other.idpago))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Registro [idpago=" + idpago + ", numlinea=" + numlinea + ", numdocto=" + numdocto + ", idestatus="
				+ idestatus + "]";
	}

}
