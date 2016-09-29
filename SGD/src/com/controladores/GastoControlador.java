package com.controladores;

import java.util.ArrayList;

import com.excepciones.ConexionException;
import com.excepciones.ErrorInesperadoException;
import com.excepciones.InicializandoException;
import com.excepciones.NoTienePermisosException;
import com.excepciones.ObteniendoPermisosException;
import com.excepciones.Cuentas.ObteniendoCuentasException;
import com.excepciones.Cuentas.ObteniendoRubrosException;
import com.excepciones.DocLog.InsertandoLogException;
import com.excepciones.DocLog.ModificandoLogException;
import com.excepciones.Gastos.ExisteGastoException;
import com.excepciones.Gastos.IngresandoGastoException;
import com.excepciones.Gastos.ModificandoGastoException;
import com.excepciones.Gastos.NoExisteGastoException;
import com.excepciones.Gastos.ObteniendoGastosException;
import com.excepciones.Impuestos.ObteniendoImpuestosException;
import com.excepciones.Monedas.ObteniendoMonedaException;
import com.excepciones.Procesos.ObteniendoProcesosException;
import com.excepciones.Saldos.EliminandoSaldoException;
import com.excepciones.Saldos.IngresandoSaldoException;
import com.excepciones.Saldos.ModificandoSaldoException;
import com.excepciones.clientes.ObteniendoClientesException;
import com.excepciones.funcionarios.ObteniendoFuncionariosException;
import com.logica.Fachada;
import com.logica.FachadaDD;
import com.valueObject.FuncionarioVO;
import com.valueObject.ImpuestoVO;
import com.valueObject.MonedaVO;
import com.valueObject.RubroVO;
import com.valueObject.UsuarioPermisosVO;
import com.valueObject.Cuenta.CuentaVO;
import com.valueObject.Gasto.GastoVO;
import com.valueObject.Numeradores.NumeradoresVO;
import com.valueObject.cliente.ClienteVO;
import com.valueObject.proceso.ProcesoVO;

public class GastoControlador {
	
	public GastoControlador(){
		
	}
	
	/**
	 * Obtiene array list de VO de todos los gastos
	 * @throws NoTienePermisosException 
	 * @throws ObteniendoPermisosException 
	 */
	public ArrayList<GastoVO> getGastos(UsuarioPermisosVO permisos) throws ObteniendoGastosException, ConexionException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException {
		
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			return FachadaDD.getInstance().getGastos(permisos.getCodEmp());
		else
			throw new NoTienePermisosException();
	}
	
	/**
	 * Inserta un nuevo gasto
	 * @throws NoTienePermisosException 
	 * @throws ObteniendoPermisosException 
	 */
	public NumeradoresVO insertarGasto(GastoVO gastoVO, UsuarioPermisosVO permisos) throws IngresandoGastoException, ExisteGastoException, InicializandoException, ConexionException, ErrorInesperadoException, ObteniendoPermisosException, NoTienePermisosException
	{
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			return FachadaDD.getInstance().insertarGasto(gastoVO, permisos.getCodEmp());
		else
			throw new NoTienePermisosException();
	}
	
	
	/**
	 * Actualiza los datos de un gasto
	 * @throws NoTienePermisosException 
	 * @throws ObteniendoPermisosException 
	 * @throws IngresandoGastoException 
	 * @throws IngresandoSaldoException 
	 * @throws EliminandoSaldoException 
	 * @throws ModificandoSaldoException 
	 * @throws InsertandoLogException 
	 * @throws ModificandoLogException 
	 */
	public void actualizarGasto(GastoVO gastoVO, UsuarioPermisosVO permisos) throws ConexionException, NoExisteGastoException, ModificandoGastoException, ExisteGastoException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException, IngresandoGastoException, ModificandoSaldoException, EliminandoSaldoException, IngresandoSaldoException, InsertandoLogException, ModificandoLogException {
		
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			FachadaDD.getInstance().actualizarGasto(gastoVO, permisos.getCodEmp());
		else
			throw new NoTienePermisosException();
	}
	
	/**
	 * Trae los clientes activos
	 */
	public ArrayList<ClienteVO> getClientes(UsuarioPermisosVO permisos) throws ConexionException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException, ObteniendoClientesException {
		
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			return Fachada.getInstance().getClientesActivos(permisos.getCodEmp());
		else
			throw new NoTienePermisosException();
	}
	
	public ArrayList<MonedaVO> getMonedas(UsuarioPermisosVO permisos) throws ObteniendoMonedaException, ConexionException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException{
		
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			return FachadaDD.getInstance().getMonedasActivas(permisos.getCodEmp());
		else
			throw new NoTienePermisosException();
	}
	
	public ArrayList<CuentaVO> getCuentas(UsuarioPermisosVO permisos, String codRubro) throws ObteniendoCuentasException, ConexionException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException, ObteniendoRubrosException{
		
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			return FachadaDD.getInstance().getCuentasxRubro(permisos.getCodEmp(), codRubro);
		else
			throw new NoTienePermisosException();
	}
	
	public ArrayList<RubroVO> getRubros(UsuarioPermisosVO permisos) throws ObteniendoRubrosException, ConexionException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException, ObteniendoRubrosException, com.excepciones.Rubros.ObteniendoRubrosException{
		
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			return FachadaDD.getInstance().getRubrosActivos(permisos.getCodEmp());
		else
			throw new NoTienePermisosException();
	}
	
	public ArrayList<ImpuestoVO> getImpuestos(UsuarioPermisosVO permisos) throws ObteniendoImpuestosException, ConexionException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException, ObteniendoImpuestosException{
		
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			return FachadaDD.getInstance().getImpuestosActivos(permisos.getCodEmp());
		else
			throw new NoTienePermisosException();
	}
	
	public ArrayList<ProcesoVO> getProcesos(UsuarioPermisosVO permisos) throws ObteniendoProcesosException, ConexionException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException, ObteniendoProcesosException{
		
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			return FachadaDD.getInstance().getProcesos(permisos.getCodEmp());
		else
			throw new NoTienePermisosException();
	}
	
	public ArrayList<FuncionarioVO> getFuncionarios(UsuarioPermisosVO permisos) throws ObteniendoFuncionariosException, ConexionException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException {
		
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			return Fachada.getInstance().getFuncionariosTodos(permisos.getCodEmp());
		else
			throw new NoTienePermisosException();
	}

}
