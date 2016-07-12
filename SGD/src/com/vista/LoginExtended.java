package com.vista;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.controladores.LoginControlador;
import com.excepciones.ConexionException;
import com.excepciones.ErrorInesperadoException;
import com.excepciones.InicializandoException;
import com.excepciones.Login.LoginException;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.server.Page;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.valueObject.LoginVO;

public class LoginExtended extends Login implements ViewDisplay {
	
	LoginControlador controlador;
	Navigator navigator;
	private Principal principal;
	
	public LoginExtended(Principal principal){
		
		this.principal = principal;
		
		controlador = new LoginControlador();
		
		
		this.btnIngresar.addClickListener(click -> {
			
			LoginVO loginVO = new LoginVO();
			
			loginVO.setPass(this.tfPass.getValue());
			loginVO.setUsuario(this.tfUsuario.getValue());
			
						
			this.ingresarSistema(loginVO);
			
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
			
			if(usuarioValido){
				
				getSession().setAttribute("usuario", loginVO.getUsuario());
				getSession().setAttribute("pass", loginVO.getPass());
				
				principal.setMenu();
			
			}else{
				
				new Notification("Atención",
	                     "<br/>" + "Usuario o contraseña no válidos",
	                     Notification.Type.WARNING_MESSAGE, true)
	                         .show(Page.getCurrent());
			}
					
		} catch (LoginException | InicializandoException | ErrorInesperadoException | ConexionException e) {
			
			 new Notification("Error",
                     "<br/>" + e.getMessage(),
                     Notification.Type.ERROR_MESSAGE, true)
                         .show(Page.getCurrent());
			
		} 
	}

	@Override
	public void showView(View view) {
		// TODO Auto-generated method stub
		
	}

}
