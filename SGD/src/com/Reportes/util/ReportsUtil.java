package com.Reportes.util;

import com.vaadin.server.FileDownloader;
import com.vaadin.server.StreamResource;
import com.vaadin.server.StreamResource.StreamSource;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Button;
import net.sf.jasperreports.engine.JRException;
import com.Reportes.reportgenerator.ReportGenerator;
import com.excepciones.ConexionException;
import com.logica.Pool;
import org.apache.commons.digester.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogConfigurationException;
import org.apache.commons.logging.LogFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Esta clase tiene los metodos para generar el reporte con JasperReports
 */
public class ReportsUtil {
  
    
    /**
     * Genera el reporte
     * @param reportTemplate nombre del template
     * @param reportOutputFilename nombre del pdf obtenido
     */
    public StreamResource prepareForPdfReportReturn(String reportTemplate,
                                 String reportOutputFilename,  HashMap<String, Object> fillParameters){
        System.out.println("Generando reporte...");
        Connection conn = null;
		try {
			
			conn = Pool.getInstance().obtenerConeccion();
			
		} catch (InstantiationException | ConexionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        reportOutputFilename+=("_"+getDateAsString()+".pdf");
        
        StreamResource myResource =
                createPdfResource(conn, reportTemplate,reportOutputFilename, fillParameters);
        
        return myResource;

    }

    /**
     * Genera el pdf, y lo retorna como un StreamResource
     * @param conn Coneccion a la base de datos
     * @param templatePath el path al template del reporte
     * @param reportFileName nombre del pdf obtenido
     * @return StreamResource StreamResource con el pdf generado
     */
    private StreamResource createPdfResource(final Connection conn, final String templatePath, String reportFileName, HashMap<String, Object> fillParameters) {
        return new StreamResource(new StreamResource.StreamSource() {
            
		private static final long serialVersionUID = 1L;


			@Override
            public InputStream getStream () {
                ByteArrayOutputStream pdfBuffer = new ByteArrayOutputStream();
                ReportGenerator reportGenerator=new ReportGenerator();

                try {
                    /*Generamos el reporte*/
                    try {
						reportGenerator.executeReport(templatePath, conn, pdfBuffer, fillParameters);
					} catch (Exception e) {
						
						e.printStackTrace();
					} 
                } catch (Exception e) {
                    e.printStackTrace();
                }
                /*Retornamos el stram desde el buffer*/
                return new ByteArrayInputStream(
                        pdfBuffer.toByteArray());
            }
        }, reportFileName);
    	
    	
    
    }
    
    
    /**
     * Convierte fecha a string
     * @return String with date
     */
    private String getDateAsString(){
        return(String.valueOf(Calendar.getInstance().get(Calendar.YEAR))+
                String.valueOf(Calendar.getInstance().get(Calendar.MONTH)+1)+
                String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH))+
                String.valueOf(Calendar.getInstance().get(Calendar.HOUR_OF_DAY))+
                String.valueOf(Calendar.getInstance().get(Calendar.MINUTE))+
                String.valueOf(Calendar.getInstance().get(Calendar.SECOND)));
    }
}
