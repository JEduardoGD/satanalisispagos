package mx.egd.sat.descargopagoanalizer.util;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import mx.egd.sat.descargopagoanalizer.daos.CreditosFiscales;
import mx.egd.sat.descargopagoanalizer.exceptions.ParseUtilException;

public abstract class ParserUtil {
	public static CreditosFiscales parse(File file) throws ParseUtilException {
		XmlMapper xmlMapper = new XmlMapper();
		try {
			return xmlMapper.readValue(file, CreditosFiscales.class);
		} catch (IOException e) {
			throw new ParseUtilException(e);
		}
	}
}
