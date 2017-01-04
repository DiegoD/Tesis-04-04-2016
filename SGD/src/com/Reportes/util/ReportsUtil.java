/**
 * Licensed to the Apache Software Foundation (ASF) under one
 or more contributor license agreements.  See the NOTICE file
 distributed with this work for additional information
 regarding copyright ownership.  The ASF licenses this file
 to you under the Apache License, Version 2.0 (the
 "License"); you may not use this file except in compliance
 with the License.  You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing,
 software distributed under the License is distributed on an
 "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 KIND, either express or implied.  See the License for the
 specific language governing permissions and limitations
 under the License.
 */
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

/**
 * Created by Alvaro on 9/1/2015.
 * This class has util methods in order to generate report with JasperReports
 */
public class ReportsUtil {
	   //Base path for report template
    //private String baseReportsPath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath()+"/WEB-INF";
   	
  
    /**
     * Get database connection, call report generation method and export's report to Vaadin's FileDownloader
     * @param reportTemplate Report template file name
     * @param reportOutputFilename Pdf output file name
     * @param buttonToExtend Vaadin button to extend
     */
    public void prepareForPdfReport(String reportTemplate,
                                 String reportOutputFilename, Button buttonToExtend){
        System.out.println("Generating report...");
        Connection conn = null;
		try {
			
			conn = Pool.getInstance().obtenerConeccion();
			
		} catch (InstantiationException | ConexionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        reportOutputFilename+=("_"+getDateAsString()+".pdf");
        
        /**************/
       
        /*************/
        
        StreamResource myResource =
                createPdfResource(conn, /*ACAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA*/
                        reportTemplate,reportOutputFilename);
        FileDownloader fileDownloader = new FileDownloader(myResource);
        fileDownloader.extend(buttonToExtend);

    }

    /**
     * Generate pdf report, and return it as a StreamResource
     * @param conn Database connection
     * @param templatePath Report template path
     * @param reportFileName Pdf output file name
     * @return StreamResource with the generated pdf report
     */
    private StreamResource createPdfResource(final Connection conn, final String templatePath, String reportFileName) {
        return new StreamResource(new StreamResource.StreamSource() {
            
    	   
		
		private static final long serialVersionUID = 1L;

			//private static final long serialVersionUID = 1L;

			@Override
            public InputStream getStream () {
                ByteArrayOutputStream pdfBuffer = new ByteArrayOutputStream();
                ReportGenerator reportGenerator=new ReportGenerator();

                try {
                    //Generate the report
                    try {
						reportGenerator.executeReport(templatePath, conn, pdfBuffer);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} /*Acaaaaaaaaaaaaaaaaaaaa*/
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // Return a stream from the buffer.
                return new ByteArrayInputStream(
                        pdfBuffer.toByteArray());
            }
        }, reportFileName);
    	
    	
    
    }
    
    /**
     * Generate pdf report, and return it as a StreamResource
     * @param conn Database connection
     * @param templatePath Report template path
     * @param reportFileName Pdf output file name
     * @return StreamResource with the generated pdf report
     */
    private StreamResource createPdfResource2(final Connection conn, final String templatePath, String reportFileName) {
       return new StreamResource(new StreamResource.StreamSource() {
            
		
		private static final long serialVersionUID = 1L;

			//private static final long serialVersionUID = 1L;

			@Override
            public InputStream getStream () {
                ByteArrayOutputStream pdfBuffer = new ByteArrayOutputStream();
                ReportGenerator reportGenerator=new ReportGenerator();

                // Return a stream from the buffer.
                return new ByteArrayInputStream(
                        pdfBuffer.toByteArray());
            }
        }, reportFileName);
    	
    	
    
    }

    
   
    
    /**
     * Convert a date to String
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
