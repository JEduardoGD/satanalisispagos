package mx.egd.sat.descargopagoanalizer.service;

import java.util.List;

import mx.egd.sat.descargopagoanalizer.daos.xml.CreditosFiscales;
import mx.egd.sat.descargopagoanalizer.daos.xml.ResultadosAnalisis;

public interface CifrasService {

	ResultadosAnalisis gettingCifras(List<CreditosFiscales> beansList);


}
