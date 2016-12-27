package com.controladores;

import java.util.ArrayList;

import com.logica.Fachada;
import com.valueObject.Saldos.SaCuentasVO;
import com.valueObject.Saldos.SaDocumsVO;

public class VistasControlador {
	
	public VistasControlador(){
		
	}
	
	/**
	 * Obtiene array list de VO de todas los rubros
	 * @throws Exception 
	 */
	public ArrayList<SaDocumsVO> getSaldosDocum(String cod_emp) throws Exception {
		
		
		return Fachada.getInstance().getSaldosDocum(cod_emp);
	
	}
	
	public ArrayList<SaCuentasVO> getSaCuentas(String cod_emp) throws Exception {
		
		
		return Fachada.getInstance().getSaCuentas(cod_emp);
	
	}
	
	
	

}
