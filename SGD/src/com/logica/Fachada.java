package com.logica;

import java.sql.Date;
import java.util.ArrayList;

import com.excepciones.*;
import com.excepciones.cotizaciones.ExisteCotizacionException;
import com.excepciones.cotizaciones.IngresandoCotizacionException;
import com.excepciones.cotizaciones.MemberCotizacionException;
import com.excepciones.cotizaciones.NoExisteCotizacionException;
import com.excepciones.cotizaciones.ObteniendoCotizacionException;
import com.valueObject.*;
import com.persistencia.*;

public class Fachada {

	private static final Object lock = new Object();
	private static volatile Fachada INSTANCE = null;
	
	private DAOMonedas monedas;
	private DAOCotizaciones cotizaciones;
	
    private Fachada()
    {
        this.monedas = new DAOMonedas();
        this.cotizaciones = new DAOCotizaciones();
    }
    
    public static Fachada getInstance(){
         
        if(INSTANCE == null)
        {
            synchronized (lock)
            {   
                INSTANCE = new Fachada();
            }
        }
        
        return INSTANCE;
    }
    
    
    /////////////////////////////////<MONEDAS>/////////////////////////////////////////////////
    public ArrayList<MonedaVO> getMonedas() throws ObteniendoMonedasException{
    	
    	return this.monedas.getMonedas();
    }

    /////////////////////////////////<MONEDAS/>/////////////////////////////////////////////////
    
    
    /////////////////////////////////<COTIZACIONES>////////////////////////////////////////////
    
    public ArrayList<CotizacionVO> getCotizaciones() throws ObteniendoCotizacionException{
    	
    	return this.cotizaciones.getCotizaciones();
    }
    
    public CotizacionVO getCotizacion(Date fecha, int codMoneda) throws ObteniendoCotizacionException, MemberCotizacionException, NoExisteCotizacionException{
    	
    	if(this.cotizaciones.memberCotizacion(fecha, codMoneda))
    	    	return this.cotizaciones.getCotizacion(fecha, codMoneda);
    	else 
    		throw new NoExisteCotizacionException();
    }

    public void insertCotizacion(CotizacionVO cotizacionVO) throws IngresandoCotizacionException, MemberCotizacionException, ExisteCotizacionException{
    	
    	if(!this.cotizaciones.memberCotizacion(cotizacionVO.getFecha(), cotizacionVO.getCodMoneda()))
    		this.cotizaciones.insertCotizacion(cotizacionVO);
    	else
    		throw new ExisteCotizacionException();
    }
    
    /////////////////////////////////<COTIZACIONES/>//////////////////////////////////////////
    
    
}
