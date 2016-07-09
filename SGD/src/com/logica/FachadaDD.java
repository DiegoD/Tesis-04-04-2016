package com.logica;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;

import org.json.simple.JSONObject;

import com.abstractFactory.AbstractFactoryBuilder;
import com.abstractFactory.IAbstractFactory;
import com.excepciones.*;
import com.excepciones.Login.LoginException;
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
    	
    	System.out.println("estoy en FachadaDD llamando a DAO");
    	
    	this.daoImpuesto.insertImpuesto(impuesto);
    }
    
    public ArrayList<JSONObject> getImpuestosTodos() throws ClassNotFoundException{
    	
    	System.out.println("estoy en FachadaDD");
    	
    	return this.daoImpuesto.getImpuestosTodos();
    	
    }
    /////////////////////////////////<IMPUESTOS/>/////////////////////////////////
    
/////////////////////////////////INI-LOGIN/////////////////////////////////
    
    public boolean usuarioValido(LoginVO loginVO) throws LoginException{
    	
    	return this.usuarios.usuarioValido(loginVO);
    	
    }
    
/////////////////////////////////FIN-LOGIN/////////////////////////////////
    
/////////////////////////////////INI-GUPOS/////////////////////////////////
    public ArrayList<JSONObject> getGrupos() throws ObteniendoGruposException {
    	
    	
    	return this.grupos.getGrupos();
    	
    }
    
    public void insertarGrupo(GrupoVO grupoVO) throws InsertandoGrupoException, MemberGrupoException, ExisteGrupoException{
    	
    	if(!this.grupos.memberGrupo(grupoVO.getCodGrupo()))
    		this.grupos.insertarGrupo(grupoVO);
    	else
    		throw new ExisteGrupoException();
    }
    
/////////////////////////////////FIN-GUPOS/////////////////////////////////
    
    
/////////////////////////////////NUEVO/////////////////////////////////
    @SuppressWarnings("null")
	public ArrayList<JSONObject> getUsuarios() throws ObteniendoUsuariosException, ClassNotFoundException {
    	
    	ArrayList<Usuario> lstUsuarios = this.usuarios.getUsuarios();
    	ArrayList<JSONObject> lstUsuariosJson = new ArrayList<JSONObject>();
    	
    	lstUsuariosJson = this.convertArray(lstUsuarios);
		
    	System.out.println("Estoy en fachada ");
    	return lstUsuariosJson;
    }
    
    public ArrayList<JSONObject> convertArray(ArrayList<Usuario> lstUsuarios){
    	JSONObject json = null;
    	ArrayList<JSONObject> lstUsuariosJson = new ArrayList<JSONObject>();
    	
    	for (Usuario usuario : lstUsuarios) {
			json = new JSONObject();
			json.put("nombre", usuario.getNombre());
			json.put("usuario", usuario.getUsuario());
			json.put("pass", usuario.getPass());
			System.out.println(json.toString());
			
			lstUsuariosJson.add(json);
		}
    	return lstUsuariosJson;
    }
/////////////////////////////////FIN NUEVO/////////////////////////////////    
    
    
}
