package mx.egd.sat.descargopagoanalizer.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mx.egd.sat.descargopagoanalizer.daos.db.Registro;
import mx.egd.sat.descargopagoanalizer.exceptions.ParseUtilException;

public interface LogLoaderUtil {
	
	public static Registro parse(String s) throws ParseUtilException {
		String[] arr = s.concat(StaticValuesUtil.ONE_SPACE).split(StaticValuesUtil.REGEX_ONE_PIPE);
		Registro registro = new Registro();
		
		registro.setIdpago(parseLong(arr[0]));
		registro.setNumlinea(parseString(arr[1]));
		registro.setNumdocto(parseString(arr[2]));
		registro.setFecpago(parseDate(arr[3]));
		registro.setImportepagado(parseBigDecimal(arr[4]));
		registro.setImporteVirtual(parseBigDecimal(arr[5]));
		registro.setRemanente(parseBigDecimal(arr[6]));
		registro.setIdregistropago(parseLong(arr[7]));
		registro.setIdestatus(parseLong(arr[8]));
		registro.setIdtransacbaja(parseLong(arr[9]));
		registro.setIdlineatipo(parseLong(arr[10]));
		registro.setIdbatchcorrida(parseLong(arr[11]));
		registro.setIdbatchincidencia(parseLong(arr[12]));
		registro.setIdtipopago(parseLong(arr[13]));
		registro.setVersion(parseLong(arr[14]));
		registro.setOrigendyp(parseBoolean(arr[15]));
		registro.setNombrearchivo(parseString(arr[16]));
		
		return registro;
	}

	static Long parseLong(String s) {
		if (s == null || StaticValuesUtil.EMPTY_STRING.equals(s.trim())) {
			return null;
		}
		
		return Long.parseLong(s.trim());
	}

	static String parseString(String s) {
		if (s == null || StaticValuesUtil.EMPTY_STRING.equals(s.trim())) {
			return null;
		}
		return s.trim();
	}

	static Date parseDate(String s) throws ParseUtilException {
		DateFormat logDateFormat = new SimpleDateFormat(StaticValuesUtil.LOG_DATEFORMAT_S);
		if (s == null || StaticValuesUtil.EMPTY_STRING.equals(s.trim())) {
			return null;
		}
		try {
			return logDateFormat.parse(s.trim());
		} catch (ParseException e) {
			throw new ParseUtilException(e);
		}
	}

	static BigDecimal parseBigDecimal(String s) {
		if (s == null || StaticValuesUtil.EMPTY_STRING.equals(s.trim())) {
			return null;
		}
		return new BigDecimal(s.trim());
	}
	
	static Boolean parseBoolean(String s) {
		if (s == null || StaticValuesUtil.EMPTY_STRING.equals(s.trim())) {
			return null;
		}
		return Boolean.parseBoolean(s.trim());
	}
	
	public static List<String> fileToArrayList(File file) throws FileNotFoundException, IOException {
		ArrayList<String> arr = new ArrayList<String>();
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String sCurrentLine;
			while ((sCurrentLine = br.readLine()) != null) {
				arr.add(sCurrentLine.trim());
			}
		}
		return arr;
	}
}
