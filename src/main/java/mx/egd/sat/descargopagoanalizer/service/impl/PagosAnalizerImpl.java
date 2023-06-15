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
	}
}
