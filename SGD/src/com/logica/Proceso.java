package com.logica;

import java.sql.Date;

import com.valueObject.MonedaInfoVO;
import com.valueObject.cliente.ClienteInfoVO;
import com.valueObject.proceso.ProcesoVO;

public class Proceso extends Auditoria{

	private ClienteInfo clienteInfo;
	private DocumentoAduanero documento;
	
	private int codigo;
	private Date fecha;
	private int nroMega;
	//private String codDocum;
	private String nroDocum;
	private Date fecDocum;
	private String carpeta;
	private double impMo;
	private double impMn;
	private double impTr;
	private float tcMov;
	private double kilos;
	private Date fecCruce;
	private String marca;
	private String medio;
	private String descripcion;
	private String observaciones;
	//private boolean activo;
	
	
	private MonedaInfo monedaInfo;
	
	
	public Proceso(){}
	
	
	public Proceso(ClienteInfo clienteInfo, DocumentoAduanero documento, int codigo, Date fecha, int nroMega,
			String nroDocum, Date fecDocum, String carpeta, double impMo, double impMn, double impTr, float tcMov,
			double kilos, Date fecCruce, String marca, String medio, String descripcion, String observaciones,
			MonedaInfo monedaInfo) {
		super();
		this.clienteInfo = clienteInfo;
		this.documento = documento;
		this.codigo = codigo;
		this.fecha = fecha;
		this.nroMega = nroMega;
		this.nroDocum = nroDocum;
		this.fecDocum = fecDocum;
		this.carpeta = carpeta;
		this.impMo = impMo;
		this.impMn = impMn;
		this.impTr = impTr;
		this.tcMov = tcMov;
		this.kilos = kilos;
		this.fecCruce = fecCruce;
		this.marca = marca;
		this.medio = medio;
		this.descripcion = descripcion;
		this.observaciones = observaciones;
		this.monedaInfo = monedaInfo;
	}


	public Proceso(ProcesoVO procVO){
		
		this.clienteInfo = new ClienteInfo(procVO.getCodCliente(), procVO.getNomCliente());
		this.monedaInfo = new MonedaInfo(procVO.getCodMoneda(), procVO.getDescMoneda(),
				procVO.getSimboloMoneda());
		this.documento = new DocumentoAduanero(procVO.getCodDocum(), procVO.getNomDocum());
		
		this.codigo = procVO.getCodigo();
		this.fecha = procVO.getFecha();
		this.nroMega = procVO.getNroMega();
		//this.codDocum = procVO.getCodDocum();
		this.nroDocum = procVO.getNroDocum();
		this.fecDocum = procVO.getFecDocum();
		this.carpeta = procVO.getCarpeta();
		
		this.impMo = procVO.getImpMo();
		this.impMn = procVO.getImpMn();
		this.impTr = procVO.getImpTr();
		this.tcMov = procVO.getTcMov();
		this.kilos = procVO.getKilos();
		this.fecCruce = procVO.getFecCruce();
		this.marca = procVO.getMarca();
		this.medio = procVO.getMedio();
		this.descripcion = procVO.getDescripcion();
		this.observaciones = procVO.getObservaciones();
		//this.activo = procVO.isActivo();
		
		
	}
	
	public MonedaInfo getMonedaInfo() {
		return monedaInfo;
	}

	public void setMonedaInfo(MonedaInfo monedaInfo) {
		this.monedaInfo = monedaInfo;
	}

//	public ProcesoVO retornarProcesoVO(){
//		
//		ProcesoVO vo = new ProcesoVO();
//		
//		
//		vo.setClieteInfoVO(new ClienteInfoVO(this.clienteInfo.getCodigo(), this.clienteInfo.getNombre()));
//		vo.setMonedaInfoVO(new MonedaInfoVO(this.monedaInfo.getCod_moneda(), this.monedaInfo.getDescripcion(), this.monedaInfo.getSimbolo()));
//		
//		vo.setCodigo(this.codigo);
//		vo.setFecha(this.fecha);
//		vo.setNroMega(this.nroMega);
//		//vo.setCodDocum(this.codDocum);
//		vo.setNroDocum(this.nroDocum);
//		vo.setFecDocum(this.fecDocum);
//		vo.setCarpeta(this.carpeta);
//		vo.setImpMo(this.impMo);
//		vo.setImpMn(this.impMn);
//		vo.setImpTr(this.impTr);
//		vo.setTcMov(this.tcMov);
//		vo.setKilos(this.kilos);
//		vo.setMarca(this.marca);
//		vo.setMedio(this.medio);
//		vo.setDescripcion(this.descripcion);
//		vo.setObservaciones(this.observaciones);
//		//vo.setActivo(this.activo);
//		
//		return vo;
//	}
	
	
	public ClienteInfo getClienteInfo() {
		return clienteInfo;
	}
	public void setClienteInfo(ClienteInfo clienteInfo) {
		this.clienteInfo = clienteInfo;
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
//	public String getCodDocum() {
//		return codDocum;
//	}
//	public void setCodDocum(String codDocum) {
//		this.codDocum = codDocum;
//	}
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
	
	
	public DocumentoAduanero getDocumento() {
		return documento;
	}

	public void setDocumento(DocumentoAduanero documento) {
		this.documento = documento;
	}

	public Date getFecCruce() {
		return fecCruce;
	}

	public void setFecCruce(Date fecCruce) {
		this.fecCruce = fecCruce;
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
//	public boolean isActivo() {
//		return activo;
//	}
//	public void setActivo(boolean activo) {
//		this.activo = activo;
//	}
	
	
	
	
}
