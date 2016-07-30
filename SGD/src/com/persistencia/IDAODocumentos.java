package com.persistencia;

import java.util.ArrayList;

import com.excepciones.ConexionException;
import com.excepciones.Documentos.ExisteDocumentoException;
import com.excepciones.Documentos.InsertandoDocumentoException;
import com.excepciones.Documentos.ModificandoDocumentoException;
import com.excepciones.Documentos.ObteniendoDocumentosException;
import com.logica.DocumentoAduanero;

import java.sql.Connection;

public interface IDAODocumentos {
	public ArrayList<DocumentoAduanero> getDocumentos(Connection con) throws ObteniendoDocumentosException, ConexionException;
	public void insertarDocumento(DocumentoAduanero documento, Connection con) throws  InsertandoDocumentoException, ConexionException ;
	public boolean memberDocumento(String cod_documento, Connection con) throws ExisteDocumentoException, ConexionException;
	public void actualizarDocumento(DocumentoAduanero documento, Connection con) throws ModificandoDocumentoException, ConexionException;
}
