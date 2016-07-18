package com.vista;

import java.sql.Date;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;

import org.json.simple.JSONObject;

import com.controladores.GrupoControlador;
import com.excepciones.ConexionException;
import com.excepciones.InicializandoException;
import com.excepciones.grupos.ExisteGrupoException;
import com.excepciones.grupos.InsertandoGrupoException;
import com.excepciones.grupos.MemberGrupoException;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.event.SelectionEvent;
import com.vaadin.event.SelectionEvent.SelectionListener;
import com.vaadin.server.Page;
import com.vaadin.server.Page.Styles;
import com.vaadin.shared.Position;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.valueObject.CotizacionVO;
import com.valueObject.FormularioVO;
import com.valueObject.GrupoVO;



public class GrupoViewExtended extends GrupoView {

	private BeanFieldGroup<GrupoVO> fieldGroup;
	private ArrayList<FormularioVO> lstFormsVO; /*Lista de Formularios del Grupo*/
	private ArrayList<FormularioVO> lstFormsAgregar; /*Lista de Formularios a agregar*/
	private GrupoControlador controlador;
	private String operacion;
	private GruposPanelExtended mainView;
	BeanItemContainer<FormularioVO> container;
	private FormularioVO formSelecccionado; /*Variable utilizada cuando se selecciona
	 										  un formulario, para poder quitarlo de la lista*/
	
	/**
	 * Constructor del formulario, conInfo indica
	 * si hay que cargarle la info
	 *
	 */
	@SuppressWarnings("unchecked")
	public GrupoViewExtended(String opera, GruposPanelExtended main){
	
	
	this.operacion = opera;
	this.mainView = main;
	
	/*Esta lista es utilizada solamente para los formularios nuevos
	 * agregados*/
	this.lstFormsAgregar = new ArrayList<FormularioVO>();
	
	this.inicializarForm();
	
	/*Inicializamos listener de boton aceptar*/
	this.aceptar.addClickListener(click -> {
			
		try {
			
			/*Validamos los campos antes de invocar al controlador*/
			if(this.fieldsValidos())
			{
								
				GrupoVO grupoVO = new GrupoVO();				
				grupoVO.setCodGrupo(codGrupo.getValue().trim());
				grupoVO.setNomGrupo(nomGrupo.getValue().trim());
				
				grupoVO.setOperacion(operacion);
				grupoVO.setActivo(activo.getValue());
				grupoVO.setUsuarioMod(getSession().getAttribute("usuario").toString());
				
				/*Si hay algun formulario nuevo agregado
				 * lo agregamos a la lista del formulario*/
				if(this.lstFormsAgregar.size() > 0)
				{
					for (FormularioVO f : this.lstFormsAgregar) {
						this.lstFormsVO.add(f);
					}
				}
					
				grupoVO.setLstFormularios(this.lstFormsVO);
				
				if(this.operacion.equals(Variables.OPERACION_NUEVO))	
				{	
	
					this.controlador.insertarGrupo(grupoVO);
					
					this.mainView.actulaizarGrilla(grupoVO);
					
					Mensajes.mostrarMensajeOK("Se ha guardado el Grupo");
				
				}else if(this.operacion.equals(Variables.OPERACION_EDITAR))
				{
					/*VER DE IMPLEMENTAR PARA EDITAR BORRO TODO E INSERTO NUEVAMENTE*/
					this.controlador.editarGrupo(grupoVO);
					
					this.mainView.actulaizarGrilla(grupoVO);
					
					Mensajes.mostrarMensajeOK("Se ha modificado el Grupo");
					
				}
				
				/*Mensaje de que se han guardado los cambios 
				 * oculatamos el boton de guardar y mostramos el de editar*/
							
				this.enableBotonEditar();
				this.disableBotonAceptar();
				this.disableBotonAgregarQuitar();
			
			}
			else /*Si los campos no son válidos mostramos warning*/
			{
				Mensajes.mostrarMensajeWarning(Variables.WARNING_CAMPOS_NO_VALIDOS);
			}
				
			} catch (InsertandoGrupoException| MemberGrupoException| ExisteGrupoException| InicializandoException| ConexionException e) {
				
				Mensajes.mostrarMensajeError(e.getMessage());
				
			}catch(Exception e)
			{
				Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
			}
		});
	
	/*Inicalizamos listener para boton de Editar*/
		this.btnEditar.addClickListener(click -> {
				
		try {
			
			//this.codGrupo.setReadOnly(true);
			
			/*Inicializamos el Form en modo Edicion*/
			this.iniFormEditar();
			
			
	
			}catch(Exception e)
			{
				Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
			}
		});
		
		/*Inicalizamos listener para boton de Agregar Formulario*/
			this.btnAgregar.addClickListener(click -> {
					
			try {
				
				GrupoViewAgregarFormularioExtended form = new GrupoViewAgregarFormularioExtended(this);
				
				MySub sub = new MySub();
				sub.setVista(form);
				sub.setWidth("50%");
				sub.setHeight("50%");
				sub.center();
				
				String codGrupo;/*Codigo del grupo para obtener los forms del mismo*/
				
				/*Obtenemos los formularios que no estan en el grupo
				 * para mostrarlos en la grilla para seleccionar*/
				if(this.operacion.equals(Variables.OPERACION_NUEVO) )
				{
					/*Si la operacion es nuevo, ponemos el  codGrupo vacio
					 * asi nos trae todos los grupos disponibles*/
					codGrupo = "";
				}
				else 
				{
					/*Si es operacion Editar tomamos el codGrupo de el fieldGroup*/
					codGrupo = fieldGroup.getItemDataSource().getBean().getCodGrupo();
				}
				
				ArrayList<FormularioVO> lstForms = this.controlador.getFormulariosNoGrupo(codGrupo);
				form.setGrillaForms(lstForms);
				
				UI.getCurrent().addWindow(sub);

				}catch(Exception e)
				{
					Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
				}
			});
			
			/*Inicalizamos listener para boton de Quitar*/
		this.btnQuitar.addClickListener(click -> {
				
			boolean esta = false;	
	
			try {
				
				/*Verificamos que haya un formulario seleccionado para
				 * eliminar*/
				if(formSelecccionado != null)
				{

					/*Recorremos los formularios del grupo
					 * y buscamos el seleccionarlo para quitarlo*/
					int i = 0;
					while(i < lstFormsVO.size() && !esta)
					{
						if(lstFormsVO.get(i).getCodigo().equals(formSelecccionado.getCodigo()))
						{
							/*Quitamos el formulario seleccionado de la lista*/
							lstFormsVO.remove(lstFormsVO.get(i));
							
							esta = true;
						}

						i++;
					}
					
					/*Si lo encontro en la grilla*/
					if(esta)
					{
						/*Actualizamos el container y la grilla*/
						container.removeAllItems();
						container.addAll(lstFormsVO);
						lstFormularios.setContainerDataSource(container);
					}
					
				}
				else /*De lo contrario mostramos mensaje que debe selcionar un formulario*/
				{
					Mensajes.mostrarMensajeError("Debe seleccionar un formulario para quitar");
				}
		
				}catch(Exception e)
				{
					Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
				}
			});
			
			/*Inicializamos el listener de la grilla de formularios*/
			lstFormularios.addSelectionListener(new SelectionListener() {
				
			    @Override
			    public void select(SelectionEvent event) {
			       
			    	try{
			    	BeanItem<FormularioVO> item = container.getItem(lstFormularios.getSelectedRow());
			    	formSelecccionado = item.getBean(); /*Seteamos el formulario
			    	 									seleccionado para poder quitarlo*/

						  
			    	}catch(Exception e)
			    	{
			    		Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
			    	}
			      
			    }
			});
			
			
	///////////////////
	}

