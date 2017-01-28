package com.persistencia;

import java.sql.Connection;
import java.util.ArrayList;

import com.excepciones.ConexionException;
import com.excepciones.Documentos.ExisteDocumentoException;
import com.excepciones.Documentos.InsertandoDocumentoException;
import com.excepciones.Documentos.ModificandoDocumentoException;
import com.excepciones.Documentos.ObteniendoDocumentosException;
import com.logica.DocumDGI;

public interface IDAODocumDgi {

	public ArrayList<DocumDGI> getDocumentos(Connection con) throws ObteniendoDocumentosException, ConexionException;
	public void insertarDocumento(DocumDGI documento, Connection con) throws  InsertandoDocumentoException, ConexionException ;
	public boolean memberDocumento(String cod_documento, Connection con) throws ExisteDocumentoException, ConexionException;
	public void actualizarDocumento(DocumDGI documento, Connection con) throws ModificandoDocumentoException, ConexionException;
	
}
