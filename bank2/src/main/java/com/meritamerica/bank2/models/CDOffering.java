package com.meritamerica.bank2.models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;



@Entity
public class CDOffering {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JoinColumn(name = "CDOffering_id", referencedColumnName = "id")
	private long id;
	
	
	//@NotBlank(message = "Term is mandatory")
	private  int term ;
	
	
	//@NotBlank(message = "Interest rate is mandatory")
	private  double interestRate;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "cdOffering")
	@OrderColumn
	private CDAccount[] cdAccount;
	
	CDOffering(){
		
	}
	
	public  void setTerm(int term) {
		this.term = term;
	}

	public  void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}

	CDOffering(int t, double interest){
		term = t;
		interestRate = interest;
	}
	
	public int getTerm() {
		return term;
	}
	
	public double getInterestRate() {
		return interestRate;
	}
	
	public  CDOffering readFromString(String cdOfferingDataString) {
	
  		CDOffering offering = new CDOffering();
  		String[] values = cdOfferingDataString.split(",");
		try {
			
			term = Integer.parseInt(values[0]);
			interestRate = Double.parseDouble(values[1]);
			
			
		}catch (NumberFormatException e) {
			throw e;
		}
		
		offering = new CDOffering(term, interestRate);
		
		return offering;
	}
	
	public String writeToString() {
		String offering = getTerm() + "," + getInterestRate()+"\n";
		return offering;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	
}
