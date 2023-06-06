package mx.egd.sat.descargopagoanalizer.util;

import java.io.File;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import mx.egd.sat.descargopagoanalizer.daos.CreditosFiscales;
import mx.egd.sat.descargopagoanalizer.exceptions.ParseUtilException;

@Slf4j
public class FileReaderThread extends Thread {

	@Setter
	@Getter
	private File file;

	@Setter
	@Getter
	private List<CreditosFiscales> creditosFiscalesList;

	public FileReaderThread(String name) {
		this.setName(name);
	}

	public void setInsumos(File file, List<CreditosFiscales> creditosFiscalesList) {
		this.file = file;
		this.creditosFiscalesList = creditosFiscalesList;
	}

	@Override
	public void run() {
		log.debug("Thread {} start", this.getName());
		try {
			CreditosFiscales cf = ParserUtil.parse(this.file);
			creditosFiscalesList.add(cf);
		} catch (ParseUtilException e) {
			log.error(e.getMessage());
		}
		log.debug("Thread {} end", this.getName());
	}
}
