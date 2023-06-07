package mx.egd.sat.descargopagoanalizer.service;

import java.util.List;

import mx.egd.sat.descargopagoanalizer.daos.db.Registro;

public interface LogsAnalizerService {
	public List<Registro> parseLog(String pathFile);
}
