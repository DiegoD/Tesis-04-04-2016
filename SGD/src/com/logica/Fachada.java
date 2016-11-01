package com.logica;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.abstractFactory.AbstractFactoryBuilder;
import com.abstractFactory.IAbstractFactory;
import com.excepciones.*;
import com.excepciones.Bancos.ExisteBancoException;
import com.excepciones.Bancos.InsertandoBancoException;
import com.excepciones.Bancos.InsertandoCuentaException;
import com.excepciones.Bancos.ModificandoBancoException;
import com.excepciones.Bancos.ModificandoCuentaBcoException;
import com.excepciones.Bancos.ObteniendoBancosException;
import com.excepciones.Bancos.ObteniendoCuentasBcoException;
import com.excepciones.Bancos.VerificandoBancosException;
import com.excepciones.Cheques.EliminandoChequeException;
import com.excepciones.Cheques.ExisteChequeException;
import com.excepciones.Cheques.InsertandoChequeException;
import com.excepciones.Cheques.ModificandoChequeException;
import com.excepciones.Cheques.NoExisteChequeException;
import com.excepciones.Cotizaciones.ObteniendoCotizacionesException;
import com.excepciones.Documentos.ExisteDocumentoException;
import com.excepciones.Documentos.InsertandoDocumentoException;
import com.excepciones.Documentos.ModificandoDocumentoException;
import com.excepciones.Documentos.NoExisteDocumentoException;
import com.excepciones.Documentos.ObteniendoDocumentosException;
import com.excepciones.Empresas.ExisteEmpresaException;
import com.excepciones.IngresoCobros.ExisteIngresoCobroException;
import com.excepciones.IngresoCobros.InsertandoIngresoCobroException;
import com.excepciones.IngresoCobros.ModificandoIngresoCobroException;
import com.excepciones.IngresoCobros.NoExisteIngresoCobroException;
import com.excepciones.IngresoCobros.ObteniendoIngresoCobroException;
import com.excepciones.Login.LoginException;
import com.excepciones.SaldoCuentas.EliminandoSaldoCuetaException;
import com.excepciones.SaldoCuentas.ExisteSaldoCuentaException;
import com.excepciones.SaldoCuentas.InsertandoSaldoCuentaException;
import com.excepciones.SaldoCuentas.ModificandoSaldoCuentaException;
import com.excepciones.SaldoCuentas.NoExisteSaldoCuentaException;
import com.excepciones.Usuarios.ExisteUsuarioException;
import com.excepciones.clientes.ExisteClienteExeption;
import com.excepciones.clientes.ExisteDocumentoClienteException;
import com.excepciones.clientes.InsertandoClienteException;
import com.excepciones.clientes.ModificandoClienteException;
import com.excepciones.clientes.ObteniendoClientesException;
import com.excepciones.clientes.VerificandoClienteException;
import com.excepciones.funcionarios.ExisteFuncionarioDocumetnoException;
import com.excepciones.funcionarios.ExisteFuncionarioException;
import com.excepciones.funcionarios.InsertendoFuncionarioException;
import com.excepciones.funcionarios.ModificandoFuncionarioException;
import com.excepciones.funcionarios.ObteniendoFuncionariosException;
import com.excepciones.grupos.ExisteGrupoException;
import com.excepciones.grupos.InsertandoGrupoException;
import com.excepciones.grupos.MemberGrupoException;
import com.excepciones.grupos.ModificandoGrupoException;
import com.excepciones.grupos.NoExisteGrupoException;
import com.excepciones.grupos.ObteniendoFormulariosException;
import com.excepciones.grupos.ObteniendoGruposException;
import com.excepciones.Egresos.*;
import com.logica.Docum.ConvertirDocumento;
import com.logica.Docum.CuentaBcoInfo;
import com.logica.Docum.DatosDocum;
import com.logica.Docum.DocumDetalle;
import com.logica.Docum.DocumSaldo;
import com.logica.IngresoCobro.IngresoCobro;
import com.valueObject.*;
import com.valueObject.Cotizacion.CotizacionVO;
import com.valueObject.Docum.DatosDocumVO;
import com.valueObject.Docum.DocumSaldoVO;
import com.valueObject.IngresoCobro.IngresoCobroVO;
import com.valueObject.Numeradores.NumeradoresVO;
import com.valueObject.banco.BancoVO;
import com.valueObject.banco.CtaBcoVO;
import com.valueObject.cliente.ClienteVO;
import com.vista.Mensajes;
import com.vista.VariablesPermisos;
import com.persistencia.*;

public class Fachada {

	private static final Object lock = new Object();
	private static volatile Fachada INSTANCE = null;
	
	/*Pool de conecciones*/
	Pool pool;
	
	/*Esto es para abstract factory*/
	private IDAOUsuarios usuarios;
	private IDAOGrupos grupos;
	private IDAOClientes clientes;
	private IDAOFuncionarios funcionarios;
	private IDAODocumDgi documentosDGI;
	private IDAOBancos bancos;
	private IDAOIngresoCobro ingresoCobro;
	private IDAOEgresoCobro egresoCobro;
	private IDAOSaldos saldos;
	private IDAONumeradores numeradores;
	private IDAOCotizaciones cotizaciones;
	private IDAOCheques cheques;
	private IDAOSaldosCuentas saldosCuentas;
	private IDAOSaldosProc saldosProceso;
	private IDAOGastos gastos;
	private IDAOMonedas monedas;
	
	private AbstractFactoryBuilder fabrica;
	private IAbstractFactory fabricaConcreta;
	
	
    private Fachada() throws InstantiationException, IllegalAccessException, ClassNotFoundException, FileNotFoundException, IOException
    {
        pool = Pool.getInstance();
    	
    	fabrica = AbstractFactoryBuilder.getInstancia();
		fabricaConcreta = fabrica.getAbstractFactory();
		
        this.usuarios =  fabricaConcreta.crearDAOUsuarios();
        this.grupos = fabricaConcreta.crearDAOGrupos();
        this.clientes = fabricaConcreta.crearDAOClientes();
        this.funcionarios = fabricaConcreta.crearDAOFuncionarios();
        this.documentosDGI = fabricaConcreta.crearDAODocumDgi();
        this.bancos = fabricaConcreta.crearDAOBancos();
        this.ingresoCobro = fabricaConcreta.crearDAOIngresoCobro();
        this.saldos = fabricaConcreta.crearDAOSaldos();
        this.numeradores = fabricaConcreta.crearDAONumeradores();
        this.cotizaciones = fabricaConcreta.crearDAOCotizaciones();
        this.cheques = fabricaConcreta.crearDAOCheques(); 
        this.saldosCuentas = fabricaConcreta.crearDAOSaldosCuenta();
        this.saldosProceso = fabricaConcreta.crearDAOSaldosProceso();
        this.egresoCobro = fabricaConcreta.crearDAOEgresoCobro();
        this.gastos = fabricaConcreta.crearDAOGastos();
        this.monedas = fabricaConcreta.crearDAOMonedas();
        
    }
    
    public static Fachada getInstance() throws InicializandoException {
         
    	if(INSTANCE == null)
        {
            synchronized (lock)
            {   
                try {
					INSTANCE = new Fachada();
				} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | IOException e) {
					
					throw new InicializandoException();
				}
            }
        }
        
        return INSTANCE;
    }
    
    
     
        
  //
    
/////////////////////////////////INI-LOGIN/////////////////////////////////
    
    public boolean usuarioValido(LoginVO loginVO) throws LoginException, ConexionException
    {
    	
    	Connection con = null;
    	
    	try
    	{
    		con = this.pool.obtenerConeccion();
    	
    		return this.usuarios.usuarioValido(loginVO, con);
    		
    	}catch(LoginException | ConexionException e)
    	{
    		throw e;
    	}
    	finally
    	{
    		this.pool.liberarConeccion(con);
    	}
    	
    }
    
/////////////////////////////////FIN-LOGIN/////////////////////////////////
    
/////////////////////////////////INI-GUPOS/////////////////////////////////
    @SuppressWarnings("unchecked")
	public ArrayList<GrupoVO> getGrupos(String codEmp) throws ObteniendoGruposException, ConexionException,  ObteniendoFormulariosException
    {
    	
    	Connection con = null;
    	
    	ArrayList<Grupo> lstGrupos;
    	ArrayList<GrupoVO> lstGruposVO = new ArrayList<GrupoVO>();
    	    	
    	try
    	{
    		con = this.pool.obtenerConeccion();
    		
    		lstGrupos = this.grupos.getGrupos(codEmp, con);
    		
    		
    		GrupoVO aux;
    		for (Grupo grupo : lstGrupos) 
			{
    			aux = new GrupoVO();
    			
    			aux.setCodGrupo(grupo.getCodGrupo());
    			aux.setNomGrupo(grupo.getNomGrupo());
    			aux.setFechaMod(grupo.getFechaMod());
    			aux.setUsuarioMod(grupo.getUsuarioMod());
    			aux.setOperacion(grupo.getOperacion());
    			aux.setActivo(grupo.isActivo());
    			
    			
    			FormularioVO auxF;
    			for (Formulario frm : grupo.getLstFormularios()) {
					
    				auxF = new FormularioVO();
    				
    				auxF.setCodFormulario(frm.getCodFormulario());
    				auxF.setNomFormulario(frm.getNomFormulario());
    				
    				auxF.setLeer(frm.isLeer());
    				auxF.setNuevoEditar(frm.isNuevoEditar());
    				auxF.setBorrar(frm.isBorrar());
    				
    				aux.getLstFormularios().add(auxF);
				}
    			
    			lstGruposVO.add(aux);
			}
	
    	}catch(ObteniendoGruposException | ObteniendoFormulariosException e)
    	{
    		throw e;
    		
    	} catch (ConexionException e) {
			
    		throw e;
    	} 
    	finally
    	{
    		this.pool.liberarConeccion(con);
    	}
    	    
    	
    	return lstGruposVO;
    }
    
    public void insertarGrupo(GrupoVO grupoVO, String codEmp) throws InsertandoGrupoException, ConexionException, ExisteGrupoException 
    {
    	
    	Connection con = null;
    	boolean existe = false;
    	
    	try 
    	{
			con = this.pool.obtenerConeccion();
			con.setAutoCommit(false);
			
	    	Grupo grupo = new Grupo(grupoVO); 
	    	
	    	if(!this.grupos.memberGrupo(grupo.getCodGrupo(), codEmp, con))
	    	{
	    		this.grupos.insertarGrupo(grupo, codEmp, con);
	    		
	    		con.commit();
	    	}
	    	else{
	    		existe = true;
	    	}
	    		
    	
    	}catch(Exception InsertandoGrupoException)
    	{
    		try {
				con.rollback();
				
			} catch (SQLException e) {
				
				throw new InsertandoGrupoException();
			}
    		
    		throw new InsertandoGrupoException();
    	}
    	finally
    	{
    		pool.liberarConeccion(con);
    	}
    	if (existe){
    		throw new ExisteGrupoException();
    	}
    }
    
	public void editarGrupo(GrupoVO grupoVO, String codEmp) throws ConexionException, NoExisteGrupoException, ModificandoGrupoException  
	{
	    	
	    	Connection con = null;
	    	
	    	try 
	    	{
				con = this.pool.obtenerConeccion();
				con.setAutoCommit(false);
				
				Grupo grupo = new Grupo(grupoVO);
		    	
		    	if(this.grupos.memberGrupo(grupo.getCodGrupo(), codEmp, con))
		    	{
		    		/*Primero eliminamos el grupo*/
		    		this.grupos.actualizarGrupo(grupo, codEmp, con);
		    			    		
		    		
		    		con.commit();
		    	}
		    	
		    	else
		    		throw new NoExisteGrupoException();
	    	
	    	}catch(MemberGrupoException| ConexionException | SQLException | ModificandoGrupoException e)
	    	{
	    		try {
					con.rollback();
					
				} catch (SQLException e1) {
					
					throw new ConexionException();
				}
	    		throw new ModificandoGrupoException();
	    	}
	    	finally
	    	{
	    		pool.liberarConeccion(con);
	    	}
	    }
    
	 @SuppressWarnings("unchecked")
		public ArrayList<FormularioVO> getFormulariosNoGrupo(String codGrupo, String codEmp) throws ObteniendoGruposException, ConexionException, ErrorInesperadoException {
	    	
	    	Connection con = null;
	    	
	    	ArrayList<Formulario> lstFormularios = new ArrayList<Formulario>();
	    	ArrayList<FormularioVO> lstFormSelVO = new ArrayList<FormularioVO>();

	    	try
	    	{
	    		con = this.pool.obtenerConeccion();
	    		
	    		lstFormularios = this.grupos.getFormulariosNoGrupo(codGrupo, codEmp, con);
	    		
	    		
	    		/*Transformamos al VO de seleccion*/
	    		FormularioVO formSelVO;
	    		for (Formulario formulario : lstFormularios) {
	    			formSelVO = new FormularioVO(formulario);
	    			
	    			lstFormSelVO.add(formSelVO);
				}
	    		
	    	}catch(Exception e)
	    	{
	    		throw new ErrorInesperadoException();
	    	}
	    	finally
	    	{
	    		this.pool.liberarConeccion(con);
	    	}
	    	
	    	return lstFormSelVO;
	    	    	
	    }
	 
	 
	 @SuppressWarnings("unchecked")
		public Hashtable<String, FormularioVO> getFormulariosxUsuario(String usuario, String codEmp) throws ObteniendoFormulariosException, ConexionException 
		
	 	{
	    	Connection con = null;
	    	
	    	ArrayList<Formulario> lstFormularios = new ArrayList<Formulario>();
	    	Hashtable<String, FormularioVO> hLstFormSelVO = new Hashtable<String, FormularioVO>();

	    	try {
	    		con = this.pool.obtenerConeccion();
	    		
	    		
					lstFormularios = this.usuarios.getFormulariosxUsuario(usuario, codEmp, con);
			
	    		
	    		/*Transformamos al VO */
	    		FormularioVO formSelVO;
	    		for (Formulario formulario : lstFormularios) {
	    			formSelVO = new FormularioVO(formulario);
	    			
	    			hLstFormSelVO.put(formSelVO.getCodigo(), formSelVO);
				}
	    		
	    	} catch (ObteniendoFormulariosException e) {
	    		throw e;
	    		
			} catch (ConexionException e) {
				
				throw e;
			}
    		finally
	    	{
	    		this.pool.liberarConeccion(con);
	    	}
    	
	    	return hLstFormSelVO;
	    	    	
	    }
		
