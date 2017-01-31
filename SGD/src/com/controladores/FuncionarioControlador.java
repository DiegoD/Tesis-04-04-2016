package com.controladores;

import java.util.ArrayList;

import com.excepciones.ConexionException;
import com.excepciones.InicializandoException;
import com.excepciones.NoTienePermisosException;
import com.excepciones.ObteniendoPermisosException;
import com.excepciones.Documentos.ObteniendoDocumentosException;
import com.excepciones.clientes.ExisteClienteExeption;
import com.excepciones.clientes.InsertandoClienteException;
import com.excepciones.clientes.ModificandoClienteException;
import com.excepciones.clientes.ObteniendoClientesException;
import com.excepciones.funcionarios.ExisteFuncionarioDocumetnoException;
import com.excepciones.funcionarios.ExisteFuncionarioException;
import com.excepciones.funcionarios.InsertendoFuncionarioException;
import com.excepciones.funcionarios.ModificandoFuncionarioException;
import com.excepciones.funcionarios.ObteniendoFuncionariosException;
import com.excepciones.grupos.ObteniendoFormulariosException;
import com.logica.Fachada;
import com.valueObject.DocumDGIVO;
import com.valueObject.FuncionarioVO;
import com.valueObject.UsuarioPermisosVO;

public class FuncionarioControlador {

	/**
	 * Obtiene todos los clientes del sistema
	 * @throws ObteniendoFuncionariosException 
	 * @throws NoTienePermisosException 
	 * @throws ObteniendoFormulariosException 
	 */
	public ArrayList<FuncionarioVO> getFuncionariosTodos(UsuarioPermisosVO permisos) throws ConexionException, InicializandoException, ObteniendoFuncionariosException, NoTienePermisosException, ObteniendoPermisosException  
	{
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			return Fachada.getInstance().getFuncionariosTodos(permisos.getCodEmp());
		else
			throw new NoTienePermisosException();
    }
	
	/**
	 * Obtiene todos los clientes del sistema
	 * @throws ObteniendoFuncionariosException 
	 * @throws ObteniendoFormulariosException 
	 * @throws NoTienePermisosException 
	 */
	public ArrayList<FuncionarioVO> getFuncionariosAvtivos(UsuarioPermisosVO permisos) throws ConexionException, InicializandoException, ObteniendoFuncionariosException, ObteniendoPermisosException, NoTienePermisosException  
	{
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			return Fachada.getInstance().getFuncionariosActivos(permisos.getCodEmp());
		else
			throw new NoTienePermisosException();
    }
	
	
	/**
	 * Inserta un funcionario dado su VO
	 * @throws ExisteFuncionarioDocumetnoException 
	 * @throws NoTienePermisosException 
	 * @throws ObteniendoFormulariosException 
	 */
	public int insertarFuncionario(FuncionarioVO funcionarioVO, UsuarioPermisosVO permisos) throws ConexionException, InicializandoException, InsertendoFuncionarioException, ExisteFuncionarioException, ExisteFuncionarioDocumetnoException, ObteniendoPermisosException, NoTienePermisosException 
	{
		int codigo = 0;
		
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			codigo = Fachada.getInstance().insertarFuncionario(funcionarioVO, permisos.getCodEmp());
		else
			throw new NoTienePermisosException();
		
		return codigo;
	}
	
	/**
	 * Modifica los datos de un funcionario dado el VO con las modificaciones
	 * @throws ExisteFuncionarioDocumetnoException 
	 * @throws NoTienePermisosException 
	 * @throws ObteniendoFormulariosException 
	 */
	public void modificarFuncionario(FuncionarioVO funcionarioVO, UsuarioPermisosVO permisos) throws ConexionException, InicializandoException, ModificandoFuncionarioException, ExisteFuncionarioException, ExisteFuncionarioDocumetnoException, ObteniendoPermisosException, NoTienePermisosException 
	{
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			Fachada.getInstance().editarFuncionario(funcionarioVO, permisos.getCodEmp());
		else
			throw new NoTienePermisosException();
	}
	
	public ArrayList<DocumDGIVO> obtnerDocumentosDgi() throws ObteniendoDocumentosException, ConexionException, InicializandoException
	{
		return Fachada.getInstance().getDocumentosDGI();
	}
	
}
