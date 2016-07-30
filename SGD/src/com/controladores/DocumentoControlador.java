package com.controladores;

import java.util.ArrayList;

import com.excepciones.ConexionException;
import com.excepciones.ErrorInesperadoException;
import com.excepciones.InicializandoException;
import com.excepciones.Documentos.ExisteDocumentoException;
import com.excepciones.Documentos.InsertandoDocumentoException;
import com.excepciones.Documentos.ModificandoDocumentoException;
import com.excepciones.Documentos.NoExisteDocumentoException;
import com.excepciones.Documentos.ObteniendoDocumentosException;
import com.logica.FachadaDD;
import com.valueObject.DocumentoAduaneroVO;

public class DocumentoControlador {
	
	public DocumentoControlador(){
		
	}

	/**
	 * Obtiene array list de VO de todos los documentos
	 */
	public ArrayList<DocumentoAduaneroVO> getDocumentos() throws ObteniendoDocumentosException, ConexionException, InicializandoException {
		
			return FachadaDD.getInstance().getDocumentos();
	}
	
	/**
	 * Inserta un nuevo documento
	 */
	public void insertarDocumento(DocumentoAduaneroVO documentoVO) throws InsertandoDocumentoException, ExisteDocumentoException, InicializandoException, ConexionException, ErrorInesperadoException
	{
		FachadaDD.getInstance().insertarDocumento(documentoVO);
	}
	
	
	/**
	 * Actualiza los datos de un documento
	 */
	public void actualizarDocumento(DocumentoAduaneroVO documentoVO) throws ConexionException, NoExisteDocumentoException, ModificandoDocumentoException, ExisteDocumentoException, InicializandoException {
		FachadaDD.getInstance().actualizarDocumento(documentoVO);
	}
}
