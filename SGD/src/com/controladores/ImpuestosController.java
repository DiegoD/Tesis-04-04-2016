package com.controladores;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.logica.Fachada;
import com.valueObject.ImpuestoVO;
import org.json.*;
import org.json.simple.JSONObject;

public class ImpuestosController {
	
	public ImpuestosController(){
		
	}
	
public void insertImpuesto(ImpuestoVO impuestoVO, JSONObject json) throws InstantiationException, IllegalAccessException, ClassNotFoundException, FileNotFoundException, IOException, JSONException {
		
		System.out.println("estoy en controlador llamando a fachada");
		System.out.println(json.get("codigo"));
		System.out.println(json.get("descImpuesto"));
		
		Fachada.getInstance().insertImpuesto(impuestoVO);
		
	}
}
