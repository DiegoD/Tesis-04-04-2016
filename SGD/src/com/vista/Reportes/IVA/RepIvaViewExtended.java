package com.vista.Reportes.IVA;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import com.Reportes.util.ReportsUtil;
import com.controladores.reportes.ReportesControlador;
import com.excepciones.ConexionException;
import com.excepciones.InicializandoException;
import com.excepciones.NoTienePermisosException;
import com.excepciones.ObteniendoPermisosException;
import com.excepciones.Monedas.ObteniendoMonedaException;
import com.excepciones.clientes.ObteniendoClientesException;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.StreamResource;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.UI;
import com.valueObject.MonedaVO;
import com.valueObject.TitularVO;
import com.valueObject.UsuarioPermisosVO;
import com.valueObject.Cheque.ChequeVO;
import com.valueObject.Cotizacion.CotizacionVO;
import com.valueObject.Gasto.GtoSaldoAux;
import com.valueObject.IngresoCobro.IngresoCobroDetalleVO;
import com.valueObject.IngresoCobro.IngresoCobroVO;
import com.valueObject.banco.BancoVO;
import com.valueObject.banco.CtaBcoVO;
import com.valueObject.cliente.ClienteVO;
import com.vista.BusquedaViewExtended;
import com.vista.IBusqueda;
import com.vista.IMensaje;
import com.vista.MensajeExtended;
import com.vista.Mensajes;
import com.vista.MySub;
import com.vista.PermisosUsuario;
import com.vista.Variables;
import com.vista.VariablesPermisos;
import com.vista.Validaciones.Validaciones;


public class RepIvaViewExtended extends RepIvaViews implements IBusqueda, IMensaje{

	MySub sub = new MySub("60%","75%");
	
	private BeanFieldGroup<IngresoCobroVO> fieldGroup;
	
	private ReportesControlador controlador;
	private String operacion;
	
	private IngresoCobroDetalleVO formSelecccionado; /*Variable utilizada cuando se selecciona
	 										  un detalle, para poder quitarlo de la lista*/
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
	public RepIvaViewExtended(){
	
	this.controlador = new ReportesControlador();
		
	/*Inicializamos los permisos para el usuario*/
	this.permisos = (PermisosUsuario)VaadinService.getCurrentRequest().getWrappedSession().getAttribute("permisos");
	
	this.inicializarForm();
	
	
	

	

	/*Inicializamos listener de boton aceptar*/
	this.aceptar.addClickListener(click -> {
			
		//try {
			
			/*Seteamos validaciones en nuevo, cuando es editar
			 * solamente cuando apreta el boton editar*/
			//this.setearValidaciones(true);
			
			
			/*Inicializamos VO de permisos para el usuario, formulario y operacion
			 * para confirmar los permisos del usuario*/
		
		
			UsuarioPermisosVO permisoAux = 
					new UsuarioPermisosVO(this.permisos.getCodEmp(),
							this.permisos.getUsuario(),
							VariablesPermisos.FORMULARIO_INGRESO_COBRO,
							VariablesPermisos.OPERACION_NUEVO_EDITAR);				
				
			//ACA LLAMAMOS AL REPORTE
			
			try {
				
				//inicializarBoton();
				
				com.Reportes.util.ReportsUtil report;
				
				String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath() + "/WEB-INF/Reportes";
				
				System.out.println(basepath);
				
			    report = new ReportsUtil();
				 
				  
				  try {
					  
					  HashMap<String, Object> fillParameters=new HashMap<String, Object>();
					  
					  if(this.mes.getValue()!= "" && this.mes.getValue()!= null 
							  && this.anio.getValue()!= "" && this.anio.getValue()!= null 
							  
							  )
					  {
					  try{
					  
							 
							fillParameters.put("anio", this.anio.getValue().trim());
							 fillParameters.put("mes", this.mes.getValue().trim());
					        
					        fillParameters.put("codEmp",this.permisos.getCodEmp());
					       
					        fillParameters.put("periodo", this.mes.getValue().trim() + "-" +  this.anio.getValue().trim());
					         
			             
					        fillParameters.put("REPORTS_DIR",basepath);
					      
						  }catch(Exception e) {}
					        
					        
						StreamResource myResource = report.prepareForPdfReportReturn( basepath+"/RepIVA.jrxml",
									                "ReporteIVA",
									                fillParameters);
					  
						  
						//FileDownloader fileDownloader = new FileDownloader(myResource);
						//fileDownloader.extend(aceptar); /*VER DOWNLOAD!!!*/
						
				        Embedded e = new Embedded();
				        e.setSizeFull();
				        e.setType(Embedded.TYPE_BROWSER);

				        // Here we create a new StreamResource which downloads our StreamSource,
				        // which is our pdf.
				       
				        // StreamResource resource = new StreamResource(new Pdf(), "test.pdf?" + System.currentTimeMillis(), this);
				        StreamResource resource = myResource;
				        
				        // Set the right mime type
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
					  int o = 0;
				  }
				
				
			} catch (Exception e) {
				
				Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
			}
			
		});
		
	}
	
	

	public  void inicializarForm(){
		
	}

	

	/**
	 * Seteamos las validaciones del Formulario
	 * pasamos un booleano para activarlos y desactivarlos
	 * EN modo LEER: las deshabilitamos (para que no aparezcan los asteriscos, etc)
	 * EN modo NUEVO: las habilitamos
	 * EN modo EDITAR: las habilitamos
	 *
	 */
	private void setearValidaciones(boolean setear){
		
	}
	
	/**
	 * Seteamos el formulario en modo solo Lectura
	 *
	 */
	private void iniFormLectura()
	{
		/*Verificamos que tenga permisos para editar*/
		boolean permisoNuevoEditar = this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_INGRESO_COBRO, VariablesPermisos.OPERACION_NUEVO_EDITAR);
		boolean permisoEliminar = this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_INGRESO_EGRESO, VariablesPermisos.OPERACION_BORRAR);
		
		
		/*No mostramos las validaciones*/
		this.setearValidaciones(false);
		
		/*Dejamos todods los campos readonly*/
		this.readOnlyFields(true);
						
	}
	

	
	/**
	 * Seteamos el formulario en modo Nuevo
	 *
	 */
	private void iniFormNuevo()
	{
		/*Si es nuevo ocultamos el nroDocum (ya que aun no tenemos el numero)*/
		/*Chequeamos si tiene permiso de editar*/
		boolean permisoNuevoEditar = this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_INGRESO_COBRO, VariablesPermisos.OPERACION_NUEVO_EDITAR);
		
		/*Como es en operacion nuevo, dejamos todos los campos editabls*/
		this.readOnlyFields(false);
	}



	
	/**
	 * Dejamos todos los Fields readonly o no,
	 * dado el boolenao pasado por parametro
	 *
	 */
	private void readOnlyFields(boolean setear)
	{
		
		this.mes.setReadOnly(setear);
		
		this.anio.setReadOnly(setear);
		
	}
	
	/////////////////////////////////////////////////
	
	
	@Override
	public void setInfo(Object datos) {
		
		
		
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



	@Override
	public void anularFact() {
		// TODO Auto-generated method stub
		
	}
}
