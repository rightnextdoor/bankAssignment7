package com.meritamerica.bank2.models;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.meritamerica.bank2.exception.ExceedsCombinedBalanceLimitException;
import com.meritamerica.bank2.exception.ExceedsFraudSuspicionLimitException;
import com.meritamerica.bank2.repository.CheckingAccountRepository;
import com.meritamerica.bank2.transaction.DepositTransaction;



@Entity
@Table(name = "AccountHolder", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "id",
                "user_id"
        })
})
public class AccountHolder implements Comparable<AccountHolder>
{
	private static final double MAX_BALANCE_AMOUNT = 250000;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	
	@NotBlank(message = "First name is mandatory")
	private  String firstName;
	
	private  String middleName;
	
	@NotBlank(message = "Last name is mandatory")
	private  String lastName;
	

	@NotBlank(message = "SSN is mandatory")
	private  String ssn;
	
	@JsonIgnore
	private int countCheck = 0;
	@JsonIgnore
	private int countSavings = 0;
	@JsonIgnore
	private int countCD = 0;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "accountHolder")
	private List<AccountHoldersContactDetails> accountHoldersContactDetails;
	
	
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "accountHolder")
	@OrderColumn
	private CheckingAccount[] checkingAccounts = new CheckingAccount[countCheck];
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "accountHolder")
	@OrderColumn
	private SavingsAccount[] savingsAccounts = new SavingsAccount[countSavings];
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "accountHolder")
	@OrderColumn
	private CDAccount[] cdAccounts = new CDAccount[countCD];
	
	@JsonIgnore
	@Max(value = 250000, message = "Total bank account should not be greater than $250,000")
	private  double totalB = 0;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
	
	
	

	public double getTotalB() {
		return totalB;
	}
	

	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


