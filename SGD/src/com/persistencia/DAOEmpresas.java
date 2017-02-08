package com.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.excepciones.ConexionException;
import com.excepciones.Empresas.ExisteEmpresaException;
import com.excepciones.Empresas.InsertandoEmpresaException;
import com.excepciones.Empresas.ModificandoEmpresaException;
import com.excepciones.Empresas.ObteniendoEmpresasException;
import com.logica.Empresa;

public class DAOEmpresas implements IDAOEmpresas{

	@Override
	public ArrayList<Empresa> getEmpreas(Connection con) throws ObteniendoEmpresasException, ConexionException {
		// TODO Auto-generated method stub
		ArrayList<Empresa> lstEmpresas = new ArrayList<Empresa>();
		
		try
		{
			ConsultasDD consultas = new ConsultasDD ();
			String query = consultas.getEmpresas();
			
			PreparedStatement pstmt1 = con.prepareStatement(query);
			
			
			ResultSet rs = pstmt1.executeQuery();
			
			Empresa empresa;
			
			while(rs.next ()) {

				empresa = new Empresa();
				
				empresa.setCod_emp(rs.getString(1));
				empresa.setNom_emp(rs.getString(2));
				empresa.setFechaMod(rs.getTimestamp(3));
				empresa.setUsuarioMod(rs.getString(4));
				empresa.setOperacion(rs.getString(5));
				empresa.setActivo(rs.getBoolean(6));
				
				lstEmpresas.add(empresa);
			}
			
			
			
			rs.close ();
			pstmt1.close ();
		}
		catch (SQLException e) {
			
			throw new ObteniendoEmpresasException();
		}
			
		return lstEmpresas;
	}

	@Override
	/**
	 * Inserta una empresa en la base
	 * Pre condición: El código de empresa no debe existir previamente
	 */
	public void insertarEmpresa(Empresa empresa, Connection con) throws InsertandoEmpresaException, ConexionException {
		// TODO Auto-generated method stub
		ConsultasDD clts = new ConsultasDD();
    	
    	String insert = clts.insertarEmpresa();
    	
    	PreparedStatement pstmt1;
    	    	
    	
    	try {
    		
			pstmt1 =  con.prepareStatement(insert);
			pstmt1.setString(1, empresa.getCod_emp());
			pstmt1.setString(2, empresa.getNom_emp());
			pstmt1.setString(3, empresa.getUsuarioMod());
			pstmt1.setString(4, empresa.getOperacion());
			pstmt1.setBoolean(5, empresa.isActivo());
			
			pstmt1.executeUpdate ();
			pstmt1.close ();
			
			this.insertarAux(con, empresa.getCod_emp());
			
		} 
    	catch (SQLException e) {
			throw new InsertandoEmpresaException();
		} 
		
	}
	