	public  void inicializarForm(){
		
		this.controlador = new GrupoControlador(this);
					
		this.fieldGroup =  new BeanFieldGroup<GrupoVO>(GrupoVO.class);
		
		//Seteamos info del form si es requerido
		if(fieldGroup != null)
			fieldGroup.buildAndBindMemberFields(this);
		
		/*Seteamos las validaciones de los fields*/
		this.agregarFieldsValidaciones();
		
		/*SI LA OPERACION NO ES NUEVO, OCULTAMOS BOTON ACEPTAR*/
		if(this.operacion.equals(Variables.OPERACION_NUEVO))
		{
			/*Inicializamos al formulario como nuevo*/
			this.iniFormNuevo();
	
		}else if(this.operacion.equals(Variables.OPERACION_LECTURA))
		{
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
		
		this.codGrupo.setRequired(setear);
		this.codGrupo.setRequiredError("Es requerido");
		
		this.nomGrupo.setRequired(setear);
		this.nomGrupo.setRequiredError("Es requerido");
	}
	
	/**
	 * Dado un item GrupoVO seteamos la info del formulario
	 *
	 */
	public void setDataSourceFormulario(BeanItem<GrupoVO> item)
	{
		this.fieldGroup.setItemDataSource(item);
		
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
		this.enableBotonEditar();
		this.disableBotonAceptar();
		this.disableBotonAgregarQuitar();
		
		/*No mostramos las validaciones*/
		this.setearValidaciones(false);
		
		/*Dejamos todods los campos readonly*/
		this.readOnlyFields(true);
		
		
		/*Seteamos la grilla con los formularios*/
		this.container = 
				new BeanItemContainer<FormularioVO>(FormularioVO.class);
		
		
		if(this.lstFormsVO != null)
		{
			for (FormularioVO formVO : this.lstFormsVO) {
				container.addBean(formVO);
			}
		}
		
		
		lstFormularios.setContainerDataSource(container);
				
	}
	
	/**
	 * Seteamos el formulario en modo Edicion
	 *
	 */
	private void iniFormEditar()
	{
		/*Seteamos el form en editar*/
		this.operacion = Variables.OPERACION_EDITAR;
		
		/*Oculatamos Editar y mostramos el de guardar y de agregar formularios*/
		this.enableBotonAceptar();
		this.disableBotonEditar();
		this.enableBotonAgregarQuitar();
		
		/*Dejamos los textfields que se pueden editar
		 * en readonly = false asi  se pueden editar*/
		this.setearFieldsEditar();
		
		/*Seteamos las validaciones*/
		this.setearValidaciones(true);
	}
	
	/**
	 * Seteamos el formulario en modo Nuevo
	 *
	 */
	private void iniFormNuevo()
	{
		this.enableBotonAceptar();
		this.disableBotonEditar();
		this.enableBotonAgregarQuitar();
		this.lstFormsAgregar = new ArrayList<FormularioVO>();
		this.lstFormsVO = new ArrayList<FormularioVO>();
		
		/*Inicializamos el container*/
		this.container = 
				new BeanItemContainer<FormularioVO>(FormularioVO.class);
		
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
		this.nomGrupo.setReadOnly(false);
	}
	
	/**
	 * Deshabilitamos el boton editar
	 *
	 */
	private void disableBotonEditar()
	{
		this.btnEditar.setEnabled(false);
		this.btnEditar.setVisible(false);
	}
	
	/**
	 * Habilitamos el boton editar
	 *
	 */
	private void enableBotonEditar()
	{
		this.btnEditar.setEnabled(true);
		this.btnEditar.setVisible(true);
		
	}
	
	/**
	 * Habilitamos el boton editar
	 *
	 */
	private void enableBotonAgregarQuitar()
	{
		this.btnAgregar.setEnabled(true);
		this.btnAgregar.setVisible(true);
		
		this.btnQuitar.setEnabled(true);
		this.btnQuitar.setVisible(true);
		
	}
	
	/**
	 * Deshabilitamos el boton editar
	 *
	 */
	private void disableBotonAgregarQuitar()
	{
		this.btnAgregar.setEnabled(false);
		this.btnAgregar.setVisible(false);
		
		this.btnQuitar.setEnabled(false);
		this.btnQuitar.setVisible(false);
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
	 * Dejamos todos los Fields readonly o no,
	 * dado el boolenao pasado por parametro
	 *
	 */
	private void readOnlyFields(boolean setear)
	{
		this.codGrupo.setReadOnly(setear);
		this.nomGrupo.setReadOnly(setear);
				
	}
	
	/**
	 * Seteamos todos las validaciones de los fields
	 * del formulario
	 *
	 */
	private void agregarFieldsValidaciones()
	{
        this.codGrupo.addValidator(
                new StringLengthValidator(
                     " 20 caracteres máximo", 1, 20, false));
        
        this.nomGrupo.addValidator(
                new StringLengthValidator(
                        " 255 caracteres máximo", 1, 255, false));
        
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
		
		try
		{
			if(this.codGrupo.isValid() && this.nomGrupo.isValid())
				valido = true;
			
		}catch(Exception e)
		{
			Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
		}
		
		return valido;
	}

	/**
	 * Seteamos la lista de los formularios para mostrarlos
	 * en la grilla
	 */
	public void setLstFormularios(ArrayList<FormularioVO> lstForms)
	{
		this.lstFormsVO = lstForms;
		
		/*Seteamos la grilla con los formularios*/
		this.container = 
				new BeanItemContainer<FormularioVO>(FormularioVO.class);
		
		
		if(this.lstFormsVO != null)
		{
			for (FormularioVO formVO : this.lstFormsVO) {
				container.addBean(formVO);
			}
		}

		lstFormularios.setContainerDataSource(container);
		
	}
	
	/**
	 * Agregar Formularios seleccionados
	 */
	public void agregarFormulariosSeleccionados(ArrayList<FormularioVO> lstForms)
	{

        FormularioVO bean = new FormularioVO();
        
		if(lstForms.size() > 0)
		{
			for (FormularioVO formVO : lstForms) {
				
				/*Hacemos un nuevo objeto por bug de vaadin
				 * de lo contrario no refresca la grilla*/
				bean = new FormularioVO();
		        bean.setCodFormulario(formVO.getCodigo());
		        bean.setNomFormulario(formVO.getNombre());
				
		        /*Por ESTO*/
			//	this.lstFormsVO.add(formVO);
		        this.lstFormsAgregar.add(formVO);
				
				this.container.addBean(bean);
			}
		}
		
		lstFormularios.setContainerDataSource(container);

	}

}



