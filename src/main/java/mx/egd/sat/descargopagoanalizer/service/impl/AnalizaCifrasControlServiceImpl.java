package mx.egd.sat.descargopagoanalizer.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import mx.egd.sat.descargopagoanalizer.daos.cifracontrol.Cifracontrol;
import mx.egd.sat.descargopagoanalizer.daos.db.Registro;
import mx.egd.sat.descargopagoanalizer.exceptions.ParseUtilException;
import mx.egd.sat.descargopagoanalizer.service.AnalizaCifrasControlService;
import mx.egd.sat.descargopagoanalizer.util.LogLoaderUtil;
import mx.egd.sat.descargopagoanalizer.util.ParseCifrasControlUitl;
import mx.egd.sat.descargopagoanalizer.util.StaticValuesUtil;
import mx.egd.sat.descargopagoanalizer.util.TimmerUtil;

@Service
@Slf4j
public class AnalizaCifrasControlServiceImpl implements AnalizaCifrasControlService {
	
	@Override
	public Boolean isFolder(String s) {
		if (s == null || StaticValuesUtil.EMPTY_STRING.equals(s)) {
			return null;
		}
		Path p = Paths.get(s);
		File f = p.toFile();
		return Boolean.valueOf(f.isDirectory());
	}
	
	@Override
	public Boolean isFile(String s) {
		if (s == null || StaticValuesUtil.EMPTY_STRING.equals(s)) {
			return null;
		}
		Path p = Paths.get(s);
		File f = p.toFile();
		return Boolean.valueOf(f.isFile());
	}

	@Override
	public List<Registro> contrasta(List<Cifracontrol> cifracontrolList, List<Registro> registrosLog) {
		List<Registro> finalLista = new ArrayList<>();
		
		LocalDateTime nowTime = LocalDateTime.now();
		long count = 1;
		long total = cifracontrolList.size();
		
		for (Cifracontrol cf : cifracontrolList) {
			nowTime = TimmerUtil.timmer(StaticValuesUtil.STR_CONTRASTA_LISTA_TOPIC, nowTime, count++, total);
			
			LinkedHashSet<Registro> busca = new LinkedHashSet<>();
			List<Registro> buscaPorNumDocto = new ArrayList<>();
			List<Registro> buscaPorLinea = new ArrayList<>();

			Registro r = new Registro();
			r.setEncontrados(0);
			r.setTipoPago(cf.getTipoPago());
			r.setFileName(cf.getFilename());

			if (cf.getNumerodocumento() != null && !StaticValuesUtil.EMPTY_STRING.equals(cf.getNumerodocumento().toString())) {
				r.setNumdocto(cf.getNumerodocumento().toString());
				// busqueda por numero de documento
				buscaPorNumDocto = registrosLog.stream().filter(log -> log.getNumdocto() != null)
						.filter(log -> log.getNumdocto().equals(cf.getNumerodocumento().toString()))
						.collect(Collectors.toList());
				buscaPorNumDocto = buscaPorNumDocto.stream().map(u -> {
					u.setTipoPago(cf.getTipoPago());
					u.setFileName(cf.getFilename());
					return u;
				}).collect(Collectors.toList());
			}
			if (cf.getLineacaptura() != null && !StaticValuesUtil.EMPTY_STRING.equals(cf.getLineacaptura())) {
				r.setNumlinea(cf.getLineacaptura());
				// busqueda por numero de linea de captura
				buscaPorLinea = registrosLog.stream().filter(log -> log.getNumlinea() != null)
						.filter(log -> log.getNumlinea().equals(cf.getLineacaptura())).collect(Collectors.toList());
				buscaPorLinea = buscaPorLinea.stream().map(u -> {
					u.setTipoPago(cf.getTipoPago());
					u.setFileName(cf.getFilename());
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
	public List<Cifracontrol> creaListaCifrasControlFile(String pathFile) {

		Path path = Paths.get(pathFile);

		File file = path.toFile();
		if (!file.exists()) {
			log.error("No existe el archivo de cifras de control {}" + pathFile);
			return Arrays.asList();
		}

		List<String> array = new ArrayList<>();
		try {
			array = LogLoaderUtil.fileToArrayList(path.toFile());
		} catch (IOException e) {
			log.error(e.getMessage());
		}

		return array.stream().filter(s -> s != null && s.contains(",")).map(s -> {
			try {
				return ParseCifrasControlUitl.parse(s, file.getName());
			} catch (ParseUtilException e) {
				log.error("Error al parsear: {}", s);
				log.error(e.getMessage());
			}
			return null;
		})
				.collect(Collectors.toList());
	}

	@Override
	public List<Cifracontrol> creaListaCifrasControlFolder(String pathFile) {
		File f = Paths.get(pathFile).toFile();
		File[] fileArray = f.listFiles();
		List<File> fileList = Arrays.asList(fileArray).stream()
				.filter(file -> file.getName().toLowerCase().endsWith(StaticValuesUtil.TXT_EXTENSION_LC))
				.collect(Collectors.toList());
		List<Cifracontrol> cifracontrolListFinal = new ArrayList<>();

		for (File file : fileList) {
			try {
				List<String> listStringTemp = LogLoaderUtil.fileToArrayList(file);
				log.info("{} registros obtenidos del archivo {}", listStringTemp.size(), file.getName());

				List<Cifracontrol> cifracontrolList = listStringTemp.stream().filter(s -> s != null && s.contains(",")).map(s -> {
					try {
						return ParseCifrasControlUitl.parse(s, file.getName());
					} catch (ParseUtilException e) {
						log.error(e.getMessage());
					}
					return null;
				}).collect(Collectors.toList());
				cifracontrolListFinal.addAll(cifracontrolList);

			} catch (IOException e) {
				log.error(e.getMessage());
			}
		}

		return cifracontrolListFinal;
	}
}