/**
	 * AccountHolder Constructor (String, String, String, String)
	 */
	public AccountHolder() {}
	
	public AccountHolder(String firstName, String middleName, String lastName, String ssn, Long userId) 
	{
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.ssn = ssn;
		this.user = new User(userId);
		
	}
	/** -----------------------------------------------GETTERS------------------------------------------------------*/

	public long getId() {
		return id;
	}
	/** returns instance variable firstName */

	public String getFirstName() 
	{
		return firstName;
	}

	/*
	 * returns instance variable middleName
	 */
	public String getMiddleName()
	{
		return middleName;
	}
	/*
	 * returns instance variable lastName
	 */
	public String getLastName() 
	{
		return lastName;
	}
	/*
	 * returns instance variable ssn
	 */
	public String getSSN() 
	{
		return ssn;
	}

	/** -----------------------------------------------SETTERS------------------------------------------------------*/

	/** Sets first name */
	public void setFirstName(String firstName) 
	{
		this.firstName= firstName; 
	}

	/** Sets middle name */
	public void setMiddleName(String middleName) 
	{
		this.middleName = middleName;
	}

	/** Sets last name */
	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	/** Sets SSN name */
	public void setSSN(String ssn) 
	{
		this.ssn = ssn;
	}
	public void setId(long id) {
		this.id = id;
	}

	/** -----------------------------------------------CHECKING ACCOUNT------------------------------------------------------*/
	/** Creates a checking account and calls addCheckingAccount(CheckingAccount) method */
	

	
	public AccountHoldersContactDetails addContact(Long number) {
		return addContact(number);
	}
	
	
	public CheckingAccount addCheckingAccount(double openingBalance)throws ExceedsCombinedBalanceLimitException

	{
		
		return addCheckingAccount(openingBalance);
		
	}

	/** Receives a checkingAccount and stores it in an CheckingAccount[] */
	public CheckingAccount addCheckingAccount(CheckingAccount checkingAccount) throws ExceedsCombinedBalanceLimitException

	{

		totalB = totalB + checkingAccount.getBalance();
		if (totalB < MAX_BALANCE_AMOUNT) {
			if(countCheck >= checkingAccounts.length) {
				CheckingAccount[] newCheckingAccounts = new CheckingAccount[countCheck + 1];
				for(int i = 0 ; i < checkingAccounts.length; i++) {
					newCheckingAccounts[i] = checkingAccounts[i];
				} 
				checkingAccounts = newCheckingAccounts;
			}			
			checkingAccounts[countCheck] = checkingAccount;
			countCheck++; 
			return checkingAccount;
		} else 
			return null;
	}

	/** Returns CheckingAccount[] */
	public CheckingAccount[] getCheckingAccounts() 
	{
		return checkingAccounts;
	}

	/** Returns the total amount of checkingAccounts*/
	public int getNumberOfCheckingAccounts() 
	{
		return checkingAccounts.length;	
	}

	/** Returns the collective Balance between all checkingAccounts
	 *  that are stored in a CheckingAccount[] */
	public double getCheckingBalance() 
	{
		double total = 0.0;
		for(int i = 0; i < getNumberOfCheckingAccounts(); i++) {
			total += checkingAccounts[i].getBalance();
		}
		return total;

	}
	

	/** -----------------------------------------------SAVINGS ACCOUNT------------------------------------------------------*/
	/** Creates a savings account and calls addSavingsAccount(SavingsAccount) method */
	public SavingsAccount addSavingsAccount(double openingBalance)throws ExceedsCombinedBalanceLimitException

	{
		return addSavingsAccount(openingBalance);
	}
	/** Receives a savingsAccount and stores it into a SavingsAccount[]*/
	public SavingsAccount addSavingsAccount(SavingsAccount savingsAccount)throws ExceedsCombinedBalanceLimitException

	{
		totalB = totalB + savingsAccount.getBalance();
		if (totalB < MAX_BALANCE_AMOUNT) {
			if(countSavings >= savingsAccounts.length) {
				SavingsAccount[] newSavingsAccounts = new SavingsAccount[countSavings + 1];
				for(int i = 0 ; i < savingsAccounts.length  ; i++) {
					newSavingsAccounts[i] = savingsAccounts[i];
				} 
				savingsAccounts = newSavingsAccounts;
			}			
			savingsAccounts[countSavings] = savingsAccount;
			countSavings++; 
			return savingsAccount;
		} else 
			return null;
	}

	/** Returns a SavingsAccount[]*/
	public SavingsAccount[] getSavingsAccounts()
	{
		return savingsAccounts;
	}

	/** Returns the total amount of savingsAccounts*/
	public int getNumberOfSavingsAccounts() 
	{
		return savingsAccounts.length;
	}

	/** Returns the collective Balance between all savingsAccounts
	 *  that are stored in a SavingsAccount[] */
	public double getSavingsBalance() 
	{
		double total = 0.0;
		for(int i = 0; i < getNumberOfSavingsAccounts(); i++) {
			total += savingsAccounts[i].getBalance();
		}
		return total;

	}
	

	/** -----------------------------------------------CD ACCOUNT------------------------------------------------------*/

	/** Creates a CD Account
	 * @throws ExceedsFraudSuspicionLimitException */
	public CDAccount addCDAccount(CDOffering offering, double openingBalance) throws ExceedsFraudSuspicionLimitException 
	{
	
		if(openingBalance > 1000) {
			throw  new ExceedsFraudSuspicionLimitException("Amount is $1000 or more " );
		} else {
			DepositTransaction d; //= new DepositTransaction(cdAccount,openingBalance);
			CDAccount cdA = new CDAccount(offering, openingBalance);
			return addCDAccount(cdA);
			
		}
		
	}

	/** Adds cdAccount into a CDAccounts[]*/
	public CDAccount addCDAccount(CDAccount cdAccount) 
	{
		if(countCD >= this.cdAccounts.length) 
		{
			CDAccount[] newCDAccount = new CDAccount[countCD + 1];
			for(int i = 0; i < cdAccounts.length; i++) 
			{
				newCDAccount[i] = this.cdAccounts[i];
			}
			cdAccounts = newCDAccount;
			cdAccounts[countCD] = cdAccount;
			countCD ++;
		}
		//---add deposit with opening balance
		return cdAccount;
	}
	/** Returns a CDAccounts[]*/
	public CDAccount[] getCDAccounts() 
	{
		return cdAccounts;
	}
	
	/** Returns the amount of CDAcounts*/
	public int getNumberOfCDAccounts() 
	{
		return cdAccounts.length;
	}
	/** Returns the combined balance of all CD Accounts*/
	public double getCDBalance() {
		double total = 0.0;
		for(int i = 0; i < cdAccounts.length; i++) {
			total += cdAccounts[i].getBalance();
		}
		return total;
	}
	
	/**------------------------------------------------------------------------------------------------------------------------ */
	/** Adds Savings Balance, Checking Balance and CD Balance*/
	public double getCombinedBalance() 
	{
		return (getCheckingBalance() + getSavingsBalance() + getCDBalance());
		//return totalB;
	}

	public  static AccountHolder readFromString(String accountHolderData) 
	{

		String lastName;
		String middleName;
		String firstName;
		String ssn;
		AccountHolder ac;
		String[] values = accountHolderData.split(",");

		try 
		{
			lastName = String.valueOf(values[0]);
			middleName = values[1];
			firstName = values[2];
			ssn = values[3];
		} catch (Exception e) {
			throw e;
		}
		//ac = new AccountHolder(firstName, middleName, lastName, ssn);

		//return ac;
		return null;
	}

	@Override
	public String toString()
	{
		String client = "Name: " + this.firstName + " " + this.middleName + " " + this.lastName + "\n" + 
				"SSN: " + this.ssn + "\n" +
				"Checkings Balance: $" + getCheckingBalance() + "\n" +
				"Savings Balance: $" + getSavingsBalance() + "\n" +
				"CD Accounts Balance: $" + getCDBalance() + " " +   
				"Total Balance: $" + getCombinedBalance();
		return client;
	}
	
	@Override
	public int compareTo(AccountHolder otherAccountHolder) 
	{

		if(getCombinedBalance() > otherAccountHolder.getCombinedBalance()) {
			return 1;
		}else {
			return -1;
		}
	}
}
