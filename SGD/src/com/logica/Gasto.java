package com.logica;

import java.sql.Timestamp;

import com.valueObject.Gasto.GastoVO;

public class Gasto extends Auditoria{
	
	
	private int cod_gasto;
	private Proceso proceso;
	private ClienteInfo cliente;
	private Cuenta cuenta;
	private Rubro rubro;
	private MonedaInfo moneda;
	private Timestamp fecha;
	private float impMo;
	private float impMn;
	private float tcMov;
	private String descripcion;
	
	
	public Gasto(){
		
	}
	
	public Gasto(int cod_gasto, Proceso proceso, ClienteInfo cliente, Cuenta cuenta, Rubro rubro, MonedaInfo moneda,
			Timestamp fecha, float impMo, float impMn, float tcMov, String descripcion) {
		super();
		this.cod_gasto = cod_gasto;
		this.proceso = proceso;
		this.cliente = cliente;
		this.cuenta = cuenta;
		this.rubro = rubro;
		this.moneda = moneda;
		this.fecha = fecha;
		this.impMo = impMo;
		this.impMn = impMn;
		this.tcMov = tcMov;
		this.descripcion = descripcion;
	}

	public Gasto(GastoVO gastoVO){
		
		super(gastoVO.getUsuarioMod(), gastoVO.getFechaMod(), gastoVO.getOperacion());
		this.cod_gasto = gastoVO.getCodGasto();
		this.cliente = new ClienteInfo(gastoVO.getCodCliente(), gastoVO.getNomCliente());
		this.moneda = new MonedaInfo(gastoVO.getCodMoneda(), gastoVO.getDescMoneda(),
				gastoVO.getSimboloMoneda());
		
		this.proceso = new Proceso(gastoVO.getCodProceso());
		this.cuenta = new Cuenta(gastoVO.getCodCuenta(), gastoVO.getDescripcionCuenta());
		Impuesto impuesto = new Impuesto(gastoVO.getCodImpuesto(), gastoVO.getDescripcionImpuesto(), gastoVO.getPorcentajeImpuesto());
		this.rubro = new Rubro(gastoVO.getCodRubro(), gastoVO.getDescripcionRubro(), impuesto);
		this.fecha = gastoVO.getFecha();
		this.impMo = gastoVO.getImpMo();
		this.impMn = gastoVO.getImpMn();
		this.tcMov = gastoVO.getTcMov();
		this.descripcion = gastoVO.getDescripcion();
		
	}

	public int getCod_gasto() {
		return cod_gasto;
	}


	public void setCod_gasto(int cod_gasto) {
		this.cod_gasto = cod_gasto;
	}


	public Proceso getProceso() {
		return proceso;
	}


	public void setProceso(Proceso proceso) {
		this.proceso = proceso;
	}


	public ClienteInfo getCliente() {
		return cliente;
	}


	public void setCliente(ClienteInfo cliente) {
		this.cliente = cliente;
	}


	public Cuenta getCuenta() {
		return cuenta;
	}


	public void setCuenta(Cuenta cuenta) {
		this.cuenta = cuenta;
	}


	public Rubro getRubro() {
		return rubro;
	}


	public void setRubro(Rubro rubro) {
		this.rubro = rubro;
	}


	public MonedaInfo getMoneda() {
		return moneda;
	}


	public void setMoneda(MonedaInfo moneda) {
		this.moneda = moneda;
	}


	public Timestamp getFecha() {
		return fecha;
	}


	public void setFecha(Timestamp fecha) {
		this.fecha = fecha;
	}


	public float getImpMo() {
		return impMo;
	}


	public void setImpMo(float impMo) {
		this.impMo = impMo;
	}


	public float getImpMn() {
		return impMn;
	}


	public void setImpMn(float impMn) {
		this.impMn = impMn;
	}


	public float getTcMov() {
		return tcMov;
	}


	public void setTcMov(float tcMov) {
		this.tcMov = tcMov;
	}


	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
