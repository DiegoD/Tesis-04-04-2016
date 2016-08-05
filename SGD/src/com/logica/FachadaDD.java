package com.logica;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.abstractFactory.AbstractFactoryBuilder;
import com.abstractFactory.IAbstractFactory;
import com.excepciones.*;
import com.excepciones.CodigosGeneralizados.EliminandoCodigoGeneralizadoException;
import com.excepciones.CodigosGeneralizados.ExisteCodigoException;
import com.excepciones.CodigosGeneralizados.InsertandoCodigoException;
import com.excepciones.CodigosGeneralizados.ModificandoCodigoException;
import com.excepciones.CodigosGeneralizados.NoExisteCodigoException;
import com.excepciones.CodigosGeneralizados.ObteniendoCodigosException;
import com.excepciones.Documentos.ExisteDocumentoException;
import com.excepciones.Documentos.InsertandoDocumentoException;
import com.excepciones.Documentos.ModificandoDocumentoException;
import com.excepciones.Documentos.NoExisteDocumentoException;
import com.excepciones.Documentos.ObteniendoDocumentosException;
import com.excepciones.Empresas.ExisteEmpresaException;
import com.excepciones.Empresas.InsertandoEmpresaException;
import com.excepciones.Empresas.ModificandoEmpresaException;
import com.excepciones.Empresas.NoExisteEmpresaException;
import com.excepciones.Empresas.ObteniendoEmpresasException;
import com.excepciones.Impuestos.ExisteImpuestoException;
import com.excepciones.Impuestos.InsertandoImpuestoException;
import com.excepciones.Impuestos.ModificandoImpuestoException;
import com.excepciones.Impuestos.NoExisteImpuestoException;
import com.excepciones.Impuestos.ObteniendoImpuestosException;
import com.excepciones.Login.LoginException;
import com.excepciones.Monedas.ExisteMonedaException;
import com.excepciones.Monedas.InsertandoMonedaException;
import com.excepciones.Monedas.ModificandoMonedaException;
import com.excepciones.Monedas.NoExisteMonedaException;
import com.excepciones.Monedas.ObteniendoMonedaException;
import com.excepciones.Rubros.ExisteRubroException;
import com.excepciones.Rubros.InsertandoRubroException;
import com.excepciones.Rubros.ModificandoRubroException;
import com.excepciones.Rubros.NoExisteRubroException;
import com.excepciones.Rubros.ObteniendoRubrosException;
import com.excepciones.Usuarios.ExisteUsuarioException;
import com.excepciones.Usuarios.InsertandoUsuarioException;
import com.excepciones.Usuarios.ObteniendoUsuariosException;
import com.excepciones.Usuarios.ObteniendoUsuariosxEmpExeption;
import com.excepciones.grupos.ExisteGrupoException;
import com.excepciones.grupos.InsertandoGrupoException;
import com.excepciones.grupos.MemberGrupoException;
import com.excepciones.grupos.ModificandoGrupoException;
import com.excepciones.grupos.NoExisteGrupoException;
import com.excepciones.grupos.ObteniendoFormulariosException;
import com.excepciones.grupos.ObteniendoGruposException;
import com.valueObject.*;
import com.persistencia.*;

public class FachadaDD {

	private static final Object lock = new Object();
	private static volatile FachadaDD INSTANCE = null;
	Pool pool;
	
	/*Esto es para abstract factory*/
	private IDAOUsuarios usuarios;
	private IDAOGrupos grupos;
	private IDAOImpuestos impuestos;
	private IDAOMonedas monedas;
	private IDAOEmpresas empresas;
	private IDAORubros rubros;
	private IDAODocumentos documentos;
	private IDAOCodigosGeneralizados codigosGeneralizados;
	
