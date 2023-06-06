package mx.egd.sat.descargopagoanalizer.service;

import java.util.List;

import mx.egd.sat.descargopagoanalizer.daos.CreditosFiscales;
import mx.egd.sat.descargopagoanalizer.daos.ResultadosAnalisis;

public interface CifrasService {

	ResultadosAnalisis gettingCifras(List<CreditosFiscales> beansList);


}
