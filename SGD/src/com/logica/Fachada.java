package com.logica;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
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
import com.excepciones.grupos.ModificandoGrupoException;
import com.excepciones.grupos.NoExisteGrupoException;
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
    
    public boolean usuarioValido(LoginVO loginVO) throws LoginException, ConexionException{
    	
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
	public ArrayList<GrupoVO> getGrupos() throws ObteniendoGruposException, ConexionException, ErrorInesperadoException {
    	
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
    				
    				aux.getLstFormularios().add(auxF);
				}
    			
    			lstGruposVO.add(aux);
			}
    		
   		
    		return lstGruposVO;
    		
    	}catch(Exception e)
    	{
    		throw new ErrorInesperadoException();
    	}
    	finally
    	{
    		this.pool.liberarConeccion(con);
    	}
    	    	
    }
    
    public void insertarGrupo(GrupoVO grupoVO) throws InsertandoGrupoException, ConexionException 
    {
    	
    	Connection con = null;
    	
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
	    	else
	    		throw new ExisteGrupoException();
    	
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
		    		this.grupos.eliminarGrupo(grupo.getCodGrupo(), con);
		    		
		    		/*Luego lo volvemos a insertar*/
		    		this.grupos.insertarGrupo(grupo, con);
		    		
		    		con.commit();
		    	}
		    	
		    	else
		    		throw new NoExisteGrupoException();
	    	
	    	}catch(InsertandoGrupoException| MemberGrupoException| ConexionException | SQLException | ModificandoGrupoException e)
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
		
/////////////////////////////////FIN-GUPOS/////////////////////////////////
}
