package mx.egd.sat.descargopagoanalizer.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import mx.egd.sat.descargopagoanalizer.daos.xml.CreditosFiscales;
import mx.egd.sat.descargopagoanalizer.exceptions.ParseUtilException;

@Slf4j
public class ListFileParserThread extends Thread {

	@Setter
	@Getter
	private List<File> listFile;

	@Setter
	@Getter
	private List<CreditosFiscales> creditosFiscalesList;

	public ListFileParserThread() {
		creditosFiscalesList = new ArrayList<>();
	}

	@Override
	public void run() {
		for (File file : listFile) {
			try {
				creditosFiscalesList.add(ParserUtil.parse(file));
			} catch (ParseUtilException e) {
				log.error(e.getMessage());
			}
		}
	}
}
