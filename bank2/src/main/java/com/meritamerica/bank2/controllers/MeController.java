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
import com.meritamerica.bank2.repository.AccountRepository;
import com.meritamerica.bank2.repository.CDAccountRepository;
import com.meritamerica.bank2.repository.CheckingAccountRepository;
import com.meritamerica.bank2.repository.SavingsAccountRepository;
import com.meritamerica.bank2.security.CurrentUser;
import com.meritamerica.bank2.security.UserPrincipal;

@RestController
public class MeController {
	
	@Autowired
	MeritBank bank;
	
	@Autowired
	AccountRepository account;
	@Autowired
	CheckingAccountRepository checkings;
	@Autowired
	SavingsAccountRepository savings;
	@Autowired
	CDAccountRepository cd;
	
	List<CheckingAccount> checking = new ArrayList<>();
	
	@GetMapping(value = "/Me")
	@ResponseStatus(HttpStatus.OK)
	public  AccountHolder getAccount(@CurrentUser UserPrincipal currentUser){
		AccountHolder a = new AccountHolder();
		Long id = (long) 2;
		a.setUser(new User(currentUser.getId()));
		
		return bank.getAccountHolderByUserId(currentUser);
	}
	
	@PostMapping(value = "/Me/CheckingAccounts")
	@ResponseStatus(HttpStatus.CREATED)
	public CheckingAccount[] addCheckingAccount(@CurrentUser UserPrincipal currentUser
			,@RequestBody @Valid CheckingAccount  balance)throws NoSuchResourceFoundException, ExceedsCombinedBalanceLimitException {
		
		 checkings.save((bank.getAccountHolderByUserId(currentUser).addCheckingAccount(balance)));
		return bank.getAccountHolderByUserId(currentUser).getCheckingAccounts() ;
	}
	
	@GetMapping(value = "/Me/CheckingAccounts")
	@ResponseStatus(HttpStatus.OK)
	public List<CheckingAccount> getCheckingAccount(@CurrentUser UserPrincipal currentUser) throws NoSuchResourceFoundException {
		
		//return bank.getAccountHolderByUserId(currentUser).getCheckingAccounts();
		return checkings.findAll();
	}
	
	@PostMapping(value = "/Me/SavingsAccounts")
	@ResponseStatus(HttpStatus.CREATED)
	public SavingsAccount[] addSavingsAccount(@CurrentUser UserPrincipal currentUser, 
			@RequestBody SavingsAccount  balance)throws NoSuchResourceFoundException, ExceedsCombinedBalanceLimitException {
		
		 savings.save((bank.getAccountHolderByUserId(currentUser).addSavingsAccount(balance)));
			return bank.getAccountHolderByUserId(currentUser).getSavingsAccounts() ;
	}
	
	@GetMapping(value = "/Me/SavingsAccounts")
	@ResponseStatus(HttpStatus.OK)
	public List<SavingsAccount> getSavingsAccount(@CurrentUser UserPrincipal currentUser) throws NoSuchResourceFoundException {
		
		return savings.findAll();
	}
	
	@PostMapping(value = "/Me/CDAccounts")
	@ResponseStatus(HttpStatus.CREATED)
	public CDAccount[] addCDAccount(@CurrentUser UserPrincipal currentUser, 
			@RequestBody @Valid CDAccount  cdAccount)throws NoSuchResourceFoundException, ExceedsCombinedBalanceLimitException {
		
		
		 cd.save((bank.getAccountHolderByUserId(currentUser).addCDAccount(cdAccount)));
			return bank.getAccountHolderByUserId(currentUser).getCDAccounts() ;
	}
	
	@GetMapping(value = "/Me/CDAccounts")
	@ResponseStatus(HttpStatus.OK)
	public List<CDAccount> getCDAccount(@CurrentUser UserPrincipal currentUser) throws NoSuchResourceFoundException {
		
		return cd.findAll();
	
	}
	
}
