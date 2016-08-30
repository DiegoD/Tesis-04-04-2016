package com.controladores;

import java.util.ArrayList;

import com.excepciones.ConexionException;
import com.excepciones.ErrorInesperadoException;
import com.excepciones.InicializandoException;
import com.excepciones.NoTienePermisosException;
import com.excepciones.ObteniendoPermisosException;
import com.excepciones.Cuentas.ExisteCuentaException;
import com.excepciones.Cuentas.InsertandoCuentaException;
import com.excepciones.Cuentas.MemberCuentaException;
import com.excepciones.Cuentas.ModificandoCuentaException;
import com.excepciones.Cuentas.NoExisteCuentaException;
import com.excepciones.Cuentas.ObteniendoCuentasException;
import com.excepciones.Cuentas.ObteniendoRubrosException;
import com.logica.Fachada;
import com.logica.FachadaDD;
import com.valueObject.RubroVO;
import com.valueObject.UsuarioPermisosVO;
import com.valueObject.Cuenta.CuentaVO;
import com.vista.Cuentas.CuentaViewExtended;

public class CuentaControlador {
	
	private CuentaViewExtended vista;
	
	public CuentaControlador(CuentaViewExtended view){
		this.vista = view;
	}
	
	public CuentaControlador(){
		
	}
	
	public void insertarCuenta(CuentaVO cuentaVO, UsuarioPermisosVO permisos) throws InsertandoCuentaException, MemberCuentaException, ExisteCuentaException, InicializandoException, ConexionException, ErrorInesperadoException, NoTienePermisosException, ObteniendoPermisosException{
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			FachadaDD.getInstance().insertarCuenta(cuentaVO, permisos.getCodEmp());
		
		else
			throw new NoTienePermisosException();
	}
		
	public ArrayList<CuentaVO> getCuentas(UsuarioPermisosVO permisos) throws ObteniendoCuentasException, InicializandoException, ConexionException, ErrorInesperadoException, ObteniendoRubrosException, ObteniendoPermisosException, NoTienePermisosException{
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			return FachadaDD.getInstance().getCuentas(permisos.getCodEmp());
		else
			throw new NoTienePermisosException();
	
	}
	
	public void editarCuenta(CuentaVO cuentaVO, UsuarioPermisosVO permisos) throws InsertandoCuentaException, MemberCuentaException, NoExisteCuentaException, ConexionException, ErrorInesperadoException, InicializandoException, ModificandoCuentaException, ObteniendoPermisosException, NoTienePermisosException{
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			FachadaDD.getInstance().editarCuenta(cuentaVO, permisos.getCodEmp());
		else
			throw new NoTienePermisosException();
	}
	
	public ArrayList<RubroVO> getRubrosNoCuenta(String codCuenta, String codEmp) throws ObteniendoRubrosException, ConexionException, ErrorInesperadoException, InicializandoException, ObteniendoCuentasException {
		return FachadaDD.getInstance().getRubrosNoCuenta(codCuenta, codEmp);
	}
}
