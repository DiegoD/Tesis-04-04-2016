package com.vista;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.UI;

public class FacturaEjExtended extends FacturaEj implements IBusqueda {

	//private FacturaEjExtended form;
	
		public FacturaEjExtended(){
			
			this.inicializarBoton();
			
		}
		
		private void inicializarBoton(){
			
			//this.form = new FacturaEjExtended();
			  
			  btnBusqueda.addClickListener(new Button.ClickListener() {
				  @Override
				    public void buttonClick(ClickEvent event) {
				    	
					  //PROBAR ESTAS DOS LINEAS
					  MySearch sub = new MySearch(FacturaEjExtended.this);
					  UI.getCurrent().addWindow(sub);
					  
					 
				    }
				});
		}
		
	

		@Override
		public void setField(String content) {
			this.address_field.setValue(content);
		}
}
