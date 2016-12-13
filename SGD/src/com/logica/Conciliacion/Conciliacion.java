package com.logica.Conciliacion;

import java.sql.Timestamp;
import java.util.ArrayList;

import com.logica.Auditoria;
import com.logica.MonedaInfo;
import com.logica.Docum.BancoInfo;
import com.logica.Docum.CuentaBcoInfo;
import com.valueObject.Conciliaciones.ConciliacionDetalleVO;
import com.valueObject.Conciliaciones.ConciliacionVO;

public class Conciliacion extends Auditoria{

	private String cod_docum;
	private String serie_docum;
	private int nro_docum;
	private String cod_emp;
	private double impTotMn;
	private double impTotMo;
	private String cuenta;
	private long nroTrans;
	private Timestamp fecValor;
	private Timestamp fecDoc;
	private String observaciones;
	private MonedaInfo moneda;
	private BancoInfo banco;
	private CuentaBcoInfo cuentaBanco;
	private String tipo;
	
	private ArrayList<ConciliacionDetalle> lstDetalle;
	
	public Conciliacion(){
		lstDetalle = new ArrayList<ConciliacionDetalle>();
	}
	
	public ConciliacionVO getConciliacionVO(Conciliacion conciliacion){
		
		ConciliacionVO conciliacionVO = new ConciliacionVO();
		
		conciliacionVO.setSerieDocum(conciliacion.getSerie_docum());
		conciliacionVO.setNroDocum(String.valueOf(conciliacion.getNro_docum()));
		conciliacionVO.setCodDocum(conciliacion.getCod_docum());
		conciliacionVO.setCod_emp(conciliacion.getCod_emp());
		conciliacionVO.setImpTotMn(conciliacion.getImpTotMn());
		conciliacionVO.setImpTotMo(conciliacion.getImpTotMo());
		conciliacionVO.setCuenta(conciliacion.getCuenta());
		conciliacionVO.setNroTrans(conciliacion.getNroTrans());
		conciliacionVO.setFecValor(conciliacion.getFecValor());
		conciliacionVO.setFecDoc(conciliacion.getFecDoc());
		conciliacionVO.setObservaciones(conciliacion.getObservaciones());
		conciliacionVO.setCodMoneda(conciliacion.getMoneda().getCodMoneda());
		conciliacionVO.setDescripcion(conciliacion.getMoneda().getDescripcion());
		conciliacionVO.setSimbolo(conciliacion.getMoneda().getSimbolo());
		conciliacionVO.setNacional(conciliacion.getMoneda().isNacional());
		conciliacionVO.setNomBanco(conciliacion.getBanco().getNomBanco());
		conciliacionVO.setCodBanco(conciliacion.getBanco().getCodBanco());
		conciliacionVO.setNomCuenta(conciliacion.getCuentaBanco().getNomCuenta());
		conciliacionVO.setCodCuenta(conciliacion.getCuentaBanco().getCodCuenta());
		conciliacionVO.setUsuarioMod(conciliacion.getUsuarioMod());
		conciliacionVO.setFechaMod(conciliacion.getFechaMod());
		conciliacionVO.setOperacion(conciliacion.getOperacion());
		conciliacionVO.setTipo(conciliacion.getTipo());
		
		ConciliacionDetalleVO auxDet;
		lstDetalle = conciliacion.getLstDetalle();
		
		for (ConciliacionDetalle conciliacionDetalle: lstDetalle) {
			auxDet = new ConciliacionDetalleVO();
			
			auxDet = conciliacionDetalle.retornarConciliacionDetalleVO(conciliacionDetalle);
			
			conciliacionVO.getLstDetalle().add(auxDet);
			
		}
		
		return conciliacionVO;
	}
	
	
	public Conciliacion convierteConciliacion(ConciliacionVO conciliacionVO){
		
		Conciliacion conciliacion = new Conciliacion();
		
		BancoInfo banco = new BancoInfo();
		banco.setCodBanco(conciliacionVO.getCodBanco());
		banco.setNomBanco(conciliacionVO.getNomBanco());
		conciliacion.setBanco(banco);
		
		CuentaBcoInfo cuenta = new CuentaBcoInfo();
		cuenta.setCodCuenta(conciliacionVO.getCodCuenta());
		cuenta.setNomCuenta(conciliacionVO.getNomCuenta());
		cuenta.setCodMoneda(conciliacionVO.getCodMoneda());
		cuenta.setNacional(conciliacionVO.isNacional());
		conciliacion.setCuentaBanco(cuenta);
		
		MonedaInfo moneda = new MonedaInfo();
		moneda.setCodMoneda(conciliacionVO.getCodMoneda());
		moneda.setDescripcion(conciliacionVO.getDescripcion());
		conciliacion.setMoneda(moneda);
		
		conciliacion.setCod_docum(conciliacionVO.getCodDocum());
		conciliacion.setSerie_docum(conciliacionVO.getSerieDocum());
		conciliacion.setNro_docum(Integer.parseInt(conciliacionVO.getNroDocum()));
		conciliacion.setCod_emp(conciliacionVO.getCod_emp());
		conciliacion.setNroTrans(conciliacionVO.getNroTrans());
		
		
		conciliacion.setFecDoc(conciliacionVO.getFecDoc());
		conciliacion.setFechaMod(conciliacionVO.getFecDoc());
		conciliacion.setFecValor(conciliacionVO.getFecValor());
		conciliacion.setImpTotMn(conciliacionVO.getImpTotMn());
		conciliacion.setImpTotMo(conciliacionVO.getImpTotMo());
		conciliacion.setCuenta(conciliacionVO.getCuenta());
		conciliacion.setObservaciones(conciliacionVO.getObservaciones());
		conciliacion.setUsuarioMod(conciliacionVO.getUsuarioMod());
		conciliacion.setOperacion(conciliacionVO.getOperacion());
		conciliacion.setTipo(conciliacionVO.getTipo());
		conciliacion.setCod_emp(conciliacionVO.getCod_emp());
		
		
		ConciliacionDetalle auxDetalle;
		for (ConciliacionDetalleVO conciliacionDetalleVO: conciliacionVO.getLstDetalle()) {
			
			auxDetalle = new ConciliacionDetalle();
			auxDetalle.setNroTrans(conciliacion.getNroTrans());
			auxDetalle.setSerie_docum(conciliacionDetalleVO.getSerie_docum());
			auxDetalle.setNro_docum(conciliacionDetalleVO.getNro_docum());
			auxDetalle.setFecDoc(conciliacionDetalleVO.getFecDoc());
			auxDetalle.setFecValor(conciliacionDetalleVO.getFecValor());
			auxDetalle.setNroTransDoc(conciliacionDetalleVO.getNroTransDoc());
			auxDetalle.setImpTotMn(conciliacionDetalleVO.getImpTotMn());
			auxDetalle.setImpTotMo(conciliacionDetalleVO.getImpTotMo());
			auxDetalle.setCod_docum(conciliacionDetalleVO.getCod_docum());
			auxDetalle.setCod_emp(conciliacion.getCod_emp());
			auxDetalle.setDescripcion(conciliacionDetalleVO.getDescripcion());
			
			
			
			conciliacion.getLstDetalle().add(auxDetalle);
			
		}
		
		
		return conciliacion;
		//deposito.setLstDetalle(depositoVO.getLstDetalle());
		
	}


