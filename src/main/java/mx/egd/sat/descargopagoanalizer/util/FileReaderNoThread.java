package mx.egd.sat.descargopagoanalizer.util;

import java.io.File;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import mx.egd.sat.descargopagoanalizer.daos.xml.CreditosFiscales;
import mx.egd.sat.descargopagoanalizer.exceptions.ParseUtilException;

@Slf4j
public class FileReaderNoThread {
	
	@Getter
	@Setter
	private String name;

	@Setter
	@Getter
	private File file;

	@Setter
	@Getter
	private List<CreditosFiscales> creditosFiscalesList;

	public FileReaderNoThread(String name) {
		this.setName(name);
	}

	public void setInsumos(File file, List<CreditosFiscales> creditosFiscalesList) {
		this.file = file;
		this.creditosFiscalesList = creditosFiscalesList;
	}

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
