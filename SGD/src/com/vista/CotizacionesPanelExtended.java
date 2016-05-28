package com.vista;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Date;
import java.util.Calendar;

import com.vaadin.client.widgets.Grid.HeaderRow;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.SelectionEvent;
import com.vaadin.event.SelectionEvent.SelectionListener;
import com.vaadin.ui.Grid;
import com.vaadin.ui.UI;
import com.valueObject.CotizacionVO;

public class CotizacionesPanelExtended extends CotizacionesPanel {
	
	
	
	private MenuExtended menuAcual;
	
	//private CotizacionesView form;
	private FacturaEjExtended form;
	BeanItemContainer<CotizacionVO> container;
	
	public void setMenu(MenuExtended menu){
		this.menuAcual = menu;
	}
	
	
	public CotizacionesPanelExtended() throws InstantiationException, IllegalAccessException, ClassNotFoundException, FileNotFoundException, IOException{
		
		this.inicializarGrilla();
	}
	
	
	private void inicializarGrilla() throws InstantiationException, IllegalAccessException, ClassNotFoundException, FileNotFoundException, IOException{
		java.util.Date utilDate = new java.util.Date();
		
		java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
		
		CotizacionVO c1 = new CotizacionVO();
		CotizacionVO c2 = new CotizacionVO();
		
		c1.setCodMoneda(1);
		c1.setFecha(sqlDate);
		c1.setFechaMod(sqlDate);
		c1.setImpCompra(10);
		c1.setImpVenta(12);
		c1.setUsuarioMod("gfeuerstein");
		
		c2.setCodMoneda(2);
		c2.setFecha(sqlDate);
		c2.setFechaMod(sqlDate);
		c2.setImpCompra(18);
		c2.setImpVenta(21);
		c2.setUsuarioMod("gfeuerstein");
		
		this.container = 
				new BeanItemContainer<CotizacionVO>(CotizacionVO.class);
		
		container.addBean(c1);
		container.addBean(c2);
		
		gridview.setContainerDataSource(container);
		
		//gridview.getColumn(1).setHeaderCaption("Fulano");
		
		com.vaadin.ui.Grid.HeaderRow row = gridview.prependHeaderRow();
		//row.getCell("Fecha").setHtml("<b>Full name</b>");
		
		gridview.setEditorEnabled(true);
		gridview.setEditorSaveCaption("Save my data, please!");
		
		this.form = new FacturaEjExtended();
		//panelVerticalGral.addComponent(form);
		gridview.addSelectionListener(new SelectionListener() {
						
		    @Override
		    public void select(SelectionEvent event) {
		        BeanItem<CotizacionVO> item = container.getItem(gridview.getSelectedRow());
		        //form.fieldGroup.setItemDataSource(item);
		        
		        try {
		        	
		        	//CotizacionesView cot = new CotizacionesView();
		        	
					MySub subCotizaciones = new MySub();
					subCotizaciones.setVista(form);
					//subCotizaciones.setVista(cot);
					
					  UI.getCurrent().addWindow(subCotizaciones);
				
		        
		        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		                       
		    }
		});
		
	}
	
  

}
