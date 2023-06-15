package mx.egd.sat.descargopagoanalizer.daos.cifracontrol;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;
import mx.egd.sat.descargopagoanalizer.enumss.EnumTipoPago;

@Data
public class Cifracontrol {
	private Long numerodocumento;
	private Date fechapago;
	private String rfc;
	private BigDecimal importepagado;
	private Long numero;
	private String lineacaptura;
	private Long numerob;
	private Long tipodocumento;
	private EnumTipoPago tipoPago;
}
