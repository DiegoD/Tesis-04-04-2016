package com.logica;

import com.valueObject.DocumentoAduaneroVO;
import com.valueObject.EmpresaVO;

public class DocumentoAduanero extends Auditoria {
	private String cod_docucmento;
	private String descirpcion;
	private boolean activo;
	
	public DocumentoAduanero(){
		
	}

	public DocumentoAduanero(String cod_docucmento, String descirpcion, boolean activo) {
		super();
		this.cod_docucmento = cod_docucmento;
		this.descirpcion = descirpcion;
		this.activo = activo;
	}

	public DocumentoAduanero(DocumentoAduaneroVO documentoVO){
		
		super(documentoVO.getUsuarioMod(), documentoVO.getFechaMod(), documentoVO.getOperacion());
		
		this.cod_docucmento = documentoVO.getcodDocumento();
		this.descirpcion = documentoVO.getdescripcion();
		this.activo = documentoVO.isActivo();
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
