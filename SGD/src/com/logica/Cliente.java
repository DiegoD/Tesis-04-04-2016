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
	
	public ClienteVO retornarClienteVO(){
		
		ClienteVO aux = new ClienteVO();
		
		aux.setRazonSocial(this.getRazonSocial());
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
		aux.setTipo(this.getTipo());
		
		return aux;
	}
	
	private String razonSocial;
	
	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	
}
