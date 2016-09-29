package com.valueObject.IngresoCobro;

import java.util.ArrayList;

import com.logica.Docum.BancoInfo;
import com.logica.Docum.CuentaInfo;
import com.valueObject.Docum.DatosDocumVO;

public class IngresoCobroVO extends DatosDocumVO{

	
	private String mPago;
	private String codDocRef;
	private String serieDocRef;
	private int nroDocRef;
	
	private String codBanco;
	private String nomBanco;
	
	private String codCta;
	private String nomCta;
	
	private String codCtaBco;
	private String nomCtaBco;
	
	
	private ArrayList<IngresoCobroDetalleVO> detalle;
	
	public IngresoCobroVO(){
		this.setCodDocum("ingcobro");
		this.setSerieDocum("0");
		
		this.detalle = new ArrayList<IngresoCobroDetalleVO>();
	}
	
	public void copiar(IngresoCobroVO t){
		
		super.copiar(t);
		
		this.mPago = t.mPago;
		this.codDocRef = t.codDocRef;
		this.serieDocRef = t.serieDocRef;
		this.nroDocRef = t.nroDocRef;
		
		this.codBanco = t.codBanco;
		this.nomBanco = t.nomBanco;
		this.codCta = t.codCta;
		this.nomCta = t.nomCta;
		this.nomCtaBco = t.nomCtaBco;
		this.codCtaBco = t.codCtaBco;
		
		IngresoCobroDetalleVO aux;
		for (IngresoCobroDetalleVO detVO : detalle) {
			
			aux = new IngresoCobroDetalleVO();
			aux.copiar(detVO);
			
			this.detalle.add(aux);
		}
		
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

	public String getCodBanco() {
		return codBanco;
	}

	public void setCodBanco(String codBanco) {
		this.codBanco = codBanco;
	}

	public String getNomBanco() {
		return nomBanco;
	}

	public void setNomBanco(String nomBanco) {
		this.nomBanco = nomBanco;
	}

	public String getCodCta() {
		return codCta;
	}

	public void setCodCta(String codCta) {
		this.codCta = codCta;
	}

	public String getNomCta() {
		return nomCta;
	}

	public void setNomCta(String nomCta) {
		this.nomCta = nomCta;
	}

	public ArrayList<IngresoCobroDetalleVO> getDetalle() {
		return detalle;
	}

	public void setDetalle(ArrayList<IngresoCobroDetalleVO> detalle) {
		this.detalle = detalle;
	}

	public String getCodCtaBco() {
		return codCtaBco;
	}

	public void setCodCtaBco(String codCtaBco) {
		this.codCtaBco = codCtaBco;
	}

	public String getNomCtaBco() {
		return nomCtaBco;
	}

	public void setNomCtaBco(String nomCtaBco) {
		this.nomCtaBco = nomCtaBco;
	}

	
	
}
