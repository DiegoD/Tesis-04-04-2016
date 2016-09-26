package com.vista.Gastos;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.controladores.GastoControlador;
import com.excepciones.ConexionException;
import com.excepciones.ErrorInesperadoException;
import com.excepciones.InicializandoException;
import com.excepciones.NoTienePermisosException;
import com.excepciones.ObteniendoPermisosException;
import com.excepciones.Cuentas.ObteniendoCuentasException;
import com.excepciones.Cuentas.ObteniendoRubrosException;
import com.excepciones.Gastos.ExisteGastoException;
import com.excepciones.Gastos.IngresandoGastoException;
import com.excepciones.Gastos.ModificandoGastoException;
import com.excepciones.Gastos.NoExisteGastoException;
import com.excepciones.Impuestos.ObteniendoImpuestosException;
import com.excepciones.Monedas.ObteniendoMonedaException;
import com.excepciones.Procesos.ObteniendoProcesosException;
import com.excepciones.funcionarios.ObteniendoFuncionariosException;
import com.vaadin.data.Container.ItemSetChangeEvent;
import com.vaadin.data.Container.ItemSetChangeListener;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.event.FieldEvents.BlurEvent;
import com.vaadin.event.FieldEvents.BlurListener;
import com.vaadin.event.FieldEvents.FocusEvent;
import com.vaadin.event.FieldEvents.FocusListener;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.UI;
import com.valueObject.FuncionarioVO;
import com.valueObject.ImpuestoVO;
import com.valueObject.MonedaVO;
import com.valueObject.RubroVO;
import com.valueObject.UsuarioPermisosVO;
import com.valueObject.Cuenta.CuentaVO;
import com.valueObject.Gasto.GastoVO;
import com.valueObject.Numeradores.NumeradoresVO;
import com.valueObject.proceso.ProcesoVO;
import com.vista.BusquedaViewExtended;
import com.vista.IBusqueda;
import com.vista.Mensajes;
import com.vista.MySub;
import com.vista.PermisosUsuario;
import com.vista.Variables;
import com.vista.VariablesPermisos;

public class GastoViewExtended extends GastoView implements IBusqueda{
	
	private BeanFieldGroup<GastoVO> fieldGroup;
	private GastoControlador controlador;
	private String operacion;
	private GastosPanelExtended mainView;
	MySub sub;
	private PermisosUsuario permisos;
	UsuarioPermisosVO permisoAux;
	Integer codigoInsert;
	String aux;
	NumeradoresVO codigos;
	
