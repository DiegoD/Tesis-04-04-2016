package com.vista.Rubros;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.controladores.CodigoGeneralizadoControlador;
import com.controladores.EmpresaControlador;
import com.controladores.ImpuestoControlador;
import com.controladores.RubroControlador;
import com.excepciones.ConexionException;
import com.excepciones.ErrorInesperadoException;
import com.excepciones.InicializandoException;
import com.excepciones.Empresas.ExisteEmpresaException;
import com.excepciones.Empresas.InsertandoEmpresaException;
import com.excepciones.Empresas.ModificandoEmpresaException;
import com.excepciones.Empresas.NoExisteEmpresaException;
import com.excepciones.Impuestos.ObteniendoImpuestosException;
import com.excepciones.Rubros.ExisteRubroException;
import com.excepciones.Rubros.InsertandoRubroException;
import com.excepciones.Rubros.ModificandoRubroException;
import com.excepciones.Rubros.NoExisteRubroException;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.UI;
import com.valueObject.CodigoGeneralizadoVO;
import com.valueObject.EmpLoginVO;
import com.valueObject.EmpresaVO;
import com.valueObject.ImpuestoVO;
import com.valueObject.RubroVO;
import com.vista.BusquedaView;
import com.vista.BusquedaViewExtended;
import com.vista.IBusqueda;
import com.vista.Mensajes;
import com.vista.MySub;
import com.vista.PermisosUsuario;
import com.vista.Variables;
import com.vista.VariablesPermisos;
import com.vista.Empresas.EmpresasPanelExtended;
import com.vista.Usuarios.UsuarioViewAgregarGrupoExtend;

public class RubroViewExtended extends RubroView implements IBusqueda{
	
