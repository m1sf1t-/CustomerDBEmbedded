package uk.co.wilsonpcrepair.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBManager {
	
	private static Connection con = null;
	
	private static final String driver = 
			"org.apache.derby.jdbc.EmbeddedDriver";
	
	private static final String url = 
			"jdbc:derby:";
	
	private static final String dbName = "CustomerDatabase";
	
	private static final String createCustomersSQL = 
			"CREATE TABLE customers (" +
			"forename VARCHAR(20), " +
			"surname VARCHAR(20), " +
			"address1 VARCHAR(40), " +
			"address2 VARCHAR(40), " +
			"address3 VARCHAR(40), " +
			"postCode VARCHAR(10), " +
			"homePhone VARCHAR(11), " +
			"mobilePhone VARCHAR(11), " +
			"email VARCHAR(40), " +
			"pcUsername VARCHAR(20), " +
			"pcPassword VARCHAR(100), " +
			"customerNo INT PRIMARY KEY, " +
			"invoiceNo INT, " +
			"comments VARCHAR(2000))";
	
	private static final String createInvoicesSQL = 
			"CREATE TABLE invoices (" +
			"invoiceNo INT PRIMARY KEY, " +
			"customerNo INT NOT NULL," +
			"invoiceFile BLOB(2 M), " +
			"date DATE)";
	
	private InvoiceDAO idao = null;
	private CustomerDAO cdao = null;
	
	public DBManager(){
		if(!dbExists()){
			try{
				Class.forName(driver);
				con = DriverManager.getConnection(url + dbName + ";create=true");
				
				System.out.println("No database exists. Creating database...");
				int rows = processStatement(createCustomersSQL);
				System.out.println(rows + " rows inserted into customers.");
				
				rows = processStatement(createInvoicesSQL);
				System.out.println(rows + " rows inserted into customers.");
				System.out.println("Database created sucessfully!");

			}catch(ClassNotFoundException ce){
				System.out.println("Class not found! Is derby in the CLASSPATH?");
			}catch(SQLException se){
				printSQLException(se);
			}
		}
		idao = new InvoiceDAO(con);
		cdao = new CustomerDAO(con);
	}
	
	public void close(){
		try{
			con = DriverManager.getConnection(url + ";shutdown=true");
		}catch(SQLException se){
			// Do nothing, system has now shut down.
		}
	}
	
	public InvoiceDAO getInvoiceDAO(){
		return idao;
	}
	
	public CustomerDAO getCustomerDAO(){
		return cdao;
	}
	
	private boolean dbExists(){
		Boolean exists = false;
		
		try{
			Class.forName(driver);
			con = DriverManager.getConnection(url + dbName);
			exists = true;
		}catch(Exception e){
			// 
		}
		
		return exists;
	}
	
    static int processStatement(String sql) throws SQLException{
    	
    	System.out.println("SQL statement: \n'" + sql + "' executing...");
    	
    	Statement stmt = con.createStatement();
    	int count = stmt.executeUpdate(sql);
    	
    	stmt.close();
    	
    	System.out.println("Successful.");
    	return(count);
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

