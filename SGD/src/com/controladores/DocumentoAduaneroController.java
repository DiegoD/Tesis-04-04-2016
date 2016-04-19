package com.controladores;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import com.excepciones.documentosAduaneros.IngresandoDocumentoAduaneroException;
import com.excepciones.documentosAduaneros.ObteniendoDocumentoAduaneroException;
import com.logica.Fachada;
import com.valueObject.DocumentoAuaneroVO;

public class DocumentoAduaneroController {

	 public ArrayList<DocumentoAuaneroVO> getDocumentosAduanerosActivos() throws ObteniendoDocumentoAduaneroException, InstantiationException, IllegalAccessException, ClassNotFoundException, FileNotFoundException, IOException  {
	    	
	    	return Fachada.getInstance().getDocumentosAduanerosActivos();
	    }
	    
	    public ArrayList<DocumentoAuaneroVO> getDocumentosAduanerosTodos() throws ObteniendoDocumentoAduaneroException, InstantiationException, IllegalAccessException, ClassNotFoundException, FileNotFoundException, IOException  {
	    	
	    	return Fachada.getInstance().getDocumentosAduanerosTodos();
	    }
	    
	    public DocumentoAuaneroVO  getDocumentosAduanero(int codDocum) throws ObteniendoDocumentoAduaneroException, InstantiationException, IllegalAccessException, ClassNotFoundException, FileNotFoundException, IOException {
	    	
	    	return Fachada.getInstance().getDocumentosAduanero(codDocum);
	    }

	    public void insertDocumentAduanero(DocumentoAuaneroVO documentoAuaneroVO) throws IngresandoDocumentoAduaneroException, InstantiationException, IllegalAccessException, ClassNotFoundException, FileNotFoundException, IOException{
	    	
	    	Fachada.getInstance().insertDocumentAduanero(documentoAuaneroVO);
	    }
	
	
}
