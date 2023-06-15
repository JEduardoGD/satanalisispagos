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

import lombok.extern.slf4j.Slf4j;
import mx.egd.sat.descargopagoanalizer.daos.cifracontrol.Cifracontrol;
import mx.egd.sat.descargopagoanalizer.enumss.EnumTipoPago;

@Slf4j
public abstract class ParseCifrasControlUitl {

	private static final String EMPTY_STRING = "";
	private static final String ONE_SPACE = " ";
	private static final String COMMA = "\\,{1}";
	private static final DateFormat DF = new SimpleDateFormat("yyyyMMdd");
	
	/**
	 * @param s
	 * @return 
	 */
	public static Cifracontrol parse(String s) {
		String[] arr = s.concat(ONE_SPACE).split(COMMA);
		
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
		if (s == null || EMPTY_STRING.equals(s.trim())) {
			return null;
		}
		
		return Long.parseLong(s.trim());
	}

	static String parseString(String s) {
		if (s == null || EMPTY_STRING.equals(s.trim())) {
			return null;
		}
		return s.trim();
	}

	static Date parseDate(String s) {
		if (s == null || EMPTY_STRING.equals(s.trim())) {
			return null;
		}
		try {
			return DF.parse(s.trim());
		} catch (ParseException e) {
			log.debug(e.getMessage());
		}
		return null;
	}

	static BigDecimal parseBigDecimal(String s) {
		if (s == null || EMPTY_STRING.equals(s.trim())) {
			return null;
		}
		return new BigDecimal(s.trim());
	}
	
	static Boolean parseBoolean(String s) {
		if (s == null || EMPTY_STRING.equals(s.trim())) {
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
