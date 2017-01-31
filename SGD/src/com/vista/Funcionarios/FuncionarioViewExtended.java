package com.vista.Funcionarios;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.controladores.FuncionarioControlador;
import com.excepciones.ConexionException;
import com.excepciones.InicializandoException;
import com.excepciones.NoTienePermisosException;
import com.excepciones.ObteniendoPermisosException;
import com.excepciones.Documentos.ObteniendoDocumentosException;
import com.excepciones.Impuestos.ObteniendoImpuestosException;
import com.excepciones.funcionarios.ExisteFuncionarioDocumetnoException;
import com.excepciones.funcionarios.ExisteFuncionarioException;
import com.excepciones.funcionarios.InsertendoFuncionarioException;
import com.excepciones.funcionarios.ModificandoFuncionarioException;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.UI;
import com.valueObject.DocumDGIVO;
import com.valueObject.FuncionarioVO;
import com.valueObject.UsuarioPermisosVO;
import com.vista.BusquedaViewExtended;
import com.vista.IBusqueda;
import com.vista.Mensajes;
import com.vista.MySub;
import com.vista.PermisosUsuario;
import com.vista.Variables;
import com.vista.VariablesPermisos;

public class FuncionarioViewExtended extends FuncionarioView implements IBusqueda{

	private BeanFieldGroup<FuncionarioVO> fieldGroup;
	private FuncionarioControlador controlador;
	private String operacion;
	private FuncionariosPanelExtended mainView;
	MySub sub;
	private PermisosUsuario permisos;
	
