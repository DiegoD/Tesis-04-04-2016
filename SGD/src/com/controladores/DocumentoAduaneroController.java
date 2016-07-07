package com.controladores;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import com.excepciones.InicializandoException;
import com.excepciones.documentosAduaneros.IngresandoDocumentoAduaneroException;
import com.excepciones.documentosAduaneros.ObteniendoDocumentoAduaneroException;
import com.logica.Fachada;
import com.valueObject.DocumentoAuaneroVO;

public class DocumentoAduaneroController {

	 public ArrayList<DocumentoAuaneroVO> getDocumentosAduanerosActivos() throws ObteniendoDocumentoAduaneroException, InicializandoException{
	    	
	    	return Fachada.getInstance().getDocumentosAduanerosActivos();
	    }
	    
	    public ArrayList<DocumentoAuaneroVO> getDocumentosAduanerosTodos() throws ObteniendoDocumentoAduaneroException, InicializandoException {
	    	
	    	return Fachada.getInstance().getDocumentosAduanerosTodos();
	    }
	    
	    public DocumentoAuaneroVO  getDocumentosAduanero(int codDocum) throws ObteniendoDocumentoAduaneroException, InicializandoException {
	    	
	    	return Fachada.getInstance().getDocumentosAduanero(codDocum);
	    }

	    public void insertDocumentAduanero(DocumentoAuaneroVO documentoAuaneroVO) throws IngresandoDocumentoAduaneroException, InicializandoException {
	    	
	    	Fachada.getInstance().insertDocumentAduanero(documentoAuaneroVO);
	    }
	
	
}
