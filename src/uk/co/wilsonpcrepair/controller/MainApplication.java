package uk.co.wilsonpcrepair.controller;

import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.UIManager;

import uk.co.wilsonpcrepair.model.Customer;
import uk.co.wilsonpcrepair.model.CustomerDAO;
import uk.co.wilsonpcrepair.model.DBManager;
import uk.co.wilsonpcrepair.view.MainGUI;

public class MainApplication {

	private DBManager dbm = null;
	MainGUI mainGUI = null;
	
	public MainApplication(){

		this.dbm = new DBManager();
		dbm.getCustomerDAO();
		dbm.getInvoiceDAO();
	}
	
	// GUI invoked here, within the controller and not MainGUI's main method.
	void showGUI(){
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainGUI mainGUI = new MainGUI();
					mainGUI.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static void populateCustomersList(){
		String fullName = null;
		ArrayList<String> customerArray = new ArrayList<String>();
		Customer c = null;
		
		
		/* Goes through each customer in the DB and adds the surname and forename
		 * to an array 
		 */
		for(int customer = 1; customer <= CustomerDAO.getMaxCustomerNo(); customer++){
			c = CustomerDAO.getCustomer(customer);
		    
			if(c != null)
				fullName = c.getCustomerNo() + "  - " + 
			              c.getSurname() + " - " + c.getForename();
		    
			if(fullName != null){
		    	customerArray.add(fullName);
		    	fullName = null;
		    }
		}
		
		// Trim the array to avoid null values
		customerArray.trimToSize();
		
		MainGUI.populateCustomersList(customerArray);
	}
	
	public static void main(String[] args) {

		// Used to initialise DBManager, Customer DAO, and Invoice DAO (see constructor).
		MainApplication mainApplication = new MainApplication();
		
		// GUI invoked within this method.
		mainApplication.showGUI();
		
		// Populate customer list in mainGUI
		MainApplication.populateCustomersList();
	
	}

}
