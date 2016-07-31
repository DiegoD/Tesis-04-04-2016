package com.valueObject;

import com.logica.Auditoria;

public class DocumentoAduaneroVO extends Auditoria{
	private String codDocumento;
	private String descripcion;
	private boolean activo;
	
	public DocumentoAduaneroVO(){
		
	}
	
	public DocumentoAduaneroVO(String cod_docucmento, String descirpcion, boolean activo) {
		super();
		this.codDocumento = cod_docucmento;
		this.descripcion = descirpcion;
		this.activo = activo;
	}
	
	/**
	 * Copiamos todos los datos del documento pasado
	 * por parametro
	 *
	 */
	public void copiar(DocumentoAduaneroVO documentoVO){

		this.setUsuarioMod(documentoVO.getUsuarioMod());
		this.setFechaMod(documentoVO.getFechaMod());
		this.setOperacion(documentoVO.getOperacion());
		
		this.codDocumento = documentoVO.getcodDocumento();
		this.descripcion = documentoVO.getdescripcion();
		this.activo = 	documentoVO.isActivo();
	}

	public String getcodDocumento() {
		return codDocumento;
	}

	public void setcodDocumento(String cod_docucmento) {
		this.codDocumento = cod_docucmento;
	}

	public String getdescripcion() {
		return descripcion;
	}

	public void setdescripcion(String descirpcion) {
		this.descripcion = descirpcion;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	
	

}
