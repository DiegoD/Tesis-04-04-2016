package com.logica.Docum;

import java.util.ArrayList;

import com.logica.ProcesoInfo;
import com.valueObject.Docum.NotaCreditoDetalleVO;
import com.valueObject.Docum.NotaCreditoVO;

public class NotaCredito extends DatosDocum{

	private double impuTotMn;
	private double impuTotMo;
	private double impSubMo;
	private double impSubMn;
	

	ArrayList<NotaCreditoDetalle> detalle;
	
	public NotaCredito(){
		super();
		
	}
	
	public NotaCredito(NotaCreditoVO t){
		
		super(t);
		
		this.detalle = new ArrayList<NotaCreditoDetalle>();
		
		
		this.impuTotMn = t.getImpuTotMn();
		this.impuTotMo = t.getImpuTotMo();
		this.impSubMo = t.getImpSubMo();
		this.impSubMn = t.getImpSubMn();
		

		this.setCuenta(new CuentaInfo(t.getCodCuenta(), t.getNomCuenta(), t.getCodMoneda(), t.isNacionalMonedaCtaBco()));
		
		
		NotaCreditoDetalle aux;
		for (NotaCreditoDetalleVO detVO : t.getDetalle()) {
			
			aux = new NotaCreditoDetalle(detVO);
			
			this.detalle.add(aux);
		}
	}
		
	
	public NotaCreditoVO retornarVO(){
			
		NotaCreditoVO aux = new NotaCreditoVO();
		
		aux.copiar(super.retornarDatosDocumVO());
				
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
		

		
		NotaCreditoDetalleVO auxDet;
		for (NotaCreditoDetalle lin : this.detalle) {
			auxDet = new NotaCreditoDetalleVO();
			
			auxDet =  lin.retornarVO();
			
			aux.getDetalle().add(auxDet);
		}
	
		return aux;
	}
	

	public ArrayList<NotaCreditoDetalle> getDetalle() {
		return this.detalle;
	}

	public void setDetalle(ArrayList<NotaCreditoDetalle> detalle) {
		this.detalle = detalle;
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

}
