package com.valueObject;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.logica.Grupo;

public class UsuarioVO extends AuditoriaVO{
	
	private String usuario;
	private String pass;
	private String nombre;
	private boolean activo;
	private ArrayList<GrupoVO> lstGrupos;

	public UsuarioVO()
	{
		this.lstGrupos = new ArrayList<GrupoVO>();
	}
	/**
	 * Crea un objeto UsuarioVO dado un JSON con sus datos
	 */
	public UsuarioVO(JSONObject obj){
		
		this.usuario = (String) obj.get("usuario");
		this.pass = (String) obj.get("pass");
		this.nombre = (String) obj.get("nombre");
		this.activo = (Boolean) obj.get("activo");
		this.lstGrupos = new ArrayList<GrupoVO>();
		
		JSONArray jLstGrupos = (JSONArray) obj.get("lstGruposUsuario");
		GrupoVO grupoNombre;
		if(jLstGrupos != null)
		{
			for(int i = 0; i < jLstGrupos.size(); i++)
			{
				JSONObject jGrupoUsuario = (JSONObject) jLstGrupos.get(i);
				grupoNombre = new GrupoVO();
				grupoNombre.setCodGrupo((String) jGrupoUsuario.get("codigo"));
				grupoNombre.setNomGrupo((String) jGrupoUsuario.get("nombre"));
				
				this.lstGrupos.add(grupoNombre);
			}
		}
		
	}
	
	public ArrayList<GrupoVO> getLstGrupos() {
		return lstGrupos;
	}

	public void setLstGrupos(ArrayList<GrupoVO> lstGrupos) {
		this.lstGrupos = lstGrupos;
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
	

	public void copiar(UsuarioVO usuarioVO)
	{
		this.setUsuarioMod(usuarioVO.getUsuarioMod());
		this.setFechaMod(usuarioVO.getFechaMod());
		this.setOperacion(usuarioVO.getOperacion());
		
		this.usuario = usuarioVO.getUsuario();
		this.nombre = usuarioVO.getNombre();
		this.pass = usuarioVO.getPass();
		this.activo = usuarioVO.isActivo();
		
		this.lstGrupos = new ArrayList<GrupoVO>();
		
				
		if (usuarioVO.getLstGrupos() != null)
		{
			GrupoVO grupoVO;
			for (GrupoVO grupoVO1 : usuarioVO.getLstGrupos()) 
			{
				
				grupoVO = new GrupoVO();		    
				grupoVO.setNomGrupo(grupoVO1.getNomGrupo());
				grupoVO.setCodGrupo(grupoVO1.getCodGrupo());
			  
			    this.lstGrupos.add(grupoVO);
				
			}
			
		}
		
	}
	
}
