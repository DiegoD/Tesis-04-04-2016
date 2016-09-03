package com.persistencia;

import java.sql.Connection;
import java.util.ArrayList;

import com.excepciones.ConexionException;
import com.excepciones.Impuestos.ExisteImpuestoException;
import com.excepciones.Impuestos.InsertandoImpuestoException;
import com.excepciones.Impuestos.ModificandoImpuestoException;
import com.excepciones.Impuestos.ObteniendoImpuestosException;
import com.excepciones.grupos.InsertandoGrupoException;
import com.excepciones.grupos.MemberGrupoException;
import com.excepciones.grupos.ModificandoGrupoException;
import com.excepciones.grupos.ObteniendoFormulariosException;
import com.excepciones.grupos.ObteniendoGruposException;
import com.logica.Grupo;
import com.logica.Impuesto;

public interface IDAOImpuestos {
	
	public ArrayList<Impuesto> getImpuestos(String codEmp, Connection con) throws ObteniendoImpuestosException, ConexionException;
	public ArrayList<Impuesto> getImpuestosActivos(String codEmp, Connection con) throws ObteniendoImpuestosException, ConexionException;
	public void insertarImpuesto(Impuesto impuesto, String codEmp, Connection con) throws  InsertandoImpuestoException, ConexionException ;
	public boolean memberImpuesto(String cod_impuesto, String codEmp, Connection con) throws ExisteImpuestoException, ConexionException;
	public void eliminarImpuesto(String cod_impuesto, String codEmp, Connection con) throws ModificandoImpuestoException, ConexionException;
	public void actualizarImpuesto(Impuesto impuesto, String codEmp, Connection con) throws ModificandoImpuestoException, ConexionException;
	
}
