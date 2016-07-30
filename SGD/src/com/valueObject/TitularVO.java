package com.valueObject;

import com.logica.Documento;

public class TitularVO extends AuditoriaVO{

private DocumentoVO documento;
	
	private int codigo;
	private String nombre;
	private String tel;
	private String direccion;
	private String mail;
	private boolean activo;
		

	public DocumentoVO getDocumento() {
		return documento;
	}
	public void setDocumento(DocumentoVO documento) {
		this.documento = documento;
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
	
	
}
