package com.vista;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.codec.binary.Hex;

import com.controladores.LoginControlador;
import com.excepciones.ConexionException;
import com.excepciones.ErrorInesperadoException;
import com.excepciones.InicializandoException;
import com.excepciones.Login.LoginException;
import com.excepciones.Usuarios.ObteniendoUsuariosxEmpExeption;
import com.excepciones.grupos.ObteniendoFormulariosException;
import com.vaadin.event.FieldEvents.FocusEvent;
import com.vaadin.event.FieldEvents.FocusListener;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.server.Page;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.valueObject.EmpLoginVO;
import com.valueObject.LoginVO;
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
		
		ddlEmresa.addFocusListener(new FocusListener() {
			
			/*En el forcus de del combo de la empresa tomamos las empresas para el usario*/
			public void focus(FocusEvent event) {
								
				boolean usuarioValido;
						
				try {
					
					LoginVO loginVO = new LoginVO();
					
					/*Convertimos el pass a MD5*/
					String password = tfPass.getValue().toString().trim();
					String passwordMd5;
					
					MessageDigest md = MessageDigest.getInstance("MD5");
			    	byte[] bytes = md.digest(password.getBytes());
			    	passwordMd5 = new String(Hex.encodeHex(bytes));
					
					
					loginVO.setPass(tfPass.getValue());
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
						Mensajes.mostrarMensajeError("Usuario y contraseña no válido");
					}
					
				} catch (LoginException | InicializandoException | ErrorInesperadoException | ConexionException | ObteniendoUsuariosxEmpExeption | NoSuchAlgorithmException e) {
					
					Mensajes.mostrarMensajeError(e.getMessage());
				}

			}
           
        });
		
		this.btnIngresar.addClickListener(click -> {
			
			LoginVO loginVO = new LoginVO();
			
			loginVO.setPass(this.tfPass.getValue());
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
					
					/*Inicializamos una variable de session para obtener los permisos
					 * del usuario*/
					PermisosUsuario permisos = new PermisosUsuario();
					permisos.lstFormularios = this.controlador.getPermisosUsuario(loginVO.getUsuario(), loginVO.getCodEmp());
					
					getSession().setAttribute("permisos", permisos);
					
					getSession().setAttribute("usuario", loginVO.getUsuario());
					//getSession().setAttribute("pass", loginVO.getPass());
					
					permisos.setCodEmp(this.obtenerCodEmpxNomEmp(this.ddlEmresa.getValue().toString()));
					
					principal.setMenu(permisos);
				
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