	/**
	 * Inserta una empresa en la base
	 * Pre condición: El código de empresa no debe existir previamente
	 */
	private void insertarAux(Connection con, String codEmp) throws InsertandoEmpresaException, ConexionException {
		
		ConsultasDD clts = new ConsultasDD();
    	
    	String insertMonedaDolares = clts.insMonedaDolares();
    	String insertBcoNoAsignado = clts.insBcoNoAsignado();
    	String insertImpuestoExento = clts.insImuestoExento();
    	String insertTipoRubro = clts.insTipoRubroNoAsignado();
    	String insertRubroNoAsig = clts.insRubroNoAsignado();
    	
    	PreparedStatement pstmMonDolares;
    	PreparedStatement pstmBcoNoAsig;
    	PreparedStatement pstmImuestoExento;
    	PreparedStatement pstmTipoRubroNoAsig;
    	PreparedStatement pstmRubroNoAsig;
    	    	
    	
    	try {
    		
    		/*Insertamos moneda dolares*/
			pstmMonDolares =  con.prepareStatement(insertMonedaDolares);
			
			pstmMonDolares.setString(1, codEmp);
			pstmMonDolares.executeUpdate();
			pstmMonDolares.close();
			
			/*Insertamos Banco no asignado*/
			pstmBcoNoAsig = con.prepareStatement(insertBcoNoAsignado);
			pstmBcoNoAsig.setString(1, codEmp);
			pstmBcoNoAsig.executeUpdate();
			pstmBcoNoAsig.close();
			
			/*Insertamos impuesto exento*/
			pstmImuestoExento = con.prepareStatement(insertImpuestoExento);
			pstmImuestoExento.setString(1, codEmp);
			pstmImuestoExento.executeUpdate();
			pstmImuestoExento.close();
			
			/*Insertamos tipo rubro no asignado*/
			pstmTipoRubroNoAsig = con.prepareStatement(insertTipoRubro);
			pstmTipoRubroNoAsig.setString(1, codEmp);
			pstmTipoRubroNoAsig.executeUpdate();
			pstmTipoRubroNoAsig.close();
			
			/*Insertamos rubro no asignado*/
			pstmRubroNoAsig = con.prepareStatement(insertRubroNoAsig);
			pstmRubroNoAsig.setString(1, codEmp);
			pstmRubroNoAsig.executeUpdate();
			pstmRubroNoAsig.close();
			
			/*Insertamos titular oficina*/ 
			String insTitOficina = clts.insTitularOficina();
			PreparedStatement pstmTitOficina;
			pstmTitOficina = con.prepareStatement(insTitOficina);
			pstmTitOficina.setString(1, codEmp);
			pstmTitOficina.executeUpdate();
			pstmTitOficina.close();
			
			/*Insertamos cuenta factura*/ 
			String insCuentaFactura = clts.insCuentaFacturas();
			PreparedStatement pstmCuentaFactura;
			pstmCuentaFactura = con.prepareStatement(insCuentaFactura);
			pstmCuentaFactura.setString(1, codEmp);
			pstmCuentaFactura.executeUpdate();
			pstmCuentaFactura.close();
			
			/*Insertamos proceso no asignado*/ 
			String insClienteNoAsig = clts.insClienteNoAsig();
			PreparedStatement pstmClienteNoAsig;
			pstmClienteNoAsig = con.prepareStatement(insClienteNoAsig);
			pstmClienteNoAsig.setString(1, codEmp);
			pstmClienteNoAsig.executeUpdate();
			pstmClienteNoAsig.close();
			
			/*Insertamos proceso no asignado*/ 
			String insProcNoAsig = clts.insProcesoNoAsig();
			PreparedStatement pstmProcNoAsig;
			pstmProcNoAsig = con.prepareStatement(insProcNoAsig);
			pstmProcNoAsig.setString(1, codEmp);
			pstmProcNoAsig.executeUpdate();
			pstmProcNoAsig.close();
			
			
			/*Insertamos grupo administrador */ 
			String insGrupoAdm = clts.insGrupoAdm();
			PreparedStatement pstmGrupoAdm;
			pstmGrupoAdm = con.prepareStatement(insGrupoAdm);
			pstmGrupoAdm.setString(1, codEmp);
			pstmGrupoAdm.executeUpdate();
			pstmGrupoAdm.close();
			
			/*Insertamos formularios para el grupo adm */ 
			String insFormsAdm = clts.insGruposxFormAdm();
			PreparedStatement pstmFormAdm;
			pstmFormAdm = con.prepareStatement(insFormsAdm);
			pstmFormAdm.setString(1, codEmp);
			pstmFormAdm.executeUpdate();
			pstmFormAdm.close();
			
			/*Insertamos numerador para procesos */ 
			String insNumProc = clts.insNumProceso();
			PreparedStatement pstmNumProc;
			pstmNumProc = con.prepareStatement(insNumProc);
			pstmNumProc.setString(1, codEmp);
			pstmNumProc.executeUpdate();
			pstmNumProc.close();
			
			/*Insertamos numerador para gastos */ 
			String insNumGto = clts.insNumGasto();
			PreparedStatement pstmNumGto;
			pstmNumGto = con.prepareStatement(insNumGto);
			pstmNumGto.setString(1, codEmp);
			pstmNumGto.executeUpdate();
			pstmNumGto.close();
			
			/*Insertamos numerador titulares */ 
			String insNumTitulares = clts.insNumTitulares();
			PreparedStatement pstmNumTitulares;
			pstmNumTitulares = con.prepareStatement(insNumTitulares);
			pstmNumTitulares.setString(1, codEmp);
			pstmNumTitulares.executeUpdate();
			pstmNumTitulares.close();
			
			/*Insertamos numerador ctaproc */ 
			String insCtaProc = clts.insCtaProc();
			PreparedStatement pstmCtaProc;
			pstmCtaProc = con.prepareStatement(insCtaProc);
			pstmCtaProc.setString(1, codEmp);
			pstmCtaProc.executeUpdate();
			pstmCtaProc.close();
			
			/*Insertamos numerador egreso cobro */ 
			String insEgrCob = clts.insEgrCobro();
			PreparedStatement pstmEgrCob;
			pstmEgrCob = con.prepareStatement(insEgrCob);
			pstmEgrCob.setString(1, codEmp);
			pstmEgrCob.executeUpdate();
			pstmEgrCob.close();
			
			/*Insertamos numerador factura */ 
			String insFact = clts.insFact();
			PreparedStatement pstmFact;
			pstmFact = con.prepareStatement(insFact);
			pstmFact.setString(1, codEmp);
			pstmFact.executeUpdate();
			pstmFact.close();
			
			/*Insertamos numerador ingreso cobro */ 
			String insIngCob = clts.insIngCob();
			PreparedStatement pstmIngCob;
			pstmIngCob = con.prepareStatement(insIngCob);
			pstmIngCob.setString(1, codEmp);
			pstmIngCob.executeUpdate();
			pstmIngCob.close();
			
		} 
    	catch (SQLException e) {
			throw new InsertandoEmpresaException();
		} 
		
	}

