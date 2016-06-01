package com.vista;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;

import com.vaadin.client.widgets.Grid.HeaderRow;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.SelectionEvent;
import com.vaadin.event.SelectionEvent.SelectionListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Grid;
import com.vaadin.ui.UI;
import com.valueObject.AuditoriaVO;
import com.valueObject.CotizacionVO;

public class CotizacionesPanelExtended extends CotizacionesPanel {
	
	public class ItemGrilla 
	{
		private Date fecha;
		private int codMoneda;
		private float impCompra;
		private float impVenta;
		
		public Date getFecha() {
			return fecha;
		}


		public void setFecha(Date fecha) {
			this.fecha = fecha;
		}


		public int getCodMoneda() {
			return codMoneda;
		}


		public void setCodMoneda(int codMoneda) {
			this.codMoneda = codMoneda;
		}


		public float getImpCompra() {
			return impCompra;
		}


		public void setImpCompra(float impCompra) {
			this.impCompra = impCompra;
		}


		public float getImpVenta() {
			return impVenta;
		}


		public void setImpVenta(float impVenta) {
			this.impVenta = impVenta;
		}


		
		
			
		public ItemGrilla(Date fecha,int codMoneda, float impCompra, float impVenta
				          ){
			
			//super(usuarioMod, fechaMod);
			
			this.fecha = fecha;
			this.codMoneda = codMoneda;
			this.impCompra = impCompra;
			this.impVenta = impVenta;
			
			
		}
			
	}
	
	
	//private ArrayList<ItemGrilla> listItemsGrilla;
	private MenuExtended menuAcual;
	
	//private CotizacionesView form;
	private FacturaEjExtended form;
	BeanItemContainer<ItemGrilla> container;
	
	public void setMenu(MenuExtended menu){
		this.menuAcual = menu;
	}
	
	
	public CotizacionesPanelExtended() throws InstantiationException, IllegalAccessException, ClassNotFoundException, FileNotFoundException, IOException{
		
		this.inicializarGrilla();
		this.inicializarBoton();
		
	}
	
	
	private void inicializarBoton(){
		
		  this.form = new FacturaEjExtended();
		  
		  boton.addClickListener(new Button.ClickListener() {
			  @Override
			    public void buttonClick(ClickEvent event) {
			    	
			    	MySub sub;
					sub = new MySub();
					sub.setVista(form);
					
					
					 // Add it to the root component
					UI.getCurrent().addWindow(sub);
			    }
			});
	}
	
	private void inicializarGrilla() throws InstantiationException, IllegalAccessException, ClassNotFoundException, FileNotFoundException, IOException{
		java.util.Date utilDate = new java.util.Date();
		
		java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
		
		ItemGrilla c1 = new ItemGrilla(sqlDate, 1, 10, 12);
		ItemGrilla c2 = new ItemGrilla(sqlDate, 2, 18, 22);
		
				
		this.container = 
				new BeanItemContainer<ItemGrilla>(ItemGrilla.class);
		
		container.addBean(c1);
		container.addBean(c2);
		
		gridview.setContainerDataSource(container);
		
		//gridview.getColumn(1).setHeaderCaption("Fulano");
		
		//com.vaadin.ui.Grid.HeaderRow row = gridview.prependHeaderRow();
		//row.getCell("Fecha").setHtml("<b>Full name</b>");
		
		gridview.setEditorEnabled(true);
		gridview.setEditorSaveCaption("Save my data, please!");
		
		this.form = new FacturaEjExtended();
		//panelVerticalGral.addComponent(form);
		gridview.addSelectionListener(new SelectionListener() {
						
		    @Override
		    public void select(SelectionEvent event) {
		        BeanItem<ItemGrilla> item = container.getItem(gridview.getSelectedRow());
		        //form.fieldGroup.setItemDataSource(item);
		        
		       
		        	
		        	//CotizacionesView cot = new CotizacionesView();
		        	
		        	
		        	
					MySub subCotizaciones = new MySub();
					subCotizaciones.setVista(form);
					//subCotizaciones.setVista(cot);
					
					  UI.getCurrent().addWindow(subCotizaciones);
				
		        
		        
		                       
		    }
		});
		
	}
	
  

}
