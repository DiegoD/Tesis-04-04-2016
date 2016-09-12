package com.valueObject.Gasto;

import java.sql.Timestamp;

import com.logica.ClienteInfo;
import com.logica.Cuenta;
import com.logica.MonedaInfo;
import com.logica.Proceso;
import com.logica.Rubro;
import com.valueObject.AuditoriaVO;

public class GastoVO extends AuditoriaVO{
	
	private int codGasto;
	private int codProceso;
	private String codCliente;
	private String nomCliente;
	private String codMoneda;
	private String descMoneda;
	private String simboloMoneda;
	private String codCuenta;
	private String descripcionCuenta;
	private String codRubro;
	private String descripcionRubro;
	private String tipoRubro;
	private String codImpuesto;
	private String descripcionImpuesto;
	private float porcentajeImpuesto;
	private Timestamp fecha;
	private float impMo;
	private float impMn;
	private float tcMov;
	private String descripcion;
	
	public GastoVO(){
		
	}

	

	public GastoVO(int codGasto, int codProceso, String codCliente, String nomCliente, String codMoneda,
			String descMoneda, String simboloMoneda, String codCuenta, String descripcionCuenta, String codRubro,
			String descripcionRubro, String tipoRubro, String codImpuesto, String descripcionImpuesto,
			float porcentajeImpuesto, Timestamp fecha, float impMo, float impMn, float tcMov, String descripcion) {
		super();
		this.codGasto = codGasto;
		this.codProceso = codProceso;
		this.codCliente = codCliente;
		this.nomCliente = nomCliente;
		this.codMoneda = codMoneda;
		this.descMoneda = descMoneda;
		this.simboloMoneda = simboloMoneda;
		this.codCuenta = codCuenta;
		this.descripcionCuenta = descripcionCuenta;
		this.codRubro = codRubro;
		this.descripcionRubro = descripcionRubro;
		this.tipoRubro = tipoRubro;
		this.codImpuesto = codImpuesto;
		this.descripcionImpuesto = descripcionImpuesto;
		this.porcentajeImpuesto = porcentajeImpuesto;
		this.fecha = fecha;
		this.impMo = impMo;
		this.impMn = impMn;
		this.tcMov = tcMov;
		this.descripcion = descripcion;
	}



	public void Copiar(GastoVO gastoVO){
		
		this.setUsuarioMod(gastoVO.getUsuarioMod());
		this.setFechaMod(gastoVO.getFechaMod());
		this.setOperacion(gastoVO.getOperacion());

		this.codGasto = gastoVO.getCodGasto();
		this.codProceso = gastoVO.getCodProceso();
		this.codCliente = gastoVO.getCodCliente();
		this.nomCliente = gastoVO.getNomCliente();
		this.codMoneda = gastoVO.getCodMoneda();
		this.descMoneda = gastoVO.getCodMoneda();
		this.simboloMoneda = gastoVO.getSimboloMoneda();
		this.codCuenta = gastoVO.getCodCuenta();
		this.descripcionCuenta = gastoVO.getDescripcionCuenta();
		this.codRubro = gastoVO.getCodRubro();
		this.descripcionRubro = gastoVO.getDescripcionRubro();
		this.tipoRubro = gastoVO.getTipoRubro();
		this.codImpuesto = gastoVO.getCodImpuesto();
		this.descripcionImpuesto = gastoVO.getDescripcionImpuesto();
		this.porcentajeImpuesto = gastoVO.getPorcentajeImpuesto();
		this.fecha = gastoVO.getFecha();
		this.impMo = gastoVO.getImpMo();
		this.impMn = gastoVO.getImpMn();
		this.tcMov = gastoVO.getTcMov();
		this.descripcion = gastoVO.getDescripcion();
		
	}

	public int getCodGasto() {
		return codGasto;
	}

	public void setCodGasto(int codGasto) {
		this.codGasto = codGasto;
	}

	public int getCodProceso() {
		return codProceso;
	}

	public void setCodProceso(int codProceso) {
		this.codProceso = codProceso;
	}

	public String getCodCliente() {
		return codCliente;
	}

	public void setCodCliente(String codCliente) {
		this.codCliente = codCliente;
	}

	public String getNomCliente() {
		return nomCliente;
	}

	public void setNomCliente(String nomCliente) {
		this.nomCliente = nomCliente;
	}

	public String getCodMoneda() {
		return codMoneda;
	}

	public void setCodMoneda(String codMoneda) {
		this.codMoneda = codMoneda;
	}

	public String getDescMoneda() {
		return descMoneda;
	}

	public void setDescMoneda(String descMoneda) {
		this.descMoneda = descMoneda;
	}

	public String getSimboloMoneda() {
		return simboloMoneda;
	}

	public void setSimboloMoneda(String simboloMoneda) {
		this.simboloMoneda = simboloMoneda;
	}

	public String getCodCuenta() {
		return codCuenta;
	}

	public void setCodCuenta(String codCuenta) {
		this.codCuenta = codCuenta;
	}

	public String getDescripcionCuenta() {
		return descripcionCuenta;
	}

	public void setDescripcionCuenta(String descripcionCuenta) {
		this.descripcionCuenta = descripcionCuenta;
	}

	public String getCodRubro() {
		return codRubro;
	}

	public void setCodRubro(String codRubro) {
		this.codRubro = codRubro;
	}

	public String getDescripcionRubro() {
		return descripcionRubro;
	}

	public void setDescripcionRubro(String descripcionRubro) {
		this.descripcionRubro = descripcionRubro;
	}

	public String getTipoRubro() {
		return tipoRubro;
	}

	public void setTipoRubro(String tipoRubro) {
		this.tipoRubro = tipoRubro;
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



	public String getCodImpuesto() {
		return codImpuesto;
	}



	public void setCodImpuesto(String codImpuesto) {
		this.codImpuesto = codImpuesto;
	}



	public String getDescripcionImpuesto() {
		return descripcionImpuesto;
	}



	public void setDescripcionImpuesto(String descripcionImpuesto) {
		this.descripcionImpuesto = descripcionImpuesto;
	}



	public float getPorcentajeImpuesto() {
		return porcentajeImpuesto;
	}



	public void setPorcentajeImpuesto(float porcentajeImpuesto) {
		this.porcentajeImpuesto = porcentajeImpuesto;
	}

	
}
