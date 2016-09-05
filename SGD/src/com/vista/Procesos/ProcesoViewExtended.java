package com.vista.Procesos;

import com.controladores.ProcesoControlador;
import com.excepciones.ConexionException;
import com.excepciones.ErrorInesperadoException;
import com.excepciones.InicializandoException;
import com.excepciones.NoTienePermisosException;
import com.excepciones.ObteniendoPermisosException;
import com.excepciones.Monedas.ExisteMonedaException;
import com.excepciones.Monedas.InsertandoMonedaException;
import com.excepciones.Monedas.ModificandoMonedaException;
import com.excepciones.Monedas.NoExisteMonedaException;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.server.VaadinService;
import com.valueObject.MonedaVO;
import com.valueObject.UsuarioPermisosVO;
import com.valueObject.proceso.ProcesoVO;
import com.vista.Mensajes;
import com.vista.MySub;
import com.vista.PermisosUsuario;
import com.vista.Variables;
import com.vista.VariablesPermisos;

public class ProcesoViewExtended extends ProcesoView{
	
	private BeanFieldGroup<ProcesoVO> fieldGroup;
	private ProcesoControlador controlador;
	private String operacion;
	private ProcesosPanelExtended mainView;
	MySub sub;
	private PermisosUsuario permisos;
	
	public ProcesoViewExtended(String opera, ProcesosPanelExtended main){
		
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
					UsuarioPermisosVO permisoAux = 
							new UsuarioPermisosVO(this.permisos.getCodEmp(),
									this.permisos.getUsuario(),
									VariablesPermisos.FORMULARIO_PROCESOS,
									VariablesPermisos.OPERACION_NUEVO_EDITAR);
									
					ProcesoVO procesoVO = new ProcesoVO();		
					
					procesoVO.setCodigo(Integer.parseInt(codigo.getValue().trim()));
					procesoVO.setCodCliente(codCliente.getValue().trim());
					procesoVO.setNomCliente(nomCliente.getValue().trim());
					//procesoVO.setFecha(fecDocum);
					procesoVO.setNroMega(nroMega.getValue().trim());

					
					
					monedaVO.setCodMoneda(cod_moneda.getValue().trim());
					monedaVO.setDescripcion(descripcion.getValue().trim());
					monedaVO.setSimbolo(simbolo.getValue().trim());
					monedaVO.setAceptaCotizacion(aceptaCotizacion.getValue());
					monedaVO.setActivo(activo.getValue());
					monedaVO.setUsuarioMod(this.permisos.getUsuario());
					monedaVO.setOperacion(operacion);
					
					if(this.operacion.equals(Variables.OPERACION_NUEVO)) {	
		
						this.controlador.insertarMoneda(monedaVO, permisoAux);
						
						this.mainView.actulaizarGrilla(monedaVO);
						
						Mensajes.mostrarMensajeOK("Se ha guardado la moneda");
						main.cerrarVentana();
					
					}
					else if(this.operacion.equals(Variables.OPERACION_EDITAR))	{
						
						this.controlador.actualizarMoneda(monedaVO, permisoAux);
						
						this.mainView.actulaizarGrilla(monedaVO);
						
						Mensajes.mostrarMensajeOK("Se ha modificado la moneda");
						main.cerrarVentana();
						
					}
				}
				else /*Si los campos no son válidos mostramos warning*/
				{
					Mensajes.mostrarMensajeWarning(Variables.WARNING_CAMPOS_NO_VALIDOS);
				}
					
				} 
				catch (ConexionException | ModificandoMonedaException | ExisteMonedaException | 
						 InicializandoException | InsertandoMonedaException | NoExisteMonedaException |
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
	}

}
