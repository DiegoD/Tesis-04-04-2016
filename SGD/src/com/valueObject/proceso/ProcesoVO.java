package com.valueObject.proceso;

import java.sql.Timestamp;

import com.valueObject.AuditoriaVO;
import com.valueObject.IngresoCobro.IngresoCobroDetalleVO;

public class ProcesoVO extends AuditoriaVO{

	private int codigo;
	private int linea;
	
	private String codCliente;
	private String nomCliente;
	
	private String codMoneda;
	private String descMoneda;
	private String simboloMoneda;

	
	private Timestamp fecha;
	private String nroMega;
	private String codDocum;
	private String nomDocum;
	private String nroDocum;
	private Timestamp fecDocum;
	private String carpeta;
	private double impMo;
	private double impMn;
	private double impTr;
	
	private double impSubTot;
	
	private double tcMov;
	private double kilos;
	private Timestamp fecCruce;
	private String marca;
	private String medio;
	private String descripcion;
	private String observaciones;
	
	private String codRubro;
	private String nomRubro;
	private String codCuenta;
	private String nomCuenta;
	private String codImpuesto;
	

	
	public ProcesoVO(){
		
	}
	
	public void copiar(ProcesoVO procesoVO){
		
		this.setUsuarioMod(procesoVO.getUsuarioMod());
		this.setFechaMod(procesoVO.getFechaMod());
		this.setOperacion(procesoVO.getOperacion());
		
		this.codCliente = procesoVO.getCodCliente();
		this.nomCliente = procesoVO.getNomCliente();
		this.codMoneda = procesoVO.getCodMoneda();
		this.descMoneda = procesoVO.getDescMoneda();
		this.simboloMoneda = procesoVO.getSimboloMoneda();
		this.codigo = procesoVO.getCodigo();
		this.fecha = procesoVO.getFecha();
		this.nroMega = procesoVO.getNroMega();
		this.codDocum = procesoVO.getCodDocum();
		this.nroDocum = procesoVO.getNroDocum();
		this.nomDocum = procesoVO.getNomDocum();
		this.fecDocum = procesoVO.getFecDocum();
		this.carpeta = procesoVO.getCarpeta();
		this.impMo = procesoVO.getImpMo();
		this.impMn = procesoVO.getImpMn();
		this.impTr = procesoVO.getImpTr();
		this.tcMov = procesoVO.getTcMov();
		this.kilos = procesoVO.getKilos();
		this.fecCruce = procesoVO.getFecCruce();
		this.marca = procesoVO.getMarca();
		this.medio = procesoVO.getMedio();
		this.descripcion = procesoVO.getDescripcion();
		this.observaciones = procesoVO.getObservaciones();
		this.codCuenta = procesoVO.getCodCuenta();
		this.nomCuenta = procesoVO.getNomCuenta();
		this.codRubro = procesoVO.getCodRubro();
		this.nomRubro = procesoVO.getNomRubro();
		this.impSubTot = procesoVO.impSubTot;
		this.codImpuesto = procesoVO.codImpuesto; 
		this.linea = procesoVO.linea;
	}
	
	public IngresoCobroDetalleVO crearDocumDetalle(ProcesoVO procesoVO){
		IngresoCobroDetalleVO docum = new IngresoCobroDetalleVO();
		
		docum.setCodCtaInd(String.valueOf(procesoVO.getCodigo()));
		docum.setCodCuenta(String.valueOf(procesoVO.getCodigo()));
		docum.setCodDocum(String.valueOf(procesoVO.getCodigo()));
		docum.setReferencia(procesoVO.getDescripcion());
		docum.setCodImpuesto("0");
		docum.setNomImpuesto("");
		docum.setPorcentajeImpuesto(0);
		docum.setImpImpuMn(0);
		docum.setImpImpuMo(0);
		docum.setImpSubMn(0);
		docum.setImpSubMo(0);
		docum.setImpTotMn(0);
		docum.setImpTotMo(0);
		docum.setTcMov(0);
		docum.setCodCuenta(procesoVO.getCodCuenta());
		docum.setNomCuenta(procesoVO.getNomCuenta());
		docum.setCodProceso(String.valueOf(procesoVO.getCodigo()));
		docum.setDescProceso(procesoVO.getDescripcion());
		docum.setNomRubro(procesoVO.getNomRubro());
		docum.setCodRubro(procesoVO.getCodRubro());
		
		
		/***VER DE DONDE SACAR***/
		docum.setUsuarioMod(procesoVO.getUsuarioMod());
		docum.setOperacion(procesoVO.getOperacion());
		
		
		
		return docum;
	}
	
