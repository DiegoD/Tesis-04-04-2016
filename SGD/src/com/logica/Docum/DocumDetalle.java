package com.logica.Docum;

import com.valueObject.Docum.DocumDetalleVO;
import com.valueObject.IngresoCobro.IngresoCobroDetalleVO;


public abstract class DocumDetalle extends DatosDocum{
	
	private String codProceso;
	private String descProceso;
	private String referencia;
	
	private double impImpuMn;
	private double impImpuMo ;
	private double impSubMn ;
	private double impSubMo ;
	
	private double tcMov;
	private RubroInfo rubroInfo;
	
	private ImpuestoInfo impuestoInfo;
	
	private int linea;
	private String estadoGasto;
	
	
	public DocumDetalle(){
		super();
		this.impuestoInfo = new ImpuestoInfo();
		this.rubroInfo = new RubroInfo();
	}
	
	public DocumDetalle(DocumDetalleVO t){
		
		super(t);
		
		this.impuestoInfo = new ImpuestoInfo(t.getCodImpuesto(), t.getNomImpuesto(), t.getPorcentajeImpuesto());
		
		this.impImpuMn = t.getImpImpuMn();
		this.impImpuMo = t.getImpImpuMo();
		this.impSubMn = t.getImpSubMn();
		this.impSubMo = t.getImpSubMo();
		this.tcMov = t.getTcMov();
		this.codProceso = t.getCodProceso();
		this.descProceso = t.getDescProceso();
		this.rubroInfo = new RubroInfo(t.getCodRubro(), t.getNomRubro());
		this.linea = t.getLinea();
		this.referencia = t.getReferencia();
		this.estadoGasto = t.getEstadoGasto();
	}
	
	


	public double getImpImpuMn() {
		return impImpuMn;
	}

	public void setImpImpuMn(double impImpuMn) {
		this.impImpuMn = impImpuMn;
	}

	public double getImpImpuMo() {
		return impImpuMo;
	}

	public void setImpImpuMo(double impImpuMo) {
		this.impImpuMo = impImpuMo;
	}

	public double getImpSubMn() {
		return impSubMn;
	}

	public void setImpSubMn(double impSubMn) {
		this.impSubMn = impSubMn;
	}

	public double getImpSubMo() {
		return impSubMo;
	}

	public void setImpSubMo(double impSubMo) {
		this.impSubMo = impSubMo;
	}

	
	public double getTcMov() {
		return tcMov;
	}

	public void setTcMov(double tcMov) {
		this.tcMov = tcMov;
	}

//	public CuentaInfo getCuenta() {
//		return cuenta;
//	}
//
//	public void setCuenta(CuentaInfo cuenta) {
//		this.cuenta = cuenta;
//	}


	public String getCodProceso() {
		return codProceso;
	}

	public void setCodProceso(String codProceso) {
		this.codProceso = codProceso;
	}
	
	

	public String getDescProceso() {
		return descProceso;
	}

	public void setDescProceso(String descProceso) {
		this.descProceso = descProceso;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public ImpuestoInfo getImpuestoInfo() {
		return impuestoInfo;
	}

	public void setImpuestoInfo(ImpuestoInfo impuestoInfo) {
		this.impuestoInfo = impuestoInfo;
	}

	public RubroInfo getRubroInfo() {
		return rubroInfo;
	}

	public void setRubroInfo(RubroInfo rubroInfo) {
		this.rubroInfo = rubroInfo;
	}


	public int getLinea() {
		return linea;
	}

	public void setLinea(int linea) {
		this.linea = linea;
	}

	public String getEstadoGasto() {
		return estadoGasto;
	}

	public void setEstadoGasto(String estadoGasto) {
		this.estadoGasto = estadoGasto;
	}

	

	
	
}
