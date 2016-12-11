package com.vista;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
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
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.TextField;
import com.valueObject.CodigoGeneralizadoVO;
import com.valueObject.DocumDGIVO;
import com.valueObject.FormularioVO;
import com.valueObject.FuncionarioVO;
import com.valueObject.ImpuestoVO;
import com.valueObject.MonedaVO;
import com.valueObject.RubroCuentaVO;
import com.valueObject.RubroVO;
import com.valueObject.TitularVO;
import com.valueObject.Cuenta.CuentaVO;
import com.valueObject.Docum.FacturaVO;
import com.valueObject.Docum.ReciboDetalleVO;
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
	BeanItemContainer<TitularVO> containerTitulares;
	BeanItemContainer<ProcesoVO> containerProceso;
	BeanItemContainer<RubroVO> containerRubro;
	BeanItemContainer<CuentaVO> containerCuenta;
	BeanItemContainer<GastoVO> containerGasto;
	BeanItemContainer<FacturaVO> containerFactura;
	BeanItemContainer<FuncionarioVO> containerFuncionario;
	BeanItemContainer<RubroCuentaVO> containerRubroCuenta;
	Object seleccionado;
	IBusqueda main;
	private Object objRef;  /*Variable para diferenciar en el boton agregar
	 						para identificar que objet viene por parametro*/
	
	public BusquedaViewExtended(IBusqueda main, Object obj){
		
		this.objRef = obj;
		
		/*Ocultamos el boton de agresgar, solamente
		 * se utiliza para los casos de la ventana
		 * multiseleccion*/
		this.btnAgregar.setEnabled(false);
		this.btnAgregar.setVisible(false);
		
		this.btnCancelar.setEnabled(false);
		this.btnCancelar.setVisible(false);
		
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
		
		else if(obj instanceof TitularVO){
			
			this.containerTitulares = new BeanItemContainer<TitularVO>(TitularVO.class);
			this.lblNombre.setValue("Titulares");
			this.seleccionado = new TitularVO();
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
			
			/*Dejamos que sea multi seleccion la grilla*/
			grid.setSelectionMode(SelectionMode.MULTI);
			
			
			/*Mostramos el boton de agregar*/
			this.btnAgregar.setEnabled(true);
			this.btnAgregar.setVisible(true);
			
			this.btnCancelar.setEnabled(true);
			this.btnCancelar.setVisible(true);
			
			this.containerGasto = new BeanItemContainer<GastoVO>(GastoVO.class);
			this.lblNombre.setValue("Gastos");
			this.seleccionado = new GastoVO();
			
		}else if(obj instanceof FacturaVO){
			
			/*Dejamos que sea multi seleccion la grilla*/
			grid.setSelectionMode(SelectionMode.MULTI);
			
			
			/*Mostramos el boton de agregar*/
			this.btnAgregar.setEnabled(true);
			this.btnAgregar.setVisible(true);
			
			this.btnCancelar.setEnabled(true);
			this.btnCancelar.setVisible(true);
			
			this.containerFactura = new BeanItemContainer<FacturaVO>(FacturaVO.class);
			this.lblNombre.setValue("Facturas");
			this.seleccionado = new FacturaVO();
		}
		else if(obj instanceof FuncionarioVO){
			
			this.containerFuncionario = new BeanItemContainer<FuncionarioVO>(FuncionarioVO.class);
			this.lblNombre.setValue("Funcionarios");
			this.seleccionado = new FuncionarioVO();
		}
		
		else if(obj instanceof RubroCuentaVO){
			
			this.containerRubroCuenta = new BeanItemContainer<RubroCuentaVO>(RubroCuentaVO.class);
			this.lblNombre.setValue("Rubro/Cuenta");
			this.seleccionado = new RubroCuentaVO();
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
		    		
		    		else if(seleccionado instanceof TitularVO){
			    		if(grid.getSelectedRow() != null){
			    			
			    			BeanItem<TitularVO> item = containerTitulares.getItem(grid.getSelectedRow());
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
			    		/*Si es Gasto no hacemos nada, ya que es multi seleccion
			    		 *se maneja desde el boton de agregar*/
		    			
		    		
		    		}else if(seleccionado instanceof FacturaVO){
			    		/*Si es Factura no hacemos nada, ya que es multi seleccion
			    		 *se maneja desde el boton de agregar*/
		    		
		    		}
		    		
		    		else if(seleccionado instanceof FuncionarioVO){
			    		if(grid.getSelectedRow() != null){
			    			
			    			BeanItem<FuncionarioVO> item = containerFuncionario.getItem(grid.getSelectedRow());
					    	seleccionado = item.getBean(); 
					    	main.setInfo(seleccionado);	
					    	main.cerrarVentana();
					    }
		    		}
		    		
		    		else if(seleccionado instanceof RubroCuentaVO){
			    		if(grid.getSelectedRow() != null){
			    			
			    			BeanItem<RubroCuentaVO> item = containerRubroCuenta.getItem(grid.getSelectedRow());
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
			grid.removeColumn("tipo");
			
		
			this.arreglarGrilla();
			this.filtroGrilla();
			
		}
		
		if((seleccionado instanceof TitularVO) && !(seleccionado instanceof ClienteVO )){
			
			ArrayList<TitularVO> lstDoc = new ArrayList<>();
			
			TitularVO i;
			for (Object o : lst) {
				
				i = (TitularVO) o;
				lstDoc.add(i);
			}
			
			this.containerTitulares.addAll(lstDoc);
			this.grid.setContainerDataSource(containerTitulares);
			
			grid.removeColumn("tel");
			grid.removeColumn("direccion");
			grid.removeColumn("mail");
			grid.removeColumn("activo");
			grid.removeColumn("fechaMod");
			grid.removeColumn("usuarioMod");
			grid.removeColumn("operacion");
			grid.removeColumn("codigoDoc");
		
			grid.setColumnOrder("codigo", "nombre", "nombreDoc", "numeroDoc", "tipo");
			grid.getColumn("codigo").setWidth(100);
			grid.getColumn("nombreDoc").setWidth(150);
			grid.getColumn("nombreDoc").setHeaderCaption("Documento");
			grid.getColumn("numeroDoc").setHeaderCaption("Número");
			
			
			//this.arreglarGrilla();
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
			grid.removeColumn("codDocum");
			grid.removeColumn("nomDocum");
			grid.removeColumn("nroDocum");
			grid.removeColumn("fecDocum");
			grid.removeColumn("impMo");
			grid.removeColumn("impMn");
			grid.removeColumn("impTr");
			grid.removeColumn("kilos");
			grid.removeColumn("fecCruce");
			grid.removeColumn("marca");
			grid.removeColumn("medio");
			grid.removeColumn("observaciones");
			grid.removeColumn("descMoneda");
			grid.removeColumn("codRubro");
			
			grid.removeColumn("codCuenta");
			grid.removeColumn("codImpuesto");
			grid.removeColumn("impSubTot");
			grid.removeColumn("nomCuenta");
			grid.removeColumn("nomRubro");
			grid.removeColumn("linea");
			
			grid.getColumn("descripcion").setWidth(300);
			grid.getColumn("codigo").setWidth(100);
			grid.getColumn("fecha").setWidth(100);
			grid.getColumn("nomCliente").setWidth(150);
			grid.getColumn("nroMega").setWidth(100);
			grid.getColumn("carpeta").setWidth(100);
			
			
			grid.getColumn("nomCliente").setHeaderCaption("Cliente");
			grid.getColumn("codigo").setHeaderCaption("Proceso");
			grid.getColumn("nroMega").setHeaderCaption("Mega");
			grid.getColumn("carpeta").setHeaderCaption("Carpeta");
			
			grid.setColumnOrder("codigo", "fecha", "nomCliente", "descripcion", "carpeta", "nroMega");
			
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
			grid.removeColumn("facturable");
			
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
			grid.removeColumn("nacional");
			grid.removeColumn("estadoGasto");
			grid.removeColumn("tipo");
			
			grid.setEditorEnabled(true);
			grid.getColumn("impTotMn").setEditable(true);
			
			grid.getColumn("simboloMoneda").setHeaderCaption("Moneda");
			grid.getColumn("impTotMo").setHeaderCaption("Importe");
			
			grid.getColumn("referencia").setWidth(300);
			grid.getColumn("nroDocum").setWidth(90);
			grid.getColumn("codProceso").setWidth(90);
			grid.getColumn("simboloMoneda").setWidth(100);
			grid.getColumn("impTotMo").setWidth(200);
			
			grid.getColumn("nroDocum").setHeaderCaption("Doc");
			grid.getColumn("codProceso").setHeaderCaption("Proceso");
			
			this.arreglarGrillaGasto(); /*FaltaImplementar*/
			this.filtroGrilla();
			
		}
		
		if(seleccionado instanceof FacturaVO){
			
			ArrayList<FacturaVO> lstDoc = new ArrayList<>();
			
			FacturaVO i;
			for (Object o : lst) {
				
				i = (FacturaVO) o;
				lstDoc.add(i);
			}
			
			this.containerFactura.addAll(lstDoc);
			this.grid.setContainerDataSource(containerFactura);
			


			
			this.arreglarGrillaFactura(); /*FaltaImplementar*/
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
		
		if(seleccionado instanceof RubroCuentaVO){
			
			ArrayList<RubroCuentaVO> lstDoc = new ArrayList<>();
			
			RubroCuentaVO i;
			for (Object o : lst) {
				
				i = (RubroCuentaVO) o;
				lstDoc.add(i);
			}
			
			this.containerRubroCuenta.addAll(lstDoc);
			this.grid.setContainerDataSource(containerRubroCuenta);
			
			grid.removeColumn("cod_rubro");
			grid.removeColumn("cod_cuenta");
			grid.removeColumn("cod_impuesto");
			grid.removeColumn("oficina");
			grid.removeColumn("proceso");
			grid.removeColumn("persona");
			grid.removeColumn("cod_tipoRubro");
			grid.removeColumn("facturable");
			
			this.arreglarGrillaRubroCuenta(); /*FaltaImplementar*/
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
		
		
		
	try{	
		grid.getColumn("codCtaInd").setHidden(true);
		grid.getColumn("codCuenta").setHidden(true);
		grid.getColumn("codDocum").setHidden(true);
		grid.getColumn("codEmp").setHidden(true);
		grid.getColumn("codImpuesto").setHidden(true);
		grid.getColumn("codMoneda").setHidden(true);
		//lstGastos.getColumn("codProceso").setHidden(true);
		grid.getColumn("codRubro").setHidden(true);
		grid.getColumn("codTitular").setHidden(true);
		//lstGastos.getColumn("cuenta").setHidden(true);
		grid.getColumn("descProceso").setHidden(true);
		grid.getColumn("fecDoc").setHidden(true);
		grid.getColumn("fecValor").setHidden(true);
		grid.getColumn("impImpuMn").setHidden(true);
		grid.getColumn("impImpuMo").setHidden(true);
		grid.getColumn("impSubMn").setHidden(true);
		grid.getColumn("impSubMo").setHidden(true);
		grid.getColumn("linea").setHidden(true);
		grid.getColumn("impTotMn").setHidden(true);
		grid.getColumn("nomCuenta").setHidden(true);
		grid.getColumn("nomImpuesto").setHidden(true);
		grid.getColumn("nomMoneda").setHidden(true);
		grid.getColumn("nomRubro").setHidden(true);
		grid.getColumn("nomTitular").setHidden(true);
		grid.getColumn("nroTrans").setHidden(true);
		grid.getColumn("porcentajeImpuesto").setHidden(true);
		grid.getColumn("serieDocum").setHidden(true);
		//grid.getColumn("simboloMoneda").setHidden(true);
		grid.getColumn("tcMov").setHidden(true);
		//grid.getColumn("usuarioMod").setHidden(true);
		
		grid.setColumnOrder("nroDocum", "referencia", "simboloMoneda", "impTotMo", "codProceso");
		
	}catch(Exception e){
		int i = 0;
		
	}
	
	}
	
	private void arreglarGrillaFactura(){ 
		
		List<Column> lstColumn = grid.getColumns();
		
		lstColumn.get(0).setWidth(150);
		lstColumn.get(0).setHeaderCaption("Número");
		lstColumn.get(1).setWidth(300);
		lstColumn.get(2).setWidth(150);
		
		try{	
			
			grid.getColumn("codProceso").setHidden(true);
			grid.getColumn("descProceso").setHidden(true);

			grid.getColumn("codEmp").setHidden(true);
			
			grid.getColumn("codTitular").setHidden(true);
			grid.getColumn("nomTitular").setHidden(true);
			grid.getColumn("tipo").setHidden(true);

			grid.getColumn("nomTitular").setHidden(true);
			grid.getColumn("codTitular").setHidden(true);
			
			grid.getColumn("codMoneda").setHidden(true);
			grid.getColumn("nomMoneda").setHidden(true);
			grid.getColumn("simboloMoneda").setHidden(true);
			
			grid.getColumn("impTotMn").setHidden(true);

			grid.getColumn("tcMov").setHidden(true);
			grid.getColumn("fecValor").setHidden(true);
			
			grid.getColumn("nroTrans").setHidden(true);

			grid.getColumn("usuarioMod").setHidden(true);
			
			grid.getColumn("fechaMod").setHidden(true);
			grid.getColumn("operacion").setHidden(true);

			grid.getColumn("codCuenta").setHidden(true);
			grid.getColumn("nomCuenta").setHidden(true);

			grid.getColumn("codCtaInd").setHidden(true);
			grid.getColumn("nacional").setHidden(true);
			
			grid.getColumn("codProceso").setHidden(true);
			
			grid.getColumn("simboloMoneda").setHeaderCaption("Moneda");
			grid.getColumn("impTotMo").setHeaderCaption("Importe");
			
			grid.getColumn("referencia").setWidth(300);
			grid.getColumn("serieDocum").setWidth(50);
			grid.getColumn("nroDocum").setWidth(90);
			grid.getColumn("simboloMoneda").setWidth(100);
			grid.getColumn("impTotMo").setWidth(200);
			
			grid.getColumn("nroDocum").setHeaderCaption("Doc");
			//grid.getColumn("codProceso").setHeaderCaption("Proceso");
			
			grid.getColumn("codDocum").setHidden(true);
			grid.getColumn("detalle").setHidden(true);


			grid.getColumn("impSubMn").setHidden(true);
			grid.getColumn("impSubMo").setHidden(true);
			grid.getColumn("impuTotMn").setHidden(true);
			grid.getColumn("impuTotMo").setHidden(true);
			
			grid.setColumnOrder("fecDoc","serieDocum", "nroDocum", "referencia", "simboloMoneda", "impTotMo");
			
		}catch(Exception e){
			int i = 0;
			
		}
	
	}
	
	/*Este falta implementarlo es copia del de proceso*/
	private void arreglarGrillaFuncionario(){
		List<Column> lstColumn = grid.getColumns();
		
		lstColumn.get(0).setWidth(150);
		lstColumn.get(0).setHeaderCaption("Número");
		//Modifica el formato de fecha en la grilla 
	}
	
	
	/*Este falta implementarlo es copia del de proceso*/
	private void arreglarGrillaRubroCuenta(){
		List<Column> lstColumn = grid.getColumns();
		grid.setColumnOrder("descripcionRubro", "descripcionCuenta", "descripcionImpuesto", "porcentaje", "descripcionTipoRubro");
		grid.getColumn("descripcionRubro").setHeaderCaption("Rubro");
		grid.getColumn("descripcionCuenta").setHeaderCaption("Cuenta");
		grid.getColumn("descripcionImpuesto").setHeaderCaption("Impuesto");
		grid.getColumn("porcentaje").setHeaderCaption("Porcentaje");
		grid.getColumn("descripcionTipoRubro").setHeaderCaption("Tipo Rubro");
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
				    	
				    	else if(seleccionado instanceof TitularVO) /*PARA CLIENTES*/
				    	{
					    	// Can't modify filters so need to replace
					    	this.containerTitulares.removeContainerFilters(pid);
			
					        // (Re)create the filter if necessary
					        if (! change.getText().isEmpty())
					        	this.containerTitulares.addContainerFilter(
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
				    	
				    	else if(seleccionado instanceof FacturaVO) /*PARA FACTURAS*/
				    	{
					    	// Can't modify filters so need to replace
					    	this.containerFactura.removeContainerFilters(pid);
			
					        // (Re)create the filter if necessary
					        if (! change.getText().isEmpty())
					        	this.containerFactura.addContainerFilter(
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
				    	
				    	else if(seleccionado instanceof RubroCuentaVO) /*PARA GASTOS*/
				    	{
					    	// Can't modify filters so need to replace
					    	this.containerRubroCuenta.removeContainerFilters(pid);
			
					        // (Re)create the filter if necessary
					        if (! change.getText().isEmpty())
					        	this.containerRubroCuenta.addContainerFilter(
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
		
		
		/**
		 * Listener boton agregar
		 * Se utiliza para los casos que la grilla
		 * es multi seleccion
		 *
		 */
		this.btnAgregar.addClickListener(click -> {
			
    		try {
				
				/*ACA*/
				ArrayList<Object> lstSeleccionados = new ArrayList<Object>();
				
				/*Obtenemos los formularios seleccionados y se los pasamos a
				 * la View de Grupos para agregarlos*/
				Collection<Object> col= grid.getSelectedRows();
				
				
				if(objRef instanceof GastoVO){ /*Si es gasto*/
					GastoVO aux;
					for (Object object : col) {
						aux = (GastoVO)object;
						
											
						lstSeleccionados.add(aux);
						
					}
					
				} else if(objRef instanceof FacturaVO){ /*Si es factura*/
					FacturaVO aux;
					ReciboDetalleVO recVO; 
					for (Object object : col) {
											
						aux = (FacturaVO)object;
						recVO = getReciboDetalleVODesdeFacturaVO(aux); /*Convertimos la factura en detalle
						 												de recibo*/

						lstSeleccionados.add(recVO);
						
					}
				}
				
				
		    	main.setInfoLst(lstSeleccionados);	
		    	main.cerrarVentana();
	    	
	    	
			} catch (Exception e) {
				Mensajes.mostrarMensajeError(Variables.ERROR_INESPERADO);
			}	
		    
			
		});
		
		this.btnCancelar.addClickListener(click -> {
			
			main.cerrarVentana();
			
		});
	}
	
	/***
	 * Para el detalle del recibo, dado que obtenemos las FacturasVO
	 * necesitamos pasarlo a ReciboDetalleVO
	 */
	private ReciboDetalleVO getReciboDetalleVODesdeFacturaVO(FacturaVO t){
		
		ReciboDetalleVO r = new ReciboDetalleVO();
		
		r.setCodImpuesto("0");
		r.setImpImpuMn(t.getImpuTotMn());
		r.setImpImpuMo(t.getImpuTotMo());
		r.setImpSubMn(t.getImpSubMn());
		r.setImpSubMo(t.getImpSubMo());
		r.setTcMov(t.getTcMov());

		r.setCodProceso(String.valueOf(t.getCodProceso()));
		r.setNomRubro("No asignado");
		r.setCodRubro("0");
		r.setCodCtaInd("recibo");
		r.setLinea(1);
		r.setDescProceso(t.getDescProceso());
		r.setEstadoGasto("0");
		r.setPorcentajeImpuesto(0);
		r.setNomImpuesto("No asignado");
		
		
		r.setFecDoc(t.getFecDoc()); 
		r.setCodDocum(t.getCodDocum());
		r.setSerieDocum(t.getSerieDocum());
		r.setNroDocum(t.getNroDocum());
		r.setCodEmp(t.getCodEmp());
		
		r.setCodTitular(t.getCodTitular());
		r.setNomTitular(t.getNomTitular());
		r.setTipo(t.getTipo());
		
		r.setReferencia(t.getReferencia());
		r.setNomTitular(t.getNomTitular());
		r.setCodTitular(t.getCodTitular());
		
		r.setCodMoneda(t.getCodMoneda());
		r.setNomMoneda(t.getNomMoneda());
		r.setSimboloMoneda(t.getSimboloMoneda());
		
		r.setImpTotMn(t.getImpTotMn());
		r.setImpTotMo(t.getImpTotMo());
		r.setTcMov(t.getTcMov());
		r.setFecValor(t.getFecValor());
		
		r.setNroTrans(0); /*Se cambia*/
		r.setUsuarioMod(t.getUsuarioMod());
		
		if(t.getFechaMod() !=null)
			r.setFechaMod(t.getFechaMod());
		r.setOperacion(t.getOperacion());
		
		r.setCodCuenta(t.getCodCuenta());
		
		if(t.getNomCuenta() != null)
			r.setNomCuenta(t.getNomCuenta());
		
		r.setCodCtaInd(t.getCodCtaInd());
		r.setNacional(t.isNacional());
		
		return r;
	}
	

}
