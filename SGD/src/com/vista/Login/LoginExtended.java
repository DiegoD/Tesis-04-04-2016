package com.vista.Login;

import java.util.ArrayList;


import com.controladores.LoginControlador;
import com.excepciones.ConexionException;
import com.excepciones.ErrorInesperadoException;
import com.excepciones.InicializandoException;
import com.excepciones.Login.LoginException;
import com.excepciones.Usuarios.ObteniendoUsuariosxEmpExeption;
import com.excepciones.grupos.ObteniendoFormulariosException;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.server.VaadinService;
import com.valueObject.EmpLoginVO;
import com.valueObject.LoginVO;
import com.vista.MD5;
import com.vista.Mensajes;
import com.vista.PermisosUsuario;
import com.vista.Principal;


public class LoginExtended extends Login implements ViewDisplay {
	
	LoginControlador controlador;
	private Principal principal;

	
	public LoginExtended(Principal principal){
		
		this.principal = principal;
		
		
		/****************************************LOGO**************************************/
		//ThemeResource resource = new ThemeResource("Icons/logo.png");
		//Image image = new Image("", resource); 
		//image.setHeight("30%");
		//image.setWidth("100%");
		
		//this.layout.addComponent(image);
		/************************************FIN LOGO**************************************/
		
		controlador = new LoginControlador();
		this.tfUsuario.focus();
		this.btnIngresar.setClickShortcut(KeyCode.ENTER);
		
		
		this.btnIngresar.addClickListener(click -> {
			
			PermisosUsuario permisos = (PermisosUsuario)VaadinService.getCurrentRequest().getWrappedSession().getAttribute("permisos");
			
			
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
						/*Obtenemos la empresa para el usuario*/
						EmpLoginVO emp = controlador.getEmpresaUsuario(loginVO.getUsuario());
						loginVO.setCodEmp(emp.getCodEmp());
						
						this.ingresarSistema(loginVO);
						
					}
					else
					{
						Mensajes.mostrarMensajeError("Usuario y contraseña no válido");
					}
					
				} catch (LoginException | InicializandoException | ErrorInesperadoException | ConexionException | ObteniendoUsuariosxEmpExeption  e) {
					
					Mensajes.mostrarMensajeError(e.getMessage());
				}

				
		});
		
	}
	
	/**
	 * Valida usuario y contraseña
	 * Si el usuario es valido, ingresa a menu principal
	 * Si el usuario no es valido retorna Exception usuario no válido
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
					
										
					permisos.setCodEmp(loginVO.getCodEmp());
					
					principal.setMenu();
					
					@SuppressWarnings("unused")
					int i =0;
				
					
				}else{
					
					Mensajes.mostrarMensajeError("Usuario o contraseña no válidos");
									
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

	
	@Override
	public void showView(View view) {
		// TODO Auto-generated method stub
		
	}
	



}
