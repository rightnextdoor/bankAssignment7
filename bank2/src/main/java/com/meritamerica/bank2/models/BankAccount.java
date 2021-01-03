package com.meritamerica.bank2.models;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;

import com.fasterxml.jackson.annotation.JsonIgnore;


@MappedSuperclass
public abstract class BankAccount {
	

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private long id;
	
	@ManyToOne
	@JoinColumn(name = "account_holder_id")
	@JsonIgnore
	protected AccountHolder accountHolder;
	
	//@Column(name = "Balance")
	@Min(value = 0, message="Balance must be positive")
	private double  balance ;
	
	//@Column(name = "InterestRate")
	@DecimalMin (value = "0.0", message = "interestRate > 0.0")
	@DecimalMax (value = "1.0", message = "interestRate < 1.0")
	private double interestRate;
	
	//@Column(name = "AccountNumber")
	private long accountNumber;
	
	//@Column(name = "FutereValue")
	private double futureValue;
	
	//@Column(name = "Date")
	private Date date;
	
	//@Column(name = "Term")
	//@Min(value = 1, message="Term must be higher then 0")
	private int term;
	//List<Transaction> transactions;
	
	
	 BankAccount(double balance, double interestRate){
		this.balance = balance;
		this.interestRate = interestRate;
	}
	
	 BankAccount(long accountNumber, double balance, double interestRate){
		 this.accountNumber = accountNumber;
		 this.balance = balance;
		 this.interestRate = interestRate;
	 }
	BankAccount(long accountNumber, double balance, double interestRate, Date date){
		this.accountNumber = accountNumber;
		this.balance = balance;
		this.interestRate = interestRate;
		this.date= date;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public int getTerm() {
		return term;
	}

	public void setTerm(int term) {
		this.term = term;
	}

	public long getAccountNumber() 
	{
		return accountNumber;
	}
	
	public double getBalance()
	{
		return balance;
	}
	public double getInterestRate() 
	{
		return interestRate;
	}
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}

	public void setAccountNumber(long accountNumber) {
		this.accountNumber = accountNumber;
	}

	public boolean withdraw(double amount)
	{
		if((balance - amount) >= 0) 
		{
			balance = balance - amount;
			return true;
		} else
			{
			
			return false;
			}	
	}
	
	public boolean deposit(double amount) 
	{
		if(((balance + amount) <= 250000) && amount > 0)
		{
			balance = balance + amount;
			return true;
		} else 
			return false;	
	}
	
	
	public double futureValue(int term) 
	{
		futureValue = balance * Math.pow((1+ interestRate ), term);
		return this.futureValue;
		
	}

	
	//public date
	
	public  Date dateAccountOpened(String string)
	{
			try 
			{
				DateFormat startDate = new SimpleDateFormat("dd/MM/yyyy"); //sets format
				Date dates = startDate.parse(string); //converts to date
	        	date = dates;
	        	return date;				// returns correct date, but with hrs/min/sec at 00:00:00 didnt know how to eliminate this. 
			} catch(ParseException e)
			{
				System.out.println();
			}
			return date;
	}
	
	public  Date getOpenedOn()
	{
		return date;
	}
	
	//ASssignment4===
	
/*	
	public void addTransaction(Transaction transaction){
	//	if(MeritBank.processTransaction(true)) {}
		transactions.add(transaction);	
		
		
	}
	
	public List<Transaction>getTransactions(){
		return transactions;
	}
	*/
	public static BankAccount readFromString()
	{
		return null;
	}
	
	public String writeToString()
	{
		return"";
	}
	@Override
	public String toString() 
	{
		return "";
	}
	
		
}
