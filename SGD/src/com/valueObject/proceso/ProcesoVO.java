package com.valueObject.proceso;

import java.sql.Date;
import java.sql.Timestamp;

import com.valueObject.AuditoriaVO;
import com.valueObject.MonedaInfoVO;
import com.valueObject.cliente.ClienteInfoVO;

public class ProcesoVO extends AuditoriaVO{

	private ClienteInfoVO clieteInfoVO;
	private MonedaInfoVO monedaInfoVO;
	

	private int codigo;
	private Date fecha;
	private int nroMega;
	private String codDocum;
	private String nroDocum;
	private Date fecDocum;
	private String carpeta;
	private double impMo;
	private double impMn;
	private double impTr;
	private float tcMov;
	private double kilos;
	private String marca;
	private String medio;
	private String descripcion;
	private String observaciones;
	private boolean activo;
	
	
	public ClienteInfoVO getClieteInfoVO() {
		return clieteInfoVO;
	}
	public void setClieteInfoVO(ClienteInfoVO clieteInfoVO) {
		this.clieteInfoVO = clieteInfoVO;
	}
	
	
	public MonedaInfoVO getMonedaInfoVO() {
		return monedaInfoVO;
	}
	public void setMonedaInfoVO(MonedaInfoVO monedaInfoVO) {
		this.monedaInfoVO = monedaInfoVO;
	}
	
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public int getNroMega() {
		return nroMega;
	}
	public void setNroMega(int nroMega) {
		this.nroMega = nroMega;
	}
	public String getCodDocum() {
		return codDocum;
	}
	public void setCodDocum(String codDocum) {
		this.codDocum = codDocum;
	}
	public String getNroDocum() {
		return nroDocum;
	}
	public void setNroDocum(String nroDocum) {
		this.nroDocum = nroDocum;
	}
	public Date getFecDocum() {
		return fecDocum;
	}
	public void setFecDocum(Date fecDocum) {
		this.fecDocum = fecDocum;
	}
	public String getCarpeta() {
		return carpeta;
	}
	public void setCarpeta(String carpeta) {
		this.carpeta = carpeta;
	}
	
	public double getImpMo() {
		return impMo;
	}
	public void setImpMo(double impMo) {
		this.impMo = impMo;
	}
	public double getImpMn() {
		return impMn;
	}
	public void setImpMn(double impMn) {
		this.impMn = impMn;
	}
	public double getImpTr() {
		return impTr;
	}
	public void setImpTr(double impTr) {
		this.impTr = impTr;
	}
	public float getTcMov() {
		return tcMov;
	}
	public void setTcMov(float tcMov) {
		this.tcMov = tcMov;
	}
	public double getKilos() {
		return kilos;
	}
	public void setKilos(double kilos) {
		this.kilos = kilos;
	}
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public String getMedio() {
		return medio;
	}
	public void setMedio(String medio) {
		this.medio = medio;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	public boolean isActivo() {
		return activo;
	}
	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	
	
	
	
	
	
	
}
