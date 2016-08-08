package com.persistencia;

import java.sql.Connection;
import java.util.ArrayList;

import com.excepciones.ConexionException;
import com.excepciones.funcionarios.*;
import com.logica.Funcionario;



public interface IDAOFuncionarios {


	public ArrayList<Funcionario> getFuncionariosTodos(Connection con, String codEmp) throws ObteniendoFuncionariosException, ConexionException ;
	public ArrayList<Funcionario> getFuncionariosActivos(Connection con, String codEmp) throws ObteniendoFuncionariosException, ConexionException;
	public boolean memberFuncionario(int codFuncionario, String codEmp, Connection con) throws ExisteFuncionarioException, ConexionException;
	public int insertarFuncionario(Funcionario funcionario, String empresa, Connection con) throws InsertendoFuncionarioException, ConexionException;
	public void modificarFuncionario(Funcionario funcionario, String empresa, Connection con) throws ModificandoFuncionarioException;
	
}