	private AbstractFactoryBuilder fabrica;
	private IAbstractFactory fabricaConcreta;
	
	
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
	public ArrayList<UsuarioVO> getUsuarios() throws ConexionException, ClassNotFoundException, ObteniendoUsuariosException, ErrorInesperadoException, ObteniendoGruposException {
    	
    	Connection con = null;
    	ArrayList<Usuario> lstUsuarios = new ArrayList<Usuario>();
    	ArrayList<UsuarioVO> lstUsuariosVO = new ArrayList<UsuarioVO>();
    	
    	try 
    	{
			con = this.pool.obtenerConeccion();
	    	
			
			lstUsuarios = this.usuarios.getUsuarios(con);
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
	 * VER
	 */
    public ArrayList<EmpLoginVO> getUsuariosxEmp(String usuario) throws ConexionException, ObteniendoUsuariosxEmpExeption
    {
    	Connection con = null;
    	ArrayList<EmpLoginVO> lstEmpresas = new ArrayList<EmpLoginVO>();
    	try 
    	{
			con = this.pool.obtenerConeccion();
			lstEmpresas = this.usuarios.getUsuariosxEmp(usuario, con);
			
			
		} 
    	catch (ObteniendoUsuariosxEmpExeption | ConexionException e) 
    	{
    		throw e;
		}
    	finally
    	{
    		this.pool.liberarConeccion(con);
    	}
    	return lstEmpresas;
    	
    }
    
    /**
	 * Obtiene todos los impuestos existentes
	 */
    @SuppressWarnings("unchecked")
	public ArrayList<ImpuestoVO> getImpuestos() throws ObteniendoImpuestosException, ConexionException
    {
    	
    	Connection con = null;
    	
    	ArrayList<Impuesto> lstImpuestos;
    	ArrayList<ImpuestoVO> lstImpuestoVO = new ArrayList<ImpuestoVO>();
    	    	
    	try
    	{
    		con = this.pool.obtenerConeccion();
    		
    		lstImpuestos = this.impuestos.getImpuestos(con);
    		
    		
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
    public void insertarImpuesto(ImpuestoVO impuestoVO) throws InsertandoImpuestoException, ConexionException, ExisteImpuestoException 
    {
    	
    	Connection con = null;
    	boolean existe = false;
    	
    	try 
    	{
			con = this.pool.obtenerConeccion();
			con.setAutoCommit(false);
			
	    	Impuesto impuesto = new Impuesto(impuestoVO); 
	    	
	    	if(!this.impuestos.memberImpuesto(impuesto.getCod_imp(), con)) 	{
	    		
	    		this.impuestos.insertarImpuesto(impuesto, con);
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
    public void actualizarImpuesto(ImpuestoVO impuestoVO) throws ConexionException, NoExisteImpuestoException, ModificandoImpuestoException, ExisteImpuestoException  
	{
	    	
	    	Connection con = null;
	    	
	    	try 
	    	{
				con = this.pool.obtenerConeccion();
				con.setAutoCommit(false);
				
				Impuesto impuesto = new Impuesto(impuestoVO);
		    	
		    	if(this.impuestos.memberImpuesto(impuesto.getCod_imp(), con))
		    	{
		    		impuestos.actualizarImpuesto(impuesto, con);
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
	public ArrayList<MonedaVO> getMonedas() throws ObteniendoMonedaException, ConexionException
    {
    	
    	Connection con = null;
    	
    	ArrayList<Moneda> lstMonedas;
    	ArrayList<MonedaVO> lstMonedasVO = new ArrayList<MonedaVO>();
    	    	
    	try
    	{
    		con = this.pool.obtenerConeccion();
    		
    		lstMonedas = this.monedas.getMonedas(con);
    		
    		
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
	 */
    public void insertarMoneda(MonedaVO monedaVO) throws InsertandoMonedaException, ConexionException, ExisteMonedaException 
    {
    	
    	Connection con = null;
    	boolean existe = false;
    	
    	try 
    	{
			con = this.pool.obtenerConeccion();
			con.setAutoCommit(false);
			
	    	Moneda moneda = new Moneda(monedaVO); 
	    	
	    	if(!this.monedas.memberMoneda(moneda.getCod_moneda(), con)) 	{
	    		
	    		this.monedas.insertarMoneda(moneda, con);
	    		con.commit();
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
    }

    /**
	 * Actualiza los datos de una moneda dado su código
	 * valida que exista el código 
	 */
    public void actualizarMoneda(MonedaVO monedaVO) throws ConexionException, NoExisteMonedaException, ModificandoMonedaException, ExisteMonedaException  
	{
	    	
	    	Connection con = null;
	    	
	    	try 
	    	{
				con = this.pool.obtenerConeccion();
				con.setAutoCommit(false);
				
				Moneda moneda = new Moneda(monedaVO);
		    	
		    	if(this.monedas.memberMoneda(moneda.getCod_moneda(), con))
		    	{
		    		monedas.actualizarMoneda(moneda, con);
		    		con.commit();
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
    public void insertarEmprea(EmpresaVO empresaVO) throws InsertandoEmpresaException, ConexionException, ExisteEmpresaException 
    {
    	
    	Connection con = null;
    	boolean existe = false;
    	
    	try 
    	{
			con = this.pool.obtenerConeccion();
			con.setAutoCommit(false);
			
	    	Empresa empresa = new Empresa(empresaVO); 
	    	
	    	if(!this.empresas.memberEmpresa(empresa.getCod_emp(), con)) 	{
	    		
	    		this.empresas.insertarEmpresa(empresa, con);
	    		con.commit();
	    	}
	    	else{
	    		existe = true;
	    	}
	    		
    	
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
	public ArrayList<RubroVO> getRubros() throws ObteniendoRubrosException, ConexionException
    {
    	
    	Connection con = null;
    	
    	ArrayList<Rubro> lstRubros;
    	ArrayList<RubroVO> lstRubrosVO = new ArrayList<RubroVO>();
    	    	
    	try
    	{
    		con = this.pool.obtenerConeccion();
    		
    		lstRubros = this.rubros.getRubros(con);
    		
    		
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
    			aux.setCod_impuesto(rubro.getImpuesto().getCod_imp());
    			aux.setDescripcionImpuesto(rubro.getImpuesto().getDescripcion());
    			aux.setPorcentajeImpuesto(rubro.getImpuesto().getPorcentaje());
    			
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
    public void insertarRubro(RubroVO rubroVO) throws InsertandoRubroException, ConexionException, ExisteRubroException 
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
	    	
	    	if(!this.rubros.memberRubro(rubro.getCod_rubro(), con)) 	{
	    		
	    		this.rubros.insertarRubro(rubro, con);
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
    public void actualizarRubro(RubroVO rubroVO) throws ConexionException, NoExisteRubroException, ModificandoRubroException, ExisteRubroException  
	{
	    	
	    	Connection con = null;
	    	
	    	try 
	    	{
				con = this.pool.obtenerConeccion();
				con.setAutoCommit(false);
				
				Rubro rubro = new Rubro(rubroVO);
		    	
		    	if(this.rubros.memberRubro(rubro.getCod_rubro(), con))
		    	{
		    		this.rubros.actualizarRubro(rubro, con);
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
	public ArrayList<DocumentoAduaneroVO> getDocumentos() throws ObteniendoDocumentosException, ConexionException
	{
	
		Connection con = null;
		
		ArrayList<DocumentoAduanero> lstDocumentos;
		ArrayList<DocumentoAduaneroVO> lstDocumentosVO = new ArrayList<DocumentoAduaneroVO>();
		
		try
		{
			con = this.pool.obtenerConeccion();
			
			lstDocumentos = this.documentos.getDocumentos(con);
			
			
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
	public void insertarDocumento(DocumentoAduaneroVO documentoVO) throws InsertandoDocumentoException, ConexionException, ExisteDocumentoException 
	{
	
		Connection con = null;
		boolean existe = false;
		
		try 
		{
			con = this.pool.obtenerConeccion();
			con.setAutoCommit(false);
			
			DocumentoAduanero documento = new DocumentoAduanero(documentoVO); 
		
			if(!this.documentos.memberDocumento(documento.getCod_docucmento(), con)) 	{
			
				this.documentos.insertarDocumento(documento, con);
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
	public void actualizarDocumento(DocumentoAduaneroVO documentoVO) throws ConexionException, NoExisteDocumentoException, ModificandoDocumentoException, ExisteDocumentoException  
	{
	
		Connection con = null;
		
		try 
		{
			con = this.pool.obtenerConeccion();
			con.setAutoCommit(false);
		
			DocumentoAduanero documento = new DocumentoAduanero(documentoVO);
		
		if(this.documentos.memberDocumento(documento.getCod_docucmento(), con))	{
			this.documentos.actualizarDocumento(documento, con);
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
}
