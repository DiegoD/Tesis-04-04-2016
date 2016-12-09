package com.logica.Periodo;

import com.logica.Auditoria;
import com.valueObject.Periodo.PeriodoVO;

public class Periodo extends Auditoria{
	
	private String mes;
	private Integer anio;
	private Boolean abierto;
	
	public Periodo(){
		
	}
	
	public Periodo(String mes, Integer anio, Boolean abierto) {
		super();
		this.mes = mes;
		this.anio = anio;
		this.abierto = abierto;
	}

	public Periodo(PeriodoVO periodoVO){
		super(periodoVO.getUsuarioMod(), periodoVO.getFechaMod(), periodoVO.getOperacion());
		this.mes = periodoVO.getMes();
		this.anio = Integer.parseInt(periodoVO.getAnio());
		this.abierto = periodoVO.getAbierto();
		
	}
	
	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}

	public Integer getAnio() {
		return anio;
	}

	public void setAnio(Integer anio) {
		this.anio = anio;
	}

	public Boolean getAbierto() {
		return abierto;
	}

	public void setAbierto(Boolean abierto) {
		this.abierto = abierto;
	}
	
}
