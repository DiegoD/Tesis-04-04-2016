package com.vista.Validaciones;

import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import com.controladores.PeriodoControlador;
import com.controladores.ValidacionesControlador;
import com.excepciones.ConexionException;
import com.excepciones.InicializandoException;
import com.excepciones.NoTienePermisosException;
import com.excepciones.ObteniendoPermisosException;
import com.excepciones.Cheques.ExisteChequeException;
import com.excepciones.Conciliaciones.MovimientoConciliadoException;
import com.excepciones.Depositos.ExisteDepositoException;
import com.excepciones.Egresos.ExisteEgresoCobroException;
import com.excepciones.Factura.ExisteFacturaException;
import com.excepciones.Gastos.ExisteGastoException;
import com.excepciones.IngresoCobros.ExisteIngresoCobroException;
import com.excepciones.NotaCredito.ExisteNotaCreditoException;
import com.excepciones.Periodo.ExistePeriodoException;
import com.excepciones.Periodo.NoExistePeriodoException;
import com.excepciones.Recibo.ExisteReciboException;
import com.excepciones.SaldoCuentas.ExisteNroTransferencia;
import com.excepciones.Saldos.ExisteSaldoException;
import com.valueObject.UsuarioPermisosVO;
import com.valueObject.Cheque.ChequeVO;
import com.valueObject.Deposito.DepositoVO;
import com.valueObject.IngresoCobro.IngresoCobroDetalleVO;

public class Validaciones {
	
	ValidacionesControlador validaciones;
	
	public Validaciones(){
		
	}
	
	public boolean validaPeriodo(java.util.Date date, UsuarioPermisosVO permisos) throws NumberFormatException, ExistePeriodoException, ConexionException, SQLException, NoExistePeriodoException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException{
		
		String fecha = new SimpleDateFormat("dd/MM/yyyy").format(date);
		String mes, mesValidar = "";
		Integer anio;
		
		anio = Integer.valueOf(fecha.substring(6, 10));
		
		PeriodoControlador periodo = new PeriodoControlador();
		
		mes = fecha.substring(3, 5);
		
		if(mes.equals("01")){
			mesValidar = "Enero";
		}
		else if(mes.equals("02")){
			mesValidar = "Febrero";
		}
		else if(mes.equals("03")){
			mesValidar = "Marzo";
		}
		else if(mes.equals("04")){
			mesValidar = "Abril";
		}
		else if(mes.equals("05")){
			mesValidar = "Mayo";
		}
		else if(mes.equals("06")){
			mesValidar = "Junio";
		}
		else if(mes.equals("07")){
			mesValidar = "Julio";
		}
		else if(mes.equals("08")){
			mesValidar = "Agosto";
		}
		else if(mes.equals("09")){
			mesValidar = "Setiembre";
		}
		else if(mes.equals("10")){
			mesValidar = "Octubre";
		}
		else if(mes.equals("11")){
			mesValidar = "Noviembre";
		}
		
		else if(mes.equals("12")){
			mesValidar = "Diciembre";
		}
		
		return periodo.validaPeriodo(mesValidar, anio, permisos);
	}
	
	public boolean existeCheque(UsuarioPermisosVO permisos, ChequeVO cheque) throws ObteniendoPermisosException, ConexionException, InicializandoException, ExisteChequeException, NoTienePermisosException{
		
		validaciones = new ValidacionesControlador();
		return validaciones.existeCheque(cheque, permisos);
	}
	
	public boolean existeTransferencia(UsuarioPermisosVO permisos, String nro, String codBco, String codCta) throws ObteniendoPermisosException, ConexionException, InicializandoException, NoTienePermisosException, NumberFormatException, ExisteNroTransferencia{
		
		validaciones = new ValidacionesControlador();
		return validaciones.existeNroTransferencia(nro, codBco, codCta, permisos);
	}

	public boolean existeChequeDepositado(UsuarioPermisosVO permisos, ChequeVO cheque) throws ObteniendoPermisosException, ConexionException, InicializandoException, NoTienePermisosException, ExisteDepositoException{
		
		validaciones = new ValidacionesControlador();
		return validaciones.existeChequeDepospitado(cheque, permisos);
	}
	
