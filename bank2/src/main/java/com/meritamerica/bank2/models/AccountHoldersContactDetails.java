package com.meritamerica.bank2.models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;



@Entity
public class AccountHoldersContactDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "AccountHolderContact_Id")
	private long id;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	private long phone;
	


	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "accountHolder_id", referencedColumnName = "id")
	private AccountHolder accountHolder;
	
	public AccountHoldersContactDetails() {
		super();
		
	}
	
	public AccountHoldersContactDetails(long phone) {
		super();
		phone = phone;
	}

	public long getPhone() {
		return phone;
	}

	public void setPhone(long phone) {
		this.phone = phone;
	}
}
