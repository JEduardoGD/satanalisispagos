package mx.egd.sat.descargopagoanalizer.service.impl;

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import mx.egd.sat.descargopagoanalizer.daos.xml.CreditosFiscales;
import mx.egd.sat.descargopagoanalizer.exceptions.ParseUtilException;
import mx.egd.sat.descargopagoanalizer.service.CifrasService;
import mx.egd.sat.descargopagoanalizer.service.ConversionExcelService;
import mx.egd.sat.descargopagoanalizer.service.PagosAnalizer;
import mx.egd.sat.descargopagoanalizer.util.FileReaderThread;
import mx.egd.sat.descargopagoanalizer.util.FileUtil;
import mx.egd.sat.descargopagoanalizer.util.ParserUtil;


@Service
@Slf4j
public class PagosAnalizerImpl implements PagosAnalizer {

	FileReaderThread fileReaderThreadArray[] = new FileReaderThread[5];

	@Autowired
	ConversionExcelService conversionExcelService;
	@Autowired
	CifrasService cifrasService;

	@Override
	public List<CreditosFiscales> analize(String sPath) {
		Path path = FileUtil.getPathOf(sPath);
		List<File> xmlFileList = FileUtil.getXmlFileList(path);

		return xmlFileList.stream().map(xml -> {
			try {
				return ParserUtil.parse(xml);
			} catch (ParseUtilException e) {
				log.error("Error al parsear el archivo {}", xml.getName());
			}
			return null;
		}).collect(Collectors.toList());
		
		/*
		ListFileParserThread[] listListFileParserThread = new ListFileParserThread[4];
		listListFileParserThread = divide(listListFileParserThread, xmlFileList);

		for (ListFileParserThread listFileParserThread : listListFileParserThread) {
			if (listFileParserThread != null) {
				listFileParserThread.start();
			}
		}
		
		for (ListFileParserThread listFileParserThread : listListFileParserThread) {
			if (listFileParserThread != null) {
				do {
					try {
						Thread.sleep(5 * 1000);
					} catch (InterruptedException e) {
						log.error(e.getMessage());
					}
				} while (!listFileParserThread.getState().equals(Thread.State.TERMINATED));
			}
		}
		*/
	}
	
	/*
	private ListFileParserThread[] divide(ListFileParserThread[] listListFileParserThread, List<File> xmlFileList) {
		int ListFileParserThreadArrayLength = listListFileParserThread.length;
		int xmlFileListLen = xmlFileList.size();
		int fromIndex = 0;
		int intDivision = xmlFileListLen / ListFileParserThreadArrayLength;
		int toIndex = intDivision;
		if (xmlFileListLen >= ListFileParserThreadArrayLength) {
			int residuoDivision = xmlFileListLen % ListFileParserThreadArrayLength;
			for (int i = 0; i < listListFileParserThread.length; i++) {
				List<File> xmlFileSubList = xmlFileList.subList(fromIndex, toIndex);
				listListFileParserThread[i] = new ListFileParserThread();
				listListFileParserThread[i].setListFile(xmlFileSubList);
				fromIndex += intDivision;
				toIndex = fromIndex + intDivision;
			}
			if (residuoDivision > 0) {
				// sublist of final element
				List<File> xmlFileSubListOriginal = listListFileParserThread[listListFileParserThread.length - 1]
						.getListFile();
				// elementos fuera de la division
				List<File> xmlFileSubList = xmlFileList.subList(fromIndex, xmlFileListLen);
				// juntar ambas listas
				xmlFileSubListOriginal.addAll(xmlFileSubList);
				// setear nueva sublist
				listListFileParserThread[listListFileParserThread.length - 1].setListFile(xmlFileSubListOriginal);
			}
		} else {
			listListFileParserThread[0].setListFile(xmlFileList);
		}
		return listListFileParserThread;
	}
	*/
	
	
/*
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
	}.*/
}
