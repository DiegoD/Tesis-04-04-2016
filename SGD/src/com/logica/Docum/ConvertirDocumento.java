package com.logica.Docum;

import com.logica.Moneda;
import com.logica.MonedaInfo;
import com.valueObject.Docum.DatosDocumVO;
import com.valueObject.IngresoCobro.IngresoCobroVO;

/**
* Esta es una clase auxilar que la utilizamos para poder 
* convertir a distintos documentos dado distintos
* tipos de dato como parametro
 * 
*
*/
public class ConvertirDocumento {

	/**
	*Nos retorna un DatosDocumVO para ingresar el cheque dado un IngresoCobro
	 * Lo utilizamos para ingresar el cheque
	*
	*/
	public static DatosDocumVO getDatosDocumChequeDadoIngCobro(IngresoCobroVO ingVO){
		
		DatosDocumVO aux = new DatosDocumVO();
		
		aux.copiar(ingVO);
		
		aux.setCodDocum(ingVO.getCodDocRef());
		aux.setSerieDocum(ingVO.getSerieDocRef());
		aux.setNroDocum(ingVO.getNroDocRef());
		
		return aux;
		
	}
	
	/**
	*Nos retorna un DatosDocumVO para ingresar el cheque dado un IngresoCobro
	 * Lo utilizamos para ingresar el cheque
	*
	*/
	public static DatosDocumVO getDatosDocumChequeDadoEgrCobro(IngresoCobroVO ingVO){
		
		DatosDocumVO aux = new DatosDocumVO();
		
		aux.copiar(ingVO);
		
		aux.setCodDocum(ingVO.getCodDocRef());
		aux.setSerieDocum(ingVO.getSerieDocRef());
		aux.setNroDocum(ingVO.getNroDocRef());
		
		return aux;
		
	}
	
	/**
	*Nos retorna un DocumSaldo para ingresar el saldo a la cuenta correspondiente
	*dado el ingreso cobro
	*
	*/
	public static DocumSaldo getDocumSaldoChequeDadoIngCobro(IngresoCobroVO ingVO){
		
		DatosDocumVO aux = new DatosDocumVO();
		
		aux.copiar(ingVO);
		
		DocumSaldo docSaldo = new DocumSaldo(aux);//new DocumSaldo(); //(DocumSaldo)doc;
		//docSaldo
		
		docSaldo.setCodDocum(ingVO.getCodDocRef());
		docSaldo.setSerieDocum(ingVO.getSerieDocRef());
		docSaldo.setNroDocum(ingVO.getNroDocRef());
		
		//private String codBco;
		//private String codCtaBco;
		//private String movimiento;
		
		docSaldo.setCodBco(ingVO.getCodBanco());
		docSaldo.setCodCtaBco(ingVO.getCodCtaBco());
		docSaldo.setMovimiento(ingVO.getReferencia());
		
		docSaldo.setSigno(1); /*Signo positivo*/
		
		return docSaldo;
		
	}
	
	
	/**
	*Nos retorna un DocumSaldo para ingresar el saldo a la cuenta correspondiente
	*dado el egreso cobro
	*
	*/
	public static DocumSaldo getDocumSaldoChequeDadoEgrCobro(IngresoCobroVO ingVO){
		
		DatosDocumVO aux = new DatosDocumVO();
		
		aux.copiar(ingVO);
		
		DocumSaldo docSaldo = new DocumSaldo(aux);//new DocumSaldo(); //(DocumSaldo)doc;
		//docSaldo
		
		docSaldo.setCodDocum(ingVO.getCodDocRef());
		docSaldo.setSerieDocum(ingVO.getSerieDocRef());
		docSaldo.setNroDocum(ingVO.getNroDocRef());
		
		//private String codBco;
		//private String codCtaBco;
		//private String movimiento;
		
		docSaldo.setCodBco(ingVO.getCodBanco());
		docSaldo.setCodCtaBco(ingVO.getCodCtaBco());
		docSaldo.setMovimiento(ingVO.getReferencia());
		
		docSaldo.setSigno(-1); /*Signo negativo*/
		
		return docSaldo;
		
	}
	
