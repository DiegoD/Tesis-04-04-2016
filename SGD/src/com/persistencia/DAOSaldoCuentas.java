package com.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.excepciones.ConexionException;
import com.excepciones.Rubros.ObteniendoRubrosException;
import com.excepciones.SaldoCuentas.*;
import com.logica.RubroCuenta;
import com.logica.Docum.DatosDocum;
import com.logica.Docum.DocumSaldo;
import com.valueObject.Saldos.SaCuentasVO;
import com.valueObject.Saldos.SaDocumsVO;

public class DAOSaldoCuentas implements IDAOSaldosCuentas{

	
	/**
	 * Dado el documento, valida si existe
	 * @throws ExisteSaldoException 
	 */
	public boolean memberSaldoCta(DocumSaldo docum, Connection con) throws ExisteSaldoCuentaException{
		
		boolean existe = false;
		
		try{
			
			ConsultasDD consultas = new ConsultasDD ();
			String query = consultas.memberSaldoCuenta();
			
			
			PreparedStatement pstmt1 = con.prepareStatement(query);
			
			pstmt1.setLong(1, docum.getNroTrans());
			
			ResultSet rs = pstmt1.executeQuery();
			
			if (rs.next ()) 
				existe = true;
						
			rs.close ();
			pstmt1.close ();
			
			return existe;
			
		}catch(SQLException e){
			
			throw new ExisteSaldoCuentaException();
		}
	}
	
	public boolean existeNroTransferencia(Integer nro, String codBco, String codCta, String codEmp, Connection con) throws ExisteNroTransferencia{
		
		boolean existe = false;
		
		try{
			
			ConsultasDD consultas = new ConsultasDD ();
			String query = consultas.existeNroTransferencia();
			
			
			PreparedStatement pstmt1 = con.prepareStatement(query);
			
			pstmt1.setInt(1, nro);
			pstmt1.setString(2, codBco);
			pstmt1.setString(3, codCta);
			pstmt1.setString(4, codEmp);
			
			
			ResultSet rs = pstmt1.executeQuery();
			
			if (rs.next ()) 
				existe = true;
						
			rs.close ();
			pstmt1.close ();
			
			return existe;
			
		}catch(SQLException e){
			
			throw new ExisteNroTransferencia();
		}
	}


	public void insertarSaldoCuenta(DocumSaldo documento, Connection con)
			throws InsertandoSaldoCuentaException, ConexionException, SQLException {
		
		ConsultasDD clts = new ConsultasDD();
		
    	String insert = clts.insertarSaldoCuenta();
    	
    	
    	PreparedStatement pstmt1;
    	
    	try {
    		
			pstmt1 =  con.prepareStatement(insert);
			
			pstmt1.setString(1, documento.getCodDocum());
			pstmt1.setString(2, documento.getSerieDocum());
			pstmt1.setInt(3, documento.getNroDocum());
			pstmt1.setString(4, documento.getCodEmp());
			pstmt1.setString(5, documento.getMoneda().getCodMoneda());
			pstmt1.setString(6, documento.getTitInfo().getCodigo());
			pstmt1.setDouble(7, documento.getImpTotMn());
			pstmt1.setDouble(8, documento.getImpTotMo());
			pstmt1.setString(9, documento.getCodCuentaInd());
			pstmt1.setString(10, documento.getUsuarioMod());
			pstmt1.setString(11, documento.getOperacion());
			pstmt1.setString(12, documento.getCodCuentaInd());
			pstmt1.setString(13, documento.getReferencia());
			pstmt1.setLong(14, documento.getNroTrans());
			
			pstmt1.setString(15, documento.getCodDocumRef());
			pstmt1.setString(16, documento.getSerieDocumRef());
			pstmt1.setInt(17, documento.getNroDocumRef());
			pstmt1.setString(18, documento.getCodBco());
			pstmt1.setString(19, documento.getCodCtaBco());
			pstmt1.setString(20, documento.getMovimiento());
			pstmt1.setInt(21, documento.getSigno());
			pstmt1.setTimestamp(22, documento.getFecDoc());
			pstmt1.setTimestamp(23, documento.getFecValor());
			
			pstmt1.executeUpdate ();
			pstmt1.close ();
					
		} 
    	catch (SQLException e) 
    	{
			throw new InsertandoSaldoCuentaException();
		} 
		
	}

	public void eliminarSaldoCuenta(DocumSaldo documento, Connection con)
			throws EliminandoSaldoCuetaException, ConexionException {
		ConsultasDD consultas = new ConsultasDD();
		String eliminar = consultas.eliminarSaldoCuenta();
		PreparedStatement pstmt1;
		
		try {
			
			pstmt1 =  con.prepareStatement(eliminar);
			pstmt1.setLong(1, documento.getNroTrans());
			
			pstmt1.executeUpdate ();
			pstmt1.close ();
	
		} 
		
		catch (SQLException e) {
			
			throw new EliminandoSaldoCuetaException();
		}
	}
	
