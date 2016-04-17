package com.controladores;

import java.sql.Date;
import java.util.ArrayList;

import com.excepciones.*;
import com.logica.Fachada;
import com.valueObject.*;
import com.persistencia.*;

public class CotizacionesController {

	
	public CotizacionesController(){}
	
	
	public ArrayList<MonedaVO> getMonedas() throws ObteniendoMonedasException{
		
		return Fachada.getInstance().getMonedas();
	}
	
	
	public ArrayList<CotizacionVO> getCotizaciones() throws ObteniendoCotizacionException{
		
		return Fachada.getInstance().getCotizaciones();
	}
	
	
	public CotizacionVO getCotizacion(Date fecha, int codMoneda) throws ObteniendoCotizacionException, MemberCotizacionException, NoExisteCotizacionException{
		
		return Fachada.getInstance().getCotizacion(fecha, codMoneda);
	}
	
	public void insertCotizacion(CotizacionVO cotizacionVO) throws IngresandoCotizacionException, MemberCotizacionException, ExisteCotizacionException{
		
		Fachada.getInstance().insertCotizacion(cotizacionVO);
		
	}
}
