package mx.egd.sat.descargopagoanalizer.service;

import java.util.List;

import mx.egd.sat.descargopagoanalizer.daos.xml.CreditosFiscales;

public interface ConversionExcelService {

	void convert(List<CreditosFiscales> beansList);

}
