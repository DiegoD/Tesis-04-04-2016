package com.logica;

import java.util.ArrayList;

import com.valueObject.banco.BancoVO;
import com.valueObject.banco.CtaBcoVO;

public class Banco extends Auditoria{

	private String codigo;
	private String nombre;
	private String tel;
	private String direccion;
	private String contacto;
	private boolean activo;
	private String codEmp;
	

	public ArrayList<CtaBco> getLstCtas() {
		return lstCtas;
	}

	public void setLstCtas(ArrayList<CtaBco> lstCtas) {
		this.lstCtas = lstCtas;
	}
	private ArrayList<CtaBco> lstCtas;
	
	public Banco(){
		
		this.lstCtas = new ArrayList<CtaBco>();
		
	}
	
	public Banco(BancoVO bcoVO){
		
		this.codigo = bcoVO.getCodigo();
		this.nombre = bcoVO.getNombre();
		this.tel = bcoVO.getTel();
		this.direccion = bcoVO.getDireccion();
		this.contacto = bcoVO.getContacto();
		this.activo = bcoVO.isActivo();
		
		this.lstCtas = new ArrayList<CtaBco>();
		
		for (CtaBcoVO ctaBcoVO : bcoVO.getLstCtas()) {
			
			this.lstCtas.add(new CtaBco(ctaBcoVO));
		}
		
	}
	
	/**
	*Nos retorna un BancoVO
	*
	*/
	public BancoVO getBancoVO(){
		
		BancoVO bcoVO = new BancoVO();
		
		bcoVO.setCodigo(this.codigo);
		bcoVO.setNombre(this.nombre);
		bcoVO.setTel(this.tel);
		bcoVO.setDireccion(this.direccion);
		bcoVO.setContacto(this.contacto);
		bcoVO.setActivo(this.activo);
		
		this.lstCtas = new ArrayList<CtaBco>();
		
		for (CtaBco ctaBco : this.getLstCtas()) {
			
			bcoVO.getLstCtas().add(ctaBco.getCtaBcoVO());
		}
		
		return bcoVO;
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
