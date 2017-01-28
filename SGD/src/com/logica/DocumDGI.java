package com.logica;

import com.valueObject.DocumDGIVO;

public class DocumDGI extends Auditoria{
	private String codigo;
	private String nombre;
	private boolean activo;
	
	public DocumDGI(){}

	public DocumDGI(DocumDGIVO documentoVO){
		
		super(documentoVO.getUsuarioMod(), documentoVO.getFechaMod(), documentoVO.getOperacion());
		
		this.codigo = documentoVO.getcodDocumento();
		this.nombre = documentoVO.getdescripcion();
		this.activo = documentoVO.isActivo();
	} 

	public String getCod_docucmento() {
		return codigo;
	}

	public void setCod_docucmento(String cod_docucmento) {
		this.codigo = cod_docucmento;
	}

	public String getDescirpcion() {
		return nombre;
	}

	public void setDescirpcion(String descirpcion) {
		this.nombre = descirpcion;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	

}
