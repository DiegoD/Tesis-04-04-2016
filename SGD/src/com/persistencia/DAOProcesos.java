package com.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.excepciones.ConexionException;
import com.excepciones.Procesos.EliminandoProcesoException;
import com.excepciones.Procesos.ExisteProcesoException;
import com.excepciones.Procesos.IngresandoProcesoException;
import com.excepciones.Procesos.ModificandoProcesoException;
import com.excepciones.Procesos.ObteniendoProcesosException;
import com.logica.ClienteInfo;
import com.logica.DocumentoAduanero;
import com.logica.MonedaInfo;
import com.logica.Proceso;
import com.mysql.jdbc.Statement;

public class DAOProcesos implements IDAOProcesos{

	/**
	 * Nos retorna una lista con todos los procesos del sistema
	 */
	public ArrayList<Proceso> getProcesosTodos(Connection con, String codEmp, Timestamp inicio, Timestamp fin) throws ObteniendoProcesosException, ConexionException {
		
		ArrayList<Proceso> lstProcesos = new ArrayList<Proceso>();
	
		try {
			
	    	ConsultasDD clts = new ConsultasDD();
	    	String query = clts.getProcesos();
	    	PreparedStatement pstmt1 = con.prepareStatement(query);
	    	
	    	ResultSet rs;
	    	
	    	pstmt1.setString(1, codEmp);
	    	pstmt1.setTimestamp(2, inicio);
	    	pstmt1.setTimestamp(3, fin);
	    	
			rs = pstmt1.executeQuery();
			
			Proceso aux;
			while(rs.next ()) {
				
							
				aux = new Proceso();
				
				aux.setCodigo(rs.getInt(1));
				aux.setFecha(rs.getTimestamp(2));
				aux.setNroDocum(rs.getInt(3));
				aux.setFecDocum(rs.getTimestamp(4));
				aux.setNroMega(rs.getString(5));
				aux.setCarpeta(rs.getString(6));
				aux.setImpMo(rs.getDouble(7));
				aux.setImpMn(rs.getDouble(8));
				aux.setImpTr(rs.getDouble(9));
				aux.setTcMov(rs.getDouble(10));
				aux.setKilos(rs.getDouble(11));
				aux.setFecCruce(rs.getTimestamp(12));
				aux.setMarca(rs.getString(13));
				aux.setMedio(rs.getString(14));
				aux.setDescripcion(rs.getString(15));
				aux.setObservaciones(rs.getString(16));
				aux.setFechaMod(rs.getTimestamp(17));
				aux.setUsuarioMod(rs.getString(18));
				aux.setOperacion(rs.getString(19));
				
				aux.setDocumento(new DocumentoAduanero((rs.getString(20)), 
						rs.getString(21)));
				
				aux.setClienteInfo(new ClienteInfo(rs.getString(22), rs.getString(23)));
				
				aux.setMonedaInfo(new MonedaInfo(rs.getString(24)
						, rs.getString(25)
						, rs.getString(26)));
				
				lstProcesos.add(aux);
				
			}
			rs.close ();
			pstmt1.close ();
    	}	
    	
		catch (SQLException e) {
			throw new ObteniendoProcesosException();
			
		}
    	
    	return lstProcesos;
	}
	
	/**
	 * Nos retorna una lista con todos los procesos del sistema
	 */
	public ArrayList<Proceso> getProcesosTodosSinFecha(Connection con, String codEmp) throws ObteniendoProcesosException, ConexionException {
		
		ArrayList<Proceso> lstProcesos = new ArrayList<Proceso>();
	
		try {
			
	    	ConsultasDD clts = new ConsultasDD();
	    	String query = clts.getProcesosSinFecha();
	    	PreparedStatement pstmt1 = con.prepareStatement(query);
	    	
	    	ResultSet rs;
	    	
	    	pstmt1.setString(1, codEmp);
	    	
			rs = pstmt1.executeQuery();
			
			Proceso aux;
			while(rs.next ()) {
				
							
				aux = new Proceso();
				
				aux.setCodigo(rs.getInt(1));
				aux.setFecha(rs.getTimestamp(2));
				aux.setNroDocum(rs.getInt(3));
				aux.setFecDocum(rs.getTimestamp(4));
				aux.setNroMega(rs.getString(5));
				aux.setCarpeta(rs.getString(6));
				aux.setImpMo(rs.getDouble(7));
				aux.setImpMn(rs.getDouble(8));
				aux.setImpTr(rs.getDouble(9));
				aux.setTcMov(rs.getDouble(10));
				aux.setKilos(rs.getDouble(11));
				aux.setFecCruce(rs.getTimestamp(12));
				aux.setMarca(rs.getString(13));
				aux.setMedio(rs.getString(14));
				aux.setDescripcion(rs.getString(15));
				aux.setObservaciones(rs.getString(16));
				aux.setFechaMod(rs.getTimestamp(17));
				aux.setUsuarioMod(rs.getString(18));
				aux.setOperacion(rs.getString(19));
				
				aux.setDocumento(new DocumentoAduanero((rs.getString(20)), 
						rs.getString(21)));
				
				aux.setClienteInfo(new ClienteInfo(rs.getString(22), rs.getString(23)));
				
				aux.setMonedaInfo(new MonedaInfo(rs.getString(24)
						, rs.getString(25)
						, rs.getString(26)));
				
				lstProcesos.add(aux);
				
			}
			rs.close ();
			pstmt1.close ();
    	}	
    	
		catch (SQLException e) {
			throw new ObteniendoProcesosException();
			
		}
    	
    	return lstProcesos;
	}
	
