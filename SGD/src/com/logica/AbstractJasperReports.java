package com.logica;

import java.sql.Connection;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.view.JasperViewer;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JRException;

public abstract class AbstractJasperReports {

	
	private static JasperReport	report;
	private static JasperPrint	reportFilled;
	private static JasperViewer viewer;
	
	public static void createReport(Connection conn, String path){
		
		try {
			
			report = (JasperReport)JRLoader.loadObjectFromFile(path);
			reportFilled = JasperFillManager.fillReport(report, null, conn);
			
			
		} catch (JRException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void showViewer(){
		
		
	}
	
	public static void exportToPDF(String destination){
		
		
	}
}
