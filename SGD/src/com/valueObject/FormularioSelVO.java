package com.valueObject;

import com.logica.Formulario;


/*Este VO sirve para el formulario de seleccion
 * De formularios en la vista de grupos
 * para seleccionar los Formularios a dicho grupo*/
public class FormularioSelVO extends FormularioVO{
	
	boolean sel;
	
	public FormularioSelVO(Formulario form)
	{
		this.setCodFormulario(form.getCodFormulario());
		this.setNomFormulario(form.getNomFormulario()); 
		
		this.sel = false; /*Dejamos la variable de seleccion en false
		 					como no seleccionado*/
	}
	
	public boolean isSel() {
		return sel;
	}
	public void setSel(boolean sel) {
		this.sel = sel;
	}

}