	/**
	 * Nos retorna una lista con todos los procesos del sistema
	 */
	public ArrayList<Proceso> getProcesosxTit(Connection con, String codEmp, String codTit) throws ObteniendoProcesosException, ConexionException {
		
		ArrayList<Proceso> lstProcesos = new ArrayList<Proceso>();
	
		try {
			
	    	ConsultasDD clts = new ConsultasDD();
	    	String query = clts.getProcesosxTit();
	    	PreparedStatement pstmt1 = con.prepareStatement(query);
	    	
	    	ResultSet rs;
	    	
	    	pstmt1.setString(1, codEmp);
	    	pstmt1.setString(2, codTit);
	    	
	    
	    	
			rs = pstmt1.executeQuery();
			
			Proceso aux;
			while(rs.next ()) {
				
							
				aux = new Proceso();
				
				aux.setCodigo(rs.getInt(1));
				aux.setFecha(rs.getTimestamp(2));
				aux.setNroDocum(rs.getInt(3));
				aux.setFecDocum(rs.getTimestamp(4));
				aux.setNroMega(rs.getString(5));
				aux.setCarpeta(rs.getString(6));
				aux.setImpMo(rs.getDouble(7));
				aux.setImpMn(rs.getDouble(8));
				aux.setImpTr(rs.getDouble(9));
				aux.setTcMov(rs.getDouble(10));
				aux.setKilos(rs.getDouble(11));
				aux.setFecCruce(rs.getTimestamp(12));
				aux.setMarca(rs.getString(13));
				aux.setMedio(rs.getString(14));
				aux.setDescripcion(rs.getString(15));
				aux.setObservaciones(rs.getString(16));
				aux.setFechaMod(rs.getTimestamp(17));
				aux.setUsuarioMod(rs.getString(18));
				aux.setOperacion(rs.getString(19));
				
				aux.setDocumento(new DocumentoAduanero((rs.getString(20)), 
						rs.getString(21)));
				
				aux.setClienteInfo(new ClienteInfo(rs.getString(22), rs.getString(23)));
				
				aux.setMonedaInfo(new MonedaInfo(rs.getString(24)
						, rs.getString(25)
						, rs.getString(26)));
				
				lstProcesos.add(aux);
				
			}
			rs.close ();
			pstmt1.close ();
    	}	
    	
		catch (SQLException e) {
			throw new ObteniendoProcesosException();
			
		}
    	
    	return lstProcesos;
	}
	

