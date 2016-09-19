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
	
	public TitularInfo(TitularInfoVO t){
		
		this.codigo = t.getCodigo();
		this.nombre = t.getNombre();
	}
	
	public TitularInfoVO retornarTitularInfoVO(){
		
		TitularInfoVO aux = new TitularInfoVO();
		
		aux.setNombre(this.nombre);
		aux.setCodigo(this.codigo);
		
		return aux;
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
