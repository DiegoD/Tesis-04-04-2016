package com.logica;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import com.abstractFactory.AbstractFactoryBuilder;
import com.abstractFactory.IAbstractFactory;
import com.excepciones.*;
import com.excepciones.Bancos.ObteniendoBancosException;
import com.excepciones.Bancos.ObteniendoCuentasBcoException;
import com.excepciones.Cheques.ExisteChequeException;
import com.excepciones.Cheques.ObteniendoChequeException;
import com.excepciones.CodigosGeneralizados.EliminandoCodigoGeneralizadoException;
import com.excepciones.CodigosGeneralizados.ExisteCodigoException;
import com.excepciones.CodigosGeneralizados.InsertandoCodigoException;
import com.excepciones.CodigosGeneralizados.ModificandoCodigoException;
import com.excepciones.CodigosGeneralizados.NoExisteCodigoException;
import com.excepciones.CodigosGeneralizados.ObteniendoCodigosException;
import com.excepciones.Conciliaciones.EliminandoConcialiacionException;
import com.excepciones.Conciliaciones.ExisteConciliacionException;
import com.excepciones.Conciliaciones.InsertandoConciliacionException;
import com.excepciones.Conciliaciones.ModificandoConciliacionException;
import com.excepciones.Conciliaciones.MovimientoConciliadoException;
import com.excepciones.Conciliaciones.NoExisteConciliacionException;
import com.excepciones.Conciliaciones.ObteniendoConciliacionException;
import com.excepciones.Cotizaciones.ExisteCotizacionException;
import com.excepciones.Cotizaciones.InsertandoCotizacionException;
import com.excepciones.Cotizaciones.ModificandoCotizacionException;
import com.excepciones.Cotizaciones.NoExisteCotizacionException;
import com.excepciones.Cotizaciones.ObteniendoCotizacionesException;
import com.excepciones.Cuentas.ExisteCuentaException;
import com.excepciones.Cuentas.InsertandoCuentaException;
import com.excepciones.Cuentas.MemberCuentaException;
import com.excepciones.Cuentas.ModificandoCuentaException;
import com.excepciones.Cuentas.NoExisteCuentaException;
import com.excepciones.Cuentas.ObteniendoCuentasException;
import com.excepciones.Depositos.EliminandoDepositoException;
import com.excepciones.Depositos.ExisteDepositoException;
import com.excepciones.Depositos.InsertandoDepositoException;
import com.excepciones.Depositos.ModificandoDepositoException;
import com.excepciones.Depositos.NoExisteDepositoException;
import com.excepciones.Depositos.ObteniendoDepositoException;
import com.excepciones.DocLog.InsertandoLogException;
import com.excepciones.DocLog.ModificandoLogException;
import com.excepciones.Documentos.ExisteDocumentoException;
import com.excepciones.Documentos.InsertandoDocumentoException;
import com.excepciones.Documentos.ModificandoDocumentoException;
import com.excepciones.Documentos.NoExisteDocumentoException;
import com.excepciones.Documentos.ObteniendoDocumentosException;
import com.excepciones.Egresos.ExisteEgresoCobroException;
import com.excepciones.Empresas.ExisteEmpresaException;
import com.excepciones.Empresas.InsertandoEmpresaException;
import com.excepciones.Empresas.ModificandoEmpresaException;
import com.excepciones.Empresas.NoExisteEmpresaException;
import com.excepciones.Empresas.ObteniendoEmpresasException;
import com.excepciones.Factura.ExisteFacturaException;
import com.excepciones.Factura.NoExisteFacturaException;
import com.excepciones.Factura.ObteniendoSaldoException;
import com.excepciones.Gastos.EliminandoGastoException;
import com.excepciones.Gastos.ExisteGastoException;
import com.excepciones.Gastos.IngresandoGastoException;
import com.excepciones.Gastos.ModificandoGastoException;
import com.excepciones.Gastos.NoExisteGastoException;
import com.excepciones.Gastos.ObteniendoGastosException;
import com.excepciones.Impuestos.ExisteImpuestoException;
import com.excepciones.Impuestos.InsertandoImpuestoException;
import com.excepciones.Impuestos.ModificandoImpuestoException;
import com.excepciones.Impuestos.NoExisteImpuestoException;
import com.excepciones.Impuestos.ObteniendoImpuestosException;
import com.excepciones.IngresoCobros.ExisteIngresoCobroException;
import com.excepciones.IngresoCobros.InsertandoIngresoCobroException;
import com.excepciones.IngresoCobros.ModificandoIngresoCobroException;
import com.excepciones.IngresoCobros.NoExisteIngresoCobroException;
import com.excepciones.IngresoCobros.ObteniendoIngresoCobroException;
import com.excepciones.Monedas.ExisteMonedaException;
import com.excepciones.Monedas.ExisteNacional;
import com.excepciones.Monedas.InsertandoMonedaException;
import com.excepciones.Monedas.ModificandoMonedaException;
import com.excepciones.Monedas.NoExisteMonedaException;
import com.excepciones.Monedas.ObteniendoMonedaException;
import com.excepciones.NotaCredito.ExisteNotaCreditoException;
import com.excepciones.Periodo.ExistePeriodoException;
import com.excepciones.Periodo.InsertandoPeriodoException;
import com.excepciones.Periodo.ModificandoPeriodoException;
import com.excepciones.Periodo.NoExistePeriodoException;
import com.excepciones.Periodo.ObteniendoPeriodosException;
import com.excepciones.Procesos.EliminandoProcesoException;
import com.excepciones.Procesos.ExisteProcesoException;
import com.excepciones.Procesos.IngresandoProcesoException;
import com.excepciones.Procesos.ModificandoProcesoException;
import com.excepciones.Procesos.NoExisteProcesoException;
import com.excepciones.Procesos.ObteniendoProcesosException;
import com.excepciones.Recibo.ExisteReciboException;
import com.excepciones.Rubros.ExisteRubroException;
import com.excepciones.Rubros.InsertandoRubroException;
import com.excepciones.Rubros.ModificandoRubroException;
import com.excepciones.Rubros.NoExisteRubroException;
import com.excepciones.Rubros.ObteniendoRubrosException;
import com.excepciones.SaldoCuentas.EliminandoSaldoCuetaException;
import com.excepciones.SaldoCuentas.ExisteNroTransferencia;
import com.excepciones.SaldoCuentas.InsertandoSaldoCuentaException;
import com.excepciones.Saldos.EliminandoSaldoException;
import com.excepciones.Saldos.ExisteSaldoException;
import com.excepciones.Saldos.IngresandoSaldoException;
import com.excepciones.Saldos.ModificandoSaldoException;
import com.excepciones.Saldos.ObteniendoSaldosException;
import com.excepciones.TipoRubro.ExisteTipoRubroException;
import com.excepciones.TipoRubro.InsertandoTipoRubroException;
import com.excepciones.TipoRubro.ModificandoTipoRubroException;
import com.excepciones.TipoRubro.NoExisteTipoRubroException;
import com.excepciones.TipoRubro.ObteniendoTipoRubroException;
import com.excepciones.Titulares.ObteniendoTitularesException;
import com.excepciones.Usuarios.ExisteUsuarioException;
import com.excepciones.Usuarios.InsertandoUsuarioException;
import com.excepciones.Usuarios.ObteniendoUsuariosException;
import com.excepciones.Usuarios.ObteniendoUsuariosxEmpExeption;
import com.excepciones.grupos.ObteniendoGruposException;
import com.logica.Conciliacion.Conciliacion;
import com.logica.Conciliacion.ConciliacionDetalle;
import com.logica.Depositos.Deposito;
import com.logica.Depositos.DepositoDetalle;
import com.logica.DocLog.DocLog;
import com.logica.Docum.ConvertirDocumento;
import com.logica.Docum.DatosDocum;
import com.logica.Docum.DocumDetalle;
import com.logica.Docum.DocumSaldo;
import com.logica.Docum.TitularInfo;
import com.logica.IngresoCobro.IngresoCobro;
import com.logica.Periodo.Periodo;
import com.valueObject.*;
import com.valueObject.Cheque.ChequeVO;
import com.valueObject.Conciliaciones.ConciliacionDetalleVO;
import com.valueObject.Conciliaciones.ConciliacionVO;
import com.valueObject.Cotizacion.CotizacionVO;
import com.valueObject.Cuenta.CuentaVO;
import com.valueObject.Deposito.DepositoDetalleVO;
import com.valueObject.Deposito.DepositoVO;

import com.valueObject.Gasto.GastoVO;
import com.valueObject.IngresoCobro.IngresoCobroDetalleVO;
import com.valueObject.IngresoCobro.IngresoCobroVO;
import com.valueObject.Numeradores.NumeradoresVO;
import com.valueObject.Periodo.PeriodoVO;
import com.valueObject.TipoRubro.TipoRubroVO;
import com.valueObject.empresa.EmpresaUsuVO;
import com.valueObject.empresa.EmpresaVO;
import com.valueObject.proceso.ProcesoVO;
import com.valueObject.proceso.SaldoProcesoVO;
import com.persistencia.*;

public class FachadaDD {

	private static final Object lock = new Object();
	private static volatile FachadaDD INSTANCE = null;
	Pool pool;
	DocLog logDocum;
	
	/*Esto es para abstract factory*/
	private IDAOUsuarios usuarios;
	private IDAOGrupos grupos;
	private IDAOImpuestos impuestos;
	private IDAOMonedas monedas;
	private IDAOEmpresas empresas;
	private IDAORubros rubros;
	private IDAODocumentos documentos;
	private IDAOCodigosGeneralizados codigosGeneralizados;
	private IDAOCotizaciones cotizaciones;
	private IDAOTipoRubro tipoRubros;
	private IDAOCuentas cuentas;
	private IDAOProcesos procesos;
	private IDAONumeradores numeradores;
	private AbstractFactoryBuilder fabrica;
	private IAbstractFactory fabricaConcreta;
	private IDAOGastos gastos;
	private IDAOSaldos saldos;
	private IDAODocLog logsDocumentos;
	private IDAOSaldosProc saldosProceso;
	private IDAOTitulares titulares;
	private IDAOPeriodo periodo;
	private IDAOCheques cheques;
	private IDAODepositos depositos;
	private IDAOSaldosCuentas saldosCuentas;
	private IDAOConciliaciones conciliaciones;
	private IDAOEgresoCobro egresoCobro;
	private IDAOFacturas facturas;
	private IDAOIngresoCobro ingresoCobro;
	private IDAORecibos recibos;
	private IDAONotaCredito notaCredito;
	
	
    private FachadaDD() throws InstantiationException, IllegalAccessException, ClassNotFoundException, FileNotFoundException, IOException
    {
    	pool = Pool.getInstance();
    	
        fabrica = AbstractFactoryBuilder.getInstancia();
		fabricaConcreta = fabrica.getAbstractFactory();
		
        this.usuarios =  fabricaConcreta.crearDAOUsuarios();
        this.grupos = fabricaConcreta.crearDAOGrupos();
        this.impuestos = fabricaConcreta.crearDAOImpuestos();
        this.monedas = fabricaConcreta.crearDAOMonedas();
        this.empresas = fabricaConcreta.crearDAOEmpresas();
        this.rubros = fabricaConcreta.crearDAORubros();
        this.documentos = fabricaConcreta.crearDAODocumentos();
        this.codigosGeneralizados = fabricaConcreta.crearDAOCodigosGeneralizados();
        this.cotizaciones = fabricaConcreta.crearDAOCotizaciones();
        this.tipoRubros = fabricaConcreta.crearDAOTipoRubro();
        this.cuentas = fabricaConcreta.crearDAOCuentas();
        this.procesos = fabricaConcreta.crearDAOProcesos();
        this.numeradores = fabricaConcreta.crearDAONumeradores();
        this.gastos = fabricaConcreta.crearDAOGastos();
        this.saldos = fabricaConcreta.crearDAOSaldos();
        this.logsDocumentos = fabricaConcreta.crearDAODocLog();
        this.saldosProceso = fabricaConcreta.crearDAOSaldosProceso();
        this.titulares = fabricaConcreta.crearDAOTitulares();
        this.periodo = fabricaConcreta.crearDAOPeriodo();
        this.cheques = fabricaConcreta.crearDAOCheques();
        this.depositos = fabricaConcreta.crearDAODeposito();
        this.saldosCuentas = fabricaConcreta.crearDAOSaldosCuenta();
        this.conciliaciones = fabricaConcreta.crearDAOConciliaciones();
        this.egresoCobro = fabricaConcreta.crearDAOEgresoCobro();
        this.facturas = fabricaConcreta.crearDAOFactura();
        this.ingresoCobro = fabricaConcreta.crearDAOIngresoCobro();
        this.recibos = fabricaConcreta.crearDAORecibos();
        this.notaCredito = fabricaConcreta.crearDAONotaCredito();
    }
    
    public static FachadaDD getInstance() throws InicializandoException {
         
         	
    	if(INSTANCE == null)
        {
            synchronized (lock)
            {   
                try {
					INSTANCE = new FachadaDD();
				} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | IOException e) {
					
					throw new InicializandoException();
				}
            }
        }
        
        return INSTANCE;
    }
    
/////////////////////////////////LISTADO DE USUARIOS/////////////////////////////////
    @SuppressWarnings({ "null", "resource" })
    /**
	 * Obtiene Array de todos lo usuarios existentes 
	 */
	public ArrayList<UsuarioVO> getUsuarios(String codEmp) throws ConexionException, ClassNotFoundException, ObteniendoUsuariosException, ErrorInesperadoException, ObteniendoGruposException {
    	
    	Connection con = null;
    	ArrayList<Usuario> lstUsuarios = new ArrayList<Usuario>();
    	ArrayList<UsuarioVO> lstUsuariosVO = new ArrayList<UsuarioVO>();
    	
    	try 
    	{
			con = this.pool.obtenerConeccion();
	    	
			
			lstUsuarios = this.usuarios.getUsuarios(codEmp, con);
			UsuarioVO aux;
			
			for (Usuario usuario: lstUsuarios)
			{
				aux = new UsuarioVO();
				aux.setUsuario(usuario.getUsuario());
				aux.setNombre(usuario.getNombre());
				aux.setPass(usuario.getPass());
				aux.setActivo(usuario.isActivo());
				aux.setFechaMod(usuario.getFechaMod());
				aux.setUsuarioMod(usuario.getUsuarioMod());
				aux.setOperacion(usuario.getOperacion());
				aux.setMail(usuario.getMail());
				
				GrupoVO auxGrupo;
				for (Grupo grupo: usuario.getLstGrupos())
				{
					auxGrupo = new GrupoVO();
					auxGrupo.setCodGrupo(grupo.getCodGrupo());
					auxGrupo.setNomGrupo(grupo.getNomGrupo());
					aux.getLstGrupos().add(auxGrupo);
				}
				
				lstUsuariosVO.add(aux);
			}
	    	
	    	return lstUsuariosVO;
	    	
	    	
		} 
    	catch (Exception e) 
    	{
    		throw new ErrorInesperadoException();
		}
    	finally 
    	{
    		this.pool.liberarConeccion(con);
		}
    	
		
    	
    }
    

/////////////////////////////////FIN LISTADO DE USUARIOS/////////////////////////////////
    
/////////////////////////////////NUEVO/////////////////////////////////
    @SuppressWarnings("resource")
    /**
	 * Inserta un usuario en la base
	 */
	public void insertarUsuario(UsuarioVO usuarioVO, String empresa) throws InsertandoUsuarioException, ConexionException, ExisteUsuarioException, ErrorInesperadoException
    {
    	Connection con = null;
    	Usuario user = new Usuario(usuarioVO);
    	boolean existe = false;
    	
    	try 
    	{
    		con = this.pool.obtenerConeccion();
			con.setAutoCommit(false);
			con = this.pool.obtenerConeccion();
			
    		if(!this.usuarios.memberUsuario(user.getUsuario(), con))
        	{
        		this.usuarios.insertarUsuario(user, empresa, con);
        	}
        	else
        	{
        		existe = true;
        	}
		}
    	catch(Exception InsertandoUsuarioException){
    		try {
				con.rollback();
				
			} catch (SQLException e) {
				
				throw new InsertandoUsuarioException();
			}
    		
    		throw new InsertandoUsuarioException();
    	}
    	finally 
    	{
    		this.pool.liberarConeccion(con);
		}
    	
    	if(existe)
    		throw new ExisteUsuarioException();
    }
////////////////////////////////FIN NUEVO/////////////////////////////////   
    
