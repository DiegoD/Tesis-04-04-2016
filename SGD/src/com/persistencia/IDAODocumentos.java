package com.persistencia;

import java.util.ArrayList;

import com.excepciones.ConexionException;
import com.excepciones.Documentos.ExisteDocumentoException;
import com.excepciones.Documentos.InsertandoDocumentoException;
import com.excepciones.Documentos.ModificandoDocumentoException;
import com.excepciones.Documentos.ObteniendoDocumentosException;
import com.logica.Documento;
import java.sql.Connection;

public interface IDAODocumentos {
	public ArrayList<Documento> getDocumentos(Connection con) throws ObteniendoDocumentosException, ConexionException;
	public void insertarDocumento(Documento documento, Connection con) throws  InsertandoDocumentoException, ConexionException ;
	public boolean memberDocumento(String cod_documento, Connection con) throws ExisteDocumentoException, ConexionException;
	public void actualizarDocumento(Documento documento, Connection con) throws ModificandoDocumentoException, ConexionException;
}