	public int getCodigo() {
		return codigo;
	}
	
	
	public void setCodigo(int codigo) {
		this.codigo = codigo;
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
	public Timestamp getFecha() {
		return fecha;
	}
	public void setFecha(Timestamp timestamp) {
		this.fecha = timestamp;
	}
	public String getNroMega() {
		return nroMega;
	}
	public void setNroMega(String nroMega) {
		this.nroMega = nroMega;
	}
	public String getCodDocum() {
		return codDocum;
	}
	public void setCodDocum(String codDocum) {
		this.codDocum = codDocum;
	}
	public String getNroDocum() {
		return nroDocum;
	}
	
	public String getNomDocum() {
		return nomDocum;
	}

	public void setNomDocum(String nomDocum) {
		this.nomDocum = nomDocum;
	}

	public void setNroDocum(String nroDocum) {
		this.nroDocum = nroDocum;
	}
	public Timestamp getFecDocum() {
		return fecDocum;
	}
	public void setFecDocum(Timestamp fecDocum) {
		this.fecDocum = fecDocum;
	}
	public String getCarpeta() {
		return carpeta;
	}
	public void setCarpeta(String carpeta) {
		this.carpeta = carpeta;
	}
	
	public double getImpMo() {
		return impMo;
	}
	public void setImpMo(double impMo) {
		this.impMo = impMo;
	}
	public double getImpMn() {
		return impMn;
	}
	public void setImpMn(double impMn) {
		this.impMn = impMn;
	}
	public double getImpTr() {
		return impTr;
	}
	public void setImpTr(double impTr) {
		this.impTr = impTr;
	}
	public double getTcMov() {
		return tcMov;
	}
	public void setTcMov(double tcMov) {
		this.tcMov = tcMov;
	}
	public double getKilos() {
		return kilos;
	}
	public void setKilos(double kilos) {
		this.kilos = kilos;
	}
	
	public Timestamp getFecCruce() {
		return fecCruce;
	}

	public void setFecCruce(Timestamp fecCruce) {
		this.fecCruce = fecCruce;
	}

	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public String getMedio() {
		return medio;
	}
	public void setMedio(String medio) {
		this.medio = medio;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public String getCodRubro() {
		return codRubro;
	}

	public void setCodRubro(String codRubro) {
		this.codRubro = codRubro;
	}

	public String getNomRubro() {
		return nomRubro;
	}

	public void setNomRubro(String nomRubro) {
		this.nomRubro = nomRubro;
	}

	public String getCodCuenta() {
		return codCuenta;
	}

	public void setCodCuenta(String codCuenta) {
		this.codCuenta = codCuenta;
	}

	public String getNomCuenta() {
		return nomCuenta;
	}

	public void setNomCuenta(String nomCuenta) {
		this.nomCuenta = nomCuenta;
	}

	public double getImpSubTot() {
		return impSubTot;
	}

	public void setImpSubTot(double impSubTot) {
		this.impSubTot = impSubTot;
	}

	public String getCodImpuesto() {
		return codImpuesto;
	}

	public void setCodImpuesto(String codImpuesto) {
		this.codImpuesto = codImpuesto;
	}

	public int getLinea() {
		return linea;
	}

	public void setLinea(int linea) {
		this.linea = linea;
	}
	
	
	
}
