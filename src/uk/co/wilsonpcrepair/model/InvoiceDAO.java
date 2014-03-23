package uk.co.wilsonpcrepair.model;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class InvoiceDAO {

	Connection con = null;
	PreparedStatement pstmt = null;
	
	private int maxInvoiceNo = 0;
	
	private static final String selectInvoiceSQL = 
			"SELECT invoiceNo, customerNo, invoiceFile, date " +
			"FROM invoices WHERE CustomerNo = ?";
	
	private static final String insertInvoiceSQL = 
			"INSERT INTO invoices " +
			"(invoiceNo, customerNo, invoiceFile, date) " +
			"VALUES (?, ?, ?, ?)";
	
	private static final String updateInvoiceSQL = 
			"UPDATE invoices " +
			"SET customerNo = ?, invoiceFile = ?, date = ? " +
			"WHERE invoiceNo = ?";
	
	private static final String deleteInvoiceSQL = 
			"DELETE FROM invoices " +
			"WHERE invoiceNo = ?";
	
	public InvoiceDAO(Connection con){
		this.con = con;

		updateMaxInvoiceNo();
	}
	
	public void updateMaxInvoiceNo(){
		try{
			
			Statement stmt = con.createStatement();
			
			ResultSet rs = 
					stmt.executeQuery("SELECT MAX(invoiceNo) FROM invoices");
			
			if(rs.next()){
				maxInvoiceNo = rs.getInt(1);
			}
			
			rs.close();
			stmt.close();
		}catch(SQLException se){
			printSQLException(se);
		}
	}
	
	public int getMaxInvoiceNo(){
		updateMaxInvoiceNo();
		return this.maxInvoiceNo + 1;
	}
	
	public Invoice getInvoice(int targetInvoiceNo){
		Invoice invoice = null;
		
		try{
			pstmt = con.prepareStatement(selectInvoiceSQL);
			pstmt.clearParameters();
			pstmt.setInt(1, targetInvoiceNo);
			
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()){
				int invoiceNo = rs.getInt("invoiceNo");
				int customerNo = rs.getInt("customerNo");
				Blob invoiceFile = rs.getBlob("invoiceFile");
				Date date = rs.getDate("date");
				
				
				invoice = new Invoice(invoiceNo, customerNo, invoiceFile, date);
			}
		}catch(SQLException se){
			printSQLException(se);
		}
		
		return invoice;
	}
	
    public void insertInvoice(Invoice invoice) throws SQLException{
    	
    	// Will count the number of rows inserted into DB.
    	int numRows = 0;
    	
    	// uses java.sql.Connection (con) to create a PreparedStatement.
    	PreparedStatement stmt = con.prepareStatement(insertInvoiceSQL);
    	
    	//                                stmt.setXXX(1, dataToBeInserted);
    	//                   set certain data type ^  |          ^-------------<
    	//                                            ^------------------<      |
    	// This number represents which "?" is in the PreparedStatement --^     |
    	// This is a reference to a variable that is to be inserted-------------^
    	
    	stmt.setInt(1, invoice.getInvoiceNo());
    	stmt.setInt(2, invoice.getCustomerNo());
    	stmt.setBlob(3, invoice.getInvoiceFile());
    	stmt.setDate(4, invoice.getDate());
    	
    	
    	numRows += stmt.executeUpdate();
    	
    	
    	System.out.println("\n" + numRows + " row/s inserted into invoices table.\n");
    	
    	// ALWAYS REMEMBER TO CLOSE
    	stmt.close();
    }

    public void updateInvoice(Invoice invoice) throws SQLException{
    	
    	// Will count the number of rows inserted into DB.
    	int numRows = 0;
    	
    	// uses java.sql.Connection (con) to create a PreparedStatement.
    	PreparedStatement stmt = con.prepareStatement(updateInvoiceSQL);
    	
    	//                                stmt.setXXX(1, dataToBeInserted);
    	//                   set certain data type ^  |          ^-------------<
    	//                                            ^------------------<      |
    	// This number represents which "?" is in the PreparedStatement --^     |
    	// This is a reference to a variable that is to be inserted-------------^
    	
    	stmt.setInt(1, invoice.getCustomerNo());
    	stmt.setBlob(2, invoice.getInvoiceFile());
    	stmt.setDate(3, invoice.getDate());
    	stmt.setInt(4, invoice.getInvoiceNo());
    	
    	numRows += stmt.executeUpdate();
    	
    	
    	System.out.println("\n" + numRows + " row/s updated in invoices table.\n");
    	
    	// ALWAYS REMEMBER TO CLOSE
    	stmt.close();
    }
    
    public void deleteInvoice(int invoiceNo) throws SQLException{
    	
    	// Will count the number of rows inserted into DB.
    	int numRows = 0;
    	
    	// uses java.sql.Connection (con) to create a PreparedStatement.
    	PreparedStatement stmt = con.prepareStatement(deleteInvoiceSQL);
    	
    	//                                stmt.setXXX(1, dataToBeInserted);
    	//                   set certain data type ^  |          ^-------------<
    	//                                            ^------------------<      |
    	// This number represents which "?" is in the PreparedStatement --^     |
    	// This is a reference to a variable that is to be inserted-------------^
    	
    	stmt.setInt(1, invoiceNo);
    	
    	numRows += stmt.executeUpdate();
    	
    	
    	System.out.println("\n" + numRows + " row/s deleted from invoices table.\n");
    	
    	// ALWAYS REMEMBER TO CLOSE
    	stmt.close();
    }
    
    static void printSQLException(SQLException se) {
        while(se != null) {

            System.out.print("SQLException: State:   " + se.getSQLState());
            System.out.println("Severity: " + se.getErrorCode());
            System.out.println(se.getMessage());            
            
            se = se.getNextException();
        }
    }
}

