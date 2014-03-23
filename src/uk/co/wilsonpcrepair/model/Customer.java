package uk.co.wilsonpcrepair.model;

public class Customer {

	private String forename = null;
	private String surname = null;
	private String address1 = null;
	private String address2 = null;
	private String address3 = null;
	private String postCode = null;
	private String homePhone = null;
	private String mobilePhone = null;
	private String email = null;
	private String pcUsername = null;
	private String pcPassword = null;
	private int customerNo = -1;
	private int invoiceNo = -1;
	private String comments = null;
	
	public Customer(String forename, String surname, String address1, String address2, String address3, 
			String postCode, String homePhone, String mobilePhone, 
			String email, String pcUsername, String pcPassword, 
			int customerNo, int invoiceNo, String comments){
		
		this.forename = forename;
		this.surname = surname;
		this.address1 = address1;
		this.address2 = address2;
		this.address3 = address3;
		this.postCode = postCode;
		this.homePhone = homePhone;
		this.mobilePhone = mobilePhone;
		this.email = email;
		this.pcUsername = pcUsername;
		this.pcPassword = pcPassword;
		this.customerNo = customerNo;
		this.invoiceNo = invoiceNo;
		this.comments = comments;
	}
	
	public void printCustomer(){
		System.out.println("----------------------------------------------");
		System.out.println("Customer Number: " + customerNo + "\n");
		System.out.println("       Forename: " + forename);
		System.out.println("        Surname: " + surname + "\n");
		System.out.println(" Address line 1: " + address1);
		System.out.println(" Address line 2: " + address2);
		System.out.println(" Address line 3: " + address3);
		System.out.println("       Postcode: " + postCode + "\n");
		System.out.println("     Home Phone: " + homePhone);
		System.out.println("   Mobile Phone: " + mobilePhone + "\n");
		System.out.println("          Email: " + email + "\n");
		System.out.println("    PC Username: " + pcUsername);
		System.out.println("    PC Password: " + pcPassword + "\n");
		System.out.println("       Comments: " + comments);
		System.out.println("----------------------------------------------\n");
	}

	public String getForename() {
		return forename;
	}

	public void setForename(String forename) {
		this.forename = forename;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getHomePhone() {
		return homePhone;
	}

	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPcUsername() {
		return pcUsername;
	}

	public void setPcUsername(String pcUsername) {
		this.pcUsername = pcUsername;
	}

	public String getPcPassword() {
		return pcPassword;
	}

	public void setPcPassword(String pcPassword) {
		this.pcPassword = pcPassword;
	}

	public int getCustomerNo() {
		return customerNo;
	}

	public void setCustomerNo(int customerNo) {
		this.customerNo = customerNo;
	}

	public int getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(int invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getAddress3() {
		return address3;
	}

	public void setAddress3(String address3) {
		this.address3 = address3;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
	
	
	
	
}

