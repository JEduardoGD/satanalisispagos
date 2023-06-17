package mx.egd.sat.descargopagoanalizer.service.impl;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import mx.egd.sat.descargopagoanalizer.daos.xml.CreditosFiscales;
import mx.egd.sat.descargopagoanalizer.daos.xml.DatosGenerales;
import mx.egd.sat.descargopagoanalizer.daos.xml.PagoEfectivo;
import mx.egd.sat.descargopagoanalizer.daos.xml.PagoVirtual;
import mx.egd.sat.descargopagoanalizer.daos.xml.ResultadosAnalisis;
import mx.egd.sat.descargopagoanalizer.service.CifrasService;
import mx.egd.sat.descargopagoanalizer.util.CreditosFiscalesStreamUtil;

@Service
public class CifrasServiceImpl extends CreditosFiscalesStreamUtil implements CifrasService {

	/**
	 *
	 */
	@Override
	public ResultadosAnalisis gettingCifras(List<CreditosFiscales> beansList) {

		ResultadosAnalisis resultadosAnalisis = new ResultadosAnalisis();

		List<CreditosFiscales> virtuales = beansList.stream().filter(CreditosFiscalesStreamUtil::isVirtual)
				.collect(Collectors.toList());

		resultadosAnalisis.getVirtuales().setEncontrados(virtuales.size());

		List<CreditosFiscales> efectivo = beansList.stream().filter(CreditosFiscalesStreamUtil::isEfectivo)
				.collect(Collectors.toList());
		resultadosAnalisis.getEfectivo().setEncontrados(efectivo.size());

		List<CreditosFiscales> noEfectivoNoVirutales = beansList.stream()
				.filter(cf -> !CreditosFiscalesStreamUtil.isEfectivo(cf) && !CreditosFiscalesStreamUtil.isVirtual(cf))
				.collect(Collectors.toList());
		resultadosAnalisis.getNoEfectivoNoVirtuales().setEncontrados(noEfectivoNoVirutales.size());

		Date minVirtual = virtuales.stream().map(CreditosFiscales::getDatosGenerales)
				.map(DatosGenerales::getPagoVirtual).map(PagoVirtual::getFechaPago).min(Comparator.naturalOrder())
				.orElse(null);
		resultadosAnalisis.getVirtuales().setMasAntiguo(minVirtual);

		Date maxVirtual = virtuales.stream().map(CreditosFiscales::getDatosGenerales)
				.map(DatosGenerales::getPagoVirtual).map(PagoVirtual::getFechaPago).max(Comparator.naturalOrder())
				.orElse(null);
		resultadosAnalisis.getVirtuales().setMasReciente(maxVirtual);

		Date minEfectivo = efectivo.stream().map(CreditosFiscales::getDatosGenerales)
				.map(DatosGenerales::getPagoEfectivo).map(PagoEfectivo::getFechaPago).min(Comparator.naturalOrder())
				.orElse(null);
		resultadosAnalisis.getEfectivo().setMasAntiguo(minEfectivo);

		Date maxEfectivo = efectivo.stream().map(CreditosFiscales::getDatosGenerales)
				.map(DatosGenerales::getPagoEfectivo).map(PagoEfectivo::getFechaPago).max(Comparator.naturalOrder())
				.orElse(null);
		resultadosAnalisis.getEfectivo().setMasReciente(maxEfectivo);

		List<CreditosFiscales> doubleTransactionBaja = beansList.stream().filter(CreditosFiscalesStreamUtil::doubleTF)
				.collect(Collectors.toList());
		resultadosAnalisis.getDobleTransacBaja().setEncontrados(doubleTransactionBaja.size());

		Date minTransactionBaja = doubleTransactionBaja.stream().map(CreditosFiscales::getDatosGenerales)
				.map(DatosGenerales::getPagoVirtual).map(PagoVirtual::getFechaPago).min(Comparator.naturalOrder())
				.orElse(null);
		resultadosAnalisis.getDobleTransacBaja().setMasAntiguo(minTransactionBaja);

		Date maxTransactionBaja = doubleTransactionBaja.stream().map(CreditosFiscales::getDatosGenerales)
				.map(DatosGenerales::getPagoVirtual).map(PagoVirtual::getFechaPago).max(Comparator.naturalOrder())
				.orElse(null);
		resultadosAnalisis.getDobleTransacBaja().setMasReciente(maxTransactionBaja);

		List<Date> fechasPagoEfectivo = efectivo.stream().map(CreditosFiscales::getDatosGenerales)
				.map(DatosGenerales::getPagoEfectivo).map(PagoEfectivo::getFechaPago).collect(Collectors.toList());

		List<Date> fechasPagoVirtual = virtuales.stream().map(CreditosFiscales::getDatosGenerales)
				.map(DatosGenerales::getPagoVirtual).map(PagoVirtual::getFechaPago).collect(Collectors.toList());

		List<Date> fechasPago = fechasPagoEfectivo;
		fechasPago.addAll(fechasPagoVirtual);

		Date fechaMinPago = fechasPago.stream().min(Comparator.naturalOrder()).orElse(null);
		Date fechaMaxPago = fechasPago.stream().max(Comparator.naturalOrder()).orElse(null);
		resultadosAnalisis.getReclamados().setEncontrados(fechasPago.size());
		resultadosAnalisis.getReclamados().setMasAntiguo(fechaMinPago);
		resultadosAnalisis.getReclamados().setMasReciente(fechaMaxPago);

		resultadosAnalisis.getTodos().setEncontrados(beansList.size());

		return resultadosAnalisis;

	}
}
