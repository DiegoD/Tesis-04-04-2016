package com.persistencia;

import java.sql.Date;
import java.util.ArrayList;

import com.excepciones.cotizaciones.IngresandoCotizacionException;
import com.excepciones.cotizaciones.MemberCotizacionException;
import com.excepciones.cotizaciones.ObteniendoCotizacionException;
import com.valueObject.CotizacionVO;

public interface IDAOCotizaciones {

	
	public CotizacionVO getCotizacion(Date fecha, int codMoneda) throws ObteniendoCotizacionException;
	
	public ArrayList<CotizacionVO> getCotizaciones() throws ObteniendoCotizacionException;
	
	public void insertCotizacion(CotizacionVO cotizacionVO) throws IngresandoCotizacionException;
	
	public boolean memberCotizacion(Date fecha, int codMoneda) throws MemberCotizacionException;
}