/////////////////////////////////NUEVO/////////////////////////////////
    @SuppressWarnings("resource")
    /**
	 * Modifica los datos de un usuario dado el VO con los nuevos datos
	 */
	public void modificarUsuario(UsuarioVO usuarioVO, String empresa) throws InsertandoUsuarioException, ConexionException, ExisteUsuarioException, ErrorInesperadoException
	{
		Connection con = null;
		Usuario user = new Usuario(usuarioVO);
	
		try 
		{
			
			con = this.pool.obtenerConeccion();
			//con.setAutoCommit(false);
			this.usuarios.modificarUsuario(user, empresa, con);
			//con.commit();
		} 
		catch (Exception e) 
		{
			throw new ErrorInesperadoException();
		}
		finally 
		{
			this.pool.liberarConeccion(con);
		}
	}
    
    /**
	 * Obtiene los grupos que no tiene asignados un usuario
	 */
    public ArrayList<GrupoVO> getGruposNoUsuario(String nombreUsuario) throws ErrorInesperadoException, ConexionException
    {
    	Connection con = null;
    	ArrayList<GrupoVO> lstGrupos = new ArrayList<GrupoVO>();
    	try 
    	{
			con = this.pool.obtenerConeccion();
			lstGrupos = this.usuarios.getGruposNoUsuario(nombreUsuario, con);
			
			
		} 
    	catch (Exception e) 
    	{
    		throw new ErrorInesperadoException();
		}
    	finally
    	{
    		this.pool.liberarConeccion(con);
    	}
    	return lstGrupos;
    	
    }
    
    /**
	 * Nos retorna la empresa para el usuario
	 */
    public EmpLoginVO getEmpresaUsuario(String usuario) throws ConexionException, ObteniendoUsuariosxEmpExeption
    {
    	Connection con = null;
    	EmpLoginVO empLogin = null;
    	try 
    	{
			con = this.pool.obtenerConeccion();
			empLogin = this.usuarios.getEmpresaUsuario(usuario, con);
			
		} 
    	catch (ObteniendoUsuariosxEmpExeption | ConexionException e) 
    	{
    		throw e;
		}
    	finally
    	{
    		this.pool.liberarConeccion(con);
    	}
    	return empLogin;
    	
    }
    
    /**
	 * Obtiene todos los impuestos existentes
	 */
    @SuppressWarnings("unchecked")
	public ArrayList<ImpuestoVO> getImpuestos(String codEmp) throws ObteniendoImpuestosException, ConexionException
    {
    	
    	Connection con = null;
    	
    	ArrayList<Impuesto> lstImpuestos;
    	ArrayList<ImpuestoVO> lstImpuestoVO = new ArrayList<ImpuestoVO>();
    	    	
    	try
    	{
    		con = this.pool.obtenerConeccion();
    		
    		lstImpuestos = this.impuestos.getImpuestos(codEmp, con);
    		
    		
    		ImpuestoVO aux;
    		for (Impuesto impuesto : lstImpuestos) 
			{
    			aux = new ImpuestoVO();
    			
    			aux.setcodImpuesto(impuesto.getCod_imp());
    			aux.setDescripcion(impuesto.getDescripcion());
    			aux.setPorcentaje(impuesto.getPorcentaje());
    			aux.setActivo(impuesto.isActivo());
    			aux.setFechaMod(impuesto.getFechaMod());
    			aux.setOperacion(impuesto.getOperacion());
    			aux.setUsuarioMod(impuesto.getUsuarioMod());
    			
    			lstImpuestoVO.add(aux);
			}
	
    	}
    	catch(ObteniendoImpuestosException e){
    		throw e;
    		
    	} 
    	catch (ConexionException e) {
			
    		throw e;
    	} 
    	finally
    	{
    		this.pool.liberarConeccion(con);
    	}
    	    
    	
    	return lstImpuestoVO;
    }
    
    /**
   	 * Obtiene todos los impuestos existentes ACTIVOS
   	 */
       @SuppressWarnings("unchecked")
   	public ArrayList<ImpuestoVO> getImpuestosActivos(String codEmp) throws ObteniendoImpuestosException, ConexionException
    {
       	
       	Connection con = null;
       	
       	ArrayList<Impuesto> lstImpuestos;
       	ArrayList<ImpuestoVO> lstImpuestoVO = new ArrayList<ImpuestoVO>();
       	    	
       	try
       	{
       		con = this.pool.obtenerConeccion();
       		
       		lstImpuestos = this.impuestos.getImpuestosActivos(codEmp, con);
       		
       		
       		ImpuestoVO aux;
       		for (Impuesto impuesto : lstImpuestos) 
   			{
       			aux = new ImpuestoVO();
       			
       			aux.setcodImpuesto(impuesto.getCod_imp());
       			aux.setDescripcion(impuesto.getDescripcion());
       			aux.setPorcentaje(impuesto.getPorcentaje());
       			aux.setActivo(impuesto.isActivo());
       			aux.setFechaMod(impuesto.getFechaMod());
       			aux.setOperacion(impuesto.getOperacion());
       			aux.setUsuarioMod(impuesto.getUsuarioMod());
       			
       			lstImpuestoVO.add(aux);
   			}
   	
       	}
       	catch(ObteniendoImpuestosException e){
       		throw e;
       		
       	} 
       	catch (ConexionException e) {
   			
       		throw e;
       	} 
       	finally
       	{
       		this.pool.liberarConeccion(con);
       	}
       	    
       	
       	return lstImpuestoVO;
       }
    
    /**
	 * Inserta un impuesto en la base
	 * Valida que no exista un impuesto con el mismo código
	 */
    public void insertarImpuesto(ImpuestoVO impuestoVO, String codEmp) throws InsertandoImpuestoException, ConexionException, ExisteImpuestoException 
    {
    	
    	Connection con = null;
    	boolean existe = false;
    	
    	try 
    	{
			con = this.pool.obtenerConeccion();
			con.setAutoCommit(false);
			
	    	Impuesto impuesto = new Impuesto(impuestoVO); 
	    	
	    	if(!this.impuestos.memberImpuesto(impuesto.getCod_imp(), codEmp, con)) 	{
	    		
	    		this.impuestos.insertarImpuesto(impuesto, codEmp, con);
	    		con.commit();
	    	}
	    	else{
	    		existe = true;
	    	}
	    		
    	
    	}
    	catch(Exception InsertandoImpuestoException)  	{
    		try {
				con.rollback();
				
			} catch (SQLException e) {
				
				throw new InsertandoImpuestoException();
			}
    		
    		throw new InsertandoImpuestoException();
    	}
    	finally
    	{
    		pool.liberarConeccion(con);
    	}
    	if (existe){
    		throw new ExisteImpuestoException();
    	}
    }

    /**
	 * Actualiza los datos de un impuesto dado su código
	 * valida que exista el código 
	 */
    public void actualizarImpuesto(ImpuestoVO impuestoVO, String codEmp) throws ConexionException, NoExisteImpuestoException, ModificandoImpuestoException, ExisteImpuestoException  
	{
	    	
	    	Connection con = null;
	    	
	    	try 
	    	{
				con = this.pool.obtenerConeccion();
				con.setAutoCommit(false);
				
				Impuesto impuesto = new Impuesto(impuestoVO);
		    	
		    	if(this.impuestos.memberImpuesto(impuesto.getCod_imp(), codEmp, con))
		    	{
		    		impuestos.actualizarImpuesto(impuesto, codEmp,  con);
		    		con.commit();
		    	}
		    	
		    	else
		    		throw new NoExisteImpuestoException();
	    	
	    	}catch(ExisteImpuestoException| ConexionException | SQLException | ModificandoImpuestoException e)
	    	{
	    		try {
					con.rollback();
					
				} 
	    		catch (SQLException e1) {
					
					throw new ConexionException();
				}
	    		throw new ModificandoImpuestoException();
	    	}
	    	finally
	    	{
	    		pool.liberarConeccion(con);
	    	}
	    }
    
    /**
	 * Obtiene todos las monedas existentes
	 */
    @SuppressWarnings("unchecked")
	public ArrayList<MonedaVO> getMonedas(String codEmp) throws ObteniendoMonedaException, ConexionException
    {
    	
    	Connection con = null;
    	
    	ArrayList<Moneda> lstMonedas;
    	ArrayList<MonedaVO> lstMonedasVO = new ArrayList<MonedaVO>();
    	    	
    	try
    	{
    		con = this.pool.obtenerConeccion();
    		
    		lstMonedas = this.monedas.getMonedas(codEmp, con);
    		
    		
    		MonedaVO aux;
    		for (Moneda moneda : lstMonedas) 
			{
    			aux = new MonedaVO();
    			
    			aux.setCodMoneda(moneda.getCod_moneda());
    			aux.setDescripcion(moneda.getDescripcion());
    			aux.setSimbolo(moneda.getSimbolo());
    			aux.setAceptaCotizacion(moneda.isAcepta_cotizacion());
    			aux.setActivo(moneda.isActivo());
    			aux.setFechaMod(moneda.getFechaMod());
    			aux.setOperacion(moneda.getOperacion());
    			aux.setUsuarioMod(moneda.getUsuarioMod());
    			aux.setNacional(moneda.isNacional());
    			
    			lstMonedasVO.add(aux);
			}
	
    	}
    	catch(ObteniendoMonedaException e){
    		throw e;
    		
    	} 
    	catch (ConexionException e) {
			
    		throw e;
    	} 
    	finally
    	{
    		this.pool.liberarConeccion(con);
    	}
    	    
    	
    	return lstMonedasVO;
    }
    
    
    /**
	 * Obtiene todos las monedas existentes
	 */
    @SuppressWarnings("unchecked")
	public ArrayList<MonedaVO> getMonedasActivas(String codEmp) throws ObteniendoMonedaException, ConexionException
    {
    	
    	Connection con = null;
    	
    	ArrayList<Moneda> lstMonedas;
    	ArrayList<MonedaVO> lstMonedasVO = new ArrayList<MonedaVO>();
    	    	
    	try
    	{
    		con = this.pool.obtenerConeccion();
    		
    		lstMonedas = this.monedas.getMonedasActivas(codEmp, con);
    		
    		
    		MonedaVO aux;
    		for (Moneda moneda : lstMonedas) 
			{
    			aux = new MonedaVO();
    			
    			aux.setCodMoneda(moneda.getCod_moneda());
    			aux.setDescripcion(moneda.getDescripcion());
    			aux.setSimbolo(moneda.getSimbolo());
    			aux.setAceptaCotizacion(moneda.isAcepta_cotizacion());
    			aux.setActivo(moneda.isActivo());
    			aux.setFechaMod(moneda.getFechaMod());
    			aux.setOperacion(moneda.getOperacion());
    			aux.setUsuarioMod(moneda.getUsuarioMod());
    			aux.setNacional(moneda.isNacional());
    			
    			lstMonedasVO.add(aux);
			}
	
    	}
    	catch(ObteniendoMonedaException e){
    		throw e;
    		
    	} 
    	catch (ConexionException e) {
			
    		throw e;
    	} 
    	finally
    	{
    		this.pool.liberarConeccion(con);
    	}
    	    
    	
    	return lstMonedasVO;
    }
    
    
    /**
	 * Inserta un impuesto en la base
	 * Valida que no exista un impuesto con el mismo código
     * @throws ExisteNacional 
	 */
    public void insertarMoneda(MonedaVO monedaVO, String codEmp) throws InsertandoMonedaException, ConexionException, ExisteMonedaException, ExisteNacional 
    {
    	
    	Connection con = null;
    	boolean existe = false, existeNacional = false;
    	
    	try 
    	{
			con = this.pool.obtenerConeccion();
			con.setAutoCommit(false);
			
	    	Moneda moneda = new Moneda(monedaVO); 
	    	
	    	if(!this.monedas.memberMoneda(moneda.getCod_moneda(), codEmp, con)) 	{
	    	
	    		if(moneda.isNacional()){
	    			if(!this.monedas.existeNacional(moneda.getCod_moneda(), codEmp, con)){
	    				this.monedas.insertarMoneda(moneda,codEmp, con);
	    	    		con.commit();
	    			}
	    			else{
	    				existeNacional = true;
	    			}
	    		}
	    		else{
	    			this.monedas.insertarMoneda(moneda,codEmp, con);
	    			con.commit();
	    		}
	    		
	    	}
	    	else{
	    		existe = true;
	    	}
	    		
    	
    	}
    	catch(Exception InsertandoMonedaException)  	{
    		try {
				con.rollback();
				
			} catch (SQLException e) {
				
				throw new InsertandoMonedaException();
			}
    		
    		throw new InsertandoMonedaException();
    	}
    	finally
    	{
    		pool.liberarConeccion(con);
    	}
    	if (existe){
    		throw new ExisteMonedaException();
    	}
    	if(existeNacional){
    		throw new ExisteNacional();
    	}
    }

    /**
	 * Actualiza los datos de una moneda dado su código
	 * valida que exista el código 
     * @throws ExisteNacional 
	 */
    public void actualizarMoneda(MonedaVO monedaVO, String codEmp) throws ConexionException, NoExisteMonedaException, ModificandoMonedaException, ExisteMonedaException, ExisteNacional  
	{
	    	
	    	Connection con = null;
	    	boolean existeNacional = false;
	    	
	    	try 
	    	{
				con = this.pool.obtenerConeccion();
				con.setAutoCommit(false);
				
				Moneda moneda = new Moneda(monedaVO);
		    	
		    	if(this.monedas.memberMoneda(moneda.getCod_moneda(), codEmp, con))
		    	{
		    		if(moneda.isNacional()){
		    			if(!this.monedas.existeNacional(moneda.getCod_moneda(), codEmp, con)){
		    				monedas.actualizarMoneda(moneda, codEmp, con);
				    		con.commit();
		    			}
		    			else{
		    				existeNacional = true;
		    			}
		    		}
		    		else{
		    			monedas.actualizarMoneda(moneda, codEmp, con);
			    		con.commit();
		    		}
		    			
		    	}
		    	
		    	else
		    		throw new NoExisteMonedaException();
	    	
	    	}catch(NoExisteMonedaException| ConexionException | SQLException | ModificandoMonedaException e)
	    	{
	    		try {
					con.rollback();
					
				} 
	    		catch (SQLException e1) {
					
					throw new ConexionException();
				}
	    		throw new ModificandoMonedaException();
	    	}
	    	finally
	    	{
	    		pool.liberarConeccion(con);
	    	}
	    	if(existeNacional){
	    		throw new ExisteNacional();
	    	}
	    }
    
    ///////////////////////////////////////////////////////////////////EMPRESAS////////////////////////////////////////////////////////////////////////////////////////////
    /**
	 * Obtiene todos las empresas existentes
	 */
    @SuppressWarnings("unchecked")
	public ArrayList<EmpresaVO> getEmpresas() throws ObteniendoEmpresasException, ConexionException
    {
    	
    	Connection con = null;
    	
    	ArrayList<Empresa> lstEmpresas;
    	ArrayList<EmpresaVO> lstEmpresasVO = new ArrayList<EmpresaVO>();
    	    	
    	try
    	{
    		con = this.pool.obtenerConeccion();
    		
    		lstEmpresas = this.empresas.getEmpreas(con);
    		
    		
    		EmpresaVO aux;
    		for (Empresa empresa : lstEmpresas) 
			{
    			aux = new EmpresaVO();
    			
    			aux.setCodEmp(empresa.getCod_emp());
    			aux.setNomEmp(empresa.getNom_emp());
    			aux.setActivo(empresa.isActivo());
    			aux.setFechaMod(empresa.getFechaMod());
    			aux.setOperacion(empresa.getOperacion());
    			aux.setUsuarioMod(empresa.getUsuarioMod());
    			
    			lstEmpresasVO.add(aux);
			}
	
    	}
    	catch(ObteniendoEmpresasException e){
    		throw e;
    		
    	} 
    	catch (ConexionException e) {
			
    		throw e;
    	} 
    	finally
    	{
    		this.pool.liberarConeccion(con);
    	}
    	    
    	
    	return lstEmpresasVO;
    }
    
    /**
	 * Inserta una empresa en la base
	 * Valida que no exista una empresa con el mismo código
     * @throws ExisteEmpresaException 
	 */
    public void insertarEmprea(EmpresaUsuVO empresaUsuVO) throws InsertandoEmpresaException, ConexionException, ExisteEmpresaException, ExisteUsuarioException 
    {
    	
    	Connection con = null;
    	boolean existe = false;
    	boolean existeUsu = false;
    	
    	try 
    	{
			con = this.pool.obtenerConeccion();
			con.setAutoCommit(false);
			
	    	Empresa empresa = new Empresa(empresaUsuVO); 
	    	
	    	if(!this.empresas.memberEmpresa(empresa.getCod_emp(), con)) 	{
	    		
	    		this.empresas.insertarEmpresa(empresa, con);
	    		
	    		//FachadaDD.getInstance().insertarUsuario(usuarioVO, empresa);
	    		
	    		Usuario usu = new Usuario();
	    		usu.setUsuario(empresaUsuVO.getUsuario());
	    		usu.setPass(empresaUsuVO.getPass());
	    		usu.setNombre(empresaUsuVO.getUsuario() + "-" + empresaUsuVO.getNomEmp());
	    		usu.setActivo(true);
	    		usu.setMail("");
	    		
	    		usu.setUsuarioMod(empresaUsuVO.getUsuarioMod());
	    		usu.setOperacion(empresaUsuVO.getOperacion());
	    		
	    		/*Obtenemos el grupo admin para otorgale los permisos al usuario
	    		 * Param es la empresa para tomar la parametrizacion de donde creamos
	    		 * los usuarios admin, Param esta creada solamente con este fin*/
	    		Grupo g = this.grupos.getGrupo("Param",con, "Adm");
	    		ArrayList<Grupo> grupos = new ArrayList<Grupo>();
	    		grupos.add(g);
	    		    		
	    		usu.setLstGrupos(grupos);
	    		
	    		if(!this.usuarios.memberUsuario(usu.getUsuario(), con))
	        	{
	        		this.usuarios.insertarUsuario(usu, empresa.getCod_emp(), con);
	        	}
	        	else
	        	{
	        		existeUsu = true;
	        	}
	    		    		
	    	}
	    	else{
	    		existe = true;
	    	}
	    	
    		if(!existe && !existeUsu) /*Si no existe la empresa y el usuario commiteamos*/
    			con.commit();
    	
    	}
    	catch(Exception InsertandoEmpresaException)  	{
    		try {
				con.rollback();
				
			} catch (SQLException e) {
				
				throw new InsertandoEmpresaException();
			}
    		
    		throw new InsertandoEmpresaException();
    	}
    	finally
    	{
    		pool.liberarConeccion(con);
    	}
    	if (existe){
    		throw new ExisteEmpresaException();
    	}
    	if (existeUsu){
    		throw new ExisteUsuarioException();
    	}
    }

    /**
	 * Actualiza los datos de una empresa dado su código
	 * valida que exista el código 
	 */
    public void actualizarEmpresa(EmpresaVO empresaVO) throws ConexionException, NoExisteEmpresaException, ModificandoEmpresaException, ExisteEmpresaException  
	{
	    	
	    	Connection con = null;
	    	
	    	try 
	    	{
				con = this.pool.obtenerConeccion();
				con.setAutoCommit(false);
				
				Empresa empresa= new Empresa(empresaVO);
		    	
		    	if(this.empresas.memberEmpresa(empresa.getCod_emp(), con))
		    	{
		    		this.empresas.actualizarEmpresa(empresa, con);
		    		con.commit();
		    	}
		    	
		    	else
		    		throw new NoExisteEmpresaException();
	    	
	    	}
	    	catch(NoExisteEmpresaException| ConexionException | SQLException | ModificandoEmpresaException e){
	    		try {
					con.rollback();
					
				} 
	    		catch (SQLException e1) {
					
					throw new ConexionException();
				}
	    		throw new ModificandoEmpresaException();
	    	}
	    	finally
	    	{
	    		pool.liberarConeccion(con);
	    	}
	    }
    
    ///////////////////////////////////////////////////////////////////RUBROS////////////////////////////////////////////////////////////////////////////////////////////
    /**
	 * Obtiene todos los rubros existentes
	 */
    @SuppressWarnings("unchecked")
	public ArrayList<RubroVO> getRubros(String cod_emp) throws ObteniendoRubrosException, ConexionException
    {
    	
    	Connection con = null;
    	
    	ArrayList<Rubro> lstRubros;
    	ArrayList<RubroVO> lstRubrosVO = new ArrayList<RubroVO>();
    	    	
    	try
    	{
    		con = this.pool.obtenerConeccion();
    		
    		lstRubros = this.rubros.getRubros(cod_emp, con);
    		
    		
    		RubroVO aux;
    		for (Rubro rubro : lstRubros) 
			{
    			aux = new RubroVO();
    			
    			aux.setcodRubro(rubro.getCod_rubro());
    			aux.setDescripcion(rubro.getDescripcion());
    			aux.setActivo(rubro.isActivo());
    			aux.setFechaMod(rubro.getFechaMod());
    			aux.setOperacion(rubro.getOperacion());
    			aux.setUsuarioMod(rubro.getUsuarioMod());
    			aux.setCodigoImpuesto(rubro.getImpuesto().getCod_imp());
    			aux.setDescripcionImpuesto(rubro.getImpuesto().getDescripcion());
    			aux.setPorcentajeImpuesto(rubro.getImpuesto().getPorcentaje());
    			aux.setDescripcionTipoRubro(rubro.getTipoRubro().getDescripcion());
    			aux.setCodTipoRubro(rubro.getTipoRubro().getCod_tipoRubro());
    			aux.setFacturable(rubro.isFacturable());
    			
    			lstRubrosVO.add(aux);
			}
	
    	}
    	catch(ObteniendoRubrosException e){
    		throw e;
    		
    	} 
    	catch (ConexionException e) {
			
    		throw e;
    	} 
    	finally
    	{
    		this.pool.liberarConeccion(con);
    	}
    	    
    	
    	return lstRubrosVO;
    }
    
    /**
	 * Obtiene todos los rubros existentes
	 */
    @SuppressWarnings("unchecked")
	public ArrayList<RubroVO> getRubrosActivos(String cod_emp) throws ObteniendoRubrosException, ConexionException
    {
    	
    	Connection con = null;
    	
    	ArrayList<Rubro> lstRubros;
    	ArrayList<RubroVO> lstRubrosVO = new ArrayList<RubroVO>();
    	    	
    	try
    	{
    		con = this.pool.obtenerConeccion();
    		
    		lstRubros = this.rubros.getRubrosActivos(cod_emp, con);
    		
    		
    		RubroVO aux;
    		for (Rubro rubro : lstRubros) 
			{
    			aux = new RubroVO();
    			
    			aux.setcodRubro(rubro.getCod_rubro());
    			aux.setDescripcion(rubro.getDescripcion());
    			aux.setActivo(rubro.isActivo());
    			aux.setFechaMod(rubro.getFechaMod());
    			aux.setOperacion(rubro.getOperacion());
    			aux.setUsuarioMod(rubro.getUsuarioMod());
    			aux.setCodigoImpuesto(rubro.getImpuesto().getCod_imp());
    			aux.setDescripcionImpuesto(rubro.getImpuesto().getDescripcion());
    			aux.setPorcentajeImpuesto(rubro.getImpuesto().getPorcentaje());
    			aux.setDescripcionTipoRubro(rubro.getTipoRubro().getDescripcion());
    			aux.setCodTipoRubro(rubro.getTipoRubro().getCod_tipoRubro());
    			aux.setFacturable(rubro.isFacturable());
    			
    			lstRubrosVO.add(aux);
			}
	
    	}
    	catch(ObteniendoRubrosException e){
    		throw e;
    		
    	} 
    	catch (ConexionException e) {
			
    		throw e;
    	} 
    	finally
    	{
    		this.pool.liberarConeccion(con);
    	}
    	    
    	
    	return lstRubrosVO;
    }
    
    
    /**
   	 * Obtiene todos los rubros existentes
   	 */
       @SuppressWarnings("unchecked")
   	public ArrayList<RubroCuentaVO> getRubrosCuentasActivos(String cod_emp) throws ObteniendoRubrosException, ConexionException
       {
       	
       	Connection con = null;
       	
       	ArrayList<RubroCuenta> lstRubros;
       	ArrayList<RubroCuentaVO> lstRubrosVO = new ArrayList<RubroCuentaVO>();
       	    	
       	try
       	{
       		con = this.pool.obtenerConeccion();
       		
       		lstRubros = this.rubros.getRubrosCuentasActivos(cod_emp, con);
       		
       		for (RubroCuenta rubro : lstRubros) 
   			{
       			RubroCuentaVO aux = new RubroCuentaVO(rubro);
       			
       			lstRubrosVO.add(aux);
   			}
   	
       	}
       	catch(ObteniendoRubrosException e){
       		throw e;
       		
       	} 
       	catch (ConexionException e) {
   			
       		throw e;
       	} 
       	finally
       	{
       		this.pool.liberarConeccion(con);
       	}
       	    
       	
       	return lstRubrosVO;
       }
       
       /**
      	 * Obtiene todos los rubros existentes
      	 */
          @SuppressWarnings("unchecked")
      	public ArrayList<RubroCuentaVO> getRubrosCuentasActivosFacturable(String cod_emp) throws ObteniendoRubrosException, ConexionException
          {
          	
          	Connection con = null;
          	
          	ArrayList<RubroCuenta> lstRubros;
          	ArrayList<RubroCuentaVO> lstRubrosVO = new ArrayList<RubroCuentaVO>();
          	    	
          	try
          	{
          		con = this.pool.obtenerConeccion();
          		
          		lstRubros = this.rubros.getRubrosCuentasActivosFacturable(cod_emp, con);
          		
          		for (RubroCuenta rubro : lstRubros) 
      			{
          			RubroCuentaVO aux = new RubroCuentaVO(rubro);
          			
          			lstRubrosVO.add(aux);
      			}
      	
          	}
          	catch(ObteniendoRubrosException e){
          		throw e;
          		
          	} 
          	catch (ConexionException e) {
      			
          		throw e;
          	} 
          	finally
          	{
          		this.pool.liberarConeccion(con);
          	}
          	    
          	
          	return lstRubrosVO;
          }
       
    /**
	 * Inserta un nuevo rubro en la base
	 * Valida que no exista un rubro con el mismo código
     * @throws ExisteEmpresaException 
	 */
    public void insertarRubro(RubroVO rubroVO, String cod_emp) throws InsertandoRubroException, ConexionException, ExisteRubroException 
    {
    	
    	Connection con = null;
    	boolean existe = false;
    	
    	try 
    	{
			con = this.pool.obtenerConeccion();
			con.setAutoCommit(false);
			
	    	Rubro rubro = new Rubro(rubroVO); 
	    	Impuesto impuesto = new Impuesto(rubro.getImpuesto().getCod_imp());
	    	rubro.setImpuesto(impuesto);
	    	
	    	if(!this.rubros.memberRubro(rubro.getCod_rubro(), cod_emp, con)) 	{
	    		
	    		this.rubros.insertarRubro(rubro, cod_emp, con);
	    		con.commit();
	    	}
	    	else{
	    		existe = true;
	    	}
	    		
    	
    	}
    	catch(Exception InsertandoRubroException)  	{
    		try {
				con.rollback();
				
			} catch (SQLException e) {
				
				throw new InsertandoRubroException();
			}
    		
    		throw new InsertandoRubroException();
    	}
    	finally
    	{
    		pool.liberarConeccion(con);
    	}
    	if (existe){
    		throw new ExisteRubroException();
    	}
    }

    /**
	 * Actualiza los datos de un rubro dado su código
	 * valida que exista el código 
	 */
    public void actualizarRubro(RubroVO rubroVO, String cod_emp) throws ConexionException, NoExisteRubroException, ModificandoRubroException, ExisteRubroException  
	{
	    	
	    	Connection con = null;
	    	
	    	try 
	    	{
				con = this.pool.obtenerConeccion();
				con.setAutoCommit(false);
				
				Rubro rubro = new Rubro(rubroVO);
		    	
		    	if(this.rubros.memberRubro(rubro.getCod_rubro(), cod_emp, con))
		    	{
		    		this.rubros.actualizarRubro(rubro, cod_emp, con);
		    		con.commit();
		    	}
		    	
		    	else
		    		throw new NoExisteRubroException();
	    	
	    	}
	    	catch(ConexionException | SQLException | ModificandoRubroException e){
	    		try {
					con.rollback();
					
				} 
	    		catch (SQLException e1) {
					
					throw new ConexionException();
				}
	    		throw new ModificandoRubroException();
	    	}
	    	finally
	    	{
	    		pool.liberarConeccion(con);
	    	}
	    }
    
