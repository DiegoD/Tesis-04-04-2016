package com.valueObject.Cuenta;

import java.util.ArrayList;

import com.valueObject.AuditoriaVO;
import com.valueObject.RubroVO;

public class CuentaVO extends AuditoriaVO{
	
	private String codCuenta;
	private String descripcion;
	private boolean activo;
	private ArrayList<RubroVO> lstRubros;
	
	public CuentaVO(){
		this.lstRubros = new ArrayList<RubroVO>();
	}
	

	/**
	 * Copiamos todos los datos de la CuentaVO pasado
	 * por parametro
	 *
	 */
	
	public void copiar(CuentaVO cuentaVO){
		
		
		this.setUsuarioMod(cuentaVO.getUsuarioMod());
		this.setFechaMod(cuentaVO.getFechaMod());
		this.setOperacion(cuentaVO.getOperacion());
		
		this.setCodCuenta(cuentaVO.getCodCuenta());
		this.setDescripcion(cuentaVO.getDescripcion());
		this.setActivo(cuentaVO.isActivo());
		
		this.lstRubros = new ArrayList<RubroVO>();
		
		if(cuentaVO.getLstRubros() != null){
			
			RubroVO rubroVO;
			for(RubroVO aux : cuentaVO.getLstRubros()){
				
				rubroVO = new RubroVO();
				
				rubroVO.setcodRubro(aux.getcodRubro());
				rubroVO.setDescripcion(aux.getDescripcion());
				rubroVO.setActivo(aux.isActivo());
				rubroVO.setCodigoImpuesto(aux.getCodigoImpuesto());
				rubroVO.setDescripcionImpuesto(aux.getDescripcionImpuesto());
				rubroVO.setPorcentajeImpuesto(aux.getPorcentajeImpuesto());
				rubroVO.setActivoImpuesto(aux.isActivoImpuesto());
				rubroVO.setDescripcionTipoRubro(aux.getDescripcionTipoRubro());
				rubroVO.setCodTipoRubro(aux.getCodTipoRubro());
				
				this.lstRubros.add(rubroVO);
			}
		}
	}
	
	public String getCodCuenta() {
		return codCuenta;
	}

	public void setCodCuenta(String codCuenta) {
		this.codCuenta = codCuenta;
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

	public ArrayList<RubroVO> getLstRubros() {
		return lstRubros;
	}

	public void setLstRubros(ArrayList<RubroVO> lstRubros) {
		this.lstRubros = lstRubros;
	}

}
