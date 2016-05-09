package com.vista;

import java.util.ArrayList;

import org.json.simple.JSONObject;

import com.controladores.ImpuestosController;
import com.vaadin.client.widgets.Grid.SelectionMode;
import com.vaadin.ui.Grid.SingleSelectionModel;
import com.vaadin.ui.Notification;

public class GrillaExtend extends Grilla {
	
	
	private ImpuestosController controladorImpuesto;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GrillaExtend() {
		super();
		this.grillaImpuestos.setSelectionMode(com.vaadin.ui.Grid.SelectionMode.SINGLE);
		SingleSelectionModel selection =
			    (SingleSelectionModel) grillaImpuestos.getSelectionModel();
			//selection.select( // Select 3rd item
			//		grillaImpuestos.getContainerDataSource().getIdByIndex(2));
		
		this.controladorImpuesto = new ImpuestosController();
		fillGrilla();
		
		grillaImpuestos.addSelectionListener(selectionEvent -> { // Java 8
		    // Get selection from the selection model
		    Object selected = ((SingleSelectionModel)
		        grillaImpuestos.getSelectionModel()).getSelectedRow();

		    if (selected != null)
		    
		    	Notification.show("Selected " + 
		    			
		            grillaImpuestos.getContainerDataSource().getItem(selected)
		               .getItemProperty("Codigo").getValue());
		    else
		        Notification.show("Nothing selected");
		});
	}
	
	private void fillGrilla(){
		try {
			ArrayList<JSONObject> lstImpuestos = controladorImpuesto.getImpuestosTodos();
			this.grillaImpuestos.addColumn("Codigo", Integer.class);
			this.grillaImpuestos.addColumn("Descripción", String.class);
			this.grillaImpuestos.addColumn("Porcentaje", Integer.class);
			
			for (JSONObject json : lstImpuestos) {
				
				grillaImpuestos.addRow(json.get("cod_impuesto"), json.get("desc_impuesto"), 
						json.get("porcentaje_impuesto"));
			}
			
		} catch (Exception e) {
			// TODO: handle exc
			e.printStackTrace();
		}
		
	}
	

}
