package com.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.excepciones.ConexionException;
import com.excepciones.Gastos.ExisteGastoException;
import com.excepciones.Gastos.IngresandoGastoException;
import com.excepciones.Gastos.ModificandoGastoException;
import com.excepciones.Gastos.ObteniendoGastosException;
import com.logica.ClienteInfo;
import com.logica.Cuenta;
import com.logica.Gasto;
import com.logica.Impuesto;
import com.logica.MonedaInfo;
import com.logica.Proceso;
import com.logica.Rubro;
import com.mysql.jdbc.Statement;

public class DAOGastos implements IDAOGastos{

	@Override
	public ArrayList<Gasto> getGastos(Connection con, String codEmp)
			throws ObteniendoGastosException, ConexionException {
		// TODO Auto-generated method stub
		ArrayList<Gasto> lstGastos = new ArrayList<Gasto>();
		
		try {
			
	    	ConsultasDD clts = new ConsultasDD();
	    	String query = clts.getGastos();
	    	PreparedStatement pstmt1 = con.prepareStatement(query);
	    	
	    	ResultSet rs;
	    	
	    	pstmt1.setString(1, codEmp);
			rs = pstmt1.executeQuery();
			
			Gasto aux;
			while(rs.next ()) {
				
							
				aux = new Gasto();
				
				aux.setCod_gasto(rs.getInt(1));
				aux.setProceso(new Proceso(rs.getInt(2)));
				aux.setFecha(rs.getTimestamp(3));
				aux.setImpMo(rs.getFloat(4));
				aux.setImpMn(rs.getFloat(5));
				aux.setTcMov(rs.getFloat(6));
				aux.setDescripcion(rs.getString(7));
				aux.setFechaMod(rs.getTimestamp(8));
				aux.setUsuarioMod(rs.getString(9));
				aux.setOperacion(rs.getString(10));
				aux.setCliente(new ClienteInfo(rs.getString(11), rs.getString(12)));
				aux.setMoneda(new MonedaInfo (rs.getString(13), rs.getString(14), rs.getString(15)));
				aux.setCuenta(new Cuenta(rs.getString(17), rs.getString(18)));
				Impuesto imp = new Impuesto();
				imp.setCod_imp(rs.getString(23));
				imp.setDescripcion(rs.getString(24));
				imp.setPorcentaje(rs.getFloat(25));
				aux.setCuenta(new Cuenta(rs.getString(17), rs.getString(18)));
				aux.setRubro(new Rubro(rs.getString(19), rs.getString(20), imp));
				
				lstGastos.add(aux);
				
			}
			rs.close ();
			pstmt1.close ();
    	}	
    	
		catch (SQLException e) {
			throw new ObteniendoGastosException();
			
		}
    	
    	return lstGastos;
	}

	@Override
	public boolean memberGasto(int codGasto, String codEmp, Connection con)
			throws ExisteGastoException, ConexionException {
		// TODO Auto-generated method stub
		boolean existe = false;
		
		try{
			
			
			ConsultasDD consultas = new ConsultasDD();
			String query = consultas.memberGasto();
			
			PreparedStatement pstmt1 = con.prepareStatement(query);
			
			pstmt1.setInt(1, codGasto);
			pstmt1.setString(2, codEmp);
			
			ResultSet rs = pstmt1.executeQuery();
			
			if (rs.next ()) 
				existe = true;
						
			rs.close ();
			pstmt1.close ();
			
			return existe;
			
		}catch(SQLException e){
			
			throw new ExisteGastoException();
		}
	}

	@Override
	public void insertarGasto(Gasto gasto, String codEmp, Connection con)
			throws IngresandoGastoException, ConexionException, SQLException {
		// TODO Auto-generated method stub
		ConsultasDD clts = new ConsultasDD();
		
    	String insert = clts.insertarGasto();
    	
    	PreparedStatement pstmt1;
    	
    	try {
    		
			pstmt1 =  con.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
			
			pstmt1.setInt(1, gasto.getCod_gasto());
			pstmt1.setInt(2, gasto.getProceso().getCodigo());
			pstmt1.setString(3, gasto.getCliente().getCodigo());
			pstmt1.setString(4, gasto.getRubro().getCod_rubro());
			pstmt1.setString(5, gasto.getCuenta().getCod_cuenta());
			pstmt1.setString(6, gasto.getMoneda().getCod_moneda());
			pstmt1.setTimestamp(7, gasto.getFecha());
			pstmt1.setFloat(8, gasto.getImpMo());
			pstmt1.setFloat(9, gasto.getImpMn());
			pstmt1.setFloat(10, gasto.getTcMov());
			pstmt1.setString(11, gasto.getDescripcion());
			pstmt1.setString(12, codEmp);
			pstmt1.setString(13, gasto.getUsuarioMod());
			pstmt1.setString(14, gasto.getOperacion());
			
			pstmt1.executeUpdate ();
			pstmt1.close ();
					
		} 
    	catch (SQLException e) 
    	{
			throw new IngresandoGastoException();
		} 
		
	}

	@Override
	public void modificarGasto(Gasto gasto, String codEmp, Connection con) throws ModificandoGastoException {
		// TODO Auto-generated method stub
		ConsultasDD consultas = new ConsultasDD();
		String update = consultas.actualizarGasto();
		PreparedStatement pstmt1;
		
		
		try {
			
			/*Updateamos la info del usuario*/
     		pstmt1 =  con.prepareStatement(update);
     		
     		pstmt1.setInt(1, gasto.getProceso().getCodigo());
     		pstmt1.setString(2, gasto.getCliente().getCodigo());
     		pstmt1.setString(3, gasto.getRubro().getCod_rubro());
     		pstmt1.setString(4, gasto.getCuenta().getCod_cuenta());
     		pstmt1.setString(5, gasto.getMoneda().getCod_moneda());
     		pstmt1.setTimestamp(6, gasto.getFecha());
     		pstmt1.setFloat(7, gasto.getImpMo());
     		pstmt1.setFloat(8, gasto.getImpMn());
     		pstmt1.setFloat(9, gasto.getTcMov());
     		pstmt1.setString(10, gasto.getDescripcion());
     		pstmt1.setString(11, codEmp);
     		pstmt1.setString(12, gasto.getUsuarioMod());
     		pstmt1.setString(13, gasto.getOperacion());
     		pstmt1.setInt(14, gasto.getCod_gasto());
     		pstmt1.setString(15, codEmp);
			
			pstmt1.executeUpdate ();
			
			pstmt1.close ();
	
		} 
		
		catch (SQLException e) {
			
			throw new ModificandoGastoException();
		}
	}
}
