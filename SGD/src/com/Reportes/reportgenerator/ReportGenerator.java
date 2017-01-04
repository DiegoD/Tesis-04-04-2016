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
 * This class makes the work, loads the template, compile, fill and export the report.
 */
public class ReportGenerator {
    private Log log= LogFactory.getLog(this.getClass());

    public ReportGenerator() {
    }

    public void executeReport(String templatePath, Connection conn, OutputStream outputStream) throws Exception {

    	try{
    		JasperDesign jasperDesign=loadTemplate(templatePath);
    		setTempDirectory(templatePath);
    		JasperReport jasperReport=compileReport(jasperDesign);
    		JasperPrint jasperPrint=fillReport(jasperReport, conn);
    		exportReportToPdf(jasperPrint, outputStream);
    	}catch(Exception e){
    		throw e;
    	}
    }
    
    public void executeReport2(String templatePath, Connection conn, OutputStream outputStream) throws Exception//JRException 
    {

        JasperDesign jasperDesign=loadTemplate(templatePath);
        setTempDirectory(templatePath);
        JasperReport jasperReport=compileReport(jasperDesign);
        
        HashMap hm = new HashMap();
        hm.put("REPORT_TITLE","This is the title of the report");
        
        //JasperPrint jasperPrint=fillReport(jasperReport, conn);
        JasperPrint jasperPrint=fillReport(jasperReport, conn);
        exportReportToPdf(jasperPrint, outputStream);
        
        
    }


    /**
     * Load the template (defined by templatePath) and return a JasperDesign object representing the template
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
                log.error("Error in loading the template... "+e.getMessage());
            }
        }
        else
            log.error("Error, the file dont exists");
        return(jasperDesign);
    }

    /**
     * Compile the report and generates a binary version of it
     * @param jasperDesign The report design
     * @return JasperReport
     */
    private JasperReport compileReport(JasperDesign jasperDesign){
        JasperReport jasperReport=null;
        try {
            jasperReport= JasperCompileManager.compileReport(jasperDesign);
        } catch (JRException e) {
            log.error("Error in compiling the report.... "+e.getMessage());
        }
        return(jasperReport);
    }

    /**
     * Fill the report and generates a binary version of it
     * @param jasperReport The Compiled report design
     * @return JasperPrint
     */
    private JasperPrint fillReport(JasperReport jasperReport, Connection conn){
        JasperPrint jasperPrint=null;
        HashMap<String, Object> fillParameters=new HashMap<String, Object>();
        fillParameters.put("codTit", "14");
        
        try {
            jasperPrint =JasperFillManager.fillReport(
                    jasperReport,
                    fillParameters,
                    conn);
        } catch (JRException e) {
            log.error("Error in filling the report..... "+e.getMessage());
        }
        return(jasperPrint);
    }

    /**
     * Prepare a JRExporter for the filled report (to HTML)
     * @param jasperPrint The jasperPrint
     * @return The HTML text
     */
    private void exportReportToPdf(JasperPrint jasperPrint, OutputStream outputStream) throws JRException {
        JRPdfExporter exporter = new JRPdfExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));
        exporter.exportReport();
    }
    /**
     * Set the temp directory for report generation
     */
    private void setTempDirectory(String templatePath){
        File templateFile=new File(templatePath);
        if(templateFile.exists()){
            log.info("Setting parentDirectory: "+templateFile.getParent());
            System.setProperty("jasper.reports.compile.temp", templateFile.getParent());
        }
    }
}
