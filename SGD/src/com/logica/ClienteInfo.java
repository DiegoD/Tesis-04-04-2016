package com.logica;

import com.valueObject.cliente.ClienteInfoVO;

public class ClienteInfo {

	private String codigo;
	private String nombre;
	
	public ClienteInfo(){}
	
	public ClienteInfo(String codigo, String nombre){
		
		this.codigo = codigo;
		this.nombre = nombre;
	}
	
	public ClienteInfo(ClienteInfoVO cliInfVO){
		
		this.codigo = cliInfVO.getCodigo();
		this.nombre = cliInfVO.getNombre();
		
	}
	
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
