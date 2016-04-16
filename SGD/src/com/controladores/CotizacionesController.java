package com.controladores;

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
	
}
