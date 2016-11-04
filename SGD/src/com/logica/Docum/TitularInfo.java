package com.logica.Docum;

import com.valueObject.Docum.DatosDocumVO;
import com.valueObject.Titular.TitularInfoVO;

public class TitularInfo {

	public TitularInfo(){
		
		
	}
	
	public TitularInfo(String codigo, String nombre){
		
		this.codigo = codigo;
		this.nombre = nombre;
	}
	
	public TitularInfo(String codigo, String nombre, String tipo){
		
		this.codigo = codigo;
		this.nombre = nombre;
		this.tipo   = tipo;
	}

	public TitularInfo(TitularInfoVO t){
		
		this.codigo = t.getCodigo();
		this.nombre = t.getNombre();
		this.tipo   = t.getTipo();
	}
	
	public TitularInfoVO retornarTitularInfoVO(){
		
		TitularInfoVO aux = new TitularInfoVO();
		
		aux.setNombre(this.nombre);
		aux.setCodigo(this.codigo);
		
		return aux;
	}
	
	private String codigo;
	private String nombre;
	private String tipo;
	
	
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

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	
}
