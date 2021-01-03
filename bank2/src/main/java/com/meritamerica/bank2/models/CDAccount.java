package com.meritamerica.bank2.models;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;



@Entity
public class CDAccount extends BankAccount {
	
	private static double balance;
	private static int term;
	private static double interestRate;
	private static Date date;
	private  static long accountNumber;
	 
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cdAccount_id",referencedColumnName = "id",nullable = false)
	private AccountHolder[] accountHolder;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "cdAccount_id_cdOffering")
	private CDOffering[] cdOffering;
	
	CDAccount(){
		super(accountNumber, balance, interestRate, date);
	}
	CDAccount(long accountNumber, double balance, double interestRate, Date date, int term){
		super(accountNumber, balance, interestRate, date);
	}
	CDAccount(CDOffering offering, double balance){
		super(balance,offering.getInterestRate());
		
		//cdOffering = offering;
		cdOffering.equals(offering);
		term=  offering.getTerm();
		interestRate = offering.getInterestRate();
		CDAccount.balance = balance;
	}
	
	
	
	//Need to override deposit and withdraw.
	@Override
	public boolean deposit(double amount) {
		return false;
	}
	@Override
	public boolean withdraw (double amount) {
		return false;
	}
	
	public static CDAccount readFromString(String accountData) {
		CDAccount cd = new CDAccount();
		String[] trans = accountData.split(",");
		try {
				
					
				accountNumber =    Long.parseLong(trans[0]);
				balance =      Double.parseDouble(trans[1]);
				interestRate = Double.parseDouble(trans[2]);
				date =       cd.dateAccountOpened(trans[3]);
				term =           Integer.parseInt(trans[4]);
			
		} catch (NumberFormatException e) {
			throw e;
		}
		
		//CDOffering offering = new CDOffering();
		cd = new CDAccount(accountNumber, balance, interestRate, date, term);
		
		/*
		System.out.println("Account: " + accountNumber + "\n" +
				"Balance: " + balance + "\n" + 
				"Interest Rate: " + interestRate + "\n" + 
				"Date: " + date + "\n" + 
				"Term: " + term);
		*/
		return cd;
	}
	@Override
	public String toString() {
		return "CDAccount [balance=" + balance + ", Offering Term =" + getTerm() + "]";
	}
	
}
