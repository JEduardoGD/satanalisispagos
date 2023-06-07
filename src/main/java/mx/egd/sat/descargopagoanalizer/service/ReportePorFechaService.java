package mx.egd.sat.descargopagoanalizer.service;

import java.util.List;

import mx.egd.sat.descargopagoanalizer.daos.db.Registro;
import mx.egd.sat.descargopagoanalizer.daos.xml.CreditosFiscales;
import mx.egd.sat.descargopagoanalizer.daos.xml.ObjAnalisisPagadosFecha;
import mx.egd.sat.descargopagoanalizer.daos.xml.ObjReporte;

public interface ReportePorFechaService {

	List<ObjAnalisisPagadosFecha> createReporteFechas(List<Registro> registrosLog, List<CreditosFiscales> listCreditosFiscales);

	List<ObjReporte> createReport(List<ObjAnalisisPagadosFecha> objAnalisisPagadosFechaList);

	void createSpreadsheet(List<ObjReporte> objReporteList, String pathFileOutput);

}
