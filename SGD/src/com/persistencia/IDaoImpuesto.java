package com.persistencia;

import java.util.ArrayList;

import org.json.simple.JSONObject;

import com.logica.Impuesto;

public interface IDaoImpuesto {
	
	public void insertImpuesto(Impuesto impuesto) throws ClassNotFoundException;
	
	public ArrayList<JSONObject> getImpuestosTodos() throws ClassNotFoundException;

}
