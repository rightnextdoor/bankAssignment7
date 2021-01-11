package com.meritamerica.bank2.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.meritamerica.bank2.exception.ExceedsCombinedBalanceLimitException;
import com.meritamerica.bank2.exception.NoSuchResourceFoundException;
import com.meritamerica.bank2.models.AccountHolder;
import com.meritamerica.bank2.models.CDAccount;
import com.meritamerica.bank2.models.CheckingAccount;
import com.meritamerica.bank2.models.MeritBank;
import com.meritamerica.bank2.models.SavingsAccount;
import com.meritamerica.bank2.models.User;
import com.meritamerica.bank2.repository.UserRepository;
import com.meritamerica.bank2.security.UserPrincipal;



@RestController
public class AccountHolderController {
	
	@Autowired
	UserRepository users;
	
	@Autowired
	MeritBank service;
	
	List<AccountHolder> accountHolder = new ArrayList<AccountHolder>();
	
	@PostMapping(value = "/AccountHolders")
	@ResponseStatus(HttpStatus.CREATED)
	public AccountHolder addAccountHolder(@RequestBody @Valid AccountHolder account) {
		
		service.postAccountHolder(account);
	
		return account;
	}
	
	@GetMapping(value = "/AccountHolders")
	@ResponseStatus(HttpStatus.OK)
	public List<AccountHolder> getAccountHolder(){
		
		return service.getAccountHoldersRepository();
	}
	
	@GetMapping(value = "/AccountHolders/{id}")
	@ResponseStatus(HttpStatus.OK)
	public List<AccountHolder> getAccountHolderByID(@PathVariable Long id) throws NoSuchResourceFoundException {
		if(id > accountHolder.size()) {
			throw new NoSuchResourceFoundException("Invalid id");
		}
		for(int i = 0; i < accountHolder.size(); i++) {
			if(accountHolder.get(i).getId() == id) {
				return service.getAccountHolderById(id);
			}
		}
		return null;
	}
	
///// checking account///////////////// 	
	@PostMapping(value = "/AccountHolders/{id}/CheckingAccounts")
	@ResponseStatus(HttpStatus.CREATED)
	public CheckingAccount[] addByIdCheckingAccount(@PathVariable Long id, 
			@RequestBody @Valid CheckingAccount  balance)throws NoSuchResourceFoundException, ExceedsCombinedBalanceLimitException {
		if(id > accountHolder.size()) {
			throw new NoSuchResourceFoundException("Invalid id");
		}
		for(int i = 0; i < accountHolder.size(); i++) {
			if(accountHolder.get(i).getId() == id) {
				service.postCheckingAccount(balance, id);
				
			}
		}
		
		return service.getCheckingAccountsRepository(id);
	}
	
	@GetMapping(value = "/AccountHolders/{id}/CheckingAccounts")
	@ResponseStatus(HttpStatus.OK)
	public CheckingAccount[] getPostByIdCheckingAccount(@PathVariable Long id) throws NoSuchResourceFoundException {
		if(id >= accountHolder.size()) {
			throw new NoSuchResourceFoundException("Invalid id");
		}
		for(int i = 0; i < accountHolder.size(); i++) {
			if(accountHolder.get(i).getId() == id) {
				return service.getCheckingAccountsRepository(id);
			}
		}
		return null;
	}

	
///// savings account///////////////// 
	@PostMapping(value = "/AccountHolders/{id}/SavingsAccounts")
	@ResponseStatus(HttpStatus.CREATED)
	public SavingsAccount[] addByIdSavingsAccount(@PathVariable Long id, 
			@RequestBody SavingsAccount  balance)throws NoSuchResourceFoundException, ExceedsCombinedBalanceLimitException {
		if(id > accountHolder.size()) {
			throw new NoSuchResourceFoundException("Invalid id");
		}
		for(int i = 0; i < accountHolder.size(); i++) {
			if(accountHolder.get(i).getId() == id) {
				
				service.postSavingsAccount(balance, id);
			}
		}
		
		return service.getSavingsAccountsRepository(id);
	}
	
	@GetMapping(value = "/AccountHolders/{id}/SavingsAccounts")
	@ResponseStatus(HttpStatus.OK)
	public SavingsAccount[] getPostByIdSavingsAccount(@PathVariable Long id) throws NoSuchResourceFoundException {
		if(id >= accountHolder.size()) {
			throw new NoSuchResourceFoundException("Invalid id");
		}
		for(int i = 0; i < accountHolder.size(); i++) {
			if(accountHolder.get(i).getId() == id) {
				return service.getSavingsAccountsRepository(id);
			}
		}
		return null;
	}
	

	
///// cd account///////////////// 
	@PostMapping(value = "/AccountHolders/{id}/CDAccounts")
	@ResponseStatus(HttpStatus.CREATED)
	public CDAccount[] addByIdCDAccount(@PathVariable Long id, 
			@RequestBody @Valid CDAccount  cdAccount)throws NoSuchResourceFoundException, ExceedsCombinedBalanceLimitException {
		if(id > accountHolder.size()) {
			throw new NoSuchResourceFoundException("Invalid id");
		}
		for(int i = 0; i < accountHolder.size(); i++) {
			if(accountHolder.get(i).getId() == id) {
				service.postCDAccount(cdAccount, id);
			}
		}
		
		return service.getCDAccountsRepository(id);
	}
	
	@GetMapping(value = "/AccountHolders/{id}/CDAccounts")
	@ResponseStatus(HttpStatus.OK)
	public CDAccount[] getPostByIdCDAccount(@PathVariable Long id) throws NoSuchResourceFoundException {
		if(id > accountHolder.size()) {
			throw new NoSuchResourceFoundException("Invalid id");
		}
		for(int i = 0; i < accountHolder.size(); i++) {
			if(accountHolder.get(i).getId() == id) {
				return service.getCDAccountsRepository(id);
			}
		}
		return null;
	
	}

	
}
