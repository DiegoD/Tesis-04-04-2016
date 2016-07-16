package com.valueObject;

import java.sql.Timestamp;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class GrupoVO extends AuditoriaVO{
	
	private String codGrupo;
	private String nomGrupo;
	private boolean activo;
	private ArrayList<FormularioVO> lstFormularios;


	public GrupoVO()
	 {
	  
	 }
	
	public GrupoVO(JSONObject obj){
		
		super(((String)obj.get("usuarioMod")),((Timestamp)obj.get("fechaMod")), ((String)obj.get("operacion")));
		
		this.codGrupo = (String) obj.get("codGrupo");
		this.nomGrupo = (String) obj.get("nomGrupo");
		this.activo = (boolean) obj.get("activo");
		
		this.lstFormularios = new ArrayList<FormularioVO>();
		
		JSONArray JlstForms = (JSONArray) obj.get("lstFormularios");
		
		if (JlstForms != null)
		{
			FormularioVO formVO;
			for (int i = 0; i < JlstForms.size(); i++) {
				
			    JSONObject form = (JSONObject) JlstForms.get(i);
			    formVO = new FormularioVO();		    
			    formVO.setCodFormulario((String)form.get("codFormulario")); 
			    formVO.setNomFormulario((String)form.get("nomFormulario")); 
			  
			    this.lstFormularios.add(formVO);
			}
		}
		
	}
	
	public String getCodGrupo() {
		return codGrupo;
	}
	public void setCodGrupo(String codGrupo) {
		this.codGrupo = codGrupo;
	}
	public String getNomGrupo() {
		return nomGrupo;
	}
	public void setNomGrupo(String nomGrupo) {
		this.nomGrupo = nomGrupo;
	}

	public boolean getActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	
	public ArrayList<FormularioVO> getLstFormularios() {
		return lstFormularios;
	}

	public void setLstFormularios(ArrayList<FormularioVO> lstFormularios) {
		this.lstFormularios = lstFormularios;
	}


}
