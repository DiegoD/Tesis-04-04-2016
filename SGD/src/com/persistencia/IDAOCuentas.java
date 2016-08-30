package com.persistencia;

import java.sql.Connection;
import java.util.ArrayList;

import com.excepciones.ConexionException;
import com.excepciones.Cuentas.InsertandoCuentaException;
import com.excepciones.Cuentas.MemberCuentaException;
import com.excepciones.Cuentas.ModificandoCuentaException;
import com.excepciones.Cuentas.ObteniendoCuentasException;
import com.excepciones.Cuentas.ObteniendoRubrosException;
import com.logica.Cuenta;
import com.logica.Rubro;

public interface IDAOCuentas {
	
	public ArrayList<Cuenta> getCuentas(String codEmp, Connection con) throws ObteniendoCuentasException, ConexionException, ObteniendoRubrosException;
	public void insertarCuenta(Cuenta cuenta, String codEmp, Connection con) throws  InsertandoCuentaException, ConexionException ;
	public boolean memberCuenta(String codCuenta, String codEmp, Connection con) throws MemberCuentaException, ConexionException;
	public void eliminarCuenta(String codCuenta, String codEmp, Connection con) throws ModificandoCuentaException, ConexionException;
	public ArrayList<Rubro> getRubrosNoCuenta(String codCuenta, String codEmp, Connection con) throws ObteniendoRubrosException;
	public void actualizarCuenta(Cuenta cuenta, String codEmp, Connection con) throws ModificandoCuentaException, ConexionException, InsertandoCuentaException;
	public Cuenta getCuenta(String codEmp, Connection con, String codCuenta) throws ObteniendoCuentasException, ObteniendoRubrosException;
}