///////////////////////////////////////////////////////////////////DOCUMENTOS////////////////////////////////////////////////////////////////////////////////////////////
	/**
	* Obtiene todos los documentos existentes
	*/
	@SuppressWarnings("unchecked")
	public ArrayList<DocumentoAduaneroVO> getDocumentos(String codEmp) throws ObteniendoDocumentosException, ConexionException
	{
	
		Connection con = null;
		
		ArrayList<DocumentoAduanero> lstDocumentos;
		ArrayList<DocumentoAduaneroVO> lstDocumentosVO = new ArrayList<DocumentoAduaneroVO>();
		
		try
		{
			con = this.pool.obtenerConeccion();
			
			lstDocumentos = this.documentos.getDocumentos(codEmp, con);
			
			
			DocumentoAduaneroVO aux;
			for (DocumentoAduanero documento : lstDocumentos) 
			{
				aux = new DocumentoAduaneroVO();
				
				aux.setcodDocumento(documento.getCod_docucmento());
				aux.setdescripcion(documento.getDescirpcion());
				aux.setActivo(documento.isActivo());
				aux.setFechaMod(documento.getFechaMod());
				aux.setOperacion(documento.getOperacion());
				aux.setUsuarioMod(documento.getUsuarioMod());
				
				lstDocumentosVO.add(aux);
			}
		
		}
		catch(ObteniendoDocumentosException e){
			throw e;
		
		} 
		catch (ConexionException e) {
		
			throw e;
		} 
		finally	{
			this.pool.liberarConeccion(con);
		}
		
		
		return lstDocumentosVO;
	}

	/**
	* Inserta un nuevo documento en la base
	* Valida que no exista un documento con el mismo código
	* @throws ExisteEmpresaException 
	*/
	public void insertarDocumento(DocumentoAduaneroVO documentoVO, String codEmp) throws InsertandoDocumentoException, ConexionException, ExisteDocumentoException 
	{
	
		Connection con = null;
		boolean existe = false;
		
		try 
		{
			con = this.pool.obtenerConeccion();
			con.setAutoCommit(false);
			
			DocumentoAduanero documento = new DocumentoAduanero(documentoVO); 
		
			if(!this.documentos.memberDocumento(documento.getCod_docucmento(), codEmp, con)) 	{
			
				this.documentos.insertarDocumento(documento, codEmp, con);
				con.commit();
			}
			else{
				existe = true;
			}
		
		
		}
		
		catch(Exception InsertandoRubroException)  	{
			try {
			con.rollback();
			
			} 
			catch (SQLException e) {
			
				throw new InsertandoDocumentoException();
			}
		
			throw new InsertandoDocumentoException();
		}
		finally	{
			pool.liberarConeccion(con);
		}
		if (existe){
			throw new ExisteDocumentoException();
		}
	}

	/**
	* Actualiza los datos de un documento dado su código
	* valida que exista el código 
	*/
	public void actualizarDocumento(DocumentoAduaneroVO documentoVO, String codEmp) throws ConexionException, NoExisteDocumentoException, ModificandoDocumentoException, ExisteDocumentoException  
	{
	
		Connection con = null;
		
		try 
		{
			con = this.pool.obtenerConeccion();
			con.setAutoCommit(false);
		
			DocumentoAduanero documento = new DocumentoAduanero(documentoVO);
		
		if(this.documentos.memberDocumento(documento.getCod_docucmento(), codEmp, con))	{
			this.documentos.actualizarDocumento(documento, codEmp, con);
			con.commit();
		}
		
		else
			throw new NoExisteDocumentoException();
		
		}
		catch(ConexionException | SQLException | ModificandoDocumentoException e){
			try {
				con.rollback();
			
			} 
			catch (SQLException e1) {
			
				throw new ConexionException();
			}
			
			throw new ModificandoDocumentoException();
		}
		finally	{
			pool.liberarConeccion(con);
		}
	}
	
///////////////////////////////////////////////////////////////////CÓDIGOS GENERALIZADOS////////////////////////////////////////////////////////////////////////////////////////////
	/**
	* Obtiene todos los documentos existentes
	*/
	@SuppressWarnings("unchecked")
	public ArrayList<CodigoGeneralizadoVO> getCodigosGeneralizados() throws ObteniendoCodigosException, ConexionException
	{
	
		Connection con = null;
		
		ArrayList<CodigoGeneralizado> lstCodigos;
		ArrayList<CodigoGeneralizadoVO> lstCodigosVO = new ArrayList<CodigoGeneralizadoVO>();
		
		try
		{
			con = this.pool.obtenerConeccion();
			
			lstCodigos = this.codigosGeneralizados.getCodigosGeneralizados(con);
			
			
			CodigoGeneralizadoVO aux;
			for (CodigoGeneralizado codigoGeneralizado : lstCodigos) 
			{
				aux = new CodigoGeneralizadoVO();
				
				aux.setCodigo(codigoGeneralizado.getCodigo());
				aux.setValor(codigoGeneralizado.getValor());
				aux.setDescripcion(codigoGeneralizado.getDescripcion());
				aux.setFechaMod(codigoGeneralizado.getFechaMod());
				aux.setOperacion(codigoGeneralizado.getOperacion());
				aux.setUsuarioMod(codigoGeneralizado.getUsuarioMod());
				
				lstCodigosVO.add(aux);
			}
		
		}
		catch(ObteniendoCodigosException e){
			throw e;
		
		} 
		catch (ConexionException e) {
		
			throw e;
		} 
		finally	{
			this.pool.liberarConeccion(con);
		}
		
		
		return lstCodigosVO;
	}

	/**
	* Obtiene todos los códigos generalizados para un código dado
	*/
	@SuppressWarnings("unchecked")
	public ArrayList<CodigoGeneralizadoVO> getCodigosGeneralizadosxCodigo(String codigo) throws ObteniendoCodigosException, ConexionException
	{
	
		Connection con = null;
		
		ArrayList<CodigoGeneralizado> lstCodigos;
		ArrayList<CodigoGeneralizadoVO> lstCodigosVO = new ArrayList<CodigoGeneralizadoVO>();
		
		try
		{
			con = this.pool.obtenerConeccion();
			
			lstCodigos = this.codigosGeneralizados.getCodigosGeneralizadosxCodigo(codigo, con);
			
			
			CodigoGeneralizadoVO aux;
			for (CodigoGeneralizado codigoGeneralizado : lstCodigos) 
			{
				aux = new CodigoGeneralizadoVO();
				
				aux.setCodigo(codigoGeneralizado.getCodigo());
				aux.setValor(codigoGeneralizado.getValor());
				aux.setDescripcion(codigoGeneralizado.getDescripcion());
				aux.setFechaMod(codigoGeneralizado.getFechaMod());
				aux.setOperacion(codigoGeneralizado.getOperacion());
				aux.setUsuarioMod(codigoGeneralizado.getUsuarioMod());
				
				lstCodigosVO.add(aux);
			}
		
		}
		catch(ObteniendoCodigosException e){
			throw e;
		
		} 
		catch (ConexionException e) {
		
			throw e;
		} 
		finally	{
			this.pool.liberarConeccion(con);
		}
		
		
		return lstCodigosVO;
	}
	
	/**
	* Inserta un nuevo codigo generalizado en la base
	* @throws ExisteEmpresaException 
	*/
	public void insertarCodigoGeneralizado(CodigoGeneralizadoVO codigoGeneralizadoVO) throws InsertandoCodigoException, ConexionException, ExisteCodigoException 
	{
	
		Connection con = null;
		boolean existe = false;
		
		try 
		{
			con = this.pool.obtenerConeccion();
			con.setAutoCommit(false);
			
			CodigoGeneralizado codigoGeneralizado = new CodigoGeneralizado(codigoGeneralizadoVO); 
			
			if(!this.codigosGeneralizados.memberCodigoGeneralizado(codigoGeneralizado.getCodigo(), codigoGeneralizado.getValor(), con)){
			
				this.codigosGeneralizados.insertarCodigoGeneralizado(codigoGeneralizado, con);
				con.commit();
			}
			else{
				existe = true;
			}
		
		
		}
		
		catch(Exception InsertandoRubroException)  	{
			try {
				con.rollback();
			
			} 
			catch (SQLException e) {
			
				throw new InsertandoCodigoException();
			}
		
			throw new InsertandoCodigoException();
		}
		finally	{
			pool.liberarConeccion(con);
		}
		if (existe){
			throw new ExisteCodigoException();
		}
	}

	/**
	* Actualiza los datos de un codigo dado su código y valor
	* valida que exista el código 
	*/
	public void actualizarCodigoGeneralizado(CodigoGeneralizadoVO codigoGeneralizadoVO) throws ConexionException, NoExisteCodigoException, ModificandoCodigoException, ExisteCodigoException  
	{
	
		Connection con = null;
		
		try 
		{
			con = this.pool.obtenerConeccion();
			con.setAutoCommit(false);
			
			CodigoGeneralizado codigoGeneralizado = new CodigoGeneralizado(codigoGeneralizadoVO);
			
			if(this.codigosGeneralizados.memberCodigoGeneralizado(codigoGeneralizado.getCodigo(), codigoGeneralizado.getValor(), con)){
				this.codigosGeneralizados.actualizarCodigoGeneralizado(codigoGeneralizado, con);
				con.commit();
			}
		
			else
				throw new NoExisteCodigoException();
		
		}
		catch(ConexionException | SQLException | ModificandoCodigoException e){
			try {
				con.rollback();
			
			} 
			catch (SQLException e1) {
			
				throw new ConexionException();
			}
		
			throw new ModificandoCodigoException();
		}
		finally	{
			pool.liberarConeccion(con);
		}
	}
	
	/**
	* Elimina un codigo generalizado en la base
	 * @throws NoExisteCodigoException 
	*/
	public void eliminarCodigoGeneralizado(CodigoGeneralizadoVO codigoGeneralizadoVO) throws EliminandoCodigoGeneralizadoException, ConexionException, NoExisteCodigoException
	{
	
		Connection con = null;
		boolean existe = false;
		
		try 
		{
			con = this.pool.obtenerConeccion();
			con.setAutoCommit(false);
			
			CodigoGeneralizado codigoGeneralizado = new CodigoGeneralizado(codigoGeneralizadoVO); 
			
			if(this.codigosGeneralizados.memberCodigoGeneralizado(codigoGeneralizado.getCodigo(), codigoGeneralizado.getValor(), con)){
			
				this.codigosGeneralizados.eliminarCodigoGeneralizado(codigoGeneralizado.getCodigo(), codigoGeneralizado.getValor(), con);
				con.commit();
			}
			else{
				existe = false;
			}
		
		
		}
		
		catch(Exception EliminandoCodigoGeneralizadoException)  {
			try {
				con.rollback();
			
			} 
			catch (SQLException e) {
			
				throw new EliminandoCodigoGeneralizadoException();
			}
		
			throw new EliminandoCodigoGeneralizadoException();
		}
		finally	{
			pool.liberarConeccion(con);
		}
		if (!existe){
			throw new NoExisteCodigoException();
		}
	}
	
///////////////////////////////////////////////////////////////////COTIZACIONES////////////////////////////////////////////////////////////////////////////////////////////
	/**
	* Obtiene todas las cotizaciones existentes para la empresa
	*/
	@SuppressWarnings("unchecked")
	public ArrayList<CotizacionVO> getCotizaciones(String codEmp, Timestamp inicio, Timestamp fin) throws ObteniendoCotizacionesException, ConexionException
	{
	
		Connection con = null;
		
		ArrayList<Cotizacion> lstCotizaciones;
		ArrayList<CotizacionVO> lstCotizacionesVO = new ArrayList<CotizacionVO>();
		
		try
		{
			con = this.pool.obtenerConeccion();
			
			lstCotizaciones = this.cotizaciones.getCotizaciones(codEmp, con, inicio, fin);
			
			CotizacionVO aux;
			for (Cotizacion cotizacion : lstCotizaciones) 
			{
				aux = new CotizacionVO();
				
				
				aux.setFecha(cotizacion.getFecha());
				aux.setCotizacionCompra(cotizacion.getCotizacion_compra());
				aux.setCotizacionVenta(cotizacion.getCotizacion_venta());
				aux.setFechaMod(cotizacion.getFechaMod());
				aux.setUsuarioMod(cotizacion.getUsuarioMod());
				aux.setOperacion(cotizacion.getOperacion());
				
				aux.setCodMoneda(cotizacion.getMoneda().getCod_moneda());
				aux.setDescripcionMoneda(cotizacion.getMoneda().getDescripcion());
				aux.setSimboloMoneda(cotizacion.getMoneda().getSimbolo());
				aux.setAceptaCotizacionMoneda(cotizacion.getMoneda().isAcepta_cotizacion());
				aux.setActivoMoneda(cotizacion.getMoneda().isActivo());
				
				lstCotizacionesVO.add(aux);
			}
		
		}
		catch(ObteniendoCotizacionesException e){
			throw e;
		
		} 
		catch (ConexionException e) {
		
			throw e;
		} 
		finally	{
			this.pool.liberarConeccion(con);
		}
		
		
		return lstCotizacionesVO;
	}

	//public Cotizacion getCotizacion(String codEmp, Date fecha,Connection con)
	
	/**
	* Obtiene la cotizacion para la empresa y fecha
	*/
	@SuppressWarnings("unchecked")
	public CotizacionVO getCotizacion(String codEmp, Date fecha, String codMoneda) throws ObteniendoCotizacionesException, ConexionException
	{
	
		Connection con = null;
		
		Cotizacion cotizacion;
		ArrayList<CotizacionVO> lstCotizacionesVO = new ArrayList<CotizacionVO>();
		CotizacionVO aux = new CotizacionVO();
		
		try
		{
			con = this.pool.obtenerConeccion();
			
			cotizacion = this.cotizaciones.getCotizacion(codEmp, fecha, codMoneda, con);
			
			aux = new CotizacionVO();
			
			if(cotizacion != null)
			{
				aux.setFecha(cotizacion.getFecha());
				aux.setCotizacionCompra(cotizacion.getCotizacion_compra());
				aux.setCotizacionVenta(cotizacion.getCotizacion_venta());
				aux.setFechaMod(cotizacion.getFechaMod());
				aux.setUsuarioMod(cotizacion.getUsuarioMod());
				aux.setOperacion(cotizacion.getOperacion());
				
				aux.setCodMoneda(cotizacion.getMoneda().getCod_moneda());
				aux.setDescripcionMoneda(cotizacion.getMoneda().getDescripcion());
				aux.setSimboloMoneda(cotizacion.getMoneda().getSimbolo());
				aux.setAceptaCotizacionMoneda(cotizacion.getMoneda().isAcepta_cotizacion());
				aux.setActivoMoneda(cotizacion.getMoneda().isActivo());
			}
		
		}
		catch(ObteniendoCotizacionesException e){
			throw e;
		
		} 
		catch (ConexionException e) {
		
			throw e;
		} 
		finally	{
			this.pool.liberarConeccion(con);
		}
		
		
		return aux;
	}
	
	/**
	* Inserta una nueva cotización en la base
	* Valida que no exista una cotización para moneda/fecha
	* @throws ExisteEmpresaException 
	*/
	public void insertarCotizacion(CotizacionVO cotizacionVO, String codEmp) throws InsertandoCotizacionException, ConexionException, 
		ExisteCotizacionException{
	
		Connection con = null;
		boolean existe = false;
		
		try 
		{
			con = this.pool.obtenerConeccion();
			con.setAutoCommit(false);
			
			Cotizacion cotizacion = new Cotizacion(cotizacionVO); 
			
			if(!this.cotizaciones.memberCotizacion(cotizacionVO.getCodMoneda(), (Timestamp) cotizacionVO.getFecha(), codEmp, con)) 	{
			
				this.cotizaciones.insertarCotizacion(cotizacion, codEmp, con);
				con.commit();
			}
			else{
				existe = true;
			}
		}
		catch(Exception InsertandoCotizacionException)  	{
			try {
			con.rollback();
			
			} 
			catch (SQLException e) {
			
				throw new InsertandoCotizacionException();
			}
		
			throw new InsertandoCotizacionException();
		}
		finally	{
			pool.liberarConeccion(con);
		}
		if (existe){
			throw new ExisteCotizacionException();
		}
	}

	/**
	* Actualiza los datos de una cotizacion dada la moneda y la fecha
	* valida que exista el código 
	*/
	public void actualizarCotizacion(CotizacionVO cotizacionVO, String codEmp) throws ConexionException, NoExisteCotizacionException,
		ModificandoCotizacionException, ExisteCotizacionException  
	{
	
		Connection con = null;
		
		try 
		{
			con = this.pool.obtenerConeccion();
			con.setAutoCommit(false);
			
			Cotizacion cotizacion = new Cotizacion(cotizacionVO);
			
			if(this.cotizaciones.memberCotizacion(cotizacion.getMoneda().getCod_moneda(), cotizacion.getFecha(), codEmp, con)){
				
				this.cotizaciones.actualizarCotizacion(cotizacion, codEmp, con);
				con.commit();
			}
		
			else
				throw new NoExisteCotizacionException();
		
		}
		catch(ConexionException | SQLException | ModificandoCotizacionException e){
			try {
			con.rollback();
			
			} 
			catch (SQLException e1) {
		
				throw new ConexionException();
			}
		
			throw new ModificandoCotizacionException();
		}
		finally	{
			pool.liberarConeccion(con);
		}
	}
	
	 ///////////////////////////////////////////////////////////////////TIPO RUBROS////////////////////////////////////////////////////////////////////////////////////////////
    /**
	 * Obtiene todos los tipos de rubro existentes
	 */
    @SuppressWarnings("unchecked")
	public ArrayList<TipoRubroVO> getTipoRubros(String cod_emp) throws ObteniendoTipoRubroException, ConexionException
    {
    	
    	Connection con = null;
    	
    	ArrayList<TipoRubro> lstTipoRubros;
    	ArrayList<TipoRubroVO> lstTipoRubrosVO = new ArrayList<TipoRubroVO>();
    	    	
    	try
    	{
    		con = this.pool.obtenerConeccion();
    		
    		lstTipoRubros = this.tipoRubros.getTipoRubros(con, cod_emp); 
    		
    		TipoRubroVO aux;
    		for (TipoRubro tipoRubro : lstTipoRubros) 
			{
    			aux = new TipoRubroVO();
    			
    			aux.setCodTipoRubro(tipoRubro.getCod_tipoRubro());
    			aux.setDescripcion(tipoRubro.getDescripcion());
    			aux.setActivo(tipoRubro.isActivo());
    			aux.setFechaMod(tipoRubro.getFechaMod());
    			aux.setOperacion(tipoRubro.getOperacion());
    			aux.setUsuarioMod(tipoRubro.getUsuarioMod());
    			
    			lstTipoRubrosVO.add(aux);
			}
	
    	}
    	catch(ObteniendoTipoRubroException e){
    		throw e;
    		
    	} 
    	catch (ConexionException e) {
			
    		throw e;
    	} 
    	finally
    	{
    		this.pool.liberarConeccion(con);
    	}
    	    
    	
    	return lstTipoRubrosVO;
    }
    
    
    /**
	 * Obtiene todos los tipos de rubro existentes ACTIVOS
	 */
    @SuppressWarnings("unchecked")
	public ArrayList<TipoRubroVO> getTipoRubrosActivos(String cod_emp) throws ObteniendoTipoRubroException, ConexionException
    {
    	
    	Connection con = null;
    	
    	ArrayList<TipoRubro> lstTipoRubros;
    	ArrayList<TipoRubroVO> lstTipoRubrosVO = new ArrayList<TipoRubroVO>();
    	    	
    	try
    	{
    		con = this.pool.obtenerConeccion();
    		
    		lstTipoRubros = this.tipoRubros.getTipoRubrosActivos(con, cod_emp); 
    		
    		TipoRubroVO aux;
    		for (TipoRubro tipoRubro : lstTipoRubros) 
			{
    			aux = new TipoRubroVO();
    			
    			aux.setCodTipoRubro(tipoRubro.getCod_tipoRubro());
    			aux.setDescripcion(tipoRubro.getDescripcion());
    			aux.setActivo(tipoRubro.isActivo());
    			aux.setFechaMod(tipoRubro.getFechaMod());
    			aux.setOperacion(tipoRubro.getOperacion());
    			aux.setUsuarioMod(tipoRubro.getUsuarioMod());
    			
    			lstTipoRubrosVO.add(aux);
			}
	
    	}
    	catch(ObteniendoTipoRubroException e){
    		throw e;
    		
    	} 
    	catch (ConexionException e) {
			
    		throw e;
    	} 
    	finally
    	{
    		this.pool.liberarConeccion(con);
    	}
    	    
    	
    	return lstTipoRubrosVO;
    }
    
    /**
	 * Inserta un nuevo tipo rubro en la base
	 * Valida que no exista un tipo rubro con el mismo código para la misma empresa
     * @throws ExisteEmpresaException 
	 */
    public void insertarTipoRubro(TipoRubroVO tipoRubroVO, String cod_emp) throws InsertandoTipoRubroException, ConexionException, ExisteTipoRubroException 
    {
    	
    	Connection con = null;
    	boolean existe = false;
    	
    	try 
    	{
			con = this.pool.obtenerConeccion();
			con.setAutoCommit(false);
			
	    	TipoRubro tipoRubro = new TipoRubro(tipoRubroVO); 
	    	
	    	if(!this.tipoRubros.memberTipoRubro(tipoRubro.getCod_tipoRubro(), cod_emp, con)){
	    		
	    		this.tipoRubros.insertarTipoRubro(tipoRubro, cod_emp, con);
	    		con.commit();
	    	}
	    	else{
	    		existe = true;
	    	}
	    		
    	
    	}
    	catch(Exception InsertandoTipoRubroException)  	{
    		try {
				con.rollback();
				
			} catch (SQLException e) {
				
				throw new InsertandoTipoRubroException();
			}
    		
    		throw new InsertandoTipoRubroException();
    	}
    	finally
    	{
    		pool.liberarConeccion(con);
    	}
    	if (existe){
    		throw new ExisteTipoRubroException();
    	}
    }

    /**
	 * Actualiza los datos de un tipo de rubro dado su código y la empresa
	 * valida que exista el código 
	 */
    public void actualizarTipoRubro(TipoRubroVO tipoRubroVO, String cod_emp) throws ConexionException, NoExisteTipoRubroException, ModificandoTipoRubroException, ExisteTipoRubroException  
	{
	    	
    	Connection con = null;
    	
    	try 
    	{
			con = this.pool.obtenerConeccion();
			con.setAutoCommit(false);
			
			TipoRubro tipoRubro= new TipoRubro(tipoRubroVO);
	    	
			if(this.tipoRubros.memberTipoRubro(tipoRubro.getCod_tipoRubro(), cod_emp, con))
	    	{
				this.tipoRubros.actualizarTipoRubro(tipoRubro, cod_emp, con);
	    		con.commit();
	    	}
	    	
	    	else
	    		throw new NoExisteTipoRubroException();
    	
    	}
    	catch(NoExisteTipoRubroException| ConexionException | SQLException | ModificandoTipoRubroException e){
    		try {
				con.rollback();
				
			} 
    		catch (SQLException e1) {
				
				throw new ConexionException();
			}
    		throw new ModificandoTipoRubroException();
    	}
    	finally
    	{
    		pool.liberarConeccion(con);
    	}
	}
    
    
