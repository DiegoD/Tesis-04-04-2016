package com.logica.Docum;

import java.util.ArrayList;

import com.logica.ProcesoInfo;
import com.valueObject.Docum.ReciboDetalleVO;
import com.valueObject.Docum.ReciboVO;

public class Recibo extends DatosDocum{

	private double impuTotMn ;
	private double impuTotMo;
	private double impSubMo;
	private double impSubMn;
	
	private String mPago;
	private String codDocRef;
	private String serieDocRef;
	private int nroDocRef;
	private BancoInfo bancoInfo;
	private CuentaBcoInfo cuentaBcoInfo;
	
	private ProcesoInfo procesoInfo;
	
	ArrayList<ReciboDetalle> detalle;
	
	public Recibo(){
		super();
		
		this.procesoInfo = new ProcesoInfo(0,"No Asignado");
	}
	
	public Recibo(ReciboVO t){
		
		super(t);
		
		this.detalle = new ArrayList<ReciboDetalle>();
		
		this.procesoInfo = new ProcesoInfo(t.getCodProceso(), t.getDescProceso());
		
		
		this.impuTotMn = t.getImpuTotMn();
		this.impuTotMo = t.getImpuTotMo();
		this.impSubMo = t.getImpSubMo();
		this.impSubMn = t.getImpSubMn();
		
		this.mPago = t.getmPago();
		this.codDocRef = t.getCodDocRef();
		this.serieDocRef = t.getSerieDocRef();
		this.nroDocRef = Integer.parseInt(t.getNroDocRef());
		this.bancoInfo = new BancoInfo(t.getCodBanco(), t.getNomBanco());
		this.setCuenta(new CuentaInfo(t.getCodCuenta(), t.getNomCuenta(), t.getCodMonedaCtaBco(), t.isNacionalMonedaCtaBco()));
		
		this.cuentaBcoInfo = new CuentaBcoInfo(t.getCodCtaBco(), t.getNomCtaBco(), t.getCodMonedaCtaBco(), t.isNacionalMonedaCtaBco());
		
		
		
		ReciboDetalle aux;
		for (ReciboDetalleVO detVO : t.getDetalle()) {
			
			aux = new ReciboDetalle(detVO);
			
			this.detalle.add(aux);
		}
	}
		
	
	public ReciboVO retornarVO(){
			
		
		
		ReciboVO aux = new ReciboVO();
		
		aux.copiar(super.retornarDatosDocumVO());
				
		aux.setCodProceso(this.procesoInfo.getCodProceso());
		aux.setDescProceso(this.procesoInfo.getDescProceso());
		aux.setCodCtaInd("0");
		aux.setFecValor(aux.getFecValor());
		aux.setFecDoc(aux.getFecDoc());
		
		aux.setImpuTotMn(this.impuTotMn);
		aux.setImpuTotMo(this.impuTotMo);
		aux.setImpSubMo(this.impSubMo);
		aux.setImpSubMn(this.impSubMn);
		
		aux.setCodCtaInd(this.getCodCuentaInd());
		
		aux.setCodCuenta(this.getCuenta().getCodCuenta());
		aux.setNomCuenta(this.getCuenta().getNomCuenta());
		
		aux.setCodEmp(this.getCodEmp());
		
		aux.setmPago(mPago);
		aux.setCodDocRef(codDocRef);
		aux.setSerieDocRef(serieDocRef);
		aux.setNroDocRef(String.valueOf(nroDocRef));
		aux.setCodBanco(bancoInfo.getCodBanco());
		aux.setNomBanco(bancoInfo.getNomBanco());


		aux.setNomCtaBco(this.cuentaBcoInfo.getNomCuenta());
		aux.setCodCtaBco(this.cuentaBcoInfo.getCodCuenta());
		
		ReciboDetalleVO auxDet;
		for (ReciboDetalle lin : this.detalle) {
			auxDet = new ReciboDetalleVO();
			
			auxDet =  lin.retornarVO();
			
			aux.getDetalle().add(auxDet);
		}
	
		return aux;
	}
	

	public ArrayList<ReciboDetalle> getDetalle() {
		return this.detalle;
	}

	public void setDetalle(ArrayList<ReciboDetalle> detalle) {
		this.detalle = detalle;
	}

	public ProcesoInfo getProcesoInfo() {
		return procesoInfo;
	}

	public void setProcesoInfo(ProcesoInfo procesoInfo) {
		this.procesoInfo = procesoInfo;
	}

	public double getImpuTotMn() {
		return impuTotMn;
	}

	public void setImpuTotMn(double impuTotMn) {
		this.impuTotMn = impuTotMn;
	}

	public double getImpuTotMo() {
		return impuTotMo;
	}

	public void setImpuTotMo(double impuTotMo) {
		this.impuTotMo = impuTotMo;
	}

	public double getImpSubMo() {
		return impSubMo;
	}

	public void setImpSubMo(double impSubMo) {
		this.impSubMo = impSubMo;
	}

	public double getImpSubMn() {
		return impSubMn;
	}

	public void setImpSubMn(double impSubMn) {
		this.impSubMn = impSubMn;
	}

	public String getmPago() {
		return mPago;
	}

	public void setmPago(String mPago) {
		this.mPago = mPago;
	}

	public String getCodDocRef() {
		return codDocRef;
	}

	public void setCodDocRef(String codDocRef) {
		this.codDocRef = codDocRef;
	}

	public String getSerieDocRef() {
		return serieDocRef;
	}

	public void setSerieDocRef(String serieDocRef) {
		this.serieDocRef = serieDocRef;
	}

	public int getNroDocRef() {
		return nroDocRef;
	}

	public void setNroDocRef(int nroDocRef) {
		this.nroDocRef = nroDocRef;
	}

	public BancoInfo getBancoInfo() {
		return bancoInfo;
	}

	public void setBancoInfo(BancoInfo bancoInfo) {
		this.bancoInfo = bancoInfo;
	}

	public CuentaBcoInfo getCuentaBcoInfo() {
		return cuentaBcoInfo;
	}

	public void setCuentaBcoInfo(CuentaBcoInfo cuentaBcoInfo) {
		this.cuentaBcoInfo = cuentaBcoInfo;
	}
	
	
	
}
