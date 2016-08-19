package com.valueObject.Cotizacion;

import java.sql.Timestamp;
import com.valueObject.AuditoriaVO;

public class CotizacionVO extends AuditoriaVO{
	
	private Timestamp fecha;
	private float cotizacion;
	private String codMoneda;
	private String descripcionMoneda;
	private String simboloMoneda;
	private boolean aceptaCotizacionMoneda;
	private boolean activoMoneda;
	
	public CotizacionVO(Timestamp fecha, float cotizacion, String codMoneda, String descripcionMoneda,
			String simboloMoneda, boolean aceptaCotizacionMoneda, boolean activoMoneda) {
		super();
		this.fecha = fecha;
		this.cotizacion = cotizacion;
		this.codMoneda = codMoneda;
		this.descripcionMoneda = descripcionMoneda;
		this.simboloMoneda = simboloMoneda;
		this.aceptaCotizacionMoneda = aceptaCotizacionMoneda;
		this.activoMoneda = activoMoneda;
	}

	public Timestamp getFecha() {
		return fecha;
	}

	public void setFecha(Timestamp fecha) {
		this.fecha = fecha;
	}

	public float getCotizacion() {
		return cotizacion;
	}

	public void setCotizacion(float cotizacion) {
		this.cotizacion = cotizacion;
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
	
}