/////////////////////////////////INI-CUENTAS/////////////////////////////////
	@SuppressWarnings("unchecked")
	public ArrayList<CuentaVO> getCuentas(String codEmp) throws ObteniendoCuentasException, ConexionException, com.excepciones.Cuentas.ObteniendoRubrosException
	{
	
		Connection con = null;
		
		ArrayList<Cuenta> lstCuentas; 
		ArrayList<CuentaVO> lstCuentasVO = new ArrayList<CuentaVO>();
		
		try
		{
			con = this.pool.obtenerConeccion();
			
			lstCuentas = this.cuentas.getCuentas(codEmp, con);
			
			
			CuentaVO aux;
			for (Cuenta cuenta : lstCuentas) 
			{
				aux = new CuentaVO();
				
				aux.setCodCuenta(cuenta.getCod_cuenta());
				aux.setDescripcion(cuenta.getDescripcion());
				aux.setFechaMod(cuenta.getFechaMod());
				aux.setUsuarioMod(cuenta.getUsuarioMod());
				aux.setOperacion(cuenta.getOperacion());
				aux.setActivo(cuenta.isActivo());
				
				
				RubroVO auxR;
				for(Rubro rub : cuenta.getLstRubros()){
					auxR = new RubroVO();
					auxR.setcodRubro(rub.getCod_rubro());
					auxR.setDescripcion(rub.getDescripcion());
					auxR.setOficina(rub.isOficina());
					auxR.setProceso(rub.isProceso());
					auxR.setPersona(rub.isPersona());
					
					aux.getLstRubros().add(auxR);
				}
				
				lstCuentasVO.add(aux);
			}
		
		}
		catch(ObteniendoCuentasException | com.excepciones.Cuentas.ObteniendoRubrosException e){
			throw e;
		
		} 
		catch (ConexionException e) {
		
			throw e;
		} 
		finally{
			this.pool.liberarConeccion(con);
		}
		
		
		return lstCuentasVO;
	}

	public ArrayList<CuentaVO> getCuentasxRubro(String codEmp, String codRubro) throws ObteniendoCuentasException, ConexionException, com.excepciones.Cuentas.ObteniendoRubrosException
	{
	
		Connection con = null;
		
		ArrayList<Cuenta> lstCuentas; 
		ArrayList<CuentaVO> lstCuentasVO = new ArrayList<CuentaVO>();
		
		try
		{
			con = this.pool.obtenerConeccion();
			
			lstCuentas = this.cuentas.getCuentasxRubro(codEmp, codRubro, con);
			
			
			CuentaVO aux;
			for (Cuenta cuenta : lstCuentas) 
			{
				aux = new CuentaVO();
				
				aux.setCodCuenta(cuenta.getCod_cuenta());
				aux.setDescripcion(cuenta.getDescripcion());
				aux.setFechaMod(cuenta.getFechaMod());
				aux.setUsuarioMod(cuenta.getUsuarioMod());
				aux.setOperacion(cuenta.getOperacion());
				aux.setActivo(cuenta.isActivo());
				
				
				RubroVO auxR;
				for(Rubro rub : cuenta.getLstRubros()){
					auxR = new RubroVO();
					auxR.setcodRubro(rub.getCod_rubro());
					auxR.setDescripcion(rub.getDescripcion());
					auxR.setOficina(rub.isOficina());
					auxR.setProceso(rub.isProceso());
					auxR.setPersona(rub.isPersona());
					
					aux.getLstRubros().add(auxR);
				}
				
				lstCuentasVO.add(aux);
			}
		
		}
		catch(ObteniendoCuentasException | com.excepciones.Cuentas.ObteniendoRubrosException e){
			throw e;
		
		} 
		catch (ConexionException e) {
		
			throw e;
		} 
		finally{
			this.pool.liberarConeccion(con);
		}
		
		
		return lstCuentasVO;
	}
	
	public void insertarCuenta(CuentaVO cuentaVO, String codEmp) throws InsertandoCuentaException, ConexionException, ExisteCuentaException 
	{
	
		Connection con = null;
		boolean existe = false;
		
		try 
		{
			con = this.pool.obtenerConeccion();
			con.setAutoCommit(false);
			
			Cuenta cuenta = new Cuenta(cuentaVO); 
			
			if(!this.cuentas.memberCuenta(cuenta.getCod_cuenta(), codEmp, con)){
				
				this.cuentas.insertarCuenta(cuenta, codEmp, con);
				con.commit();
			}
			else{
				existe = true;
			}
		
		
		}
		catch(Exception InsertandoCuentaException){
			
			try {
				con.rollback();
		
			} 
			catch (SQLException e) {
		
				throw new InsertandoCuentaException();
			}
		
			throw new InsertandoCuentaException();
		}
		finally	{
			pool.liberarConeccion(con);
		}
		if (existe){
			throw new ExisteCuentaException();
		}
	}

	public void editarCuenta(CuentaVO cuentaVO, String codEmp) throws ConexionException, NoExisteCuentaException, ModificandoCuentaException, InsertandoCuentaException  
	{
	
		Connection con = null;
		
		try 
		{
		con = this.pool.obtenerConeccion();
		con.setAutoCommit(false);
		
		Cuenta cuenta = new Cuenta(cuentaVO);
		
		if(this.cuentas.memberCuenta(cuenta.getCod_cuenta(), codEmp, con)){
			
			this.cuentas.actualizarCuenta(cuenta, codEmp, con);
			con.commit();
		}
		
		else
			throw new NoExisteCuentaException();
		
		}
		catch(MemberCuentaException| ConexionException | SQLException | ModificandoCuentaException e){
			try {
				con.rollback();
		
			} 
			catch (SQLException e1) {
		
				throw new ConexionException();
			}
			throw new ModificandoCuentaException();
		}
		finally{
			pool.liberarConeccion(con);
		}
	}

	@SuppressWarnings("unchecked")
	public ArrayList<RubroVO> getRubrosNoCuenta(String codCuenta, String codEmp) throws ObteniendoCuentasException, ConexionException, ErrorInesperadoException {
	
		Connection con = null;
		
		ArrayList<Rubro> lstRubros = new ArrayList<Rubro>();
		ArrayList<RubroVO> lstRubroSelVO = new ArrayList<RubroVO>();
		
		try{
			con = this.pool.obtenerConeccion();
			
			lstRubros = this.cuentas.getRubrosNoCuenta(codCuenta, codEmp, con);
			
			/*Transformamos al VO de seleccion*/
			RubroVO rubroSelVO;
			for (Rubro rubro : lstRubros) {
				rubroSelVO = new RubroVO(rubro);
				
				lstRubroSelVO.add(rubroSelVO);
			}
		
		}
		catch(Exception e){
			throw new ErrorInesperadoException();
		}
		finally{
			this.pool.liberarConeccion(con);
		}
		
		return lstRubroSelVO;
	
	}


//	@SuppressWarnings("unchecked")
//	public Hashtable<String, FormularioVO> getFormulariosxUsuario(String usuario, String codEmp) throws ObteniendoFormulariosException, ConexionException 
//	
//	{
//		Connection con = null;
//		
//		ArrayList<Formulario> lstFormularios = new ArrayList<Formulario>();
//		Hashtable<String, FormularioVO> hLstFormSelVO = new Hashtable<String, FormularioVO>();
//		
//		try {
//			con = this.pool.obtenerConeccion();
//			
//			
//			lstFormularios = this.usuarios.getFormulariosxUsuario(usuario, codEmp, con);
//			
//			
//			/*Transformamos al VO */
//			FormularioVO formSelVO;
//			for (Formulario formulario : lstFormularios) {
//			formSelVO = new FormularioVO(formulario);
//			
//			hLstFormSelVO.put(formSelVO.getCodigo(), formSelVO);
//			}
//			
//		} 
//		catch (ObteniendoFormulariosException e) {
//			throw e;
//		
//		} 
//		catch (ConexionException e) {
//		
//			throw e;
//		}
//		finally{
//			this.pool.liberarConeccion(con);
//		}
//		
//		return hLstFormSelVO;
//	
//	}

/////////////////////////////////FIN-CUENTAS/////////////////////////////////
	
	
/////////////////////////////////INI-PROCESOS/////////////////////////////////
	/**
	 * Obtiene todos los procesos existentes 
	 */
    @SuppressWarnings("unchecked")
	public ArrayList<ProcesoVO> getProcesos(String cod_emp, Timestamp inicio, Timestamp fin) throws ObteniendoProcesosException, ConexionException
    {
    	
    	Connection con = null;
    	
    	ArrayList<Proceso> lstProcesos;
    	ArrayList<ProcesoVO> lstProcesosVO = new ArrayList<ProcesoVO>();
    	    	
    	try
    	{
    		con = this.pool.obtenerConeccion();
    		
    		lstProcesos = this.procesos.getProcesosTodos(con, cod_emp, inicio, fin);
    		
    		
    		ProcesoVO aux;
    		for (Proceso proceso : lstProcesos) 
			{
    			aux = new ProcesoVO();
    			
    			aux.setOperacion(proceso.getOperacion());
    			aux.setFechaMod(proceso.getFechaMod());
    			aux.setUsuarioMod(proceso.getUsuarioMod());
    			aux.setCodigo(proceso.getCodigo());
    			aux.setCodCliente(proceso.getClienteInfo().getCodigo());
    			aux.setNomCliente(proceso.getClienteInfo().getNombre());
    			aux.setCodMoneda(proceso.getMonedaInfo().getCodMoneda());
    			aux.setDescMoneda(proceso.getMonedaInfo().getDescripcion());
    			aux.setSimboloMoneda(proceso.getMonedaInfo().getSimbolo());
    			aux.setFecha(proceso.getFecha());
    			aux.setNroMega(proceso.getNroMega());
    			aux.setCodDocum(proceso.getDocumento().getCod_docucmento());
    			aux.setNomDocum(proceso.getDocumento().getDescirpcion());
    			aux.setNroDocum(String.valueOf(proceso.getNroDocum()));
    			aux.setFecDocum(proceso.getFecDocum());
    			aux.setCarpeta(proceso.getCarpeta());
    			aux.setImpMo(proceso.getImpMo());
    			aux.setImpMn(proceso.getImpMn());
    			aux.setImpTr(proceso.getImpTr());
    			aux.setTcMov(proceso.getTcMov());
    			aux.setKilos(proceso.getKilos());
    			aux.setFecCruce(proceso.getFecCruce());
    			aux.setMarca(proceso.getMarca());
    			aux.setMedio(proceso.getMedio());
    			aux.setDescripcion(proceso.getDescripcion());
    			aux.setObservaciones(proceso.getObservaciones());
    			
    			lstProcesosVO.add(aux);
			}
	
    	}
    	catch(ObteniendoProcesosException e){
    		throw e;
    		
    	} 
    	catch (ConexionException e) {
			
    		throw e;
    	} 
    	finally
    	{
    		this.pool.liberarConeccion(con);
    	}
    	    
    	
    	return lstProcesosVO;
    }
    
    @SuppressWarnings("unchecked")
   	public ArrayList<ProcesoVO> getProcesosSinFecha(String cod_emp) throws ObteniendoProcesosException, ConexionException
       {
       	
       	Connection con = null;
       	
       	ArrayList<Proceso> lstProcesos;
       	ArrayList<ProcesoVO> lstProcesosVO = new ArrayList<ProcesoVO>();
       	    	
       	try
       	{
       		con = this.pool.obtenerConeccion();
       		
       		lstProcesos = this.procesos.getProcesosTodosSinFecha(con, cod_emp);
       		
       		
       		ProcesoVO aux;
       		for (Proceso proceso : lstProcesos) 
   			{
       			aux = new ProcesoVO();
       			
       			aux.setOperacion(proceso.getOperacion());
       			aux.setFechaMod(proceso.getFechaMod());
       			aux.setUsuarioMod(proceso.getUsuarioMod());
       			aux.setCodigo(proceso.getCodigo());
       			aux.setCodCliente(proceso.getClienteInfo().getCodigo());
       			aux.setNomCliente(proceso.getClienteInfo().getNombre());
       			aux.setCodMoneda(proceso.getMonedaInfo().getCodMoneda());
       			aux.setDescMoneda(proceso.getMonedaInfo().getDescripcion());
       			aux.setSimboloMoneda(proceso.getMonedaInfo().getSimbolo());
       			aux.setFecha(proceso.getFecha());
       			aux.setNroMega(proceso.getNroMega());
       			aux.setCodDocum(proceso.getDocumento().getCod_docucmento());
       			aux.setNomDocum(proceso.getDocumento().getDescirpcion());
       			aux.setNroDocum(String.valueOf(proceso.getNroDocum()));
       			aux.setFecDocum(proceso.getFecDocum());
       			aux.setCarpeta(proceso.getCarpeta());
       			aux.setImpMo(proceso.getImpMo());
       			aux.setImpMn(proceso.getImpMn());
       			aux.setImpTr(proceso.getImpTr());
       			aux.setTcMov(proceso.getTcMov());
       			aux.setKilos(proceso.getKilos());
       			aux.setFecCruce(proceso.getFecCruce());
       			aux.setMarca(proceso.getMarca());
       			aux.setMedio(proceso.getMedio());
       			aux.setDescripcion(proceso.getDescripcion());
       			aux.setObservaciones(proceso.getObservaciones());
       			
       			lstProcesosVO.add(aux);
   			}
   	
       	}
       	catch(ObteniendoProcesosException e){
       		throw e;
       		
       	} 
       	catch (ConexionException e) {
   			
       		throw e;
       	} 
       	finally
       	{
       		this.pool.liberarConeccion(con);
       	}
       	    
       	
       	return lstProcesosVO;
       }
    
    @SuppressWarnings("unchecked")
   	public ArrayList<ProcesoVO> getProcesosCliente(String cod_emp, String cod_cliente) throws ObteniendoProcesosException, ConexionException
       {
       	
       	Connection con = null;
       	
       	ArrayList<Proceso> lstProcesos;
       	ArrayList<ProcesoVO> lstProcesosVO = new ArrayList<ProcesoVO>();
       	    	
       	try
       	{
       		con = this.pool.obtenerConeccion();
       		
       		lstProcesos = this.procesos.getProcesosCliente(con, cod_emp, cod_cliente);
       		
       		
       		ProcesoVO aux;
       		for (Proceso proceso : lstProcesos) 
   			{
       			aux = new ProcesoVO();
       			
       			aux.setOperacion(proceso.getOperacion());
       			aux.setFechaMod(proceso.getFechaMod());
       			aux.setUsuarioMod(proceso.getUsuarioMod());
       			aux.setCodigo(proceso.getCodigo());
       			aux.setCodCliente(proceso.getClienteInfo().getCodigo());
       			aux.setNomCliente(proceso.getClienteInfo().getNombre());
       			aux.setCodMoneda(proceso.getMonedaInfo().getCodMoneda());
       			aux.setDescMoneda(proceso.getMonedaInfo().getDescripcion());
       			aux.setSimboloMoneda(proceso.getMonedaInfo().getSimbolo());
       			aux.setFecha(proceso.getFecha());
       			aux.setNroMega(proceso.getNroMega());
       			aux.setCodDocum(proceso.getDocumento().getCod_docucmento());
       			aux.setNomDocum(proceso.getDocumento().getDescirpcion());
       			aux.setNroDocum(String.valueOf(proceso.getNroDocum()));
       			aux.setFecDocum(proceso.getFecDocum());
       			aux.setCarpeta(proceso.getCarpeta());
       			aux.setImpMo(proceso.getImpMo());
       			aux.setImpMn(proceso.getImpMn());
       			aux.setImpTr(proceso.getImpTr());
       			aux.setTcMov(proceso.getTcMov());
       			aux.setKilos(proceso.getKilos());
       			aux.setFecCruce(proceso.getFecCruce());
       			aux.setMarca(proceso.getMarca());
       			aux.setMedio(proceso.getMedio());
       			aux.setDescripcion(proceso.getDescripcion());
       			aux.setObservaciones(proceso.getObservaciones());
       			
       			lstProcesosVO.add(aux);
   			}
   	
       	}
       	catch(ObteniendoProcesosException e){
       		throw e;
       		
       	} 
       	catch (ConexionException e) {
   			
       		throw e;
       	} 
       	finally
       	{
       		this.pool.liberarConeccion(con);
       	}
       	    
       	
       	return lstProcesosVO;
       }
    
    /**
	 * Inserta un nuevo proceso en la base
	 */
    public int insertarProceso(ProcesoVO procesoVO, String cod_emp) throws IngresandoProcesoException, ConexionException, ExisteProcesoException 
    {
    	
    	Connection con = null;
    	boolean existe = false;
    	int codigo;
    	
    	try 
    	{
			con = this.pool.obtenerConeccion();
			con.setAutoCommit(false);
			
	    	Proceso proceso = new Proceso(procesoVO); 
	    	codigo = numeradores.getNumero(con, "01", cod_emp);
	    	proceso.setCodigo(codigo);
	    	this.procesos.insertarProceso(proceso, cod_emp, con);
    		con.commit();
    		return codigo;
    	
    	}
    	catch(Exception IngresandoProcesoException)  	{
    		try {
				con.rollback();
				
			} catch (SQLException e) {
				
				throw new IngresandoProcesoException();
			}
    		
    		throw new IngresandoProcesoException();
    	}
    	finally
    	{
    		pool.liberarConeccion(con);
    	}
    }

    /**
	 * Actualiza los datos de un proceso dado su código y la empresa
	 * valida que exista el código 
	 */
    public void actualizarProceso(ProcesoVO procesoVO, String cod_emp) throws ConexionException, ModificandoProcesoException, NoExisteProcesoException, ExisteProcesoException  
	{
	    	
    	Connection con = null;
    	
    	try 
    	{
			con = this.pool.obtenerConeccion();
			con.setAutoCommit(false);
			
			Proceso proceso = new Proceso(procesoVO);
	    	
			if(this.procesos.memberProceso(proceso.getCodigo(), cod_emp, con)){
				this.procesos.modificarProceso(proceso, cod_emp, con);
				con.commit();
			}
	    	else
	    		throw new NoExisteProcesoException();
    	
    	}
    	catch(NoExisteProcesoException| ConexionException | SQLException | ModificandoProcesoException e){
    		try {
				con.rollback();
				
			} 
    		catch (SQLException e1) {
				
				throw new ConexionException();
			}
    		throw new ModificandoProcesoException();
    	}
    	finally
    	{
    		pool.liberarConeccion(con);
    	}
	}
    
    /**
   	 * Elimina un proceso
   	 * valida que exista el código 
     * @throws EliminandoProcesoException 
   	 */
    public void eliminarProceso(int codigo, String cod_emp) throws ConexionException, NoExisteProcesoException, ExisteProcesoException, EliminandoProcesoException  
   	{
   	    	
       	Connection con = null;
       	
       	try 
       	{
   			con = this.pool.obtenerConeccion();
   			con.setAutoCommit(false);
   			
   	    	
   			if(this.procesos.memberProceso(codigo, cod_emp, con)){
   				this.procesos.eliminarProceso(codigo, cod_emp, con);
   				con.commit();
   			}
   	    	else
   	    		throw new NoExisteProcesoException();
       	
       	}
       	catch(NoExisteProcesoException| ConexionException | SQLException | EliminandoProcesoException e){
       		try {
   				con.rollback();
   				
   			} 
       		catch (SQLException e1) {
   				
   				throw new ConexionException();
   			}
       		throw new EliminandoProcesoException();
       	}
       	finally
       	{
       		pool.liberarConeccion(con);
       	}
   	}
    
    /**
	 * Obtiene todos los procesos existentes 
	 */
    @SuppressWarnings("unchecked")
	public ProcesoVO getProceso(String cod_emp, Integer codProceso) throws ObteniendoProcesosException, ConexionException
    {
    	
    	Connection con = null;
    	Proceso proceso = new Proceso();
    	ProcesoVO aux = new ProcesoVO();
    	
    	try
    	{
    		con = this.pool.obtenerConeccion();
    		
    		proceso = this.procesos.getProceso(con, cod_emp, codProceso);
    			
			aux.setOperacion(proceso.getOperacion());
			aux.setFechaMod(proceso.getFechaMod());
			aux.setUsuarioMod(proceso.getUsuarioMod());
			aux.setCodigo(proceso.getCodigo());
			aux.setCodCliente(proceso.getClienteInfo().getCodigo());
			aux.setNomCliente(proceso.getClienteInfo().getNombre());
			aux.setCodMoneda(proceso.getMonedaInfo().getCodMoneda());
			aux.setDescMoneda(proceso.getMonedaInfo().getDescripcion());
			aux.setSimboloMoneda(proceso.getMonedaInfo().getSimbolo());
			aux.setFecha(proceso.getFecha());
			aux.setNroMega(proceso.getNroMega());
			aux.setCodDocum(proceso.getDocumento().getCod_docucmento());
			aux.setNomDocum(proceso.getDocumento().getDescirpcion());
			aux.setNroDocum(String.valueOf(proceso.getNroDocum()));
			aux.setFecDocum(proceso.getFecDocum());
			aux.setCarpeta(proceso.getCarpeta());
			aux.setImpMo(proceso.getImpMo());
			aux.setImpMn(proceso.getImpMn());
			aux.setImpTr(proceso.getImpTr());
			aux.setTcMov(proceso.getTcMov());
			aux.setKilos(proceso.getKilos());
			aux.setFecCruce(proceso.getFecCruce());
			aux.setMarca(proceso.getMarca());
			aux.setMedio(proceso.getMedio());
			aux.setDescripcion(proceso.getDescripcion());
			aux.setObservaciones(proceso.getObservaciones());
    			
    	}
    	catch(ObteniendoProcesosException e){
    		throw e;
    		
    	} 
    	catch (ConexionException e) {
			
    		throw e;
    	} 
    	finally
    	{
    		this.pool.liberarConeccion(con);
    	}
    	    
    	
    	return aux;
    }