/////////////////////////////////FIN-GUPOS/////////////////////////////////
	 
/////////////////////////////////CLIENTES/////////////////////////////////
	 
	 
		/**
		 *Nos retorna todos los clientes para la empresa
		 *
		 */
	 @SuppressWarnings("unchecked")
		public ArrayList<ClienteVO> getClientesTodos(String codEmp) throws ObteniendoClientesException, ConexionException {
	    	
	    	Connection con = null;
	    	
	    	ArrayList<Cliente> lstClientes;
	    	ArrayList<ClienteVO> lstClientesVO = new ArrayList<ClienteVO>();
	    	    	
	    	try
	    	{
	    		con = this.pool.obtenerConeccion();
	    		
	    		lstClientes = this.clientes.getClientesTodos(con, codEmp);
	    		
	    		
	    		ClienteVO aux;
	    		for (Cliente cliente : lstClientes) 
				{
	    			aux = new ClienteVO();
	    			
	    			aux.setRazonSocial(cliente.getRazonSocial());
	    			aux.setNombre(cliente.getNombre());
	    			aux.setNombreDoc(cliente.getDocumento().getNombre());
	    			aux.setCodigoDoc(cliente.getDocumento().getCodigo());
	    			aux.setNumeroDoc(cliente.getDocumento().getNumero());
	    			aux.setCodigo(cliente.getCodigo());
	    			aux.setNombre(cliente.getNombre());
	    			aux.setTel(cliente.getTel());
	    			aux.setDireccion(cliente.getDireccion());
	    			aux.setFechaMod(cliente.getFechaMod());
	    			aux.setOperacion(cliente.getOperacion());
	    			aux.setUsuarioMod(cliente.getUsuarioMod());
	    			aux.setMail(cliente.getMail());
	    			aux.setActivo(cliente.isActivo());

	    			
	    			
	    			lstClientesVO.add(aux);
				}
		
	    	}catch(ObteniendoClientesException  e)
	    	{
	    		throw e;
	    		
	    	} catch (ConexionException e) {
				
	    		throw e;
	    	} 
	    	finally
	    	{
	    		this.pool.liberarConeccion(con);
	    	}
	    	    
	    	
	    	return lstClientesVO;
	    }	 
	 
	 
		/**
		 * Nos retorna todos los clientes activos para la empresa
		 *
		 */
	 @SuppressWarnings("unchecked")
		public ArrayList<ClienteVO> getClientesActivos(String codEmp) throws ObteniendoClientesException, ConexionException {
	    	
	    	Connection con = null;
	    	
	    	ArrayList<Cliente> lstClientes;
	    	ArrayList<ClienteVO> lstClientesVO = new ArrayList<ClienteVO>();
	    	    	
	    	try
	    	{
	    		con = this.pool.obtenerConeccion();
	    		
	    		lstClientes = this.clientes.getClientesActivos(con, codEmp);
	    		
	    		
	    		ClienteVO aux;
	    		for (Cliente cliente : lstClientes) 
				{
	    			aux = new ClienteVO();
	    			
	    			aux = cliente.retornarClienteVO();

	    			lstClientesVO.add(aux);
				}
		
	    	}catch(ObteniendoClientesException  e)
	    	{
	    		throw e;
	    		
	    	} catch (ConexionException e) {
				
	    		throw e;
	    	} 
	    	finally
	    	{
	    		this.pool.liberarConeccion(con);
	    	}
	    	    
	    	
	    	return lstClientesVO;
	    }	 

	 
	 public int insertarCliente(ClienteVO clienteVO, String codEmp) throws InsertandoClienteException, ConexionException, ExisteDocumentoClienteException  
	    {
	    	
	    	Connection con = null;
	    	boolean existe = false;
	    	int codigo = 0;
	    	
	    	try 
	    	{
				con = this.pool.obtenerConeccion();
				con.setAutoCommit(false);
				
		    	Cliente cliente = new Cliente(clienteVO); 
		    	
		    	/*Verificamos que no exista un cliente con el mismo documento*/
		    	if(!this.clientes.memberClienteDocumentoNuevo(cliente.getDocumento(), codEmp, con))
		    	{
		    		codigo = numeradores.getNumero(con, "04", codEmp);
		    		cliente.setCodigo(codigo);
		    		this.clientes.insertarCliente(cliente, codEmp, con);
		    		
		    		con.commit();
		    	}
		    	else{
		    		existe = true;
		    	}
		    		
	    	
	    	}catch(Exception InsertandoGrupoException)
	    	{
	    		try {
					con.rollback();
					
				} catch (SQLException e) {
					
					throw new InsertandoClienteException();
				}
	    		
	    		throw new InsertandoClienteException();
	    	}
	    	finally
	    	{
	    		pool.liberarConeccion(con);
	    	}
	    	if (existe){
	    		throw new ExisteDocumentoClienteException();
	    	}
	    	
	    	return codigo;
	    }
	 
	 public void editarCliente(ClienteVO clienteVO, String codEmp) throws  ConexionException, ModificandoClienteException, VerificandoClienteException, ExisteDocumentoClienteException     
		{
		    	
		    	Connection con = null;
		    	
		    	try 
		    	{
					con = this.pool.obtenerConeccion();
					con.setAutoCommit(false);
					
					Cliente cliente = new Cliente(clienteVO);
			    	
					/*Verificamos que exista el codigo del cliente*/
			    	if(this.clientes.memberCliente(cliente.getCodigo(), codEmp, con))
			    	{
			    		/*Verificamos que otro cliente no tenga el mismo documento*/
			    		if(!this.clientes.memberClienteDocumentoEditar(cliente.getDocumento(), cliente.getCodigo(), codEmp, con)){
			    			/*Modificamos el cliente*/
				    		this.clientes.modificarCliente(cliente, codEmp, con);
			    		}
			    		else /*Si existe otro cliente con el mismo documento retornamos exception*/
			    			throw new ExisteDocumentoClienteException();
			    		
			    			    		
			    		
			    		con.commit();
			    	}
			    	
			    	else
			    		throw new ModificandoClienteException();
		    	
		    	}catch(ModificandoClienteException| ExisteClienteExeption| ConexionException | SQLException  e)
		    	{
		    		try {
						con.rollback();
						
					} catch (SQLException e1) {
						
						throw new ConexionException();
					}
		    		throw new ModificandoClienteException();
		    	}
		    	finally
		    	{
		    		pool.liberarConeccion(con);
		    	}
		    }
	 
/////////////////////////////////FIN-CLIENTES/////////////////////////////////
	 
