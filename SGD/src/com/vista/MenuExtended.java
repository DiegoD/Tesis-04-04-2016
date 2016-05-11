package com.vista;

import com.vaadin.ui.Component;

public class MenuExtended extends Menu{
	
	private Component contentAnterior;
	

	
	public MenuExtended(){
		
		
		
		
		this.userButton.addClickListener(click -> {
			
			this.content.removeAllComponents();
			try {
				
				CotizacionesPanelExtended c = new CotizacionesPanelExtended();
				c.setMenu(this);
				
				this.content.addComponent(c);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
		this.statusButton.addClickListener(click -> {
			
			this.content.removeAllComponents();
			this.content.addComponent(new ImpuestosView());
		});
		
		this.inboxButton.addClickListener(click -> {
			
			this.content.removeAllComponents();
			//this.content.addComponent(new MonedaAltaExtended());
		});
		
		this.archiveButton.addClickListener(click -> {
			
			this.content.removeAllComponents();
			//this.content.addComponent(new GrillaExtend());
		});
		
		
	}
	
	public  void setContent(Component comp)
	{
		contentAnterior = this.content;
		this.content.removeAllComponents();
		this.content.addComponent(comp);
	}
	

}
