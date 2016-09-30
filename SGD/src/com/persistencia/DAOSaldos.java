package com.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.excepciones.ConexionException;
import com.excepciones.Gastos.IngresandoGastoException;
import com.excepciones.Gastos.ModificandoGastoException;
import com.excepciones.Rubros.ExisteRubroException;
import com.excepciones.Saldos.EliminandoSaldoException;
import com.excepciones.Saldos.ExisteSaldoException;
import com.excepciones.Saldos.IngresandoSaldoException;
import com.excepciones.Saldos.ModificandoSaldoException;
import com.excepciones.Saldos.ObteniendoSaldosException;
import com.logica.Docum.DatosDocum;
import com.logica.Docum.DocumDetalle;
import com.mysql.jdbc.Statement;

public class DAOSaldos implements IDAOSaldos{

	@Override
	public ArrayList<DocumDetalle> getSaldos(Connection con, String codEmp)
			throws ObteniendoSaldosException, ConexionException {
		// TODO Auto-generated method stub
		return null;
	}


	

	
	/**
	 * Dado el codigo de rubro, valida si existe
	 * @throws ExisteSaldoException 
	 */
	public boolean memberSaldo(DatosDocum docum, Connection con) throws ExisteSaldoException{
		
		boolean existe = false;
		
		try{
			
			ConsultasDD consultas = new ConsultasDD ();
			String query = consultas.memberSaldo();
			
			
			PreparedStatement pstmt1 = con.prepareStatement(query);
			
			pstmt1.setString(1, docum.getCodDocum());
			pstmt1.setString(2, docum.getSerieDocum());
			pstmt1.setInt(3, docum.getNroDocum());
			pstmt1.setString(4, docum.getCodEmp());
			pstmt1.setString(5, docum.getTitInfo().getCodigo());
			
			ResultSet rs = pstmt1.executeQuery();
			
			if (rs.next ()) 
				existe = true;
						
			rs.close ();
			pstmt1.close ();
			
			return existe;
			
		}catch(SQLException e){
			
			throw new ExisteSaldoException();
		}
	}
	

	@Override
	public void insertarSaldo(DocumDetalle documento, String codEmp, Connection con)
			throws IngresandoSaldoException, ConexionException, SQLException {
		ConsultasDD clts = new ConsultasDD();
		
    	String insert = clts.insertarSaldo();
    	
    	PreparedStatement pstmt1;
    	
    	try {
    		
			pstmt1 =  con.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
			
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
			
			pstmt1.executeUpdate ();
			pstmt1.close ();
					
		} 
    	catch (SQLException e) 
    	{
			throw new IngresandoSaldoException();
		} 
		
	}

	@Override
	public void eliminarSaldo(DocumDetalle documento, String codEmp, Connection con)
			throws EliminandoSaldoException, ConexionException {
		// TODO Auto-generated method stub
		ConsultasDD consultas = new ConsultasDD();
		String eliminar = consultas.eliminarSaldo();
		PreparedStatement pstmt1;
		
		try {
			
			pstmt1 =  con.prepareStatement(eliminar);
			pstmt1.setString(1, documento.getCodDocum());
			pstmt1.setString(2, documento.getSerieDocum());
			pstmt1.setInt(3, documento.getNroDocum());
			pstmt1.setString(4, documento.getCodEmp());
			pstmt1.setString(5, documento.getTitInfo().getCodigo());
			
			pstmt1.executeUpdate ();
			pstmt1.close ();
	
		} 
		
		catch (SQLException e) {
			
			throw new EliminandoSaldoException();
		}
	}
	
	@Override
	public void modificarSaldo(DocumDetalle documento, String codEmp, Connection con)
			throws ModificandoSaldoException, ConexionException, EliminandoSaldoException, IngresandoSaldoException, ExisteSaldoException {
		// TODO Auto-generated method stub
		
		try {
			
			/*Verificamos si existe el documento en la tabla de saldos
			 * si existe eliminamos e insertamos con la nueva info*/
			if(this.memberSaldo((DatosDocum)documento, con))
			{
				this.eliminarSaldo(documento, codEmp, con);
				this.insertarSaldo(documento, codEmp, con);
			}
			else /*Si no existe, es nuevo y solamente insertamos*/
			{
				this.insertarSaldo(documento, codEmp, con);
			}
		} 
		
		catch (SQLException e) {
			
			throw new ModificandoSaldoException();
		}
	}
	

}
