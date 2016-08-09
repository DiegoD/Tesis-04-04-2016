package com.vista.Login;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;


import com.controladores.LoginControlador;
import com.excepciones.ConexionException;
import com.excepciones.ErrorInesperadoException;
import com.excepciones.InicializandoException;
import com.excepciones.Login.LoginException;
import com.excepciones.Usuarios.ObteniendoUsuariosxEmpExeption;
import com.excepciones.grupos.ObteniendoFormulariosException;
import com.vaadin.event.FieldEvents.FocusEvent;
import com.vaadin.event.FieldEvents.FocusListener;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.valueObject.EmpLoginVO;
import com.valueObject.LoginVO;
import com.vista.MD5;
import com.vista.Mensajes;
import com.vista.PermisosUsuario;
import com.vista.Principal;
import com.vista.Variables;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginExtended extends Login implements ViewDisplay {
	
	LoginControlador controlador;
	Navigator navigator;
	private Principal principal;
	private ArrayList<EmpLoginVO> lstEmpresasUsu;

	
	public LoginExtended(Principal principal){
		
		this.principal = principal;
		
		controlador = new LoginControlador();
		this.tfUsuario.focus();
		this.btnIngresar.setClickShortcut(KeyCode.ENTER);
		
		ddlEmresa.addFocusListener(new FocusListener() {
			
			/*En el forcus de del combo de la empresa tomamos las empresas para el usario*/
			public void focus(FocusEvent event) {
								
				boolean usuarioValido;
						
				try {
					
					MD5 md5 = new MD5();
					
					LoginVO loginVO = new LoginVO();
					
					/*Convertimos el pass a MD5*/
					String password = tfPass.getValue().toString().trim();
					String passwordMd5 = md5.getMD5Hash(password);

					loginVO.setPass(passwordMd5);
					loginVO.setUsuario(tfUsuario.getValue());
					
					usuarioValido = controlador.usuarioValido(loginVO);
					
					if(usuarioValido)
					{
						/*Obtenemos las empresas para el usuario y lo
						 * desplegamos en el combo de empresas*/
						lstEmpresasUsu = controlador.getUsuariosxEmp(loginVO.getUsuario());
						
						for (EmpLoginVO emp : lstEmpresasUsu) 
						{
							ddlEmresa.addItem(emp.getNomEmp());
						}
						
					}
					else
					{
						Mensajes.mostrarMensajeError("Usuario y contrase�a no v�lido");
					}
					
				} catch (LoginException | InicializandoException | ErrorInesperadoException | ConexionException | ObteniendoUsuariosxEmpExeption | NoSuchAlgorithmException  e) {
					
					Mensajes.mostrarMensajeError(e.getMessage());
				}

			}
           
        });
		
		this.btnIngresar.addClickListener(click -> {
			
			PermisosUsuario permisos = (PermisosUsuario)VaadinService.getCurrentRequest().getWrappedSession().getAttribute("permisos");
			
			
			/*Si la session es nulla dejamos ingresar
			 * de lo contrario tiene una session y se debe desloguear primero*/
			//if(permisos == null)
			//{
				MD5 md5 = new MD5();
				
				LoginVO loginVO = new LoginVO();
				
				try {
					String passwordMd5 = md5.getMD5Hash(this.tfPass.getValue().toString().trim());
				
				
					
				loginVO.setPass(passwordMd5);
				loginVO.setUsuario(this.tfUsuario.getValue());
				
				if(this.ddlEmresa.getValue() != null)
				{
					loginVO.setCodEmp(this.obtenerCodEmpxNomEmp(this.ddlEmresa.getValue().toString().trim()));
					this.ingresarSistema(loginVO);
				}
				else
				{
					Mensajes.mostrarMensajeError("Debe seleccionar una empresa");
				}
				
				} catch (Exception e) {
					
					Mensajes.mostrarMensajeWarning(Variables.ERROR_INESPERADO);
				}
			//}
			//else{
				
				//Mensajes.mostrarMensajeError("Tiene una session iniciada, debe desloguearse");
			//}
				
		});
		
	}
	
	/**
	 * Valida usuario y contrase�a
	 * Si el usuario es valido, ingresa a menu principal
	 * Si el usuario no es valido retorna Exception usuario no v�lido
	 */
	private void ingresarSistema(LoginVO loginVO){
		
		boolean usuarioValido = false;
		
		try {
			
			usuarioValido = this.controlador.usuarioValido(loginVO);
			
			if(loginVO.getCodEmp() != null)
			{
				if(usuarioValido)
				{
					
					/*Seteamos la variable con los permisos para el usuario*/
					PermisosUsuario permisos = new PermisosUsuario();
					
					//permisos.setCodEmp(loginVO.getCodEmp().toString());
					permisos.setUsuario(loginVO.getUsuario());
					permisos.setLstPermisos(this.controlador.getPermisosUsuario(loginVO.getUsuario(), loginVO.getCodEmp())); 
									
					
					VaadinService.getCurrentRequest().getWrappedSession().setAttribute("usuario", loginVO.getUsuario());
					//getSession().setAttribute("usuario", loginVO.getUsuario());
					
					VaadinService.getCurrentRequest().getWrappedSession().setAttribute("permisos", permisos);
					//getSession().setAttribute("permisos", permisos);
					
					PermisosUsuario permisos2 = (PermisosUsuario)getSession().getAttribute("permisos");
					
										
					permisos.setCodEmp(this.obtenerCodEmpxNomEmp(this.ddlEmresa.getValue().toString()));
					
					principal.setMenu();
				
				}else{
					
					Mensajes.mostrarMensajeError("Usuario o contrase�a no v�lidos");
									
				}
				
			}
			else
			{
				Mensajes.mostrarMensajeError("Debe seleccionar una empresa");
			}
					
		} catch (LoginException | InicializandoException | ErrorInesperadoException | ConexionException | ObteniendoFormulariosException e) {
			
			 Mensajes.mostrarMensajeError( e.getMessage());
		} 
	}

	private String obtenerCodEmpxNomEmp(String nomEmp)
	{
		String codEmp = null;
		
		if(this.lstEmpresasUsu != null)
		{
			
			boolean salir = false;
			
			int i =0;
			while(i < this.lstEmpresasUsu.size() && !salir)
			{
				if(this.lstEmpresasUsu.get(i).getNomEmp().equals(nomEmp))
				{
					codEmp = this.lstEmpresasUsu.get(i).getCodEmp();
					
					salir = true;
				}
					
				i++;
			}
		}
		
		return codEmp;
	}
	
	@Override
	public void showView(View view) {
		// TODO Auto-generated method stub
		
	}
	



}