/////////////////////////////////FIN-PROCESOS/////////////////////////////////
 
/////////////////////////////////INI-GASTOS/////////////////////////////////
	/**
	* Obtiene todos los gastos existentes 
	*/
	@SuppressWarnings("unchecked")
	public ArrayList<GastoVO> getGastos(String cod_emp, Timestamp inicio, Timestamp fin) throws ObteniendoGastosException, ConexionException
	{
	
		Connection con = null;
		
		ArrayList<Gasto> lstGastos;
		ArrayList<GastoVO> lstGastosVO = new ArrayList<GastoVO>();
		
		try
		{
			con = this.pool.obtenerConeccion();
			
			lstGastos = this.gastos.getGastos(con, cod_emp, inicio, fin);
			
			
			GastoVO aux;
			for (Gasto gasto : lstGastos) 
			{
				aux = new GastoVO();
				
				aux.setFecDoc(gasto.getFecDoc());
				aux.setCodDocum(gasto.getCodDocum());
				aux.setSerieDocum(gasto.getSerieDocum());
				aux.setNroDocum(String.valueOf(gasto.getNroDocum()));
				aux.setCodEmp(gasto.getCodEmp());
				aux.setCodMoneda(gasto.getMoneda().getCodMoneda());
				aux.setNomMoneda(gasto.getMoneda().getDescripcion());
				aux.setReferencia(gasto.getReferencia());
				aux.setCodTitular(gasto.getTitInfo().getCodigo());
				aux.setNomTitular(gasto.getTitInfo().getNombre());
				aux.setNroTrans(gasto.getNroTrans());
				aux.setFecValor(gasto.getFecValor());
				aux.setCodProceso(gasto.getCodProceso());
				aux.setReferencia(gasto.getReferencia());
				aux.setImpImpuMn(gasto.getImpImpuMn());
				aux.setImpImpuMo(gasto.getImpImpuMo());
				aux.setImpSubMn(gasto.getImpSubMn());
				aux.setImpSubMo(gasto.getImpSubMo());
				aux.setImpTotMn(gasto.getImpTotMn());
				aux.setImpTotMo(gasto.getImpTotMo());
				aux.setTcMov(gasto.getTcMov());
				aux.setCodCuenta(gasto.getCuenta().getCodCuenta());
				aux.setNomCuenta(gasto.getCuenta().getNomCuenta());
				aux.setCodRubro(gasto.getRubroInfo().getCodRubro());
				aux.setNomRubro(gasto.getRubroInfo().getNomRubro());
				aux.setCodCtaInd(gasto.getCodCuentaInd());
				aux.setFechaMod(gasto.getFechaMod());
				aux.setUsuarioMod(gasto.getUsuarioMod());
				aux.setOperacion(gasto.getOperacion());
				aux.setDescProceso(gasto.getDescProceso());
				aux.setCodImpuesto(gasto.getImpuestoInfo().getCodImpuesto());
				aux.setNomImpuesto(gasto.getImpuestoInfo().getNomImpuesto());
				aux.setPorcentajeImpuesto(gasto.getImpuestoInfo().getPorcentaje());
				aux.setEstadoGasto(gasto.getEstadoGasto());
				
				lstGastosVO.add(aux);
			}
		
		}
		catch(ObteniendoGastosException e){
			throw e;
		
		} 
		catch (ConexionException e) {
		
			throw e;
		} 
		finally{
			this.pool.liberarConeccion(con);
		}
		
		
		return lstGastosVO;
	}
	
	/**
	* Obtiene todos los no cobrables para un proceso
	*/
	@SuppressWarnings("unchecked")
	public ArrayList<GastoVO> getGastosNoCobrablesxProceso(String cod_emp, int codProceso) throws ObteniendoGastosException, ConexionException
	{
	
		Connection con = null;
		
		ArrayList<DocumDetalle> lstGastos;
		ArrayList<GastoVO> lstGastosVO = new ArrayList<GastoVO>();
		
		try
		{
			con = this.pool.obtenerConeccion();
			
			lstGastos = this.gastos.getGastosNoCobrablesxProceso(con, cod_emp, codProceso);
			
			
			GastoVO aux;
			for (DocumDetalle gasto : lstGastos) 
			{
				aux = new GastoVO();
				
				aux.setFecDoc(gasto.getFecDoc());
				aux.setCodDocum(gasto.getCodDocum());
				aux.setSerieDocum(gasto.getSerieDocum());
				aux.setNroDocum(String.valueOf(gasto.getNroDocum()));
				aux.setCodEmp(gasto.getCodEmp());
				aux.setCodMoneda(gasto.getMoneda().getCodMoneda());
				aux.setSimboloMoneda(gasto.getMoneda().getSimbolo());
				aux.setNomMoneda(gasto.getMoneda().getDescripcion());
				aux.setReferencia(gasto.getReferencia());
				aux.setCodTitular(gasto.getTitInfo().getCodigo());
				aux.setNomTitular(gasto.getTitInfo().getNombre());
				aux.setNroTrans(gasto.getNroTrans());
				aux.setFecValor(gasto.getFecValor());
				aux.setCodProceso(gasto.getCodProceso());
				aux.setReferencia(gasto.getReferencia());
				aux.setImpImpuMn(gasto.getImpImpuMn());
				aux.setImpImpuMo(gasto.getImpImpuMo());
				aux.setImpSubMn(gasto.getImpSubMn());
				aux.setImpSubMo(gasto.getImpSubMo());
				aux.setImpTotMn(gasto.getImpTotMn());
				aux.setImpTotMo(gasto.getImpTotMo());
				aux.setTcMov(gasto.getTcMov());
				aux.setCodCuenta(gasto.getCuenta().getCodCuenta());
				aux.setNomCuenta(gasto.getCuenta().getNomCuenta());
				aux.setCodRubro(gasto.getRubroInfo().getCodRubro());
				aux.setNomRubro(gasto.getRubroInfo().getNomRubro());
				aux.setCodCtaInd(gasto.getCodCuentaInd());
				aux.setFechaMod(gasto.getFechaMod());
				aux.setUsuarioMod(gasto.getUsuarioMod());
				aux.setOperacion(gasto.getOperacion());
				aux.setDescProceso(gasto.getDescProceso());
				aux.setCodImpuesto(gasto.getImpuestoInfo().getCodImpuesto());
				aux.setNomImpuesto(gasto.getImpuestoInfo().getNomImpuesto());
				aux.setPorcentajeImpuesto(gasto.getImpuestoInfo().getPorcentaje());
				aux.setEstadoGasto(gasto.getEstadoGasto());
				lstGastosVO.add(aux);
			}
		
		}
		catch(ObteniendoGastosException e){
			throw e;
		
		} 
		catch (ConexionException e) {
		
			throw e;
		} 
		finally{
			this.pool.liberarConeccion(con);
		}
		
		
		return lstGastosVO;
	}
	
	
	/**
	* Obtiene todos los cobrables para un proceso
	*/
	@SuppressWarnings("unchecked")
	public ArrayList<GastoVO> getGastosCobrablesxProceso(String cod_emp, int codProceso) throws ObteniendoGastosException, ConexionException
	{
		Connection con = null;
		
		ArrayList<DocumDetalle> lstGastos;
		ArrayList<GastoVO> lstGastosVO = new ArrayList<GastoVO>();
		
		try
		{
			con = this.pool.obtenerConeccion();
			
			lstGastos = this.gastos.getGastosCobrablesxProceso(con, cod_emp, codProceso);
			
			
			GastoVO aux;
			for (DocumDetalle gasto : lstGastos) 
			{
				aux = new GastoVO();
				
				aux.setFecDoc(gasto.getFecDoc());
				aux.setCodDocum(gasto.getCodDocum());
				aux.setSerieDocum(gasto.getSerieDocum());
				aux.setNroDocum(String.valueOf(gasto.getNroDocum()));
				aux.setCodEmp(gasto.getCodEmp());
				aux.setCodMoneda(gasto.getMoneda().getCodMoneda());
				aux.setSimboloMoneda(gasto.getMoneda().getSimbolo());
				aux.setNomMoneda(gasto.getMoneda().getDescripcion());
				aux.setReferencia(gasto.getReferencia());
				aux.setCodTitular(gasto.getTitInfo().getCodigo());
				aux.setNomTitular(gasto.getTitInfo().getNombre());
				aux.setNroTrans(gasto.getNroTrans());
				aux.setFecValor(gasto.getFecValor());
				aux.setCodProceso(gasto.getCodProceso());
				aux.setReferencia(gasto.getReferencia());
				aux.setImpImpuMn(gasto.getImpImpuMn());
				aux.setImpImpuMo(gasto.getImpImpuMo());
				aux.setImpSubMn(gasto.getImpSubMn());
				aux.setImpSubMo(gasto.getImpSubMo());
				aux.setImpTotMn(gasto.getImpTotMn());
				aux.setImpTotMo(gasto.getImpTotMo());
				aux.setTcMov(gasto.getTcMov());
				aux.setCodCuenta(gasto.getCuenta().getCodCuenta());
				aux.setNomCuenta(gasto.getCuenta().getNomCuenta());
				aux.setCodRubro(gasto.getRubroInfo().getCodRubro());
				aux.setNomRubro(gasto.getRubroInfo().getNomRubro());
				aux.setCodCtaInd(gasto.getCodCuentaInd());
				aux.setFechaMod(gasto.getFechaMod());
				aux.setUsuarioMod(gasto.getUsuarioMod());
				aux.setOperacion(gasto.getOperacion());
				aux.setDescProceso(gasto.getDescProceso());
				aux.setCodImpuesto(gasto.getImpuestoInfo().getCodImpuesto());
				aux.setNomImpuesto(gasto.getImpuestoInfo().getNomImpuesto());
				aux.setPorcentajeImpuesto(gasto.getImpuestoInfo().getPorcentaje());
				aux.setEstadoGasto(gasto.getEstadoGasto());
				lstGastosVO.add(aux);
			}
		
		}
		catch(ObteniendoGastosException e){
			throw e;
		
		} 
		catch (ConexionException e) {
		
			throw e;
		} 
		finally{
			this.pool.liberarConeccion(con);
		}
		
		
		return lstGastosVO;
	}
	
	
	/**
	* Obtiene todos los cobrables para un proceso
	*/
	@SuppressWarnings("unchecked")
	public ArrayList<GastoVO> getGastosAPagarxProceso(String cod_emp, int codProceso) throws ObteniendoGastosException, ConexionException
	{
	
		Connection con = null;
		
		ArrayList<DocumDetalle> lstGastos;
		ArrayList<GastoVO> lstGastosVO = new ArrayList<GastoVO>();
		
		try
		{
			con = this.pool.obtenerConeccion();
			
			lstGastos = this.gastos.getGastosAPagarxProceso(con, cod_emp, codProceso);
			
			
			GastoVO aux;
			for (DocumDetalle gasto : lstGastos) 
			{
				aux = new GastoVO();
				
				aux.setFecDoc(gasto.getFecDoc());
				aux.setCodDocum(gasto.getCodDocum());
				aux.setSerieDocum(gasto.getSerieDocum());
				aux.setNroDocum(String.valueOf(gasto.getNroDocum()));
				aux.setCodEmp(gasto.getCodEmp());
				aux.setCodMoneda(gasto.getMoneda().getCodMoneda());
				aux.setSimboloMoneda(gasto.getMoneda().getSimbolo());
				aux.setNomMoneda(gasto.getMoneda().getDescripcion());
				aux.setReferencia(gasto.getReferencia());
				aux.setCodTitular(gasto.getTitInfo().getCodigo());
				aux.setNomTitular(gasto.getTitInfo().getNombre());
				aux.setNroTrans(gasto.getNroTrans());
				aux.setFecValor(gasto.getFecValor());
				aux.setCodProceso(gasto.getCodProceso());
				aux.setReferencia(gasto.getReferencia());
				aux.setImpImpuMn(gasto.getImpImpuMn());
				aux.setImpImpuMo(gasto.getImpImpuMo());
				aux.setImpSubMn(gasto.getImpSubMn());
				aux.setImpSubMo(gasto.getImpSubMo());
				aux.setImpTotMn(gasto.getImpTotMn());
				aux.setImpTotMo(gasto.getImpTotMo());
				aux.setTcMov(gasto.getTcMov());
				aux.setCodCuenta(gasto.getCuenta().getCodCuenta());
				aux.setNomCuenta(gasto.getCuenta().getNomCuenta());
				aux.setCodRubro(gasto.getRubroInfo().getCodRubro());
				aux.setNomRubro(gasto.getRubroInfo().getNomRubro());
				aux.setCodCtaInd(gasto.getCodCuentaInd());
				aux.setFechaMod(gasto.getFechaMod());
				aux.setUsuarioMod(gasto.getUsuarioMod());
				aux.setOperacion(gasto.getOperacion());
				aux.setDescProceso(gasto.getDescProceso());
				aux.setCodImpuesto(gasto.getImpuestoInfo().getCodImpuesto());
				aux.setNomImpuesto(gasto.getImpuestoInfo().getNomImpuesto());
				aux.setPorcentajeImpuesto(gasto.getImpuestoInfo().getPorcentaje());
				aux.setEstadoGasto(gasto.getEstadoGasto());
				lstGastosVO.add(aux);
			}
		
		}
		catch(ObteniendoGastosException e){
			throw e;
		
		} 
		catch (ConexionException e) {
		
			throw e;
		} 
		finally{
			this.pool.liberarConeccion(con);
		}
		
		
		return lstGastosVO;
	}
	
	public double getSaldoGasto(int nroDocum, String codEmp) throws  ObteniendoSaldoException, ExisteFacturaException, NoExisteFacturaException, ConexionException{
		
		Connection con = null;
		double saldo = 0;
		
		try 
		{
			con = this.pool.obtenerConeccion();
			
			
			saldo = this.gastos.getSaldoGasto(nroDocum, codEmp, con);
				
			
		
		}catch(ObteniendoSaldoException | ConexionException  e)
		{
			try {
				
				con.rollback();
			
			} catch (SQLException e1) {
			
				throw new ConexionException();
			}
				throw new ObteniendoSaldoException();
		}
		finally
		{
			pool.liberarConeccion(con);
		}
		
		return saldo;
	}
	
	/**
	* Obtiene todos los cobrables para un proceso
	*/
	@SuppressWarnings("unchecked")
	public ArrayList<GastoVO> getGastosAnuladosxProceso(String cod_emp, int codProceso) throws ObteniendoGastosException, ConexionException
	{
	
		Connection con = null;
		
		ArrayList<DocumDetalle> lstGastos;
		ArrayList<GastoVO> lstGastosVO = new ArrayList<GastoVO>();
		
		try
		{
			con = this.pool.obtenerConeccion();
			
			lstGastos = this.gastos.getGastosAnuladosxProceso(con, cod_emp, codProceso);
			
			
			GastoVO aux;
			for (DocumDetalle gasto : lstGastos) 
			{
				aux = new GastoVO();
				
				aux.setFecDoc(gasto.getFecDoc());
				aux.setCodDocum(gasto.getCodDocum());
				aux.setSerieDocum(gasto.getSerieDocum());
				aux.setNroDocum(String.valueOf(gasto.getNroDocum()));
				aux.setCodEmp(gasto.getCodEmp());
				aux.setCodMoneda(gasto.getMoneda().getCodMoneda());
				aux.setSimboloMoneda(gasto.getMoneda().getSimbolo());
				aux.setNomMoneda(gasto.getMoneda().getDescripcion());
				aux.setReferencia(gasto.getReferencia());
				aux.setCodTitular(gasto.getTitInfo().getCodigo());
				aux.setNomTitular(gasto.getTitInfo().getNombre());
				aux.setNroTrans(gasto.getNroTrans());
				aux.setFecValor(gasto.getFecValor());
				aux.setCodProceso(gasto.getCodProceso());
				aux.setReferencia(gasto.getReferencia());
				aux.setImpImpuMn(gasto.getImpImpuMn());
				aux.setImpImpuMo(gasto.getImpImpuMo());
				aux.setImpSubMn(gasto.getImpSubMn());
				aux.setImpSubMo(gasto.getImpSubMo());
				aux.setImpTotMn(gasto.getImpTotMn());
				aux.setImpTotMo(gasto.getImpTotMo());
				aux.setTcMov(gasto.getTcMov());
				aux.setCodCuenta(gasto.getCuenta().getCodCuenta());
				aux.setNomCuenta(gasto.getCuenta().getNomCuenta());
				aux.setCodRubro(gasto.getRubroInfo().getCodRubro());
				aux.setNomRubro(gasto.getRubroInfo().getNomRubro());
				aux.setCodCtaInd(gasto.getCodCuentaInd());
				aux.setFechaMod(gasto.getFechaMod());
				aux.setUsuarioMod(gasto.getUsuarioMod());
				aux.setOperacion(gasto.getOperacion());
				aux.setDescProceso(gasto.getDescProceso());
				aux.setCodImpuesto(gasto.getImpuestoInfo().getCodImpuesto());
				aux.setNomImpuesto(gasto.getImpuestoInfo().getNomImpuesto());
				aux.setPorcentajeImpuesto(gasto.getImpuestoInfo().getPorcentaje());
				aux.setEstadoGasto(gasto.getEstadoGasto());
				lstGastosVO.add(aux);
			}
		
		}
		catch(ObteniendoGastosException e){
			throw e;
		
		} 
		catch (ConexionException e) {
		
			throw e;
		} 
		finally{
			this.pool.liberarConeccion(con);
		}
		
		
		return lstGastosVO;
	}
	
	/**
	* Obtiene todos los cobrables para un proceso
	*/
	@SuppressWarnings("unchecked")
	public ArrayList<SaldoProcesoVO> getSaldosSinAdjuxProceso(String cod_emp, int codProceso) throws ObteniendoSaldosException,  ConexionException
	{
	
		Connection con = null;
		
		ArrayList<SaldoProceso> lst;
		ArrayList<SaldoProcesoVO> lstVO = new ArrayList<SaldoProcesoVO>();
		
		try
		{
			con = this.pool.obtenerConeccion();
			
			lst = this.saldosProceso.getSaldosSinAdjuxProceso(cod_emp, codProceso, con);
			
			
			SaldoProcesoVO aux;
			for (SaldoProceso saldo : lst) 
			{
				aux = new SaldoProcesoVO();
				
				aux.setCodProceso(saldo.getCodProceso());
				aux.setImpTotMN(saldo.getImpTotMN());
				aux.setImpTotMO(saldo.getImpTotMO());
				aux.setCodMoneda(saldo.getMoneda().getCodMoneda());
				aux.setDescMoneda(saldo.getMoneda().getDescripcion());
				aux.setSimboloMoneda(saldo.getMoneda().getSimbolo());
				aux.setNacional(saldo.getMoneda().isNacional());
				
				lstVO.add(aux);
			}
		
		}
		catch(ObteniendoSaldosException e){
			throw e;
		
		} 
		catch (ConexionException e) {
		
			throw e;
		} 
		finally{
			this.pool.liberarConeccion(con);
		}
		
		return lstVO;
	}
	
	
	/**
	* Obtiene todos los gastos existentes por moenda
	*/
	@SuppressWarnings("unchecked")
	public ArrayList<GastoVO> getGastosConSaldoxMoneda(String cod_emp, String codTit, String codMoneda) throws ObteniendoGastosException, ConexionException
	{
	
		Connection con = null;
		
		ArrayList<Gasto> lstGastos;
		ArrayList<GastoVO> lstGastosVO = new ArrayList<GastoVO>();
		
		try
		{
			con = this.pool.obtenerConeccion();
			
			lstGastos = this.gastos.getGastosConSaldoxMoneda(con, cod_emp, codTit, codMoneda);
			
			
			GastoVO aux;
			for (Gasto gasto : lstGastos) 
			{
				aux = new GastoVO();
				
				aux.setFecDoc(gasto.getFecDoc());
				aux.setCodDocum(gasto.getCodDocum());
				aux.setSerieDocum(gasto.getSerieDocum());
				aux.setNroDocum(String.valueOf(gasto.getNroDocum()));
				aux.setCodEmp(gasto.getCodEmp());
				aux.setCodMoneda(gasto.getMoneda().getCodMoneda());
				aux.setNomMoneda(gasto.getMoneda().getDescripcion());
				aux.setReferencia(gasto.getReferencia());
				aux.setCodTitular(gasto.getTitInfo().getCodigo());
				aux.setNomTitular(gasto.getTitInfo().getNombre());
				aux.setNroTrans(gasto.getNroTrans());
				aux.setFecValor(gasto.getFecValor());
				aux.setCodProceso(gasto.getCodProceso());
				aux.setReferencia(gasto.getReferencia());
				aux.setImpImpuMn(gasto.getImpImpuMn());
				aux.setImpImpuMo(gasto.getImpImpuMo());
				aux.setImpSubMn(gasto.getImpSubMn());
				aux.setImpSubMo(gasto.getImpSubMo());
				aux.setImpTotMn(gasto.getImpTotMn());
				aux.setImpTotMo(gasto.getImpTotMo());
				aux.setTcMov(gasto.getTcMov());
				aux.setCodCuenta(gasto.getCuenta().getCodCuenta());
				aux.setNomCuenta(gasto.getCuenta().getNomCuenta());
				aux.setCodRubro(gasto.getRubroInfo().getCodRubro());
				aux.setNomRubro(gasto.getRubroInfo().getNomRubro());
				aux.setCodCtaInd(gasto.getCodCuentaInd());
				aux.setFechaMod(gasto.getFechaMod());
				aux.setUsuarioMod(gasto.getUsuarioMod());
				aux.setOperacion(gasto.getOperacion());
				aux.setDescProceso(gasto.getDescProceso());
				aux.setCodImpuesto(gasto.getImpuestoInfo().getCodImpuesto());
				aux.setNomImpuesto(gasto.getImpuestoInfo().getNomImpuesto());
				aux.setPorcentajeImpuesto(gasto.getImpuestoInfo().getPorcentaje());
				aux.setEstadoGasto(gasto.getEstadoGasto());
				lstGastosVO.add(aux);
			}
		
		}
		catch(ObteniendoGastosException e){
			throw e;
		
		} 
		catch (ConexionException e) {
		
			throw e;
		} 
		finally{
			this.pool.liberarConeccion(con);
		}
		
		return lstGastosVO;
	}

	/**
	* Obtiene todos los gastos con saldo para el titular y empresa 
	*/
	@SuppressWarnings("unchecked")
	public ArrayList<GastoVO> getGastosConSaldo(String cod_emp, String codTit) throws ObteniendoGastosException, ConexionException
	{
	
		Connection con = null;
		
		ArrayList<Gasto> lstGastos;
		ArrayList<GastoVO> lstGastosVO = new ArrayList<GastoVO>();
		
		try
		{
			con = this.pool.obtenerConeccion();
			
			lstGastos = this.gastos.getGastosConSaldo(con, cod_emp, codTit);
			
			
			GastoVO aux;
			for (Gasto gasto : lstGastos) 
			{
				aux = new GastoVO();
				
				aux.setFecDoc(gasto.getFecDoc());
				aux.setCodDocum(gasto.getCodDocum());
				aux.setSerieDocum(gasto.getSerieDocum());
				aux.setNroDocum(String.valueOf(gasto.getNroDocum()));
				aux.setCodEmp(gasto.getCodEmp());
				aux.setCodMoneda(gasto.getMoneda().getCodMoneda());
				aux.setNomMoneda(gasto.getMoneda().getDescripcion());
				aux.setReferencia(gasto.getReferencia());
				aux.setCodTitular(gasto.getTitInfo().getCodigo());
				aux.setNomTitular(gasto.getTitInfo().getNombre());
				aux.setNroTrans(gasto.getNroTrans());
				aux.setFecValor(gasto.getFecValor());
				aux.setCodProceso(gasto.getCodProceso());
				aux.setReferencia(gasto.getReferencia());
				aux.setImpImpuMn(gasto.getImpImpuMn());
				aux.setImpImpuMo(gasto.getImpImpuMo());
				aux.setImpSubMn(gasto.getImpSubMn());
				aux.setImpSubMo(gasto.getImpSubMo());
				aux.setImpTotMn(gasto.getImpTotMn());
				aux.setImpTotMo(gasto.getImpTotMo());
				aux.setTcMov(gasto.getTcMov());
				aux.setCodCuenta(gasto.getCuenta().getCodCuenta());
				aux.setNomCuenta(gasto.getCuenta().getNomCuenta());
				aux.setCodRubro(gasto.getRubroInfo().getCodRubro());
				aux.setNomRubro(gasto.getRubroInfo().getNomRubro());
				aux.setCodCtaInd(gasto.getCodCuentaInd());
				aux.setFechaMod(gasto.getFechaMod());
				aux.setUsuarioMod(gasto.getUsuarioMod());
				aux.setOperacion(gasto.getOperacion());
				aux.setDescProceso(gasto.getDescProceso());
				aux.setCodImpuesto(gasto.getImpuestoInfo().getCodImpuesto());
				aux.setNomImpuesto(gasto.getImpuestoInfo().getNomImpuesto());
				aux.setPorcentajeImpuesto(gasto.getImpuestoInfo().getPorcentaje());
				aux.setSimboloMoneda(gasto.getMoneda().getSimbolo());
				aux.setNacional(gasto.getMoneda().isNacional());
				aux.setEstadoGasto(gasto.getEstadoGasto());
				lstGastosVO.add(aux);
			}
		
		}
		catch(ObteniendoGastosException e){
			throw e;
		
		} 
		catch (ConexionException e) {
		
			throw e;
		} 
		finally{
			this.pool.liberarConeccion(con);
		}
		
		return lstGastosVO;
	}
	
	/**
	* Obtiene todos los gastos con saldo cobrables para el titular y empresa 
	*/
	@SuppressWarnings("unchecked")
	public ArrayList<GastoVO> getGastosConSaldoCobrable(String cod_emp, String codTit) throws ObteniendoGastosException, ConexionException
	{
	
		Connection con = null;
		
		ArrayList<Gasto> lstGastos;
		ArrayList<GastoVO> lstGastosVO = new ArrayList<GastoVO>();
		
		try
		{
			con = this.pool.obtenerConeccion();
			
			lstGastos = this.gastos.getGastosConSaldoCobrable(con, cod_emp, codTit);
			
			
			GastoVO aux;
			for (Gasto gasto : lstGastos) 
			{
				aux = new GastoVO();
				
				aux.setFecDoc(gasto.getFecDoc());
				aux.setCodDocum(gasto.getCodDocum());
				aux.setSerieDocum(gasto.getSerieDocum());
				aux.setNroDocum(String.valueOf(gasto.getNroDocum()));
				aux.setCodEmp(gasto.getCodEmp());
				aux.setCodMoneda(gasto.getMoneda().getCodMoneda());
				aux.setNomMoneda(gasto.getMoneda().getDescripcion());
				aux.setReferencia(gasto.getReferencia());
				aux.setCodTitular(gasto.getTitInfo().getCodigo());
				aux.setNomTitular(gasto.getTitInfo().getNombre());
				aux.setNroTrans(gasto.getNroTrans());
				aux.setFecValor(gasto.getFecValor());
				aux.setCodProceso(gasto.getCodProceso());
				aux.setReferencia(gasto.getReferencia());
				aux.setImpImpuMn(gasto.getImpImpuMn());
				aux.setImpImpuMo(gasto.getImpImpuMo());
				aux.setImpSubMn(gasto.getImpSubMn());
				aux.setImpSubMo(gasto.getImpSubMo());
				aux.setImpTotMn(gasto.getImpTotMn());
				aux.setImpTotMo(gasto.getImpTotMo());
				aux.setTcMov(gasto.getTcMov());
				aux.setCodCuenta(gasto.getCuenta().getCodCuenta());
				aux.setNomCuenta(gasto.getCuenta().getNomCuenta());
				aux.setCodRubro(gasto.getRubroInfo().getCodRubro());
				aux.setNomRubro(gasto.getRubroInfo().getNomRubro());
				aux.setCodCtaInd(gasto.getCodCuentaInd());
				aux.setFechaMod(gasto.getFechaMod());
				aux.setUsuarioMod(gasto.getUsuarioMod());
				aux.setOperacion(gasto.getOperacion());
				aux.setDescProceso(gasto.getDescProceso());
				aux.setCodImpuesto(gasto.getImpuestoInfo().getCodImpuesto());
				aux.setNomImpuesto(gasto.getImpuestoInfo().getNomImpuesto());
				aux.setPorcentajeImpuesto(gasto.getImpuestoInfo().getPorcentaje());
				aux.setSimboloMoneda(gasto.getMoneda().getSimbolo());
				aux.setNacional(gasto.getMoneda().isNacional());
				aux.setEstadoGasto(gasto.getEstadoGasto());
				lstGastosVO.add(aux);
			}
		
		}
		catch(ObteniendoGastosException e){
			throw e;
		
		} 
		catch (ConexionException e) {
		
			throw e;
		} 
		finally{
			this.pool.liberarConeccion(con);
		}
		
		return lstGastosVO;
	}
	
	/**
	* Obtiene todos los gastos facturables con saldo para el titular y empresas
	*/
	@SuppressWarnings("unchecked")
	public ArrayList<GastoVO> getGastosFacturablesxProcesoConSaldo(String cod_emp, String codTit, int codProceso) throws ObteniendoGastosException, ConexionException
	{
	
		Connection con = null;
		
		ArrayList<Gasto> lstGastos;
		ArrayList<GastoVO> lstGastosVO = new ArrayList<GastoVO>();
		
		try
		{
			con = this.pool.obtenerConeccion();
			
			lstGastos = this.gastos.getGastosFacturablesxProcesoConSaldo(con, cod_emp, codTit, codProceso);
			
			
			GastoVO aux;
			for (Gasto gasto : lstGastos) 
			{
				aux = new GastoVO();
				
				aux.setFecDoc(gasto.getFecDoc());
				aux.setCodDocum(gasto.getCodDocum());
				aux.setSerieDocum(gasto.getSerieDocum());
				aux.setNroDocum(String.valueOf(gasto.getNroDocum()));
				aux.setCodEmp(gasto.getCodEmp());
				aux.setCodMoneda(gasto.getMoneda().getCodMoneda());
				aux.setNomMoneda(gasto.getMoneda().getDescripcion());
				aux.setReferencia(gasto.getReferencia());
				aux.setCodTitular(gasto.getTitInfo().getCodigo());
				aux.setNomTitular(gasto.getTitInfo().getNombre());
				aux.setNroTrans(gasto.getNroTrans());
				aux.setFecValor(gasto.getFecValor());
				aux.setCodProceso(gasto.getCodProceso());
				aux.setReferencia(gasto.getReferencia());
				aux.setImpImpuMn(gasto.getImpImpuMn());
				aux.setImpImpuMo(gasto.getImpImpuMo());
				aux.setImpSubMn(gasto.getImpSubMn());
				aux.setImpSubMo(gasto.getImpSubMo());
				aux.setImpTotMn(gasto.getImpTotMn());
				aux.setImpTotMo(gasto.getImpTotMo());
				aux.setTcMov(gasto.getTcMov());
				aux.setCodCuenta(gasto.getCuenta().getCodCuenta());
				aux.setNomCuenta(gasto.getCuenta().getNomCuenta());
				aux.setCodRubro(gasto.getRubroInfo().getCodRubro());
				aux.setNomRubro(gasto.getRubroInfo().getNomRubro());
				aux.setCodCtaInd(gasto.getCodCuentaInd());
				aux.setFechaMod(gasto.getFechaMod());
				aux.setUsuarioMod(gasto.getUsuarioMod());
				aux.setOperacion(gasto.getOperacion());
				aux.setDescProceso(gasto.getDescProceso());
				aux.setCodImpuesto(gasto.getImpuestoInfo().getCodImpuesto());
				aux.setNomImpuesto(gasto.getImpuestoInfo().getNomImpuesto());
				aux.setPorcentajeImpuesto(gasto.getImpuestoInfo().getPorcentaje());
				aux.setSimboloMoneda(gasto.getMoneda().getSimbolo());
				aux.setNacional(gasto.getMoneda().isNacional());
				aux.setEstadoGasto(gasto.getEstadoGasto());
				lstGastosVO.add(aux);
			}
		
		}
		catch(ObteniendoGastosException e){
			throw e;
		
		} 
		catch (ConexionException e) {
		
			throw e;
		} 
		finally{
			this.pool.liberarConeccion(con);
		}
		
		return lstGastosVO;
	}
	
	/**
	* Obtiene todos los gastos facturables con saldo para el titular y empresas
	*/
	@SuppressWarnings("unchecked")
	public ArrayList<GastoVO> getGastosFacturablesxProcesoConSaldoxMoneda(String cod_emp, String codTit, int codProceso, String codMoneda) throws ObteniendoGastosException, ConexionException
	{
	
		Connection con = null;
		
		ArrayList<Gasto> lstGastos;
		ArrayList<GastoVO> lstGastosVO = new ArrayList<GastoVO>();
		
		try
		{
			con = this.pool.obtenerConeccion();
			
			lstGastos = this.gastos.getGastosFacturablesxProcesoConSaldoxMoneda(con, cod_emp, codTit, codProceso, codMoneda);
			
			
			GastoVO aux;
			for (Gasto gasto : lstGastos) 
			{
				aux = new GastoVO();
				
				aux.setFecDoc(gasto.getFecDoc());
				aux.setCodDocum(gasto.getCodDocum());
				aux.setSerieDocum(gasto.getSerieDocum());
				aux.setNroDocum(String.valueOf(gasto.getNroDocum()));
				aux.setCodEmp(gasto.getCodEmp());
				aux.setCodMoneda(gasto.getMoneda().getCodMoneda());
				aux.setNomMoneda(gasto.getMoneda().getDescripcion());
				aux.setReferencia(gasto.getReferencia());
				aux.setCodTitular(gasto.getTitInfo().getCodigo());
				aux.setNomTitular(gasto.getTitInfo().getNombre());
				aux.setNroTrans(gasto.getNroTrans());
				aux.setFecValor(gasto.getFecValor());
				aux.setCodProceso(gasto.getCodProceso());
				aux.setReferencia(gasto.getReferencia());
				aux.setImpImpuMn(gasto.getImpImpuMn());
				aux.setImpImpuMo(gasto.getImpImpuMo());
				aux.setImpSubMn(gasto.getImpSubMn());
				aux.setImpSubMo(gasto.getImpSubMo());
				aux.setImpTotMn(gasto.getImpTotMn());
				aux.setImpTotMo(gasto.getImpTotMo());
				aux.setTcMov(gasto.getTcMov());
				aux.setCodCuenta(gasto.getCuenta().getCodCuenta());
				aux.setNomCuenta(gasto.getCuenta().getNomCuenta());
				aux.setCodRubro(gasto.getRubroInfo().getCodRubro());
				aux.setNomRubro(gasto.getRubroInfo().getNomRubro());
				aux.setCodCtaInd(gasto.getCodCuentaInd());
				aux.setFechaMod(gasto.getFechaMod());
				aux.setUsuarioMod(gasto.getUsuarioMod());
				aux.setOperacion(gasto.getOperacion());
				aux.setDescProceso(gasto.getDescProceso());
				aux.setCodImpuesto(gasto.getImpuestoInfo().getCodImpuesto());
				aux.setNomImpuesto(gasto.getImpuestoInfo().getNomImpuesto());
				aux.setPorcentajeImpuesto(gasto.getImpuestoInfo().getPorcentaje());
				aux.setSimboloMoneda(gasto.getMoneda().getSimbolo());
				aux.setNacional(gasto.getMoneda().isNacional());
				aux.setEstadoGasto(gasto.getEstadoGasto());
				lstGastosVO.add(aux);
			}
		
		}
		catch(ObteniendoGastosException e){
			throw e;
		
		} 
		catch (ConexionException e) {
		
			throw e;
		} 
		finally{
			this.pool.liberarConeccion(con);
		}
		
		return lstGastosVO;
	}

	/**
	* Inserta un nuevo gasto en la base
	*/
	public NumeradoresVO insertarGasto(GastoVO gastoVO, String cod_emp) throws IngresandoGastoException, ConexionException, ExisteGastoException 
	{
	
		Connection con = null;
		boolean existe = false;
		Integer codigo;
		NumeradoresVO codigos = new NumeradoresVO();
		
		try 
		{
			con = this.pool.obtenerConeccion();
			con.setAutoCommit(false);
			
			Gasto gasto = new Gasto(gastoVO); 
			
			//Obtengo numerador de gastos
			codigos.setCodigo(numeradores.getNumero(con, "02", cod_emp)); //Gasto
			
			//Obtengo numerador de la transacción
			codigos.setNumeroTrans(numeradores.getNroTrans(con, "03")); //Transacción
			
			//Seteo los numeradores obtenidos
			gasto.setNroDocum(codigos.getCodigo());
			gasto.setNroTrans(codigos.getNumeroTrans());
			
			//Inserto el gasto
			this.gastos.insertarGasto(gasto, cod_emp, con);
			
			//Genero saldo del gasto insertado
			this.saldos.insertarSaldo((DocumDetalle) gasto, con);
			
			//Genero log de documento
			this.logDocum = new DocLog();
			logDocum.setCod_docum(gasto.getCodDocum());
			logDocum.setSerie_docum(gasto.getSerieDocum());
			logDocum.setNro_docum(gasto.getNroDocum());
			logDocum.setCod_doca(gasto.getCodDocum());
			logDocum.setSerie_doca(gasto.getSerieDocum());
			logDocum.setNro_doca(gasto.getNroDocum());
			logDocum.setCod_doc_ref(gasto.getCodDocum());
			logDocum.setSerie_doc_ref(gasto.getSerieDocum());
			logDocum.setNro_doc_ref(gasto.getNroDocum());
			logDocum.setCod_tit(gasto.getTitInfo().getCodigo());
			logDocum.setNro_trans(gasto.getNroTrans());
			logDocum.setCod_moneda(gasto.getMoneda().getCodMoneda());
			logDocum.setImp_tot_mn(gasto.getImpTotMn());
			logDocum.setImp_tot_mo(gasto.getImpTotMo());
			logDocum.setCuenta(gasto.getCodCuentaInd());
			logDocum.setFechaMod(gasto.getFechaMod());
			logDocum.setUsuarioMod(gasto.getUsuarioMod());
			logDocum.setOperacion(gasto.getOperacion());
			logDocum.setLinea(1);
			
			this.logsDocumentos.insertarDocLog(logDocum, cod_emp, con);
			
			con.commit();
			return codigos;
		
		}
		catch(Exception IngresandoProcesoException)  	{
			try {
				con.rollback();
			
			} 
			catch (SQLException e) {
		
				throw new IngresandoGastoException();
			}
		
			throw new IngresandoGastoException();
		}
		finally{
			pool.liberarConeccion(con);
		}
	}

	/**
	* Actualiza los datos de un gasto dado su código y la empresa
	* valida que exista el código 
	 * @throws IngresandoGastoException 
	 * @throws IngresandoSaldoException 
	 * @throws EliminandoSaldoException 
	 * @throws ModificandoSaldoException 
	 * @throws InsertandoLogException 
	 * @throws ModificandoLogException 
	 * @throws ExisteSaldoException 
	*/
	public void actualizarGasto(GastoVO gastoVO, String cod_emp) throws ConexionException, ModificandoGastoException, NoExisteGastoException, ExisteGastoException, IngresandoGastoException, ModificandoSaldoException, EliminandoSaldoException, IngresandoSaldoException, InsertandoLogException, ModificandoLogException, ExisteSaldoException  
	{
	
		Connection con = null;
		
		try 
		{
			con = this.pool.obtenerConeccion();
			con.setAutoCommit(false);
			
			Gasto gasto = new Gasto(gastoVO);
			
			if(this.gastos.memberGasto(gasto.getNroTrans(), cod_emp, con)){
				
				//Modifico gasto
				this.gastos.modificarGasto(gasto, cod_emp, con);
				this.saldos.modificarSaldoSinSA((DocumDetalle) gasto, con);
				
				//Genero log de documento
				this.logDocum = new DocLog();
				logDocum.setCod_docum(gasto.getCodDocum());
				logDocum.setSerie_docum(gasto.getSerieDocum());
				logDocum.setNro_docum(gasto.getNroDocum());
				logDocum.setCod_doca(gasto.getCodDocum());
				logDocum.setSerie_doca(gasto.getSerieDocum());
				logDocum.setNro_doca(gasto.getNroDocum());
				logDocum.setCod_doc_ref(gasto.getCodDocum());
				logDocum.setSerie_doc_ref(gasto.getSerieDocum());
				logDocum.setNro_doc_ref(gasto.getNroDocum());
				logDocum.setCod_tit(gasto.getTitInfo().getCodigo());
				logDocum.setNro_trans(gasto.getNroTrans());
				logDocum.setCod_moneda(gasto.getMoneda().getCodMoneda());
				logDocum.setImp_tot_mn(gasto.getImpTotMn());
				logDocum.setImp_tot_mo(gasto.getImpTotMo());
				logDocum.setCuenta(gasto.getCodCuentaInd());
				logDocum.setFechaMod(gasto.getFechaMod());
				logDocum.setUsuarioMod(gasto.getUsuarioMod());
				logDocum.setOperacion(gasto.getOperacion());
				logDocum.setLinea(1);
				
				this.logsDocumentos.modificarDocLog(logDocum, cod_emp, con);
				con.commit();
			}
			else
				throw new NoExisteGastoException();
			
		}
		catch(NoExisteGastoException| ConexionException | SQLException | ModificandoGastoException e){
			try {
			con.rollback();
			
			} 
			catch (SQLException e1) {
		
				throw new ConexionException();
			}
			throw new ModificandoGastoException();
		}
		finally{
			pool.liberarConeccion(con);
		}
	}
	
	/**
   	 * Elimina un gasto
   	 * valida que exista el código 
	 * @throws EliminandoGastoException 
	 * @throws ExisteGastoException 
	 * @throws ConexionException 
	 * @throws EliminandoProcesoException 
	 * @throws EliminandoSaldoException 
   	 */
    public void eliminarGasto(GastoVO gastoVO, String cod_emp) throws ExisteGastoException, EliminandoGastoException, ConexionException, EliminandoProcesoException, EliminandoSaldoException 
   	{
   	    	
       	Connection con = null;
       	
       	try 
       	{
       		Gasto gasto = new Gasto(gastoVO);
       		con = this.pool.obtenerConeccion();
   			con.setAutoCommit(false);
       		if(this.gastos.memberGasto(gastoVO.getNroTrans(), cod_emp, con)){
       			this.gastos.eliminarGasto(gastoVO.getNroTrans(), cod_emp, con);
       			this.saldos.eliminarSaldo((DocumDetalle) gasto, con);
       			con.commit();
       		}
   			
   	    	else
   	    		throw new NoExisteGastoException();
       	
       	}
       	catch(NoExisteGastoException| ConexionException | ExisteGastoException |SQLException e){
       		try {
   				con.rollback();
   				
   			} 
       		catch (SQLException e1) {
   				
   				throw new ConexionException();
   			}
       		throw new EliminandoGastoException();
       	}
       	finally
       	{
       		pool.liberarConeccion(con);
       	}
   	}