	public void modificarSaldoCuenta(DocumSaldo saldo, Connection con)
			throws ModificandoSaldoCuentaException, ConexionException, EliminandoSaldoCuetaException, InsertandoSaldoCuentaException, ExisteSaldoCuentaException, NoExisteSaldoCuentaException {
		
		try {
			
			/*Verificamos si existe el documento en la tabla de saldos
			 * si existe eliminamos e insertamos con la nueva info*/
			if(this.memberSaldoCta(saldo, con))
			{
				this.eliminarSaldoCuenta(saldo, con);
				this.insertarSaldoCuenta(saldo, con);
			}
			else /*Si no existe,tiramos exception*/
			{
				throw new NoExisteSaldoCuentaException();
			} 
		} 
		
		catch (SQLException e) {
			
			throw new ModificandoSaldoCuentaException();
		}
	}
	
	///////////////////VISTAS////////////////////////////
	
	@Override
	public ArrayList<SaDocumsVO> getSaldosDocum(String codEmp, Connection con) throws Exception {
		// TODO Auto-generated method stub
		ArrayList<SaDocumsVO> lst = new ArrayList<SaDocumsVO>();
		
		try
		{
			Consultas consultas = new Consultas ();
			String query = consultas.getSaDocum();
			
			PreparedStatement pstmt1 = con.prepareStatement(query);
			
			pstmt1.setString(1, codEmp);
			
			String s = pstmt1.toString();
			
			ResultSet rs = pstmt1.executeQuery();
			
			SaDocumsVO doc;
			
			while(rs.next ()) {

				doc = new SaDocumsVO();
							
				//doc.setCod_rubro(rs.getString(1));
				
				doc.setCod_docum(rs.getString(1));
				doc.setSerie_docum(rs.getString(2));
				doc.setNro_docum(rs.getInt(3));
				doc.setCod_emp(rs.getString(4));
				doc.setCod_moneda(rs.getString(5));
				doc.setCod_tit(rs.getInt(6));
				doc.setImp_tot_mn(rs.getDouble(7));
				doc.setImp_tot_mo(rs.getDouble(8));
				doc.setCuenta(rs.getString(9));

				
				
				lst.add(doc);
			}
			
			rs.close ();
			pstmt1.close ();
		}
		catch (SQLException e) {
			
			throw new ObteniendoRubrosException();
		}
			
		return lst;
	}
	
	@Override
	public ArrayList<SaCuentasVO> getSaCuentas(String codEmp, Connection con) throws Exception {
		// TODO Auto-generated method stub
		ArrayList<SaCuentasVO> lst = new ArrayList<SaCuentasVO>();
		
		try
		{
			Consultas consultas = new Consultas();
			String query = consultas.getSaCuentas();
			
			PreparedStatement pstmt1 = con.prepareStatement(query);
			
			pstmt1.setString(1, codEmp);
			
			ResultSet rs = pstmt1.executeQuery();
			
			SaCuentasVO doc;
			
			while(rs.next ()) {

				doc = new SaCuentasVO();
				
	
				
				doc.setCod_docum(rs.getString(1));
				doc.setSerie_docum(rs.getString(2));
				doc.setNro_docum(rs.getInt(3));
				doc.setCod_doc_ref(rs.getString(4));
				doc.setSerie_doc_ref(rs.getString(5));
				doc.setNro_doc_ref(rs.getInt(6));
				doc.setCod_bco(rs.getString(7));
				doc.setCod_ctabco(rs.getString(8));
				doc.setMovimiento(rs.getString(9));
				doc.setCod_emp(rs.getString(10));
				doc.setCod_moneda(rs.getString(11));
				doc.setCod_tit(rs.getInt(12));
				doc.setImp_tot_mn(rs.getDouble(13));
				doc.setImp_tot_mo(rs.getDouble(14));
				doc.setSigno(rs.getInt(15));
				doc.setCuenta(rs.getString(16));
				doc.setCod_cta(rs.getString(17));
				doc.setReferencia(rs.getString(18));
				doc.setNro_trans(rs.getInt(19));
				doc.setFec_valor(rs.getTimestamp(20));
				doc.setFec_doc(rs.getTimestamp(21));
				doc.setConciliado(rs.getInt(22));

				
				lst.add(doc);
			}
			
			rs.close ();
			pstmt1.close ();
		}
		catch (SQLException e) {
			
			throw new ObteniendoRubrosException();
		}
			
		return lst;
	}
}
