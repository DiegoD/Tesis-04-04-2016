package com.logica;

import com.valueObject.cliente.ClienteVO;

public class Cliente extends Titular{

	public Cliente(){}
	
	public Cliente(ClienteVO clienteVO){
		
		this.setCodigo(clienteVO.getCodigo());
		this.setNombre(clienteVO.getNombre());
		this.setTel(clienteVO.getTel());
		this.setDireccion(clienteVO.getDireccion());
		this.setMail(clienteVO.getMail());
		this.setActivo(clienteVO.isActivo());
		
		this.getDocumento().setCodigo(clienteVO.getCodigoDoc());
		this.getDocumento().setNombre(clienteVO.getNombreDoc());
		this.getDocumento().setNumero(clienteVO.getNumeroDoc());
		
		this.razonSocial = clienteVO.getRazonSocial();
		
		this.setFechaMod(clienteVO.getFechaMod());
		this.setUsuarioMod(clienteVO.getUsuarioMod());
		this.setOperacion(clienteVO.getOperacion());
		
	}
	
	private String razonSocial;
	
	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	
}
