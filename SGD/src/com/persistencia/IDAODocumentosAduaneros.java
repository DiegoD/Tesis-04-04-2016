package com.persistencia;

import java.util.ArrayList;

import com.excepciones.documentosAduaneros.IngresandoDocumentoAduaneroException;
import com.excepciones.documentosAduaneros.MemberDocumentosAduanerosException;
import com.excepciones.documentosAduaneros.ObteniendoDocumentoAduaneroException;
import com.valueObject.DocumentoAuaneroVO;

public interface IDAODocumentosAduaneros {

	public ArrayList<DocumentoAuaneroVO>  getDocumentosAduanerosActivos() throws ObteniendoDocumentoAduaneroException;

	public ArrayList<DocumentoAuaneroVO>  getDocumentosAduanerosTodos() throws ObteniendoDocumentoAduaneroException;
	
	public DocumentoAuaneroVO  getDocumentosAduanero(int codDocum) throws ObteniendoDocumentoAduaneroException;
	
	public boolean  memberDocumentosAduanerosActivos(int codDocum) throws MemberDocumentosAduanerosException;
	
	public void insertCotizacion(DocumentoAuaneroVO documentoAuaneroVO) throws IngresandoDocumentoAduaneroException;
}
