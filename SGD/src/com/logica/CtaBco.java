package com.logica;

import com.valueObject.MonedaVO;
import com.valueObject.banco.CtaBcoVO;

public class CtaBco extends Auditoria{

	private String codigo;
	private String nombre;
	private String codBco;
	private String codEmp;
	private boolean activo;
	
	private Moneda moneda;
	
	

	public CtaBco(){
		this.moneda = new Moneda();
	}
	
	public CtaBco(CtaBcoVO vo){
		
		this.codigo = vo.getCodigo();
		this.nombre = vo.getNombre();
		this.codBco = vo.getCodBco();
		this.codEmp = vo.getCodEmp();
		this.activo = vo.isActivo();
		this.moneda = new Moneda(vo.getMonedaVO());
		
		this.setUsuarioMod(vo.getUsuarioMod());
		this.setOperacion(vo.getOperacion());
		this.setFechaMod(vo.getFechaMod());
		
	}
	
	/**
	*Nos retorna un CtaBcoVO
	*
	*/
	public CtaBcoVO getCtaBcoVO()
	{
		CtaBcoVO vo = new CtaBcoVO();

		vo.setCodigo(this.codigo);
		vo.setNombre(this.nombre);
		vo.setCodBco(this.codBco);
		vo.setCodEmp(this.codEmp);
		vo.setActivo(this.activo);
		vo.setFechaMod(this.getFechaMod());
		vo.setUsuarioMod(this.getUsuarioMod());
		vo.setOperacion(this.getOperacion());
		
		vo.setMonedaVO(this.moneda.getMonedaVO());
		
		return vo;
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
	public String getCodBco() {
		return codBco;
	}
	public void setCodBco(String codBco) {
		this.codBco = codBco;
	}
	public String getCodEmp() {
		return codEmp;
	}
	public void setCodEmp(String codEmp) {
		this.codEmp = codEmp;
	}
	public boolean isActivo() {
		return activo;
	}
	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	
	public Moneda getMoneda() {
		return moneda;
	}

	public void setMoneda(Moneda moneda) {
		this.moneda = moneda;
	}
}
