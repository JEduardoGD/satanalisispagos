package mx.egd.sat.descargopagoanalizer.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import mx.egd.sat.descargopagoanalizer.daos.CreditosFiscales;
import mx.egd.sat.descargopagoanalizer.service.ConversionExcelService;
import mx.egd.sat.descargopagoanalizer.util.HashUtil;

@Service
public class ConversionExcelServiceImpl implements ConversionExcelService {
	@Override
	public void convert(List<CreditosFiscales> beansList) {

		Workbook workbook = new XSSFWorkbook();
		
		Map<String, CreditosFiscales> mapCreditosFiscales = new HashMap<>();

		for (CreditosFiscales cf : beansList) {
			mapCreditosFiscales.put(HashUtil.getHashOf(cf.toString()), cf);
		}
	}
}
