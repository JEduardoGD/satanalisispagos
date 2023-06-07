package mx.egd.sat.descargopagoanalizer.service.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import mx.egd.sat.descargopagoanalizer.daos.db.Registro;
import mx.egd.sat.descargopagoanalizer.daos.xml.CreditosFiscales;
import mx.egd.sat.descargopagoanalizer.daos.xml.ObjAnalisisPagadosFecha;
import mx.egd.sat.descargopagoanalizer.daos.xml.ObjReporte;
import mx.egd.sat.descargopagoanalizer.daos.xml.ObjReporteDetalle;
import mx.egd.sat.descargopagoanalizer.daos.xml.PagoEfectivo;
import mx.egd.sat.descargopagoanalizer.daos.xml.PagoVirtual;
import mx.egd.sat.descargopagoanalizer.enumss.EnumEstatusPago;
import mx.egd.sat.descargopagoanalizer.service.ReportePorFechaService;
import mx.egd.sat.descargopagoanalizer.util.CreditosFiscalesStreamUtil;

@Service
@Slf4j
public class ReportePorFechaServiceImpl extends CreditosFiscalesStreamUtil implements ReportePorFechaService {
	
	private static final String ENCABEZADOS = "fecha" +
	"|efectivo registrados|efectivo no aplicado|efectivo aplicado|efectivo total" + 
	"|virtrual registrados|virtrual no aplicado|virtrual aplicado|virtrual total" +
	"|otro registrados|otro no aplicado|otro aplicado|otro total";
	private static final String SALTO_LINEA = "\n\r";
	
