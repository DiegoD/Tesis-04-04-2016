package com.logica;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.abstractFactory.AbstractFactoryBuilder;
import com.abstractFactory.IAbstractFactory;
import com.excepciones.*;
import com.excepciones.Login.LoginException;
import com.excepciones.Usuarios.ExisteUsuarioException;
import com.excepciones.Usuarios.InsertandoUsuarioException;
import com.excepciones.Usuarios.ObteniendoUsuariosException;
import com.excepciones.cotizaciones.ExisteCotizacionException;
import com.excepciones.cotizaciones.IngresandoCotizacionException;
import com.excepciones.cotizaciones.MemberCotizacionException;
import com.excepciones.cotizaciones.NoExisteCotizacionException;
import com.excepciones.cotizaciones.ObteniendoCotizacionException;
import com.excepciones.documentosAduaneros.IngresandoDocumentoAduaneroException;
import com.excepciones.documentosAduaneros.ObteniendoDocumentoAduaneroException;
import com.excepciones.grupos.ExisteGrupoException;
import com.excepciones.grupos.InsertandoGrupoException;
import com.excepciones.grupos.MemberGrupoException;
import com.excepciones.grupos.ObteniendoGruposException;
import com.valueObject.*;
import com.persistencia.*;

public class FachadaDD {

	private static final Object lock = new Object();
	private static volatile FachadaDD INSTANCE = null;
	Pool pool;
	
	/*Esto es para abstract factory*/
	private IDaoImpuesto daoImpuesto;
	private IDAOCotizaciones cotizaciones;
	private IDAOMonedas monedas;
	private IDAODocumentosAduaneros docsAduaneros;
	private IDAOUsuarios usuarios;
	private IDAOGrupos grupos;
	
	private AbstractFactoryBuilder fabrica;
	private IAbstractFactory fabricaConcreta;
	
	
    private FachadaDD() throws InstantiationException, IllegalAccessException, ClassNotFoundException, FileNotFoundException, IOException
    {
    	pool = Pool.getInstance();
    	
        fabrica = AbstractFactoryBuilder.getInstancia();
		fabricaConcreta = fabrica.getAbstractFactory();
		
        this.daoImpuesto = fabricaConcreta.crearDaoImpuestos();
        this.cotizaciones = fabricaConcreta.crearDAOCotizaciones();
        this.monedas = fabricaConcreta.crearDAOMonedas();
        this.docsAduaneros = fabricaConcreta.crearDAODocumentosAduaneros();
        this.usuarios =  fabricaConcreta.crearDAOUsuarios();
        this.grupos = fabricaConcreta.crearDAOGrupos();
        
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
    
    public ArrayList<JSONObject> convertArray(ArrayList<Usuario> lstUsuarios)
    {
    	JSONObject json = null;
    	ArrayList<JSONObject> lstUsuariosJson = new ArrayList<JSONObject>();
    	JSONArray JSONlstGruposUsuario = new JSONArray();
    	
    	for (Usuario usuario : lstUsuarios) {
			json = new JSONObject();
			json.put("nombre", usuario.getNombre());
			json.put("usuario", usuario.getUsuario());
			json.put("pass", usuario.getPass());
			json.put("activo", usuario.isActivo());
			System.out.println(json.toString());
			
			for (Grupo gruposUsuario : usuario.getLstGrupos())
			{
				
				JSONlstGruposUsuario = new JSONArray();
				
				JSONObject jGrupoUsuario = new JSONObject();
				jGrupoUsuario.put("codigo", gruposUsuario.getCodGrupo());
				jGrupoUsuario.put("nombre", gruposUsuario.getNomGrupo());
				
				JSONlstGruposUsuario.add(jGrupoUsuario);
				
			}
			
			if(usuario.getLstGrupos().size() > 0)
				json.put("lstGruposUsuario", JSONlstGruposUsuario);
			
			lstUsuariosJson.add(json);
		}
    	return lstUsuariosJson;
    }
/////////////////////////////////FIN LISTADO DE USUARIOS/////////////////////////////////
    
/////////////////////////////////NUEVO/////////////////////////////////
    @SuppressWarnings("resource")
	public void insertarUsuario(UsuarioVO usuarioVO) throws InsertandoUsuarioException, ConexionException, ExisteUsuarioException, ErrorInesperadoException
    {
    	Connection con = null;
    	Usuario user = new Usuario(usuarioVO);
    	
    	try 
    	{
    		con = this.pool.obtenerConeccion();
			con.setAutoCommit(false);
			con = this.pool.obtenerConeccion();
			
    		if(!this.usuarios.memberUsuario(user.getUsuario(), con))
        	{
        		System.out.println("voy a insertar");
        		this.usuarios.insertarUsuario(user, con);
        	}
        	else
        	{
        		System.out.println("ya estaba");
        		throw new ExisteUsuarioException();
        	}
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
////////////////////////////////FIN NUEVO/////////////////////////////////   
    
/////////////////////////////////NUEVO/////////////////////////////////
    @SuppressWarnings("resource")
	public void modificarUsuario(UsuarioVO usuarioVO) throws InsertandoUsuarioException, ConexionException, ExisteUsuarioException, ErrorInesperadoException
	{
		Connection con = null;
		Usuario user = new Usuario(usuarioVO);
	
		try 
		{
			
			con = this.pool.obtenerConeccion();
			con.setAutoCommit(false);
			System.out.println("voy a insertar");
			this.usuarios.eliminarUsuario(user, con);
			this.usuarios.insertarUsuario(user, con);
			con.commit();
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
    
    
    
    
    
    
    
    
    

}
