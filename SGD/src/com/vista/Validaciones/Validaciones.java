package com.vista.Validaciones;

import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import com.controladores.PeriodoControlador;
import com.excepciones.ConexionException;
import com.excepciones.InicializandoException;
import com.excepciones.NoTienePermisosException;
import com.excepciones.ObteniendoPermisosException;
import com.excepciones.Periodo.ExistePeriodoException;
import com.excepciones.Periodo.NoExistePeriodoException;
import com.valueObject.UsuarioPermisosVO;

public class Validaciones {
	
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

}
