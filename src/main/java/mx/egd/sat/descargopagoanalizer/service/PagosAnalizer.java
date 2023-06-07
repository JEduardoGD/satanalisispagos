package mx.egd.sat.descargopagoanalizer.service;

import java.util.List;

import mx.egd.sat.descargopagoanalizer.daos.xml.CreditosFiscales;

public interface PagosAnalizer {
	
	List<CreditosFiscales> analize(String sPath);

}