	public ArrayList<Proceso> getProcesosCliente(Connection con, String codEmp, String cod_cliente) throws ObteniendoProcesosException, ConexionException {
		
		ArrayList<Proceso> lstProcesos = new ArrayList<Proceso>();
	
		try {
			
	    	ConsultasDD clts = new ConsultasDD();
	    	String query = clts.getProcesosCliente();
	    	PreparedStatement pstmt1 = con.prepareStatement(query);
	    	
	    	ResultSet rs;
	    	
	    	pstmt1.setString(1, codEmp);
	    	pstmt1.setString(2, cod_cliente);
			rs = pstmt1.executeQuery();
			
			Proceso aux;
			while(rs.next ()) {
				
							
				aux = new Proceso();
				
				aux.setCodigo(rs.getInt(1));
				aux.setFecha(rs.getTimestamp(2));
				aux.setNroDocum(rs.getInt(3));
				aux.setFecDocum(rs.getTimestamp(4));
				aux.setNroMega(rs.getString(5));
				aux.setCarpeta(rs.getString(6));
				aux.setImpMo(rs.getDouble(7));
				aux.setImpMn(rs.getDouble(8));
				aux.setImpTr(rs.getDouble(9));
				aux.setTcMov(rs.getDouble(10));
				aux.setKilos(rs.getDouble(11));
				aux.setFecCruce(rs.getTimestamp(12));
				aux.setMarca(rs.getString(13));
				aux.setMedio(rs.getString(14));
				aux.setDescripcion(rs.getString(15));
				aux.setObservaciones(rs.getString(16));
				aux.setFechaMod(rs.getTimestamp(17));
				aux.setUsuarioMod(rs.getString(18));
				aux.setOperacion(rs.getString(19));
				
				aux.setDocumento(new DocumentoAduanero((rs.getString(20)), 
						rs.getString(21)));
				
				aux.setClienteInfo(new ClienteInfo(rs.getString(22), rs.getString(23)));
				
				aux.setMonedaInfo(new MonedaInfo(rs.getString(24)
						, rs.getString(25)
						, rs.getString(26)));
				
				lstProcesos.add(aux);
				
			}
			rs.close ();
			pstmt1.close ();
    	}	
    	
		catch (SQLException e) {
			throw new ObteniendoProcesosException();
			
		}
    	
    	return lstProcesos;
	}
	/**
	 * Dado el codigo del proceso, valida si existe
	 */
	public boolean memberProceso(int codProceso, String codEmp, Connection con) throws ExisteProcesoException, ConexionException{
		
		boolean existe = false;
		
		try{
			
			
			Consultas consultas = new Consultas();
			String query = consultas.memberProceso();
			
			PreparedStatement pstmt1 = con.prepareStatement(query);
			
			pstmt1.setInt(1, codProceso);
			pstmt1.setString(2, codEmp);
			
			ResultSet rs = pstmt1.executeQuery();
			
			if (rs.next ()) 
				existe = true;
						
			rs.close ();
			pstmt1.close ();
			
			return existe;
			
		}catch(SQLException e){
			
			throw new ExisteProcesoException();
		}
	}
	

	/**
	 * Inserta un proceso en la base, nos retrorna un entero que es el codigo del proceso insertado
	 * @throws SQLException 
	 * 
	 */
	public void insertarProceso(Proceso proceso, String codEmp, Connection con) throws IngresandoProcesoException, ConexionException, SQLException {

		ConsultasDD clts = new ConsultasDD();
		
    	String insert = clts.insertarProceso();
    	
    	PreparedStatement pstmt1;
    	
    	try {
    		
			pstmt1 =  con.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
			
			pstmt1.setTimestamp(1, proceso.getFecha());
			pstmt1.setInt(2, proceso.getNroDocum());
			pstmt1.setTimestamp(3, proceso.getFecDocum());
			pstmt1.setInt(4, Integer.parseInt(proceso.getNroMega()));
			pstmt1.setString(5, proceso.getCarpeta());
			pstmt1.setDouble(6, proceso.getImpMo());
			pstmt1.setDouble(7, proceso.getImpMn());
			pstmt1.setDouble(8, proceso.getImpTr());
			pstmt1.setDouble(9, proceso.getTcMov());
			pstmt1.setDouble(10, proceso.getKilos());
			pstmt1.setTimestamp(11, proceso.getFecCruce());
			pstmt1.setString(12, proceso.getMarca());
			pstmt1.setString(13, proceso.getMedio());
			pstmt1.setString(14, proceso.getDescripcion());
			pstmt1.setString(15, proceso.getObservaciones());
			pstmt1.setString(16, proceso.getUsuarioMod());
			pstmt1.setString(17, proceso.getOperacion());
			pstmt1.setString(18, proceso.getDocumento().getCod_docucmento());
			pstmt1.setString(19, proceso.getClienteInfo().getCodigo());
			pstmt1.setString(20, proceso.getMonedaInfo().getCodMoneda());
			pstmt1.setString(21, codEmp);
			pstmt1.setInt(22, proceso.getCodigo());
			
			pstmt1.executeUpdate ();
			
			pstmt1.close ();
					
		} 
    	catch (SQLException e) 
    	{
			throw new IngresandoProcesoException();
		} 
		
	}
	
