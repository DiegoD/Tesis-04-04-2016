package com.controladores;

import java.util.ArrayList;

import com.excepciones.ConexionException;
import com.excepciones.InicializandoException;
import com.excepciones.NoTienePermisosException;
import com.excepciones.ObteniendoPermisosException;
import com.excepciones.Bancos.ObteniendoBancosException;
import com.excepciones.Bancos.ObteniendoCuentasBcoException;
import com.excepciones.Cheques.ObteniendoChequeException;
import com.excepciones.Titulares.ObteniendoTitularesException;
import com.excepciones.clientes.ObteniendoClientesException;
import com.logica.Fachada;
import com.logica.FachadaDD;
import com.valueObject.TitularVO;
import com.valueObject.UsuarioPermisosVO;
import com.valueObject.Deposito.DepositoVO;
import com.valueObject.banco.BancoVO;
import com.valueObject.banco.CtaBcoVO;

public class DepositoControlador {

	
	public ArrayList<BancoVO> getBcos(UsuarioPermisosVO permisos) throws  ConexionException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException, ObteniendoBancosException, ObteniendoCuentasBcoException{
		
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			return Fachada.getInstance().getBancosActivos(permisos.getCodEmp());
		else
			throw new NoTienePermisosException();
	}

	public ArrayList<CtaBcoVO> getCtaBcos(UsuarioPermisosVO permisos, String codBco) throws  ConexionException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException, ObteniendoCuentasBcoException{
		
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			return Fachada.getInstance().getCtaBcoActivosxBco(permisos.getCodEmp(), codBco);
		else
			throw new NoTienePermisosException();
	}
	
	public ArrayList<TitularVO> getTitulares(UsuarioPermisosVO permisos) throws ConexionException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException, ObteniendoClientesException, ObteniendoTitularesException {
		
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			return FachadaDD.getInstance().getTitularesActivosFuncionarios(permisos.getCodEmp());
		else
			throw new NoTienePermisosException();
	}
	
	public ArrayList<DepositoVO> getChequesBanco(UsuarioPermisosVO permisos, String codBco, String codCtaBco) throws ConexionException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException, ObteniendoChequeException, ObteniendoCuentasBcoException, ObteniendoBancosException {
		
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			return FachadaDD.getInstance().getChequesBanco(permisos.getCodEmp(), codBco, codCtaBco);
		else
			throw new NoTienePermisosException();
	}
	
	public void depositarCheques(UsuarioPermisosVO permisos, ArrayList<DepositoVO> cheques) throws ConexionException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException, ObteniendoChequeException, ObteniendoCuentasBcoException, ObteniendoBancosException {
		
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			FachadaDD.getInstance().depositarCheques(permisos.getCodEmp(), cheques);
		else
			throw new NoTienePermisosException();
	}
}
