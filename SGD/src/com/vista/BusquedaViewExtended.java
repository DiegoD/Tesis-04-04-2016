package com.vista;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.controladores.ImpuestoControlador;
import com.excepciones.ConexionException;
import com.excepciones.InicializandoException;
import com.excepciones.Impuestos.ObteniendoImpuestosException;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.converter.StringToDateConverter;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.event.SelectionEvent;
import com.vaadin.event.SelectionEvent.SelectionListener;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.TextField;
import com.valueObject.CodigoGeneralizadoVO;
import com.valueObject.DocumDGIVO;
import com.valueObject.FuncionarioVO;
import com.valueObject.ImpuestoVO;
import com.valueObject.MonedaVO;
import com.valueObject.RubroVO;
import com.valueObject.Cuenta.CuentaVO;
import com.valueObject.Gasto.GastoVO;
import com.valueObject.TipoRubro.TipoRubroVO;
import com.valueObject.cliente.ClienteVO;
import com.valueObject.proceso.ProcesoVO;
import com.vista.Rubros.RubroViewExtended;

public class BusquedaViewExtended extends BusquedaView{
	
	private ArrayList<Object> lst;
	BeanItemContainer<ImpuestoVO> containerImpuesto;
	BeanItemContainer<DocumDGIVO> containerDocumentosDgi;
	BeanItemContainer<TipoRubroVO> containerTipoRubro;
	BeanItemContainer<MonedaVO> containerMoneda;
	BeanItemContainer<ClienteVO> containerCliente;
	BeanItemContainer<ProcesoVO> containerProceso;
	BeanItemContainer<RubroVO> containerRubro;
	BeanItemContainer<CuentaVO> containerCuenta;
	BeanItemContainer<GastoVO> containerGasto;
	BeanItemContainer<FuncionarioVO> containerFuncionario;
	Object seleccionado;
	IBusqueda main;
	
