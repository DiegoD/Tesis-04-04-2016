package com.valueObject;

import java.util.ArrayList;


public class GrupoVO extends AuditoriaVO{
	
	private String codGrupo;
	private String nomGrupo;
	private boolean activo;
	private ArrayList<FormularioVO> lstFormularios;


	public GrupoVO()
	 {
		this.lstFormularios = new ArrayList<FormularioVO>();
	 }
	
	
	/**
	 * Copiamos todos los datos del GrupoVO pasado
	 * por parametro
	 *
	 */
	public void copiar(GrupoVO grpVO){

		this.setUsuarioMod(grpVO.getUsuarioMod());
		this.setFechaMod(grpVO.getFechaMod());
		this.setOperacion(grpVO.getOperacion());
		
		this.codGrupo = grpVO.getCodGrupo();
		this.nomGrupo = grpVO.getNomGrupo();
		this.activo = 	grpVO.getActivo();
		
		this.lstFormularios = new ArrayList<FormularioVO>();
		
				
		if (grpVO.getLstFormularios() != null)
		{
			FormularioVO formVO;
			for (FormularioVO frmVO : grpVO.getLstFormularios()) {
				
				formVO = new FormularioVO();		    
			    formVO.setCodFormulario(frmVO.getCodigo()); 
			    formVO.setNomFormulario(frmVO.getNombre()); 
			  
			    formVO.setLeer(frmVO.isLeer());
			    formVO.setNuevoEditar(frmVO.isNuevoEditar());
			    formVO.setBorrar(frmVO.isBorrar());
			    
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
		
		for (FormularioVO formularioVO : lstFormularios) 
		{
			this.lstFormularios.add(formularioVO);
						
		}
		
	}

}
