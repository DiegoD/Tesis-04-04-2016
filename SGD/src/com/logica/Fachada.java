package com.logica;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

import org.json.simple.JSONObject;

import com.abstractFactory.AbstractFactoryBuilder;
import com.abstractFactory.IAbstractFactory;
import com.excepciones.*;
import com.excepciones.Login.LoginException;
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

public class Fachada {

	private static final Object lock = new Object();
	private static volatile Fachada INSTANCE = null;
	
	/*Pool de conecciones*/
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
	
	
    private Fachada() throws InstantiationException, IllegalAccessException, ClassNotFoundException, FileNotFoundException, IOException
    {
        pool = new Pool();
    	
    	fabrica = AbstractFactoryBuilder.getInstancia();
		fabricaConcreta = fabrica.getAbstractFactory();
		
        this.daoImpuesto = fabricaConcreta.crearDaoImpuestos();
        this.cotizaciones = fabricaConcreta.crearDAOCotizaciones();
        this.monedas = fabricaConcreta.crearDAOMonedas();
        this.docsAduaneros = fabricaConcreta.crearDAODocumentosAduaneros();
        this.usuarios =  fabricaConcreta.crearDAOUsuarios();
        this.grupos = fabricaConcreta.crearDAOGrupos();
        
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
    
    
    /////////////////////////////////<MONEDAS>/////////////////////////////////////////////////
    public ArrayList<MonedaVO> getMonedas() throws ObteniendoMonedasException{
    	
    	return this.monedas.getMonedas();
    }

    /////////////////////////////////<MONEDAS/>/////////////////////////////////////////////////
    
    
    /////////////////////////////////<COTIZACIONES>////////////////////////////////////////////
    
    public ArrayList<CotizacionVO> getCotizaciones() throws ObteniendoCotizacionException{
    	
    	return this.cotizaciones.getCotizaciones();
    }
    
    public CotizacionVO getCotizacion(Date fecha, int codMoneda) throws ObteniendoCotizacionException, MemberCotizacionException, NoExisteCotizacionException{
    	
    	if(this.cotizaciones.memberCotizacion(fecha, codMoneda))
    	    	return this.cotizaciones.getCotizacion(fecha, codMoneda);
    	else 
    		throw new NoExisteCotizacionException();
    }

    public void insertCotizacion(CotizacionVO cotizacionVO) throws IngresandoCotizacionException, MemberCotizacionException, ExisteCotizacionException{
    	
    	if(!this.cotizaciones.memberCotizacion(cotizacionVO.getFecha(), cotizacionVO.getCodMoneda()))
    		this.cotizaciones.insertCotizacion(cotizacionVO);
    	else
    		throw new ExisteCotizacionException();
    }
    
    
    /////////////////////////////////<COTIZACIONES/>//////////////////////////////////////////
    
    
    /////////////////////////////////<DOCUMENTOS ADUANEROS>//////////////////////////////////
    
    
    public ArrayList<DocumentoAuaneroVO> getDocumentosAduanerosActivos() throws ObteniendoDocumentoAduaneroException  {
    	
    	return this.docsAduaneros.getDocumentosAduanerosActivos();
    }
    
    public ArrayList<DocumentoAuaneroVO> getDocumentosAduanerosTodos() throws ObteniendoDocumentoAduaneroException  {
    	
    	return this.docsAduaneros.getDocumentosAduanerosTodos();
    }
    
    public DocumentoAuaneroVO  getDocumentosAduanero(int codDocum) throws ObteniendoDocumentoAduaneroException {
    	
    	return this.docsAduaneros.getDocumentosAduanero(codDocum);
    }

    public void insertDocumentAduanero(DocumentoAuaneroVO documentoAuaneroVO) throws IngresandoDocumentoAduaneroException{
    	
    	this.docsAduaneros.insertCotizacion(documentoAuaneroVO);
    }
    
    
    
    /////////////////////////////////<DOCUMENTOS ADUANEROS/>/////////////////////////////////
    
    /////////////////////////////////<IMPUESTOS>/////////////////////////////////
    public void insertImpuesto (ImpuestoVO impuestoVO) throws ClassNotFoundException{
    	
    	Impuesto impuesto = new Impuesto(impuestoVO.getCodImpuesto(), impuestoVO.getDescImpuesto(), impuestoVO.getPorcentaje());
    	
    	System.out.println("estoy en fachada llamando a DAO");
    	
    	this.daoImpuesto.insertImpuesto(impuesto);
    }
    
    public ArrayList<JSONObject> getImpuestosTodos() throws ClassNotFoundException{
    	
    	System.out.println("estoy en fachada");
    	
    	return this.daoImpuesto.getImpuestosTodos();
    	
    }
    /////////////////////////////////<IMPUESTOS/>/////////////////////////////////
    
/////////////////////////////////INI-LOGIN/////////////////////////////////
    
    public boolean usuarioValido(LoginVO loginVO) throws LoginException, ErrorInesperadoException, ConexionException{
    	
    	Connection con = null;
    	
    	try
    	{
    		con = this.pool.obtenerConeccion();
    	
    		return this.usuarios.usuarioValido(loginVO, con);
    		
    	}catch(Exception e)
    	{
    		throw new ErrorInesperadoException();
    	}
    	finally
    	{
    		this.pool.liberarConeccion(con);
    	}
    	
    }
    
/////////////////////////////////FIN-LOGIN/////////////////////////////////
    
/////////////////////////////////INI-GUPOS/////////////////////////////////
    public ArrayList<JSONObject> getGrupos() throws ObteniendoGruposException, ConexionException, ErrorInesperadoException {
    	
    	Connection con = null;
    	
    	try
    	{
    		con = this.pool.obtenerConeccion();
    		
    		return this.grupos.getGrupos(con);
    		
    	}catch(Exception e)
    	{
    		throw new ErrorInesperadoException();
    	}
    	finally
    	{
    		this.pool.liberarConeccion(con);
    	}
    	
    	    	
    }
    
    public void insertarGrupo(JSONObject grupoJS) throws InsertandoGrupoException, MemberGrupoException, ExisteGrupoException, ConexionException, ErrorInesperadoException{
    	
    	Connection con = null;
    	
    	try 
    	{
			con = this.pool.obtenerConeccion();

	    	Grupo grupo = new Grupo(grupoJS);
	    	
	    	if(!this.grupos.memberGrupo(grupo.getCodGrupo(), con))
	    		this.grupos.insertarGrupo(grupo, con);
	    	else
	    		throw new ExisteGrupoException();
    	
    	}catch(Exception e)
    	{
    		throw new ErrorInesperadoException();
    	}
    	finally
    	{
    		pool.liberarConeccion(con);
    	}
    }
    
public void editarGrupo(JSONObject grupoJS) throws InsertandoGrupoException, MemberGrupoException, ExisteGrupoException, ConexionException, ErrorInesperadoException{
    	
    	Connection con = null;
    	
    	try 
    	{
			con = this.pool.obtenerConeccion();
			con.setAutoCommit(false);
			
			Grupo grupo = new Grupo(grupoJS);
	    	
	    	if(!this.grupos.memberGrupo(grupo.getCodGrupo(), con))
	    	{
	    		/*Primero eliminamos el grupo*/
	    		this.grupos.eliminarGrupo(grupo.getCodGrupo(), con);
	    		
	    		/*Luego lo volvemos a insertar*/
	    		this.grupos.insertarGrupo(grupo, con);
	    		
	    		con.commit();
	    	}
	    	
	    	else
	    		throw new ExisteGrupoException();
    	
    	}catch(Exception e)
    	{
    		try {
				con.rollback();
				
			} catch (SQLException e1) {
				
				throw new ConexionException();
			}
    		throw new ErrorInesperadoException();
    	}
    	finally
    	{
    		pool.liberarConeccion(con);
    	}
    }
    
/////////////////////////////////FIN-GUPOS/////////////////////////////////
}
