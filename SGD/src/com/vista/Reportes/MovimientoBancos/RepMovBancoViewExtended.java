package com.vista.Reportes.MovimientoBancos;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

import com.Reportes.util.ReportsUtil;
import com.controladores.IngresoCobroOtroControlador;
import com.controladores.reportes.ReportesControlador;
import com.excepciones.ConexionException;
import com.excepciones.InicializandoException;
import com.excepciones.NoTienePermisosException;
import com.excepciones.ObteniendoPermisosException;
import com.excepciones.Bancos.ObteniendoBancosException;
import com.excepciones.Bancos.ObteniendoCuentasBcoException;
import com.excepciones.Cotizaciones.ObteniendoCotizacionesException;
import com.excepciones.Monedas.ObteniendoMonedaException;
import com.excepciones.clientes.ObteniendoClientesException;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.FileDownloader;
import com.vaadin.server.StreamResource;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
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
import com.vista.Periodo.PeriodosPanelExtended;
import com.vista.Validaciones.Validaciones;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;


public class RepMovBancoViewExtended extends RepMovBancoViews implements IBusqueda, IMensaje{

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
	public RepMovBancoViewExtended(){
	
	this.controlador = new ReportesControlador();
		
	/*Inicializamos los permisos para el usuario*/
	this.permisos = (PermisosUsuario)VaadinService.getCurrentRequest().getWrappedSession().getAttribute("permisos");
	
	this.inicializarForm();
	
	/**
	* Agregamos listener al combo de tipo (banco, caja), determinamos si mostramos
	* los campos del banco o no;
	*
	*/
	comboBancos.addValueChangeListener(new Property.ValueChangeListener() {

   		@Override
		public void valueChange(ValueChangeEvent event) {
   			BancoVO bcoAux;
			
			if(comboBancos.getValue() != null){
				bcoAux = new BancoVO();
				bcoAux = (BancoVO) comboBancos.getValue();
				
				inicializarComboCuentas(bcoAux.getCodigo(), "Banco"); 
			}		
		}
    });
	
    comboCuentas.addValueChangeListener(new Property.ValueChangeListener() {

   		@Override
		public void valueChange(ValueChangeEvent event) {
   			
   			if("ProgramaticallyChanged".equals(comboCuentas.getData())){
   				comboCuentas.setData(null);
   	            return;
   	        }
   			
   			/*Inicializamos VO de permisos para el usuario, formulario y operacion
   			 * para confirmar los permisos del usuario*/
   			UsuarioPermisosVO permisoAux = 
   					new UsuarioPermisosVO(permisos.getCodEmp(),
   							permisos.getUsuario(),
   							VariablesPermisos.FORMULARIO_REP_MOVIMIENTOS_BANCO,
   							VariablesPermisos.OPERACION_LEER);
   			
   			CtaBcoVO ctaBcoAux;
   			ctaBcoAux = new CtaBcoVO();
   			if(comboCuentas.getValue() != null){
   				ctaBcoAux = (CtaBcoVO) comboCuentas.getValue();
   			
	   			MonedaVO auxMoneda = new MonedaVO();
	   		
	   			auxMoneda = (MonedaVO) ctaBcoAux.getMonedaVO();
				
	   			if(ctaBcoAux != null && !comboCuentas.getValue().equals("")){
	   				
	   				monedaBanco.setReadOnly(false);
	   				cuentaBanco.setReadOnly(false);
	   				
	   				monedaBanco.setValue(ctaBcoAux.getMonedaVO().getSimbolo());
	   				cuentaBanco.setValue(ctaBcoAux.getCodigo());
	   				
	   				monedaBanco.setReadOnly(true);
	   				cuentaBanco.setReadOnly(true);
	   				
	   			}
   			}
   			
   			
			
		}
    });

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
					  
					  if(	  this.fecDesde.getValue()!= null 
							  && this.fecHasta.getValue()!= null
							  //&& this.comboMoneda.getValue() != null  /*ACA EN EL IF PONER CAMPOS*/
							  )
					  {
					  try{
					  
							 
							//fillParameters.put("codTit", this.codTitular.getValue());
					        
					        fillParameters.put("codEmp",this.permisos.getCodEmp());
					       
					        
					        
					        CtaBcoVO ctaBcoAux = null;
				   			ctaBcoAux = new CtaBcoVO();
				   			if(comboCuentas.getValue() != null){
				   				ctaBcoAux = (CtaBcoVO) comboCuentas.getValue();
				   			}
					        
					        BancoVO bcoAux1 = null;
					        
			   				if(comboBancos.getValue() != null){
			   					bcoAux1 = new BancoVO();
			   					bcoAux1 = (BancoVO) comboBancos.getValue();
			   				}	
			   				
			   			 /*Concatenamos banco y cuenta para mostrar en el titulo del reporte*/
					        String bcoCtaStr = bcoAux1.getNombre() + " " + ctaBcoAux.getNombre() + "-" + cuentaBanco.getValue();
					        fillParameters.put("nomTit", bcoCtaStr);
				   				
					        fillParameters.put("codCta", cuentaBanco.getValue());
					        fillParameters.put("codBco", bcoAux1.getCodigo());
					        
					        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			                String fecDesdeStr = format.format(fecDesde.getValue());
			                String fecHastaStr = format.format(fecHasta.getValue());
					        
					        fillParameters.put("periodo", "Período: " + fecDesdeStr + " al " + fecHastaStr);
					        
					        
					        fillParameters.put("fecDesde", convertFromJAVADateToSQLDate(fecDesde.getValue()));
					        fillParameters.put("fecHasta", convertFromJAVADateToSQLDate(fecHasta.getValue()));
					        
					        fillParameters.put("REPORTS_DIR",basepath);
					      
						  }catch(Exception e) {}
					        
					        
						StreamResource myResource = report.prepareForPdfReportReturn( basepath+"/6-RepMovimietosBancoxCta.jrxml",
									                "MovimientosBanco",
									                fillParameters);
					  
						
				        Embedded e = new Embedded();
				        e.setSizeFull();
				        e.setType(Embedded.TYPE_BROWSER);

				        StreamResource resource = myResource;
				        
				        resource.setMIMEType("application/pdf");
 
				        e.setSource(resource);
				        
				        sub = new MySub("80%","75%");
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
		
		this.inicializarComboBancos(null);
		
		monedaBanco.setReadOnly(true);
		cuentaBanco.setReadOnly(true);
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
	
	
public void inicializarComboCuentas(String cod, String llamador ){
		
		if(cod != "0" && cod != null){
			BeanItemContainer<CtaBcoVO> ctaObj = new BeanItemContainer<CtaBcoVO>(CtaBcoVO.class);
			CtaBcoVO cta = new CtaBcoVO();
			ArrayList<CtaBcoVO> lstctas = new ArrayList<CtaBcoVO>();
			UsuarioPermisosVO permisosAux;
			
			try {
				permisosAux = 
						new UsuarioPermisosVO(this.permisos.getCodEmp(),
								this.permisos.getUsuario(),
								VariablesPermisos.FORMULARIO_REP_MOVIMIENTOS_BANCO,
								VariablesPermisos.OPERACION_LEER);
				
				/*Si se selacciona un banco buscamos las cuentas, de lo contrario no*/
				if(this.comboBancos.getValue() != null)
					lstctas = this.controlador.getCtaBcos(permisosAux,((BancoVO) this.comboBancos.getValue()).getCodigo());
				
			} catch (ObteniendoCuentasBcoException | InicializandoException | ConexionException | ObteniendoPermisosException | NoTienePermisosException e) {

				Mensajes.mostrarMensajeError(e.getMessage());
			}
			
			
			for (CtaBcoVO ctav : lstctas) {
					
				ctaObj.addBean(ctav);
				
				if(llamador.equals("CuentaBanco")){
					if(cod != null){
						if(cod.equals(ctav.getCodigo())){
							cta = ctav;
							this.monedaBanco.setValue(cta.getMonedaVO().getSimbolo());
							this.cuentaBanco.setValue(cta.getCodigo());
						}
					}
				}
			}
			
			
			if(lstctas.size()>0){
				
				//this.comboCuentas.setData("ProgramaticallyChanged");
				
				this.comboCuentas.setContainerDataSource(ctaObj);
				
				this.comboCuentas.setItemCaptionPropertyId("nombre");
			}
			
			if(cod!=null)
			{
				try{
					this.comboCuentas.setReadOnly(false);
					this.comboCuentas.setValue(cta);
					//this.comboCuentas.setReadOnly(true);
				}catch(Exception e)
				{}
			}
		
		}
		else{
			this.comboCuentas.setEnabled(false);
			this.cuentaBanco.setValue("");
			this.monedaBanco.setValue("");
		}
	}
	
public void inicializarComboBancos(String cod){
	
	BeanItemContainer<BancoVO> bcoObj = new BeanItemContainer<BancoVO>(BancoVO.class);
	BancoVO bcoVO = new BancoVO();
	ArrayList<BancoVO> lstBcos = new ArrayList<BancoVO>();
	UsuarioPermisosVO permisosAux;
	
	try {
		permisosAux = 
				new UsuarioPermisosVO(this.permisos.getCodEmp(),
						this.permisos.getUsuario(),
						VariablesPermisos.FORMULARIO_REP_MOVIMIENTOS_BANCO, 
						VariablesPermisos.OPERACION_LEER);
		
		lstBcos = this.controlador.getBcos(permisosAux);
		
	} catch ( InicializandoException | ConexionException | ObteniendoPermisosException | NoTienePermisosException | ObteniendoBancosException | ObteniendoCuentasBcoException e) {

		Mensajes.mostrarMensajeError(e.getMessage());
	}
	
	for (BancoVO bco : lstBcos) {
		
		if(bco.getCodigo().equals("0")){
			lstBcos.remove(bco);
		}
		else{
			bcoObj.addBean(bco);
			
			if(cod != null){
				if(cod.equals(bco.getCodigo())){
					bcoVO = bco;
					bcoVO.setCodEmp("0");
				}
			}
		}
		
	}
	
	this.comboBancos.setContainerDataSource(bcoObj);
	this.comboBancos.setItemCaptionPropertyId("nombre");
	
	if(cod!=null)
	{
		this.comboBancos.setReadOnly(false);
		this.comboBancos.setValue(bcoVO);
		this.comboBancos.setReadOnly(true);
	}
}
	
}
