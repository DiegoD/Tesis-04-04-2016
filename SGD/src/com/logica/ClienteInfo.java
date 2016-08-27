package com.logica;

import com.valueObject.cliente.ClienteInfoVO;

public class ClienteInfo {

	public ClienteInfo(){}
	
	public ClienteInfo(String codigo, String nombre){
		
		this.codigo = codigo;
		this.nombre = nombre;
	}
	
	public ClienteInfo(ClienteInfoVO cliInfVO){
		
		this.codigo = cliInfVO.getCodigo();
		this.nombre = cliInfVO.getNombre();
		
	}
	
	private String codigo;
	private String nombre;
	
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
}
