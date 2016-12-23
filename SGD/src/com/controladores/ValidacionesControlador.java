package com.controladores;

import java.util.ArrayList;

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
import com.excepciones.SaldoCuentas.ExisteNroTransferencia;
import com.excepciones.Saldos.ExisteSaldoException;
import com.logica.Fachada;
import com.logica.FachadaDD;
import com.valueObject.UsuarioPermisosVO;
import com.valueObject.Cheque.ChequeVO;
import com.valueObject.Deposito.DepositoVO;
import com.valueObject.IngresoCobro.IngresoCobroDetalleVO;


public class ValidacionesControlador {

	public boolean existeCheque(ChequeVO cheque, UsuarioPermisosVO permisos) throws ObteniendoPermisosException, ConexionException, InicializandoException, ExisteChequeException, NoTienePermisosException{
		
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			return FachadaDD.getInstance().existeCheque(cheque, permisos.getCodEmp());
		else
			throw new NoTienePermisosException();
    }
	
	public boolean existeNroTransferencia(String nro, String codBco, String codCta, UsuarioPermisosVO permisos) throws ObteniendoPermisosException, ConexionException, InicializandoException, NoTienePermisosException, NumberFormatException, ExisteNroTransferencia{
		
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			return FachadaDD.getInstance().existeNroTransferencia(nro, codBco, codCta, permisos.getCodEmp());
		else
			throw new NoTienePermisosException();
    }
	
	public boolean existeChequeDepospitado(ChequeVO cheque, UsuarioPermisosVO permisos) throws ObteniendoPermisosException, ConexionException, InicializandoException, NoTienePermisosException, ExisteDepositoException{
		
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			return FachadaDD.getInstance().existeChequeDepositado(cheque, permisos.getCodEmp());
		else
			throw new NoTienePermisosException();
    }
	
	public boolean existeEgreso(Integer nro_docum, UsuarioPermisosVO permisos) throws ConexionException, ExisteEgresoCobroException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException{
		
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			return FachadaDD.getInstance().existeEgreso(nro_docum, permisos.getCodEmp());
		else
			throw new NoTienePermisosException();
	}
	
	public boolean existeGastoFactura(Integer nro_docum, UsuarioPermisosVO permisos) throws ConexionException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException, ExisteFacturaException{
		
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			return FachadaDD.getInstance().existeGastoFactura(nro_docum, permisos.getCodEmp());
		else
			throw new NoTienePermisosException();
	}
	
	public boolean existeGastoIngresoCobro(Integer nro_docum, UsuarioPermisosVO permisos) throws ConexionException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException, ExisteIngresoCobroException{
		
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			return FachadaDD.getInstance().existeGastoIngresoCobro(nro_docum, permisos.getCodEmp());
		else
			throw new NoTienePermisosException();
	}
	
	public boolean depositoConciliado(DepositoVO deposito, UsuarioPermisosVO permisos) throws ConexionException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException, NumberFormatException, MovimientoConciliadoException{
		
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			return FachadaDD.getInstance().depositoConciliado(deposito, permisos.getCodEmp());
		else
			throw new NoTienePermisosException();
	}
	
	public boolean validaNumDeposito(UsuarioPermisosVO permisos, String codBco, String codCta, String nro) throws ConexionException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException, NumberFormatException, ExisteDepositoException{
		
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			return FachadaDD.getInstance().validaNumDeposito(codBco, codCta, nro, permisos.getCodEmp());
		else
			throw new NoTienePermisosException();
	}
	
	public boolean egresoConciliado(UsuarioPermisosVO permisos, String nroEgreso) throws ConexionException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException, NumberFormatException, MovimientoConciliadoException{
		
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			return FachadaDD.getInstance().egresoConciliado(permisos.getCodEmp(), nroEgreso);
		else
			throw new NoTienePermisosException();
	}
	
	public boolean ingresoConciliado(UsuarioPermisosVO permisos, String nroEgreso) throws ConexionException, InicializandoException, ObteniendoPermisosException, NoTienePermisosException, NumberFormatException, MovimientoConciliadoException{
		
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			return FachadaDD.getInstance().ingresoConciliado(permisos.getCodEmp(), nroEgreso);
		else
			throw new NoTienePermisosException();
	}
	
	public boolean existeGastoAsociado(IngresoCobroDetalleVO detalle, UsuarioPermisosVO permisos) throws ObteniendoPermisosException, ConexionException, InicializandoException, NoTienePermisosException, NumberFormatException, ExisteGastoException{
		
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			return FachadaDD.getInstance().existeGastoAsociado(detalle, permisos.getCodEmp());
		else
			throw new NoTienePermisosException();
    }
	
	public boolean existeGastoAsociadoProceso(Integer nroProceso, UsuarioPermisosVO permisos) throws ObteniendoPermisosException, ConexionException, InicializandoException, NoTienePermisosException, NumberFormatException, ExisteGastoException{
		
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			return FachadaDD.getInstance().existeGastoAsociadoProceso(nroProceso, permisos.getCodEmp());
		else
			throw new NoTienePermisosException();
    }
	
	public boolean existeSaldoAsociadoProceso(Integer nroProceso, UsuarioPermisosVO permisos) throws ObteniendoPermisosException, ConexionException, InicializandoException, NoTienePermisosException, NumberFormatException, ExisteSaldoException{
		
		/*Primero se verifican los permisos*/
		if(Fachada.getInstance().permisoEnFormulario(permisos))
			return FachadaDD.getInstance().existeSaldoAsociadoProceso(nroProceso, permisos.getCodEmp());
		else
			throw new NoTienePermisosException();
    }
}

