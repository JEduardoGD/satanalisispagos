package mx.egd.sat.descargopagoanalizer.util;

public abstract class StaticValuesUtil {
	public static String DATEFORMAT_CF = "dd/MM/yyyy";	
	public static int PAGO_EN_EFECTIVO = 0;
	public static int PAGO_VIRTUAL = 1;
	
	public static String ONLY_CERO_REGEX = "^\\x30+$";
	public static String FOURTYFOUR_REGEX = "^\\w{10}(44)\\w+$";
}