/////////////////////////////////FIN-GASTOS/////////////////////////////////
    
/////////////////////////////////INI-TITULARES/////////////////////////////////
    
    public ArrayList<TitularVO> getTitularesActivos(String codEmp) throws ObteniendoTitularesException, ConexionException {
    	
    	Connection con = null;
    	
    	ArrayList<Titular> lstTitulares;
    	ArrayList<TitularVO> lstTitularesVO = new ArrayList<TitularVO>();
    	    	
    	try
    	{
    		con = this.pool.obtenerConeccion();
    		
    		lstTitulares = this.titulares.getTitularesActivos(con, codEmp);
    		
    		
    		TitularVO aux;
    		for (Titular titular : lstTitulares) 
			{
    			aux = new TitularVO();
    			
    			aux = titular.retornarTitularVO();

    			lstTitularesVO.add(aux);
			}
	
    	}catch(ObteniendoTitularesException  e)
    	{
    		throw e;
    		
    	} catch (ConexionException e) {
			
    		throw e;
    	} 
    	finally
    	{
    		this.pool.liberarConeccion(con);
    	}
    	    
    	
    	return lstTitularesVO;
    }	 
    
    public ArrayList<TitularVO> getTitularesActivosFuncionarios(String codEmp) throws ObteniendoTitularesException, ConexionException {
    	
    	Connection con = null;
    	
    	ArrayList<Titular> lstTitulares;
    	ArrayList<TitularVO> lstTitularesVO = new ArrayList<TitularVO>();
    	    	
    	try
    	{
    		con = this.pool.obtenerConeccion();
    		
    		lstTitulares = this.titulares.getTitularesActivosFuncioanrios(con, codEmp);
    		
    		
    		TitularVO aux;
    		for (Titular titular : lstTitulares) 
			{
    			aux = new TitularVO();
    			
    			aux = titular.retornarTitularVO();

    			lstTitularesVO.add(aux);
			}
	
    	}catch(ObteniendoTitularesException  e)
    	{
    		throw e;
    		
    	} catch (ConexionException e) {
			
    		throw e;
    	} 
    	finally
    	{
    		this.pool.liberarConeccion(con);
    	}
    	    
    	
    	return lstTitularesVO;
    }	 
