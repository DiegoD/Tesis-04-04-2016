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
	private double tcMov;
	
	private ArrayList<DepositoDetalle> lstDetalle;
	
	public Deposito(){
		lstDetalle = new ArrayList<DepositoDetalle>();
	}
	
	public DepositoVO getDepositoVO(Deposito deposito){
		
		DepositoVO depositoVO = new DepositoVO();
		
		depositoVO.setCodBanco(deposito.getBanco().getCodBanco());
		depositoVO.setNomBanco(deposito.getBanco().getNomBanco());
		depositoVO.setCodDocum(deposito.getCodDocum());

		depositoVO.setCodCuenta(deposito.getCuentaBanco().getCodCuenta());
		depositoVO.setNomCuenta(deposito.getCuentaBanco().getNomCuenta());
		depositoVO.setCodMoneda(deposito.getCuentaBanco().getCodMoneda());
		
		depositoVO.setFecDoc(deposito.getFecDoc());
		depositoVO.setFecValor(deposito.getFecValor());
		depositoVO.setFuncionario(deposito.getFuncionario());
		depositoVO.setImpTotMn(deposito.getImpTotMn());
		depositoVO.setImpTotMo(deposito.getImpTotMo());
		//depositoVO.setMoneda(deposito.getMoneda());
		depositoVO.setNomMoneda(deposito.getMoneda().getDescripcion());
		depositoVO.setNroDocum(deposito.getNroDocum());
		depositoVO.setNroTrans(deposito.getNroTrans());
		depositoVO.setNumComprobante(deposito.getNumComprobante());
		depositoVO.setObservaciones(deposito.getObservaciones());
		depositoVO.setSerieDocum(deposito.getSerieDocum());
		depositoVO.setTcMov(deposito.getTcMov());
		
		DepositoDetalleVO auxDet;
		lstDetalle = deposito.getLstDetalle();
		
		for (DepositoDetalle depositoDetalle: lstDetalle) {
			auxDet = new DepositoDetalleVO();
			
			auxDet = depositoDetalle.retornarDepositoDetalleVO(depositoDetalle);
			
			depositoVO.getLstDetalle().add(auxDet);
			
		}
		
		
		return depositoVO;
		
		
	}
	
	public Deposito convierteDeposito(DepositoVO depositoVO){
		
		Deposito deposito = new Deposito();
		
		BancoInfo banco = new BancoInfo();
		banco.setCodBanco(depositoVO.getCodBanco());
		banco.setNomBanco(depositoVO.getNomBanco());
		deposito.setBanco(banco);
		
		CuentaBcoInfo cuenta = new CuentaBcoInfo();
		cuenta.setCodCuenta(depositoVO.getCodCuenta());
		cuenta.setNomCuenta(depositoVO.getNomCuenta());
		cuenta.setCodMoneda(depositoVO.getCodMoneda());
		cuenta.setNacional(depositoVO.isNacional());
		deposito.setCuentaBanco(cuenta);
		
		deposito.setCodDocum(depositoVO.getCodDocum());
		
		deposito.setFecDoc(depositoVO.getFecDoc());
		deposito.setFechaMod(depositoVO.getFecDoc());
		deposito.setFecValor(depositoVO.getFecValor());
		deposito.setFuncionario(depositoVO.getFuncionario());
		deposito.setImpTotMn(depositoVO.getImpTotMn());
		deposito.setImpTotMo(depositoVO.getImpTotMo());
		//deposito.setMoneda(depositoVO.getMoneda());
		MonedaInfo moneda = new MonedaInfo();
		moneda.setCodMoneda(depositoVO.getCodMoneda());
		moneda.setDescripcion(depositoVO.getNomMoneda());
		deposito.setMoneda(moneda);
		deposito.setNroDocum(depositoVO.getNroDocum());
		deposito.setNroTrans(depositoVO.getNroTrans());
		deposito.setNumComprobante(depositoVO.getNumComprobante());
		deposito.setObservaciones(depositoVO.getObservaciones());
		deposito.setOperacion(depositoVO.getOperacion());
		deposito.setSerieDocum(depositoVO.getSerieDocum());
		deposito.setUsuarioMod(depositoVO.getUsuarioMod());
		deposito.setTcMov(depositoVO.getTcMov());
		
		
		DepositoDetalleVO auxDet;
		for (DepositoDetalle depositoDetalle: lstDetalle) {
			auxDet = new DepositoDetalleVO();
			
			auxDet = depositoDetalle.retornarDepositoDetalleVO(depositoDetalle);
			
			depositoVO.getLstDetalle().add(auxDet);
			
		}
		
		DepositoDetalle auxDetalle;
		for (DepositoDetalleVO depositoDetalleVO: depositoVO.getLstDetalle()) {
			
			auxDetalle = new DepositoDetalle();
			auxDetalle.setNroTrans(deposito.getNroTrans());
			
			Cheque cheque = new Cheque();
			BancoInfo bancoInfo = new BancoInfo();
			bancoInfo.setCodBanco(depositoDetalleVO.getCodBanco());
			
			bancoInfo.setNomBanco(depositoDetalleVO.getNomBanco());
			cheque.setBanco(bancoInfo);
			
			cheque.setCodCuentaInd(depositoDetalleVO.getCodCtaInd());
			cheque.setCodDocum(depositoDetalleVO.getCodDocum());
			cheque.setCodEmp(depositoDetalleVO.getCodEmp());
			
			CuentaBcoInfo cuentaInfo = new CuentaBcoInfo();
			cuentaInfo.setCodCuenta(depositoDetalleVO.getCodCuenta());
			cuentaInfo.setNomCuenta(depositoDetalleVO.getNomCuenta());
			
			cheque.setCuentaBanco(cuentaInfo);
			
			cheque.setCuentaBanco(cuentaInfo);
			cheque.setFecDoc(depositoDetalleVO.getFecDoc());
			cheque.setFecValor(depositoDetalleVO.getFecValor());
			cheque.setImpTotMn(depositoDetalleVO.getImpTotMn());
			cheque.setImpTotMo(depositoDetalleVO.getImpTotMo());
			
			MonedaInfo moneda2 = new MonedaInfo();
			moneda2.setCodMoneda(depositoDetalleVO.getCodMoneda());
			moneda2.setDescripcion(depositoDetalleVO.getNomMoneda());
			moneda2.setSimbolo(depositoDetalleVO.getSimboloMoneda());
			moneda2.setNacional(depositoDetalleVO.isNacional());
			cheque.setMoneda(moneda2);
			
			cheque.setNroDocum(depositoDetalleVO.getNroDocum());
			cheque.setNroTrans(depositoDetalleVO.getNroTrans());
			cheque.setReferencia(depositoDetalleVO.getReferencia());
			cheque.setSerieDocum(depositoDetalleVO.getSerieDocum());
			cheque.setTcMov(depositoDetalleVO.getTcMov());
			
			TitularInfo titular = new TitularInfo();
			titular.setCodigo(depositoDetalleVO.getCodTitular());
			titular.setNombre(depositoDetalleVO.getNomTitular());
			titular.setTipo(depositoDetalleVO.getTipo());
			cheque.setTitInfo(titular);
			
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

	public double getTcMov() {
		return tcMov;
	}

	public void setTcMov(double tcMov) {
		this.tcMov = tcMov;
	}

	
	
}
