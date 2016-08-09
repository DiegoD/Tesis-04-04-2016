package com.controladores;

import java.util.ArrayList;

import com.excepciones.ConexionException;
import com.excepciones.InicializandoException;
import com.excepciones.Documentos.ObteniendoDocumentosException;
import com.excepciones.clientes.ExisteClienteExeption;
import com.excepciones.clientes.InsertandoClienteException;
import com.excepciones.clientes.ModificandoClienteException;
import com.excepciones.clientes.ObteniendoClientesException;
import com.excepciones.funcionarios.ExisteFuncionarioException;
import com.excepciones.funcionarios.InsertendoFuncionarioException;
import com.excepciones.funcionarios.ModificandoFuncionarioException;
import com.excepciones.funcionarios.ObteniendoFuncionariosException;
import com.logica.Fachada;
import com.valueObject.DocumDGIVO;
import com.valueObject.FuncionarioVO;
import com.valueObject.cliente.ClienteVO;

public class FuncionarioControlador {

	/**
	 * Obtiene todos los clientes del sistema
	 * @throws ObteniendoFuncionariosException 
	 */
	public ArrayList<FuncionarioVO> getFuncionariosTodos(String codEmp) throws ConexionException, InicializandoException, ObteniendoFuncionariosException  
	{
		return Fachada.getInstance().getFuncionariosTodos(codEmp);
    }
	
	/**
	 * Obtiene todos los clientes del sistema
	 * @throws ObteniendoFuncionariosException 
	 */
	public ArrayList<FuncionarioVO> getFuncionariosAvtivos(String codEmp) throws ConexionException, InicializandoException, ObteniendoFuncionariosException  
	{
		return Fachada.getInstance().getFuncionariosActivos(codEmp);
    }
	
	
	/**
	 * Inserta un funcionario dado su VO
	 */
	public int insertarFuncionario(FuncionarioVO funcionarioVO, String empresa) throws ConexionException, InicializandoException, InsertendoFuncionarioException, ExisteFuncionarioException 
	{
		int codigo = 0;
		
		codigo = Fachada.getInstance().insertarFuncionario(funcionarioVO, empresa);
		
		return codigo;
	}
	
	/**
	 * Modifica los datos de un funcionario dado el VO con las modificaciones
	 */
	public void modificarFuncionario(FuncionarioVO funcionarioVO, String empresa) throws ConexionException, InicializandoException, ModificandoFuncionarioException, ExisteFuncionarioException 
	{
		Fachada.getInstance().editarFuncionario(funcionarioVO, empresa);
	}
	
	public ArrayList<DocumDGIVO> obtnerDocumentosDgi() throws ObteniendoDocumentosException, ConexionException, InicializandoException
	{
		return Fachada.getInstance().getDocumentosDGI();
	}
	
}
