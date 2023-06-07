package mx.egd.sat.descargopagoanalizer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;
import mx.egd.sat.descargopagoanalizer.daos.db.Registro;
import mx.egd.sat.descargopagoanalizer.daos.xml.CreditosFiscales;
import mx.egd.sat.descargopagoanalizer.daos.xml.ObjAnalisisPagadosFecha;
import mx.egd.sat.descargopagoanalizer.daos.xml.ObjReporte;
import mx.egd.sat.descargopagoanalizer.daos.xml.ResultadosAnalisis;
import mx.egd.sat.descargopagoanalizer.service.CifrasService;
import mx.egd.sat.descargopagoanalizer.service.LogsAnalizerService;
import mx.egd.sat.descargopagoanalizer.service.PagosAnalizer;
import mx.egd.sat.descargopagoanalizer.service.ReportePorFechaService;

@SpringBootApplication
@Slf4j
public class DescargopagoanalizerApplication implements CommandLineRunner {

	@Autowired private PagosAnalizer pagosAnalizer;
	@Autowired private LogsAnalizerService logsAnalizerService;
	@Autowired private CifrasService cifrasService;
	@Autowired private ReportePorFechaService reportePorFechaService;

	public static void main(String[] args) {
		SpringApplication.run(DescargopagoanalizerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		if (args != null && args.length == 1) {
			pagosAnalizer.analize(args[0]);
		} else if (args != null && args.length == 2) {
			//se analiza la explotacion de la base de datos
			log.info("Analizando la explotacion de datos PAGOS");
			log.info("{}", args[1]);
			List<Registro> registrosLog = logsAnalizerService.parseLog(args[1]);
			
			//se analizan los archivos en formato XM
			log.info("Analizando los archivos xml");
			log.info("{}", args[0]);
			List<CreditosFiscales> listCreditosFiscales = pagosAnalizer.analize(args[0]);

			log.info("Se generan cifras generales");
			//Se generan las cifras generales
			ResultadosAnalisis resultadosAnalisis = cifrasService.gettingCifras(listCreditosFiscales);
			//Se imprimen las cifras generales
			log.info(resultadosAnalisis.toString());
			
			//Se genera el objeto analisis contrastado con el log
			log.info("Se genera objetos de analisis xml-log");
			List<ObjAnalisisPagadosFecha> objAnalisisPagadosFechaList = reportePorFechaService.createReporteFechas(registrosLog, listCreditosFiscales);
			
			log.info("Generando reporte de aplicacion");
			List<ObjReporte> objReporteList = reportePorFechaService.createReport(objAnalisisPagadosFechaList);
			reportePorFechaService.createSpreadsheet(objReporteList, "reporte.txt");
		} else {
			log.error("Error en los argumentos.");
		}
	}

}