/////////////////////////////////FNI-TITULARES/////////////////////////////////
    
/////////////////////////////////INI-PERÍODO/////////////////////////////////
 
   /**
    * Obtiene todos los períodos existentes
    */
   @SuppressWarnings("unchecked")
   public ArrayList<PeriodoVO> getPeriodos(String codEmp) throws ObteniendoPeriodosException, ConexionException
   {
   	
	   Connection con = null;
	   ArrayList<Periodo> lstPeriodos;
	   ArrayList<PeriodoVO> lstPeriodosVO = new ArrayList<PeriodoVO>();
   	
	   try 	
	   {
		   con = this.pool.obtenerConeccion();
		   lstPeriodos = this.periodo.getPeriodo(codEmp, con);
   		
		   PeriodoVO aux;
   		
		   for (Periodo periodo : lstPeriodos) 
		   {
			   aux = new PeriodoVO();
			   
			   aux.setMes(periodo.getMes());
			   aux.setAnio(periodo.getAnio().toString());
			   aux.setAbierto(periodo.getAbierto());
			   aux.setFechaMod(periodo.getFechaMod());
			   aux.setOperacion(periodo.getOperacion());
			   aux.setUsuarioMod(periodo.getUsuarioMod());
			   
			   lstPeriodosVO.add(aux);
		   }
	   }
   	
	   catch(ObteniendoPeriodosException e){
		   throw e;
	   } 
   	
	   catch (ConexionException e) {
		   throw e;
	   } 
   	
	   finally{
		   this.pool.liberarConeccion(con);
	   }
	   
	   return lstPeriodosVO;
   
   }
       
       
   /**
   	 * Inserta un período en la base
 * @throws ExistePeriodoException 
        * @throws ExisteNacional 
   	 */
       
   public void insertarPeriodo(PeriodoVO periodoVO, String codEmp) throws InsertandoPeriodoException, ConexionException, ExistePeriodoException 
   {

	   Connection con = null;
	   boolean existe = false; 
			   
	   try 
	   {
		   con = this.pool.obtenerConeccion();
		   con.setAutoCommit(false);

		   Periodo periodo = new Periodo(periodoVO); 

		   if(!this.periodo.memberPeriodo(periodo.getMes(), periodo.getAnio(), codEmp, con)){
			   this.periodo.insertarPeriodo(periodo, codEmp, con);
			   con.commit();
		   }
		   else{
			   existe = true;
		   }


	   }
	   catch(Exception InsertandoPeriodoException)  	{
		   try {
			   con.rollback();

		   } catch (SQLException e) {

			   throw new InsertandoPeriodoException();
		   }

		   throw new InsertandoPeriodoException();
	   }
	   finally
	   {
		   pool.liberarConeccion(con);
	   }
	   if (existe){
		   throw new ExistePeriodoException();
	   }
   }

       /**
   	 * Actualiza los datos de una moneda dado su código
   	 * valida que exista el código 
        * @throws ExisteNacional 
   	 */
   public void actualizarPeriodo(PeriodoVO periodoVO, String codEmp) throws ConexionException, NoExistePeriodoException, ModificandoPeriodoException, ExistePeriodoException  
   {

	   Connection con = null;
	   boolean existeNacional = false;

	   try 
	   {
		   con = this.pool.obtenerConeccion();
		   con.setAutoCommit(false);

		   Periodo periodo = new Periodo(periodoVO);

		   if(this.periodo.memberPeriodo(periodo.getMes(), periodo.getAnio(), codEmp, con)){
			   this.periodo.actualizarPeriodo(periodo, codEmp, con);
			   con.commit();
		   }

		   else
			   throw new NoExistePeriodoException();

	   }catch(NoExistePeriodoException| ConexionException | SQLException | ModificandoPeriodoException e)
	   {
		   try {
			   con.rollback();

		   } 
		   catch (SQLException e1) {

			   throw new ConexionException();
		   }
		   throw new ModificandoPeriodoException();
	   }
	   finally
	   {
		   pool.liberarConeccion(con);
	   }
   }
   
   public boolean validaPeriodo(String mes, int anio, String codEmp) throws ExistePeriodoException, ConexionException, SQLException, NoExistePeriodoException{
	   
	   Boolean abierto = false;
	   Connection con = null;
	   try {
		   
		   con = this.pool.obtenerConeccion();
		   con.setAutoCommit(false);
		   abierto = this.periodo.validaPeriodo(mes, anio, codEmp, con);
		
	   } catch(ConexionException | SQLException | NoExistePeriodoException e)
	   {
		   try {
			   con.rollback();

		   } 
		   catch (SQLException e1) {

			   throw new ConexionException();
		   }
		   
	   }
	   finally
	   {
		   pool.liberarConeccion(con);
	   }
	  
	   return abierto;
   }
       
/////////////////////////////////INI-PERÍODO/////////////////////////////////
   
/////////////////////////////////INI-DEPOSITOS/////////////////////////////////
   
	/**
	* Obtiene todos los cheques a depositar 
	 * @throws ObteniendoBancosException 
	 * @throws ObteniendoCuentasBcoException 
	*/
	@SuppressWarnings("unchecked")
	public ArrayList<DepositoDetalleVO> getChequesBanco(String codEmp, String codMoneda) throws ObteniendoChequeException, ConexionException, ObteniendoCuentasBcoException, ObteniendoBancosException
	{

		Connection con = null;
		ArrayList<DepositoDetalle> lstDepositos;
		ArrayList<DepositoDetalleVO> lstDepositosVO = new ArrayList<DepositoDetalleVO>();

		try 	
		{
			con = this.pool.obtenerConeccion();
			lstDepositos = this.cheques.getChequesBanco(con, codEmp, codMoneda);
			
			DepositoDetalleVO aux;
			
			for (DepositoDetalle deposito : lstDepositos) 
			{
				aux = new DepositoDetalleVO();
				
				aux = deposito.retornarDepositoDetalleVO(deposito);
				
				lstDepositosVO.add(aux);
			}
		}
		
		catch (ConexionException e) {
			throw e;
		}	 

		finally{
			this.pool.liberarConeccion(con);
		}

		return lstDepositosVO;

	}
	
	@SuppressWarnings("unchecked") 
	public ArrayList<DepositoVO> getDepositos(String codEmp, Timestamp inicio, Timestamp fin ) throws ObteniendoDepositoException, ConexionException {
		
		Connection con = null;
		
		ArrayList<Deposito> lst;
		ArrayList<DepositoVO> lstVO = new ArrayList<DepositoVO>();
		
		try
		{
			con = this.pool.obtenerConeccion();
			
			lst = this.depositos.getDepositosTodos(con, codEmp, inicio, fin);
			
			for (Deposito deposito : lst) 
			{
				DepositoVO aux = deposito.getDepositoVO(deposito);
				
				lstVO.add(aux);
			}
		
		}catch(ObteniendoDepositoException  e)
		{
			throw e;
		
		} catch (ConexionException e) {
		
			throw e;
		} 
		finally
		{
			this.pool.liberarConeccion(con);
		}
		
		
		return lstVO;
	}	 
	
	public Integer insertarDeposito(DepositoVO depVO, String codEmp) throws InsertandoDepositoException, ConexionException, ExisteDepositoException{

		Connection con = null;
		boolean existe = false;
		Integer codigo;
		NumeradoresVO codigos = new NumeradoresVO();


    	try 
    	{
    		con = this.pool.obtenerConeccion();
			con.setAutoCommit(false);
			
			Deposito deposito = new Deposito();
			DatosDocum datos;
			DocumSaldo documSaldo;
			TitularInfo tit;
			deposito = deposito.convierteDeposito(depVO);

			//Obtengo numerador de la transacción
			codigo = numeradores.getNroTrans(con, "03"); //Transacción
			deposito.setNroTrans(codigo);
			
			if(!this.depositos.memberDeposito(deposito.getNroTrans(), codEmp, con)){
	    		
				//Inerto el depósito
	    		this.depositos.insertarDeposito(deposito, con, codEmp);
	    		
	    		
	    		documSaldo = new DocumSaldo();
				documSaldo.setCodBco(deposito.getBanco().getCodBanco());
				documSaldo.setCodCtaBco(deposito.getCuentaBanco().getCodCuenta());
				documSaldo.setMovimiento("Deposito");
				documSaldo.setSigno(1);
				documSaldo.setFecDoc(deposito.getFecDoc());
				documSaldo.setFecValor(deposito.getFecValor());
				documSaldo.setCodDocum(deposito.getCodDocum());
				documSaldo.setSerieDocum(deposito.getSerieDocum());
				documSaldo.setNroDocum(deposito.getNroDocum());
				documSaldo.setCodEmp(codEmp);
				documSaldo.setMoneda(deposito.getMoneda());
				documSaldo.setReferencia(deposito.getObservaciones());
				tit = new TitularInfo();
				tit.setCodigo(String.valueOf(deposito.getFuncionario().getCodigo()));
				tit.setNombre(deposito.getFuncionario().getNombre());
				documSaldo.setTitInfo(tit);
				documSaldo.setNroTrans(deposito.getNroTrans());
				documSaldo.setImpTotMn(deposito.getImpTotMn());
				documSaldo.setImpTotMo(deposito.getImpTotMo());
				documSaldo.setTcMov(deposito.getTcMov());
				documSaldo.setUsuarioMod(deposito.getUsuarioMod());
				documSaldo.setOperacion(deposito.getOperacion());
				documSaldo.setCodDocumRef(deposito.getCodDocum());
				documSaldo.setSerieDocumRef(deposito.getSerieDocum());
				documSaldo.setNroDocumRef(deposito.getNroDocum());
				
				//Muevo la cuenta del banco para el depósito 
				saldosCuentas.insertarSaldoCuenta(documSaldo, con);
	    		
				
	    		int linea = 1;
				for (DepositoDetalle lin : deposito.getLstDetalle()) {
					
				
					/*A cada linea le seteamos el nroTrans*/
					lin.setNroTrans(deposito.getNroTrans());
					
					//Inserto el detalle del depósito
					depositos.insertarDepositoDetalle(lin, linea, con, codEmp);
					
					datos = new DatosDocum();
					datos.setCodDocum(lin.getCheque().getCodDocum());
					datos.setSerieDocum(lin.getCheque().getSerieDocum());
					datos.setNroDocum(lin.getCheque().getNroDocum());
					datos.setCodEmp(codEmp);
					datos.setMoneda(lin.getCheque().getMoneda());
					datos.setImpTotMn(0);
					datos.setImpTotMo(0);
					tit = new TitularInfo();
					tit.setCodigo(lin.getCheque().getTitInfo().getCodigo());
					datos.setTitInfo(tit);	
					datos.setCodEmp(codEmp);
					
					//Modifico los saldos de los cheques (dejo en cero)
					this.saldos.modificarSaldoImporte(datos, con);
					
					
					linea++;
				}
				
	    		con.commit();
	    		
	    	}
	    	else{
	    		existe = true;
	    	}
	    		
    	
    	}
    	catch(Exception InsertandoImpuestoException)  	{
    		try {
				con.rollback();
				
			} catch (SQLException e) {
				
				throw new InsertandoDepositoException();
			}
    		
    		throw new InsertandoDepositoException();
    	}
    	finally
    	{
    		pool.liberarConeccion(con);
    	}
    	if (existe){
    		throw new ExisteDepositoException();
    	}
		return codigo;
	}
	
	public void modificarDeposito(DepositoVO depositoVO, String codEmp) throws  ConexionException, ModificandoDepositoException, ExisteDepositoException, NoExisteDepositoException, ModificandoIngresoCobroException, InsertandoDepositoException, EliminandoDepositoException{

		Connection con = null;
		
		try 
		{
			con = this.pool.obtenerConeccion();
			con.setAutoCommit(false);
			
			Deposito deposito = new Deposito();
			deposito = deposito.convierteDeposito(depositoVO);
			
			/*Verificamos que exista el nro de cobro*/
			if(this.depositos.memberDeposito(deposito.getNroTrans(), codEmp, con)){
				
				this.eliminarDepositoModificacion(deposito, codEmp, con);
				this.insertarDepositoModificacion(deposito, codEmp, con);
				con.commit();
			}
				
				
			else
			throw new ModificandoDepositoException();
		
		}catch(ExisteDepositoException| ConexionException | SQLException  e)
		{
			try {
			con.rollback();
			
			} catch (SQLException e1) {
			
			throw new ConexionException();
			}
				throw new ModificandoDepositoException();
		}
		finally
		{
			pool.liberarConeccion(con);
		}
	}
	
	public void eliminarDeposito(DepositoVO depositoVO, String codEmp) throws  ConexionException, EliminandoDepositoException, ExisteDepositoException, NoExisteDepositoException, InsertandoDepositoException, EliminandoSaldoCuetaException, ModificandoSaldoException, ExisteSaldoException{

		Connection con = null;
		
		try 
		{
			con = this.pool.obtenerConeccion();
			con.setAutoCommit(false);
			
			Deposito deposito = new Deposito();
			DatosDocum datos;
			DocumSaldo documSaldo;
			TitularInfo tit;
			
			deposito = deposito.convierteDeposito(depositoVO);
			
			/*Verificamos que exista el nro de cobro*/
			if(this.depositos.memberDeposito(deposito.getNroTrans(), codEmp, con)){
				
				documSaldo = new DocumSaldo();
				documSaldo.setCodBco(deposito.getBanco().getCodBanco());
				documSaldo.setCodCtaBco(deposito.getCuentaBanco().getCodCuenta());
				documSaldo.setMovimiento("Deposito");
				documSaldo.setSigno(1);
				documSaldo.setFecDoc(deposito.getFecDoc());
				documSaldo.setFecValor(deposito.getFecValor());
				documSaldo.setCodDocum(deposito.getCodDocum());
				documSaldo.setSerieDocum(deposito.getSerieDocum());
				documSaldo.setNroDocum(deposito.getNroDocum());
				documSaldo.setCodEmp(codEmp);
				documSaldo.setMoneda(deposito.getMoneda());
				documSaldo.setReferencia(deposito.getObservaciones());
				tit = new TitularInfo();
				tit.setCodigo(String.valueOf(deposito.getFuncionario().getCodigo()));
				tit.setNombre(deposito.getFuncionario().getNombre());
				documSaldo.setTitInfo(tit);
				documSaldo.setNroTrans(deposito.getNroTrans());
				documSaldo.setImpTotMn(deposito.getImpTotMn());
				documSaldo.setImpTotMo(deposito.getImpTotMo());
				documSaldo.setTcMov(deposito.getTcMov());
				documSaldo.setUsuarioMod(deposito.getUsuarioMod());
				documSaldo.setOperacion(deposito.getOperacion());
				documSaldo.setCodDocumRef(deposito.getCodDocum());
				documSaldo.setSerieDocumRef(deposito.getSerieDocum());
				documSaldo.setNroDocumRef(deposito.getNroDocum());
				
				//Muevo la cuenta del banco para el depósito 
				saldosCuentas.eliminarSaldoCuenta(documSaldo, con);
	    		
				this.depositos.eliminarDeposito(deposito, con, codEmp);
				
				for (DepositoDetalle lin : deposito.getLstDetalle()) {
					
				
					/*A cada linea le seteamos el nroTrans*/
					lin.setNroTrans(deposito.getNroTrans());
					
					//Inserto el detalle del depósito
					depositos.eliminarDepositoDetalle(deposito, con, codEmp);
					
					datos = new DatosDocum();
					datos.setCodDocum(lin.getCheque().getCodDocum());
					datos.setSerieDocum(lin.getCheque().getSerieDocum());
					datos.setNroDocum(lin.getCheque().getNroDocum());
					datos.setCodEmp(codEmp);
					datos.setMoneda(lin.getCheque().getMoneda());
					datos.setImpTotMn(lin.getCheque().getImpTotMn());
					datos.setImpTotMo(lin.getCheque().getImpTotMo());
					tit = new TitularInfo();
					tit.setCodigo(lin.getCheque().getTitInfo().getCodigo());
					datos.setTitInfo(tit);	
					datos.setCodEmp(codEmp);
					
					//Modifico los saldos de los cheques (dejo en cero)
					this.saldos.modificarSaldoImporte(datos, con);
				}
				
	    		con.commit();
			}
				
				
			else{
				throw new NoExisteDepositoException();
			}
		
		}
		catch(ExisteDepositoException| ConexionException | SQLException  e){
			
			try {
				con.rollback();
			
			} 
			catch (SQLException e1) {
			
				throw new ConexionException();
			}
		}
		finally
		{
			pool.liberarConeccion(con);
		}
	}
	
	public void insertarDepositoModificacion(Deposito dep, String codEmp, Connection con) throws InsertandoDepositoException, ConexionException, ExisteDepositoException, ModificandoDepositoException{

		boolean existe = false;

    	try 
    	{
			if(!this.depositos.memberDeposito(dep.getNroTrans(), codEmp, con)){
	    		
	    		this.depositos.insertarDeposito(dep, con, codEmp);
	    		
	    		int linea = 1;
				for (DepositoDetalle lin : dep.getLstDetalle()) {
					
				
					/*A cada linea le seteamos el nroTrans*/
					lin.setNroTrans(dep.getNroTrans());
					
					//Inserto el detalle del depósito
					depositos.insertarDepositoDetalle(lin, linea, con, codEmp);
					
					linea++;
				}
	    	}
	    	else{
	    		existe = true;
	    	}
	    		
    	
    	}
    	catch(Exception ModificandoDepositoException)  	{
    		
    		throw new ModificandoDepositoException();
    	}
    	if (existe){
    		throw new ExisteDepositoException();
    	}
	}
	
	public void eliminarDepositoModificacion(Deposito deposito, String codEmp, Connection con) throws  ConexionException, EliminandoDepositoException, ExisteDepositoException, NoExisteDepositoException, InsertandoDepositoException, ModificandoDepositoException{

		try 
		{
			/*Verificamos que exista el nro de cobro*/
			if(this.depositos.memberDeposito(deposito.getNroTrans(), codEmp, con)){
				
				this.depositos.eliminarDeposito(deposito, con, codEmp);
				
				for (DepositoDetalle lin : deposito.getLstDetalle()) {
					
					
					/*A cada linea le seteamos el nroTrans*/
					lin.setNroTrans(deposito.getNroTrans());
					
					//Inserto el detalle del depósito
					depositos.eliminarDepositoDetalle(deposito, con, codEmp);
					
				}
			}
				
				
			else{
				throw new NoExisteDepositoException();
			}
		
		}
		catch(ExisteDepositoException| ConexionException  e){
			
			try {
				con.rollback();
			
			} 
			catch (SQLException e1) {
			
			throw new ConexionException();
			}
				throw new ModificandoDepositoException();
		}
	}
	
/////////////////////////////////FIN-DEPOSITOS/////////////////////////////////
	
/////////////////////////////////INI-CONCILIACIONES/////////////////////////////////
	   