	public boolean existeEgreso(UsuarioPermisosVO permisos, Integer nro_docum) throws ConexionException, ExisteEgresoCobroException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException {
		
		validaciones = new ValidacionesControlador();
		return validaciones.existeEgreso(nro_docum, permisos);
	}
	
	public boolean existeFacturaGasto(UsuarioPermisosVO permisos, Integer nro_docum) throws ConexionException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException, ExisteFacturaException {
		
		validaciones = new ValidacionesControlador();
		return validaciones.existeGastoFactura(nro_docum, permisos);
	}
	
	public boolean existeGastoIngresoCobro(UsuarioPermisosVO permisos, Integer nro_docum) throws ConexionException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException, ExisteIngresoCobroException {
		
		validaciones = new ValidacionesControlador();
		return validaciones.existeGastoIngresoCobro(nro_docum, permisos);
	}

	public boolean depositoConciliado(UsuarioPermisosVO permisos, DepositoVO deposito) throws ConexionException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException, NumberFormatException, MovimientoConciliadoException {
		
		validaciones = new ValidacionesControlador();
		return validaciones.depositoConciliado(deposito, permisos);
	}
	
	public boolean validaNumDeposito(UsuarioPermisosVO permisos, String codBco, String codCta, String nro) throws ConexionException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException, NumberFormatException, ExisteDepositoException {
		
		validaciones = new ValidacionesControlador();
		return validaciones.validaNumDeposito(permisos, codBco, codCta, nro);
	}
	
	public boolean egresoConciliado(UsuarioPermisosVO permisos, String nroEgreso) throws ConexionException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException, NumberFormatException, MovimientoConciliadoException {
		
		validaciones = new ValidacionesControlador();
		return validaciones.egresoConciliado(permisos, nroEgreso);
	}
	
	public boolean ingresoConciliado(UsuarioPermisosVO permisos, String nroEgreso) throws ConexionException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException, NumberFormatException, MovimientoConciliadoException {
		
		validaciones = new ValidacionesControlador();
		return validaciones.ingresoConciliado(permisos, nroEgreso);
	}
	
	//Comento porque no se usa para ver que no esté en otro lado
//	public boolean existeGastoAsociado(UsuarioPermisosVO permisos, IngresoCobroDetalleVO detalle) throws ObteniendoPermisosException, ConexionException, InicializandoException, NoTienePermisosException, NumberFormatException, ExisteGastoException{
//		
//		validaciones = new ValidacionesControlador();
//		return validaciones.existeGastoAsociado(detalle, permisos);
//	}
	
	public boolean existeGastoAsociadoProceso(UsuarioPermisosVO permisos, Integer nroProceso) throws ObteniendoPermisosException, ConexionException, InicializandoException, NoTienePermisosException, NumberFormatException, ExisteGastoException{
		
		validaciones = new ValidacionesControlador();
		return validaciones.existeGastoAsociadoProceso(nroProceso, permisos);
	}
	
	public boolean existeSaldoAsociadoProceso(UsuarioPermisosVO permisos, Integer nroProceso) throws ObteniendoPermisosException, ConexionException, InicializandoException, NoTienePermisosException, NumberFormatException, ExisteGastoException, ExisteSaldoException{
		
		validaciones = new ValidacionesControlador();
		return validaciones.existeSaldoAsociadoProceso(nroProceso, permisos);
	}
	
	public boolean existeReciboFactura(UsuarioPermisosVO permisos, Integer nro_docum, String serie_docum) throws ConexionException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException, ExisteReciboException {
		
		validaciones = new ValidacionesControlador();
		return validaciones.existeReciboFactura(nro_docum, serie_docum, permisos);
	}
	
	public boolean existeNCFactura(UsuarioPermisosVO permisos, Integer nro_docum, String serie_docum) throws ConexionException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException, ExisteNotaCreditoException {
		
		validaciones = new ValidacionesControlador();
		return validaciones.existeNCFactura(nro_docum, serie_docum, permisos);
	}
}
