package com.meritamerica.bank2.transaction;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.meritamerica.bank2.exception.ExceedsAvailableBalanceException;
import com.meritamerica.bank2.exception.ExceedsFraudSuspicionLimitException;
import com.meritamerica.bank2.exception.NegativeAmountException;
import com.meritamerica.bank2.models.BankAccount;



public abstract class Transaction {

	BankAccount sourceAccount;
	BankAccount targetAccount;
	double amount;
	Date TransactionDate;
	String reason;
	boolean isProcessed;


	public BankAccount getSourceAccount() {
		return sourceAccount;
	}
	public void setSourceAccount(BankAccount sourceAccount) {
		this.sourceAccount = sourceAccount;
	}
	public BankAccount getTargetAccount() {
		return targetAccount;
	}
	public void setTargetAccount(BankAccount targetAccount) {
		this.targetAccount = targetAccount;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public Date getTransactionDate() {
		return TransactionDate;
	}
	public void setTransactionDate(Date date) {
		TransactionDate = date;
	}

	public String writeToString() {
		String temp;
		
		temp = 	getSourceAccount().toString() + "," + getAmount() + "," +	getTransactionDate() + "\n";
		return temp;
	}

	public static Transaction readFromString(String transactionDataString) {
		String[] info = new String[4];
		info = transactionDataString.split(",");
		BankAccount account = null;
		int accountNum = (int) account.getAccountNumber();
		double balance = 0;
		Date opened = null;
		try {
			accountNum = Integer.parseInt(info[1]);
			balance = Double.parseDouble(info[2]);
			opened = new SimpleDateFormat("dd/MM/yyyy").parse(info[3]);
		}catch(NumberFormatException e ){
			e.printStackTrace();
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
		/*
		accountNum = Integer.parseInt(info[1]);
		balance = Double.parseDouble(info[2]);
		try {
			opened = new SimpleDateFormat("dd/MM/yyyy").parse(info[3]);
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
		*/
		Transaction ba = null;
		ba.setAmount(balance);
		//ba.setSourceAccount(accountNum);
		ba.setTransactionDate(opened);
		
		return null;
		
	}

	public abstract void process() throws NegativeAmountException, ExceedsAvailableBalanceException, ExceedsFraudSuspicionLimitException;
	

	public boolean isProcessedByFraudTeam() {
		return isProcessed;
	}

	public void setProcessedByFraudTeam(boolean isProcessed) {
		this.isProcessed = isProcessed;
	}

	public String getRejectionReason() {
		return reason;
	}

	public void setRejectionReason(String reason) {
		this.reason = reason;
	}


}
