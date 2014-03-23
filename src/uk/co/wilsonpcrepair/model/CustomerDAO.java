package uk.co.wilsonpcrepair.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CustomerDAO {

	static Connection con = null;
	static PreparedStatement pstmt = null;
	
	private static int maxCustomerNo = 0;
	
	private static final String selectCustomerSQL = 
			"SELECT forename, surname, address1, address2, address3, postCode, homePhone, " +
			       "mobilePhone, email, pcUsername, " +
			       "pcPassword, customerNo, invoiceNo, comments " +
			"FROM customers WHERE CustomerNo = ?";
	
	private static final String insertCustomerSQL = 
			"INSERT INTO customers " +
			"(forename, surname, address1, address2, address3, postCode, homePhone, mobilePhone, " +
			"email, pcUsername, " +
			"pcPassword, customerNo, invoiceNo, comments) " +
			"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
	private static final String updateCustomerSQL = 
			"UPDATE customers " +
			"SET forename = ?, surname = ?, address1 = ?, address2 = ?, address3 = ?, " +
			"postCode = ?  homePhone = ?, " +
			"mobilePhone = ?, email = ?, pcUsername = ?, " +
			"pcPassword = ?, invoiceNo = ?, comments = ? " +
			"WHERE customerNo = ?";
	
	private static final String deleteCustomerSQL = 
			"DELETE FROM customers " +
			"WHERE customerNo = ?";
	
	public CustomerDAO(Connection con){
		CustomerDAO.con = con;

		// This method invoked during constructor in order to have a
		// constantly up to date CustomerNo.
		updateMaxCustomerNo();
	}
	
	public static void updateMaxCustomerNo(){
		try{
			
			Statement stmt = con.createStatement();
			
			ResultSet rs = 
					stmt.executeQuery("SELECT MAX(customerNo) FROM customers");
			
			if(rs.next()){
				maxCustomerNo = rs.getInt(1);
			}
			
			rs.close();
			stmt.close();
		}catch(SQLException se){
			printSQLException(se);
		}
	}
	
	public static int getMaxCustomerNo(){
		updateMaxCustomerNo();
		return maxCustomerNo + 1;
	}
	
	public static Customer getCustomer(int targetCustomerNo){
		Customer customer = null;
		
		try{
			pstmt = con.prepareStatement(selectCustomerSQL);
			pstmt.clearParameters();
			pstmt.setInt(1, targetCustomerNo);
			
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()){
				String forename = rs.getString("forename");
				String surname = rs.getString("surname");
				String address1 = rs.getString("address1");
				String address2 = rs.getString("address2");
				String address3 = rs.getString("address3");
				String postCode = rs.getString("postCode");
				String homePhone = rs.getString("homePhone");
				String mobilePhone = rs.getString("mobilePhone");
				String email = rs.getString("email");
				String pcUsername = rs.getString("pcUsername");
				String pcPassword = rs.getString("pcPassword");
				int customerNo = rs.getInt("customerNo");
				int invoiceNo = rs.getInt("invoiceNo");
				String comments = rs.getString("comments");
				
				customer = new Customer(forename, surname, address1, address2, 
						                address3, postCode, homePhone, 
						                mobilePhone, email, pcUsername, 
						                pcPassword, customerNo, invoiceNo, comments);
			}
		}catch(SQLException se){
			printSQLException(se);
		}
		
		return customer;
	}
	
    public static void insertCustomer(Customer customer) throws SQLException{
    	
    	// Will count the number of rows inserted into DB.
    	int numRows = 0;
    	
    	// uses java.sql.Connection (con) to create a PreparedStatement.
    	PreparedStatement stmt = con.prepareStatement(insertCustomerSQL);
    	
    	//                                stmt.setXXX(1, dataToBeInserted);
    	//                   set certain data type ^  |          ^-------------<
    	//                                            ^------------------<      |
    	// This number represents which "?" is in the PreparedStatement --^     |
    	// This is a reference to a variable that is to be inserted-------------^
    	
    	stmt.setString(1, customer.getForename());
    	stmt.setString(2, customer.getSurname());
    	stmt.setString(3, customer.getAddress1());
    	stmt.setString(4, customer.getAddress2());
    	stmt.setString(5, customer.getAddress3());
    	stmt.setString(6, customer.getPostCode());
    	stmt.setString(7, customer.getHomePhone());
    	stmt.setString(8, customer.getMobilePhone());
    	stmt.setString(9, customer.getEmail());
    	stmt.setString(10, customer.getPcUsername());
    	stmt.setString(11, customer.getPcPassword());
    	stmt.setInt(12, getMaxCustomerNo());
    	stmt.setInt(13, customer.getInvoiceNo());
    	stmt.setString(14, customer.getComments());
    	
    	numRows += stmt.executeUpdate();
    	
    	
    	System.out.println("\n" + numRows + " row/s inserted into customers table.\n");
    	
    	// ALWAYS REMEMBER TO CLOSE
    	stmt.close();
    }

    public void deleteCustomer(int customerNo) throws SQLException{
    	
    	// Will count the number of rows inserted into DB.
    	int numRows = 0;
    	
    	// uses java.sql.Connection (con) to create a PreparedStatement.
    	PreparedStatement stmt = con.prepareStatement(deleteCustomerSQL);
    	
    	//                                stmt.setXXX(1, dataToBeInserted);
    	//                   set certain data type ^  |          ^-------------<
    	//                                            ^------------------<      |
    	// This number represents which "?" is in the PreparedStatement --^     |
    	// This is a reference to a variable that is to be inserted-------------^
    	
    	stmt.setInt(1, customerNo);
    	
    	numRows += stmt.executeUpdate();
    	
    	
    	System.out.println("\n" + numRows + " row/s deleted from customers table.\n");
    	
    	// ALWAYS REMEMBER TO CLOSE
    	stmt.close();
    }
    
    public void updateCustomer(Customer customer) throws SQLException{
    	
    	// Will count the number of rows inserted into DB.
    	int numRows = 0;
    	
    	// uses java.sql.Connection (con) to create a PreparedStatement.
    	PreparedStatement stmt = con.prepareStatement(updateCustomerSQL);
    	
    	//                                stmt.setXXX(1, dataToBeInserted);
    	//                   set certain data type ^  |          ^-------------<
    	//                                            ^------------------<      |
    	// This number represents which "?" is in the PreparedStatement --^     |
    	// This is a reference to a variable that is to be inserted-------------^
    	
    	stmt.setString(1, customer.getForename());
    	stmt.setString(2, customer.getSurname());
    	stmt.setString(3, customer.getAddress1());
    	stmt.setString(4, customer.getAddress2());
    	stmt.setString(5, customer.getAddress3());
    	stmt.setString(6, customer.getPostCode());
    	stmt.setString(7, customer.getHomePhone());
    	stmt.setString(8, customer.getMobilePhone());
    	stmt.setString(9, customer.getEmail());
    	stmt.setString(10, customer.getPcUsername());
    	stmt.setString(11, customer.getPcPassword());
    	stmt.setInt(12, customer.getInvoiceNo());
    	stmt.setString(13, customer.getComments());
    	stmt.setInt(14, customer.getCustomerNo());
    	
    	numRows += stmt.executeUpdate();
    	
    	
    	System.out.println("\n" + numRows + " row/s updated in customers table.\n");
    	
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

