package com.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.excepciones.ConexionException;
import com.excepciones.Procesos.ExisteProcesoException;
import com.excepciones.Procesos.IngresandoProcesoException;
import com.excepciones.Procesos.ModificandoProcesoException;
import com.excepciones.Procesos.ObteniendoProcesosException;
import com.logica.ClienteInfo;
import com.logica.DocumentoAduanero;
import com.logica.MonedaInfo;
import com.logica.Proceso;
import com.mysql.jdbc.Statement;
import com.valueObject.MonedaInfoVO;

public class DAOProcesos implements IDAOProcesos{

	/**
	 * Nos retorna una lista con todos los procesos del sistema
	 */
	public ArrayList<Proceso> getProcesosTodos(Connection con, String codEmp) throws ObteniendoProcesosException, ConexionException {
		
		ArrayList<Proceso> lstProcesos = new ArrayList<Proceso>();
	
		try {
			
	    	ConsultasDD clts = new ConsultasDD();
	    	String query = clts.getProcesos();
	    	PreparedStatement pstmt1 = con.prepareStatement(query);
	    	
	    	ResultSet rs;
	    	
	    	pstmt1.setString(1, codEmp);
			rs = pstmt1.executeQuery();
			
			Proceso aux;
			while(rs.next ()) {
				
							
				aux = new Proceso();
				
				aux.setClienteInfo(new ClienteInfo(rs.getString("cod_cliente"), rs.getString("nom_tit")));
				aux.setMonedaInfo(new MonedaInfo(rs.getString("cod_moneda")
									, rs.getString("nomMoneda")
									, rs.getString("simbolo")));
				aux.setDocumento(new DocumentoAduanero(rs.getString("m_documentos_aduaneros.cod_documento"), 
									rs.getString("m_documentos_aduaneros.descripcion")));
				
				aux.setCodigo(rs.getInt("cod_proceso"));
				aux.setFecha(rs.getDate("fec_doc"));
				aux.setNroMega(rs.getInt("nro_mega"));
				
				//aux.setCodDocum(rs.getString("cod_docum"));
				aux.setNroDocum(rs.getString("nro_docum"));
				aux.setFecDocum(rs.getDate("fec_docum"));
				aux.setCarpeta(rs.getString("carpeta"));
				aux.setImpMo(rs.getDouble("imp_mo"));
				aux.setImpMn(rs.getDouble("imp_mn"));
				aux.setImpTr(rs.getDouble("imp_tr"));
				aux.setTcMov(rs.getFloat("tc_mov"));
				aux.setKilos(rs.getDouble("kilos"));
				aux.setMarca(rs.getString("marca"));
				aux.setMedio(rs.getString("medio"));
				aux.setDescripcion(rs.getString("descripcion"));
				aux.setObservaciones(rs.getString("observaciones"));
				//aux.setActivo(rs.getBoolean("activo"));
				
				
				aux.setUsuarioMod(rs.getString("usuario_mod"));
				aux.setOperacion(rs.getString("operacion"));
				aux.setFechaMod(rs.getTimestamp("fecha_mod"));
				
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
	
//	/**
//	 * Nos retorna una lista con todos los procesos activos del sistema
//	 */
//	public ArrayList<Proceso> getClientesActivos(Connection con, String codEmp) throws ObteniendoProcesosException, ConexionException {
//		
//		ArrayList<Proceso> lstProcesos = new ArrayList<Proceso>();
//		
//		try {
//			
//	    	Consultas clts = new Consultas();
//	    	String query = clts.getProcesosActivos();
//	    	PreparedStatement pstmt1 = con.prepareStatement(query);
//	    	
//	    	ResultSet rs;
//	    	
//	    	pstmt1.setString(1, codEmp);
//			rs = pstmt1.executeQuery();
//			
//			Proceso aux;
//			while(rs.next ()) {
//				
//							
//				aux = new Proceso();
//				
//				aux.setClienteInfo(new ClienteInfo(rs.getString("cod_cliente"), rs.getString("nom_tit")));
//				aux.setMonedaInfo(new MonedaInfo(rs.getString("cod_moneda")
//									, rs.getString("nomMoneda")
//									, rs.getString("simbolo")));
//				
//				aux.setDocumento(new DocumentoAduanero(rs.getString("m_documentos_aduaneros.cod_documento"), 
//						rs.getString("m_documentos_aduaneros.descripcion")));
//				
//				aux.setCodigo(rs.getInt("cod_proceso"));
//				aux.setFecha(rs.getDate("fec_doc"));
//				aux.setNroMega(rs.getInt("nro_mega"));
//				aux.setNroDocum(rs.getString("nro_docum"));
//				aux.setFecDocum(rs.getDate("fec_docum"));
//				aux.setCarpeta(rs.getString("carpeta"));
//				aux.setImpMo(rs.getDouble("imp_mo"));
//				aux.setImpMn(rs.getDouble("imp_mn"));
//				aux.setImpTr(rs.getDouble("imp_tr"));
//				aux.setTcMov(rs.getFloat("tc_mov"));
//				aux.setKilos(rs.getDouble("kilos"));
//				aux.setMarca(rs.getString("marca"));
//				aux.setMedio(rs.getString("medio"));
//				aux.setDescripcion(rs.getString("descripcion"));
//				aux.setObservaciones(rs.getString("observaciones"));
//				
//				
//				aux.setUsuarioMod(rs.getString("usuario_mod"));
//				aux.setOperacion(rs.getString("operacion"));
//				aux.setFechaMod(rs.getTimestamp("fecha_mod"));
//				
//				lstProcesos.add(aux);
//				
//			}
//			rs.close ();
//			pstmt1.close ();
//    	}	
//    	
//		catch (SQLException e) {
//			throw new ObteniendoProcesosException();
//			
//		}
//    	
//    	return lstProcesos;
//	}

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
	 * 
	 */
	public int insertarProceso(Proceso proceso, String codEmp, Connection con) throws IngresandoProcesoException, ConexionException {

		Consultas clts = new Consultas();
    	
    	String insert = clts.insertarProceso();
    	
    	PreparedStatement pstmt1;
    	int codigo =0;	
    	
    	try {
    		
			pstmt1 =  con.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
			
			pstmt1.setString(1, proceso.getClienteInfo().getCodigo());
			pstmt1.setDate(2,  proceso.getFecha());
			pstmt1.setInt(3, proceso.getNroMega());
			
			pstmt1.setString(5, proceso.getNroDocum());
			pstmt1.setDate(6, proceso.getFecDocum());
			pstmt1.setString(7, proceso.getCarpeta());
			pstmt1.setString(8, proceso.getMonedaInfo().getCod_moneda());
			pstmt1.setDouble(9, proceso.getImpMo());
			pstmt1.setFloat(10, proceso.getTcMov());
			pstmt1.setDouble(11, proceso.getImpMn());
			pstmt1.setDouble(12, proceso.getImpTr());
			pstmt1.setDouble(13, proceso.getKilos());
			pstmt1.setString(14, proceso.getMarca());
			pstmt1.setString(15, proceso.getMedio());
			pstmt1.setString(16, proceso.getDescripcion());
			pstmt1.setString(17, proceso.getObservaciones());
			
			pstmt1.setString(18, proceso.getUsuarioMod());
			pstmt1.setString(19, proceso.getOperacion());
			//pstmt1.setTimestamp(20, proceso.getFechaMod());
			
			
			pstmt1.executeUpdate ();
			
			/*Obtenemos el codigo del cliente insertado*/
			ResultSet rs = pstmt1.getGeneratedKeys();
			if (rs.next()){
			    codigo=rs.getInt(1);
			}
			
			pstmt1.close ();
					
		} 
    	catch (SQLException e) 
    	{
			throw new IngresandoProcesoException();
		} 
		
    	return codigo;
	}
	
	/**
	 * MOdificamos cliente
	 *  PRECONDICION: EXISTE CODIGO DE CLIENTE EN EL SISTEMA
	 * PRECONDICION: NO EXISTE OTRO CLIENTE EN EL SISTEMA CON EL MISMO DOCUMENTO
	 * 
	 */
	public void modificarProceso(Proceso proceso, String codEmp, Connection con) throws ModificandoProcesoException{
		
		Consultas consultas = new Consultas();
		String update = consultas.actualizarProceso();
		PreparedStatement pstmt1;
		
		
		try {
			
			/*Updateamos la info del usuario*/
     		pstmt1 =  con.prepareStatement(update);
     		
     		pstmt1.setString(1, proceso.getClienteInfo().getCodigo());
			pstmt1.setDate(2,  proceso.getFecha());
			pstmt1.setInt(3, proceso.getNroMega());
			
			//pstmt1.setString(4, proceso.getCodDocum());
			pstmt1.setString(5, proceso.getNroDocum());
			pstmt1.setDate(6, proceso.getFecDocum());
			pstmt1.setString(7, proceso.getCarpeta());
			pstmt1.setString(8, proceso.getMonedaInfo().getCod_moneda());
			pstmt1.setDouble(9, proceso.getImpMo());
			pstmt1.setFloat(10, proceso.getTcMov());
			pstmt1.setDouble(11, proceso.getImpMn());
			pstmt1.setDouble(12, proceso.getImpTr());
			pstmt1.setDouble(13, proceso.getKilos());
			pstmt1.setString(14, proceso.getMarca());
			pstmt1.setString(15, proceso.getMedio());
			pstmt1.setString(16, proceso.getDescripcion());
			pstmt1.setString(17, proceso.getObservaciones());
			
			pstmt1.setString(18, proceso.getUsuarioMod());
			pstmt1.setString(19, proceso.getOperacion());
			pstmt1.setInt(20, proceso.getCodigo()); /*WHERE*/
			
			pstmt1.executeUpdate ();
			
			pstmt1.close ();
	
		} 
		
		catch (SQLException e) {
			
			throw new ModificandoProcesoException();
		}
	}

	
}
