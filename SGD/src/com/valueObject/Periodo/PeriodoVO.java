package com.valueObject.Periodo;

import com.valueObject.AuditoriaVO;
import com.valueObject.MonedaVO;

public class PeriodoVO extends AuditoriaVO{
	
	private String mes;
	private String anio;
	private Boolean abierto;
	
	public PeriodoVO(){
		
	}
	
	public PeriodoVO(String mes, String anio, Boolean abierto) {
		super();
		this.mes = mes;
		this.anio = anio;
		this.abierto = abierto;
	}
	
	public void copiar(PeriodoVO periodoVO){
		
		this.setMes(periodoVO.getMes());
		this.setAnio(periodoVO.getAnio());
		this.setAbierto(periodoVO.getAbierto());
		
		this.setUsuarioMod(periodoVO.getUsuarioMod());
		this.setFechaMod(periodoVO.getFechaMod());
		this.setOperacion(periodoVO.getOperacion());

	}

	public String getMes() {
		return mes;
	}
	public void setMes(String mes) {
		this.mes = mes;
	}
	public String getAnio() {
		return anio;
	}
	public void setAnio(String anio) {
		this.anio = anio;
	}
	public Boolean getAbierto() {
		return abierto;
	}
	public void setAbierto(Boolean abierto) {
		this.abierto = abierto;
	}
	
	

}
