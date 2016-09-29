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
	private CuentaInfo cuentaInfo;
	private CuentaBcoInfo cuentaBcoInfo;
	
	ArrayList<IngresoCobroLinea> detalle;
	
	public IngresoCobro(){
	
		this.bancoInfo = new BancoInfo();
		this.cuentaInfo = new CuentaInfo();
		this.cuentaBcoInfo = new CuentaBcoInfo();
		this.detalle = new ArrayList<IngresoCobroLinea>();
	}
	
	
	public IngresoCobro(IngresoCobroVO t){
		
		super(t);
		
		this.detalle = new ArrayList<IngresoCobroLinea>();
		
		this.mPago = t.getmPago();
		this.codDocRef = t.getCodDocRef();
		this.serieDocRef = t.getSerieDocRef();
		this.nroDocRef = t.getNroDocRef();
		this.bancoInfo = new BancoInfo(t.getCodBanco(), t.getNomBanco());
		this.cuentaInfo = new CuentaInfo(t.getCodCta(), t.getNomCta());
		
		this.cuentaBcoInfo = new CuentaBcoInfo(t.getCodCtaBco(), t.getNomCtaBco());
		
		IngresoCobroLinea aux;
		for (IngresoCobroDetalleVO detVO : t.getDetalle()) {
			
			aux = new IngresoCobroLinea(detVO);
			
			this.detalle.add(aux);
		}
	}
		
		
	public IngresoCobroVO retornarIngresoCobroVO(){
			
		
		//IngresoCobroVO aux = (IngresoCobroVO)super.retornarDatosDocumVO();
		
		IngresoCobroVO aux = new IngresoCobroVO();
		aux.copiar((IngresoCobroVO)super.retornarDatosDocumVO());
				
		aux.setmPago(mPago);
		aux.setCodDocRef(codDocRef);
		aux.setSerieDocRef(serieDocRef);
		aux.setNroDocRef(nroDocRef);
		aux.setCodBanco(bancoInfo.getCodBanco());
		aux.setNomBanco(bancoInfo.getNomBanco());
		aux.setCodCta(cuentaInfo.getCodCuenta());
		aux.setNomCta(cuentaInfo.getNomCuenta());
		
		aux.setNomCtaBco(this.bancoInfo.getNomBanco());
		aux.setCodCtaBco(this.bancoInfo.getCodBanco());
		
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


	public CuentaInfo getCuentaInfo() {
		return cuentaInfo;
	}


	public void setCuentaInfo(CuentaInfo cuentaInfo) {
		this.cuentaInfo = cuentaInfo;
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



	
	
}
