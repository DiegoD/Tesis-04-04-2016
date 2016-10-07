package com.logica.Docum;

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
		
		return docSaldo;
		
	}
	
}
