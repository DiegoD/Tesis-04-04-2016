package com.vista.Reportes.RepCuentaRubro;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import com.Reportes.util.ReportsUtil;
import com.controladores.reportes.ReportesControlador;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.server.StreamResource;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.UI;
import com.valueObject.MonedaVO;
import com.valueObject.TitularVO;
import com.valueObject.UsuarioPermisosVO;
import com.valueObject.Cheque.ChequeVO;
import com.valueObject.Cotizacion.CotizacionVO;
import com.valueObject.IngresoCobro.IngresoCobroDetalleVO;
import com.valueObject.IngresoCobro.IngresoCobroVO;
import com.valueObject.cliente.ClienteVO;
import com.vista.IBusqueda;
import com.vista.IMensaje;
import com.vista.Mensajes;
import com.vista.MySub;
import com.vista.PermisosUsuario;
import com.vista.Variables;
import com.vista.Validaciones.Validaciones;


public class RepMovCtaRubroExtended extends RepMovCtaRubro implements IBusqueda, IMensaje{

	MySub sub = new MySub("60%","75%");
	
	UsuarioPermisosVO permisoAux;
	CotizacionVO cotizacion =  new CotizacionVO();
	Double cotizacionVenta = null;
	TitularVO titularVO = new TitularVO();
	MonedaVO monedaNacional = new MonedaVO();
	ArrayList<MonedaVO> lstMonedas = new ArrayList<MonedaVO>();
	Validaciones val = new Validaciones();
	ChequeVO chequeVO = new ChequeVO();
	
	
	private PermisosUsuario permisos; /*Variable con los permisos del usuario*/
	
	
	/**
	 * Constructor del formulario, conInfo indica
	 * si hay que cargarle la info
	 *
	 */
	@SuppressWarnings({ "unchecked", "deprecation" })
	public RepMovCtaRubroExtended(){
	
		
	/*Inicializamos los permisos para el usuario*/
	this.permisos = (PermisosUsuario)VaadinService.getCurrentRequest().getWrappedSession().getAttribute("permisos");
	
	this.inicializarForm();

	/*Inicializamos listener de boton aceptar*/
	this.aceptar.addClickListener(click -> {
			
			/*REPORTE*/
			
			try {
				
				com.Reportes.util.ReportsUtil report;
				
				String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath() + "/WEB-INF/Reportes";
				
				System.out.println(basepath);
				
			    report = new ReportsUtil();
				 
				  
				  try {
					  
					  HashMap<String, Object> fillParameters=new HashMap<String, Object>();
					  
					  if( this.fecDesde.getValue()!= null 
						&& this.fecHasta.getValue()!= null
						&& this.alMenosUnCheckChecked()
						&& this.tipoReporte.getValue() != null && this.tipoReporte.getValue() != "")
					  {
					  try{
					  
						  	String strConProceso = chkProceso.getValue() == true ? "S" : "N";
						  	String strConEmpleado = chkEmpleado.getValue() == true ? "S" : "N";
						  	String strConOficina = chkOficina.getValue() == true ? "S" : "N";
						  	String strTodos = chkSoloFacturables.getValue() == true ? "N" : "S";
						  			
						  	fillParameters.put("proceso",strConProceso);
						  	fillParameters.put("empleado",strConEmpleado);
						  	fillParameters.put("oficina",strConOficina);
						  	fillParameters.put("todos",strTodos);
						  
					        fillParameters.put("codEmp",this.permisos.getCodEmp());
					       
					        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			                String fecDesdeStr = format.format(fecDesde.getValue());
			                String fecHastaStr = format.format(fecHasta.getValue());
					        
					        fillParameters.put("periodo", "Período: " + fecDesdeStr + " al " + fecHastaStr);
					        
					        
					        fillParameters.put("fecDesde", convertFromJAVADateToSQLDate(fecDesde.getValue()));
					        fillParameters.put("fecHasta", convertFromJAVADateToSQLDate(fecHasta.getValue()));
					        
					        fillParameters.put("REPORTS_DIR",basepath);
					      
						  }catch(Exception e) {}
					     
					  String reporte = tipoReporte.getValue().toString().trim().equals("Detalle") ? "/RepGastosCuentaRubro.jrxml" : "/3-RepGastosCuentaRubroTotales.jrxml";
					  String nombreArchivo  = tipoReporte.getValue().toString().trim().equals("Detalle")? "GastosCuentaRubro" : "GastosCuentaRubroTotales";
					  
						StreamResource myResource = report.prepareForPdfReportReturn( basepath + reporte,
													nombreArchivo,
									                fillParameters);
					  
						
				        Embedded e = new Embedded();
				        e.setSizeFull();
				        e.setType(Embedded.TYPE_BROWSER);

				        StreamResource resource = myResource;
				        
				        resource.setMIMEType("application/pdf");
 
				        e.setSource(resource);
				        
				        sub = new MySub("500px","900px");
						sub.setModal(true);
						sub.setVista(e);
						
						UI.getCurrent().addWindow(sub);
						
				    
					 }
					  else{
						  
						  Mensajes.mostrarMensajeWarning("Faltan datos por completar");
					  }
					  
				  }
				  catch(Exception e)
				  {
					  Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
				  }
				
				
			} catch (Exception e) {
				
				Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
			}
			
		});
		
	}
	

	public  void inicializarForm(){
		
	}
	
	/////////////////////////////////////////////////
	
	@Override
	public void setInfo(Object datos) {
		
		if(datos instanceof ClienteVO){
			ClienteVO clienteVO = (ClienteVO) datos;
			
	
		}
		
		if(datos instanceof TitularVO){
			titularVO = (TitularVO) datos;
		
		}
		
	}

	@Override
	public void setInfoLst(ArrayList<Object> lstDatos) {
		//TODO
	}
	
	boolean tryParseInt(String value) {  
	     try {  
	         Integer.parseInt(value);  
	         return true;  
	      } catch (NumberFormatException e) {  
	         return false;  
	      }  
	}

	@Override
	public void eliminarFact() {
		// TODO Auto-generated method stub
		
	}

	public void cerrarVentana()
	{
		UI.getCurrent().removeWindow(sub);
	}
	
	
	
	public static java.sql.Date convertFromJAVADateToSQLDate(
            java.util.Date javaDate) {
        java.sql.Date sqlDate = null;
        if (javaDate != null) {
            sqlDate = new Date(javaDate.getTime());
        }
        return sqlDate;
    }
	
	/*Retorna true si al menos un chek esta checked*/
	private boolean alMenosUnCheckChecked(){
		
		boolean ok = true;
		
		if(chkProceso.getValue().booleanValue() == false &&
			chkEmpleado.getValue().booleanValue() == false &&
			chkOficina.getValue().booleanValue() == false){
			
			ok = false;
		}
		
		return ok;
	}
}
