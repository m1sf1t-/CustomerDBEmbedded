package uk.co.wilsonpcrepair.model;

import java.sql.Blob;
import java.sql.Date;

public class Invoice {

	private int invoiceNo = -1;
	private int customerNo = -1;
	private Blob invoiceFile = null;
	private Date date = null;
	
	public Invoice (int invoiceNo, int customerNo, Blob invoiceFile, Date date){
		this.invoiceNo = invoiceNo;
		this.customerNo = customerNo;
		this.invoiceFile = invoiceFile;
		this.date = date;
	}
	
	public void printInvoice(){
		System.out.println("----------------------------------------------");
		System.out.println(" Invoice Number: " + invoiceNo);
		System.out.println("Customer Number: " + customerNo);
		System.out.println("           Date: " + date);
		System.out.println("----------------------------------------------\n");
	}

	public int getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(int invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public int getCustomerNo() {
		return customerNo;
	}

	public void setCustomerNo(int customerNo) {
		this.customerNo = customerNo;
	}

	public Blob getInvoiceFile() {
		return invoiceFile;
	}

	public void setInvoiceFile(Blob invoiceFile) {
		this.invoiceFile = invoiceFile;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	
}
