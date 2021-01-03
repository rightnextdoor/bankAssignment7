package com.meritamerica.bank2.transaction;

import com.meritamerica.bank2.exception.ExceedsAvailableBalanceException;
import com.meritamerica.bank2.exception.ExceedsFraudSuspicionLimitException;
import com.meritamerica.bank2.exception.NegativeAmountException;
import com.meritamerica.bank2.models.BankAccount;

public class WithdrawTransaction extends Transaction{
	//public double amount;
	WithdrawTransaction(BankAccount targetAccount, double amount){
		//this.targetAccount = targetAccount;
		setTargetAccount(targetAccount);
		this.amount = amount;
	}

	@Override
	public void process() throws NegativeAmountException, ExceedsAvailableBalanceException, ExceedsFraudSuspicionLimitException {
		if (amount < 0) {
			setRejectionReason("You enter a negative amount " + amount);
			throw new NegativeAmountException("You enter a negative amount " + amount);
			
		} else if (targetAccount.getBalance() < amount) {
			setRejectionReason("Not enough money in target amount"  + amount);
			throw new ExceedsAvailableBalanceException("Not enough money in target amount " + amount);
			
		} else if(amount > 1000) {
			setRejectionReason("Amount more then $1000 " + amount);
			setProcessedByFraudTeam(true);
			throw  new ExceedsFraudSuspicionLimitException("Amount is $1000 or more " + amount);
			
		} else { 
			targetAccount.withdraw(getAmount() - amount );
		}
	}
	
}
