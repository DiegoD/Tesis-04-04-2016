package com.persistencia;

import java.sql.Connection;
import java.util.ArrayList;

import com.excepciones.ConexionException;
import com.excepciones.Bancos.ExisteBancoException;
import com.excepciones.Bancos.InsertandoBancoException;
import com.excepciones.Bancos.InsertandoCuentaException;
import com.excepciones.Bancos.ModificandoBancoException;
import com.excepciones.Bancos.ModificandoCuentaBcoException;
import com.excepciones.Bancos.ObteniendoBancosException;
import com.excepciones.Bancos.ObteniendoCuentasBcoException;
import com.logica.Banco;
import com.logica.CtaBco;

public interface IDAOBancos {
	
	public ArrayList<Banco> getBancosTodos(Connection con, String codEmp) throws ObteniendoBancosException, ObteniendoCuentasBcoException , ConexionException;
	public ArrayList<Banco> getBancosActivos(Connection con, String codEmp) throws ObteniendoBancosException, ObteniendoCuentasBcoException , ConexionException;
	public boolean memberBanco(String codBanco, String codEmp, Connection con) throws ExisteBancoException, ConexionException;
	public void insertarBanco(Banco banco, String codEmp, Connection con) throws InsertandoBancoException, ConexionException, InsertandoCuentaException;
	public void modificarBanco(Banco banco, String codEmp, Connection con) throws ModificandoBancoException, ModificandoCuentaBcoException, ConexionException;
	 ArrayList<CtaBco> getCtaBcoActivos(Connection con, String codEmp, String codBco) throws ObteniendoCuentasBcoException, ConexionException;
}
