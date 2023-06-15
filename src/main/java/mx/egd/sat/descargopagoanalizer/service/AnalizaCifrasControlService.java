package mx.egd.sat.descargopagoanalizer.service;

import java.util.List;

import mx.egd.sat.descargopagoanalizer.daos.cifracontrol.Cifracontrol;
import mx.egd.sat.descargopagoanalizer.daos.db.Registro;

public interface AnalizaCifrasControlService {

	List<Cifracontrol> creaListaCifrasControl(String pathFile);

	List<Registro> contrasta(List<Cifracontrol> cifracontrolList, List<Registro> registrosLog);

}