	/**
	*Nos retorna un DocumSaldo para ingresar el saldo a la cuenta correspondiente
	*dado el ingreso cobro
	*
	*/
	public static DocumSaldo getDocumSaldoSaCuentasIngCobro(IngresoCobroVO ingVO){
		
		DatosDocumVO aux = new DatosDocumVO();
		
		aux.copiar(ingVO);
		
		aux.setNroTrans(ingVO.getNroTrans());
		
		DocumSaldo docSaldo = new DocumSaldo(aux);
		
		docSaldo.setCodDocum(ingVO.getCodDocum()); /*Documento del cobro*/
		docSaldo.setSerieDocum("0");
		docSaldo.setNroDocum(ingVO.getNroDocum()); /*Nro docum del cobro*/
		
		//private String codBco;
		//private String codCtaBco;
		//private String movimiento;
		
		if(ingVO.getmPago().equals("Caja")) /*Si es caja */
		{
			docSaldo.setCodBco("0");
			docSaldo.setCodCtaBco("0");
			docSaldo.setMovimiento("0");
			
			docSaldo.setCodDocumRef("0"); /*Documento del cobro*/
			docSaldo.setSerieDocumRef("0");
			docSaldo.setNroDocumRef(0); /*Nro docum del cobro*/
		}else if(ingVO.getmPago().toUpperCase().equals("TRANSFERENCIA")){ /*Si es transferencia*/
			
			docSaldo.setCodDocumRef(ingVO.getCodDocRef()); /*Documento del cobro*/
			docSaldo.setSerieDocumRef("0");
			docSaldo.setNroDocumRef(ingVO.getNroDocRef()); /*Nro docum del cobro*/
			
		}else if(ingVO.getmPago().toUpperCase().equals("CHEQUE")){ /*Si es transferencia*/
			
			docSaldo.setCodDocumRef(ingVO.getCodDocRef()); /*Documento del cobro*/
			docSaldo.setSerieDocumRef(ingVO.getSerieDocRef());
			docSaldo.setNroDocumRef(ingVO.getNroDocRef()); /*Nro docum del cobro*/
			
		}
			
		
		if(!ingVO.getmPago().equals("Caja")) {
			
			docSaldo.setCodBco(ingVO.getCodBanco());
			docSaldo.setCodCtaBco(ingVO.getCodCtaBco());
			docSaldo.setMovimiento(ingVO.getReferencia());
			
		}else{
			docSaldo.setCodBco("0");
			docSaldo.setCodCtaBco("0");
			docSaldo.setMovimiento("0");
		}
		
		docSaldo.setSigno(1); /*Signo positivo*/
		
		MonedaInfo mInf = new MonedaInfo(); /*Moneda a setear*/
		
		/*Si es por banco si no la del cabezal*/
		if(!ingVO.getmPago().equals("Caja")) {
			
			mInf.setCodMoneda(ingVO.getCodMonedaCtaBco());
			mInf.setNacional(ingVO.isNacionalMonedaCtaBco());
	    	
		}else 
		{
			mInf.setCodMoneda(ingVO.getCodMoneda());
			mInf.setNacional(ingVO.isNacional());
		}
		
		docSaldo.setMoneda(mInf);
		
		if(docSaldo.getMoneda().isNacional())
		{
			docSaldo.setImpTotMo(docSaldo.getImpTotMn());
		}
		
		return docSaldo;
		
	}
	
	/**
	*Nos retorna un DocumSaldo para ingresar el saldo a la cuenta correspondiente
	*dado el ingreso cobro
	*
	*/
	public static DocumSaldo getDocumSaldoSaCuentasEgresoCobro(IngresoCobroVO ing){
		
		DatosDocumVO aux = new DatosDocumVO();
		
		aux.copiar(ing);
		
		
		
		aux.setNroTrans(ing.getNroTrans());
		
		DocumSaldo docSaldo = new DocumSaldo(aux);
		
		docSaldo.setCodDocum(ing.getCodDocum()); /*Documento del cobro*/
		docSaldo.setSerieDocum("0");
		docSaldo.setNroDocum(ing.getNroDocum()); /*Nro docum del cobro*/
		
		//private String codBco;
		//private String codCtaBco;
		//private String movimiento;
		
		if(ing.getmPago().equals("Caja")) /*Si es caja */
		{
			docSaldo.setCodBco("0");
			docSaldo.setCodCtaBco("0");
			docSaldo.setMovimiento("0");
			
			docSaldo.setCodDocumRef("0"); /*Documento del cobro*/
			docSaldo.setSerieDocumRef("0");
			docSaldo.setNroDocumRef(0); /*Nro docum del cobro*/
		}else if(ing.getmPago().toUpperCase().equals("TRANSFERENCIA")){ /*Si es transferencia*/
			
			docSaldo.setCodDocumRef(ing.getCodDocRef()); /*Documento del cobro*/
			docSaldo.setSerieDocumRef("0");
			docSaldo.setNroDocumRef(ing.getNroDocRef()); /*Nro docum del cobro*/
			
		}else if(ing.getmPago().toUpperCase().equals("CHEQUE")){ /*Si es transferencia*/
			
			docSaldo.setCodDocumRef(ing.getCodDocRef()); /*Documento del cobro*/
			docSaldo.setSerieDocumRef(ing.getSerieDocRef());
			docSaldo.setNroDocumRef(ing.getNroDocRef()); /*Nro docum del cobro*/
			
		}
			
		
		if(!ing.getmPago().equals("Caja")) {
			
			docSaldo.setCodBco(ing.getCodBanco());
			docSaldo.setCodCtaBco(ing.getCodCtaBco());
			docSaldo.setMovimiento(ing.getReferencia());
			
		}else{
			docSaldo.setCodBco("0");
			docSaldo.setCodCtaBco("0");
			docSaldo.setMovimiento("0");
		}
		
		docSaldo.setSigno(-1); /*Signo negativo por el egreso*/
		
		MonedaInfo mInf = new MonedaInfo(); /*Moneda a setear*/
		
		/*Si es por banco si no la del cabezal*/
		if(!ing.getmPago().equals("Caja")) {
			
			mInf.setCodMoneda(ing.getCodMonedaCtaBco());
			mInf.setNacional(ing.isNacionalMonedaCtaBco());
	    	
		} else 
		{
			mInf.setCodMoneda(ing.getCodMoneda());
			mInf.setNacional(ing.isNacional());
		}
		
		docSaldo.setMoneda(mInf);
		
		if(docSaldo.getMoneda().isNacional())
		{
			docSaldo.setImpTotMo(docSaldo.getImpTotMn());
		}
		
		return docSaldo;
		
	}
	
	/**
	*Dado una moneda, nos retorna una MonedaInfo
	*
	*/
	public static MonedaInfo getMonedainfoxMoneda(Moneda moneda){
		
		MonedaInfo monedaInf = new MonedaInfo();
		monedaInf.setCodMoneda(moneda.getCod_moneda());
		monedaInf.setDescripcion(moneda.getDescripcion());
		monedaInf.setSimbolo(moneda.getSimbolo());
		monedaInf.setNacional(moneda.isNacional());
		
		return monedaInf;
		
	}
	
}
