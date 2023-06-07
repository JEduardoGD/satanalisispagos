package mx.egd.sat.descargopagoanalizer.util;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import mx.egd.sat.descargopagoanalizer.daos.xml.Concepto;
import mx.egd.sat.descargopagoanalizer.daos.xml.CreditosFiscales;
import mx.egd.sat.descargopagoanalizer.daos.xml.DatosGenerales;

public abstract class CreditosFiscalesStreamUtil {	
	protected List<Integer> getDistintosPagos(List<CreditosFiscales> beansList) {
		return beansList.stream()
		.filter(cf -> cf.getDatosGenerales() != null)
		.map(CreditosFiscales::getDatosGenerales)
		.map(DatosGenerales::getTipoPago)
		.distinct()
        .collect(Collectors.toList());
	}
	
	public static boolean isVirtual(CreditosFiscales cf) {
		boolean sinLineaCaptura = Pattern.compile(StaticValuesUtil.ONLY_CERO_REGEX)
				.matcher(cf.getDatosGenerales().getLineaCaptura()).matches();
		Long importePagoVirtual = Long.valueOf(0);
		if (
				cf.getDatosGenerales() != null &&
				cf.getDatosGenerales().getPagoVirtual() != null && 
				cf.getDatosGenerales().getPagoVirtual().getImporte() != null) {
			importePagoVirtual = cf.getDatosGenerales().getPagoVirtual().getImporte();
		}
		boolean importePagoVirtualGreatherThanCero = importePagoVirtual.compareTo(Long.valueOf(0)) > 0;
		boolean x = sinLineaCaptura && importePagoVirtualGreatherThanCero;
		return x;
	}
	
	public static boolean isEfectivo(CreditosFiscales cf) {
		return Pattern.compile(StaticValuesUtil.FOURTYFOUR_REGEX)
				.matcher(cf.getDatosGenerales().getLineaCaptura()).matches();
	}
	
	public static boolean doubleTF(CreditosFiscales cf) {
		if(
				cf.getResoluciones() != null &&
				cf.getResoluciones().getResolucion() != null &&
				cf.getResoluciones().getResolucion().getConceptos() != null &&
				cf.getResoluciones().getResolucion().getConceptos().getConceptoList() != null
				) {
			for (Concepto concepto : cf.getResoluciones().getResolucion().getConceptos().getConceptoList()) {
				if (concepto.getTransaccionBajaList() != null && concepto.getTransaccionBajaList().size() > 1) {
					return true;
				}
			}
		}
		return false;
		
	}
}
