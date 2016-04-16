package com.logica;

import java.util.ArrayList;

import com.excepciones.*;
import com.valueObject.*;
import com.persistencia.*;

public class Fachada {

	private static final Object lock = new Object();
	private static volatile Fachada INSTANCE = null;
	
	private DAOMonedas monedas;
	
    private Fachada()
    {
        this.monedas = new DAOMonedas();
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
    
    
    public ArrayList<MonedaVO> getMonedas() throws ObteniendoMonedasException{
    	
    	return this.monedas.getMonedas();
    }

    
}
