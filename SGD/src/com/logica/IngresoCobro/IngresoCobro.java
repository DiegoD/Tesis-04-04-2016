package com.logica.IngresoCobro;

import java.sql.Timestamp;
import java.util.ArrayList;

import com.logica.Docum.BancoInfo;
import com.logica.Docum.CuentaBcoInfo;
import com.logica.Docum.CuentaInfo;
import com.logica.Docum.DatosDocum;
import com.valueObject.IngresoCobro.IngresoCobroDetalleVO;
import com.valueObject.IngresoCobro.IngresoCobroVO;

public class IngresoCobro extends DatosDocum{

	private String mPago;
	private String codDocRef;
	private String serieDocRef;
	private int nroDocRef;
	private BancoInfo bancoInfo;
	private CuentaBcoInfo cuentaBcoInfo;
	private boolean anulado;
	
	ArrayList<IngresoCobroLinea> detalle;
	
	public IngresoCobro(){
	
		super();
		this.bancoInfo = new BancoInfo();
		//this.cuentaInfo = new CuentaInfo();
		this.cuentaBcoInfo = new CuentaBcoInfo();
		this.detalle = new ArrayList<IngresoCobroLinea>();
	}
	
	
	public IngresoCobro(IngresoCobroVO t){
		
		super(t);
		
		this.detalle = new ArrayList<IngresoCobroLinea>();
		
		this.mPago = t.getmPago();
		this.codDocRef = t.getCodDocRef();
		this.serieDocRef = t.getSerieDocRef();
		this.nroDocRef = Integer.parseInt(t.getNroDocRef());
		this.bancoInfo = new BancoInfo(t.getCodBanco(), t.getNomBanco());
		this.setCuenta(new CuentaInfo(t.getCodCuenta(), t.getNomCuenta(), t.getCodMonedaCtaBco(), t.isNacionalMonedaCtaBco()));
		this.anulado = t.isAnulado();
		
		this.cuentaBcoInfo = new CuentaBcoInfo(t.getCodCtaBco(), t.getNomCtaBco(), t.getCodMonedaCtaBco(), t.isNacionalMonedaCtaBco());
		
		IngresoCobroLinea aux;
		for (IngresoCobroDetalleVO detVO : t.getDetalle()) {
			
			aux = new IngresoCobroLinea(detVO);
			
			this.detalle.add(aux);
		}
	}
		
		
	public IngresoCobroVO retornarIngresoCobroVO(){
			
		
		IngresoCobroVO aux = new IngresoCobroVO();
		
		aux.copiar(super.retornarDatosDocumVO());
				
		aux.setmPago(mPago);
		aux.setCodDocRef(codDocRef);
		aux.setSerieDocRef(serieDocRef);
		aux.setNroDocRef(String.valueOf(nroDocRef));
		aux.setCodBanco(bancoInfo.getCodBanco());
		aux.setNomBanco(bancoInfo.getNomBanco());
		aux.setCodCtaInd("0");
		aux.setFecValor(aux.getFecValor());
		aux.setFecDoc(aux.getFecDoc());
		aux.setAnulado(anulado);
		
		aux.setCodCtaInd(this.getCodCuentaInd());
		
		aux.setCodCuenta(this.getCuenta().getCodCuenta());
		aux.setNomCuenta(this.getCuenta().getNomCuenta());
		
		
		aux.setNomCtaBco(this.cuentaBcoInfo.getNomCuenta());
		aux.setCodCtaBco(this.cuentaBcoInfo.getCodCuenta());
		
		aux.setCodEmp(this.getCodEmp());
		
		IngresoCobroDetalleVO auxDet;
		for (IngresoCobroLinea ingresoCobroLinea : detalle) {
			auxDet = new IngresoCobroDetalleVO();
			
			auxDet = ingresoCobroLinea.retornarDatosDocumVO();
			
			aux.getDetalle().add(auxDet);
		}
	
		return aux;
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


	public ArrayList<IngresoCobroLinea> getDetalle() {
		return detalle;
	}


	public void setDetalle(ArrayList<IngresoCobroLinea> detalle) {
		this.detalle = detalle;
	}


	public CuentaBcoInfo getCuentaBcoInfo() {
		return cuentaBcoInfo;
	}


	public void setCuentaBcoInfo(CuentaBcoInfo cuentaBcoInfo) {
		this.cuentaBcoInfo = cuentaBcoInfo;
	}


	public boolean isAnulado() {
		return anulado;
	}


	public void setAnulado(boolean anulado) {
		this.anulado = anulado;
	}
	
	
}
