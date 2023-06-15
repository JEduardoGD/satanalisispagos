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
import java.util.regex.Pattern;

import mx.egd.sat.descargopagoanalizer.daos.cifracontrol.Cifracontrol;
import mx.egd.sat.descargopagoanalizer.enumss.EnumTipoPago;
import mx.egd.sat.descargopagoanalizer.exceptions.ParseUtilException;

public interface ParseCifrasControlUitl {
	
	/**
	 * @param s
	 * @return 
	 * @throws ParseUtilException 
	 */
	public static Cifracontrol parse(String s) throws ParseUtilException {
		String[] arr = s.concat(StaticValuesUtil.ONE_SPACE).split(StaticValuesUtil.COMMA);
		
		Cifracontrol cc = new Cifracontrol();
		
		cc.setNumerodocumento(parseLong(arr[0]));
		cc.setFechapago(parseDate(arr[1]));
		cc.setRfc(parseString(arr[2]));
		cc.setImportepagado(parseBigDecimal(arr[3]));
		cc.setNumero(parseLong(arr[4]));
		cc.setLineacaptura(parseString(arr[5]));
		cc.setNumerob(parseLong(arr[6]));
		cc.setTipodocumento(parseLong(arr[7]));
		
		boolean isVirtual = isVirtual(cc);
		boolean isEfectivo = isEfectivo(cc);
		
		if(isVirtual) {
			cc.setTipoPago(EnumTipoPago.VIRTUAL);
		}
		if(isEfectivo) {
			cc.setTipoPago(EnumTipoPago.EFECTIVO);
		}
		if(!isVirtual && !isEfectivo) {
			cc.setTipoPago(EnumTipoPago.DIFERENTE);
		}
		
		return cc;
	}
	
	private static boolean isVirtual(Cifracontrol cc) {
		boolean sinLineaCaptura;
		if (cc.getLineacaptura() == null) {
			sinLineaCaptura = true;
		} else {
			sinLineaCaptura = Pattern.compile(StaticValuesUtil.ONLY_CERO_REGEX)
					.matcher(cc.getLineacaptura()).matches();
		}
		return sinLineaCaptura;
	}
	
	private static boolean isEfectivo(Cifracontrol cc) {
		return Pattern.compile(StaticValuesUtil.FOURTYFOUR_REGEX)
				.matcher(cc.getLineacaptura()).matches();
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
		DateFormat cifraControlDateFormat = new SimpleDateFormat(StaticValuesUtil.CIFRA_CONTROL_DATEFORMAT_S);
		if (s == null || StaticValuesUtil.EMPTY_STRING.equals(s.trim())) {
			return null;
		}
		try {
			return cifraControlDateFormat.parse(s.trim());
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
