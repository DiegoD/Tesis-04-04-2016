package com.vista;

public class MenuExtended extends Menu{
	
	
	public MenuExtended(){
		
	
		this.userButton.addClickListener(click -> {
			
			this.content.removeAllComponents();
			this.content.addComponent(new MonedaView());
		});
	}
	

}