//	/**
//	* Obtiene todos los cheques a depositar 
//	* @throws ObteniendoBancosException 
//	* @throws ObteniendoCuentasBcoException 
//	*/
//	@SuppressWarnings("unchecked")
//	public ArrayList<DepositoDetalleVO> get(String codEmp, String codMoneda) throws ObteniendoChequeException, ConexionException, ObteniendoCuentasBcoException, ObteniendoBancosException
//	{
//	
//		Connection con = null;
//		ArrayList<DepositoDetalle> lstDepositos;
//		ArrayList<DepositoDetalleVO> lstDepositosVO = new ArrayList<DepositoDetalleVO>();
//		
//		try 	
//		{
//			con = this.pool.obtenerConeccion();
//			lstDepositos = this.cheques.getChequesBanco(con, codEmp, codMoneda);
//			
//			DepositoDetalleVO aux;
//			
//			for (DepositoDetalle deposito : lstDepositos) 
//			{
//			aux = new DepositoDetalleVO();
//			
//			aux = deposito.retornarDepositoDetalleVO(deposito);
//			
//			lstDepositosVO.add(aux);
//			}
//		}
//	
//		catch (ConexionException e) {
//			throw e;
//		}	 
//	
//		finally{
//			this.pool.liberarConeccion(con);
//		}
//	
//		return lstDepositosVO;
//	
//	}

	@SuppressWarnings("unchecked") 
	public ArrayList<ConciliacionVO> getConciliacionesTodos(String codEmp, Timestamp inicio, Timestamp fin) throws ObteniendoConciliacionException, ConexionException {
	
		Connection con = null;
		
		ArrayList<Conciliacion> lst;
		ArrayList<ConciliacionVO> lstVO = new ArrayList<ConciliacionVO>();
		
		try
		{
			con = this.pool.obtenerConeccion();
			
			lst = this.conciliaciones.getConciliacionesTodos(con, codEmp, inicio, fin);
			
			
			for (Conciliacion conciliacion : lst) 
			{
				ConciliacionVO aux = conciliacion.getConciliacionVO(conciliacion);
				
				lstVO.add(aux);
			}
		
		}
		catch(ObteniendoConciliacionException  e)
		{
			throw e;
			
		} 
		catch (ConexionException e) {
		
			throw e;
		} 
		finally
		{
			this.pool.liberarConeccion(con);
		}
		
		
		return lstVO;
	}	 

	public NumeradoresVO insertarConciliacion(ConciliacionVO concVO, String codEmp) throws InsertandoConciliacionException, ConexionException, ExisteConciliacionException{
	
		Connection con = null;
		boolean existe = false;
		NumeradoresVO codigos = new NumeradoresVO();
		
		
		try 
		{
			con = this.pool.obtenerConeccion();
			con.setAutoCommit(false);
			
			//Obtengo numerador de la transacción
			codigos.setCodigo(numeradores.getNroTrans(con, "Conc")); 
			codigos.setNumeroTrans(numeradores.getNroTrans(con, "03"));
			concVO.setNroDocum(String.valueOf(codigos.getCodigo()));
			concVO.setNroTrans(codigos.getNumeroTrans());
			concVO.setCod_emp(codEmp);
			
			Conciliacion conciliacion = new Conciliacion();
			conciliacion = conciliacion.convierteConciliacion(concVO);
			
			if(!this.conciliaciones.memberConciliacion(conciliacion.getNroTrans(), codEmp, con)){
			
				//Inerto la conciliacion
				this.conciliaciones.insertarConciliacion(conciliacion, con, codEmp);
				
				int linea = 1;
				for (ConciliacionDetalle lin : conciliacion.getLstDetalle()) {
				
					
					/*A cada linea le seteamos el nroTrans*/
					lin.setNroTrans(conciliacion.getNroTrans());
					
					//Inserto el detalle de la conciliación
					conciliaciones.insertarConciliacionDetalle(lin, linea, con, codEmp);
					conciliaciones.conciliarSaCuentas(lin, con, codEmp, true);
					linea++;
				}
			
				con.commit();
			
			}
			else{
				existe = true;
			}
		
		
		}
		catch(Exception InsertandoConciliacionException)  	{
			try {
				con.rollback();
		
			} 
			catch (SQLException e) {
		
				throw new InsertandoConciliacionException();
			}
		
			throw new InsertandoConciliacionException();
		}
		finally
		{
			pool.liberarConeccion(con);
		}
		if (existe){
			throw new ExisteConciliacionException();
		}
		return codigos;
	}

	public void modificarConciliacion(ConciliacionVO conciliacionVO, String codEmp) throws  ConexionException, ModificandoConciliacionException, ExisteConciliacionException, NoExisteConciliacionException, InsertandoConciliacionException, EliminandoConcialiacionException{
	
		Connection con = null;
		
		try 
		{
			con = this.pool.obtenerConeccion();
			con.setAutoCommit(false);
			
			Conciliacion conciliacion = new Conciliacion();
			conciliacion = conciliacion.convierteConciliacion(conciliacionVO);
			
			/*Verificamos que exista la conciliación*/
			if(this.conciliaciones.memberConciliacion(conciliacion.getNroTrans(), codEmp, con)){
				this.eliminarConciliacionModificacion(conciliacion, codEmp, con);
				this.insertarConciliacionModificacion(conciliacion, codEmp, con);
				con.commit();
			}
			else
				throw new ModificandoConciliacionException();
			
		}
		catch(ExisteConciliacionException | ConexionException | SQLException  e)
		{
			try {
				con.rollback();
			
			} 
			catch (SQLException e1) {
		
				throw new ConexionException();
			}
			throw new ModificandoConciliacionException();
		}
		finally
		{
			pool.liberarConeccion(con);
		}
	}

	public void eliminarConciliacion(ConciliacionVO conciliacionVO, String codEmp) throws  ConexionException, EliminandoConcialiacionException, ExisteConciliacionException, NoExisteConciliacionException, InsertandoConciliacionException{
	
		Connection con = null;
		
		try 
		{
			con = this.pool.obtenerConeccion();
			con.setAutoCommit(false);
			
			Conciliacion conciliacion = new Conciliacion();
			
			conciliacion = conciliacion.convierteConciliacion(conciliacionVO);
			
			/*Verificamos que exista el nro de conciliacion*/
			if(this.conciliaciones.memberConciliacion(conciliacion.getNroTrans(), codEmp, con)){
			
				this.conciliaciones.eliminarConciliacion(conciliacion, con, codEmp);
				
				for (ConciliacionDetalle lin : conciliacion.getLstDetalle()) {
				
					/*A cada linea le seteamos el nroTrans*/
					lin.setNroTrans(conciliacion.getNroTrans());
					
					//elimino el detalle de conciliación
					conciliaciones.eliminarConciliacionDetalle(conciliacion, con, codEmp);
					conciliaciones.conciliarSaCuentas(lin, con, codEmp, false);
					
				}
				
				con.commit();
			}
			
			
			else{
				throw new NoExisteConciliacionException();
			}
			
		}
		catch(ExisteConciliacionException| ConexionException | SQLException  e){
			
			try {
				con.rollback();
			
			} 
			catch (SQLException e1) {
			
				throw new ConexionException();
			}
		}
		finally
		{
			pool.liberarConeccion(con);
		}
	}

	public void insertarConciliacionModificacion(Conciliacion conciliacion, String codEmp, Connection con) throws InsertandoConciliacionException, ConexionException, ExisteConciliacionException, ModificandoConciliacionException{
	
		boolean existe = false;
		
		try 
		{
			if(!this.conciliaciones.memberConciliacion(conciliacion.getNroTrans(), codEmp, con)){
			
				this.conciliaciones.insertarConciliacion(conciliacion, con, codEmp);
				
				int linea = 1;
				for (ConciliacionDetalle lin : conciliacion.getLstDetalle()) {
				
				
					/*A cada linea le seteamos el nroTrans*/
					lin.setNroTrans(conciliacion.getNroTrans());
					
					//Inserto el detalle de conciliación
					conciliaciones.insertarConciliacionDetalle(lin, linea, con, codEmp);
					linea++;
				}
			}
			else{
				existe = true;
			}
		
		
		}
		catch(Exception ModificandoConciliacionException)  	{
		
			throw new ModificandoConciliacionException();
		}
		if (existe){
			throw new ExisteConciliacionException();
		}
	}

	public void eliminarConciliacionModificacion(Conciliacion conciliacion, String codEmp, Connection con) throws  ConexionException, EliminandoConcialiacionException, ExisteConciliacionException, NoExisteConciliacionException, InsertandoConciliacionException, ModificandoConciliacionException{

		try 
		{
			/*Verificamos que exista el nro de cobro*/
			if(this.conciliaciones.memberConciliacion(conciliacion.getNroTrans(), codEmp, con)){
			
				this.conciliaciones.eliminarConciliacion(conciliacion, con, codEmp);
				
				for (ConciliacionDetalle lin : conciliacion.getLstDetalle()) {
				
				
					/*A cada linea le seteamos el nroTrans*/
					lin.setNroTrans(conciliacion.getNroTrans());
					
					//Inserto el detalle de la conciliación
					conciliaciones.eliminarConciliacionDetalle(conciliacion, con, codEmp);
				
				}
			}
			
			
			else{
				throw new NoExisteConciliacionException();
			}
		
		}
		catch(ExisteConciliacionException| ConexionException  e){
		
			try {
			con.rollback();
			
			} 
			catch (SQLException e1) {
			
				throw new ConexionException();
			}
			throw new ModificandoConciliacionException();
		}
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<ConciliacionDetalleVO> getMovimientosBanco(String codEmp, String codBco, String codCtaBco) throws ObteniendoConciliacionException, ConexionException, ObteniendoCuentasBcoException, ObteniendoBancosException
	{

		Connection con = null;
		ArrayList<ConciliacionDetalle> lstMovimientos;
		ArrayList<ConciliacionDetalleVO> lstMovimientosVO = new ArrayList<ConciliacionDetalleVO>();

		try 	
		{
			con = this.pool.obtenerConeccion();
			lstMovimientos = this.conciliaciones.getMovimientosBanco(con, codEmp, codBco, codCtaBco);
			
			ConciliacionDetalleVO aux;
			
			for (ConciliacionDetalle conciliacion: lstMovimientos) 
			{
				aux = new ConciliacionDetalleVO();
				
				aux = conciliacion.retornarConciliacionDetalleVO(conciliacion);
				
				lstMovimientosVO.add(aux);
			}
		}
		
		catch (ConexionException e) {
			throw e;
		}	 

		finally{
			this.pool.liberarConeccion(con);
		}

		return lstMovimientosVO;

	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<ConciliacionDetalleVO> getMovimientosCajaMoneda(String codEmp, String codMoneda) throws ObteniendoConciliacionException, ConexionException, ObteniendoCuentasBcoException, ObteniendoBancosException
	{

		Connection con = null;
		ArrayList<ConciliacionDetalle> lstMovimientos;
		ArrayList<ConciliacionDetalleVO> lstMovimientosVO = new ArrayList<ConciliacionDetalleVO>();

		try 	
		{
			con = this.pool.obtenerConeccion();
			lstMovimientos = this.conciliaciones.getMovimientosCajaMoneda(con, codEmp, codMoneda);
			
			ConciliacionDetalleVO aux;
			
			for (ConciliacionDetalle conciliacion: lstMovimientos) 
			{
				aux = new ConciliacionDetalleVO();
				
				aux = conciliacion.retornarConciliacionDetalleVO(conciliacion);
				
				lstMovimientosVO.add(aux);
			}
		}
		
		catch (ConexionException e) {
			throw e;
		}	 

		finally{
			this.pool.liberarConeccion(con);
		}

		return lstMovimientosVO;

	}
	
	@SuppressWarnings("unchecked")
	public double getSaldoConciliadoMoneda(String codEmp, String codMoneda) throws ObteniendoConciliacionException, ConexionException, ObteniendoCuentasBcoException, ObteniendoBancosException
	{

		Connection con = null;
		double saldo_conciliado = 0;
		try 	
		{
			con = this.pool.obtenerConeccion();
			saldo_conciliado = this.conciliaciones.getSaldoConciliadoMoneda(con, codEmp, codMoneda);
			
		}
		
		catch (ConexionException e) {
			throw e;
		}	 

		finally{
			this.pool.liberarConeccion(con);
		}

		return saldo_conciliado;

	}
	
	@SuppressWarnings("unchecked")
	public double getSaldoConciliadoCuentaBanco(String codEmp, String codBanco, String codCuenta) throws ObteniendoConciliacionException, ConexionException, ObteniendoCuentasBcoException, ObteniendoBancosException
	{

		Connection con = null;
		double saldo_conciliado = 0;
		try 	
		{
			con = this.pool.obtenerConeccion();
			saldo_conciliado = this.conciliaciones.getSaldoConciliadoCuentaBanco(con, codBanco, codCuenta, codEmp);
			
		}
		
		catch (ConexionException e) {
			throw e;
		}	 

		finally{
			this.pool.liberarConeccion(con);
		}

		return saldo_conciliado;

	}
	
	
	///////////////////////////////////////////////INI VALIDACIONES////////////////////////////////////////////////////////////
	
	public boolean existeCheque(ChequeVO cheque,  String codEmp) throws ConexionException, ExisteChequeException{
		   
		   Boolean existe = false;
		   Connection con = null;
		   try {
			   
			   con = this.pool.obtenerConeccion();
			   con.setAutoCommit(false);
			   existe = this.cheques.exixsteCheque(cheque.getSerieDocum(), cheque.getNroDocum(), codEmp, cheque.getCodBanco(), cheque.getCodCuenta(), con);
			
		   } catch(ConexionException | SQLException e)
		   {
			   try {
				   con.rollback();

			   } 
			   catch (SQLException e1) {

				   throw new ConexionException();
			   }
			   
		   }
		   finally
		   {
			   pool.liberarConeccion(con);
		   }
		  
		   return existe;
	   }
	
	  public boolean existeChequeDepositado(ChequeVO cheque,  String codEmp) throws ConexionException, ExisteDepositoException{
		   
		   Boolean existe = false;
		   Connection con = null;
		   try {
			   
			   con = this.pool.obtenerConeccion();
			   con.setAutoCommit(false);
			   existe = this.depositos.existeChequeDepositado(cheque.getSerieDocum(), cheque.getNroDocum(), codEmp, cheque.getCodBanco(), cheque.getCodCuenta(), con);
			
		   } catch(ConexionException | SQLException e)
		   {
			   try {
				   con.rollback();

			   } 
			   catch (SQLException e1) {

				   throw new ConexionException();
			   }
			   
		   }
		   finally
		   {
			   pool.liberarConeccion(con);
		   }
		  
		   return existe;
	   }
	
		public boolean existeEgreso(Integer nro_docum,  String codEmp) throws ConexionException, ExisteEgresoCobroException{
		   
		   Boolean existe = false;
		   Connection con = null;
		   try {
			   
			   con = this.pool.obtenerConeccion();
			   con.setAutoCommit(false);
			   existe = this.egresoCobro.existeEgreso(nro_docum, codEmp, con);
			
		   } catch(ConexionException | SQLException e)
		   {
			   try {
				   con.rollback();

			   } 
			   catch (SQLException e1) {

				   throw new ConexionException();
			   }
			   
		   }
		   finally
		   {
			   pool.liberarConeccion(con);
		   }
		  
		   return existe;
	   }
		
		public boolean existeGastoIngresoCobro(Integer nro_docum,  String codEmp) throws ConexionException, ExisteIngresoCobroException{
			   
		   Boolean existe = false;
		   Connection con = null;
		   try {
			   
			   con = this.pool.obtenerConeccion();
			   con.setAutoCommit(false);
			   existe = this.ingresoCobro.existeGastoIngresoCobro(nro_docum, codEmp, con);
			
		   } catch(ConexionException | SQLException e)
		   {
			   try {
				   con.rollback();

			   } 
			   catch (SQLException e1) {

				   throw new ConexionException();
			   }
			   
		   }
		   finally
		   {
			   pool.liberarConeccion(con);
		   }
		  
		   return existe;
	   }
		
		
	
		public boolean existeGastoFactura(Integer nro_docum,  String codEmp) throws ConexionException, ExisteFacturaException{
			   
			   Boolean existe = false;
			   Connection con = null;
			   try {
				   
				   con = this.pool.obtenerConeccion();
				   con.setAutoCommit(false);
				   existe = this.facturas.existeGasto(nro_docum, codEmp, con);
				
			   } catch(ConexionException | SQLException e)
			   {
				   try {
					   con.rollback();

				   } 
				   catch (SQLException e1) {

					   throw new ConexionException();
				   }
				   
			   }
			   finally
			   {
				   pool.liberarConeccion(con);
			   }
			  
			   return existe;
		}
		
		public boolean existeReciboFactura(Integer nro_docum,  String serie, String codigo, String codEmp) throws ConexionException, ExisteReciboException{
			   
			   Boolean existe = false;
			   Connection con = null;
			   try {
				   
				   con = this.pool.obtenerConeccion();
				   con.setAutoCommit(false);
				   existe = this.recibos.existeReciboFactura(nro_docum, serie, codigo, codEmp, con);
			   } catch(ConexionException | SQLException e)
			   {
				   try {
					   con.rollback();

				   } 
				   catch (SQLException e1) {

					   throw new ConexionException();
				   }
				   
			   }
			   finally
			   {
				   pool.liberarConeccion(con);
			   }
			  
			   return existe;
		}
		
		public boolean existeNCFactura(Integer nro_docum,  String serie, String codigo, String codEmp) throws ConexionException, ExisteNotaCreditoException{
			   
			   Boolean existe = false;
			   Connection con = null;
			   try {
				   
				   con = this.pool.obtenerConeccion();
				   con.setAutoCommit(false);
				   existe = this.notaCredito.existeNCFactura(nro_docum, serie, codigo, codEmp, con);
			   } catch(ConexionException | SQLException e)
			   {
				   try {
					   con.rollback();

				   } 
				   catch (SQLException e1) {

					   throw new ConexionException();
				   }
				   
			   }
			   finally
			   {
				   pool.liberarConeccion(con);
			   }
			  
			   return existe;
		}
		
		public boolean depositoConciliado(DepositoVO deposito,  String codEmp) throws ConexionException, NumberFormatException, MovimientoConciliadoException{
			   
			   Boolean existe = false;
			   Connection con = null;
			   try {
				   
				   con = this.pool.obtenerConeccion();
				   con.setAutoCommit(false);
				   existe = this.conciliaciones.depositoConciliado(deposito.getSerieDocum(), Integer.parseInt(deposito.getNroDocum()), codEmp, deposito.getCodBanco(), deposito.getCodCuenta(), con);
				
			   } catch(ConexionException | SQLException e)
			   {
				   try {
					   con.rollback();

				   } 
				   catch (SQLException e1) {

					   throw new ConexionException();
				   }
				   
			   }
			   finally
			   {
				   pool.liberarConeccion(con);
			   }
			  
			   return existe;
		}
		
		public boolean validaNumDeposito(String codBco, String codCta, String nro,  String codEmp) throws ConexionException, NumberFormatException, ExisteDepositoException{
			   
			   Boolean existe = false;
			   Connection con = null;
			   try {
				   
				   con = this.pool.obtenerConeccion();
				   con.setAutoCommit(false);
				   existe = this.depositos.validaNumDeposito(codBco, codCta, Integer.parseInt(nro), codEmp, con);
			   } catch(ConexionException | SQLException e)
			   {
				   try {
					   con.rollback();

				   } 
				   catch (SQLException e1) {

					   throw new ConexionException();
				   }
				   
			   }
			   finally
			   {
				   pool.liberarConeccion(con);
			   }
			  
			   return existe;
		}
		
		public boolean egresoConciliado(String codEmp, String nroEgreso) throws ConexionException, NumberFormatException, MovimientoConciliadoException{
			   
			   Boolean existe = false;
			   Connection con = null;
			   try {
				   
				   con = this.pool.obtenerConeccion();
				   con.setAutoCommit(false);
				   existe = this.conciliaciones.egresoConciliado(codEmp, Integer.parseInt(nroEgreso), con);
			   } catch(ConexionException | SQLException e)
			   {
				   try {
					   con.rollback();

				   } 
				   catch (SQLException e1) {

					   throw new ConexionException();
				   }
				   
			   }
			   finally
			   {
				   pool.liberarConeccion(con);
			   }
			  
			   return existe;
		}
		
		public boolean ingresoConciliado(String codEmp, String nroEgreso) throws ConexionException, NumberFormatException, MovimientoConciliadoException{
			   
			   Boolean existe = false;
			   Connection con = null;
			   try {
				   
				   con = this.pool.obtenerConeccion();
				   con.setAutoCommit(false);
				   existe = this.conciliaciones.ingresoConciliado(codEmp, Integer.parseInt(nroEgreso), con);
			   } catch(ConexionException | SQLException e)
			   {
				   try {
					   con.rollback();

				   } 
				   catch (SQLException e1) {

					   throw new ConexionException();
				   }
				   
			   }
			   finally
			   {
				   pool.liberarConeccion(con);
			   }
			  
			   return existe;
		}
		
		public boolean existeNroTransferencia(String nro, String codBco, String codCta,  String codEmp) throws ConexionException, NumberFormatException, ExisteNroTransferencia{
			   
			   Boolean existe = false;
			   Connection con = null;
			   try {
				   
				   con = this.pool.obtenerConeccion();
				   con.setAutoCommit(false);
				   existe = this.saldosCuentas.existeNroTransferencia(Integer.parseInt(nro), codBco, codCta, codEmp, con);
				
			   } catch(ConexionException | SQLException e)
			   {
				   try {
					   con.rollback();

				   } 
				   catch (SQLException e1) {

					   throw new ConexionException();
				   }
				   
			   }
			   finally
			   {
				   pool.liberarConeccion(con);
			   }
			  
			   return existe;
		   }
		
			public boolean existeGastoAsociado(IngresoCobroDetalleVO detalle,  String codEmp) throws ConexionException, NumberFormatException, ExisteGastoException{
			   
			   Boolean existe = false;
			   Connection con = null;
			   try {
				   
				   con = this.pool.obtenerConeccion();
				   con.setAutoCommit(false);
				   existe = this.gastos.existeGastoIngreso(detalle.getSerieDocum(), Integer.parseInt(detalle.getNroDocum()), codEmp, con);
				
			   } catch(ConexionException | SQLException e)
			   {
				   try {
					   con.rollback();

				   } 
				   catch (SQLException e1) {

					   throw new ConexionException();
				   }
				   
			   }
			   finally
			   {
				   pool.liberarConeccion(con);
			   }
			  
			   return existe;
		   }
			
			public boolean existeGastoAsociadoProceso(Integer nroProceso,  String codEmp) throws ConexionException, NumberFormatException, ExisteGastoException{
				   
				   Boolean existe = false;
				   Connection con = null;
				   try {
					   
					   con = this.pool.obtenerConeccion();
					   con.setAutoCommit(false);
					   existe = this.gastos.existeGastoAsociadoProceso(nroProceso, codEmp, con);
					
				   } catch(ConexionException | SQLException e)
				   {
					   try {
						   con.rollback();

					   } 
					   catch (SQLException e1) {

						   throw new ConexionException();
					   }
					   
				   }
				   finally
				   {
					   pool.liberarConeccion(con);
				   }
				  
				   return existe;
			   }
			
			public boolean existeSaldoAsociadoProceso(Integer nroProceso,  String codEmp) throws ConexionException, NumberFormatException, ExisteSaldoException{
				   
				   Boolean existe = false;
				   Connection con = null;
				   try {
					   
					   con = this.pool.obtenerConeccion();
					   con.setAutoCommit(false);
					   existe = this.saldosProceso.existeSaldoAsociadoProceso(con, nroProceso, codEmp);
					
				   } catch(ConexionException | SQLException e)
				   {
					   try {
						   con.rollback();

					   } 
					   catch (SQLException e1) {

						   throw new ConexionException();
					   }
					   
				   }
				   finally
				   {
					   pool.liberarConeccion(con);
				   }
				  
				   return existe;
			 }
	///////////////////////////////////////////////FIN VALIDACIONES////////////////////////////////////////////////////////////
	
}