	/**
	 * MOdificamos cliente
	 *  PRECONDICION: EXISTE CODIGO DE CLIENTE EN EL SISTEMA
	 * PRECONDICION: NO EXISTE OTRO CLIENTE EN EL SISTEMA CON EL MISMO DOCUMENTO
	 * 
	 */
	public void modificarProceso(Proceso proceso, String codEmp, Connection con) throws ModificandoProcesoException{
		
		ConsultasDD consultas = new ConsultasDD();
		String update = consultas.actualizarProceso();
		PreparedStatement pstmt1;
		
		
		try {
			
			/*Updateamos la info del usuario*/
     		pstmt1 =  con.prepareStatement(update);
     		
			pstmt1.setTimestamp(1, proceso.getFecha());
			pstmt1.setInt(2, proceso.getNroDocum());
			pstmt1.setTimestamp(3, proceso.getFecDocum());
			pstmt1.setInt(4, Integer.parseInt(proceso.getNroMega()));
			pstmt1.setString(5, proceso.getCarpeta());
			pstmt1.setDouble(6, proceso.getImpMo());
			pstmt1.setDouble(7, proceso.getImpMn());
			pstmt1.setDouble(8, proceso.getImpTr());
			pstmt1.setDouble(9, proceso.getTcMov());
			pstmt1.setDouble(10, proceso.getKilos());
			pstmt1.setTimestamp(11, proceso.getFecCruce());
			pstmt1.setString(12, proceso.getMarca());
			pstmt1.setString(13, proceso.getMedio());
			pstmt1.setString(14, proceso.getDescripcion());
			pstmt1.setString(15, proceso.getObservaciones());
			pstmt1.setString(16, proceso.getUsuarioMod());
			pstmt1.setString(17, proceso.getOperacion());
			pstmt1.setString(18, proceso.getDocumento().getCod_docucmento());
			pstmt1.setString(19, proceso.getClienteInfo().getCodigo());
			pstmt1.setString(20, proceso.getMonedaInfo().getCodMoneda());
			pstmt1.setInt(21, proceso.getCodigo());
			pstmt1.setString(22, codEmp);
			
			pstmt1.executeUpdate ();
			
			pstmt1.close ();
	
		} 
		
		catch (SQLException e) {
			
			throw new ModificandoProcesoException();
		}
	}
	
	/**
	 * Dado el codigo del proceso, valida si existe
	 */
	public void eliminarProceso(int codProceso, String codEmp, Connection con) throws EliminandoProcesoException, ConexionException{
		
		try{
			
			ConsultasDD consultas = new ConsultasDD();
			String query = consultas.eliminarProceso();
			
			PreparedStatement pstmt1 = con.prepareStatement(query);
			
			pstmt1.setInt(1, codProceso);
			pstmt1.setString(2, codEmp);
			
			pstmt1.executeUpdate();
						
			pstmt1.close ();
			
		}catch(SQLException e){
			
			throw new EliminandoProcesoException();
		}
	}

	public Proceso getProceso(Connection con, String codEmp, Integer codProceso) throws ObteniendoProcesosException, ConexionException {
		
		Proceso aux = new Proceso();
		try {
			
	    	ConsultasDD clts = new ConsultasDD();
	    	String query = clts.getProceso();
	    	PreparedStatement pstmt1 = con.prepareStatement(query);
	    	
	    	ResultSet rs;
	    	
	    	pstmt1.setString(1, codEmp);
	    	pstmt1.setInt(2, codProceso);
			rs = pstmt1.executeQuery();
			
			
			while(rs.next ()) {
				
							
				aux.setCodigo(rs.getInt(1));
				aux.setFecha(rs.getTimestamp(2));
				aux.setNroDocum(rs.getInt(3));
				aux.setFecDocum(rs.getTimestamp(4));
				aux.setNroMega(rs.getString(5));
				aux.setCarpeta(rs.getString(6));
				aux.setImpMo(rs.getDouble(7));
				aux.setImpMn(rs.getDouble(8));
				aux.setImpTr(rs.getDouble(9));
				aux.setTcMov(rs.getDouble(10));
				aux.setKilos(rs.getDouble(11));
				aux.setFecCruce(rs.getTimestamp(12));
				aux.setMarca(rs.getString(13));
				aux.setMedio(rs.getString(14));
				aux.setDescripcion(rs.getString(15));
				aux.setObservaciones(rs.getString(16));
				aux.setFechaMod(rs.getTimestamp(17));
				aux.setUsuarioMod(rs.getString(18));
				aux.setOperacion(rs.getString(19));
				
				aux.setDocumento(new DocumentoAduanero((rs.getString(20)), 
						rs.getString(21)));
				
				aux.setClienteInfo(new ClienteInfo(rs.getString(22), rs.getString(23)));
				
				aux.setMonedaInfo(new MonedaInfo(rs.getString(24)
						, rs.getString(25)
						, rs.getString(26)));
				
			}
			rs.close ();
			pstmt1.close ();
    	}	
    	
		catch (SQLException e) {
			throw new ObteniendoProcesosException();
			
		}
    	
    	return aux;
	}
	
}