	/**
	 * Dado el codigo de empresa, valida si existe
	 */
	@Override
	public boolean memberEmpresa(String cod_emp, Connection con) throws ExisteEmpresaException, ConexionException {
		// TODO Auto-generated method stub
		boolean existe = false;
		
		try{
			
			
			ConsultasDD consultas = new ConsultasDD ();
			String query = consultas.memberEmpresa();
			
			PreparedStatement pstmt1 = con.prepareStatement(query);
			
			pstmt1.setString(1, cod_emp);
			
			ResultSet rs = pstmt1.executeQuery();
			
			if (rs.next ()) 
				existe = true;
						
			rs.close ();
			pstmt1.close ();
			
			return existe;
			
		}catch(SQLException e){
			
			throw new ExisteEmpresaException();
		}
	}

	/**
	 * Actualizamos la empresa dado el codigo,
	 * PRECONDICION: El código de la empresa debe existir
	 */
	@Override
	public void actualizarEmpresa(Empresa empresa, Connection con)
			throws ModificandoEmpresaException, ConexionException {
		// TODO Auto-generated method stub
		ConsultasDD consultas = new ConsultasDD();
		String update = consultas.actualizarEmpresa();
		PreparedStatement pstmt1;
		
		try {
			
			/*Updateamos la info del usuario*/
     		pstmt1 =  con.prepareStatement(update);
			pstmt1.setString(1, empresa.getNom_emp());
			pstmt1.setBoolean(2, empresa.isActivo());
			pstmt1.setString(3, empresa.getUsuarioMod());
			pstmt1.setString(4, empresa.getOperacion());
			pstmt1.setString(5, empresa.getCod_emp());
			
			pstmt1.executeUpdate ();
			
			pstmt1.close ();
	
		} 
		
		catch (Exception e) {
			
			throw new ModificandoEmpresaException();
		}
		
	}

	
}