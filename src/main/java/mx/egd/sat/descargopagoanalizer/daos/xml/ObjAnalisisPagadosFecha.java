package mx.egd.sat.descargopagoanalizer.daos.xml;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;
import mx.egd.sat.descargopagoanalizer.daos.db.Registro;
import mx.egd.sat.descargopagoanalizer.enumss.EnumEstatusPago;

@Data
public class ObjAnalisisPagadosFecha {
	private CreditosFiscales creditosFiscales;
	private Registro registro;
	private boolean efectivo = false;
	private boolean virtual = false;
	private Date fechaPago;
	private BigDecimal importePago;
	private EnumEstatusPago estatusPago;
}
