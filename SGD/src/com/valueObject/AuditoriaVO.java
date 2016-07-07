package com.valueObject;

import java.sql.Date;

public abstract class AuditoriaVO {

	private Date fechaMod;
	private String usuarioMod;
	private String operacion;
	
	
	public String getOperacion() {
		return operacion;
	}

	public void setOperacion(String operacion) {
		this.operacion = operacion;
	}

	protected AuditoriaVO(){};
	
	protected AuditoriaVO(String usuario, Date fecha ){
		
		this.fechaMod = fecha;
		this.usuarioMod = usuario;
	}
		
	public Date getFechaMod() {
		return fechaMod;
	}
	public void setFechaMod(Date fechaMod) {
		this.fechaMod = fechaMod;
	}
	public String getUsuarioMod() {
		return usuarioMod;
	}
	public void setUsuarioMod(String usuarioMod) {
		this.usuarioMod = usuarioMod;
	}
	
	
}
