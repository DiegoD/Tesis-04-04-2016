package com.logica;

import java.util.ArrayList;

import com.valueObject.RubroVO;
import com.valueObject.Cuenta.CuentaVO;

public class Cuenta extends Auditoria{
	
	private String cod_cuenta;
	private String descripcion;
	private boolean activo;
	private ArrayList<Rubro> lstRubros;
	
	public Cuenta(){
		this.lstRubros = new ArrayList<Rubro>();
	}
	
	public Cuenta(CuentaVO cuentaVO){
		
		super(cuentaVO.getUsuarioMod(), cuentaVO.getFechaMod(), cuentaVO.getOperacion());
		
		this.cod_cuenta = cuentaVO.getCodCuenta();
		this.descripcion = cuentaVO.getDescripcion();
		this.activo = cuentaVO.isActivo();
		this.lstRubros = new ArrayList<Rubro>();
		Rubro rubroAux;
		for(RubroVO rubroVO : cuentaVO.getLstRubros()){
			rubroAux = new Rubro(rubroVO);
			this.lstRubros.add(rubroAux);
		}
	}
	
	
	public String getCod_cuenta() {
		return cod_cuenta;
	}

	public void setCod_cuenta(String cod_cuenta) {
		this.cod_cuenta = cod_cuenta;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public ArrayList<Rubro> getLstRubros() {
		return lstRubros;
	}

	public void setLstRubros(ArrayList<Rubro> lstRubros) {
		this.lstRubros = lstRubros;
	}
	
	
}
