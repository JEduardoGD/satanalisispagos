package mx.egd.sat.descargopagoanalizer.service.impl;

import java.io.File;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import mx.egd.sat.descargopagoanalizer.daos.CreditosFiscales;
import mx.egd.sat.descargopagoanalizer.daos.ResultadosAnalisis;
import mx.egd.sat.descargopagoanalizer.service.CifrasService;
import mx.egd.sat.descargopagoanalizer.service.ConversionExcelService;
import mx.egd.sat.descargopagoanalizer.service.PagosAnalizer;
import mx.egd.sat.descargopagoanalizer.util.FileReaderThread;
import mx.egd.sat.descargopagoanalizer.util.FileUtil;
import mx.egd.sat.descargopagoanalizer.util.TimmerUtil;

@Service
@Slf4j
public class PagosAnalizerImpl implements PagosAnalizer {

	FileReaderThread fileReaderThreadArray[] = new FileReaderThread[5];

	@Autowired
	ConversionExcelService conversionExcelService;
	@Autowired
	CifrasService cifrasService;

	@Override
	public void analize(String sPath) {
		Path path = FileUtil.getPathOf(sPath);
		List<File> xmlFileList = FileUtil.getXmlFileList(path);
		List<CreditosFiscales> beansList = new ArrayList<>();

		boolean firstIteration = true;
		int k = 1;
		LocalDateTime startTime = LocalDateTime.now();

		FileReaderThread fileReaderThread = null;
		for (File xmlFile : xmlFileList) {
			startTime = TimmerUtil.timmer(startTime, k++, xmlFileList.size());
			do {
				if (!firstIteration) {
					try {
						log.debug("wait for {} assignment...", xmlFile.getName());
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				firstIteration = false;
			} while ((fileReaderThread = this.getNextReady()) == null);
			log.debug("Asignacion del archivo {} al Thread {}", xmlFile.getName(), fileReaderThread.getName());
			fileReaderThread.setInsumos(xmlFile, beansList);
			fileReaderThread.start();
			firstIteration = true;
		}

		// wait for all threads
		for (int i = 0; i < this.fileReaderThreadArray.length; i++) {
			if(fileReaderThreadArray[i] == null) {
				continue;
			}
			do {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					log.error(e.getMessage());
				}
			} while (!fileReaderThreadArray[i].getState().equals(Thread.State.TERMINATED));
		}

		ResultadosAnalisis resultadosAnalisis = cifrasService.gettingCifras(beansList);
		log.info(resultadosAnalisis.toString());
	}

	private FileReaderThread getNextReady() {
		int nextRand = (int) ((Math.random() * ((fileReaderThreadArray.length - 1) - 0)) + 0);
		FileReaderThread frt = fileReaderThreadArray[nextRand];
		String threadName = "FileReaderThread " + nextRand;
		if (frt == null) {
			frt = new FileReaderThread(threadName);
			fileReaderThreadArray[nextRand] = frt;
			return frt;
		}
		log.debug("{} status: {}", threadName, frt.getState().toString());
		if (frt.getState().equals(Thread.State.TERMINATED)) {
			frt = new FileReaderThread(threadName);
			fileReaderThreadArray[nextRand] = frt;
			return frt;
		}
		return null;
	}
}
