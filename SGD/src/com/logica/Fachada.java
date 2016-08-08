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
import com.excepciones.Documentos.ExisteDocumentoException;
import com.excepciones.Documentos.InsertandoDocumentoException;
import com.excepciones.Documentos.ModificandoDocumentoException;
import com.excepciones.Documentos.NoExisteDocumentoException;
import com.excepciones.Documentos.ObteniendoDocumentosException;
import com.excepciones.Empresas.ExisteEmpresaException;
import com.excepciones.Login.LoginException;
import com.excepciones.Usuarios.ExisteUsuarioException;
import com.excepciones.clientes.ExisteClienteExeption;
import com.excepciones.clientes.InsertandoClienteException;
import com.excepciones.clientes.ModificandoClienteException;
import com.excepciones.clientes.ObteniendoClientesException;
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
import com.valueObject.*;
import com.valueObject.cliente.ClienteVO;
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
	public ArrayList<GrupoVO> getGrupos() throws ObteniendoGruposException, ConexionException,  ObteniendoFormulariosException
    {
    	
    	Connection con = null;
    	
    	ArrayList<Grupo> lstGrupos;
    	ArrayList<GrupoVO> lstGruposVO = new ArrayList<GrupoVO>();
    	    	
    	try
    	{
    		con = this.pool.obtenerConeccion();
    		
    		lstGrupos = this.grupos.getGrupos(con);
    		
    		
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
    
    public void insertarGrupo(GrupoVO grupoVO) throws InsertandoGrupoException, ConexionException, ExisteGrupoException 
    {
    	
    	Connection con = null;
    	boolean existe = false;
    	
    	try 
    	{
			con = this.pool.obtenerConeccion();
			con.setAutoCommit(false);
			
	    	Grupo grupo = new Grupo(grupoVO); 
	    	
	    	if(!this.grupos.memberGrupo(grupo.getCodGrupo(), con))
	    	{
	    		this.grupos.insertarGrupo(grupo, con);
	    		
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
    
	public void editarGrupo(GrupoVO grupoVO) throws ConexionException, NoExisteGrupoException, ModificandoGrupoException  
	{
	    	
	    	Connection con = null;
	    	
	    	try 
	    	{
				con = this.pool.obtenerConeccion();
				con.setAutoCommit(false);
				
				Grupo grupo = new Grupo(grupoVO);
		    	
		    	if(this.grupos.memberGrupo(grupo.getCodGrupo(), con))
		    	{
		    		/*Primero eliminamos el grupo*/
		    		this.grupos.actualizarGrupo(grupo, con);
		    			    		
		    		
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
		public ArrayList<FormularioVO> getFormulariosNoGrupo(String codGrupo) throws ObteniendoGruposException, ConexionException, ErrorInesperadoException {
	    	
	    	Connection con = null;
	    	
	    	ArrayList<Formulario> lstFormularios = new ArrayList<Formulario>();
	    	ArrayList<FormularioVO> lstFormSelVO = new ArrayList<FormularioVO>();

	    	try
	    	{
	    		con = this.pool.obtenerConeccion();
	    		
	    		lstFormularios = this.grupos.getFormulariosNoGrupo(codGrupo, con);
	    		
	    		
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

	 
	 public int insertarCliente(ClienteVO clienteVO, String codEmp) throws InsertandoClienteException, ConexionException, ExisteClienteExeption  
	    {
	    	
	    	Connection con = null;
	    	boolean existe = false;
	    	int codigo = 0;
	    	
	    	try 
	    	{
				con = this.pool.obtenerConeccion();
				con.setAutoCommit(false);
				
		    	Cliente cliente = new Cliente(clienteVO); 
		    	
		    	
		    	if(!this.clientes.memberCliente(cliente.getCodigo(), codEmp, con))
		    	{
		    		codigo = this.clientes.insertarCliente(cliente, codEmp, con);
		    		
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
	    		throw new ExisteClienteExeption();
	    	}
	    	
	    	return codigo;
	    }
	 
	 public void editarCliente(ClienteVO clienteVO, String codEmp) throws  ConexionException, ModificandoClienteException     
		{
		    	
		    	Connection con = null;
		    	
		    	try 
		    	{
					con = this.pool.obtenerConeccion();
					con.setAutoCommit(false);
					
					Cliente cliente = new Cliente(clienteVO);
			    	
			    	if(this.clientes.memberCliente(cliente.getCodigo(), codEmp, con))
			    	{
			    		/*Primero eliminamos el grupo*/
			    		this.clientes.modificarCliente(cliente, codEmp, con);
			    			    		
			    		
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
	public int insertarFuncionario(FuncionarioVO funcionarioVO, String codEmp) throws InsertendoFuncionarioException, ConexionException, ExisteFuncionarioException  
	{
	
		Connection con = null;
		boolean existe = false;
		int codigo = 0;
		
		try 
		{
			con = this.pool.obtenerConeccion();
			con.setAutoCommit(false);
			
			Funcionario funcionario = new Funcionario(funcionarioVO); 
			
			if(!this.funcionarios.memberFuncionario(funcionario.getCodigo(), codEmp, con))
			{
				codigo = this.funcionarios.insertarFuncionario(funcionario, codEmp, con);
			
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
		}
		
		return codigo;
	}
	
	/**
	*	Modificamos un funcionario al sistema
	*
	*/	
	public void editarFuncionario(FuncionarioVO funcionarioVO, String codEmp) throws  ConexionException, ModificandoFuncionarioException, ExisteFuncionarioException     
	{
	
		Connection con = null;
		
		try 
		{
			con = this.pool.obtenerConeccion();
			con.setAutoCommit(false);
			
			Funcionario funcionario = new Funcionario(funcionarioVO);
			
			if(this.funcionarios.memberFuncionario(funcionario.getCodigo(), codEmp, con))
			{
				/*Primero eliminamos el grupo*/
				this.funcionarios.modificarFuncionario(funcionario, codEmp, con);
				    		
			
				con.commit();
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
	 
}
