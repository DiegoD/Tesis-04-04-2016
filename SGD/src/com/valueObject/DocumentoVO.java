package com.valueObject;

import com.logica.Auditoria;

public class DocumentoVO extends Auditoria{
	private String cod_docucmento;
	private String descirpcion;
	private boolean activo;
	
	public DocumentoVO(){
		
	}
	
	public DocumentoVO(String cod_docucmento, String descirpcion, boolean activo) {
		super();
		this.cod_docucmento = cod_docucmento;
		this.descirpcion = descirpcion;
		this.activo = activo;
	}
	
	/**
	 * Copiamos todos los datos del documento pasado
	 * por parametro
	 *
	 */
	public void copiar(DocumentoVO documentoVO){

		this.setUsuarioMod(documentoVO.getUsuarioMod());
		this.setFechaMod(documentoVO.getFechaMod());
		this.setOperacion(documentoVO.getOperacion());
		
		this.cod_docucmento = documentoVO.getCod_docucmento();
		this.descirpcion = documentoVO.getDescirpcion();
		this.activo = 	documentoVO.isActivo();
	}

	public String getCod_docucmento() {
		return cod_docucmento;
	}

	public void setCod_docucmento(String cod_docucmento) {
		this.cod_docucmento = cod_docucmento;
	}

	public String getDescirpcion() {
		return descirpcion;
	}

	public void setDescirpcion(String descirpcion) {
		this.descirpcion = descirpcion;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	
	

}