	public BusquedaViewExtended(IBusqueda main, Object obj){
		
		this.main = main;
		if(obj instanceof ImpuestoVO){
			this.containerImpuesto = new BeanItemContainer<ImpuestoVO>(ImpuestoVO.class);
			this.lblNombre.setValue("Impuestos");
			this.seleccionado = new ImpuestoVO();
			
		}
		else if(obj instanceof DocumDGIVO){
			
			this.containerDocumentosDgi = new BeanItemContainer<DocumDGIVO>(DocumDGIVO.class);
			this.lblNombre.setValue("Documentos");
			this.seleccionado = new DocumDGIVO();
		}
		else if(obj instanceof TipoRubroVO){
	
			this.containerTipoRubro = new BeanItemContainer<TipoRubroVO>(TipoRubroVO.class);
			this.lblNombre.setValue("Tipo Rubro");
			this.seleccionado = new TipoRubroVO();
		}
		
		else if(obj instanceof MonedaVO){
			
			this.containerMoneda = new BeanItemContainer<MonedaVO>(MonedaVO.class);
			this.lblNombre.setValue("Monedas");
			this.seleccionado = new MonedaVO();
		}
		
		else if(obj instanceof ClienteVO){
			
			this.containerCliente = new BeanItemContainer<ClienteVO>(ClienteVO.class);
			this.lblNombre.setValue("Clientes");
			this.seleccionado = new ClienteVO();
		}
		
		else if(obj instanceof ProcesoVO){
			
			this.containerProceso = new BeanItemContainer<ProcesoVO>(ProcesoVO.class);
			this.lblNombre.setValue("Procesos");
			this.seleccionado = new ProcesoVO();
		}
		
		else if(obj instanceof RubroVO){
			
			this.containerRubro = new BeanItemContainer<RubroVO>(RubroVO.class);
			this.lblNombre.setValue("Rubros");
			this.seleccionado = new RubroVO();
		}
		
		else if(obj instanceof CuentaVO){
			
			this.containerCuenta = new BeanItemContainer<CuentaVO>(CuentaVO.class);
			this.lblNombre.setValue("Cuentas");
			this.seleccionado = new CuentaVO();
		}
		else if(obj instanceof GastoVO){
			
			this.containerGasto = new BeanItemContainer<GastoVO>(GastoVO.class);
			this.lblNombre.setValue("Gastos");
			this.seleccionado = new GastoVO();
		}
		
		else if(obj instanceof FuncionarioVO){
			
			this.containerFuncionario = new BeanItemContainer<FuncionarioVO>(FuncionarioVO.class);
			this.lblNombre.setValue("Funcionarios");
			this.seleccionado = new FuncionarioVO();
		}
		
		grid.addSelectionListener(new SelectionListener() 
		{
			
		    @Override
		    public void select(SelectionEvent event) 
		    {
		       
		    	try
		    	{
		    		if(seleccionado instanceof ImpuestoVO){
			    		if(grid.getSelectedRow() != null){
			    			
			    			BeanItem<ImpuestoVO> item = containerImpuesto.getItem(grid.getSelectedRow());
					    	seleccionado = item.getBean(); 
					    	main.setInfo(seleccionado);	
					    	main.cerrarVentana();
					    }
		    		} 
		    		else if(seleccionado instanceof DocumDGIVO){
			    		if(grid.getSelectedRow() != null){
			    			
			    			BeanItem<DocumDGIVO> item = containerDocumentosDgi.getItem(grid.getSelectedRow());
					    	seleccionado = item.getBean(); 
					    	main.setInfo(seleccionado);	
					    	main.cerrarVentana();
					    }
		    		}
		    		
		    		else if(seleccionado instanceof TipoRubroVO){
			    		if(grid.getSelectedRow() != null){
			    			
			    			BeanItem<TipoRubroVO> item = containerTipoRubro.getItem(grid.getSelectedRow());
					    	seleccionado = item.getBean(); 
					    	main.setInfo(seleccionado);	
					    	main.cerrarVentana();
					    }
		    		}
		    		
		    		else if(seleccionado instanceof MonedaVO){
			    		if(grid.getSelectedRow() != null){
			    			
			    			BeanItem<MonedaVO> item = containerMoneda.getItem(grid.getSelectedRow());
					    	seleccionado = item.getBean(); 
					    	main.setInfo(seleccionado);	
					    	main.cerrarVentana();
					    }
		    		}
		    		
		    		else if(seleccionado instanceof ClienteVO){
			    		if(grid.getSelectedRow() != null){
			    			
			    			BeanItem<ClienteVO> item = containerCliente.getItem(grid.getSelectedRow());
					    	seleccionado = item.getBean(); 
					    	main.setInfo(seleccionado);	
					    	main.cerrarVentana();
					    }
		    		}
		    		
		    		else if(seleccionado instanceof ProcesoVO){
			    		if(grid.getSelectedRow() != null){
			    			
			    			BeanItem<ProcesoVO> item = containerProceso.getItem(grid.getSelectedRow());
					    	seleccionado = item.getBean(); 
					    	main.setInfo(seleccionado);	
					    	main.cerrarVentana();
					    }
		    		}
		    		
		    		else if(seleccionado instanceof RubroVO){
			    		if(grid.getSelectedRow() != null){
			    			
			    			BeanItem<RubroVO> item = containerRubro.getItem(grid.getSelectedRow());
					    	seleccionado = item.getBean(); 
					    	main.setInfo(seleccionado);	
					    	main.cerrarVentana();
					    }
		    		}
		    		
		    		else if(seleccionado instanceof CuentaVO){
			    		if(grid.getSelectedRow() != null){
			    			
			    			BeanItem<CuentaVO> item = containerCuenta.getItem(grid.getSelectedRow());
					    	seleccionado = item.getBean(); 
					    	main.setInfo(seleccionado);	
					    	main.cerrarVentana();
					    }
		    		}
		    		
		    		else if(seleccionado instanceof GastoVO){
			    		if(grid.getSelectedRow() != null){
			    			
			    			BeanItem<GastoVO> item = containerGasto.getItem(grid.getSelectedRow());
					    	seleccionado = item.getBean(); 
					    	main.setInfo(seleccionado);	
					    	main.cerrarVentana();
					    }
		    		}
		    		
		    		else if(seleccionado instanceof FuncionarioVO){
			    		if(grid.getSelectedRow() != null){
			    			
			    			BeanItem<FuncionarioVO> item = containerFuncionario.getItem(grid.getSelectedRow());
					    	seleccionado = item.getBean(); 
					    	main.setInfo(seleccionado);	
					    	main.cerrarVentana();
					    }
		    		}
		    		
		    	}
		    	catch(Exception e){
		    		Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
		    	}
		      
		    }
		});
	}

