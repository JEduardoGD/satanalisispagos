package mx.egd.sat.descargopagoanalizer.util;

public abstract class StaticValuesUtil {
	public static final String DATEFORMAT_CF = "dd/MM/yyyy";	
	public static final int PAGO_EN_EFECTIVO = 0;
	public static final int PAGO_VIRTUAL = 1;
	
	public static final String ONLY_CERO_REGEX = "^\\x30+$";
	public static final String FOURTYFOUR_REGEX = "^\\w{10}(44)\\w+$";

	public static final String NEW_LINE = System.lineSeparator();
	public static final String ANALISIS_TXT = "analisis.txt";
	public static final String ANALISIS_XLSX = ".analisis.xlsx";
	public static final String HOJA_PAGOS = "PAGO";
	public static final String HOJA_RESUMEN = "RESUMEN";
	public static final String EMPTY_STRING = "";
	public static final String XML_EXTENSION_LC = "xml";
	public static final String TXT_EXTENSION_LC = "txt";
	public static final String FOLDER = ".folder";
	public static final String STR_ANALISIS = ".analisis";
	public static final String STR_CONTRASTA_LISTA_TOPIC = "Contrasta lista";
  
	public static final String ONE_SPACE = " ";
	public static final String REGEX_ONE_PIPE = "\\|{1}";
	public static final String LOG_DATEFORMAT_S = "dd/MM/yyyy";

	public static final String COMMA = "\\,{1}";
	public static final String CIFRA_CONTROL_DATEFORMAT_S = "yyyyMMdd";
}
  