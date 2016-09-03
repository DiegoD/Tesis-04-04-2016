package com.valueObject.banco;

import java.util.ArrayList;

import com.valueObject.AuditoriaVO;

public class BancoVO extends AuditoriaVO{

	private String codigo;
	private String nombre;
	private String tel;
	private String direccion;
	private String contacto;
	private boolean activo;
	private String codEmp;
	

	private ArrayList<CtaBcoVO> lstCtas;
	
	public BancoVO(){
		this.lstCtas = new ArrayList<CtaBcoVO>();
	}

	public void copiar(BancoVO vo){
		
		this.codigo = vo.codigo;
		this.nombre = vo.nombre;
		this.tel = vo.tel;
		this.direccion = vo.direccion;
		this.contacto = vo.contacto;
		this.activo = vo.activo;
		
		this.lstCtas = new ArrayList<CtaBcoVO>();
		
		CtaBcoVO aux;
		
		for (CtaBcoVO ctaBcoVO : vo.getLstCtas()) {
			
			aux = new CtaBcoVO();
			aux.Copiar(ctaBcoVO);
			
			this.lstCtas.add(aux);
		}
		
	}
	
	public ArrayList<CtaBcoVO> getLstCtas() {
		return lstCtas;
	}

	public void setLstCtas(ArrayList<CtaBcoVO> lstCtas) {
		this.lstCtas = lstCtas;
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
	public String getContacto() {
		return contacto;
	}
	public void setContacto(String contacto) {
		this.contacto = contacto;
	}
	public boolean isActivo() {
		return activo;
	}
	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	
	public String getCodEmp() {
		return codEmp;
	}

	public void setCodEmp(String codEmp) {
		this.codEmp = codEmp;
	}
	
}
