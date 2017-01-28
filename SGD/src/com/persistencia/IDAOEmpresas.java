package com.persistencia;

import java.sql.Connection;
import java.util.ArrayList;

import com.excepciones.ConexionException;
import com.excepciones.Empresas.ExisteEmpresaException;
import com.excepciones.Empresas.InsertandoEmpresaException;
import com.excepciones.Empresas.ModificandoEmpresaException;
import com.excepciones.Empresas.ObteniendoEmpresasException;
import com.logica.Empresa;

public interface IDAOEmpresas {
	
	public ArrayList<Empresa> getEmpreas(Connection con) throws ObteniendoEmpresasException, ConexionException;
	public void insertarEmpresa(Empresa empresa, Connection con) throws  InsertandoEmpresaException, ConexionException ;
	public boolean memberEmpresa(String cod_emp, Connection con) throws ExisteEmpresaException, ConexionException;
	public void actualizarEmpresa(Empresa empresa, Connection con) throws ModificandoEmpresaException, ConexionException;
}
