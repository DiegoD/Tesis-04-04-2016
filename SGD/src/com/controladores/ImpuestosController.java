package com.controladores;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.simple.JSONObject;

import com.logica.Fachada;
import com.valueObject.ImpuestoVO;

public class ImpuestosController {
	
	public ImpuestosController(){
		
	}
	
	public void insertImpuesto(ImpuestoVO impuestoVO, JSONObject json) throws InstantiationException, IllegalAccessException, ClassNotFoundException, FileNotFoundException, IOException, JSONException {
		
		System.out.println("estoy en controlador llamando a fachada");
		System.out.println(json.get("codigo"));
		System.out.println(json.get("descImpuesto"));
		
		Fachada.getInstance().insertImpuesto(impuestoVO);
		
	}
	
	public ArrayList<JSONObject> getImpuestosTodos() throws ClassNotFoundException, InstantiationException, IllegalAccessException, FileNotFoundException, IOException {
		System.out.println("controlador Impuestos");
		return Fachada.getInstance().getImpuestosTodos();
	}
}