	@Override
	public void createSpreadsheet(List<ObjReporte> objReporteList, String pathFileOutput) {
		List<String> strings = new ArrayList<>();

		strings.add(ENCABEZADOS);
				
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		for (ObjReporte objReporte : objReporteList) {
			ObjReporteDetalle efectivo = objReporte.getEfectivo();
			ObjReporteDetalle virtual = objReporte.getVirtual();
			ObjReporteDetalle otro = objReporte.getOtro();
			strings.add(String.format("%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s", df.format(objReporte.getFecha()),
					efectivo.getRegistrados(), efectivo.getNoAplicados(), efectivo.getAplicados(), efectivo.getCount(),
					virtual.getRegistrados(), virtual.getNoAplicados(), virtual.getAplicados(), virtual.getCount(),
					otro.getRegistrados(), otro.getNoAplicados(), otro.getAplicados(), otro.getCount()));
		}
		
		File file = new File(pathFileOutput);
		
		if(file.exists()) {
			if(!file.canWrite()) {
				log.error("El archivo existe y no se puede borrar");
			}else {
				file.delete();
			}
		}
		
		try (FileWriter fr = new FileWriter(file)) {
			for (String s : strings) {
				fr.write(s.concat(SALTO_LINEA));
			}
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}
	

	@Override
	public List<ObjReporte> createReport(List<ObjAnalisisPagadosFecha> objAnalisisPagadosFechaList) {
		List<ObjReporte> listObjReporte = new ArrayList<>();
		Date minFechaPago = objAnalisisPagadosFechaList.stream().map(ObjAnalisisPagadosFecha::getFechaPago).min(Comparator.naturalOrder()).get();
		Date maxFechaPago = objAnalisisPagadosFechaList.stream().map(ObjAnalisisPagadosFecha::getFechaPago).max(Comparator.naturalOrder()).get();
		Calendar maxFechaPagoPlusDateCalendar = Calendar.getInstance();
		maxFechaPagoPlusDateCalendar.setTime(maxFechaPago);
		maxFechaPagoPlusDateCalendar.add(Calendar.DATE, 1);
		Date maxFechaPagoPlusDate = maxFechaPagoPlusDateCalendar.getTime();
		
		Date fechaActual = minFechaPago;
		
		while (fechaActual.before(maxFechaPagoPlusDate)) {
			final Date fechaActualFinal = fechaActual;
			List<ObjAnalisisPagadosFecha> listObj = objAnalisisPagadosFechaList.stream()
					.filter(apf -> apf.getFechaPago().compareTo(fechaActualFinal) == 0)
					.collect(Collectors.toList());
			
			ObjReporte objReporte = new ObjReporte();
			
			objReporte.setFecha(fechaActual);
			
			objReporte.getEfectivo().setCount(listObj.stream().filter(ObjAnalisisPagadosFecha::isEfectivo).count());
			objReporte.getEfectivo().setRegistrados(listObj.stream().filter(ObjAnalisisPagadosFecha::isEfectivo).filter(apf -> apf.getEstatusPago() != null && apf.getEstatusPago().equals(EnumEstatusPago.REGISTRADO)).count());
			objReporte.getEfectivo().setNoAplicados(listObj.stream().filter(ObjAnalisisPagadosFecha::isEfectivo).filter(apf -> apf.getEstatusPago() != null && apf.getEstatusPago().equals(EnumEstatusPago.NO_APLICADO)).count());
			objReporte.getEfectivo().setAplicados(listObj.stream().filter(ObjAnalisisPagadosFecha::isEfectivo).filter(apf -> apf.getEstatusPago() != null && apf.getEstatusPago().equals(EnumEstatusPago.APLICADO)).count());
			
			objReporte.getVirtual().setCount(listObj.stream().filter(ObjAnalisisPagadosFecha::isVirtual).count());
			objReporte.getVirtual().setRegistrados(listObj.stream().filter(ObjAnalisisPagadosFecha::isVirtual).filter(apf -> apf.getEstatusPago() != null && apf.getEstatusPago().equals(EnumEstatusPago.REGISTRADO)).count());
			objReporte.getVirtual().setNoAplicados(listObj.stream().filter(ObjAnalisisPagadosFecha::isVirtual).filter(apf -> apf.getEstatusPago() != null && apf.getEstatusPago().equals(EnumEstatusPago.NO_APLICADO)).count());
			objReporte.getVirtual().setAplicados(listObj.stream().filter(ObjAnalisisPagadosFecha::isVirtual).filter(apf -> apf.getEstatusPago() != null && apf.getEstatusPago().equals(EnumEstatusPago.APLICADO)).count());
			
			objReporte.getOtro().setCount(listObj.stream().filter(cf -> !cf.isEfectivo() && !cf.isVirtual()).count());
			objReporte.getOtro().setRegistrados(listObj.stream().filter(cf -> !cf.isEfectivo() && !cf.isVirtual()).filter(apf -> apf.getEstatusPago() != null && apf.getEstatusPago().equals(EnumEstatusPago.REGISTRADO)).count());
			objReporte.getOtro().setNoAplicados(listObj.stream().filter(cf -> !cf.isEfectivo() && !cf.isVirtual()).filter(apf -> apf.getEstatusPago() != null && apf.getEstatusPago().equals(EnumEstatusPago.NO_APLICADO)).count());
			objReporte.getOtro().setAplicados(listObj.stream().filter(cf -> !cf.isEfectivo() && !cf.isVirtual()).filter(apf -> apf.getEstatusPago() != null && apf.getEstatusPago().equals(EnumEstatusPago.APLICADO)).count());
			
			listObjReporte.add(objReporte);
			
			Calendar fechaActualCalendar = Calendar.getInstance();
			fechaActualCalendar.setTime(fechaActual);
			fechaActualCalendar.add(Calendar.DATE, 1);
			fechaActual = fechaActualCalendar.getTime();
		}
		
		return listObjReporte;
	}

	@Override
	public List<ObjAnalisisPagadosFecha> createReporteFechas(List<Registro> registrosLog,
			List<CreditosFiscales> listCreditosFiscales) {

		List<ObjAnalisisPagadosFecha> analisisPagadosFechaList = listCreditosFiscales.stream().map(cf -> {
			ObjAnalisisPagadosFecha apf = new ObjAnalisisPagadosFecha();
			apf.setCreditosFiscales(cf);
			if (isEfectivo(cf)) {
				apf.setEfectivo(true);
				apf.setVirtual(false);
				PagoEfectivo pagoEfectivo = cf.getDatosGenerales().getPagoEfectivo();
				apf.setFechaPago(pagoEfectivo.getFechaPago());
				apf.setImportePago(BigDecimal.valueOf(pagoEfectivo.getImporte()));
				Registro registro = registrosLog.stream()
						.filter(rl -> rl.getNumlinea() != null)
						.filter(rl -> rl.getNumlinea().equals(cf.getDatosGenerales().getLineaCaptura())).findFirst()
						.orElse(null);
				apf.setRegistro(registro);
				apf.setEstatusPago(registro != null ? EnumEstatusPago.valueOfLabel(registro.getIdestatus()) : null);
			}
			if (isVirtual(cf)) {
				apf.setEfectivo(false);
				apf.setVirtual(true);
				PagoVirtual pagoVirtual = cf.getDatosGenerales().getPagoVirtual();
				apf.setFechaPago(pagoVirtual.getFechaPago());
				apf.setImportePago(BigDecimal.valueOf(pagoVirtual.getImporte()));
				Registro registro = registrosLog.stream()
						.filter(rl -> rl.getNumdocto() != null).filter(
						rl -> rl.getNumdocto().equals(String.valueOf(cf.getDatosGenerales().getNumeroDocumento())))
						.findFirst().orElse(null);
				apf.setRegistro(registro);
				apf.setEstatusPago(registro != null ? EnumEstatusPago.valueOfLabel(registro.getIdestatus()) : null);
			}
			if (!isEfectivo(cf) && !isVirtual(cf)) {
				apf.setEfectivo(false);
				apf.setVirtual(false);
				PagoEfectivo pagoEfectivo = cf.getDatosGenerales().getPagoEfectivo();
				apf.setFechaPago(pagoEfectivo.getFechaPago());
				apf.setImportePago(BigDecimal.valueOf(pagoEfectivo.getImporte()));
				Registro registro = registrosLog.stream()
						.filter(rl -> rl.getNumlinea() != null)
						.filter(rl -> rl.getNumlinea().equals(cf.getDatosGenerales().getLineaCaptura())).findFirst()
						.orElse(null);
				apf.setRegistro(registro);
				apf.setEstatusPago(registro != null ? EnumEstatusPago.valueOfLabel(registro.getIdestatus()) : null);
			}
			return apf;
		}).collect(Collectors.toList());

		return analisisPagadosFechaList;
	}
}
