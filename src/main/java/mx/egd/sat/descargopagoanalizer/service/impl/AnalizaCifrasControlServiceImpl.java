package mx.egd.sat.descargopagoanalizer.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import mx.egd.sat.descargopagoanalizer.daos.cifracontrol.Cifracontrol;
import mx.egd.sat.descargopagoanalizer.daos.db.Registro;
import mx.egd.sat.descargopagoanalizer.service.AnalizaCifrasControlService;
import mx.egd.sat.descargopagoanalizer.util.LogLoaderUtil;
import mx.egd.sat.descargopagoanalizer.util.ParseCifrasControlUitl;

@Service
@Slf4j
public class AnalizaCifrasControlServiceImpl implements AnalizaCifrasControlService {

	private static final String EMPTY_STRING = "";
	private static final String NEW_LINE = System.lineSeparator();
	private static final String ANALISIS_TXT = ".analisis.txt";
	private static final String ANALISIS_XLSX = ".analisis.xlsx";
	private static final String HOJA_PAGOS = "PAGO";
	
	static {
		
	}
	
	@Override
	public void creaInformeExcel(List<Registro> finalLista, String filename) {
		OutputStream os = null;
		XSSFWorkbook workbook = null;
		try {
			workbook = new XSSFWorkbook();
			XSSFSheet sheetx = workbook.createSheet(HOJA_PAGOS);

			for (int rowCount = 0; rowCount < finalLista.size(); rowCount++) {
				Registro registro = finalLista.get(rowCount);
				XSSFRow rowx;

				String tipoPago = "DESCONOCIDO";
				if (registro.getTipoPago() != null) {
					switch (registro.getTipoPago()) {
					case VIRTUAL:
						tipoPago = "VIRTUAL";
						break;
					case EFECTIVO:
						tipoPago = "EFECTIVO";
						break;
					case DIFERENTE:
						tipoPago = "DIFERENTE";
						break;
					default:
						tipoPago = "DESCONOCIDO";
					}
				}

				XSSFCell cellx;

				if (rowCount == 0) {
					{
						rowx = sheetx.createRow(0);

						cellx = rowx.createCell(0);
						cellx.setCellValue("IDPAGO");

						cellx = rowx.createCell(1);
						cellx.setCellValue("TIPOPAGO");

						cellx = rowx.createCell(2);
						cellx.setCellValue("NUMDOCTO");

						cellx = rowx.createCell(3);
						cellx.setCellValue("NUMLINEA");

						cellx = rowx.createCell(4);
						cellx.setCellValue("IDESTATUS");

						cellx = rowx.createCell(5);
						cellx.setCellValue("ENCONTRADOS");
					}
				}

				rowx = sheetx.createRow(rowCount + 1);

				if (registro.getIdpago() != null) {
					cellx = rowx.createCell(0);
					cellx.setCellType(CellType.NUMERIC);
					cellx.setCellValue(registro.getIdpago());
				}

				cellx = rowx.createCell(1);
				cellx.setCellType(CellType.STRING);
				cellx.setCellValue(tipoPago);

				if (registro.getNumdocto() != null) {
					cellx = rowx.createCell(2);
					cellx.setCellType(CellType.NUMERIC);
					cellx.setCellValue(registro.getNumdocto());
				}

				if (registro.getNumlinea() != null) {
					cellx = rowx.createCell(3);
					cellx.setCellType(CellType.STRING);
					cellx.setCellValue(registro.getNumlinea());
				}

				if (registro.getIdestatus() != null) {
					cellx = rowx.createCell(4);
					cellx.setCellType(CellType.NUMERIC);
					cellx.setCellValue(registro.getIdestatus());
				}

				if (registro.getEncontrados() != 1) {
					cellx = rowx.createCell(5);
					cellx.setCellType(CellType.NUMERIC);
					cellx.setCellValue(registro.getEncontrados());
				}
			}
			
			sheetx.setAutoFilter(new CellRangeAddress(0, 0, 0, 5));
			sheetx.createFreezePane(0, 1);

			os = new FileOutputStream(filename + ANALISIS_XLSX);
			workbook.write(os);
		} catch (IOException e) {
			log.error(e.getMessage());
		} finally {
			if (workbook != null) {
				try {
					workbook.close();
				} catch (IOException e) {
					log.warn(e.getMessage());
				}
			}

			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					log.warn(e.getMessage());
				}
			}
		}
	}
	
	@Override
	public void creaInforme(List<Registro> finalLista, String filename) {
		Path path = Paths.get(filename + ANALISIS_TXT);
		
		File f = path.toFile();

		if (f.exists()) {
			if (f.canWrite()) {
				f.delete();
			} else {
				log.error("El archivo existe pero no puede borrarse: {}" + path.toString());
				return;
			}
		}
		
		
		
		List<String> salida = finalLista.stream().map(l -> {
			String tipoPago = "DESCONOCIDO";
			if (l.getTipoPago() != null) {
				switch (l.getTipoPago()) {
				case VIRTUAL:
					tipoPago = "VIRTUAL";
					break;
				case EFECTIVO:
					tipoPago = "EFECTIVO";
					break;
				case DIFERENTE:
					tipoPago = "DIFERENTE";
					break;
				default:
					tipoPago = "DESCONOCIDO";
				}
			}
			return String.format("%s|%s|%s|%s|%s|%s", l.getIdpago() != null ? l.getIdpago() : EMPTY_STRING, tipoPago,
					l.getNumdocto() != null ? l.getNumdocto() : EMPTY_STRING,
					l.getNumlinea() != null ? l.getNumlinea() : EMPTY_STRING,
					l.getIdestatus() != null ? l.getIdestatus() : EMPTY_STRING,
					l.getEncontrados() != 1 ? l.getEncontrados() : EMPTY_STRING);
		}).collect(Collectors.toList());

		try {
			Files.writeString(path, "IDPAGO|TIPOPAGO|NUMDOCTO|NUMLINEA|IDESTATUS|ENCONTRADOS" + NEW_LINE,
					StandardOpenOption.CREATE, StandardOpenOption.APPEND);
		} catch (IOException e1) {
			log.error(e1.getMessage());
		}

		salida.forEach(s -> {
			try {
				Files.writeString(path, s + NEW_LINE, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
			} catch (IOException e) {
				log.error(e.getMessage());
			}
		});
	}

	@Override
	public List<Registro> contrasta(List<Cifracontrol> cifracontrolList, List<Registro> registrosLog) {
		List<Registro> finalLista = new ArrayList<>();
		for (Cifracontrol cf : cifracontrolList) {
			LinkedHashSet<Registro> busca = new LinkedHashSet<>();
			List<Registro> buscaPorNumDocto = new ArrayList<>();
			List<Registro> buscaPorLinea = new ArrayList<>();
			
			Registro r = new Registro();
			r.setEncontrados(0);
			r.setTipoPago(cf.getTipoPago());
			
			if (cf.getNumerodocumento() != null && !EMPTY_STRING.equals(cf.getNumerodocumento().toString())) {
				r.setNumdocto(cf.getNumerodocumento().toString());
				// busqueda por numero de documento
				buscaPorNumDocto = registrosLog.stream().filter(log -> log.getNumdocto() != null)
						.filter(log -> log.getNumdocto().equals(cf.getNumerodocumento().toString()))
						.collect(Collectors.toList());
				buscaPorNumDocto = buscaPorNumDocto.stream().map(u -> {
					u.setTipoPago(cf.getTipoPago());
					return u;
				}).collect(Collectors.toList());
			}
			if (cf.getLineacaptura() != null && !EMPTY_STRING.equals(cf.getLineacaptura())) {
				r.setNumlinea(cf.getLineacaptura());
				// busqueda por numero de documento
				buscaPorLinea = registrosLog.stream()
						.filter(log -> log.getNumlinea() != null)
						.filter(log -> log.getNumlinea()
								.equals(cf.getLineacaptura()))
						.collect(Collectors.toList());
				buscaPorLinea = buscaPorLinea.stream().map(u -> {
					u.setTipoPago(cf.getTipoPago());
					return u;
				}).collect(Collectors.toList());
			}
			
			busca.addAll(buscaPorNumDocto);
			busca.addAll(buscaPorLinea);
			
			int size = busca.size();
			if (size > 0) {
				finalLista.addAll(busca.stream().map(b -> {
					b.setEncontrados(size);
					return b;
				}).collect(Collectors.toList()));
			} else {
				finalLista.add(r);
			}
		}
		
		return finalLista;
	}

	@Override
	public List<Cifracontrol> creaListaCifrasControl(String pathFile) {

		Path path = Paths.get(pathFile);
		
		File file = path.toFile();
		if(!file.exists()) {
			log.error("No existe el archivo de cifras de control {}" + pathFile);
			return null;
		}
		
		List<String> array = new ArrayList<>();
		try {
			array = LogLoaderUtil.fileToArrayList(path.toFile());
		} catch (IOException e) {
			log.error(e.getMessage());
		}

		return array.stream().filter(s -> s != null && s.contains(",")).map(s -> ParseCifrasControlUitl.parse(s))
				.collect(Collectors.toList());
	}
}
