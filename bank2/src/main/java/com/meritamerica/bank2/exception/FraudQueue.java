package com.meritamerica.bank2.exception;

import com.meritamerica.bank2.transaction.Transaction;

public class FraudQueue {

	//public List<Transaction> ListOfTransaction = ArrayList<Transaction>();
	
	FraudQueue(){

	}

	public void addTransaction(Transaction transaction) {
	this.transaction = transaction.isProcessedByFraudTeam();
	}

	public Transaction getTransaction() {
		return getTransaction();
	}
	Boolean transaction;
}