	public void inicializarGrilla(ArrayList<Object> lst) throws ObteniendoImpuestosException, ConexionException, InicializandoException{
	
		if(seleccionado instanceof ImpuestoVO){
			
			ArrayList<ImpuestoVO> lstImp = new ArrayList<>();
			
			ImpuestoVO i;
			for (Object o : lst) {
				
				i = (ImpuestoVO) o;
				lstImp.add(i);
			}
			
			this.containerImpuesto.addAll(lstImp);
			this.grid.setContainerDataSource(containerImpuesto);
			
			grid.removeColumn("activo");
			grid.removeColumn("fechaMod");
			grid.removeColumn("usuarioMod");
			grid.removeColumn("operacion");
			
			this.arreglarGrilla();
		}
		
		if(seleccionado instanceof DocumDGIVO){
			
			ArrayList<DocumDGIVO> lstDoc = new ArrayList<>();
			
			DocumDGIVO i;
			for (Object o : lst) {
				
				i = (DocumDGIVO) o;
				lstDoc.add(i);
			}
			
			this.containerDocumentosDgi.addAll(lstDoc);
			this.grid.setContainerDataSource(containerDocumentosDgi);
			
			grid.removeColumn("activo");
			grid.removeColumn("fechaMod");
			grid.removeColumn("usuarioMod");
			grid.removeColumn("operacion");
			
		
			this.arreglarGrilla();
			
		}
		
		if(seleccionado instanceof TipoRubroVO){
			
			ArrayList<TipoRubroVO> lstDoc = new ArrayList<>();
			
			TipoRubroVO i;
			for (Object o : lst) {
				
				i = (TipoRubroVO) o;
				lstDoc.add(i);
			}
			
			this.containerTipoRubro.addAll(lstDoc);
			this.grid.setContainerDataSource(containerTipoRubro);
			
			grid.removeColumn("fechaMod");
			grid.removeColumn("usuarioMod");
			grid.removeColumn("operacion");
			
		
			this.arreglarGrilla();
			
		}
		
		if(seleccionado instanceof MonedaVO){
			
			ArrayList<MonedaVO> lstDoc = new ArrayList<>();
			
			MonedaVO i;
			for (Object o : lst) {
				
				i = (MonedaVO) o;
				lstDoc.add(i);
			}
			
			this.containerMoneda.addAll(lstDoc);
			this.grid.setContainerDataSource(containerMoneda);
			
			grid.removeColumn("fechaMod");
			grid.removeColumn("usuarioMod");
			grid.removeColumn("operacion");
			
		
			this.arreglarGrilla();
			
		}
		
		if(seleccionado instanceof ClienteVO){
			
			ArrayList<ClienteVO> lstDoc = new ArrayList<>();
			
			ClienteVO i;
			for (Object o : lst) {
				
				i = (ClienteVO) o;
				lstDoc.add(i);
			}
			
			this.containerCliente.addAll(lstDoc);
			this.grid.setContainerDataSource(containerCliente);
			
			grid.removeColumn("fechaMod");
			grid.removeColumn("usuarioMod");
			grid.removeColumn("operacion");
			grid.removeColumn("tel");
			grid.removeColumn("direccion");
			grid.removeColumn("mail");
			grid.removeColumn("activo");
			grid.removeColumn("codigoDoc");
			grid.removeColumn("nombreDoc");
			grid.removeColumn("numeroDoc");
			
		
			this.arreglarGrilla();
			this.filtroGrilla();
			
		}
		
		if(seleccionado instanceof ProcesoVO){
			
			ArrayList<ProcesoVO> lstDoc = new ArrayList<>();
			
			ProcesoVO i;
			for (Object o : lst) {
				
				i = (ProcesoVO) o;
				lstDoc.add(i);
			}
			
			this.containerProceso.addAll(lstDoc);
			this.grid.setContainerDataSource(containerProceso);
			
			grid.removeColumn("tcMov");
			grid.removeColumn("simboloMoneda");
			grid.removeColumn("codCliente");
			grid.removeColumn("codMoneda");
			grid.removeColumn("fechaMod");
			grid.removeColumn("usuarioMod");
			grid.removeColumn("operacion");
			grid.removeColumn("nroMega");
			grid.removeColumn("codDocum");
			grid.removeColumn("nomDocum");
			grid.removeColumn("nroDocum");
			grid.removeColumn("fecDocum");
			grid.removeColumn("carpeta");
			grid.removeColumn("impMo");
			grid.removeColumn("impMn");
			grid.removeColumn("impTr");
			grid.removeColumn("kilos");
			grid.removeColumn("fecCruce");
			grid.removeColumn("marca");
			grid.removeColumn("medio");
			grid.removeColumn("observaciones");
			grid.removeColumn("descMoneda");
		
			this.arreglarGrillaProceso();
			this.filtroGrilla();
			
		}
		
		if(seleccionado instanceof RubroVO){
			
			ArrayList<RubroVO> lstDoc = new ArrayList<>();
			
			RubroVO i;
			for (Object o : lst) {
				
				i = (RubroVO) o;
				lstDoc.add(i);
			}
			
			this.containerRubro.addAll(lstDoc);
			this.grid.setContainerDataSource(containerRubro);
			
			grid.removeColumn("fechaMod");
			grid.removeColumn("usuarioMod");
			grid.removeColumn("operacion");
			
			grid.removeColumn("activo");
			grid.removeColumn("codigoImpuesto");
			grid.removeColumn("activoImpuesto");
			grid.removeColumn("descripcionTipoRubro");
			grid.removeColumn("codTipoRubro");
			grid.removeColumn("oficina");
			grid.removeColumn("proceso");
			grid.removeColumn("persona");
			
			//this.arreglarGrillaProceso();
			this.filtroGrilla();
			
		}
		
		if(seleccionado instanceof CuentaVO){
			
			ArrayList<CuentaVO> lstDoc = new ArrayList<>();
			
			CuentaVO i;
			for (Object o : lst) {
				
				i = (CuentaVO) o;
				lstDoc.add(i);
			}
			
			this.containerCuenta.addAll(lstDoc);
			this.grid.setContainerDataSource(containerCuenta);
			
			grid.removeColumn("fechaMod");
			grid.removeColumn("usuarioMod");
			grid.removeColumn("operacion");
			grid.removeColumn("activo");
			grid.removeColumn("lstRubros");
			
			//this.arreglarGrillaProceso();
			this.filtroGrilla();
			
		}
		
		if(seleccionado instanceof GastoVO){
			
			ArrayList<GastoVO> lstDoc = new ArrayList<>();
			
			GastoVO i;
			for (Object o : lst) {
				
				i = (GastoVO) o;
				lstDoc.add(i);
			}
			
			this.containerGasto.addAll(lstDoc);
			this.grid.setContainerDataSource(containerGasto);
			
			grid.removeColumn("fechaMod");
			grid.removeColumn("usuarioMod");
			grid.removeColumn("operacion");
			grid.removeColumn("activo");
			
			this.arreglarGrillaGasto(); /*FaltaImplementar*/
			this.filtroGrilla();
			
		}
		
		if(seleccionado instanceof FuncionarioVO){
			
			ArrayList<FuncionarioVO> lstDoc = new ArrayList<>();
			
			FuncionarioVO i;
			for (Object o : lst) {
				
				i = (FuncionarioVO) o;
				lstDoc.add(i);
			}
			
			this.containerFuncionario.addAll(lstDoc);
			this.grid.setContainerDataSource(containerFuncionario);
			
			grid.removeColumn("fechaMod");
			grid.removeColumn("usuarioMod");
			grid.removeColumn("operacion");
			grid.removeColumn("tel");
			grid.removeColumn("direccion");
			grid.removeColumn("mail");
			grid.removeColumn("codigoDoc");
			grid.removeColumn("nombreDoc");
			grid.removeColumn("numeroDoc");
			grid.removeColumn("activo");
			
			this.arreglarGrillaFuncionario(); /*FaltaImplementar*/
			this.filtroGrilla();
			
		}
	}
	