	private BeanFieldGroup<RubroVO> fieldGroup;
	private RubroControlador controlador;
	private ImpuestoControlador controladorImpuestos;
	private CodigoGeneralizadoControlador controladorTipoRubro;
	private String operacion;
	private RubrosPanelExtended mainView;
	private BeanItemContainer<ImpuestoVO> containerImpuestos;
	MySub sub;
	private PermisosUsuario permisos;
	ArrayList<ImpuestoVO> lstImpuestos = new ArrayList<ImpuestoVO>();
	private String codigoImp;
	
	
	/**
	 * Constructor del formulario
	 * Con operación y la vista que lo llamo
	 * @throws InicializandoException 
	 * @throws ConexionException 
	 * @throws ObteniendoImpuestosException 
	 */
	public RubroViewExtended(String opera, RubrosPanelExtended main) throws  ConexionException, InicializandoException{
	
		
		
		this.permisos = (PermisosUsuario)VaadinService.getCurrentRequest().getWrappedSession().getAttribute("permisos");
		this.operacion = opera;
		this.mainView = main;
		
		this.inicializarForm();
		
		/*Inicializamos listener de boton aceptar*/
		this.aceptar.addClickListener(click -> {
				
			try {
				
				/*Validamos los campos antes de invocar al controlador*/
				if(this.fieldsValidos())
				{
									
					RubroVO rubroVO = new RubroVO();		
					
					rubroVO.setcodRubro(codRubro.getValue().trim());
					rubroVO.setDescripcion(descripcion.getValue().trim());
					rubroVO.setActivo(activo.getValue());
					rubroVO.setUsuarioMod(this.permisos.getUsuario());
					rubroVO.setOperacion(operacion);
					rubroVO.setCodigoImpuesto(codigoImpuesto.getValue());
					rubroVO.setDescripcionImpuesto(descripcionImpuesto.getValue().trim());
					String aux = porcentajeImpuesto.getValue().toString().trim().replace(",", ".");
					rubroVO.setPorcentajeImpuesto(Float.parseFloat(aux));
					rubroVO.setTipoRubro(tipoRubro.getValue().trim());
					rubroVO.setCodTipoRubro(codTipoRubro.getValue().trim());
					
										
					if(this.operacion.equals(Variables.OPERACION_NUEVO)) {	
		
						this.controlador.insertarRubro(rubroVO);
						
						this.mainView.actulaizarGrilla(rubroVO);
						
						Mensajes.mostrarMensajeOK("Se ha guardado el rubro");
						main.cerrarVentana();
					
					}
					else if(this.operacion.equals(Variables.OPERACION_EDITAR))	{
						
						this.controlador.actualizarRubro(rubroVO);
						
						this.mainView.actulaizarGrilla(rubroVO);
						
						Mensajes.mostrarMensajeOK("Se ha modificado el rubro");
						main.cerrarVentana();
						
					}
				}
				else /*Si los campos no son válidos mostramos warning*/
				{
					Mensajes.mostrarMensajeWarning(Variables.WARNING_CAMPOS_NO_VALIDOS);
				}
					
			} 
			catch (ConexionException | NoExisteRubroException | ModificandoRubroException | ExisteRubroException | 
					 InicializandoException | InsertandoRubroException | ErrorInesperadoException e) {
				
				Mensajes.mostrarMensajeError(e.getMessage());
			}
				
		});
		
		/*Inicalizamos listener para boton de Editar*/
		this.btnEditar.addClickListener(click -> {
				
			try {
			
				/*Inicializamos el Form en modo Edicion*/
				this.iniFormEditar();
			}
			catch(Exception e)	{
				Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
			}
		});
			
		this.btnBuscarImpuesto.addClickListener(click -> {
			
			BusquedaViewExtended form = new BusquedaViewExtended(this, new ImpuestoVO());
			ArrayList<Object> lst = new ArrayList<Object>();
			ArrayList<ImpuestoVO> lstImpuesto = new ArrayList<ImpuestoVO>();
			controladorImpuestos = new ImpuestoControlador();
			try {
				lstImpuesto = this.controladorImpuestos.getImpuestos();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			Object obj;
			for (ImpuestoVO i: lstImpuesto) {
				obj = new Object();
				obj = (Object)i;
				lst.add(obj);
			}
			try {
				form.inicializarGrilla(lst);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			sub = new MySub("65%", "65%" );
			sub.setModal(true);
			sub.center();
			sub.setModal(true);
			sub.setVista(form);
			sub.center();
			sub.setDraggable(true);
			UI.getCurrent().addWindow(sub);
			
		});
		
		this.btnBuscarTipoRubro.addClickListener(click -> {
			BusquedaViewExtended form = new BusquedaViewExtended(this, new CodigoGeneralizadoVO());
			ArrayList<Object> lst = new ArrayList<Object>();
			ArrayList<CodigoGeneralizadoVO> lstTipoRubro = new ArrayList<CodigoGeneralizadoVO>();
			controladorTipoRubro = new CodigoGeneralizadoControlador();
			try {
				lstTipoRubro = this.controladorTipoRubro.getCodigosGeneralizadosxCodigo("tipo_rubro");
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			Object obj;
			for (CodigoGeneralizadoVO i: lstTipoRubro) {
				obj = new Object();
				obj = (Object)i;
				lst.add(obj);
			}
			try {
				form.inicializarGrilla(lst);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			sub = new MySub("65%", "65%" );
			sub.setModal(true);
			sub.center();
			sub.setModal(true);
			sub.setVista(form);
			sub.center();
			sub.setDraggable(true);
			UI.getCurrent().addWindow(sub);
			
		});
		
		this.cancelar.addClickListener(click -> {
			main.cerrarVentana();
		});
	}
	
	public  void inicializarForm(){
		
		this.controlador = new RubroControlador();
					
		this.fieldGroup =  new BeanFieldGroup<RubroVO>(RubroVO.class);
		
		//Seteamos info del form si es requerido
		if(fieldGroup != null)
			fieldGroup.buildAndBindMemberFields(this);
		
		/*SI LA OPERACION NO ES NUEVO, OCULTAMOS BOTON ACEPTAR*/
		if(this.operacion.equals(Variables.OPERACION_NUEVO))
		{
			/*Inicializamos al formulario como nuevo*/
			this.iniFormNuevo();
	
		}
		else if(this.operacion.equals(Variables.OPERACION_LECTURA))	{
			/*Inicializamos formulario como editar*/
			this.iniFormLectura();
		} 
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
		
		this.codRubro.setRequired(setear);
		this.codRubro.setRequiredError("Es requerido");
		
		this.descripcion.setRequired(setear);
		this.descripcion.setRequiredError("Es requerido");
		
	}
	
	/**
	 * Dado un item RubroVO seteamos la info del formulario
	 *
	 */
	public void setDataSourceFormulario(BeanItem<RubroVO> item)
	{
		this.fieldGroup.setItemDataSource(item);
		
		RubroVO rubro = new RubroVO();
		rubro = fieldGroup.getItemDataSource().getBean();
		String fecha = new SimpleDateFormat("dd/MM/yyyy").format(rubro.getFechaMod());
		//this.codigoImp = rubro.getCod_impuesto();
		
		auditoria.setDescription(
				"Usuario: " + rubro.getUsuarioMod() + "<br>" +
			    "Fecha: " + fecha + "<br>" +
			    "Operación: " + rubro.getOperacion());
		
		/*SETEAMOS LA OPERACION EN MODO LECUTA
		 * ES CUANDO LLAMAMOS ESTE METODO*/
		if(this.operacion.equals(Variables.OPERACION_LECTURA))
			this.iniFormLectura();
				
	}
	
	/**
	 * Seteamos el formulario en modo solo Lectura
	 *
	 */
	private void iniFormLectura()
	{
		/*Habilitamos el boton de editar,
		 * deshabilitamos botn aceptar*/
		this.enableBotonesLectura();
		this.disableBotonAceptar();
		
		/*No mostramos las validaciones*/
		this.setearValidaciones(false);
		
		/*Dejamos todods los campos readonly*/
		this.readOnlyFields(true);
		
		
		
	}
	
	/**
	 * Seteamos el formulario en modo Edicion
	 *
	 */
	private void iniFormEditar()
	{
		/*Seteamos el form en editar*/
		this.operacion = Variables.OPERACION_EDITAR;
		
		
		/*Verificamos que tenga permisos*/
		boolean permisoNuevoEditar = this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_RUBROS, VariablesPermisos.OPERACION_NUEVO_EDITAR);
		
		if(permisoNuevoEditar){
			
			/*Oculatamos Editar y mostramos el de guardar y de agregar formularios*/
			this.enableBotonAceptar();
			this.disableBotonLectura();

			/*Dejamos los textfields que se pueden editar
			 * en readonly = false asi  se pueden editar*/
			this.setearFieldsEditar();
			
			/*Seteamos las validaciones*/
			this.setearValidaciones(true);
		}
		else{
			
			/*Mostramos mensaje Sin permisos para operacion*/
			Mensajes.mostrarMensajeError(Variables.USUSARIO_SIN_PERMISOS);
		}
	}
	
	/**
	 * Seteamos el formulario en modo Nuevo
	 *
	 */
	private void iniFormNuevo()
	{
		this.operacion = Variables.OPERACION_NUEVO;
		/*Chequeamos si tiene permiso de editar*/
		boolean permisoNuevoEditar = this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_RUBROS, VariablesPermisos.OPERACION_NUEVO_EDITAR);
		
		/*Si no tiene permisos de Nuevo Cerrmamos la ventana y mostramos mensaje*/
		if(!permisoNuevoEditar)
		{
			mainView.cerrarVentana();
			mainView.mostrarMensaje(Variables.USUSARIO_SIN_PERMISOS);
		}
		
		this.enableBotonAceptar();
		this.disableBotonLectura();
		this.activo.setValue(true);
		
		/*Seteamos validaciones en nuevo, cuando es editar
		 * solamente cuando apreta el boton editar*/
		this.setearValidaciones(true);
		
		/*Como es en operacion nuevo, dejamos todos los campos editabls*/
		this.readOnlyFields(false);
	}
	
	/**
	 * Dejamos setear los texFields correspondientes, 
	 *  
	 * Solamente aquellos campos posibles de editar
	 * EJ: el codigo no se deja editar
	 *
	 */
	private void setearFieldsEditar()
	{
		this.descripcion.setReadOnly(false);
		this.activo.setReadOnly(false);
		this.descripcionImpuesto.setReadOnly(false);
		this.tipoRubro.setReadOnly(false);
		this.codigoImpuesto.setReadOnly(false);
		this.descripcionImpuesto.setEnabled(false);
		this.codigoImpuesto.setEnabled(false);
		this.tipoRubro.setEnabled(false);
	}
	
	
	/**
	 * Deshabilitamos el boton editar
	 *
	 */
	private void disableBotonLectura()
	{
		this.btnEditar.setEnabled(false);
		this.btnEditar.setVisible(false);
	}
	
	/**
	 * Habilitamos el boton editar 
	 *
	 */
	private void enableBotonesLectura()
	{
		this.btnEditar.setEnabled(true);
		this.btnEditar.setVisible(true);
		
	}
	
	/**
	 * Deshabilitamos el boton aceptar
	 *
	 */
	private void disableBotonAceptar()
	{
		this.aceptar.setEnabled(false);
		this.aceptar.setVisible(false);
		this.btnBuscarImpuesto.setEnabled(false);
		this.btnBuscarImpuesto.setVisible(false);
		this.btnBuscarTipoRubro.setEnabled(false);
		this.btnBuscarTipoRubro.setVisible(false);
	}
	
	/**
	 * Habilitamos el boton aceptar
	 *
	 */
	private void enableBotonAceptar()
	{
		this.aceptar.setEnabled(true);
		this.aceptar.setVisible(true);
		this.btnBuscarImpuesto.setEnabled(true);
		this.btnBuscarImpuesto.setVisible(true);
		this.btnBuscarTipoRubro.setEnabled(true);
		this.btnBuscarTipoRubro.setVisible(true);
	}
	
	/**
	 * Dejamos todos los Fields readonly o no,
	 * dado el boolenao pasado por parametro
	 *
	 */
	private void readOnlyFields(boolean setear)
	{
		this.codRubro.setReadOnly(setear);
		this.descripcion.setReadOnly(setear);
		this.activo.setReadOnly(setear);
		this.descripcionImpuesto.setReadOnly(false);	
		this.descripcionImpuesto.setEnabled(false);
		this.tipoRubro.setReadOnly(false);
		this.tipoRubro.setEnabled(false);
		this.codigoImpuesto.setReadOnly(false);
		this.codigoImpuesto.setEnabled(false);
		
		
	}
	
	/**
	 * Seteamos todos las validaciones de los fields
	 * del formulario
	 *
	 */
	private void agregarFieldsValidaciones()
	{
        this.codRubro.addValidator(
                new StringLengthValidator(
                     " 20 caracteres máximo", 1, 20, false));
        
        this.descripcion.addValidator(
                new StringLengthValidator(
                        " 45 caracteres máximo", 1, 45, false));
        
	}
	
	/**
	 * Nos retorna true si los campos
	 * son válidos, se debe invocar antes
	 * de consumir al controlador
	 *
	 */
	private boolean fieldsValidos()
	{
		boolean valido = false;
		
		//Agregamos validaciones a los campos para luego controlarlos
		this.agregarFieldsValidaciones();
		
		if(this.codRubro.isValid() && this.descripcion.isValid() )
			valido = true;
			
		return valido;
	}
	
	/**
	 * Obtenemos impuestos del sistema
	 * @throws Exception 
	 *
	 */
	private ArrayList<ImpuestoVO> getImpuestos() throws Exception  {
		
		this.lstImpuestos = new ArrayList<ImpuestoVO>();

		try {
			
				lstImpuestos = controladorImpuestos.getImpuestos();
			
		} 
		catch (ObteniendoImpuestosException | InicializandoException | ConexionException e) {
			
			Mensajes.mostrarMensajeError(e.getMessage());
		}
		
			
		return lstImpuestos;
	}
	
	public void setImpuesto(ImpuestoVO impuesto){
		this.descripcionImpuesto.setValue(impuesto.getDescripcion());
		this.codigoImpuesto.setValue(impuesto.getcodImpuesto());
	}
	
	public void cerrarVentana()
	{
		UI.getCurrent().removeWindow(sub);
	}

	@Override
	public void setInfo(Object datos) {
		// TODO Auto-generated method stub
		if(datos instanceof ImpuestoVO){
			ImpuestoVO impuestoVO = (ImpuestoVO) datos;
			this.descripcionImpuesto.setValue(impuestoVO.getDescripcion());
			this.codigoImpuesto.setValue(impuestoVO.getcodImpuesto());
			//this.porcentajeImpuesto.setValue(Float.toString(impuestoVO.getPorcentaje()));
			//this.porcentajeImpuesto.setValue(String.valueOf(impuestoVO.getPorcentaje()));
			this.porcentajeImpuesto.setValue(String.format("%.2f",impuestoVO.getPorcentaje()));
		}
		
		if(datos instanceof CodigoGeneralizadoVO){
			CodigoGeneralizadoVO tipoRubro = (CodigoGeneralizadoVO) datos;
			this.tipoRubro.setValue(tipoRubro.getValor());
			this.codTipoRubro.setValue(tipoRubro.getCodigo());
		}
		
		
	}
	
}
