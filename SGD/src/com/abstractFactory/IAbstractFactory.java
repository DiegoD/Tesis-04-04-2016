package com.abstractFactory;

import com.persistencia.IDAOBancos;
import com.persistencia.IDAOCheques;
import com.persistencia.IDAOClientes;
import com.persistencia.IDAOCodigosGeneralizados;
import com.persistencia.IDAOConciliaciones;
import com.persistencia.IDAOCotizaciones;
import com.persistencia.IDAOCuentas;
import com.persistencia.IDAODepositos;
import com.persistencia.IDAODocLog;
import com.persistencia.IDAODocumDgi;
//import com.persistencia.IDAODocumDgi;
import com.persistencia.IDAODocumentos;
import com.persistencia.IDAOEgresoCobro;
import com.persistencia.IDAOEmpresas;
import com.persistencia.IDAOFacturas;
import com.persistencia.IDAOFuncionarios;
import com.persistencia.IDAOGastos;
import com.persistencia.IDAOGrupos;
import com.persistencia.IDAOImpuestos;
import com.persistencia.IDAOIngresoCobro;
import com.persistencia.IDAOMonedas;
import com.persistencia.IDAONotaCredito;
import com.persistencia.IDAONumeradores;
import com.persistencia.IDAOPeriodo;
import com.persistencia.IDAOProcesos;
import com.persistencia.IDAORecibos;
import com.persistencia.IDAORubros;
import com.persistencia.IDAOSaldos;
import com.persistencia.IDAOSaldosCuentas;
import com.persistencia.IDAOSaldosProc;
import com.persistencia.IDAOTipoRubro;
import com.persistencia.IDAOTitulares;
import com.persistencia.IDAOUsuarios;

public interface IAbstractFactory 
{

	public IDAOUsuarios crearDAOUsuarios();
	
	public IDAOGrupos crearDAOGrupos();

	
	public IDAOClientes crearDAOClientes();

	public IDAOImpuestos crearDAOImpuestos();
	
	public IDAOMonedas crearDAOMonedas();
	
	public IDAOEmpresas crearDAOEmpresas() ;
	
	public IDAORubros crearDAORubros();
	
	public IDAODocumentos crearDAODocumentos();
	
	public IDAODocumDgi crearDAODocumDgi();
	
	public IDAOFuncionarios crearDAOFuncionarios();
	
	public IDAOCodigosGeneralizados crearDAOCodigosGeneralizados ();
	
	public IDAOCotizaciones crearDAOCotizaciones();
	
	public IDAOTipoRubro crearDAOTipoRubro();

	public IDAOCuentas crearDAOCuentas();
	
	public IDAOBancos crearDAOBancos();
	
	public IDAOProcesos crearDAOProcesos();

	public IDAOIngresoCobro crearDAOIngresoCobro();
	
	public IDAOEgresoCobro crearDAOEgresoCobro();
	
	public IDAONumeradores crearDAONumeradores();
	
	public IDAOGastos crearDAOGastos();
	
	public IDAOSaldos crearDAOSaldos();
	
	public IDAODocLog crearDAODocLog();
	
	public IDAOCheques crearDAOCheques();
	
	public IDAOSaldosCuentas crearDAOSaldosCuenta();
	
	public IDAOSaldosProc crearDAOSaldosProceso();
	
	public IDAOTitulares crearDAOTitulares();

	public IDAOPeriodo crearDAOPeriodo ();
	
	public IDAODepositos crearDAODeposito();

	public IDAOFacturas crearDAOFactura();
	
	public IDAORecibos crearDAORecibos();
	
	public IDAONotaCredito crearDAONotaCredito();
	
	public IDAOConciliaciones crearDAOConciliaciones();
}
