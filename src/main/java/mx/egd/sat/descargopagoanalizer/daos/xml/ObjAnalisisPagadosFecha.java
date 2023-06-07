package mx.egd.sat.descargopagoanalizer.daos.xml;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import mx.egd.sat.descargopagoanalizer.daos.db.Registro;

@Slf4j
@Data
public class ObjAnalisisPagadosFecha {
	private CreditosFiscales creditosFiscales;
	private Registro registro;
	private boolean efectivo = false;
	private boolean virtual = false;
	private Date fechaPago;
	private BigDecimal importePago;
	private Long estatusPago;
}
