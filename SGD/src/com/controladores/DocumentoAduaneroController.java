package com.controladores;

import java.util.ArrayList;

import com.excepciones.documentosAduaneros.IngresandoDocumentoAduaneroException;
import com.excepciones.documentosAduaneros.ObteniendoDocumentoAduaneroException;
import com.logica.Fachada;
import com.valueObject.DocumentoAuaneroVO;

public class DocumentoAduaneroController {

	 public ArrayList<DocumentoAuaneroVO> getDocumentosAduanerosActivos() throws ObteniendoDocumentoAduaneroException  {
	    	
	    	return Fachada.getInstance().getDocumentosAduanerosActivos();
	    }
	    
	    public ArrayList<DocumentoAuaneroVO> getDocumentosAduanerosTodos() throws ObteniendoDocumentoAduaneroException  {
	    	
	    	return Fachada.getInstance().getDocumentosAduanerosTodos();
	    }
	    
	    public DocumentoAuaneroVO  getDocumentosAduanero(int codDocum) throws ObteniendoDocumentoAduaneroException {
	    	
	    	return Fachada.getInstance().getDocumentosAduanero(codDocum);
	    }

	    public void insertDocumentAduanero(DocumentoAuaneroVO documentoAuaneroVO) throws IngresandoDocumentoAduaneroException{
	    	
	    	Fachada.getInstance().insertDocumentAduanero(documentoAuaneroVO);
	    }
	
	
}
