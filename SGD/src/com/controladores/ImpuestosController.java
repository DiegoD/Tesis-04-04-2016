package com.controladores;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.logica.Fachada;
import com.valueObject.ImpuestoVO;

public class ImpuestosController {
	
	public ImpuestosController(){
		
	}
	
public void insertImpuesto(ImpuestoVO impuestoVO) throws InstantiationException, IllegalAccessException, ClassNotFoundException, FileNotFoundException, IOException {
		
		System.out.println("estoy en controlador llamando a fachada");
		Fachada.getInstance().insertImpuesto(impuestoVO);
		
	}
}
