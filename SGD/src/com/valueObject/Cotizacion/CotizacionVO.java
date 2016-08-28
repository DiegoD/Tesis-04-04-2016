package com.valueObject.Cotizacion;

import java.sql.Timestamp;
import java.util.Date;

import com.valueObject.AuditoriaVO;
import com.valueObject.empresa.EmpresaVO;

public class CotizacionVO extends AuditoriaVO{
	
	//private Timestamp fecha;
	private Date fecha;
	private float cotizacionCompra;
	private float cotizacionVenta;
	private String codMoneda;
	private String descripcionMoneda;
	private String simboloMoneda;
	private boolean aceptaCotizacionMoneda;
	private boolean activoMoneda;
	
	public CotizacionVO(){
		
	}
	
	public CotizacionVO(Date fecha, float cotizacion_compra, float cotizacion_venta, String codMoneda, 
			String descripcionMoneda, String simboloMoneda, boolean aceptaCotizacionMoneda, boolean activoMoneda) {
		super();
		this.fecha = fecha;
		this.cotizacionCompra = cotizacion_compra;
		this.cotizacionVenta = cotizacion_venta;
		this.codMoneda = codMoneda;
		this.descripcionMoneda = descripcionMoneda;
		this.simboloMoneda = simboloMoneda;
		this.aceptaCotizacionMoneda = aceptaCotizacionMoneda;
		this.activoMoneda = activoMoneda;
		
	}

	/**
	 * Copiamos todos los datos de la cotización pasado
	 * por parametro
	 *
	 */
	public void copiar(CotizacionVO cotizacionVO){

		this.setFecha(cotizacionVO.getFecha());
		this.setCotizacionCompra(cotizacionVO.getCotizacionCompra());
		this.setCotizacionVenta(cotizacionVO.getCotizacionVenta());
		this.setFechaMod(cotizacionVO.getFechaMod());
		this.setUsuarioMod(cotizacionVO.getUsuarioMod());
		this.setOperacion(cotizacionVO.getOperacion());
		this.setCodMoneda(cotizacionVO.getCodMoneda());
		this.setDescripcionMoneda(cotizacionVO.getDescripcionMoneda());
		this.setSimboloMoneda(cotizacionVO.getSimboloMoneda());
		this.setAceptaCotizacionMoneda(cotizacionVO.isAceptaCotizacionMoneda());
		this.setActivoMoneda(cotizacionVO.isActivoMoneda());
		
	}
	
	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getCodMoneda() {
		return codMoneda;
	}

	public void setCodMoneda(String codMoneda) {
		this.codMoneda = codMoneda;
	}

	public String getDescripcionMoneda() {
		return descripcionMoneda;
	}

	public void setDescripcionMoneda(String descripcionMoneda) {
		this.descripcionMoneda = descripcionMoneda;
	}

	public String getSimboloMoneda() {
		return simboloMoneda;
	}

	public void setSimboloMoneda(String simboloMoneda) {
		this.simboloMoneda = simboloMoneda;
	}

	public boolean isAceptaCotizacionMoneda() {
		return aceptaCotizacionMoneda;
	}

	public void setAceptaCotizacionMoneda(boolean aceptaCotizacionMoneda) {
		this.aceptaCotizacionMoneda = aceptaCotizacionMoneda;
	}

	public boolean isActivoMoneda() {
		return activoMoneda;
	}

	public void setActivoMoneda(boolean activoMoneda) {
		this.activoMoneda = activoMoneda;
	}

	public float getCotizacionCompra() {
		return cotizacionCompra;
	}

	public void setCotizacionCompra(float cotizacion_compra) {
		this.cotizacionCompra = cotizacion_compra;
	}

	public float getCotizacionVenta() {
		return cotizacionVenta;
	}

	public void setCotizacionVenta(float cotizacion_venta) {
		this.cotizacionVenta = cotizacion_venta;
	}
	
}