	public GastoViewExtended(String opera, GastosPanelExtended main){
		
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
					/*Inicializamos VO de permisos para el usuario, formulario y operacion
					 * para confirmar los permisos del usuario*/
					permisoAux = 
							new UsuarioPermisosVO(this.permisos.getCodEmp(),
									this.permisos.getUsuario(),
									VariablesPermisos.FORMULARIO_GASTOS,
									VariablesPermisos.OPERACION_NUEVO_EDITAR);
									
					GastoVO gastoVO = new GastoVO();		
					
					gastoVO.setCodEmp(this.permisos.getCodEmp());
					gastoVO.setUsuarioMod(this.permisos.getUsuario());
					gastoVO.setOperacion(operacion);
					
					if(comboSeleccion.getValue().equals("Proceso")){
						gastoVO.setDescProceso(descProceso.getValue().trim());
						if(codProceso.getValue().equals("")){
							gastoVO.setCodProceso(null);
						}
						else{
							gastoVO.setCodProceso(codProceso.getValue().trim());
						}
					}
					
					
					gastoVO.setCodDocum("Gasto");
					gastoVO.setSerieDocum("A");
					gastoVO.setCodImpuesto(codImpuesto.getValue().trim());
					gastoVO.setNomImpuesto(nomImpuesto.getValue().trim());
					
					if(comboSeleccion.getValue().equals("Proceso")){
						gastoVO.setCodCtaInd("IngGastoProceso");
					}
					else if(comboSeleccion.getValue().equals("Empleado")){
						gastoVO.setCodCtaInd("IngGastoEmpleado");
					}
					else if(comboSeleccion.getValue().equals("Oficina")){
						gastoVO.setCodCtaInd("IngGastoOficina");
					}
					
					
					//gastoVO.setPorcentajeImpuesto(porcentajeImpuesto);
					
					//Cliente
					if(comboSeleccion.getValue().equals("Oficina")){
						gastoVO.setCodTitular(null);
						gastoVO.setNomTitular("Oficina");
					}
					else{
						gastoVO.setCodTitular(codTitular.getValue().trim());
						gastoVO.setNomTitular(nomTitular.getValue().trim());
					}
					
					
					//Moneda
					if(this.comboMoneda.getValue() != null){
						MonedaVO auxMoneda = new MonedaVO();
						auxMoneda = (MonedaVO) this.comboMoneda.getValue();
						gastoVO.setCodMoneda(auxMoneda.getCodMoneda());
						gastoVO.setNomMoneda(auxMoneda.getDescripcion());
						gastoVO.setSimboloMoneda(auxMoneda.getSimbolo());
					}
					else{
						gastoVO.setCodMoneda("");
						gastoVO.setNomMoneda("");
						gastoVO.setSimboloMoneda("");
					}
					
					
//					//Impuesto
//					if(this.descripcionImpuesto.getValue() != null){
//						gastoVO.setCodImpuesto(codImpuesto.getValue().trim());
//						gastoVO.setDescripcionImpuesto(descripcionImpuesto.getValue().trim());
//						
//						aux = porcentajeImpuesto.getValue().toString().trim().replace(",", ".");
//						gastoVO.setPorcentajeImpuesto(Float.parseFloat(aux));
//					}
//					else{
//						gastoVO.setCodImpuesto("");
//						gastoVO.setDescripcionImpuesto("");
//						gastoVO.setPorcentajeImpuesto(0);
//					}
					
					gastoVO.setReferencia(referencia.getValue().trim());
					gastoVO.setCodCuenta(codCuenta.getValue().trim());
					gastoVO.setNomCuenta(nomCuenta.getValue().trim());
					gastoVO.setCodRubro(codRubro.getValue().trim());
					gastoVO.setNomRubro(nomRubro.getValue().trim());
					
					if(impTotMn.getValue() != ""){
						aux = impTotMn.getValue().toString().trim().replace(",", ".");
						gastoVO.setImpTotMn(Float.parseFloat(aux));
					}
					else{
						gastoVO.setImpTotMn(0);
					}
					
					if(impTtotMo.getValue() != ""){
						aux = impTtotMo.getValue().toString().trim().replace(",", ".");
						gastoVO.setImpTtotMo(Float.parseFloat(aux));
					}
					else{
						gastoVO.setImpTtotMo(0);
					}
					
					if(impImpuMn.getValue() != ""){
						aux = impImpuMn.getValue().toString().trim().replace(",", ".");
						gastoVO.setImpImpuMn(Float.parseFloat(aux));
					}
					else{
						gastoVO.setImpImpuMn(0);
					}
					
					if(impImpuMo.getValue() != ""){
						aux = impImpuMo.getValue().toString().trim().replace(",", ".");
						gastoVO.setImpImpuMo(Float.parseFloat(aux));
					}
					else{
						gastoVO.setImpImpuMo(0);
					}
					
					gastoVO.setImpSubMn(0);
					gastoVO.setImpSubMo(0);
					
					if(tcMov.getValue() != ""){
						aux = tcMov.getValue().toString().trim().replace(",", ".");
						gastoVO.setTcMov(Float.parseFloat(aux));
					}
					else{
						gastoVO.setTcMov(0);
					}
					
					
					if(this.operacion.equals(Variables.OPERACION_NUEVO)){
						gastoVO.setNroDocum((0));
					}
					else{
						gastoVO.setNroDocum(Integer.parseInt(nroDocum.getValue().trim()));
					}
					
					if(this.operacion.equals(Variables.OPERACION_NUEVO)){
						gastoVO.setNroTrans((0));
					}
					else{
						gastoVO.setNroTrans(Integer.parseInt(nroTrans.getValue().trim()));
					}
					
					gastoVO.setFecDoc(new java.sql.Timestamp(fecDoc.getValue().getTime()));
					gastoVO.setFecValor(new java.sql.Timestamp(fecValor.getValue().getTime()));
					
					if(this.operacion.equals(Variables.OPERACION_NUEVO)) {	
		
						codigos = this.controlador.insertarGasto(gastoVO, permisoAux);
						gastoVO.setNroDocum(codigos.getCodigo());
						gastoVO.setNroTrans(codigos.getNumeroTrans());
						
						this.mainView.actulaizarGrilla(gastoVO);
						
						Mensajes.mostrarMensajeOK("Se ha guardado el gasto");
						main.cerrarVentana();
					
					}
					else if(this.operacion.equals(Variables.OPERACION_EDITAR))	{
						
						this.controlador.actualizarGasto(gastoVO, permisoAux);
						this.mainView.actulaizarGrilla(gastoVO);
						
						Mensajes.mostrarMensajeOK("Se ha modificado el gasto");
						main.cerrarVentana();
						
					}
				}
				else /*Si los campos no son v�lidos mostramos warning*/
				{
					Mensajes.mostrarMensajeWarning(Variables.WARNING_CAMPOS_NO_VALIDOS);
				}
					
			} 
			catch (ConexionException | ModificandoGastoException | ExisteGastoException | 
					 InicializandoException | IngresandoGastoException | NoExisteGastoException |
					 ErrorInesperadoException| ObteniendoPermisosException| NoTienePermisosException e) {
				
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
		
		this.cancelar.addClickListener(click -> {
			main.cerrarVentana();
		});
			
		this.btnBuscarProceso.addClickListener(click -> {
			
			BusquedaViewExtended form = new BusquedaViewExtended(this, new ProcesoVO());
			ArrayList<Object> lst = new ArrayList<Object>();
			ArrayList<ProcesoVO> lstProcesos = new ArrayList<ProcesoVO>();
			
			/*Inicializamos VO de permisos para el usuario, formulario y operacion
			 * para confirmar los permisos del usuario*/
			UsuarioPermisosVO permisoAux = 
					new UsuarioPermisosVO(this.permisos.getCodEmp(),
							this.permisos.getUsuario(),
							VariablesPermisos.FORMULARIO_GASTOS,
							VariablesPermisos.OPERACION_NUEVO_EDITAR);
			
			try {
				lstProcesos = this.controlador.getProcesos(permisoAux);
				
			} catch ( ConexionException | InicializandoException | ObteniendoPermisosException | NoTienePermisosException | ObteniendoProcesosException e) {

				Mensajes.mostrarMensajeError(e.getMessage());
			}
			Object obj;
			for (ProcesoVO i: lstProcesos) {
				obj = new Object();
				obj = (Object)i;
				lst.add(obj);
			}
			try {
				
				form.inicializarGrilla(lst);
			
				
			} catch (Exception e) {
				
				Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
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
		
		this.btnBuscarRubro.addClickListener(click -> {
			
			BusquedaViewExtended form = new BusquedaViewExtended(this, new RubroVO());
			ArrayList<Object> lst = new ArrayList<Object>();
			ArrayList<RubroVO> lstRubros = new ArrayList<RubroVO>();
			
			/*Inicializamos VO de permisos para el usuario, formulario y operacion
			 * para confirmar los permisos del usuario*/
			UsuarioPermisosVO permisoAux = 
					new UsuarioPermisosVO(this.permisos.getCodEmp(),
							this.permisos.getUsuario(),
							VariablesPermisos.FORMULARIO_GASTOS,
							VariablesPermisos.OPERACION_NUEVO_EDITAR);
			
			try {
				lstRubros = this.controlador.getRubros(permisoAux);
				
			} catch ( ConexionException | InicializandoException | ObteniendoPermisosException | NoTienePermisosException | ObteniendoRubrosException | com.excepciones.Rubros.ObteniendoRubrosException e) {

				Mensajes.mostrarMensajeError(e.getMessage());
			}
			Object obj;
			for (RubroVO i: lstRubros) {
				obj = new Object();
				obj = (Object)i;
				lst.add(obj);
			}
			try {
				
				form.inicializarGrilla(lst);
			
				
			} catch (Exception e) {
				
				Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
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
		
		this.btnBuscarCuenta.addClickListener(click -> {
			
			BusquedaViewExtended form = new BusquedaViewExtended(this, new CuentaVO());
			ArrayList<Object> lst = new ArrayList<Object>();
			ArrayList<CuentaVO> lstCuentas = new ArrayList<CuentaVO>();
			
			/*Inicializamos VO de permisos para el usuario, formulario y operacion
			 * para confirmar los permisos del usuario*/
			UsuarioPermisosVO permisoAux = 
					new UsuarioPermisosVO(this.permisos.getCodEmp(),
							this.permisos.getUsuario(),
							VariablesPermisos.FORMULARIO_GASTOS,
							VariablesPermisos.OPERACION_NUEVO_EDITAR);
			
			try {
				lstCuentas = this.controlador.getCuentas(permisoAux, this.codRubro.getValue().trim());
				
			} catch ( ObteniendoCuentasException | ConexionException | InicializandoException | ObteniendoPermisosException | NoTienePermisosException | ObteniendoRubrosException e) {

				Mensajes.mostrarMensajeError(e.getMessage());
			}
			Object obj;
			for (CuentaVO i: lstCuentas) {
				obj = new Object();
				obj = (Object)i;
				lst.add(obj);
			}
			try {
				
				form.inicializarGrilla(lst);
			
				
			} catch (Exception e) {
				
				Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
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
		
		this.btnBuscarImpuesto.addClickListener(click -> {
			
			BusquedaViewExtended form = new BusquedaViewExtended(this, new ImpuestoVO());
			ArrayList<Object> lst = new ArrayList<Object>();
			ArrayList<ImpuestoVO> lstImpuestos = new ArrayList<ImpuestoVO>();
			
			/*Inicializamos VO de permisos para el usuario, formulario y operacion
			 * para confirmar los permisos del usuario*/
			UsuarioPermisosVO permisoAux = 
					new UsuarioPermisosVO(this.permisos.getCodEmp(),
							this.permisos.getUsuario(),
							VariablesPermisos.FORMULARIO_GASTOS,
							VariablesPermisos.OPERACION_NUEVO_EDITAR);
			
			try {
				lstImpuestos = this.controlador.getImpuestos(permisoAux);
				
			} catch ( ConexionException | InicializandoException | ObteniendoPermisosException | NoTienePermisosException | ObteniendoImpuestosException e) {

				Mensajes.mostrarMensajeError(e.getMessage());
			}
			Object obj;
			for (ImpuestoVO i: lstImpuestos) {
				obj = new Object();
				obj = (Object)i;
				lst.add(obj);
			}
			try {
				
				form.inicializarGrilla(lst);
			
				
			} catch (Exception e) {
				
				Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
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
		
		this.btnBuscarEmpleado.addClickListener(click -> {
			
			BusquedaViewExtended form = new BusquedaViewExtended(this, new FuncionarioVO());
			ArrayList<Object> lst = new ArrayList<Object>();
			ArrayList<FuncionarioVO> lstFuncionarios = new ArrayList<FuncionarioVO>();
			
			/*Inicializamos VO de permisos para el usuario, formulario y operacion
			 * para confirmar los permisos del usuario*/
			UsuarioPermisosVO permisoAux = 
					new UsuarioPermisosVO(this.permisos.getCodEmp(),
							this.permisos.getUsuario(),
							VariablesPermisos.FORMULARIO_GASTOS,
							VariablesPermisos.OPERACION_NUEVO_EDITAR);
			
			try {
				lstFuncionarios = this.controlador.getFuncionarios(permisoAux);
				
			} catch ( ObteniendoFuncionariosException | ConexionException | InicializandoException | ObteniendoPermisosException | NoTienePermisosException e) {

				Mensajes.mostrarMensajeError(e.getMessage());
			}
			Object obj;
			for (FuncionarioVO i: lstFuncionarios) {
				obj = new Object();
				obj = (Object)i;
				lst.add(obj);
			}
			try {
				
				form.inicializarGrilla(lst);
			
				
			} catch (Exception e) {
				
				Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
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
		
		comboSeleccion.addBlurListener(new BlurListener() {
            
			@Override
			public void blur(BlurEvent event) {
				// TODO Auto-generated method stub
				if(comboSeleccion.getValue().equals("Empleado")){
					inicializoEmpleado();
				}
				else if(comboSeleccion.getValue().equals("Proceso")){
					inicializoProceso();
				}
				else if(comboSeleccion.getValue().equals("Oficina")){
					inicializoOficina();
				}
				
			}
        });
		

	}
	
	public  void inicializarForm(){
		
		this.controlador = new GastoControlador();
					
		this.fieldGroup =  new BeanFieldGroup<GastoVO>(GastoVO.class);
		
		
		
		//inicializar los valores de los combos impuesto y tipo de rubro
		inicializarComboMoneda(null);
		
		
		
		//Seteamos info del form si es requerido
		if(fieldGroup != null)
			fieldGroup.buildAndBindMemberFields(this);
		
		/*SI LA OPERACION NO ES NUEVO, OCULTAMOS BOTON ACEPTAR*/
		if(this.operacion.equals(Variables.OPERACION_NUEVO))
		{
			this.comboSeleccion.setValue("Proceso");
			
			/*Inicializamos al formulario como nuevo*/
			this.iniFormNuevo();
			this.inicializoProceso();
	
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
		
		this.fecDoc.setRequired(setear);
		this.fecDoc.setRequiredError("Es requerido");
		
		this.fecValor.setRequired(setear);
		this.fecValor.setRequiredError("Es requerido");
		
		this.comboMoneda.setRequired(setear);
		this.comboMoneda.setRequiredError("Es requerido");
		
		this.codTitular.setRequired(setear);
		this.codTitular.setRequiredError("Es requerido");
		
		this.tcMov.setRequired(setear);
		this.tcMov.setRequiredError("Es requerido");
		
		this.codImpuesto.setRequired(setear);
		this.codImpuesto.setRequiredError("Es requerido");
		
		this.impImpuMn.setRequired(setear);
		this.impImpuMn.setRequiredError("Es requerido");
		
		this.impImpuMo.setRequired(setear);
		this.impImpuMo.setRequiredError("Es requerido");
		
		this.impTotMn.setRequired(setear);
		this.impTotMn.setRequiredError("Es requerido");
		
		this.impTtotMo.setRequired(setear);
		this.impTtotMo.setRequiredError("Es requerido");
		
		this.codRubro.setRequired(setear);
		this.codRubro.setRequiredError("Es requerido");
		
		this.codCuenta.setRequired(setear);
		this.codCuenta.setRequiredError("Es requerido");
		
		this.codProceso.setRequired(setear);
		this.codProceso.setRequiredError("Es requerido");
	}
	
	/**
	 * Dado un item ProcesoVO seteamos la info del formulario
	 *
	 */
	public void setDataSourceFormulario(BeanItem<GastoVO> item)
	{
		this.fieldGroup.setItemDataSource(item);
		
		GastoVO gasto = new GastoVO();
		gasto = fieldGroup.getItemDataSource().getBean();
		//this.obseAux.setValue(gasto.getObservaciones()); 
		String fecha = new SimpleDateFormat("dd/MM/yyyy").format(gasto.getFechaMod());
		
		
		auditoria.setDescription(
				"Usuario: " + gasto.getUsuarioMod() + "<br>" +
			    "Fecha: " + fecha + "<br>" +
			    "Operaci�n: " + gasto.getOperacion());
		
		this.inicializarComboMoneda(gasto.getCodMoneda());
		this.inicializarComboSeleccion(gasto.getCodCtaInd());
		
		/*SETEAMOS LA OPERACION EN MODO LECUTA
		 * ES CUANDO LLAMAMOS ESTE METODO*/
		if(this.operacion.equals(Variables.OPERACION_LECTURA)){
			this.iniFormLectura();
			this.comboSeleccion.setEnabled(false);
		}
			
				
	}
	
	/**
	 * Seteamos el formulario en modo solo Lectura
	 *
	 */
	private void iniFormLectura()
	{
		/*Verificamos que tenga permisos para editar*/
		boolean permisoNuevoEditar = this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_GASTOS, VariablesPermisos.OPERACION_NUEVO_EDITAR);
		
		
		/*Si tiene permisos de editar habilitamos el boton de 
		 * edicion*/
		if(permisoNuevoEditar){
			
			this.enableBotonesLectura();
			
		}else{ /*de lo contrario lo deshabilitamos*/
			
			this.disableBotonLectura();
		}
		
		/*Deshabilitamos botn aceptar*/
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
		boolean permisoNuevoEditar = this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_GASTOS, VariablesPermisos.OPERACION_NUEVO_EDITAR);
		
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
		boolean permisoNuevoEditar = this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_GASTOS, VariablesPermisos.OPERACION_NUEVO_EDITAR);
		
		/*Si no tiene permisos de Nuevo Cerrmamos la ventana y mostramos mensaje*/
		if(!permisoNuevoEditar)
		{
			mainView.cerrarVentana();
			mainView.mostrarMensaje(Variables.USUSARIO_SIN_PERMISOS);
		}
		
		this.enableBotonAceptar();
		this.disableBotonLectura();
		
		/*Seteamos validaciones en nuevo, cuando es editar
		 * solamente cuando apreta el boton editar*/
		this.setearValidaciones(true);
		
		/*Como es en operacion nuevo, dejamos todos los campos editabls*/
		this.readOnlyFields(false);
		
		this.enableCombos();
	}
	
	private void enableCombos(){
		this.comboMoneda.setEnabled(true);
		//this.comboImpuesto.setEnabled(true);
		
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
		
		this.fecDoc.setReadOnly(false);
		this.fecValor.setReadOnly(false);
		this.tcMov.setReadOnly(false);
		this.impImpuMn.setReadOnly(false);
		this.impImpuMo.setReadOnly(false);
		this.impTotMn.setReadOnly(false);
		this.impTtotMo.setReadOnly(false);
		this.referencia.setReadOnly(false);
		
		this.codProceso.setReadOnly(false);
		this.descProceso.setReadOnly(false);
		this.codTitular.setReadOnly(false);
		this.nomTitular.setReadOnly(false);
		this.codRubro.setReadOnly(false);
		this.nomRubro.setReadOnly(false);
		this.codCuenta.setReadOnly(false);
		this.nomCuenta.setReadOnly(false);
		this.codImpuesto.setReadOnly(false);
		this.nomImpuesto.setReadOnly(false);
		this.porcentajeImpuesto.setReadOnly(false);
		this.nroDocum.setReadOnly(false);
		
		this.comboMoneda.setEnabled(true);
		
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
		
		this.btnBuscarProceso.setEnabled(false);
		this.btnBuscarProceso.setVisible(false);
		
		this.btnBuscarRubro.setEnabled(false);
		this.btnBuscarRubro.setVisible(false);
		
		this.btnBuscarCuenta.setEnabled(false);
		this.btnBuscarCuenta.setVisible(false);
		
		this.btnBuscarEmpleado.setEnabled(false);
		this.btnBuscarEmpleado.setVisible(false);
		
		this.btnBuscarImpuesto.setEnabled(false);
		this.btnBuscarImpuesto.setVisible(false);
		
	}
	
	/**
	 * Habilitamos el boton aceptar
	 *
	 */
	private void enableBotonAceptar()
	{
		this.aceptar.setEnabled(true);
		this.aceptar.setVisible(true);
		
		if(this.comboSeleccion.getValue().equals("Proceso")){
			this.btnBuscarProceso.setEnabled(true);
			this.btnBuscarProceso.setVisible(true);
		}
		
		
		this.btnBuscarRubro.setEnabled(true);
		this.btnBuscarRubro.setVisible(true);
		
		this.btnBuscarCuenta.setEnabled(true);
		this.btnBuscarCuenta.setVisible(true);
		
		this.btnBuscarImpuesto.setEnabled(true);
		this.btnBuscarImpuesto.setVisible(true);
		
		this.btnBuscarEmpleado.setEnabled(true);
		this.btnBuscarEmpleado.setVisible(true);
		
	}
	
	/**
	 * Dejamos todos los Fields readonly o no,
	 * dado el boolenao pasado por parametro
	 *
	 */
	private void readOnlyFields(boolean setear)
	{
		this.fecDoc.setReadOnly(setear);
		this.fecValor.setReadOnly(setear);
		this.tcMov.setReadOnly(setear);
		this.impImpuMn.setReadOnly(setear);
		this.impImpuMo.setReadOnly(setear);
		this.impTotMn.setReadOnly(setear);
		this.impTtotMo.setReadOnly(setear);
		this.referencia.setReadOnly(setear);
		
		this.codProceso.setEnabled(false);
		this.descProceso.setEnabled(false);
		this.codTitular.setEnabled(false);
		this.nomTitular.setEnabled(false);
		this.codRubro.setEnabled(false);
		this.nomRubro.setEnabled(false);
		this.codCuenta.setEnabled(false);
		this.nomCuenta.setEnabled(false);
		this.codImpuesto.setEnabled(false);
		this.nomImpuesto.setEnabled(false);
		this.porcentajeImpuesto.setEnabled(false);
		this.comboMoneda.setEnabled(false);
		this.nroDocum.setEnabled(false);
		
		
	}
	
	/**
	 * Seteamos todos las validaciones de los fields
	 * del formulario
	 *
	 */
	private void agregarFieldsValidaciones()
	{
		
		this.referencia.addValidator(
				new StringLengthValidator(
						"50 caracteres m�ximo", 0, 50, true));
		
//        this.referencia.addValidator(
//                new StringLengthValidator(
//                     " 50 caracteres m�ximo", 0, 50, true));
        
//        this.codCuenta.addValidator(
//                new StringLengthValidator(
//                        " 20 caracteres m�ximo", 0, 100, true));
//        this.codRubro.addValidator(
//                new StringLengthValidator(
//                        " 100 caracteres m�ximo", 0, 100, true));
//        
//        this.comboImpuesto.addValidator(
//                new StringLengthValidator(
//                        " 100 caracteres m�ximo", 0, 100, true));
//        
//        this.comboMoneda.addValidator(
//                new StringLengthValidator(
//                        " 100 caracteres m�ximo", 0, 100, true));
        
        
	}
	
	/**
	 * Nos retorna true si los campos
	 * son v�lidos, se debe invocar antes
	 * de consumir al controlador
	 *
	 */
	private boolean fieldsValidos()
	{
		boolean valido = false;
		//Agregamos validaciones a los campos para luego controlarlos
		this.agregarFieldsValidaciones();
				
		try
		{
//			if(this.fecDoc.isValid() && this.fecValor.isValid() 
//					&& this.codProceso.isValid() && this.codGasto.isValid()
//					&& this.codCuenta.isValid() && this.codRubro.isValid()
//					&& this.comboMoneda.isValid() && this.descripcionImpuesto.isValid()
//					&& this.tcMov.isValid() && this.impMn.isValid() && this.impMo.isValid())
				valido = true;
			
		}catch(Exception e)
		{
			Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
		}
		
		return valido;
	}
	
	public void cerrarVentana()	{
		UI.getCurrent().removeWindow(sub);
	}

	public void setInfo(Object datos) {
		// TODO Auto-generated method stub
		if(datos instanceof ProcesoVO){
			ProcesoVO procesoVO = (ProcesoVO) datos;
			this.codProceso.setValue(String.valueOf(procesoVO.getCodigo()));
			this.descProceso.setValue(procesoVO.getDescripcion());
			this.codTitular.setValue((procesoVO.getCodCliente()));
			this.nomTitular.setValue(procesoVO.getNomCliente());
		}
		if(datos instanceof RubroVO){
			RubroVO rubroVO = (RubroVO) datos;
			this.codRubro.setValue(rubroVO.getcodRubro());
			this.nomRubro.setValue(rubroVO.getDescripcion());
 			this.nomImpuesto.setValue(rubroVO.getDescripcionImpuesto());
			this.codImpuesto.setValue(rubroVO.getCodigoImpuesto());
			this.porcentajeImpuesto.setValue(String.valueOf(rubroVO.getPorcentajeImpuesto()));
		}
		
		if(datos instanceof CuentaVO){
			CuentaVO cuentaVO = (CuentaVO) datos;
			this.codCuenta.setValue(cuentaVO.getCodCuenta());
			this.nomCuenta.setValue(cuentaVO.getDescripcion());
			
		}
		
		if(datos instanceof ImpuestoVO){
			ImpuestoVO impuestoVO = (ImpuestoVO) datos;
			this.codImpuesto.setValue(impuestoVO.getcodImpuesto());
			this.nomImpuesto.setValue(impuestoVO.getDescripcion());
			this.porcentajeImpuesto.setValue(String.valueOf(impuestoVO.getPorcentaje()));
			
		}
		
		if(datos instanceof FuncionarioVO){
			FuncionarioVO funcionarioVO = (FuncionarioVO) datos;
			this.codTitular.setValue(String.valueOf(funcionarioVO.getCodigo()));
			this.nomTitular.setValue(funcionarioVO.getNombre());
			
		}
		
	}
	
	public void inicializarComboMoneda(String cod){
		
		BeanItemContainer<MonedaVO> monedasObj = new BeanItemContainer<MonedaVO>(MonedaVO.class);
		MonedaVO moneda = new MonedaVO();
		ArrayList<MonedaVO> lstMonedas = new ArrayList<MonedaVO>();
		
		try {
			permisoAux = 
					new UsuarioPermisosVO(this.permisos.getCodEmp(),
							this.permisos.getUsuario(),
							VariablesPermisos.FORMULARIO_GASTOS,
							VariablesPermisos.OPERACION_NUEVO_EDITAR);
			lstMonedas = this.controlador.getMonedas(permisoAux);
			
		} catch (ObteniendoMonedaException | InicializandoException | ConexionException | ObteniendoPermisosException | NoTienePermisosException e) {

			Mensajes.mostrarMensajeError(e.getMessage());
		}
		
		for (MonedaVO monedaVO : lstMonedas) {
			
			monedasObj.addBean(monedaVO);
			
			if(cod != null){
				if(cod.equals(monedaVO.getCodMoneda())){
					moneda = monedaVO;
				}
			}
		}
		
		this.comboMoneda.setContainerDataSource(monedasObj);
		this.comboMoneda.setItemCaptionPropertyId("descripcion");
		this.comboMoneda.setValue(moneda);
	}
	
	public void inicializarComboSeleccion(String cod){
		
		if(cod.equals("IngGastoProceso")){
			this.comboSeleccion.setValue("Proceso");
			this.inicializoProceso();
		}
		else if(cod.equals("IngGastoEmpleado")){
			this.comboSeleccion.setValue("Empleado");
			this.inicializoEmpleado();
		}
		else if(cod.equals("IngGastoOficina")){
			this.comboSeleccion.setValue("Oficina");
			this.inicializoOficina();
		}
		
	}
	
	public void inicializoProceso(){
		this.proceso.setVisible(true);
		this.cliente.setCaption("Cliente");
		this.btnBuscarEmpleado.setVisible(false);
	}
	
	public void inicializoEmpleado(){
		this.proceso.setVisible(false);
		this.cliente.setCaption("Empleado");
		this.btnBuscarEmpleado.setVisible(true);
		this.mainView.setSub();
	}
	
	public void inicializoOficina(){
		this.proceso.setVisible(false);
		this.cliente.setVisible(false);
		this.mainView.setSub();
	}
}