	public String getCod_docum() {
		return cod_docum;
	}
	public void setCod_docum(String cod_docum) {
		this.cod_docum = cod_docum;
	}
	public String getSerie_docum() {
		return serie_docum;
	}
	public void setSerie_docum(String serie_docum) {
		this.serie_docum = serie_docum;
	}
	public int getNro_docum() {
		return nro_docum;
	}
	public void setNro_docum(int nro_docum) {
		this.nro_docum = nro_docum;
	}
	public String getCod_emp() {
		return cod_emp;
	}
	public void setCod_emp(String cod_emp) {
		this.cod_emp = cod_emp;
	}
	public MonedaInfo getMoneda() {
		return moneda;
	}
	public void setMoneda(MonedaInfo moneda) {
		this.moneda = moneda;
	}
	public double getImpTotMn() {
		return impTotMn;
	}
	public void setImpTotMn(double impTotMn) {
		this.impTotMn = impTotMn;
	}
	public double getImpTotMo() {
		return impTotMo;
	}
	public void setImpTotMo(double impTotMo) {
		this.impTotMo = impTotMo;
	}
	public String getCuenta() {
		return cuenta;
	}
	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
	}

	public long getNroTrans() {
		return nroTrans;
	}

	public void setNroTrans(long nroTrans) {
		this.nroTrans = nroTrans;
	}

	public Timestamp getFecValor() {
		return fecValor;
	}

	public void setFecValor(Timestamp fecValor) {
		this.fecValor = fecValor;
	}

	public Timestamp getFecDoc() {
		return fecDoc;
	}

	public void setFecDoc(Timestamp fecDoc) {
		this.fecDoc = fecDoc;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public BancoInfo getBanco() {
		return banco;
	}

	public void setBanco(BancoInfo banco) {
		this.banco = banco;
	}

	public CuentaBcoInfo getCuentaBanco() {
		return cuentaBanco;
	}

	public void setCuentaBanco(CuentaBcoInfo cuentaBanco) {
		this.cuentaBanco = cuentaBanco;
	}

	public ArrayList<ConciliacionDetalle> getLstDetalle() {
		return lstDetalle;
	}

	public void setLstDetalle(ArrayList<ConciliacionDetalle> lstDetalle) {
		this.lstDetalle = lstDetalle;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	
}
