package mx.egd.sat.descargopagoanalizer.service.impl;

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import mx.egd.sat.descargopagoanalizer.beans.CreditosFiscales;
import mx.egd.sat.descargopagoanalizer.exceptions.ParseUtilException;
import mx.egd.sat.descargopagoanalizer.service.PagosAnalizer;
import mx.egd.sat.descargopagoanalizer.util.FileUtil;
import mx.egd.sat.descargopagoanalizer.util.ParserUtil;

@Service
public class PagosAnalizerImpl implements PagosAnalizer {

	@Override
	public void analize(String sPath) {
		Path path = FileUtil.getPathOf(sPath);
		List<File> xmlFileList = FileUtil.getXmlFileList(path);
		List<CreditosFiscales> beansList = xmlFileList.stream()
		.map(t -> {
			try {
				return ParserUtil.parse(t);
			} catch (ParseUtilException e) {
				System.out.println(e.getMessage());
				return null;
			}
		})
		.filter(u -> u != null)
		.collect(Collectors.toList());
		System.out.println();
	}
}