	private void arreglarGrilla()
	{
		
		List<Column> lstColumn = grid.getColumns();
		
		lstColumn.get(0).setWidth(200);
		lstColumn.get(0).setHeaderCaption("Código");
		lstColumn.get(1).setHeaderCaption("Nombre");
	}
	
	private void arreglarGrillaProceso(){
		List<Column> lstColumn = grid.getColumns();
		
		lstColumn.get(0).setWidth(150);
		lstColumn.get(0).setHeaderCaption("Número");
		lstColumn.get(1).setWidth(300);
		lstColumn.get(2).setWidth(150);
		//Modifica el formato de fecha en la grilla 
		grid.getColumn("fecha").setConverter(new StringToDateConverter(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override

			public DateFormat getFormat(Locale locale){

				return new SimpleDateFormat("dd/MM/yyyy");

			}

		});
	}
	
	/*Este falta implementarlo es copia del de proceso*/
	private void arreglarGrillaGasto(){
		List<Column> lstColumn = grid.getColumns();
		
		lstColumn.get(0).setWidth(150);
		lstColumn.get(0).setHeaderCaption("Número");
		lstColumn.get(1).setWidth(300);
		lstColumn.get(2).setWidth(150);
		//Modifica el formato de fecha en la grilla 
		grid.getColumn("fecha").setConverter(new StringToDateConverter(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override

			public DateFormat getFormat(Locale locale){

				return new SimpleDateFormat("dd/MM/yyyy");

			}

		});
	}
	
