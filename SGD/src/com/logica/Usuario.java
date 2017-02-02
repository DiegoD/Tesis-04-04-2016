package com.logica;

import java.sql.Timestamp;
import java.util.ArrayList;

import org.json.simple.JSONObject;

import com.valueObject.GrupoVO;
import com.valueObject.UsuarioVO;

public class Usuario extends Auditoria{
	
	private String usuario;
	private String pass;
	private String nombre;
	private boolean activo;
	private String mail;
	private int codTit;
	private String nomTit;
	private ArrayList<Grupo> lstGrupos;
	
	public Usuario(){
		this.lstGrupos = new ArrayList<Grupo>();
	}
	public Usuario(String usuario, String pass, String nombre, Boolean activo, 
				   String usuarioMod, Timestamp fechaMod, String operacion, String mail, int codTit, String nomTit) {
		
		super(usuarioMod, fechaMod, operacion);
		this.usuario = usuario;
		this.pass = pass;
		this.nombre = nombre;
		this.activo = activo;
		this.lstGrupos = new ArrayList<Grupo>();
		this.setUsuarioMod(usuarioMod);
		this.mail = mail;
		this.codTit = codTit;
		this.nomTit = nomTit;
	}
	
	
	
	public Usuario(UsuarioVO usuarioVO)
	{
		super(usuarioVO.getUsuarioMod(), usuarioVO.getFechaMod(), usuarioVO.getOperacion());
		this.usuario = usuarioVO.getUsuario();
		this.nombre = usuarioVO.getNombre();
		this.pass = usuarioVO.getPass();
		this.activo = usuarioVO.isActivo();
		this.lstGrupos = new ArrayList<Grupo>();
		this.mail = usuarioVO.getMail();
		this.codTit = usuarioVO.getCodTit();
		this.nomTit = usuarioVO.getNomTit();
		Grupo aux;
		for (GrupoVO grupoVO : usuarioVO.getLstGrupos()) {
			
			aux = new Grupo(grupoVO);
			this.lstGrupos.add(aux);
		}
	
	}
	
	public Usuario(JSONObject jsonUsuario)
	{
		super(((String)jsonUsuario.get("usuarioMod")),((Timestamp)jsonUsuario.get("fechaMod")), ((String)jsonUsuario.get("operacion")));
		this.usuario = jsonUsuario.get("usuario").toString();
		this.pass = jsonUsuario.get("pass").toString();
		this.nombre = jsonUsuario.get("nombre").toString();
		this.activo = (Boolean) jsonUsuario.get("activo");
		this.lstGrupos = new ArrayList<Grupo>();
	}
	
	public boolean isActivo() {
		return activo;
	}
	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public ArrayList<Grupo> getLstGrupos() {
		return lstGrupos;
	}
	public void setLstGrupos(ArrayList<Grupo> lstGrupos) {
		this.lstGrupos = lstGrupos;
	}
	
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public int getCodTit() {
		return codTit;
	}
	public void setCodTit(int codTit) {
		this.codTit = codTit;
	}
	public String getNomTit() {
		return nomTit;
	}
	public void setNomTit(String nomTit) {
		this.nomTit = nomTit;
	}

	
}
