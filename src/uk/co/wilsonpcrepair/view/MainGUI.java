package uk.co.wilsonpcrepair.view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import uk.co.wilsonpcrepair.controller.MainApplication;
import uk.co.wilsonpcrepair.model.Customer;
import uk.co.wilsonpcrepair.model.CustomerDAO;
import uk.co.wilsonpcrepair.model.EmailValidator;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;

public class MainGUI extends JFrame {

	private static JList customersList = null;
	
	private JLabel customerNameLbl = null;
	private JLabel customersPaneAddress1 = null;
	private JLabel customersPaneAddress2 = null;
	private JLabel customersPaneAddress3 = null;
	private JLabel customersPanePostCode = null;
	private JLabel customersPaneHomePhone = null;
	private JLabel customersPaneMobilePhone = null;
	private JLabel customersPaneEmail = null;
	private JLabel customersPanePCUsername = null;
	private JLabel customersPanePCPassword = null;
	private JTextArea customersPanelComments = null;
	private JLabel pcPasswordLbl = null;
	private JLabel pcUsernameLbl = null;
	private JComboBox customersPanelInvoice = null;
	private JSeparator separator = null;
	JScrollPane customersPaneCommentsScroll = null;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField newCustomerForenameText;
	private JTextField newCustomerSurnameText;
	private JTextField newCustomerAddress1Text;
	private JTextField newCustomerAddress2Text;
	private JTextField newCustomerAddress3Text;
	private JTextField newCustomerPostCodeText;
	private JTextField newCustomerPCPasswordText;
	private JTextField newCustomerPCUsernameText;
	private JTextField newCustomerEmailText;
	private JTextField newCustomerMobilePhoneText;
	private JTextField newCustomerHomePhoneText;
	private JTextArea newCustomerCommentsText;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainGUI frame = new MainGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static void populateCustomersList(ArrayList<String> customerArray){
		
		DefaultListModel model = new DefaultListModel();
		
		for(String name : customerArray){
			model.addElement(name.toString());
			
			// Make the thread sleep for 100 milliseconds, in order to stop NullPointerException
			// being thrown.
			// Not sure why this is necessary, maybe something to do with multithreading??
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(model != null)
			customersList.setModel(model);
	}
	
	public void populateCustomerInfo(Customer c){
		customerNameLbl.setText(c.getForename() + " " + c.getSurname());
		customersPaneAddress1.setText(c.getAddress1());
		customersPaneAddress2.setText(c.getAddress2());
		customersPaneAddress3.setText(c.getAddress3());
		customersPanePostCode.setText(c.getPostCode());
		customersPaneHomePhone.setText(c.getHomePhone());
		customersPaneMobilePhone.setText(c.getMobilePhone());
		customersPaneEmail.setText(c.getEmail());
		customersPanePCUsername.setText(c.getPcUsername());
		customersPanePCPassword.setText(c.getPcPassword());
		customersPanelComments.setText(c.getComments());
		
		// These GUI components are set to visible once the user
		// selects a customer from the list.
		pcPasswordLbl.setVisible(true);
		pcUsernameLbl.setVisible(true);
		customersPanelComments.setVisible(true);
		separator.setVisible(true);
		customersPaneCommentsScroll.setVisible(true);
	}
	
	int validateInsertForm(){
		
		// Check home and mobile less than or equal to 11 characters.
		
		// Home Phone
		int phoneLength = newCustomerHomePhoneText.getText().length();
		
		if(phoneLength > 11){
			JOptionPane.showMessageDialog(this, "The home phone field must be 11 characters or less.");
			return 1;
		}
		
		// Use regex to make sure that email address is valid.
		if(!newCustomerEmailText.getText().equals("")){
	    	boolean matches = EmailValidator.validate(newCustomerEmailText.getText());
		
    		if(matches == false){
		    	JOptionPane.showMessageDialog(this, "Please enter a valid email address.");
	    		return 1;
    		}
		}
		
		// Mobile Phone
		phoneLength = newCustomerMobilePhoneText.getText().length();
		
		if (phoneLength > 11){
			JOptionPane.showMessageDialog(this, "The mobile phone field must be 11 characters or less.");
		    return 1;
		}
		
		// Make sure at least the first name is not null.
		if(newCustomerForenameText.getText().equals("")){
			JOptionPane.showMessageDialog(this, "Please enter at least a forename.");
			return 1;
		}
		
		// Make sure that either home, mobile or email has been entered
		if((newCustomerHomePhoneText.getText().equals("")) && 
				(newCustomerMobilePhoneText.getText().equals("")) && 
				(newCustomerHomePhoneText.getText().equals(""))){
			
			JOptionPane.showMessageDialog(this, "Please enter either an email address, home telephone number, " +
					                            "or mobile telephone number.");
			return 1;
		}
		
		return 0;
	}
	
	void clearInsertInputFields(){
		
		// Clears all input fields in "New Customer..." tab.
		
		newCustomerForenameText.setText("");
    	newCustomerSurnameText.setText("");
		newCustomerAddress1Text.setText("");
		newCustomerAddress2Text.setText("");
		newCustomerAddress3Text.setText("");
    	newCustomerPostCodeText.setText("");
		newCustomerHomePhoneText.setText("");
		newCustomerMobilePhoneText.setText("");
		newCustomerEmailText.setText("");
	    newCustomerPCUsernameText.setText("");
    	newCustomerPCPasswordText.setText("");
		newCustomerCommentsText.setText("");
	}
	
	void insertCustomer(){
		// Confirm save action
		int confirm = JOptionPane.showConfirmDialog(null, "Save Customer?");
		
		// if valid equals 0, the form is valid. 1 means the form is not valid.
		int valid = validateInsertForm();
		
		if((confirm == JOptionPane.YES_OPTION) && (valid == 0)){
		    
			// Create customer from all the textbox's getText() methods.
		    String forename = newCustomerForenameText.getText();
	    	String surname = newCustomerSurnameText.getText();
    		String address1 = newCustomerAddress1Text.getText();
			String address2 = newCustomerAddress2Text.getText();
			String address3 = newCustomerAddress3Text.getText();
	    	String postCode = newCustomerPostCodeText.getText();
    		String homePhone = newCustomerHomePhoneText.getText();
			String mobilePhone = newCustomerMobilePhoneText.getText();
			String email = newCustomerEmailText.getText();
		    String pcUsername = newCustomerPCUsernameText.getText();
	    	String pcPassword = newCustomerPCPasswordText.getText();
    		int customerNo = 0;
			int invoiceNo = 1;
			String comments = newCustomerCommentsText.getText();
	 	
    		Customer c = new Customer(forename, surname, address1, address2,
					                  address3, postCode, homePhone, mobilePhone, 
	    			                  email, pcUsername, pcPassword, 
    				                  customerNo, invoiceNo, comments);
		
			//Insert customer into database.
			try {
	    		CustomerDAO.insertCustomer(c);
				JOptionPane.showMessageDialog(this, "Customer created sucessfully!");
				clearInsertInputFields();
    		} catch (SQLException e) {
				// TODO Auto-generated catch block
	    		e.printStackTrace();
    		}
			
			MainApplication.populateCustomersList();
		}
	
	}
	
	/**
	 * Create the frame.
	 */
	public MainGUI() {

		setTitle("Kyle Wilson's Customer Administration System");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 800);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 1, 0, 0));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBorder(new LineBorder(UIManager.getColor("Table[Enabled+Selected].textBackground"), 1, true));
		
		JDesktopPane customersPane = new JDesktopPane();
		customersPane.setBackground(Color.BLACK);
		tabbedPane.addTab("Customers", null, customersPane, null);
		
		JScrollPane customersScrollPane = new JScrollPane();
		customersScrollPane.setBounds(6, 6, 215, 627);
		customersPane.add(customersScrollPane);
		
		customersList = new JList();
		customersList.setFont(new Font("DejaVu Sans", Font.BOLD, 12));
		customersList.setForeground(Color.WHITE);
		customersList.setBackground(new Color(112, 128, 144));
		customersList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				
				// Get the string value of the list selection.
				String listString = customersList.getSelectedValue().toString();
				System.out.println(listString);
				
				// Get the first 3 characters in the string
				String customerNoString = listString.substring(0, 2);
				
				// Turn the string into an int
				customerNoString = customerNoString.trim(); //remove any whitespace

				int customerNo = Integer.parseInt(customerNoString);

				// Create new customer from customerNo.
				Customer customer = CustomerDAO.getCustomer(customerNo);
				
				// Write a method to change the text for each label,
				// using the new customer object as an input parameter.
				populateCustomerInfo(customer);
				
				
			}
		});
		
		customersScrollPane.setViewportView(customersList);
		
		customerNameLbl = new JLabel("Welcome to Kyle Wilson's Customer Administration System");
		customerNameLbl.setForeground(Color.WHITE);
		customerNameLbl.setHorizontalAlignment(SwingConstants.CENTER);
		customerNameLbl.setHorizontalTextPosition(SwingConstants.CENTER);
		customerNameLbl.setFont(new Font("DejaVu Sans", Font.BOLD, 19));
		customerNameLbl.setBounds(233, 6, 727, 48);
		customersPane.add(customerNameLbl);
		
		customersPaneAddress1 = new JLabel("           Currently under development.");
		customersPaneAddress1.setForeground(Color.WHITE);
		customersPaneAddress1.setFont(new Font("DejaVu Sans", Font.BOLD, 16));
		customersPaneAddress1.setBounds(233, 93, 338, 15);
		customersPane.add(customersPaneAddress1);
		
		customersPaneAddress2 = new JLabel("");
		customersPaneAddress2.setForeground(Color.WHITE);
		customersPaneAddress2.setFont(new Font("DejaVu Sans", Font.BOLD, 16));
		customersPaneAddress2.setBounds(233, 120, 338, 15);
		customersPane.add(customersPaneAddress2);
		
		customersPaneAddress3 = new JLabel("Please select a customer to begin.");
		customersPaneAddress3.setForeground(Color.WHITE);
		customersPaneAddress3.setFont(new Font("DejaVu Sans", Font.BOLD, 16));
		customersPaneAddress3.setBounds(233, 147, 338, 15);
		customersPane.add(customersPaneAddress3);
		
		customersPanePostCode = new JLabel("");
		customersPanePostCode.setForeground(Color.WHITE);
		customersPanePostCode.setFont(new Font("DejaVu Sans", Font.BOLD, 16));
		customersPanePostCode.setBounds(233, 174, 338, 15);
		customersPane.add(customersPanePostCode);
		
		customersPaneHomePhone = new JLabel("Consider this as version -1.0");
		customersPaneHomePhone.setForeground(Color.WHITE);
		customersPaneHomePhone.setHorizontalAlignment(SwingConstants.LEFT);
		customersPaneHomePhone.setFont(new Font("DejaVu Sans", Font.BOLD, 16));
		customersPaneHomePhone.setBounds(583, 94, 377, 15);
		customersPane.add(customersPaneHomePhone);
		
		customersPaneMobilePhone = new JLabel("");
		customersPaneMobilePhone.setForeground(Color.WHITE);
		customersPaneMobilePhone.setFont(new Font("DejaVu Sans", Font.BOLD, 16));
		customersPaneMobilePhone.setBounds(583, 121, 377, 15);
		customersPane.add(customersPaneMobilePhone);
		
		customersPaneEmail = new JLabel("");
		customersPaneEmail.setForeground(Color.WHITE);
		customersPaneEmail.setFont(new Font("DejaVu Sans", Font.BOLD, 16));
		customersPaneEmail.setBounds(583, 175, 377, 15);
		customersPane.add(customersPaneEmail);
		
		separator = new JSeparator();
		separator.setVisible(false);
		separator.setBackground(SystemColor.windowText);
		separator.setBounds(233, 201, 727, 2);
		customersPane.add(separator);
		
		customersPanePCUsername = new JLabel("");
		customersPanePCUsername.setForeground(Color.WHITE);
		customersPanePCUsername.setFont(new Font("DejaVu Sans", Font.BOLD, 16));
		customersPanePCUsername.setBounds(369, 431, 202, 15);
		customersPane.add(customersPanePCUsername);
		
		customersPanePCPassword = new JLabel("");
		customersPanePCPassword.setForeground(Color.WHITE);
		customersPanePCPassword.setFont(new Font("DejaVu Sans", Font.BOLD, 16));
		customersPanePCPassword.setBounds(712, 431, 248, 15);
		customersPane.add(customersPanePCPassword);
		
		JLabel customersPaneInvoiceLbl = new JLabel("");
		customersPaneInvoiceLbl.setForeground(Color.WHITE);
		customersPaneInvoiceLbl.setFont(new Font("DejaVu Sans", Font.BOLD, 16));
		customersPaneInvoiceLbl.setBounds(583, 243, 86, 15);
		customersPane.add(customersPaneInvoiceLbl);
		
		customersPanelInvoice = new JComboBox();
		customersPanelInvoice.setVisible(false);
		customersPanelInvoice.setModel(new DefaultComboBoxModel(new String[] {"01/01/2014", "01/02/2014", "01/03/2014", "01/04/2014"}));
		customersPanelInvoice.setBounds(681, 238, 132, 25);
		customersPane.add(customersPanelInvoice);
		
		JLabel lblNewLabel = new JLabel(" ");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("DejaVu Sans", Font.BOLD, 16));
		lblNewLabel.setBounds(233, 269, 60, 15);
		customersPane.add(lblNewLabel);
		
		JLabel customersPanelCommentLbl = new JLabel("");
		customersPanelCommentLbl.setForeground(Color.WHITE);
		customersPanelCommentLbl.setFont(new Font("DejaVu Sans", Font.BOLD, 16));
		customersPanelCommentLbl.setBounds(233, 238, 158, 15);
		customersPane.add(customersPanelCommentLbl);
		
		pcUsernameLbl = new JLabel("PC username:");
		pcUsernameLbl.setForeground(Color.WHITE);
		pcUsernameLbl.setVisible(false);
		
		customersPaneCommentsScroll = new JScrollPane();
		customersPaneCommentsScroll.setVisible(false);
		customersPaneCommentsScroll.setBounds(243, 264, 328, 155);
		customersPane.add(customersPaneCommentsScroll);
		
		customersPanelComments = new JTextArea();
		customersPaneCommentsScroll.setViewportView(customersPanelComments);
		customersPanelComments.setAutoscrolls(true);
		customersPanelComments.setLineWrap(true);
		customersPanelComments.setForeground(Color.WHITE);
		customersPanelComments.setBackground(new Color(119, 136, 153));
		customersPanelComments.setVisible(false);
		customersPanelComments.setFont(new Font("DejaVu Sans", Font.BOLD, 12));
		customersPanelComments.setWrapStyleWord(true);
		customersPanelComments.setEditable(false);
		customersPanelComments.setText("");
		pcUsernameLbl.setFont(new Font("DejaVu Sans", Font.BOLD, 16));
		pcUsernameLbl.setBounds(233, 431, 338, 15);
		customersPane.add(pcUsernameLbl);
		
		pcPasswordLbl = new JLabel("PC password:");
		pcPasswordLbl.setForeground(Color.WHITE);
		pcPasswordLbl.setVisible(false);
		pcPasswordLbl.setFont(new Font("DejaVu Sans", Font.BOLD, 16));
		pcPasswordLbl.setBounds(583, 432, 377, 15);
		customersPane.add(pcPasswordLbl);
		contentPane.add(tabbedPane);
		
		JDesktopPane newPane = new JDesktopPane();
		tabbedPane.addTab("New Customer...", null, newPane, null);
		
		JLabel lblNewCustomer = new JLabel("New Customer");
		lblNewCustomer.setForeground(Color.WHITE);
		lblNewCustomer.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewCustomer.setFont(new Font("DejaVu Sans", Font.BOLD, 27));
		lblNewCustomer.setBounds(6, 6, 954, 55);
		newPane.add(lblNewCustomer);
		
		JLabel newCustomerForename = new JLabel("Forename:");
		newCustomerForename.setForeground(Color.WHITE);
		newCustomerForename.setHorizontalAlignment(SwingConstants.RIGHT);
		newCustomerForename.setFont(new Font("DejaVu Sans", Font.BOLD, 16));
		newCustomerForename.setBounds(6, 100, 167, 15);
		newPane.add(newCustomerForename);
		
		JLabel newCustomerSurname = new JLabel("Surname:");
		newCustomerSurname.setForeground(Color.WHITE);
		newCustomerSurname.setHorizontalAlignment(SwingConstants.RIGHT);
		newCustomerSurname.setFont(new Font("DejaVu Sans", Font.BOLD, 16));
		newCustomerSurname.setBounds(6, 127, 167, 15);
		newPane.add(newCustomerSurname);
		
		JLabel newCustomerAddress1 = new JLabel("Address line 1:");
		newCustomerAddress1.setForeground(Color.WHITE);
		newCustomerAddress1.setHorizontalAlignment(SwingConstants.RIGHT);
		newCustomerAddress1.setFont(new Font("DejaVu Sans", Font.BOLD, 16));
		newCustomerAddress1.setBounds(6, 186, 167, 15);
		newPane.add(newCustomerAddress1);
		
		JLabel newCustomerAddress2 = new JLabel("Address line 2:");
		newCustomerAddress2.setForeground(Color.WHITE);
		newCustomerAddress2.setHorizontalAlignment(SwingConstants.RIGHT);
		newCustomerAddress2.setFont(new Font("DejaVu Sans", Font.BOLD, 16));
		newCustomerAddress2.setBounds(6, 213, 167, 15);
		newPane.add(newCustomerAddress2);
		
		JLabel newCustomerAddress3 = new JLabel("Address line 3:");
		newCustomerAddress3.setForeground(Color.WHITE);
		newCustomerAddress3.setHorizontalAlignment(SwingConstants.RIGHT);
		newCustomerAddress3.setFont(new Font("DejaVu Sans", Font.BOLD, 16));
		newCustomerAddress3.setBounds(6, 240, 167, 15);
		newPane.add(newCustomerAddress3);
		
		JLabel newCustomerPostCode = new JLabel("Post Code:");
		newCustomerPostCode.setForeground(Color.WHITE);
		newCustomerPostCode.setHorizontalAlignment(SwingConstants.RIGHT);
		newCustomerPostCode.setFont(new Font("DejaVu Sans", Font.BOLD, 16));
		newCustomerPostCode.setBounds(6, 267, 167, 15);
		newPane.add(newCustomerPostCode);
		
		JLabel newCustomerHomePhone = new JLabel("Home Telephone:");
		newCustomerHomePhone.setForeground(Color.WHITE);
		newCustomerHomePhone.setHorizontalAlignment(SwingConstants.RIGHT);
		newCustomerHomePhone.setFont(new Font("DejaVu Sans", Font.BOLD, 16));
		newCustomerHomePhone.setBounds(446, 100, 197, 15);
		newPane.add(newCustomerHomePhone);
		
		JLabel newCustomerMobilePhone = new JLabel("Mobile Telephone:");
		newCustomerMobilePhone.setForeground(Color.WHITE);
		newCustomerMobilePhone.setHorizontalAlignment(SwingConstants.RIGHT);
		newCustomerMobilePhone.setFont(new Font("DejaVu Sans", Font.BOLD, 16));
		newCustomerMobilePhone.setBounds(446, 127, 197, 15);
		newPane.add(newCustomerMobilePhone);
		
		JLabel newCustomerEmail = new JLabel("Email:");
		newCustomerEmail.setForeground(Color.WHITE);
		newCustomerEmail.setHorizontalAlignment(SwingConstants.RIGHT);
		newCustomerEmail.setFont(new Font("DejaVu Sans", Font.BOLD, 16));
		newCustomerEmail.setBounds(446, 186, 197, 15);
		newPane.add(newCustomerEmail);
		
		JLabel newCustomerPCUsername = new JLabel("PC Username:");
		newCustomerPCUsername.setForeground(Color.WHITE);
		newCustomerPCUsername.setHorizontalAlignment(SwingConstants.RIGHT);
		newCustomerPCUsername.setFont(new Font("DejaVu Sans", Font.BOLD, 16));
		newCustomerPCUsername.setBounds(446, 240, 197, 15);
		newPane.add(newCustomerPCUsername);
		
		JLabel newCustomerPCPassword = new JLabel("PC Password:");
		newCustomerPCPassword.setForeground(Color.WHITE);
		newCustomerPCPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		newCustomerPCPassword.setFont(new Font("DejaVu Sans", Font.BOLD, 16));
		newCustomerPCPassword.setBounds(446, 267, 197, 15);
		newPane.add(newCustomerPCPassword);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(6, 313, 954, 2);
		newPane.add(separator_1);
		
		JLabel newCustomerComments = new JLabel("Comments:");
		newCustomerComments.setForeground(Color.WHITE);
		newCustomerComments.setFont(new Font("DejaVu Sans", Font.BOLD, 16));
		newCustomerComments.setBounds(16, 327, 140, 15);
		newPane.add(newCustomerComments);
		
		newCustomerCommentsText = new JTextArea();
		newCustomerCommentsText.setBounds(26, 352, 485, 266);
		newPane.add(newCustomerCommentsText);
		
		newCustomerForenameText = new JTextField();
		newCustomerForenameText.setBounds(185, 95, 265, 27);
		newPane.add(newCustomerForenameText);
		newCustomerForenameText.setColumns(10);
		
		newCustomerSurnameText = new JTextField();
		newCustomerSurnameText.setBounds(185, 122, 265, 27);
		newPane.add(newCustomerSurnameText);
		newCustomerSurnameText.setColumns(10);
		
		newCustomerAddress1Text = new JTextField();
		newCustomerAddress1Text.setFocusCycleRoot(true);
		newCustomerAddress1Text.setBounds(185, 181, 265, 27);
		newPane.add(newCustomerAddress1Text);
		newCustomerAddress1Text.setColumns(10);
		
		newCustomerAddress2Text = new JTextField();
		newCustomerAddress2Text.setBounds(185, 208, 265, 27);
		newPane.add(newCustomerAddress2Text);
		newCustomerAddress2Text.setColumns(10);
		
		newCustomerAddress3Text = new JTextField();
		newCustomerAddress3Text.setBounds(185, 235, 265, 27);
		newPane.add(newCustomerAddress3Text);
		newCustomerAddress3Text.setColumns(10);
		
		newCustomerPostCodeText = new JTextField();
		newCustomerPostCodeText.setBounds(185, 262, 265, 27);
		newPane.add(newCustomerPostCodeText);
		newCustomerPostCodeText.setColumns(10);
		
		newCustomerPCPasswordText = new JTextField();
		newCustomerPCPasswordText.setBounds(655, 262, 272, 27);
		newPane.add(newCustomerPCPasswordText);
		newCustomerPCPasswordText.setColumns(10);
		
		newCustomerPCUsernameText = new JTextField();
		newCustomerPCUsernameText.setBounds(655, 235, 272, 27);
		newPane.add(newCustomerPCUsernameText);
		newCustomerPCUsernameText.setColumns(10);
		
		newCustomerEmailText = new JTextField();
		newCustomerEmailText.setBounds(655, 181, 272, 27);
		newPane.add(newCustomerEmailText);
		newCustomerEmailText.setColumns(10);
		
		newCustomerMobilePhoneText = new JTextField();
		newCustomerMobilePhoneText.setBounds(655, 122, 272, 27);
		newPane.add(newCustomerMobilePhoneText);
		newCustomerMobilePhoneText.setColumns(10);
		
		newCustomerHomePhoneText = new JTextField();
		newCustomerHomePhoneText.setBounds(655, 95, 272, 27);
		newPane.add(newCustomerHomePhoneText);
		newCustomerHomePhoneText.setColumns(10);
		
		JButton newCustomerSaveButton = new JButton("Save Customer");
		newCustomerSaveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
            insertCustomer();
			}
		});
		newCustomerSaveButton.setFont(new Font("DejaVu Sans", Font.BOLD, 16));
		newCustomerSaveButton.setBounds(655, 562, 168, 56);
		newPane.add(newCustomerSaveButton);
		
		JButton newCustomerClearButton = new JButton("Clear fields");
		newCustomerClearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				clearInsertInputFields();
			}
		});
		newCustomerClearButton.setFont(new Font("DejaVu Sans", Font.BOLD, 16));
		newCustomerClearButton.setBounds(655, 495, 168, 55);
		newPane.add(newCustomerClearButton);
		newPane.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{newCustomerForenameText, newCustomerSurnameText, newCustomerAddress1Text, newCustomerAddress2Text, newCustomerAddress3Text, newCustomerPostCodeText, newCustomerHomePhoneText, newCustomerMobilePhoneText, newCustomerEmailText, newCustomerPCUsernameText, newCustomerPCPasswordText, newCustomerCommentsText, newCustomerSaveButton, newCustomerClearButton, newCustomerHomePhone, lblNewCustomer, newCustomerForename, newCustomerSurname, newCustomerAddress1, newCustomerAddress2, newCustomerAddress3, newCustomerPostCode, newCustomerMobilePhone, newCustomerEmail, newCustomerPCUsername, newCustomerPCPassword, separator_1, newCustomerComments}));
		
		JDesktopPane updatePane = new JDesktopPane();
		tabbedPane.addTab("Edit Customer...", null, updatePane, null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 6, 238, 627);
		updatePane.add(scrollPane);
		
		JList list = new JList();
		scrollPane.setViewportView(list);
		
		JDesktopPane deletePane = new JDesktopPane();
		tabbedPane.addTab("Delete Customer...", null, deletePane, null);
	}
}
