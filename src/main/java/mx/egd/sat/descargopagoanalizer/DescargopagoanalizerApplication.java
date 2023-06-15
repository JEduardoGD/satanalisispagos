package mx.egd.sat.descargopagoanalizer;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;
import mx.egd.sat.descargopagoanalizer.daos.cifracontrol.Cifracontrol;
import mx.egd.sat.descargopagoanalizer.daos.db.Registro;
import mx.egd.sat.descargopagoanalizer.daos.xml.CreditosFiscales;
import mx.egd.sat.descargopagoanalizer.daos.xml.ObjAnalisisPagadosFecha;
import mx.egd.sat.descargopagoanalizer.daos.xml.ObjReporte;
import mx.egd.sat.descargopagoanalizer.daos.xml.ResultadosAnalisis;
import mx.egd.sat.descargopagoanalizer.service.AnalizaCifrasControlService;
import mx.egd.sat.descargopagoanalizer.service.CifrasService;
import mx.egd.sat.descargopagoanalizer.service.GeneraInformeService;
import mx.egd.sat.descargopagoanalizer.service.LogsAnalizerService;
import mx.egd.sat.descargopagoanalizer.service.PagosAnalizer;
import mx.egd.sat.descargopagoanalizer.service.ReportePorFechaService;
import mx.egd.sat.descargopagoanalizer.util.StaticValuesUtil;

@SpringBootApplication
@Slf4j
public class DescargopagoanalizerApplication implements CommandLineRunner {

	@Autowired private PagosAnalizer pagosAnalizer;
	@Autowired private LogsAnalizerService logsAnalizerService;
	@Autowired private CifrasService cifrasService;
	@Autowired private ReportePorFechaService reportePorFechaService;
	@Autowired private AnalizaCifrasControlService analizaCifrasControlService;
	@Autowired private GeneraInformeService generaInformeService;

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

		} else if (args != null && args.length == 3) {
			log.info("Analiza archivo cifras de control");
			log.info("{}", args[0]);
			
			Boolean isFile = analizaCifrasControlService.isFile(args[0]);
			Boolean isFolder= analizaCifrasControlService.isFolder(args[0]);
			
			List<Cifracontrol> cifracontrolList = null;
			
			if (isFile != null && isFile.booleanValue()) {
				cifracontrolList = analizaCifrasControlService.creaListaCifrasControlFile(args[0]);
			}
			if (isFolder != null && isFolder.booleanValue()) {
				cifracontrolList = analizaCifrasControlService.creaListaCifrasControlFolder(args[0]);
			}

			log.info("Analizando la explotacion de datos PAGOS");
			log.info("{}", args[1]);
			List<Registro> registrosLog = logsAnalizerService.parseLog(args[1]);
			
			List<Registro> finalLista = null;

			if (cifracontrolList != null) {
				log.info("Creando lista final");
				finalLista = analizaCifrasControlService.contrasta(cifracontrolList, registrosLog);
			} else {
				log.info("No se pudo generar la lista final");
			}

			if (finalLista != null && isFile != null && isFile.booleanValue()) {
				log.info("Generando informe file");
				generaInformeService.creaInformeExcel(finalLista, args[0]);
			} else if (finalLista != null && isFolder != null && isFolder.booleanValue()) {
				log.info("Generando informe folder");
				String filename = args[0] + File.separator + StaticValuesUtil.ANALISIS_TXT + StaticValuesUtil.FOLDER;
				generaInformeService.creaInformeExcel(finalLista, filename);
			} else {
				log.info("No se pudo generar informe ");
			}
			
		} else {
			log.error("Error en los argumentos.");
		}
		log.info("FIN...");
	}

}
