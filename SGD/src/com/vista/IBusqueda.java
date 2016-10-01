package com.vista;

import java.util.ArrayList;

public interface IBusqueda {

	/*Cuando tenemos solamente para seleccionar un elemento de la grilla*/
	public void setInfo(Object datos);
	
	/*Cuando tenemos una multi seleccion en la grilla, 
	 * para pasarle todos los datos seleccionados*/
	public void setInfoLst(ArrayList<Object> lstDatos);
	
	public void cerrarVentana();
	
}
