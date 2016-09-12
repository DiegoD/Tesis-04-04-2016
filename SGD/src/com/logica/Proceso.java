package com.logica;

import java.sql.Date;
import java.sql.Timestamp;

import com.valueObject.MonedaInfoVO;
import com.valueObject.cliente.ClienteInfoVO;
import com.valueObject.proceso.ProcesoVO;

public class Proceso extends Auditoria{

	private ClienteInfo clienteInfo;
	private DocumentoAduanero documento;
	private MonedaInfo monedaInfo;
	
	private int codigo;
	private Timestamp fecha;
	private int nroMega;
	private int nroDocum;
	private Timestamp fecDocum;
	private String carpeta;
	private float impMo;
	private float impMn;
	private float impTr;
	private float tcMov;
	private float kilos;
	private Timestamp fecCruce;
	private String marca;
	private String medio;
	private String descripcion;
	private String observaciones;
	
	public Proceso(){}
	
	
	public Proceso(ClienteInfo clienteInfo, DocumentoAduanero documento, int codigo, Timestamp fecha, int nroMega,
			Integer nroDocum, Timestamp fecDocum, String carpeta, float impMo, float impMn, float impTr, float tcMov,
			float kilos, Timestamp fecCruce, String marca, String medio, String descripcion, String observaciones,
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
	
	public Proceso(int codigo){
		this.codigo = codigo;
	}


	public Proceso(ProcesoVO procVO){
		super(procVO.getUsuarioMod(), procVO.getFechaMod(), procVO.getOperacion());
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
	public Timestamp getFecha() {
		return fecha;
	}
	public void setFecha(Timestamp fecha) {
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
	public Integer getNroDocum() {
		return nroDocum;
	}
	public void setNroDocum(Integer nroDocum) {
		this.nroDocum = nroDocum;
	}
	public Timestamp getFecDocum() {
		return fecDocum;
	}
	public void setFecDocum(Timestamp fecDocum) {
		this.fecDocum = fecDocum;
	}
	public String getCarpeta() {
		return carpeta;
	}
	public void setCarpeta(String carpeta) {
		this.carpeta = carpeta;
	}
	public float getImpMo() {
		return impMo;
	}
	public void setImpMo(float impMo) {
		this.impMo = impMo;
	}
	public float getImpMn() {
		return impMn;
	}
	public void setImpMn(float impMn) {
		this.impMn = impMn;
	}
	public float getImpTr() {
		return impTr;
	}
	public void setImpTr(float impTr) {
		this.impTr = impTr;
	}
	public float getTcMov() {
		return tcMov;
	}
	public void setTcMov(float tcMov) {
		this.tcMov = tcMov;
	}
	public float getKilos() {
		return kilos;
	}
	public void setKilos(float kilos) {
		this.kilos = kilos;
	}
	
	
	public DocumentoAduanero getDocumento() {
		return documento;
	}

	public void setDocumento(DocumentoAduanero documento) {
		this.documento = documento;
	}

	public Timestamp getFecCruce() {
		return fecCruce;
	}

	public void setFecCruce(Timestamp fecCruce) {
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
