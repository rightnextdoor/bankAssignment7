package com.meritamerica.bank2.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;



@Entity
public class CheckingAccount extends BankAccount{
	
	private static long accountNumber;
	private static double interestRate = 0.0001;
	private static Date date ;
	private static double balance;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "accountHolder_id")
	private AccountHolder[] accountHolder;

	CheckingAccount() {
		super(MeritBank.getNextAccountNumber(),balance,interestRate, date);
	}
	CheckingAccount(double openingBalance) {
		super(MeritBank.getNextAccountNumber(),openingBalance,interestRate  );
	}	
	CheckingAccount(long accountNumber, double balance, double interestRate, Date date){
		super(accountNumber,balance,interestRate, date);
	}
	public CheckingAccount(double balance, double interestRate) {
		super(balance,interestRate);
	}
	
	public static CheckingAccount readFromString(String accountData)
	{
		double balance;
		double interestRate;
		
		CheckingAccount checking = new CheckingAccount();
		String[] values = accountData.split(",");
		try 
		{
				accountNumber = Long.parseLong(values[0]);
				balance = Double.parseDouble(values[1]);
				interestRate = Double.parseDouble(values[2]);
				date = checking.dateAccountOpened(values[3]);
											
		} catch (NumberFormatException e) 
		{
			throw e;
		}
			checking = new CheckingAccount(accountNumber, balance, 
			interestRate, date);
			return checking;
	}
	
}
