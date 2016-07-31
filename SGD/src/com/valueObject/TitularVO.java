package com.valueObject;

import com.logica.Documento;

public class TitularVO extends AuditoriaVO{

	
	private int codigo;
	private String nombre;
	private String tel;
	private String direccion;
	private String mail;
	private boolean activo;
	
	private String codigoDoc;
	private String nombreDoc;
	private String numeroDoc;
	
	public void copiar(TitularVO t){
		
		this.codigo = t.getCodigo();
		this.nombre = t.getNombre();
		this.tel = t.getTel();
		this.direccion = t.getDireccion();
		this.mail = t.getMail();
		this.activo = t.isActivo();
		this.codigoDoc = t.getCodigoDoc();
		this.nombreDoc = t.getNombreDoc();
		this.numeroDoc = t.getNumeroDoc();
		
	}


	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public boolean isActivo() {
		return activo;
	}
	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	public String getCodigoDoc() {
		return codigoDoc;
	}

	public void setCodigoDoc(String codigoDoc) {
		this.codigoDoc = codigoDoc;
	}

	public String getNombreDoc() {
		return nombreDoc;
	}

	public void setNombreDoc(String nombreDoc) {
		this.nombreDoc = nombreDoc;
	}

	public String getNumeroDoc() {
		return numeroDoc;
	}

	public void setNumeroDoc(String numeroDoc) {
		this.numeroDoc = numeroDoc;
	}
	
}
