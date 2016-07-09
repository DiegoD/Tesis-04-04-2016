package com.valueObject;

import java.sql.Timestamp;

public abstract class AuditoriaVO {

	private Timestamp fechaMod;
	private String usuarioMod;
	private String operacion;
	
	
	public String getOperacion() {
		return operacion;
	}

	public void setOperacion(String operacion) {
		this.operacion = operacion;
	}

	protected AuditoriaVO(){};
	
	protected AuditoriaVO(String usuario, Timestamp fecha ){
		
		this.fechaMod = fecha;
		this.usuarioMod = usuario;
	}
	
	protected AuditoriaVO(String usuario, Timestamp fecha, String operacion){
		
		this.fechaMod = fecha;
		this.usuarioMod = usuario;
	}
		
	public Timestamp getFechaMod() {
		return fechaMod;
	}
	public void setFechaMod(Timestamp fechaMod) {
		this.fechaMod = fechaMod;
	}
	public String getUsuarioMod() {
		return usuarioMod;
	}
	public void setUsuarioMod(String usuarioMod) {
		this.usuarioMod = usuarioMod;
	}
	
	
}
