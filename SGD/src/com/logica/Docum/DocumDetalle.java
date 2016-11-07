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
	
	public IngresoCobroDetalleVO retornarDatosDocumVO(){
		
		IngresoCobroDetalleVO aux = new IngresoCobroDetalleVO();
		
		aux.setCodCuenta(this.getCuenta().getCodCuenta());
		aux.setNomCuenta(this.getCuenta().getNomCuenta());
		
		aux.setCodCtaInd(this.getCodCuentaInd());
		
		aux.setCodProceso(this.getCodProceso());
		aux.setDescProceso(this.getDescProceso());
		
		aux.setFecDoc(this.getFecDoc());
		aux.setFecValor(this.getFecValor());
		aux.setCodDocum(this.getCodDocum());
		aux.setSerieDocum(this.getSerieDocum());
		aux.setNroDocum(this.getNroDocum());
		aux.setCodEmp(this.getCodEmp());
		
		aux.setNomMoneda(this.getMoneda().getDescripcion());
		aux.setCodMoneda(this.getMoneda().getCodMoneda());
		
		aux.setSimboloMoneda(this.getMoneda().getSimbolo());
		
		aux.setNomTitular(this.getTitInfo().getNombre());
		aux.setCodTitular(this.getTitInfo().getCodigo());
		
		aux.setReferencia(this.getReferencia());
		aux.setUsuarioMod(this.getUsuarioMod());
		aux.setFechaMod(this.getFechaMod());
		aux.setOperacion(this.getOperacion());
		
		aux.setCodImpuesto(impuestoInfo.getCodImpuesto());
		aux.setNomImpuesto(impuestoInfo.getNomImpuesto());
		aux.setPorcentajeImpuesto(impuestoInfo.getPorcentaje());
		
		
		aux.setImpImpuMn(this.impImpuMn);
		aux.setImpImpuMo(this.impImpuMo);
		aux.setImpSubMn(this.impSubMn);
		aux.setImpSubMo(this.impSubMo);
		
		aux.setImpTotMn(this.getImpTotMn());
		aux.setImpTotMo(this.getImpTotMo());
		aux.setTcMov(this.tcMov);
		
		aux.setNomRubro(this.rubroInfo.getNomRubro());
		aux.setCodRubro(this.rubroInfo.getCodRubro());
		
		aux.setEstadoGasto(this.getEstadoGasto());
		
		return aux;
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