	/*Este falta implementarlo es copia del de proceso*/
	private void arreglarGrillaFuncionario(){
		List<Column> lstColumn = grid.getColumns();
		
		lstColumn.get(0).setWidth(150);
		lstColumn.get(0).setHeaderCaption("Número");
		//Modifica el formato de fecha en la grilla 
	}
	
	private void filtroGrilla()
	{
		try
		{
			 
			com.vaadin.ui.Grid.HeaderRow filterRow = grid.appendHeaderRow();
	
			// Set up a filter for all columns
			for (Object pid: grid.getContainerDataSource()
			                     .getContainerPropertyIds()) 
			{
			    
				com.vaadin.ui.Grid.HeaderCell cell = filterRow.getCell(pid);
			    
			    if(cell != null)
				{
				    // Have an input field to use for filter
				    TextField filterField = new TextField();
				    filterField.setImmediate(true);
				    filterField.setWidth("100%");
				    filterField.setHeight("80%");
				    filterField.setInputPrompt("Filtro");
				    // Update filter When the filter input is changed
				    filterField.addTextChangeListener(change -> {
				        
				    	if(seleccionado instanceof ImpuestoVO) /*PARA IMPUESTOS*/
				    	{
					    	// Can't modify filters so need to replace
					    	this.containerImpuesto.removeContainerFilters(pid);
			
					        // (Re)create the filter if necessary
					        if (! change.getText().isEmpty())
					        	this.containerImpuesto.addContainerFilter(
					                new SimpleStringFilter(pid,
					                    change.getText(), true, false));
					        
				    	}
				    	else if(seleccionado instanceof DocumDGIVO) /*PARA DOCUMENTOS GDI*/
				    	{
					    	// Can't modify filters so need to replace
					    	this.containerDocumentosDgi.removeContainerFilters(pid);
			
					        // (Re)create the filter if necessary
					        if (! change.getText().isEmpty())
					        	this.containerDocumentosDgi.addContainerFilter(
					                new SimpleStringFilter(pid,
					                    change.getText(), true, false));
				    	}
				    	
				    	else if(seleccionado instanceof TipoRubroVO) /*PARA DOCUMENTOS GDI*/
				    	{
					    	// Can't modify filters so need to replace
					    	this.containerTipoRubro.removeContainerFilters(pid);
			
					        // (Re)create the filter if necessary
					        if (! change.getText().isEmpty())
					        	this.containerTipoRubro.addContainerFilter(
					                new SimpleStringFilter(pid,
					                    change.getText(), true, false));
				    	}
				    	
				    	else if(seleccionado instanceof MonedaVO) /*PARA MONEDAS*/
				    	{
					    	// Can't modify filters so need to replace
					    	this.containerMoneda.removeContainerFilters(pid);
			
					        // (Re)create the filter if necessary
					        if (! change.getText().isEmpty())
					        	this.containerMoneda.addContainerFilter(
					                new SimpleStringFilter(pid,
					                    change.getText(), true, false));
				    	}
				    	
				    	else if(seleccionado instanceof ClienteVO) /*PARA CLIENTES*/
				    	{
					    	// Can't modify filters so need to replace
					    	this.containerCliente.removeContainerFilters(pid);
			
					        // (Re)create the filter if necessary
					        if (! change.getText().isEmpty())
					        	this.containerCliente.addContainerFilter(
					                new SimpleStringFilter(pid,
					                    change.getText(), true, false));
				    	}
				    	
				    	else if(seleccionado instanceof ProcesoVO) /*PARA PROCESOS*/
				    	{
					    	// Can't modify filters so need to replace
					    	this.containerProceso.removeContainerFilters(pid);
			
					        // (Re)create the filter if necessary
					        if (! change.getText().isEmpty())
					        	this.containerProceso.addContainerFilter(
					                new SimpleStringFilter(pid,
					                    change.getText(), true, false));
				    	}
				    	
				    	else if(seleccionado instanceof RubroVO) /*PARA RUBROS*/
				    	{
					    	// Can't modify filters so need to replace
					    	this.containerRubro.removeContainerFilters(pid);
			
					        // (Re)create the filter if necessary
					        if (! change.getText().isEmpty())
					        	this.containerRubro.addContainerFilter(
					                new SimpleStringFilter(pid,
					                    change.getText(), true, false));
				    	}
				    	
				    	else if(seleccionado instanceof CuentaVO) /*PARA CUENTAS*/
				    	{
					    	// Can't modify filters so need to replace
					    	this.containerCuenta.removeContainerFilters(pid);
			
					        // (Re)create the filter if necessary
					        if (! change.getText().isEmpty())
					        	this.containerCuenta.addContainerFilter(
					                new SimpleStringFilter(pid,
					                    change.getText(), true, false));
				    	}
				    	
				    	else if(seleccionado instanceof GastoVO) /*PARA GASTOS*/
				    	{
					    	// Can't modify filters so need to replace
					    	this.containerGasto.removeContainerFilters(pid);
			
					        // (Re)create the filter if necessary
					        if (! change.getText().isEmpty())
					        	this.containerGasto.addContainerFilter(
					                new SimpleStringFilter(pid,
					                    change.getText(), true, false));
				    	}
				    	
				    	else if(seleccionado instanceof FuncionarioVO) /*PARA GASTOS*/
				    	{
					    	// Can't modify filters so need to replace
					    	this.containerFuncionario.removeContainerFilters(pid);
			
					        // (Re)create the filter if necessary
					        if (! change.getText().isEmpty())
					        	this.containerFuncionario.addContainerFilter(
					                new SimpleStringFilter(pid,
					                    change.getText(), true, false));
				    	}
				    });

				    cell.setComponent(filterField);
				}
			}
			
		}
		catch(Exception e)	{
			 System.out.println(e.getStackTrace());
		}
	}

}
