package com.persistencia;

import java.sql.Connection;
import java.util.ArrayList;

import com.excepciones.ConexionException;
import com.excepciones.CodigosGeneralizados.EliminandoCodigoGeneralizadoException;
import com.excepciones.CodigosGeneralizados.ExisteCodigoException;
import com.excepciones.CodigosGeneralizados.InsertandoCodigoException;
import com.excepciones.CodigosGeneralizados.ModificandoCodigoException;
import com.excepciones.CodigosGeneralizados.ObteniendoCodigosException;
import com.excepciones.Usuarios.InsertandoUsuarioException;
import com.excepciones.Usuarios.ModificandoUsuarioException;
import com.logica.CodigoGeneralizado;
import com.logica.Usuario;

public interface IDAOCodigosGeneralizados {
	
	public ArrayList<CodigoGeneralizado> getCodigosGeneralizados(Connection con) throws ObteniendoCodigosException, ConexionException;
	public void insertarCodigoGeneralizado(CodigoGeneralizado codigoGeneralizado, Connection con) throws  InsertandoCodigoException, ConexionException ;
	public boolean memberCodigoGeneralizado(String codigo, String valor, Connection con) throws ExisteCodigoException, ConexionException;
	public void actualizarCodigoGeneralizado(CodigoGeneralizado codigoGeneralizado, Connection con) throws ModificandoCodigoException, ConexionException;
	public void eliminarCodigoGeneralizado(String codigo, String valor, Connection con) throws EliminandoCodigoGeneralizadoException, ConexionException;
	public ArrayList<CodigoGeneralizado> getCodigosGeneralizadosxCodigo(String codigo, Connection con) throws ObteniendoCodigosException, ConexionException;	
}
