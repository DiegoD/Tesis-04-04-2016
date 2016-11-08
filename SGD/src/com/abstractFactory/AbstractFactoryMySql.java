package com.abstractFactory;

import com.persistencia.*;

public class AbstractFactoryMySql implements IAbstractFactory
{

	@Override
	public IDAOUsuarios crearDAOUsuarios() {
		
		return new DAOUsuarios();
	}

	@Override
	public IDAOGrupos crearDAOGrupos() {
		
		return new DAOGrupos();
	}

	@Override
	public IDAOClientes crearDAOClientes() {
		
		return new DAOClientes();
	}
	

	@Override
	public IDAOImpuestos crearDAOImpuestos() {
		
		return new DAOImpuestos();
	}
	
	@Override
	public IDAOMonedas crearDAOMonedas() {
		
		return new DAOMonedas();
	}
	
	@Override
	public IDAOEmpresas crearDAOEmpresas() {
		
		return new DAOEmpresas();
	}
	
	@Override
	public IDAORubros crearDAORubros() {
		
		return new DAORubros();
	}
	
	@Override
	public IDAODocumentos crearDAODocumentos() {
		
		return new DAODocumentos();
	}
	
	@Override
	public IDAOCodigosGeneralizados crearDAOCodigosGeneralizados () {
		
		return new DAOCodigoGeneralizado();
	}

	@Override
	public IDAODocumDgi crearDAODocumDgi() {
		
		return new DAODocumDGI();
	}

	@Override
	public IDAOFuncionarios crearDAOFuncionarios() {
		
		return new DAOFuncionarios();
	}
	
	@Override
	public IDAOCotizaciones crearDAOCotizaciones() {
		
		return new DAOCotizaciones();
	}
	
	@Override
	public IDAOTipoRubro crearDAOTipoRubro() {
		
		return new DAOTipoRubro();
	}
	
	@Override
	public IDAOCuentas crearDAOCuentas() {
		
		return new DAOCuentas();
	}

	@Override
	public IDAOBancos crearDAOBancos() {
		
		return new DAOBancos();
	}
	
	@Override
	public IDAOProcesos crearDAOProcesos() {
		
		return new DAOProcesos();
	}

	
	@Override
	public IDAOIngresoCobro crearDAOIngresoCobro() {
		
		return new DAOIngresoCobro();
	}


	
	@Override
	public IDAONumeradores crearDAONumeradores() {
		
		return new DAONumeradores();
	}
	
	@Override
	public IDAOGastos crearDAOGastos() {
		
		return new DAOGastos();
	}
	
	@Override
	public IDAOSaldos crearDAOSaldos() {
		
		return new DAOSaldos();
	}

	@Override
	public IDAODocLog crearDAODocLog() {
		
		return new DAODocLog();
	}

	@Override
	public IDAOCheques crearDAOCheques() {
		
		return new DAOCheques();
	}

	@Override
	public IDAOSaldosCuentas crearDAOSaldosCuenta() {

		return new DAOSaldoCuentas();
	}

	@Override
	public IDAOSaldosProc crearDAOSaldosProceso() {
		
		return new DAOSaldoProceso();
	}


	@Override
	public IDAOEgresoCobro crearDAOEgresoCobro() {
		
		return new DAOEgresoCobro();
	}


	
	@Override
	public IDAOTitulares crearDAOTitulares() {
		
		return new DAOTitulares();
	}
	
	@Override
	public IDAOPeriodo crearDAOPeriodo () {
		
		return new DAOPeriodo();
	}

}