/////////////////////////////////DOCUMENTOS-DGI/////////////////////////////////	 
	 /**
		* Obtiene todos los documentos existentes
		*/
		@SuppressWarnings("unchecked")
		public ArrayList<DocumDGIVO> getDocumentosDGI() throws ObteniendoDocumentosException, ConexionException
		{
		
			Connection con = null;
			
			ArrayList<DocumDGI> lstDocumentos;
			ArrayList<DocumDGIVO> lstDocumentosVO = new ArrayList<DocumDGIVO>();
			
			try
			{
				con = this.pool.obtenerConeccion();
				
				lstDocumentos = this.documentosDGI.getDocumentos(con);
				
				
				DocumDGIVO aux;
				for (DocumDGI documento : lstDocumentos) 
				{
					aux = new DocumDGIVO();
					
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
		public void insertarDocumentoDGI(DocumDGIVO documentoVO) throws InsertandoDocumentoException, ConexionException, ExisteDocumentoException 
		{
		
			Connection con = null;
			boolean existe = false;
			
			try 
			{
				con = this.pool.obtenerConeccion();
				con.setAutoCommit(false);
				
				DocumDGI documento = new DocumDGI(documentoVO); 
			
				if(!this.documentosDGI.memberDocumento(documento.getCod_docucmento(), con)) 	{
				
					this.documentosDGI.insertarDocumento(documento, con);
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
		public void actualizarDocumentoDGI(DocumDGIVO documentoVO) throws ConexionException, NoExisteDocumentoException, ModificandoDocumentoException, ExisteDocumentoException  
		{
		
			Connection con = null;
			
			try 
			{
				con = this.pool.obtenerConeccion();
				con.setAutoCommit(false);
			
				DocumDGI documento = new DocumDGI(documentoVO);
			
			if(this.documentosDGI.memberDocumento(documento.getCod_docucmento(), con))	{
				this.documentosDGI.actualizarDocumento(documento, con);
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

/////////////////////////////////FIN-DOCUMENTOS-DGI/////////////////////////////////	
		
/////////////////////////////////FUNCIONARIOS/////////////////////////////////
		 
		 
	/**
	*Nos retorna todos los funcionarios para la empresa
	*
	*/
	@SuppressWarnings("unchecked")
	public ArrayList<FuncionarioVO> getFuncionariosTodos(String codEmp) throws ObteniendoFuncionariosException, ConexionException {
	
		Connection con = null;
		
		ArrayList<Funcionario> lstFuncionarios;
		ArrayList<FuncionarioVO> lstFuncionariosVO = new ArrayList<FuncionarioVO>();
		
		try
		{
		con = this.pool.obtenerConeccion();
		
		lstFuncionarios = this.funcionarios.getFuncionariosTodos(con, codEmp);
		
		
		FuncionarioVO aux;
		for (Funcionario func : lstFuncionarios) 
		{
		aux = new FuncionarioVO();
		
		aux.setNombre(func.getNombre());
		aux.setNombreDoc(func.getDocumento().getNombre());
		aux.setCodigoDoc(func.getDocumento().getCodigo());
		aux.setNumeroDoc(func.getDocumento().getNumero());
		aux.setCodigo(func.getCodigo());
		aux.setNombre(func.getNombre());
		aux.setTel(func.getTel());
		aux.setDireccion(func.getDireccion());
		aux.setFechaMod(func.getFechaMod());
		aux.setOperacion(func.getOperacion());
		aux.setUsuarioMod(func.getUsuarioMod());
		aux.setMail(func.getMail());
		aux.setActivo(func.isActivo());
		
		
		
		lstFuncionariosVO.add(aux);
	}

	}catch(ObteniendoFuncionariosException  e)
	{
		throw e;
	
	} catch (ConexionException e) {
	
		throw e;
	} 
	finally
	{
		this.pool.liberarConeccion(con);
	}
	
	
	return lstFuncionariosVO;
}	 

	
	/**
	* Nos retorna todos los funcionarios activos para la empresa
	*
	*/
	@SuppressWarnings("unchecked")
	public ArrayList<FuncionarioVO> getFuncionariosActivos(String codEmp) throws ObteniendoFuncionariosException, ConexionException {
	
		Connection con = null;
		
		ArrayList<Funcionario> lstFuncionarios;
		ArrayList<FuncionarioVO> lstFuncionariosVO = new ArrayList<FuncionarioVO>();
		
		try
		{
		con = this.pool.obtenerConeccion();
		
		lstFuncionarios = this.funcionarios.getFuncionariosActivos(con, codEmp);
		
		
		FuncionarioVO aux;
		for (Funcionario cliente : lstFuncionarios) 
		{
			aux = new FuncionarioVO();
			
			aux = cliente.retornarFuncionarioVO();
		
		lstFuncionariosVO.add(aux);
		}
		
		}catch(ObteniendoFuncionariosException  e)
		{
			throw e;
		
		} catch (ConexionException e) {
		
			throw e;
		} 
		finally
		{
			this.pool.liberarConeccion(con);
		}
		
		
		return lstFuncionariosVO;
	}	 

	
	/**
	*Ingresa un funcionario al sistema
	*
	*/	
	public int insertarFuncionario(FuncionarioVO funcionarioVO, String codEmp) throws InsertendoFuncionarioException, ConexionException, ExisteFuncionarioException, ExisteFuncionarioDocumetnoException  
	{
	
		Connection con = null;
		boolean existe = false;
		boolean existeDocumento = false;
		int codigo = 0;
		
		try 
		{
			con = this.pool.obtenerConeccion();
			con.setAutoCommit(false);
			
			Funcionario funcionario = new Funcionario(funcionarioVO); 
			
			funcionario.setCodigo(0);
			
			if(!this.funcionarios.memberFuncionario(funcionario.getCodigo(), codEmp, con))
			{
				/*Chequeamos que no haya un otro funcionario con el mismo documento*/
				if(!this.funcionarios.memberFuncionarioDocumentoNuevo(funcionario.getDocumento(),codEmp, con)){
					
					codigo = numeradores.getNumero(con, "04", codEmp);
					funcionario.setCodigo(codigo);
					this.funcionarios.insertarFuncionario(funcionario, codEmp, con);
					
					con.commit();
				}
				else {/*Si existe otro funcionario con el documento, setemos variable 
				 		en true para retornar la exception*/
					existeDocumento = true;
				}		
			}
			else{
				existe = true;
			}
		
		
		}catch(Exception InsertendoFuncionarioException)
		{
			try {
				con.rollback();
			
			} catch (SQLException e) {
			
				throw new InsertendoFuncionarioException();
			}
		
			throw new InsertendoFuncionarioException();
		}
		finally
		{
			pool.liberarConeccion(con);
		}
		if (existe){
			throw new ExisteFuncionarioException();
		}else if(existeDocumento){ /*Si existe el documento en otro funcionario retornamos
		 							exception*/
			throw new ExisteFuncionarioDocumetnoException();
		}
		
		return codigo;
	}
	
	/**
	*	Modificamos un funcionario al sistema
	 * @throws ExisteFuncionarioDocumetnoException 
	*
	*/	
	public void editarFuncionario(FuncionarioVO funcionarioVO, String codEmp) throws  ConexionException, ModificandoFuncionarioException, ExisteFuncionarioException, ExisteFuncionarioDocumetnoException     
	{
	
		Connection con = null;
		
		try 
		{
			con = this.pool.obtenerConeccion();
			con.setAutoCommit(false);
			
			Funcionario funcionario = new Funcionario(funcionarioVO);
			
			if(this.funcionarios.memberFuncionario(funcionario.getCodigo(), codEmp, con))
			{
				/*Verificamos que no exista otro funcionario con el mismo documento*/
				if(!this.funcionarios.memberFuncionarioDocumentoEditar(funcionario.getDocumento(), funcionario.getCodigo(), codEmp, con)){
					
					this.funcionarios.modificarFuncionario(funcionario, codEmp, con);
					con.commit();
				}
				else{ /*Si existe otro funcionario con el documento retornamos exception*/
					
					throw new ExisteFuncionarioDocumetnoException();
				}
				
			}
			
			else
				throw new ModificandoClienteException();
		
		}catch(ModificandoClienteException| ExisteFuncionarioException| ConexionException | SQLException  e)	{
			try {
				con.rollback();
				
			} catch (SQLException e1) {
			
				throw new ConexionException();
			}
			throw new ModificandoFuncionarioException();
		}
		finally
		{
			pool.liberarConeccion(con);
		}
	}

/////////////////////////////////FIN-FUNCIONARIOS/////////////////////////////////
	
/////////////////////////////////PERMISOS/////////////////////////////////
	/**
	 * Dado una operacion y formulario retorna true
	 * si tiene permisos
	 * @throws ConexionException 
	 * 
	 */
	public boolean permisoEnFormulario(UsuarioPermisosVO permisosForm) throws ObteniendoPermisosException, ConexionException
	{
		
		Connection con = null;
		
		boolean tienePermiso = false;
		
		//FormularioVO frm = lstFormularios.get(formulario);
		
		try 
		{
			con = this.pool.obtenerConeccion();
			
			
			
		Formulario form = this.usuarios.getPermisoFormularioOperacionUsuario(permisosForm.getUsuario(), permisosForm.getCodEmp(), permisosForm.getFormulario(), con);
		
		if(form != null)
		{
			switch(permisosForm.getOperacion())
			{
				case VariablesPermisos.OPERACION_LEER : tienePermiso = form.isLeer();
				break;
				case VariablesPermisos.OPERACION_NUEVO_EDITAR : tienePermiso = form.isNuevoEditar();
				break;
				case VariablesPermisos.OPERACION_BORRAR : tienePermiso = form.isBorrar();
				break;
			}
		}
		
		
		}catch(ObteniendoPermisosException e)
		{
			throw e;
			
		}finally{
			pool.liberarConeccion(con);
		}
		
		return tienePermiso;
	}

/////////////////////////////////BANCOS/////////////////////////////////
	 
/**
*Nos retorna todos los bancos para la empresa
 * @throws ObteniendoCuentasBcoException 
*
*/
@SuppressWarnings("unchecked")
public ArrayList<BancoVO> getBancosTodos(String codEmp) throws ObteniendoBancosException, ConexionException, ObteniendoCuentasBcoException {
	
	Connection con = null;
	
	ArrayList<Banco> lstbancos;
	ArrayList<BancoVO> lstBancoVO = new ArrayList<BancoVO>();
	
	try
	{
		con = this.pool.obtenerConeccion();
		
		lstbancos = this.bancos.getBancosTodos(con, codEmp);
		
		for (Banco banco : lstbancos) 
		{
			lstBancoVO.add(banco.getBancoVO());
		}
	
	}catch(ObteniendoBancosException  e)
	{
		throw e;
	
	} catch (ConexionException e) {
	
		throw e;
	} 
	finally
	{
		this.pool.liberarConeccion(con);
	}
	
	
	return lstBancoVO;
}	 


	/**
	* Nos retorna todos los bancos para la empresa
	 * @throws ObteniendoCuentasBcoException 
	*
	*/
	@SuppressWarnings("unchecked")
	public ArrayList<CtaBcoVO> getCtaBcoActivosxBco(String codEmp, String codBco) throws  ConexionException, ObteniendoCuentasBcoException {

		Connection con = null;
		
		ArrayList<CtaBco> lstbancos;
		ArrayList<CtaBcoVO> lstCtasBancoVO = new ArrayList<CtaBcoVO>();
		
		try
		{
			con = this.pool.obtenerConeccion();
	
			lstbancos = this.bancos.getCtaBcoActivos(con, codEmp, codBco);
			
			for (CtaBco cta : lstbancos) 
			{
				lstCtasBancoVO.add(cta.getCtaBcoVO());
			}
		
		}catch(ObteniendoCuentasBcoException  e)
		{
			throw e;
		
		} catch (ConexionException e) {
		
			throw e;
		} 
		finally
		{
			this.pool.liberarConeccion(con);
		}
	
	
		return lstCtasBancoVO;
}	 

	/**
	* Nos retorna todos los clientes activos para la empresa
	 * @throws ObteniendoCuentasBcoException 
	*
	*/
	@SuppressWarnings("unchecked")
	public ArrayList<BancoVO> getBancosActivos(String codEmp) throws ObteniendoBancosException, ConexionException, ObteniendoCuentasBcoException {

	Connection con = null;
	
	ArrayList<Banco> lstbancos;
	ArrayList<BancoVO> lstBancoVO = new ArrayList<BancoVO>();
	
	try
	{
		con = this.pool.obtenerConeccion();
		
		lstbancos = this.bancos.getBancosActivos(con, codEmp);
		
		for (Banco banco : lstbancos) 
		{
			lstBancoVO.add(banco.getBancoVO());
		}
	
	}catch(ObteniendoBancosException  e)
	{
		throw e;
	
	} catch (ConexionException e) {
	
		throw e;
	} 
	finally
	{
		this.pool.liberarConeccion(con);
	}
	
	
	return lstBancoVO;
}	 

public void insertarBanco(BancoVO bancoVO, String codEmp) throws InsertandoBancoException, ConexionException, ExisteBancoException{

	Connection con = null;
	boolean existe = false;
	
	try 
	{
		con = this.pool.obtenerConeccion();
		con.setAutoCommit(false);
		
		Banco banco = new Banco(bancoVO); 
		
		/*Verificamos que no exista un cliente con el mismo documento*/
		if(!this.bancos.memberBanco(bancoVO.getCodigo(), codEmp, con))
		{
			this.bancos.insertarBanco(banco, codEmp, con);
		
			con.commit();
		}
		else{
			existe = true;
		}
	
	}catch(Exception e){
		
		try {
			con.rollback();
		
		} catch (SQLException ex) {
		
			throw new InsertandoBancoException();
		}
	
		throw new InsertandoBancoException();
	}
	finally
	{
		pool.liberarConeccion(con);
	}
	if (existe){
		throw new ExisteBancoException();
	}
	
}

public void editarBanco(BancoVO bancoVO, String codEmp) throws  ConexionException, ModificandoBancoException, VerificandoBancosException, ExisteBancoException, ModificandoCuentaBcoException{

	Connection con = null;
	
	try 
	{
		con = this.pool.obtenerConeccion();
		con.setAutoCommit(false);
		
		Banco banco = new Banco(bancoVO);
		
		/*Verificamos que exista el codigo del cliente*/
		if(this.bancos.memberBanco(banco.getCodigo(), codEmp, con))
		{
			this.bancos.modificarBanco(banco, codEmp, con);
			
			con.commit();
		}
		else
		throw new ModificandoBancoException();
	
	}catch(ModificandoBancoException| ExisteBancoException| ConexionException | SQLException  e)
	{
		try {
		con.rollback();
		
		} catch (SQLException e1) {
		
		throw new ConexionException();
		}
			throw new ModificandoBancoException();
	}
	finally
	{
		pool.liberarConeccion(con);
	}
}

/////////////////////////////////FIN-BANCOS/////////////////////////////////

/////////////////////////////////INICIO-INGRESO COBRO//////////////////////
/**
*Nos retorna los ingreso de cobro del sistema para la empresa
 * 
*
*/
@SuppressWarnings("unchecked") 
public ArrayList<IngresoCobroVO> getIngresoCobroTodos(String codEmp) throws ObteniendoIngresoCobroException, ConexionException {
	
	Connection con = null;
	
	ArrayList<IngresoCobro> lst;
	ArrayList<IngresoCobroVO> lstVO = new ArrayList<IngresoCobroVO>();
	
	try
	{
		con = this.pool.obtenerConeccion();
		
		lst = this.ingresoCobro.getIngresoCobroTodos(con, codEmp);
		
		for (IngresoCobro ing : lst) 
		{
			IngresoCobroVO aux = ing.retornarIngresoCobroVO();
			

			
			lstVO.add(aux);
		}
	
	}catch(ObteniendoIngresoCobroException  e)
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

/**
*Nos retorna los ingreso de cobro del sistema para la empresa
 * 
*
*/
@SuppressWarnings("unchecked") 
public ArrayList<IngresoCobroVO> getIngresoCobroTodosOtro(String codEmp) throws ObteniendoIngresoCobroException, ConexionException {
	
	Connection con = null;
	
	ArrayList<IngresoCobro> lst;
	ArrayList<IngresoCobroVO> lstVO = new ArrayList<IngresoCobroVO>();
	
	try
	{
		con = this.pool.obtenerConeccion();
		
		lst = this.ingresoCobro.getIngresoCobroTodosOtro(con, codEmp); 
		
		for (IngresoCobro ing : lst) 
		{
			IngresoCobroVO aux = ing.retornarIngresoCobroVO();
			

			
			lstVO.add(aux);
		}
	
	}catch(ObteniendoIngresoCobroException  e)
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


public void insertarIngresoCobro(IngresoCobroVO ingVO, String codEmp) throws InsertandoIngresoCobroException, ConexionException, ExisteIngresoCobroException{

	Connection con = null;
	boolean existe = false;
	Integer codigo;
	NumeradoresVO codigos = new NumeradoresVO();


	try 
	{
		con = this.pool.obtenerConeccion();
		con.setAutoCommit(false);
		
		IngresoCobro ing = new IngresoCobro(ingVO); 
		Cotizacion cotiAux;
		double impMoCtaBco = 0; /*Variable se utiliza si es banco*/
		
		if(!ing.getmPago().equals("Caja")) /*Si es banco*/
		{
			 /*Obtenemos el importe moneda operativa de la cuenta del banco*/
			 impMoCtaBco = this.importeMOCtaBanco(ing);
		}
		
		//Obtengo numerador de gastos
		codigos.setCodigo(numeradores.getNumero(con, "ingcobro", codEmp)); //Ingreso Cobro 
		codigos.setNumeroTrans(numeradores.getNumero(con, "03", codEmp)); //nro trans
		
		ing.setNroDocum(codigos.getCodigo()); /*Seteamos el nroDocum*/
		ing.setNroTrans(codigos.getNumeroTrans()); /*Seteamos el nroTrans*/
		ingVO.setNroTrans(codigos.getNumeroTrans()); /*Seteamos el nroTrans al VO para obtener el DocumSaldo*/ 
		ingVO.setNroDocum(codigos.getCodigo()); /*Seteamos el nroDocum*/
		
		/*Verificamos que no exista un cobro con el mismo numero*/
		if(!this.ingresoCobro.memberIngresoCobro(ing.getNroDocum(), codEmp, con))
		{
			/*Ingresamos el cobro*/
			
			if(ing.getDetalle().size()< 1) /*Si no tiene detalle es otro cobro*/
				ing.setCodDocum("otrcobro");
				
			this.ingresoCobro.insertarIngresoCobro(ing, con);
			
			if(ing.getDetalle().size()> 0) /*Si tiene detalle porque viene del ingreso cobro, de lo contrario
			 								se consume desde el ingteso cobro otros*/
			{
				
				/*Para cada linea ingresamos el saldo*/
				for (DocumDetalle docum : ing.getDetalle()) {
					
					if(docum.getCodDocum().equals("Gasto")){ /*Para los gastos modificamos el saldo al documento*/
						/*Signo -1 porque resta al saldo del documento el cobro*/
						this.saldos.modificarSaldo(docum, -1, ingVO.getTcMov(), con);
					}
					else if(docum.getCodDocum().equals("Proc")) /*Modificamos el saldo para el proceso ingresado*/
					{
						
						docum.setCodDocum(ing.getCodDocum());
						docum.setSerieDocum(ing.getSerieDocum());
						docum.setNroDocum(ing.getNroDocum());
						docum.setTitInfo(ing.getTitInfo());
						docum.setNroTrans(ing.getNroTrans());
						docum.setFecDoc(ing.getFecDoc());
						docum.setFecValor(ing.getFecValor());
						docum.setCodEmp(ing.getCodEmp());
						
						
						/*Para el proceso esl signo es 1 porque subo el saldo a la cuenta del proceso*/
						this.saldosProceso.modificarSaldo(docum, 1, ingVO.getTcMov(), con);
					}
					
				}
			}
		
			
			/*Si el ingreso de cobro es con cheque, ingresamos el cheque*/
			if(ingVO.getCodDocRef().equals("cheqrec"))
			{
				/*Primero obtenemos el DatosDocum para el cheque dado el ingreso cobro*/

				/*Insertamos el cheque y saldo*/
				DatosDocumVO auxCheque = ConvertirDocumento.getDatosDocumChequeDadoIngCobro(ingVO);
				
				
				auxCheque.setCodMoneda(ing.getCuenta().getCodMoneda());
			    auxCheque.setNacional(ing.getCuenta().isNacional());
			    auxCheque.setImpTotMo(impMoCtaBco); /*Importe en moneda operativa de la cuenta bco*/
				
				/*Ingresamos el cheque y su saldo*/
				this.insertarChequeIntFachada(auxCheque, con);

			}
			
			/*Si es cheque no ingresamos el saldo al banco, esto se realiza cuando
			 * se ingrese el deposito del cheque, para transferencia y caja si se mueven
			 * sus respectivas cuentas*/
			if(!ingVO.getCodDocRef().equals("cheqrec")) 
			{
				/*Ingresamos el saldo a la cuenta (Banco o caja)*/
				DocumSaldo saldoCuenta = ConvertirDocumento.getDocumSaldoSaCuentasIngCobro(ingVO);
				
				if(!ing.getmPago().equals("Caja")) /*Si es banco*/
					saldoCuenta.setImpTotMo(impMoCtaBco); /*Importe en moneda operativa de la cuenta bco*/ 
				this.saldosCuentas.insertarSaldoCuenta(saldoCuenta, con);
			}
			
			con.commit();
		}
		else{
			existe = true;
		}
	
	}catch(Exception e){
		
		try {
			con.rollback();
		
		} catch (SQLException ex) {
		
			throw new InsertandoIngresoCobroException();
		}
	
		throw new InsertandoIngresoCobroException();
	}
	finally
	{
		pool.liberarConeccion(con);
	}
	if (existe){
		throw new ExisteIngresoCobroException();
	}
}

public void eliminarIngresoCobro(IngresoCobroVO ingVO, String codEmp) throws InsertandoIngresoCobroException, ConexionException, ExisteIngresoCobroException, NoExisteIngresoCobroException{

	Connection con = null;
	boolean existe = false;
	Integer codigo;
	NumeradoresVO codigos = new NumeradoresVO();


	try 
	{
		con = this.pool.obtenerConeccion();
		con.setAutoCommit(false);
		
		IngresoCobro ing = new IngresoCobro(ingVO); 
		Cotizacion cotiAux;
		
		/*Obtenemos el importe moneda operativa de la cuenta del banco*/
		double impMoCtaBco = 0;
		
		if(!ing.getmPago().equals("Caja")) /*Si es banco*/
		{
			 /*Obtenemos el importe moneda operativa de la cuenta del banco*/
			 impMoCtaBco = this.importeMOCtaBanco(ing);
		}
		
		/*Verificamos no exista un el cobro*/
		if(this.ingresoCobro.memberIngresoCobro(ing.getNroDocum(), codEmp, con))
		{
			
			
			if(ing.getDetalle().size()> 0) /*Si tiene detalle porque viene del ingreso cobro, de lo contrario
											se consume desde el ingteso cobro otros*/
			{
				/*Para cada linea volvemos el saldo sin este cobro*/
				for (DocumDetalle docum : ing.getDetalle()) {
					
					if(docum.getCodDocum().equals("Gasto")){ /*Para los gastos modificamos el saldo al documento*/
						
						/*Signo 1 para que restarue los saldos del documento sin este cobro*/
						this.saldos.modificarSaldo(docum, 1, ingVO.getTcMov(), con);
					}
					else if(docum.getCodDocum().equals("Proceso")) /*Modificamos el saldo para el proceso ingresado*/
					{
						/*Signo -1 para que restarue los saldos del proceso sin este cobro*/
						this.saldosProceso.modificarSaldo(docum, -1, ingVO.getTcMov(), con);
					}
				}
				
			}
			
			/*Si el ingreso de cobro es con cheque, eliminamos el cheque de los saldos y de los cheques*/
			if(ingVO.getCodDocRef().equals("cheqrec"))
			{
				/*Primero obtenemos el DatosDocum para el cheque dado el ingreso cobro*/
				DatosDocumVO auxCheque = ConvertirDocumento.getDatosDocumChequeDadoIngCobro(ingVO);
				
				/*Eliminamos el saldo para el cheque */
				DatosDocum auxCheque2 = new DatosDocum(auxCheque);
				this.saldos.eliminarSaldo(auxCheque2, con);
			
				auxCheque.setImpTotMo(impMoCtaBco);
			    auxCheque.setCodMoneda(ing.getCuenta().getCodMoneda());
			    auxCheque.setNacional(ing.getCuenta().isNacional());
				
				/*Eliminamos el cheque de tabla base*/                                           
				DatosDocum chequeL = new DatosDocum(auxCheque); /*Lo convertimos a objeto de logica para pasarlo al DAO*/
				
				
				this.cheques.eliminarCheque(chequeL, con);
			}
			
			
			/*Si es cheque no ingresamos el saldo al banco, esto se realiza cuando
			 * se ingrese el deposito del cheque, para transferencia y caja si se mueven
			 * sus respectivas cuentas*/
			if(!ingVO.getCodDocRef().equals("cheqrec")) 
			{
				/*Bajamos el saldo a la cuenta (Banco o caja)*/
				/*Obtenemos el objeto DocumSaldo dado el ingreso de cobro*/
				DocumSaldo saldoCuenta = ConvertirDocumento.getDocumSaldoChequeDadoIngCobro(ingVO);
				
				if(!ing.getmPago().equals("Caja")) /*Si es banco*/
					saldoCuenta.setImpTotMo(impMoCtaBco);
				
				this.saldosCuentas.eliminarSaldoCuenta(saldoCuenta, con);
			}
			
			
			/*Una vez hechos todos los movimientos de saldos y documentos
			 * procedemos a eliminar el cobro*/
			this.ingresoCobro.eliminarIngresoCobro(ing, con); 
			
			con.commit();
		}
		else{
			throw new NoExisteIngresoCobroException();
		}
	
	}catch(Exception e){
		
		try {
			con.rollback();
		
		} catch (SQLException ex) {
		
			throw new InsertandoIngresoCobroException();
		}
	
		throw new InsertandoIngresoCobroException();
	}
	finally
	{
		pool.liberarConeccion(con);
	}
	if (existe){
		throw new ExisteIngresoCobroException();
	}
}

public void modificarIngresoCobro(IngresoCobroVO ingVO, IngresoCobroVO copiaVO) throws  ConexionException, ModificandoIngresoCobroException, ExisteIngresoCobroException, NoExisteIngresoCobroException{

	Connection con = null;
	
	try 
	{
		con = this.pool.obtenerConeccion();
		con.setAutoCommit(false);
		
		IngresoCobro ing = new IngresoCobro(ingVO);
		
		/*Verificamos que exista el nro de cobro*/
		if(this.ingresoCobro.memberIngresoCobro(ing.getNroDocum(), ingVO.getCodEmp(), con))
		{
			/*Primero eliminamos la transaccion, con la copia, para poder detectar las lineas
			 * eliminadas*/
			this.eliminarIngresoCobroxModificacion(copiaVO, con); 
			
			/*Luego insertamos el cobro con las modificaciones realizadas*/
			this.insertarIngresoCobroxModificacion(ingVO, con); 
			
			con.commit();
		}
		else
		throw new ModificandoIngresoCobroException();
	
	}catch(ModificandoIngresoCobroException| ExisteIngresoCobroException| ConexionException | SQLException  e)
	{
		try {
		con.rollback();
		
		} catch (SQLException e1) {
		
		throw new ConexionException();
		}
			throw new ModificandoIngresoCobroException();
	}
	finally
	{
		pool.liberarConeccion(con);
	}
}

/**
 * Este metodo lo utilizamos para el modificar cobro.
 * lo utilizamos internamente en fachada
 * Primero eliminamos el cobro y luego lo volvemos a insertar con 
 * las nuevas modificaciones
 * @throws ModificandoIngresoCobroException 
 * 
 */
 private void insertarIngresoCobroxModificacion(IngresoCobroVO ingVO, Connection con) throws ConexionException, ExisteIngresoCobroException, ModificandoIngresoCobroException{
	
	boolean existe = false;
	Integer codigo;

	try 
	{
		IngresoCobro ing = new IngresoCobro(ingVO); 
		Cotizacion cotiAux;
		
		 /*Obtenemos el importe moneda operativa de la cuenta del banco*/
		 double impMoCtaBco = 0;
		 
		 if(!ing.getmPago().equals("Caja")) /*Si es banco*/
		 {
			 /*Obtenemos el importe moneda operativa de la cuenta del banco*/
			 impMoCtaBco = this.importeMOCtaBanco(ing);
		 }
		
		/*Verificamos que no exista un cobro con el mismo numero*/
		if(!this.ingresoCobro.memberIngresoCobro(ing.getNroDocum(), ing.getCodEmp(), con))
		{
			/*Ingresamos el cobro*/
			this.ingresoCobro.insertarIngresoCobro(ing, con);
			
			
			if(ing.getDetalle().size() > 0) /*Si tiene detalle porque viene del ingreso cobro, de lo contrario
											se consume desde el ingteso cobro otros*/
			{
				
				for (DocumDetalle docum : ing.getDetalle()) {
					
					if(docum.getCodDocum().equals("Gasto")){ /*Para los gastos modificamos el saldo al documento*/
						/*Signo -1 porque resta al saldo del documento el cobro*/
						this.saldos.modificarSaldo(docum, -1, ingVO.getTcMov(), con);
					}
					else if(docum.getCodDocum().equals("Proc")) /*Modificamos el saldo para el proceso ingresado*/
					{
						
						docum.setCodDocum(ing.getCodDocum());
						docum.setSerieDocum(ing.getSerieDocum());
						docum.setNroDocum(ing.getNroDocum());
						docum.setTitInfo(ing.getTitInfo());
						docum.setNroTrans(ing.getNroTrans());
						docum.setFecDoc(ing.getFecDoc());
						docum.setFecValor(ing.getFecValor());
						docum.setCodEmp(ing.getCodEmp());
						
						/*EL signo es 1 en proceso para que le agregue saldo al proceso*/
						this.saldosProceso.modificarSaldo(docum, 1, ingVO.getTcMov(), con);
					}
				}
				
			}
			/*Para cada linea ingresamos el saldo*/
			
			
			/*Si el ingreso de cobro es con cheque, ingresamos el cheque*/
			if(ingVO.getCodDocRef().equals("cheqrec"))
			{
				/*Primero obtenemos el DatosDocum para el cheque dado el ingreso cobro*/

				DatosDocumVO auxCheque = ConvertirDocumento.getDatosDocumChequeDadoIngCobro(ingVO);
				
				auxCheque.setCodMoneda(ing.getCuenta().getCodMoneda());
			    auxCheque.setNacional(ing.getCuenta().isNacional());
			     /*Obtenemos el importe moneda operativa de la cuenta del banco*/
			    
			    auxCheque.setImpTotMo(impMoCtaBco);
			    
				this.insertarChequeIntFachada(auxCheque, con);

				/*Ingresamos el saldo para el cheque */
				DatosDocum auxCheque2 = new DatosDocum(auxCheque);
				this.saldos.modificarSaldo(auxCheque2,1, ingVO.getTcMov() , con);
			}
			
			/*Si es cheque no ingresamos el saldo al banco, esto se realiza cuando
			 * se ingrese el deposito del cheque, para transferencia y caja si se mueven
			 * sus respectivas cuentas*/
			if(!ingVO.getCodDocRef().equals("cheqrec")) 
			{
				/*Ingresamos el saldo a la cuenta (Banco o caja)*/
				DocumSaldo saldoCuenta = ConvertirDocumento.getDocumSaldoSaCuentasIngCobro(ingVO);
				
				 if(!ing.getmPago().equals("Caja")) /*Si es banco*/
					 saldoCuenta.setImpTotMo(impMoCtaBco);
				 
				this.saldosCuentas.insertarSaldoCuenta(saldoCuenta, con);
			}

		}
		else{
			existe = true;
		}
	
	}catch(Exception e){
		
		throw new ModificandoIngresoCobroException();
	}
	if (existe){
		throw new ExisteIngresoCobroException();
	}
	
}
 
 /**
  * Este metodo lo utilizamos para el modificar cobro.
  * lo utilizamos internamente en fachada
  * Primero eliminamos el cobro y luego lo volvemos a insertar con 
  * las nuevas modificaciones
 * @throws ModificandoIngresoCobroException 
  * 
  */
 private void eliminarIngresoCobroxModificacion(IngresoCobroVO ingVO, Connection con) throws ConexionException, ExisteIngresoCobroException, NoExisteIngresoCobroException, ModificandoIngresoCobroException{

		boolean existe = false;
		Integer codigo;
	
		try 
		{
			IngresoCobro ing = new IngresoCobro(ingVO); 
			Cotizacion cotiAux;
			
			/*Obtenemos el importe moneda operativa de la cuenta del banco*/
			double impMoCtaBco = 0;
			
			 if(!ing.getmPago().equals("Caja")) /*Si es banco*/
			 {
				 /*Obtenemos el importe moneda operativa de la cuenta del banco*/
				 impMoCtaBco = this.importeMOCtaBanco(ing);
			 }
			
			/*Verificamos no exista un el cobro*/
			if(this.ingresoCobro.memberIngresoCobro(ing.getNroDocum(), ing.getCodEmp(), con))
			{
				
				
				if(ing.getDetalle().size() > 0) /*Si tiene detalle porque viene del ingreso cobro, de lo contrario
												se consume desde el ingteso cobro otros*/
				{
					/*Para cada linea volvemos el saldo sin este cobro*/
					for (DocumDetalle docum : ing.getDetalle()) {
						
						if(docum.getCodDocum().equals("Gasto")){ /*Para los gastos modificamos el saldo al documento*/
							
							/*Signo 1 para que restarue los saldos del documento sin este cobro*/
							this.saldos.modificarSaldo(docum, 1, ingVO.getTcMov(), con);
						}
						else if(docum.getCodDocum().equals("Proceso")) /*Modificamos el saldo para el proceso ingresado*/
						{
							/*Signo -1 para que le quite el saldo*/
							this.saldosProceso.modificarSaldo(docum, -1, ingVO.getTcMov(), con);
						}
					}
				}
				
				/*Si el ingreso de cobro es con cheque, eliminamos el cheque de los saldos y de los cheques*/
				if(ingVO.getCodDocRef().equals("cheqrec"))
				{
					/*Primero obtenemos el DatosDocum para el cheque dado el ingreso cobro*/
					DatosDocumVO auxCheque = ConvertirDocumento.getDatosDocumChequeDadoIngCobro(ingVO);
					
					/*Eliminamos el saldo para el cheque */
					DatosDocum auxCheque2 = new DatosDocum(auxCheque);
					this.saldos.eliminarSaldo(auxCheque2, con);
					
					auxCheque.setImpTotMo(impMoCtaBco);
				    auxCheque.setCodMoneda(ing.getCuenta().getCodMoneda());
				    auxCheque.setNacional(ing.getCuenta().isNacional());
					/*Eliminamos el cheque de tabla base*/                                           
					DatosDocum chequeL = new DatosDocum(auxCheque); /*Lo convertimos a objeto de logica para pasarlo al DAO*/
					this.cheques.eliminarCheque(chequeL, con);
				}
				
				
				/*Si es cheque no ingresamos el saldo al banco, esto se realiza cuando
				 * se ingrese el deposito del cheque, para transferencia y caja si se mueven
				 * sus respectivas cuentas*/
				if(!ingVO.getCodDocRef().equals("cheqrec")) 
				{
					/*Bajamos el saldo a la cuenta (Banco o caja)*/
					/*Obtenemos el objeto DocumSaldo dado el ingreso de cobro*/
					DocumSaldo saldoCuenta = ConvertirDocumento.getDocumSaldoChequeDadoIngCobro(ingVO);
					
					if(!ing.getmPago().equals("Caja")) /*Si es banco*/
						saldoCuenta.setImpTotMo(impMoCtaBco);
					
					this.saldosCuentas.eliminarSaldoCuenta(saldoCuenta, con);
				}
				/*Una vez hechos todos los movimientos de saldos y documentos
				 * procedemos a eliminar el cobro*/
				this.ingresoCobro.eliminarIngresoCobro(ing, con); 
				
			
			}
			else{
				throw new NoExisteIngresoCobroException();
			}
		
		}catch(Exception e){
			
			throw new ModificandoIngresoCobroException();
		}
		if (existe){
			throw new ExisteIngresoCobroException();
		}
		
	}

/////////////////////////////////FIN-INGRESO COBRO/////////////////////////
 
/////////////////////////////////INICIO-EGRESO COBRO//////////////////////
	/**
	*Nos retorna los ingreso de cobro del sistema para la empresa
	* 
	*
	*/
	@SuppressWarnings("unchecked") 
	public ArrayList<IngresoCobroVO> getEgresoCobroTodos(String codEmp) throws ObteniendoEgresoCobroException, ConexionException {
	
		Connection con = null;
		
		ArrayList<IngresoCobro> lst;
		ArrayList<IngresoCobroVO> lstVO = new ArrayList<IngresoCobroVO>();
		
		try
		{
			con = this.pool.obtenerConeccion();
			
			lst = this.egresoCobro.getEgresoCobroTodos(con, codEmp);
			
			for (IngresoCobro ing : lst) 
			{
				IngresoCobroVO aux = ing.retornarIngresoCobroVO();
			
				lstVO.add(aux);
			}
			
			}catch(ObteniendoEgresoCobroException  e)
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
	
	/**
	*Nos retorna los ingreso de cobro del sistema para la empresa
	* 
	*
	*/
	@SuppressWarnings("unchecked") 
	public ArrayList<IngresoCobroVO> getEgresoCobroOtroTodos(String codEmp) throws ObteniendoEgresoCobroException, ConexionException {
	
		Connection con = null;
		
		ArrayList<IngresoCobro> lst;
		ArrayList<IngresoCobroVO> lstVO = new ArrayList<IngresoCobroVO>();
		
		try
		{
			con = this.pool.obtenerConeccion();
			
			lst = this.egresoCobro.getEgresoCobroOtroTodos(con, codEmp); 
			
			for (IngresoCobro ing : lst) 
			{
				IngresoCobroVO aux = ing.retornarIngresoCobroVO();
			
				lstVO.add(aux);
			}
			
			}catch(ObteniendoEgresoCobroException  e)
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
	
	
	public void insertarEgresoCobro(IngresoCobroVO ingVO, String codEmp) throws InsertandoEgresoCobroException, ConexionException, ExisteEgresoCobroException{
	
		Connection con = null;
		boolean existe = false;
		NumeradoresVO codigos = new NumeradoresVO();
		
		
		try 
		{
			con = this.pool.obtenerConeccion();
			con.setAutoCommit(false);
			
			IngresoCobro ing = new IngresoCobro(ingVO); 
			Cotizacion cotiAux;
			
			/*Obtenemos el importe moneda operativa de la cuenta del banco*/
			double impMoCtaBco = 0; 
			
			 if(!ing.getmPago().equals("Caja")) /*Si es banco*/
			 {
				 /*Obtenemos el importe moneda operativa de la cuenta del banco*/
				 impMoCtaBco = this.importeMOCtaBanco(ing);
			 }
			
			//Obtengo numerador de egreso de gasto
			codigos.setCodigo(numeradores.getNumero(con, "egrcobro", codEmp)); //Ingreso Cobro 
			codigos.setNumeroTrans(numeradores.getNumero(con, "03", codEmp)); //nro trans
			
			ing.setNroDocum(codigos.getCodigo()); /*Seteamos el nroDocum*/
			ing.setNroTrans(codigos.getNumeroTrans()); /*Seteamos el nroTrans*/
			ingVO.setNroTrans(codigos.getNumeroTrans()); /*Seteamos el nroTrans al VO para obtener el DocumSaldo*/ 
			ingVO.setNroDocum(codigos.getCodigo()); /*Seteamos el nroDocum*/
			
			/*Verificamos que no exista un cobro con el mismo numero*/
			if(!this.egresoCobro.memberEgresoCobro(ing.getNroDocum(), codEmp, con))
			{
			
				
				int i = 0;
				/*Para cada linea ingresamos el saldo*/
				for (DocumDetalle docum : ing.getDetalle()) {
				
					if(docum.getCodDocum().equals("Gasto")){ /*Para los gastos modificamos el saldo al documento*/
						
						/*Seteamos el nroTrans con el del cabezal*/
						docum.setNroTrans(codigos.getNumeroTrans());
						
						/*Seteamos el nroDocum del gasto*/
						docum.setNroDocum(numeradores.getNumero(con, "02", codEmp));
						
						
						/*Ingresamos cada uno de los Gastos*/
						//this.gastos.insertarGasto((Gasto)docum, codEmp, con);
						this.gastos.insertarGasto(docum, codEmp, con);
						
						//Genero saldo del gasto 
						this.saldos.insertarSaldo(docum, con);
						
						ing.getDetalle().get(i).setNroDocum(docum.getNroDocum());
						
						i++;
					}
					/*else if(docum.getCodDocum().equals("Proceso")) //Modificamos el saldo para el proceso ingresado
					{
						//Para el proceso esl signo es 1 porque subo el saldo a la cuenta del proceso
						this.saldosProceso.modificarSaldo(docum, 1, ingVO.getTcMov(), con);
					}
					*/
				}
				
				
				/*Si el ingreso de cobro es con cheque, ingresamos el cheque*/
				if(ingVO.getCodDocRef().equals("cheqemi")) 
				{
					/*Primero obtenemos el DatosDocum para el cheque dado el ingreso cobro*/
					
					/*Insertamos el cheque*/
					DatosDocumVO auxCheque = ConvertirDocumento.getDatosDocumChequeDadoIngCobro(ingVO);
					
					
					auxCheque.setCodMoneda(ing.getCuenta().getCodMoneda());
					auxCheque.setNacional(ing.getCuenta().isNacional());
					
					
					auxCheque.setImpTotMo(impMoCtaBco);
					
					this.insertarChequeIntFachada(auxCheque, con); /*Ingresa el cheque pero no el saldo*/
				
				}
				
				/*Ingresamos el saldo a la cuenta (Banco o caja)*/
				DocumSaldo saldoCuenta = ConvertirDocumento.getDocumSaldoSaCuentasEgresoCobro(ingVO);
				
				if(!ing.getmPago().equals("Caja")) /*Si es banco*/
					 saldoCuenta.setImpTotMo(impMoCtaBco);
				
				this.saldosCuentas.insertarSaldoCuenta(saldoCuenta, con);
				
				/*Ingresamos el cobro*/
				this.egresoCobro.insertarEgresoCobro(ing, con);
				
				con.commit();
			}
			else{
			existe = true;
			}
			
		}catch(Exception e){
		
		try {
			con.rollback();
		
		} catch (SQLException ex) {
		
			throw new InsertandoEgresoCobroException();
		}
		
			throw new InsertandoEgresoCobroException();
		}
		finally
		{
			pool.liberarConeccion(con);
		}
		if (existe){
			throw new ExisteEgresoCobroException();
		}
	}
	
	public void eliminarEgresoCobro(IngresoCobroVO ingVO, String codEmp) throws InsertandoEgresoCobroException, ConexionException, ExisteEgresoCobroException, NoExisteEgresoCobroException{
	
		Connection con = null;
		boolean existe = false;
		Integer codigo;
		NumeradoresVO codigos = new NumeradoresVO();
		
		
		try 
		{
			con = this.pool.obtenerConeccion();
			con.setAutoCommit(false);
			
			IngresoCobro ing = new IngresoCobro(ingVO); 
			Cotizacion cotiAux;
			
			/*Obtenemos el importe moneda operativa de la cuenta del banco*/
			double impMoCtaBco = 0;
			
			 if(!ing.getmPago().equals("Caja")) /*Si es banco*/
			 {
				 /*Obtenemos el importe moneda operativa de la cuenta del banco*/
				 impMoCtaBco = this.importeMOCtaBanco(ing);
			 }
			
			/*Verificamos no exista un el cobro*/
			if(this.egresoCobro.memberEgresoCobro(ing.getNroDocum(), codEmp, con))
			{
				
				/*Para cada linea volvemos el saldo sin este cobro*/
				for (DocumDetalle docum : ing.getDetalle()) {
				
					if(docum.getCodDocum().equals("Gasto")){ /*Para los gastos modificamos el saldo al documento*/
					
						/*Eliminamos el saldo del gasto*/
						this.saldos.eliminarSaldo(docum, con);
						
						/*Luego eliminamos el gasto*/
						this.gastos.eliminarGastoPK(docum.getNroDocum(), docum.getSerieDocum(), docum.getCodDocum(), codEmp, con);
					}
					/* CON PROCESO POR EL MOMENTO NO HACEMOS NADA
					else if(docum.getCodDocum().equals("Proceso")) //Modificamos el saldo para el proceso ingresado
					{
						//Signo -1 para que restarue los saldos del proceso sin este cobro
						this.saldosProceso.modificarSaldo(docum, -1, ingVO.getTcMov(), con);
					}*/
				}
				
				/*Si el ingreso de cobro es con cheque, eliminamos el cheque */
				if(ingVO.getCodDocRef().equals("cheqemi"))
				{
					//Primero obtenemos el DatosDocum para el cheque dado el ingreso cobro
					DatosDocumVO auxCheque = ConvertirDocumento.getDatosDocumChequeDadoEgrCobro(ingVO);
					
					//Eliminamos el saldo para el cheque 
					//DatosDocum auxCheque2 = new DatosDocum(auxCheque);
					//this.saldos.eliminarSaldo(auxCheque2, con);
					
					/*Eliminamos el cheque de tabla base*/   
					
					auxCheque.setImpTotMo(impMoCtaBco);
					auxCheque.setCodMoneda(ing.getCuenta().getCodMoneda());
					auxCheque.setNacional(ing.getCuenta().isNacional());
					DatosDocum chequeL = new DatosDocum(auxCheque); /*Lo convertimos a objeto de logica para pasarlo al DAO*/
					this.cheques.eliminarCheque(chequeL, con);
				}
				
				/*Subimos el saldo a la cuenta (Banco o caja)*/
				/*Obtenemos el objeto DocumSaldo dado el egreso de cobro*/
				
				DocumSaldo saldoCuenta = ConvertirDocumento.getDocumSaldoChequeDadoEgrCobro(ingVO);
				
				if(!ing.getmPago().equals("Caja")) /*Si es banco*/
					saldoCuenta.setImpTotMo(impMoCtaBco);
				
				this.saldosCuentas.eliminarSaldoCuenta(saldoCuenta, con);
				
				/*Una vez hechos todos los movimientos de saldos y documentos
				* procedemos a eliminar el cobro*/
				this.egresoCobro.eliminarEgresoCobro(ing, con); 
				
				con.commit();
			}
			else{
				throw new NoExisteEgresoCobroException();
			}
		
		}catch(Exception e){
		
		try {
			con.rollback();
		
		} catch (SQLException ex) {
		
			throw new InsertandoEgresoCobroException();
		}
		
			throw new InsertandoEgresoCobroException();
		}
		finally
		{
			pool.liberarConeccion(con);
		}
			if (existe){
				throw new ExisteEgresoCobroException();
		}
	}
	
	public void modificarEgresoCobro(IngresoCobroVO ingVO, IngresoCobroVO copiaVO) throws  ConexionException, ModificandoEgresoCobroException, ExisteEgresoCobroException, NoExisteEgresoCobroException{
	
		Connection con = null;
		
		try 
		{
			con = this.pool.obtenerConeccion();
			con.setAutoCommit(false);
			
			IngresoCobro ing = new IngresoCobro(ingVO);
			
			/*Verificamos que exista el nro de cobro*/
			if(this.egresoCobro.memberEgresoCobro(ing.getNroDocum(), ingVO.getCodEmp(), con))
			{
				/*Primero eliminamos la transaccion, con la copia, para poder detectar las lineas
				* eliminadas*/
				this.eliminarEgresoCobroxModificacion(copiaVO, con); 
				
				/*Luego insertamos el cobro con las modificaciones realizadas*/
				this.insertarEgresoCobroxModificacion(ingVO, con); 
				
				con.commit();
			}
			else
				throw new ModificandoEgresoCobroException();
		
		}catch(ModificandoEgresoCobroException| ExisteEgresoCobroException| ConexionException | SQLException  e)
		{
		try {
			con.rollback();
		
		} catch (SQLException e1) {
		
		throw new ConexionException();
		}
			throw new ModificandoEgresoCobroException();
		}
		finally
		{
			pool.liberarConeccion(con);
		}
	}
	
	/**
	* Este metodo lo utilizamos para el modificar cobro.
	* lo utilizamos internamente en fachada
	* Primero eliminamos el cobro y luego lo volvemos a insertar con 
	* las nuevas modificaciones
	* @throws ModificandoEgresoCobroException 
	* 
	*/
	private void insertarEgresoCobroxModificacion(IngresoCobroVO ingVO, Connection con) throws ConexionException, ExisteEgresoCobroException, ModificandoEgresoCobroException{
	
		boolean existe = false;
		Integer codigo;
		
		try 
		{
			IngresoCobro ing = new IngresoCobro(ingVO); 
			Cotizacion cotiAux;
			
			
			/*Obtenemos el importe moneda operativa de la cuenta del banco*/
			double impMoCtaBco = 0;
			
			 if(!ing.getmPago().equals("Caja")) /*Si es banco*/
			 {
				 /*Obtenemos el importe moneda operativa de la cuenta del banco*/
				 impMoCtaBco = this.importeMOCtaBanco(ing);
			 }
			
			/*Verificamos que no exista un egreso con el mismo numero*/
			if(!this.egresoCobro.memberEgresoCobro(ing.getNroDocum(), ing.getCodEmp(), con))
			{
				
				int i = 0;
				/*Para cada linea ingresamos el saldo*/
				for (DocumDetalle docum : ing.getDetalle()) {
				
					if(docum.getCodDocum().equals("Gasto")){ /*Para los gastos modificamos el saldo al documento*/
						
						/*Ingresamos cada uno de los Gastos*/
						this.gastos.insertarGasto(docum, ing.getCodEmp(), con);
						
						//Genero saldo del gasto 
						this.saldos.insertarSaldo(docum, con);
					}
					ing.getDetalle().get(i).setNroDocum(docum.getNroDocum());
					
					i++;
				}
				
				/*Si el ingreso de cobro es con cheque, ingresamos el cheque*/
				if(ingVO.getCodDocRef().equals("cheqemi")) 
				{
					/*Primero obtenemos el DatosDocum para el cheque dado el ingreso cobro*/
					
					/*Insertamos el cheque*/
					DatosDocumVO auxCheque = ConvertirDocumento.getDatosDocumChequeDadoIngCobro(ingVO);
					
					
					auxCheque.setCodMoneda(ing.getCuenta().getCodMoneda());
					auxCheque.setNacional(ing.getCuenta().isNacional());
					/*Obtenemos el importe moneda operativa de la cuenta del banco*/
				
					auxCheque.setImpTotMo(impMoCtaBco);
					this.insertarChequeIntFachada(auxCheque, con); /*Ingresa el cheque pero no el saldo*/
				
				}
				
				/*Ingresamos el saldo a la cuenta (Banco o caja)*/
				/*Obtenemos el importe moneda operativa de la cuenta del banco*/
				DocumSaldo saldoCuenta = ConvertirDocumento.getDocumSaldoSaCuentasEgresoCobro(ingVO);
				
				if(!ing.getmPago().equals("Caja")) /*Si es banco*/
					saldoCuenta.setImpTotMo(impMoCtaBco);
				
				this.saldosCuentas.insertarSaldoCuenta(saldoCuenta, con);
				
				/*Ingresamos el cobro*/
				this.egresoCobro.insertarEgresoCobro(ing, con);
				
			}
			else{
				existe = true;
			}
		
		}catch(Exception e){
		
			throw new ModificandoEgresoCobroException();
		}
		if (existe){
			throw new ExisteEgresoCobroException();
		}
	}
	
	/**
	* Este metodo lo utilizamos para el modificar cobro.
	* lo utilizamos internamente en fachada
	* Primero eliminamos el cobro y luego lo volvemos a insertar con 
	* las nuevas modificaciones
	* @throws ModificandoEgresoCobroException 
	* 
	*/
	private void eliminarEgresoCobroxModificacion(IngresoCobroVO ingVO, Connection con) throws ConexionException, ExisteEgresoCobroException, NoExisteEgresoCobroException, ModificandoEgresoCobroException{
	
		boolean existe = false;
		Integer codigo;
		
		try 
		{
			IngresoCobro ing = new IngresoCobro(ingVO); 
			Cotizacion cotiAux;
			/*Obtenemos el importe moneda operativa de la cuenta del banco*/
			double impMoCtaBco = 0;
			
			 if(!ing.getmPago().equals("Caja")) /*Si es banco*/
			 {
				 /*Obtenemos el importe moneda operativa de la cuenta del banco*/
				 impMoCtaBco = this.importeMOCtaBanco(ing);
			 }
			
			/*Verificamos no exista un el cobro*/
			if(this.egresoCobro.memberEgresoCobro(ing.getNroDocum(), ing.getCodEmp(), con))
			{
				/*Para cada linea volvemos el saldo sin este cobro*/
				for (DocumDetalle docum : ing.getDetalle()) {
					
					if(docum.getCodDocum().equals("Gasto")){ /*Para los gastos modificamos el saldo al documento*/
					
						/*Eliminamos el saldo del gasto*/
						this.saldos.eliminarSaldo(docum, con);
						
						/*Luego eliminamos el gasto*/
						this.gastos.eliminarGastoPK(docum.getNroDocum(), docum.getSerieDocum(), docum.getCodDocum(), ing.getCodEmp(), con);
					}
					/*
					else if(docum.getCodDocum().equals("Proceso")) //Modificamos el saldo para el proceso ingresado
					{
						//Signo -1 para que le quite el saldo
						this.saldosProceso.modificarSaldo(docum, -1, ingVO.getTcMov(), con);
					}
					*/
				}
			
			/*Si el ingreso de cobro es con cheque, eliminamos el cheque de los saldos y de los cheques*/
			if(ingVO.getCodDocRef().equals("cheqemi"))
			{
				
				//Primero obtenemos el DatosDocum para el cheque dado el egreso cobro
				DatosDocumVO auxCheque = ConvertirDocumento.getDatosDocumChequeDadoEgrCobro(ingVO);
				
				/*
				//Eliminamos el saldo para el cheque 
				DatosDocum auxCheque2 = new DatosDocum(auxCheque);
				this.saldos.eliminarSaldo(auxCheque2, con);
				*/
				
				/*Eliminamos el cheque de tabla base*/    
				
				auxCheque.setImpTotMo(impMoCtaBco);
				auxCheque.setCodMoneda(ing.getCuenta().getCodMoneda());
				auxCheque.setNacional(ing.getCuenta().isNacional());
				
				DatosDocum chequeL = new DatosDocum(auxCheque); /*Lo convertimos a objeto de logica para pasarlo al DAO*/
				
				this.cheques.eliminarCheque(chequeL, con);
			}
			
			/*Bajamos el saldo a la cuenta (Banco o caja)*/
			/*Obtenemos el objeto DocumSaldo dado el egreso de cobro*/
		
			DocumSaldo saldoCuenta = ConvertirDocumento.getDocumSaldoChequeDadoEgrCobro(ingVO);
			
			if(!ing.getmPago().equals("Caja")) /*Si es banco*/
				saldoCuenta.setImpTotMo(impMoCtaBco);
			
			this.saldosCuentas.eliminarSaldoCuenta(saldoCuenta, con);
			
			/*Una vez hechos todos los movimientos de saldos y documentos
			* procedemos a eliminar el egreso*/
			this.egresoCobro.eliminarEgresoCobro(ing, con); 
			
		
		}
		else{
			throw new NoExisteEgresoCobroException();
		}
		
		}catch(Exception e){
		
			throw new ModificandoEgresoCobroException();
		}
		if (existe){
			throw new ExisteEgresoCobroException();
		}
	
	}
	
	/**
	  * Nos retorna el importe total a ingresar en la cuenta del banco
	  * Ya que la moneda de la cuenta bancaria puede ser distinta a la 
	  * del cobro
	 * @throws ConexionException 
	 * @throws ObteniendoCotizacionesException 
	  *
	  */
	private double importeMOCtaBanco(IngresoCobro ing) throws ObteniendoCotizacionesException, ConexionException{
		
		
		  /*Moneda de la cuenta del BCO*/
		  MonedaVO auxMoneda2 = null;
		  
		  double impTotalIngresado = ing.getImpTotMo();
		  
		  double impMo = 0;
		  double tcMonedaNacional = 0;
		  
		  double aux;
		  double aux2;
		  double tcAux;
		  CotizacionVO cotAux = null;
		  
		  String codMonedaCab = ing.getMoneda().getCodMoneda();
		  
		  /*Obtenemos la fecha valor para el tipo de cambio*/
		  Date fecha = new Date(ing.getFecValor().getTime());
		  
		  /*Obtenemos la moneda de la cuenta*/
		  //Datos del banco y cuenta y moneda de la cuenta
		//  CuentaBcoInfo auxctaBco = ing.getCuentaBcoInfo();
		   
		   auxMoneda2 = new MonedaVO();
		   
		  
		   auxMoneda2.setCodMoneda(ing.getCuentaBcoInfo().getCodMoneda());
		   auxMoneda2.setNacional(ing.getCuentaBcoInfo().isNacional());
		   
		   //auxMoneda2.setCodMoneda(ing.getCuenta().getCodMoneda());
		   //auxMoneda2.setNacional(ing.getCuenta().isNacional());
		  
		   
		  String codMonedaCtaBco = auxMoneda2.getCodMoneda();
		  
		  double tcMov = ing.getTcMov();
		
		  tcMonedaNacional = tcMov;
		 
		  
		  /*Si la moneda del cobro es igual  a la de la moneda de la cuenta del banco*/
		  if(codMonedaCab.equals(codMonedaCtaBco))
		  {
			  impMo = impTotalIngresado;  /*El importe es el mismo*/
		  }
		  /*Si la moneda del cobro es distinta a la de la cuenta del banco pero
		   * igual a la moneda nacional, hago el calculo al tipo de cambio
		   * de la fecha valor del cobro*/
		  else if(auxMoneda2.isNacional() &&  !codMonedaCab.equals(auxMoneda2.getCodMoneda()))
		  {
			  aux = impTotalIngresado * tcMonedaNacional;
			  impMo = aux;
		  }
		  else  /*Si no es moneda nacional y es distinto al moneda del cobro*/
		  {
		   
		   /*Obtenemos el tipo de cambio a pesos de la moneda de la cuenta del banco */
			  
		 
		    cotAux = this.getCotizacion(ing.getCodEmp(), fecha, auxMoneda2.getCodMoneda());
		    
		  
		   
		   tcAux = cotAux.getCotizacionVenta();
		   
		   aux = impTotalIngresado * tcMonedaNacional; /*Paso a moneda nacional*/
		   
		   aux2 = aux / tcAux; /*Paso la moneda nacional a la de la cuenta*/
		   
		   impMo = aux2;
		  }
		  
		  return impMo;
		 
	}

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

/////////////////////////////////FIN-INGRESO COBRO/////////////////////////
 

/////////////////////////////////CHEQUES//////////////////////////////////
	
	/**
	*Para ingresar cheque, sin pasar connection, para
	*ser invocado desde fuera de fachada
	*
	*/
	public void insertarCheque(DatosDocumVO documento)
			throws InsertandoChequeException, ExisteChequeException, ConexionException, SQLException{
	
		Connection con = null;
		boolean existe = false;
		NumeradoresVO codigos = new NumeradoresVO();
		
		try 
		{
			con = this.pool.obtenerConeccion();
			con.setAutoCommit(false);
			
			//Obtenemos nroTrans 
			//Obtengo numerador de gastos
			codigos.setNumeroTrans(numeradores.getNumero(con, "03", documento.getCodEmp())); //nro trans
			
			
			DatosDocum cheque = new DatosDocum(documento);
			
			cheque.setNroTrans(codigos.getNumeroTrans()); /*Seteamos el nroTrans*/
			
			/*Verificamos que no exista el cheque*/
			if(!this.cheques.memberCheque(cheque, con))
			{
				/*Ingresamos el cheque*/
				this.cheques.insertarCheque(cheque, con);
				
				/*Ingresamos el saldo para el documento*/
				this.saldos.modificarSaldo(cheque, 1, cheque.getTcMov(), con);
			
				con.commit();
			}
			else{
				existe = true;
			}
		
		}catch(Exception e){
			
			try {
				con.rollback();
			
			} catch (SQLException ex) {
			
				throw new InsertandoChequeException();
			}
		
			throw new InsertandoChequeException();
		}
		finally
		{
			pool.liberarConeccion(con);
		}
		if (existe){
			throw new ExisteChequeException();
		}
		
	}
	
	/**
	*Para ingresar cheque, pasando como parametro la connection,
	*para ser invocado desde fachada
	*
	*/
	private void insertarChequeIntFachada(DatosDocumVO documento, Connection con)
			throws InsertandoChequeException, ExisteChequeException, ConexionException, SQLException{
	
		boolean existe = false;
		NumeradoresVO codigos = new NumeradoresVO();
		
		try 
		{
						
			DatosDocum cheque = new DatosDocum(documento);
			
			/*El nro trans es el de la transaccion de cheque*/
			
			/*Verificamos que no exista el cheque*/
			if(!this.cheques.memberCheque(cheque, con))
			{
				/*Ingresamos el cheque*/
				this.cheques.insertarCheque(cheque, con);
				
				if(!cheque.getCodDocum().equals("cheqemi")) /*Si es cheqemi no ingresamos saldo ya que no hay un deposito del egreso*/
				{
					/*Ingresamos el saldo para el documento*/
					this.saldos.modificarSaldo(cheque, 1, cheque.getTcMov(), con);
				}
			}
			else{
				existe = true;
			}
		
		}catch(Exception e){
			
			throw new InsertandoChequeException();
		}
		
		if (existe){
			throw new ExisteChequeException();
		}
		
	}
	
	public void modificarCheque(DatosDocumVO chequeVO, int signo, double tc ) throws ModificandoChequeException, ConexionException, EliminandoChequeException, InsertandoChequeException, ExisteChequeException, NoExisteChequeException{
	
		Connection con = null;
		
		try 
		{
			con = this.pool.obtenerConeccion();
			con.setAutoCommit(false);
			
			DatosDocum cheque = new DatosDocum(chequeVO);
			
			/*Verificamos que exista el cheque*/
			if(this.cheques.memberCheque(cheque, con))
			{
				this.cheques.modificarCheque(cheque, signo, tc, con);
				
				con.commit();
			}
			else
			throw new ModificandoChequeException();
		
		}catch(ModificandoChequeException| ConexionException| EliminandoChequeException| InsertandoChequeException| ExisteChequeException| NoExisteChequeException | SQLException  e)
		{
			try {
			con.rollback();
			
			} catch (SQLException e1) {
			
			throw new ConexionException();
			}
				throw new ModificandoChequeException();
		}
		finally
		{
			pool.liberarConeccion(con);
		}
	}
	
	
	
	public void eliminarCheque(DatosDocumVO chequeVO ) throws ConexionException, ExisteChequeException, EliminandoChequeException, NoExisteChequeException{
	
		Connection con = null;
		
		try 
		{
			con = this.pool.obtenerConeccion();
			con.setAutoCommit(false);
			
			DatosDocum cheque = new DatosDocum(chequeVO);
			
			/*Verificamos que exista el cheque*/
			if(this.cheques.memberCheque(cheque, con))
			{
				this.cheques.eliminarCheque(cheque, con);
				
				con.commit();
			}
			else
			throw new NoExisteChequeException();
		
		}catch( ConexionException| EliminandoChequeException| SQLException | ExisteChequeException  e)
		{
			try {
			con.rollback();
			
			} catch (SQLException e1) {
			
			throw new ConexionException();
			}
				throw new EliminandoChequeException();
		}
		finally
		{
			pool.liberarConeccion(con);
		}
	}
	
	/////////////////////////////////FIN-CHEQUES/////////////////////////////////////
	
	
	
	/////////////////////////////////SALDO CUENTAS///////////////////////////////////
	
	public void insertarSaldoCuenta(DocumSaldoVO documento)
			throws InsertandoSaldoCuentaException, ExisteSaldoCuentaException, ConexionException, SQLException{
	
		Connection con = null;
		boolean existe = false;
		NumeradoresVO codigos = new NumeradoresVO();
		
		try 
		{
			con = this.pool.obtenerConeccion();
			con.setAutoCommit(false);
			
			//Obtenemos nroTrans 
			//Obtengo numerador de gastos
			codigos.setNumeroTrans(numeradores.getNumero(con, "03", documento.getCodEmp())); //nro trans
			
			
			
			DocumSaldo docum = new DocumSaldo(documento);
			
			docum.setNroTrans(codigos.getNumeroTrans()); /*Seteamos el nroTrans*/
			
			/*Verificamos que no exista el documento*/
			if(!this.saldosCuentas.memberSaldoCta(docum, con))
			{
				/*Ingresamos el cheque*/
				this.saldosCuentas.insertarSaldoCuenta(docum, con);
				
				/*Ingresamos el saldo para el documento*/
				this.saldosCuentas.modificarSaldoCuenta(docum, con);
			
				con.commit();
			}
			else{
				existe = true;
			}
		
		}catch(Exception e){
			
			try {
				con.rollback();
			
			} catch (SQLException ex) {
			
				throw new InsertandoSaldoCuentaException();
			}
		
			throw new InsertandoSaldoCuentaException();
		}
		finally
		{
			pool.liberarConeccion(con);
		}
		if (existe){
			throw new ExisteSaldoCuentaException();
		}
		
	}
	
	public void modificarSaldoCuenta(DocumSaldoVO docum) throws ModificandoSaldoCuentaException, ConexionException, EliminandoSaldoCuetaException, InsertandoSaldoCuentaException, ExisteSaldoCuentaException, NoExisteSaldoCuentaException{
	
		Connection con = null;
		
		try 
		{
			con = this.pool.obtenerConeccion();
			con.setAutoCommit(false);
			
			DocumSaldo doc = new DocumSaldo(docum);
			
			/*Verificamos que exista el doc*/
			if(this.saldosCuentas.memberSaldoCta(doc, con))
			{
				this.saldosCuentas.modificarSaldoCuenta(doc, con);
				
				con.commit();
			}
			else
			throw new ModificandoSaldoCuentaException();
		
		}catch(ModificandoSaldoCuentaException| ConexionException| EliminandoSaldoCuetaException| InsertandoSaldoCuentaException| ExisteSaldoCuentaException| NoExisteSaldoCuentaException | SQLException  e)
		{
			try {
			con.rollback();
			
			} catch (SQLException e1) {
			
			throw new ConexionException();
			}
				throw new ModificandoSaldoCuentaException();
		}
		finally
		{
			pool.liberarConeccion(con);
		}
	}
	
	
	
	public void eliminarSaldoCuenta(DocumSaldoVO documVO ) throws ConexionException, ExisteSaldoCuentaException, EliminandoSaldoCuetaException, NoExisteSaldoCuentaException{
	
		Connection con = null;
		
		try 
		{
			con = this.pool.obtenerConeccion();
			con.setAutoCommit(false);
			
			DocumSaldo docum = new DocumSaldo(documVO);
			
			/*Verificamos que exista el cheque*/
			if(this.saldosCuentas.memberSaldoCta(docum, con))
			{
				this.saldosCuentas.eliminarSaldoCuenta(docum, con);
				
				con.commit();
			}
			else
			throw new NoExisteSaldoCuentaException();
		
		}catch( ConexionException| EliminandoSaldoCuetaException| SQLException | ExisteSaldoCuentaException  e)
		{
			try {
			con.rollback();
			
			} catch (SQLException e1) {
			
			throw new ConexionException();
			}
				throw new EliminandoSaldoCuetaException();
		}
		finally
		{
			pool.liberarConeccion(con);
		}
	}


/////////////////////////////////FIN- SALDO CUENTAS//////////////////////////////
	
	
}