	/**
	 * Constructor del formulario
	 * Con operación y la vista que lo llamo
	 */
	public FuncionarioViewExtended(String opera, FuncionariosPanelExtended main){
		
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
									
					FuncionarioVO funcionarioVO;
										

					/*Inicializamos VO de permisos para el usuario, formulario y operacion
					 * para confirmar los permisos del usuario*/
					UsuarioPermisosVO permisoAux = 
							new UsuarioPermisosVO(this.permisos.getCodEmp(),
									this.permisos.getUsuario(),
									VariablesPermisos.FORMULARIO_FUNCIONARIOS,
									VariablesPermisos.OPERACION_NUEVO_EDITAR);
					
					
										
					if(this.operacion.equals(Variables.OPERACION_NUEVO)) {	
						
						/*Obtenemos los datos del Funcionario de los fields del formulario*/
						funcionarioVO = this.obtenerDatosFuncionarioFormulario(Variables.OPERACION_NUEVO);
						
						
						int codigo = controlador.insertarFuncionario(funcionarioVO, permisoAux);
						funcionarioVO.setCodigo(codigo);
						this.mainView.actulaizarGrilla(funcionarioVO);
						
						Mensajes.mostrarMensajeOK("Se ha guardado el funcionario");
						main.cerrarVentana();
					
					}
					else if(this.operacion.equals(Variables.OPERACION_EDITAR))	{
						
						/*Obrenemos los campos del BeanItem*/
						funcionarioVO = this.obtenerDatosFuncionarioFormulario(Variables.OPERACION_EDITAR);
						
					
						this.controlador.modificarFuncionario(funcionarioVO, permisoAux);
							
						
						this.mainView.actulaizarGrilla(funcionarioVO);
						
						Mensajes.mostrarMensajeOK("Se ha modificado funcionario");
						main.cerrarVentana();
						
					}
				}
				else /*Si los campos no son válidos mostramos warning*/
				{
					Mensajes.mostrarMensajeWarning(Variables.WARNING_CAMPOS_NO_VALIDOS);
				}
					
				} 
				catch (ConexionException| InicializandoException| InsertendoFuncionarioException
						| ModificandoFuncionarioException| ExisteFuncionarioException
						| ExisteFuncionarioDocumetnoException|ObteniendoPermisosException 
						| NoTienePermisosException  e) {
					
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
			
			this.btnBuscarDoc.addClickListener(click -> {
				
				//ImpuestosHelpExtended form = new ImpuestosHelpExtended(this);
				
				BusquedaViewExtended form = new BusquedaViewExtended(this, new DocumDGIVO());
				ArrayList<Object> lst = new ArrayList<Object>();
				ArrayList<DocumDGIVO> lstDocumDgi = new ArrayList<DocumDGIVO>();
				
				try {
					
					lstDocumDgi = this.controlador.obtnerDocumentosDgi();
				
				Object obj;
				for (DocumDGIVO i: lstDocumDgi) {
					obj = new Object();
					obj = (Object)i;
					lst.add(obj);
				}
					
					form.inicializarGrilla(lst);
					
				
				} catch (ObteniendoDocumentosException| ObteniendoImpuestosException| ConexionException| InicializandoException e) {
					
					Mensajes.mostrarMensajeError(e.getMessage());
				}
				
				sub = new MySub("60%", "60%" );
				sub.setModal(true);
				sub.center();
				sub.setModal(true);
				sub.setVista(form);
				sub.center();
				sub.setDraggable(true);
				UI.getCurrent().addWindow(sub);
				
			});
		
	}

	public  void inicializarForm(){
		
		this.controlador = new FuncionarioControlador();
					
		this.fieldGroup =  new BeanFieldGroup<FuncionarioVO>(FuncionarioVO.class);
		
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
		
		
		this.codigo.setRequired(setear);
		this.codigo.setRequiredError("Es requerido");
		
		this.nombre.setRequired(setear);
		this.nombre.setRequiredError("Es requerido");
		
		this.codigoDoc.setRequired(setear);
		this.codigoDoc.setRequiredError("Es requerido");
		
		this.nombreDoc.setRequired(setear);
		this.nombreDoc.setRequiredError("Es requerido");
		
		this.numeroDoc.setRequired(setear);
		this.numeroDoc.setRequiredError("Es requerido");
				
				
	}
	
	/**
	 * Dado un item ImpuestoVO seteamos la info del formulario
	 *
	 */
	public void setDataSourceFormulario(BeanItem<FuncionarioVO> item)
	{
		this.fieldGroup.setItemDataSource(item);
		
		FuncionarioVO funcionarioVO = new FuncionarioVO();
		funcionarioVO = fieldGroup.getItemDataSource().getBean();
		String fecha = new SimpleDateFormat("dd/MM/yyyy").format(funcionarioVO.getFechaMod());
		
		
		auditoria.setDescription(
			  
			"Usuario: " + funcionarioVO.getUsuarioMod() + "<br>" +
		    "Fecha: " + fecha + "<br>" +
		    "Operación: " + funcionarioVO.getOperacion());
		
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
		
		/*Deshabilitamos boton de busqueda de documento*/
		this.disableBotonBusquedaDoc();
		
		/*Habilitamos el boton de editar,
		 * deshabilitamos botn aceptar*/
		this.enableBotonesLectura();
		this.disableBotonAceptar();
		
		/*No mostramos las validaciones*/
		this.setearValidaciones(false);
		
		/*Dejamos todods los campos readonly*/
		this.readOnlyFields(true);
		
		/*Dejamos como read*/
		
	}
	
	/**
	 * Seteamos el formulario en modo Edicion
	 *
	 */
	private void iniFormEditar()
	{
		/*Seteamos el form en editar*/
		this.operacion = Variables.OPERACION_EDITAR;
		
		/*Habilitamos boton de busqueda de documento*/
		this.enableBotonBusquedaDoc();
		
		/*Verificamos que tenga permisos*/
		boolean permisoNuevoEditar = this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_FUNCIONARIOS, VariablesPermisos.OPERACION_NUEVO_EDITAR);
		
		if(permisoNuevoEditar){
			
			/*Oculatamos Editar y mostramos el de guardar y de agregar formularios*/
			this.enableBotonAceptar();
			this.disableBotonLectura();

			/*Dejamos los textfields que se pueden editar
			 * en readonly = false asi  se pueden editar*/
			this.setearFieldsEditar();
			
			/*Seteamos las validaciones*/
			this.setearValidaciones(true);
			
			/**/
			
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
		/*Habilitamos boton busquedad de documento*/
		this.enableBotonBusquedaDoc();
		
		/*Chequeamos si tiene permiso de editar*/
		boolean permisoNuevoEditar = this.permisos.permisoEnFormulaior(VariablesPermisos.FORMULARIO_FUNCIONARIOS, VariablesPermisos.OPERACION_NUEVO_EDITAR);
		
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
		
		/*Ocultamos el Codigo del funcionario ya que es autogenerado*/
		this.codigo.setVisible(false);
		this.codigo.setRequired(false); /*Lo dejamos no requerido ya que es autogenerado*/
		
		/*Aumentamos el tamaño del texfield del Nombre del funcionario*/
		this.nombre.setWidth("305px");
		
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
		this.nombreDoc.setReadOnly(false);
		this.numeroDoc.setReadOnly(false);
		this.nombre.setReadOnly(false);
		this.tel.setReadOnly(false);
		this.direccion.setReadOnly(false);
		this.mail.setReadOnly(false);
		this.activo.setReadOnly(false);
		
		/*Codigo y nombre de documento no los dejamos editar*/
		this.codigoDoc.setReadOnly(true);
		this.nombreDoc.setReadOnly(true);
		
		/*El codigo del funcionario tampodo lo dejamos editar*/
		this.codigo.setReadOnly(true); 
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
	}
	
	/**
	 * Habilitamos el boton aceptar
	 *
	 */
	private void enableBotonAceptar()
	{
		this.aceptar.setEnabled(true);
		this.aceptar.setVisible(true);
		
	}
	
	/**
	 * Deshabilitamos el boton busqueda documento
	 *
	 */
	private void disableBotonBusquedaDoc()
	{
		this.btnBuscarDoc.setEnabled(false);
		this.btnBuscarDoc.setVisible(false);
	}
	
	/**
	 * habilitamos el boton busqueda documento
	 *
	 */
	private void enableBotonBusquedaDoc()
	{
		this.btnBuscarDoc.setEnabled(true);
		this.btnBuscarDoc.setVisible(true);
	}
	
	
	/**
	 * Dejamos todos los Fields readonly o no,
	 * dado el boolenao pasado por parametro
	 *
	 */
	private void readOnlyFields(boolean setear)
	{
		this.codigo.setReadOnly(true); /*Codigo siempre true*/
		this.nombreDoc.setReadOnly(true); /*Nombre doc siempre true*/
		this.numeroDoc.setReadOnly(setear);
		this.nombre.setReadOnly(setear);
		this.tel.setReadOnly(setear);
		this.direccion.setReadOnly(setear);
		this.mail.setReadOnly(setear);
		this.activo.setReadOnly(setear);
				
	}
	
	/**
	 * Seteamos todos las validaciones de los fields
	 * del formulario
	 *
	 */
	private void agregarFieldsValidaciones()
	{
        this.nombre.addValidator(
                new StringLengthValidator(
                     " 45 caracteres máximo", 1, 45, false));
        
        this.numeroDoc.addValidator(
                new StringLengthValidator(
                        " 20 caracteres máximo", 1, 20, false));
        
        this.tel.addValidator(
                new StringLengthValidator(
                        " 20 caracteres máximo", 0, 20, false));
        
        this.direccion.addValidator(
                new StringLengthValidator(
                        " 100 caracteres máximo", 0, 100, false));
        
        this.mail.addValidator(
                new StringLengthValidator(
                        " 100 caracteres máximo", 0, 100, false));
        
        this.mail.addValidator(
                new EmailValidator(
                        " mail no válido"));
        
        
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
				
		try
		{
			boolean a = this.codigo.isValid();
			boolean vc = this.codigoDoc.isValid();
			boolean c = this.nombreDoc.isValid();
			boolean x = this.numeroDoc.isValid();
			boolean za = this.nombre.isValid();
			boolean q = this.tel.isValid();
			boolean ty = this.direccion.isValid();
			boolean yy = this.mail.isValid();
			
			if(this.codigo.isValid() &&
				//this.codigoDoc.isValid() &&
				this.nombreDoc.isValid() &&
				this.numeroDoc.isValid() &&
				this.nombre.isValid() &&
				this.tel.isValid() &&
				this.direccion.isValid() &&
				this.mail.isValid() 
			){
				valido = true;
			}
			
			
			
		}catch(Exception e)
		{
			Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
		}
		
		return valido;
	}
	
	/**
	 * Nos retorna retorna un funcionarioVO
	 * nuevo con todos los datos ingresados en el formulario
	 * Le pasamos la operacion por parametro (Nuevo o Editar)
	 */
	private FuncionarioVO obtenerDatosFuncionarioFormulario(String operacion)
	{
		
		
		FuncionarioVO funcionario = new FuncionarioVO();
		int codigo = 0;
		
		try
		{
			if(operacion.equals(Variables.OPERACION_EDITAR))
				codigo = Integer.parseInt(this.codigo.getValue().toString().trim());
			
			String nombre = this.nombre.getValue().toString().trim();
			String tel = this.tel.getValue().toString().trim();
			String direccion = this.direccion.getValue().toString().trim();
			String mail = this.mail.getValue().toString().trim();
			boolean activo = this.activo.getValue().booleanValue();
			String codigoDoc = this.codigoDoc.getValue().toString().trim();
			
					
			
			String nombreDoc = this.nombreDoc.getValue().toString().trim();
			String numeroDoc = this.numeroDoc.getValue().toString().trim();
			
			String usuarioMod = (String)VaadinService.getCurrentRequest().getWrappedSession().getAttribute("usuario"); 
			//String operacion = Variables.OPERACION_NUEVO;
			
			funcionario.setNombre(nombre);
			funcionario.setTel(tel);
			funcionario.setDireccion(direccion);
			funcionario.setMail(mail);
			funcionario.setActivo(activo);
			funcionario.setCodigoDoc(codigoDoc);
			funcionario.setNombreDoc(nombreDoc);
			funcionario.setNumeroDoc(numeroDoc);
			
			funcionario.setFechaMod(new Timestamp(System.currentTimeMillis()));
			
			funcionario.setUsuarioMod(usuarioMod);
			funcionario.setOperacion(operacion);
			
			if(operacion.equals(Variables.OPERACION_EDITAR))
				funcionario.setCodigo(codigo);
		
		}catch(Exception e){
			
			Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
		}
		
		
		return funcionario;
	}

	@Override
	public void setInfo(Object datos) {
		
		if(datos instanceof DocumDGIVO){
			DocumDGIVO documDgi = (DocumDGIVO) datos;
			
			/*Seteamos readOnly en false para que no de error al querer modificarlos*/
			this.codigoDoc.setReadOnly(false);
			this.nombreDoc.setReadOnly(false);
			
			this.codigoDoc.setValue(documDgi.getcodDocumento());
			this.nombreDoc.setValue(documDgi.getdescripcion());
			
			/*Volvemos a setearlos como readOnly*/
			this.codigoDoc.setReadOnly(true);
			this.nombreDoc.setReadOnly(true);
		}
		
	}

	@Override
	public void cerrarVentana() {
		
		UI.getCurrent().removeWindow(sub);
	}

	@Override
	public void setInfoLst(ArrayList<Object> lstDatos) {
		// TODO Auto-generated method stub
		
	}
}
