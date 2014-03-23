package uk.co.wilsonpcrepair.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

import uk.co.wilsonpcrepair.model.Customer;
import uk.co.wilsonpcrepair.model.CustomerDAO;
import uk.co.wilsonpcrepair.model.DBManager;
import uk.co.wilsonpcrepair.model.InvoiceDAO;

public class TestDB {

	private static DBManager dbm = null;
	private static InvoiceDAO idao = null;
	
	static BufferedReader cin = new BufferedReader(new InputStreamReader(System.in));
	
	public TestDB(){
		TestDB.dbm = new DBManager();
		dbm.getCustomerDAO();
		TestDB.idao = dbm.getInvoiceDAO();
		CustomerDAO.getMaxCustomerNo();
		idao.getMaxInvoiceNo();
	}

	public void insertCustomer(){
		
		String forename = null;
		String surname = null;
		String address1 = null;
		String address2 = null;
		String address3 = null;
		String postCode = null;
		String homePhone = null;
		String mobilePhone = null;
		String email = null;
		String pcUsername = null;
		String pcPassword = null;
		int invoiceNo = 0;
		String comments = null;
		
		int selection = 0;
		
		Customer customer = null;
		
		while(true){
			
			try{
				System.out.println("Insert Customer");
				System.out.println("--------------------------------");
				System.out.println("Enter forename: ");
				forename = cin.readLine();					
				System.out.println("Enter surname: ");
				surname = cin.readLine();
				System.out.println("Enter address line 1: ");
				address1 = cin.readLine();
				System.out.println("Enter address line 2: ");
				address2 = cin.readLine();
				System.out.println("Enter address line 3: ");
				address3 = cin.readLine();
				System.out.println("Enter postcode: ");
				postCode = cin.readLine();
				System.out.println("Enter home telephone (must be 11 characters long): ");
				homePhone = cin.readLine();
				System.out.println("Enter mobile telephone (must be 11 characters long): ");
				mobilePhone = cin.readLine();
				System.out.println("Enter email: ");
				email = cin.readLine();
				System.out.println("Enter PC username: ");
				pcUsername = cin.readLine();
				System.out.println("Enter PC password: ");
				pcPassword = cin.readLine();
				System.out.println("Enter comments: ");
				comments = cin.readLine();
				
				invoiceNo = idao.getMaxInvoiceNo();
			}catch(Exception e){
				;// Do nothing
			}
			
			customer = new Customer(forename, surname, address1, address2, address3, postCode, 
					                homePhone, mobilePhone, 
					                email, pcUsername, pcPassword, 0, invoiceNo, comments);
			
			try {
				CustomerDAO.insertCustomer(customer);
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			System.out.println("Insert another customer: 1 -- Menu: 2");
			try {
				selection = Integer.parseInt(cin.readLine());
				
				if(selection == 1){
					; //repeats the while loop
				}else if(selection == 2){
					break;
				}else{
					System.out.println("Please enter a valid selection.");
				}
			} catch (NumberFormatException e) {
				System.out.println("Invalid selection.");
				break;
			} catch (IOException e) {
				System.out.println("Invalid selection.");
				break;
			}
		}
	}
	
	public void showCustomer(){
		
		int customerNo = 0;
		Customer c = null;
		
		while(true){
			try{
				System.out.println("Enter Customer number (0 for menu): ");
				customerNo = Integer.parseInt(cin.readLine());
			}catch(Exception e){
				;// Do nothing
			}
			
			if(customerNo == 0){
				break;
			}else if((customerNo < 0) || (customerNo > CustomerDAO.getMaxCustomerNo() - 1)){
				System.out.println("Invalid customer number, please try again.\n");
			}else{
				c = CustomerDAO.getCustomer(customerNo);
				if(c != null){
				    c.printCustomer();
				}else{
					System.out.println("Invalid customer number, please try again\n");
				}
			}
		}
	}
	
	public static void main(String[] args) {
		
		TestDB tdb = new TestDB();
		
/*		Customer c =  new Customer("Kyle", "Wilson", "3 Brookdale Street", "Failsworth", "Manchester", 
				                   "M34 0HF", "01616571143", "07850687996", 
				                   "kylechat416@hotmail.com", "kyle", "Not telling ;P", 1, 1, "Comments here");
		
		Invoice i = new Invoice(2, 1, null, null);
		
		try {
			idao.insertInvoice(i);
			idao.updateInvoice(i);
			idao.deleteInvoice(2);
		} catch (SQLException e1) {
			printSQLException(e1);
		}
*/		
		System.out.println("\n      ------------------------------------------------------------------");
		System.out.println("      | Customer Database for Wilson Home Computer Support and Repair. |");
		System.out.println("      |                                                                |");
		System.out.println("      | Currently under development. Consider this as version -1.0.    |");
		System.out.println("      ------------------------------------------------------------------\n");
		
		while(true){
		    System.out.println("Insert Customer: 1 -- Show Customer: 2 -- Exit: 0");
		
	    	int selection;
			try {

				String selectionString = cin.readLine();
				
				try{
				    selection = Integer.parseInt(selectionString);
				    
					if(selection == 1){
						tdb.insertCustomer();
					}else if(selection == 2){
						tdb.showCustomer();
					}else if(selection == 0){
						break;
					}
				}catch(NumberFormatException nfe){
					System.out.println("Invalid selection.");
				}
				
			} catch (IOException e) {
				System.out.println("Invalid selection.");
			}
			
	    }
		
		dbm.close();
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

