package com.controladores;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;

import com.excepciones.*;
import com.excepciones.cotizaciones.ExisteCotizacionException;
import com.excepciones.cotizaciones.IngresandoCotizacionException;
import com.excepciones.cotizaciones.MemberCotizacionException;
import com.excepciones.cotizaciones.NoExisteCotizacionException;
import com.excepciones.cotizaciones.ObteniendoCotizacionException;
import com.logica.Fachada;
import com.valueObject.*;
import com.persistencia.*;

public class CotizacionesController {

	
	public CotizacionesController(){}
	
	
	public ArrayList<MonedaVO> getMonedas() throws ObteniendoMonedasException, InstantiationException, IllegalAccessException, ClassNotFoundException, FileNotFoundException, IOException{
		
		return Fachada.getInstance().getMonedas();
	}
	
	
	public ArrayList<CotizacionVO> getCotizaciones() throws ObteniendoCotizacionException, InstantiationException, IllegalAccessException, ClassNotFoundException, FileNotFoundException, IOException{
		
		return Fachada.getInstance().getCotizaciones();
	}
	
	
	public CotizacionVO getCotizacion(Date fecha, int codMoneda) throws ObteniendoCotizacionException, MemberCotizacionException, NoExisteCotizacionException, InstantiationException, IllegalAccessException, ClassNotFoundException, FileNotFoundException, IOException{
		
		return Fachada.getInstance().getCotizacion(fecha, codMoneda);
	}
	
	public void insertCotizacion(CotizacionVO cotizacionVO) throws IngresandoCotizacionException, MemberCotizacionException, ExisteCotizacionException, InstantiationException, IllegalAccessException, ClassNotFoundException, FileNotFoundException, IOException{
		
		Fachada.getInstance().insertCotizacion(cotizacionVO);
		
	}
}
