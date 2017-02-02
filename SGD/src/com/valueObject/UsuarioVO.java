package com.valueObject;

import java.util.ArrayList;


public class UsuarioVO extends AuditoriaVO{
	
	private String usuario;
	private String pass;
	private String nombre;
	private boolean activo;
	private String mail;
	private int codTit;
	private String nomTit;
	
	private ArrayList<GrupoVO> lstGrupos;

	public UsuarioVO()
	{
		this.lstGrupos = new ArrayList<GrupoVO>();
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
	
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
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
		this.mail = usuarioVO.mail;
		this.codTit = usuarioVO.codTit;
		this.nomTit = usuarioVO.nomTit;
		
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
