package com.logica;

import com.valueObject.FuncionarioVO;
import com.valueObject.cliente.ClienteVO;

public class Funcionario extends Titular {
	
public Funcionario(){}
	
	public Funcionario(FuncionarioVO funcionarioVO){
		
		this.setCodigo(funcionarioVO.getCodigo());
		this.setNombre(funcionarioVO.getNombre());
		this.setTel(funcionarioVO.getTel());
		this.setDireccion(funcionarioVO.getDireccion());
		this.setMail(funcionarioVO.getMail());
		this.setActivo(funcionarioVO.isActivo());
		
		this.getDocumento().setCodigo(funcionarioVO.getCodigoDoc());
		this.getDocumento().setNombre(funcionarioVO.getNombreDoc());
		this.getDocumento().setNumero(funcionarioVO.getNumeroDoc());
		
		
		this.setFechaMod(funcionarioVO.getFechaMod());
		this.setUsuarioMod(funcionarioVO.getUsuarioMod());
		this.setOperacion(funcionarioVO.getOperacion());
		
	}
	
	public FuncionarioVO retornarFuncionarioVO(){
		
		FuncionarioVO aux = new FuncionarioVO();
		
		aux.setNombre(this.getNombre());
		aux.setNombreDoc(this.getDocumento().getNombre());
		aux.setCodigoDoc(this.getDocumento().getCodigo());
		aux.setNumeroDoc(this.getDocumento().getNumero());
		aux.setCodigo(this.getCodigo());
		aux.setNombre(this.getNombre());
		aux.setTel(this.getTel());
		aux.setDireccion(this.getDireccion());
		aux.setFechaMod(this.getFechaMod());
		aux.setOperacion(this.getOperacion());
		aux.setUsuarioMod(this.getUsuarioMod());
		aux.setMail(this.getMail());
		
		return aux;
	}
	
	

}
