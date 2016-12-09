package com.valueObject.Docum;

import com.valueObject.TitularVO;

public class DocumDetalleVO extends DatosDocumVO{

	private String codImpuesto;
	private String nomImpuesto;
	private double porcentajeImpuesto;
	
	private double impImpuMn;
	private double impImpuMo ;
	private double impSubMn ;
	private double impSubMo ;
	
	private double tcMov;
//	private String codCuenta;
//	private String nomCuenta;
	
	private String codProceso;
	private String descProceso;
	


	private String nomRubro;
	private String codRubro;
	private String estadoGasto;
	
	//private String codCtaInd; /*Cuenta interna del sistema*/
	
	private int linea;
	
	public DocumDetalleVO(){
		super();
	}
	
	public void copiar(DocumDetalleVO t){
		
		super.copiar(t);
	
		this.codImpuesto = t.codImpuesto;
		this.impImpuMn = t.impImpuMn;
		this.impImpuMo = t.impImpuMo;
		this.impSubMn = t.impSubMn;
		this.impSubMo = t.impSubMo;
		this.tcMov = t.tcMov;
		//this.codCuenta = t.codCuenta;
		//this.nomCuenta = t.nomCuenta;
		this.codProceso = t.codProceso;
		this.nomRubro = t.nomRubro;
		this.codRubro = t.codRubro;
		//this.codCtaInd = t.codCtaInd;
		this.linea = t.linea;
		this.descProceso = t.descProceso;
		this.estadoGasto = t.getEstadoGasto();
		this.porcentajeImpuesto = t.getPorcentajeImpuesto();
		this.nomImpuesto = t.getNomImpuesto();
		
	}

	
	public String getCodImpuesto() {
		return codImpuesto;
	}
	
	
	public void setCodImpuesto(String codImpuesto) {
		this.codImpuesto = codImpuesto;
	}
	
	
	public double getImpImpuMn() {
		return impImpuMn;
	}
	
	
	public void setImpImpuMn(double impImpu_mn) {
		this.impImpuMn = impImpu_mn;
	}
	
	
	public double getImpImpuMo() {
		return impImpuMo;
	}
	
	
	public void setImpImpuMo(double impImpu_mo) {
		this.impImpuMo = impImpu_mo;
	}
	
	
	public double getImpSubMn() {
		return impSubMn;
	}
	
	
	public void setImpSubMn(double impSub_mn) {
		this.impSubMn = impSub_mn;
	}
	
	
	public double getImpSubMo() {
		return impSubMo;
	}
	
	
	public void setImpSubMo(double impSub_mo) {
		this.impSubMo = impSub_mo;
	}
	
	
	public double getTcMov() {
		return tcMov;
	}
	
	
	public void setTcMov(double tcMov) {
		this.tcMov = tcMov;
	}


	public String getCodProceso() {
		return codProceso;
	}

	public void setCodProceso(String codProceso) {
		this.codProceso = codProceso;
	}

	public String getNomImpuesto() {
		return nomImpuesto;
	}

	public void setNomImpuesto(String nomImpuesto) {
		this.nomImpuesto = nomImpuesto;
	}

	public double getPorcentajeImpuesto() {
		return porcentajeImpuesto;
	}

	public void setPorcentajeImpuesto(double porcentajeImpuesto) {
		this.porcentajeImpuesto = porcentajeImpuesto;
	}

	public String getNomRubro() {
		return nomRubro;
	}

	public void setNomRubro(String nomRubro) {
		this.nomRubro = nomRubro;
	}

	public String getCodRubro() {
		return codRubro;
	}

	public void setCodRubro(String codRubro) {
		this.codRubro = codRubro;
	}

	public int getLinea() {
		return linea;
	}

	public void setLinea(int linea) {
		this.linea = linea;
	}

	public String getDescProceso() {
		return descProceso;
	}

	public void setDescProceso(String descProceso) {
		this.descProceso = descProceso;
	}

	public String getEstadoGasto() {
		return estadoGasto;
	}

	public void setEstadoGasto(String estadoGasto) {
		this.estadoGasto = estadoGasto;
	}
	
	
}
