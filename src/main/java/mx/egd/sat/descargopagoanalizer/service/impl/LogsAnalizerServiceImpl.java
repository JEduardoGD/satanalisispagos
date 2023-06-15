package mx.egd.sat.descargopagoanalizer.service.impl;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import mx.egd.sat.descargopagoanalizer.daos.db.Registro;
import mx.egd.sat.descargopagoanalizer.exceptions.ParseUtilException;
import mx.egd.sat.descargopagoanalizer.service.LogsAnalizerService;
import mx.egd.sat.descargopagoanalizer.util.LogLoaderUtil;

@Slf4j
@Service
public class LogsAnalizerServiceImpl implements LogsAnalizerService {
	
	@Override
	public List<Registro> parseLog(String pathFile) {
		Path path = Paths.get(pathFile);
		List<String> array = new ArrayList<>();
		try {
			array = LogLoaderUtil.fileToArrayList(path.toFile());
		} catch (IOException e) {
			log.error(e.getMessage());
		}

		return array.stream()
		    .filter(s -> s != null && s.contains("|"))
		    .map(s -> {
				try {
					return LogLoaderUtil.parse(s);
				} catch (ParseUtilException e) {
					log.error("Error al parsear {}", e);
					log.error(e.getMessage());
				}
				return null;
			})
		    .collect(Collectors.toList());
		
	}
}
