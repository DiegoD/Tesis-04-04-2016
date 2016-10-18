package com.vista.Gastos;

import java.util.ArrayList;

import com.valueObject.Gasto.GastoVO;

public interface IGastosMain {

	public void setSub(String seleccion);
	
	public void actulaizarGrilla(GastoVO gastoVO);
	
	public void cerrarVentana();
	
	public void actuilzarGrillaEliminado(long codigo);
	
	public void mostrarMensaje(String msj);
	
	public void setInfoLst(GastoVO gasto);
	
	public String nomForm();
	
}
