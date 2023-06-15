package mx.egd.sat.descargopagoanalizer.service;

import java.util.List;

import mx.egd.sat.descargopagoanalizer.daos.db.Registro;

public interface GeneraInformeService {

	void creaInformeExcel(List<Registro> finalLista, String filename);

	void creaInformeTexto(List<Registro> finalLista, String filename);

}
