package mx.egd.sat.descargopagoanalizer.daos.db;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class Registro {
	private Long idpago;
	private String numlinea;
	private String numdocto;
	private Date fecpago;
	private BigDecimal importepagado;
	private BigDecimal importeVirtual;
	private BigDecimal remanente;
	private Long idregistropago;
	private Long idestatus;
	private Long idtransacbaja;
	private Long idlineatipo;
	private Long idbatchcorrida;
	private Long idbatchincidencia;
	private Long idtipopago;
	private Long version;
	private Boolean origendyp;
	private String nombrearchivo;
}
