package com.controladores;

import java.util.ArrayList;

import com.excepciones.ConexionException;
import com.excepciones.ErrorInesperadoException;
import com.excepciones.InicializandoException;
import com.excepciones.NoTienePermisosException;
import com.excepciones.ObteniendoPermisosException;
import com.excepciones.Documentos.ExisteDocumentoException;
import com.excepciones.Documentos.InsertandoDocumentoException;
import com.excepciones.Documentos.ModificandoDocumentoException;
import com.excepciones.Documentos.NoExisteDocumentoException;
import com.excepciones.Documentos.ObteniendoDocumentosException;
import com.logica.Fachada;
import com.logica.FachadaDD;
import com.valueObject.DocumentoAduaneroVO;
import com.valueObject.UsuarioPermisosVO;

public class DocumentoControlador {
	
	public DocumentoControlador(){
		
	}

	/**
	 * Obtiene array list de VO de todos los documentos
	 * @throws NoTienePermisosException 
	 * @throws ObteniendoPermisosException 
	 */
	public ArrayList<DocumentoAduaneroVO> getDocumentos(UsuarioPermisosVO permisos) throws ObteniendoDocumentosException, ConexionException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException {
		
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			return FachadaDD.getInstance().getDocumentos(permisos.getCodEmp());
		else
			throw new NoTienePermisosException();
	}
	
	/**
	 * Inserta un nuevo documento
	 * @throws NoTienePermisosException 
	 * @throws ObteniendoPermisosException 
	 */
	public void insertarDocumento(DocumentoAduaneroVO documentoVO, UsuarioPermisosVO permisos) throws InsertandoDocumentoException, ExisteDocumentoException, InicializandoException, ConexionException, ErrorInesperadoException, ObteniendoPermisosException, NoTienePermisosException
	{
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			FachadaDD.getInstance().insertarDocumento(documentoVO, permisos.getCodEmp());
		else
			throw new NoTienePermisosException();
	}
	
	
	/**
	 * Actualiza los datos de un documento
	 */
	public void actualizarDocumento(DocumentoAduaneroVO documentoVO, UsuarioPermisosVO permisos) throws ConexionException, NoExisteDocumentoException, ModificandoDocumentoException, ExisteDocumentoException, InicializandoException {
		FachadaDD.getInstance().actualizarDocumento(documentoVO, permisos.getCodEmp());
	}
}
