package com.vista;

public class MenuExtended extends Menu{
	
	
	public MenuExtended(){
		
	
		this.userButton.addClickListener(click -> {
			
			this.content.removeAllComponents();
			try {
				
				this.content.addComponent(new CotizacionesPanelExtended());
				
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
			this.content.addComponent(new MonedaAltaExtended());
		});
		
		this.archiveButton.addClickListener(click -> {
			
			this.content.removeAllComponents();
			this.content.addComponent(new GrillaExtend());
		});
		
		
	}
	

}
