package com.Reportes.reportgenerator;

/**
 * Created by Alvaro on 4/17/2015.
 */
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import org.apache.commons.digester.*;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import net.sf.jasperreports.engine.JRException;
import java.io.File;
import java.io.OutputStream;
import java.sql.Connection;
import java.util.HashMap;

/**
 * Esta clase carga los templates, compila y carga el reporte con datos y lo exporta
 * 
 */
public class ReportGenerator {
    private Log log= LogFactory.getLog(this.getClass());

    public ReportGenerator() {
    }

    public void executeReport(String templatePath, Connection conn, OutputStream outputStream, HashMap<String, Object> fillParameters) throws Exception {

    	try{
    		JasperDesign jasperDesign=loadTemplate(templatePath);
    		setTempDirectory(templatePath);
    		JasperReport jasperReport=compileReport(jasperDesign);
    		JasperPrint jasperPrint=fillReport(jasperReport, conn, fillParameters);
    		exportReportToPdf(jasperPrint, outputStream);
    	}catch(Exception e){
    		throw e;
    	}
    }
    

    /**
     * Carga el template (definido por la ruta pasada por parametro) y retorna un JasperDesign que representa al template
     * @return JasperDesign
     */
    private JasperDesign loadTemplate(String templatePath){
        JasperDesign jasperDesign=null;
        File templateFile=new File(templatePath);
        System.out.println("ABSOLUTE PATH: "+templateFile.getAbsolutePath());
        if(templateFile.exists()){
            try {
                jasperDesign= JRXmlLoader.load(templateFile);
            } catch (JRException e) {
                log.error("Error cargando el template... "+e.getMessage());
            }
        }
        else
            log.error("Error, el archivo no existe");
        return(jasperDesign);
    }

    /**
     * Comila el reporte y genera el binario del mismo
     * @param jasperDesign 
     * @return JasperReport
     */
    private JasperReport compileReport(JasperDesign jasperDesign){
        JasperReport jasperReport=null;
        try {
            jasperReport= JasperCompileManager.compileReport(jasperDesign);
        } catch (JRException e) {
            log.error("Error compilando el reporte.... "+e.getMessage());
        }
        return(jasperReport);
    }

    /**
     * Llena el reporte y genera el binario
     * @param jasperReport 
     * @return JasperPrint
     */
    private JasperPrint fillReport(JasperReport jasperReport, Connection conn, HashMap<String, Object> fillParameters){
        JasperPrint jasperPrint=null;
        
        try {
            jasperPrint =JasperFillManager.fillReport(
                    jasperReport,
                    fillParameters,
                    conn);
        } catch (JRException e) {
            log.error("Error llenando el reporte..... "+e.getMessage());
        }
        return(jasperPrint);
    }

    /**
     * Prepara un JRExporter para el reporte generado
     * @param jasperPrint 
     * @return The HTML text
     */
    private void exportReportToPdf(JasperPrint jasperPrint, OutputStream outputStream) throws JRException {
        JRPdfExporter exporter = new JRPdfExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));
        exporter.exportReport();
    }
    /**
     * Setea la carpeta temporal para la generacion del reporte
     */
    private void setTempDirectory(String templatePath){
        File templateFile=new File(templatePath);
        if(templateFile.exists()){
            log.info("Seteando el directorio: "+templateFile.getParent());
            System.setProperty("jasper.reports.compile.temp", templateFile.getParent());
        }
    }
}
