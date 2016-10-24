package com.logica;

import com.valueObject.TitularVO;
import com.valueObject.cliente.ClienteVO;

public  class Titular extends Auditoria{

	private Documento documento;
	private int codigo;
	private String nombre;
	private String tel;
	private String direccion;
	private String mail;
	private boolean activo;
	private String tipo;
	
	

	public Titular(){
		
		this.documento = new Documento();
	}
	
	
	public TitularVO retornarTitularVO(){
		
		TitularVO aux = new TitularVO();
		
		aux.setCodigo(this.getCodigo());
		aux.setNombre(this.getNombre());
		aux.setCodigoDoc(this.getDocumento().getCodigo());
		aux.setNumeroDoc(this.documento.getNumero());
		aux.setActivo(this.isActivo());
		aux.setTipo(this.getTipo());
		aux.setNombreDoc(this.documento.getNombre());
		
		return aux;
	}
	

	public Documento getDocumento() {
		return documento;
	}
	public void setDocumento(Documento documento) {
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


	public String getTipo() {
		return tipo;
	}


	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	
	
}
