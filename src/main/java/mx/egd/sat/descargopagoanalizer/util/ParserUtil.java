package mx.egd.sat.descargopagoanalizer.util;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import mx.egd.sat.descargopagoanalizer.beans.CreditosFiscales;
import mx.egd.sat.descargopagoanalizer.exceptions.ParseUtilException;

public abstract class ParserUtil {
	public static CreditosFiscales parse(File file) throws ParseUtilException {
		XmlMapper xmlMapper = new XmlMapper();
		//xmlMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		try {
			return xmlMapper.readValue(file, CreditosFiscales.class);
			//return xmlMapper.readValue("<CreditosFiscales><DatosGenerales><LineaCaptura>0423090K665638742261</LineaCaptura></DatosGenerales></CreditosFiscales>", CreditosFiscales.class);
			//return xmlMapper.readValue("<CreditosFiscales xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><DatosGenerales><LineaCaptura>0423090K665638742261</LineaCaptura></DatosGenerales></CreditosFiscales>", CreditosFiscales.class);
		} catch (IOException e) {
			throw new ParseUtilException(e);
		}
	}
}
