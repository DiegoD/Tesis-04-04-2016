package com.logica.Depositos;

import java.sql.Timestamp;
import java.util.ArrayList;

import com.logica.Auditoria;
import com.logica.Cheque;
import com.logica.FuncionarioInfo;
import com.logica.Grupo;
import com.logica.MonedaInfo;
import com.logica.Docum.BancoInfo;
import com.logica.Docum.CuentaBcoInfo;
import com.logica.Docum.CuentaInfo;
import com.logica.Docum.TitularInfo;
import com.logica.IngresoCobro.IngresoCobroLinea;
import com.valueObject.Deposito.DepositoDetalleVO;
import com.valueObject.Deposito.DepositoVO;
import com.valueObject.IngresoCobro.IngresoCobroDetalleVO;

public class Deposito extends Auditoria{
	
	private String codDocum;
	private String serieDocum;
	private int nroDocum;
	private Timestamp fecDoc;
	private Timestamp fecValor;
	private BancoInfo banco;
	private CuentaBcoInfo cuentaBanco;
	private MonedaInfo moneda;
	private FuncionarioInfo funcionario;
	private int numComprobante;
	private String observaciones;
	private double impTotMo;
	private double impTotMn;
	private long nroTrans;
	
	private ArrayList<DepositoDetalle> lstDetalle;
	
	public Deposito(){
		lstDetalle = new ArrayList<DepositoDetalle>();
	}
	
	public DepositoVO getDepositoVO(Deposito deposito){
		
		DepositoVO depositoVO = new DepositoVO();
		
		depositoVO.setBanco(deposito.getBanco());
		depositoVO.setCodDocum(deposito.getCodDocum());
		depositoVO.setCuentaBanco(deposito.getCuentaBanco());
		depositoVO.setFecDoc(deposito.getFecDoc());
		depositoVO.setFecValor(deposito.getFecValor());
		depositoVO.setFuncionario(deposito.getFuncionario());
		depositoVO.setImpTotMn(deposito.getImpTotMn());
		depositoVO.setImpTotMo(deposito.getImpTotMo());
		depositoVO.setMoneda(deposito.getMoneda());
		depositoVO.setNroDocum(deposito.getNroDocum());
		depositoVO.setNroTrans(deposito.getNroTrans());
		depositoVO.setNumComprobante(deposito.getNumComprobante());
		depositoVO.setObservaciones(deposito.getObservaciones());
		depositoVO.setSerieDocum(deposito.getSerieDocum());
		
		DepositoDetalleVO auxDet;
		for (DepositoDetalle depositoDetalle: lstDetalle) {
			auxDet = new DepositoDetalleVO();
			
			auxDet = depositoDetalle.retornarDepositoDetalleVO(depositoDetalle);
			
			depositoVO.getLstDetalle().add(auxDet);
			
		}
		
		return depositoVO;
		
		
	}
	
