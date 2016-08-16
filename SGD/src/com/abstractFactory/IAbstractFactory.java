package com.abstractFactory;

import com.persistencia.IDAOClientes;
import com.persistencia.IDAOCodigosGeneralizados;
import com.persistencia.IDAODocumDgi;
//import com.persistencia.IDAODocumDgi;
import com.persistencia.IDAODocumentos;
import com.persistencia.IDAOEmpresas;
import com.persistencia.IDAOFuncionarios;
import com.persistencia.IDAOGrupos;
import com.persistencia.IDAOImpuestos;
import com.persistencia.IDAOMonedas;
import com.persistencia.IDAORubros;
import com.persistencia.IDAOUsuarios;

public interface IAbstractFactory 
{

	public IDAOUsuarios crearDAOUsuarios();
	
	public IDAOGrupos crearDAOGrupos();

	
	public IDAOClientes crearDAOClientes();

	public IDAOImpuestos crearDAOImpuestos();
	
	public IDAOMonedas crearDAOMonedas();
	
	public IDAOEmpresas crearDAOEmpresas() ;
	
	public IDAORubros crearDAORubros();
	
	public IDAODocumentos crearDAODocumentos();
	
	public IDAODocumDgi crearDAODocumDgi();
	
	public IDAOFuncionarios crearDAOFuncionarios();
	
	public IDAOCodigosGeneralizados crearDAOCodigosGeneralizados ();

}
