package com.vista.Reportes.ChequesPendDepositar;

import java.sql.Date;
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
import com.valueObject.IngresoCobro.IngresoCobroDetalleVO;
import com.valueObject.IngresoCobro.IngresoCobroVO;
import com.valueObject.cliente.ClienteVO;
import com.vista.BusquedaViewExtended;
import com.vista.IBusqueda;
import com.vista.IMensaje;
import com.vista.Mensajes;
import com.vista.MySub;
import com.vista.PermisosUsuario;
import com.vista.Variables;
import com.vista.VariablesPermisos;
import com.vista.Validaciones.Validaciones;


public class ChequesDepositarViewExtended extends ChequesDepositarViews implements IBusqueda, IMensaje{

	MySub sub = new MySub("60%","75%");
	
	
	private ReportesControlador controlador;
	
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
	public ChequesDepositarViewExtended(){
	
	this.controlador = new ReportesControlador();
		
	/*Inicializamos los permisos para el usuario*/
	this.permisos = (PermisosUsuario)VaadinService.getCurrentRequest().getWrappedSession().getAttribute("permisos");
	
	this.inicializarForm();
	
	
	
	this.btnBuscarCliente.addClickListener(click -> {
		
		BusquedaViewExtended form = new BusquedaViewExtended(this, new ClienteVO());
		
		ArrayList<Object> lst = new ArrayList<Object>();
		ArrayList<TitularVO> lstTitulares = new ArrayList<TitularVO>();
		ArrayList<ClienteVO> lstClientes = new ArrayList<ClienteVO>();
		ArrayList<ClienteVO> lstClientes2 = new ArrayList<ClienteVO>();
		
		/*Inicializamos VO de permisos para el usuario, formulario y operacion
		 * para confirmar los permisos del usuario*/
		UsuarioPermisosVO permisoAux = 
				new UsuarioPermisosVO(this.permisos.getCodEmp(),
						this.permisos.getUsuario(),
						VariablesPermisos.FORMULARIO_REP_CHEQUE_CLIENTES,
						VariablesPermisos.OPERACION_LEER);
		
		try {
			
			/*Para dejar al cliente todos arriba*/
			lstClientes.add(this.obtenerClienteTodos());
			
			lstClientes2 = this.controlador.getClientes(permisoAux);
			
			for (ClienteVO clienteVO : lstClientes2) {
				lstClientes.add(clienteVO);
			}
			
			
			
		} catch (ConexionException| InicializandoException| ObteniendoPermisosException| NoTienePermisosException| ObteniendoClientesException e) 
		{
			Mensajes.mostrarMensajeError(e.getMessage());
		}
		
		
		Object obj;
		for (ClienteVO i: lstClientes) {
			obj = new Object();
			obj = (Object)i;
			lst.add(obj);
		}
		
		try {
			
			form.inicializarGrilla(lst);
		
			
		} catch (Exception e) {
			
			Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
		}
		

		sub = new MySub("500px", "870px" );
		sub.setModal(true);
		sub.center();
		sub.setModal(true);
		sub.setVista(form);
		sub.center();
		sub.setDraggable(true);
		UI.getCurrent().addWindow(sub);
		
	});
	

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
					  
					  if(this.codTitular.getValue()!= "" && this.codTitular.getValue()!= null 
							  && this.fecHasta.getValue()!= null
							  && this.comboMoneda.getValue() != null 
							  )
					  {
					  try{
					  
							 
							fillParameters.put("codTit", this.codTitular.getValue());
					        
					        fillParameters.put("codEmp",this.permisos.getCodEmp());
					       
					        fillParameters.put("nomTit", this.nomTitular.getValue().trim());
					        
					        
					        fillParameters.put("fecHasta", convertFromJAVADateToSQLDate(fecHasta.getValue()));
					        fillParameters.put("codMoneda", this.getCodMonedaSeleccionada());
					        
					        fillParameters.put("REPORTS_DIR",basepath);
					      
						  }catch(Exception e) {}
					        
					        
						StreamResource myResource = report.prepareForPdfReportReturn( basepath+"/RepChequesPendDepositar.jrxml",
									                "ChequesPendientes",
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
		
		this.inicializarComboMoneda(null);
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
		
		this.fecHasta.setRequired(setear);
		this.fecHasta.setRequiredError("Es requerido");
		
		this.comboMoneda.setRequired(setear);
		this.comboMoneda.setRequiredError("Es requerido");
		
		this.codTitular.setRequired(setear);
		this.codTitular.setRequiredError("Es requerido");
		
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
		
		this.btnBuscarCliente.setVisible(false);
		
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
		
		this.fecHasta.setReadOnly(setear);
		
		this.comboMoneda.setReadOnly(setear);
		
		this.codTitular.setReadOnly(false);
		this.codTitular.setEnabled(false);
		this.nomTitular.setEnabled(false);
		
	}
	
	/////////////////////////////////////////////////
	
	
public void inicializarComboMoneda(String cod){
		
		//this.comboMoneda = new ComboBox();
		BeanItemContainer<MonedaVO> monedasObj = new BeanItemContainer<MonedaVO>(MonedaVO.class);
		MonedaVO moneda = new MonedaVO();
		ArrayList<MonedaVO> lstMonedas = new ArrayList<MonedaVO>();
		UsuarioPermisosVO permisosAux;
		
		try {
			permisosAux = 
					new UsuarioPermisosVO(this.permisos.getCodEmp(),
							this.permisos.getUsuario(),
							VariablesPermisos.FORMULARIO_REP_CHEQUE_CLIENTES,
							VariablesPermisos.OPERACION_LEER);
			
			lstMonedas = this.controlador.getMonedas(permisosAux);
			
		} catch (ObteniendoMonedaException | InicializandoException | ConexionException | ObteniendoPermisosException | NoTienePermisosException e) {

			Mensajes.mostrarMensajeError(e.getMessage());
		}
		
		for (MonedaVO monedaVO : lstMonedas) {
			
			monedaVO.setCotizacion(1);
			monedasObj.addBean(monedaVO);
			
			if(cod != null){
				if(cod.equals(monedaVO.getCodMoneda())){
					moneda = monedaVO;
				}
			}
		}
		
		/*Agregamos moneda TODAS*/
		
		MonedaVO monedaTODAS = new MonedaVO();
		monedaTODAS.setAceptaCotizacion(false);
		monedaTODAS.setActivo(true);
		monedaTODAS.setCodMoneda("TODAS");
		monedaTODAS.setCotizacion(1);
		monedaTODAS.setDescripcion("TODAS");
		monedaTODAS.setFechaMod(lstMonedas.get(0).getFechaMod());
		monedaTODAS.setNacional(false);
		monedaTODAS.setOperacion("");
		monedaTODAS.setSimbolo("");
		monedaTODAS.setUsuarioMod("SISTE");
		
		monedasObj.addBean(monedaTODAS);
		
		
		
		this.comboMoneda.setContainerDataSource(monedasObj);
		this.comboMoneda.setItemCaptionPropertyId("descripcion");
		
		
		if(cod!=null)
		{
			try{
				this.comboMoneda.setReadOnly(false);
				this.comboMoneda.setValue(moneda);
				this.comboMoneda.setReadOnly(true);
			}catch(Exception e)
			{}
		}
		
	}
	
	
	@Override
	public void setInfo(Object datos) {
		
		if(datos instanceof ClienteVO){
			ClienteVO clienteVO = (ClienteVO) datos;
			
			this.codTitular.setReadOnly(false);
			this.nomTitular.setReadOnly(false);
			
			this.codTitular.setValue(String.valueOf(clienteVO.getCodigo()));
			this.nomTitular.setValue(clienteVO.getNombre());
			
			this.codTitular.setReadOnly(true);
			this.nomTitular.setReadOnly(true);
		}
		
		if(datos instanceof TitularVO){
			titularVO = (TitularVO) datos;
			this.codTitular.setValue(String.valueOf(titularVO.getCodigo()));
			this.nomTitular.setValue(titularVO.getNombre());
			//this.tipo.setValue(titularVO.getTipo());
		}
		
	}
	

	
	private String getCodMonedaSeleccionada(){
		
		String codMoneda = null;
		
		//Moneda
		if(this.comboMoneda.getValue() != null){
			MonedaVO auxMoneda = new MonedaVO();
			auxMoneda = (MonedaVO) this.comboMoneda.getValue();
			codMoneda = auxMoneda.getCodMoneda();
		}
		
		return codMoneda;
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
	
	
	public static java.sql.Date convertFromJAVADateToSQLDate(java.util.Date javaDate) {
        java.sql.Date sqlDate = null;
        if (javaDate != null) {
            sqlDate = new Date(javaDate.getTime());
        }
        return sqlDate;
    }
	
	public ClienteVO obtenerClienteTodos(){
		
		ClienteVO aux = new ClienteVO();
		
		aux.setCodigo(0);
		aux.setNombre("TODOS");
		
		return aux;
	}



	@Override
	public void anularFact() {
		// TODO Auto-generated method stub
		
	}
}