	public Deposito convierteDeposito(DepositoVO depositoVO){
		
		Deposito deposito = new Deposito();
		
		deposito.setBanco(depositoVO.getBanco());
		deposito.setCodDocum(depositoVO.getCodDocum());
		deposito.setCuentaBanco(depositoVO.getCuentaBanco());
		deposito.setFecDoc(depositoVO.getFecDoc());
		deposito.setFechaMod(depositoVO.getFecDoc());
		deposito.setFecValor(depositoVO.getFecValor());
		deposito.setFuncionario(depositoVO.getFuncionario());
		deposito.setImpTotMn(depositoVO.getImpTotMn());
		deposito.setImpTotMo(depositoVO.getImpTotMo());
		deposito.setMoneda(depositoVO.getMoneda());
		deposito.setNroDocum(depositoVO.getNroDocum());
		deposito.setNroTrans(depositoVO.getNroTrans());
		deposito.setNumComprobante(depositoVO.getNumComprobante());
		deposito.setObservaciones(depositoVO.getObservaciones());
		deposito.setOperacion(depositoVO.getOperacion());
		deposito.setSerieDocum(depositoVO.getSerieDocum());
		deposito.setUsuarioMod(depositoVO.getUsuarioMod());
		
		
		DepositoDetalleVO auxDet;
		for (DepositoDetalle depositoDetalle: lstDetalle) {
			auxDet = new DepositoDetalleVO();
			
			auxDet = depositoDetalle.retornarDepositoDetalleVO(depositoDetalle);
			
			depositoVO.getLstDetalle().add(auxDet);
			
		}
		
		DepositoDetalle auxDetalle;
		for (DepositoDetalleVO depositoDetalleVO: depositoVO.getLstDetalle()) {
			
			auxDetalle = new DepositoDetalle();
			
			Cheque cheque = new Cheque();
			cheque.setBanco(depositoDetalleVO.getChequeVO().getBanco());
			cheque.setCodCuentaInd(depositoDetalleVO.getChequeVO().getCodCtaInd());
			cheque.setCodDocum(depositoDetalleVO.getChequeVO().getCodDocum());
			cheque.setCodEmp(depositoDetalleVO.getChequeVO().getCodEmp());
			
			CuentaInfo cuentaInfo = new CuentaInfo();
			cuentaInfo.setCodCuenta(depositoDetalleVO.getChequeVO().getCodCuenta());
			cuentaInfo.setNomCuenta(depositoDetalleVO.getChequeVO().getNomCuenta());
			
			cheque.setCuenta(cuentaInfo);
			
			cheque.setCuentaBanco(depositoDetalleVO.getChequeVO().getCuentaBanco());
			cheque.setFecDoc(depositoDetalleVO.getChequeVO().getFecDoc());
			cheque.setFecValor(depositoDetalleVO.getChequeVO().getFecValor());
			cheque.setFechaMod(depositoDetalleVO.getChequeVO().getFechaMod());
			cheque.setImpTotMn(depositoDetalleVO.getChequeVO().getImpTotMn());
			cheque.setImpTotMo(depositoDetalleVO.getChequeVO().getImpTotMo());
			
			MonedaInfo moneda = new MonedaInfo();
			moneda.setCodMoneda(depositoDetalleVO.getChequeVO().getCodMoneda());
			moneda.setDescripcion(depositoDetalleVO.getChequeVO().getNomMoneda());
			moneda.setSimbolo(depositoDetalleVO.getChequeVO().getSimboloMoneda());
			moneda.setNacional(depositoDetalleVO.getChequeVO().isNacional());
			cheque.setMoneda(moneda);
			
			cheque.setNroDocum(depositoDetalleVO.getChequeVO().getNroDocum());
			cheque.setNroTrans(depositoDetalleVO.getNroTrans());
			cheque.setOperacion(depositoDetalleVO.getChequeVO().getOperacion());
			cheque.setReferencia(depositoDetalleVO.getChequeVO().getReferencia());
			cheque.setSerieDocum(depositoDetalleVO.getChequeVO().getSerieDocum());
			cheque.setTcMov(depositoDetalleVO.getChequeVO().getTcMov());
			
			TitularInfo titular = new TitularInfo();
			titular.setCodigo(depositoDetalleVO.getChequeVO().getCodTitular());
			titular.setNombre(depositoDetalleVO.getChequeVO().getNomTitular());
			titular.setTipo(depositoDetalleVO.getChequeVO().getTipo());
			cheque.setTitInfo(titular);
			
			cheque.setUsuarioMod(depositoDetalleVO.getChequeVO().getUsuarioMod());
			
			auxDetalle.setCheque(cheque);
			
			deposito.getLstDetalle().add(auxDetalle);
			
		}
		
		
		return deposito;
		//deposito.setLstDetalle(depositoVO.getLstDetalle());
		
	}
	
	public String getCodDocum() {
		return codDocum;
	}

	public void setCodDocum(String codDocum) {
		this.codDocum = codDocum;
	}

	public String getSerieDocum() {
		return serieDocum;
	}

	public void setSerieDocum(String serieDocum) {
		this.serieDocum = serieDocum;
	}

	public int getNroDocum() {
		return nroDocum;
	}

	public void setNroDocum(int nroDocum) {
		this.nroDocum = nroDocum;
	}

	public Timestamp getFecDoc() {
		return fecDoc;
	}

	public void setFecDoc(Timestamp fecDoc) {
		this.fecDoc = fecDoc;
	}

	public Timestamp getFecValor() {
		return fecValor;
	}

	public void setFecValor(Timestamp fecValor) {
		this.fecValor = fecValor;
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

	public MonedaInfo getMoneda() {
		return moneda;
	}

	public void setMoneda(MonedaInfo moneda) {
		this.moneda = moneda;
	}

	public FuncionarioInfo getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(FuncionarioInfo funcionario) {
		this.funcionario = funcionario;
	}

	public int getNumComprobante() {
		return numComprobante;
	}

	public void setNumComprobante(int numComprobante) {
		this.numComprobante = numComprobante;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public double getImpTotMo() {
		return impTotMo;
	}

	public void setImpTotMo(double impTotMo) {
		this.impTotMo = impTotMo;
	}

	public double getImpTotMn() {
		return impTotMn;
	}

	public void setImpTotMn(double impTotMn) {
		this.impTotMn = impTotMn;
	}

	public long getNroTrans() {
		return nroTrans;
	}

	public void setNroTrans(long nroTrans) {
		this.nroTrans = nroTrans;
	}

	public ArrayList<DepositoDetalle> getLstDetalle() {
		return lstDetalle;
	}

	public void setLstDetalle(ArrayList<DepositoDetalle> lstDetalle) {
		this.lstDetalle = lstDetalle;
	}

	
}
