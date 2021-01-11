package com.meritamerica.bank2.controllers;

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
import com.meritamerica.bank2.security.CurrentUser;
import com.meritamerica.bank2.security.UserPrincipal;

@RestController
public class MeController {
	
	@Autowired
	MeritBank bank;
	
	@GetMapping(value = "/Me")
	@ResponseStatus(HttpStatus.OK)
	public  List<AccountHolder> getAccount(@CurrentUser UserPrincipal currentUser){
		AccountHolder a = new AccountHolder();
		Long id = (long) 2;
		a.setUser(new User(currentUser.getId()));
		
		return bank.getAccountHolderByUserId(currentUser);
	}
//	
//	@PostMapping(value = "/Me/CheckingAccounts")
//	@ResponseStatus(HttpStatus.CREATED)
//	public CheckingAccount[] addCheckingAccount(@PathVariable Long id, 
//			@RequestBody @Valid CheckingAccount  balance)throws NoSuchResourceFoundException, ExceedsCombinedBalanceLimitException {
//		
//		
//		return ;
//	}
//	
//	@GetMapping(value = "/Me/CheckingAccounts")
//	@ResponseStatus(HttpStatus.OK)
//	public CheckingAccount[] getCheckingAccount(@PathVariable Long id) throws NoSuchResourceFoundException {
//		
//		return ;
//	}
//	
//	@PostMapping(value = "/Me/SavingsAccounts")
//	@ResponseStatus(HttpStatus.CREATED)
//	public SavingsAccount[] addSavingsAccount(@PathVariable Long id, 
//			@RequestBody SavingsAccount  balance)throws NoSuchResourceFoundException, ExceedsCombinedBalanceLimitException {
//		
//		return ;
//	}
//	
//	@GetMapping(value = "/Me/SavingsAccounts")
//	@ResponseStatus(HttpStatus.OK)
//	public SavingsAccount[] getSavingsAccount(@PathVariable Long id) throws NoSuchResourceFoundException {
//		
//		return null;
//	}
//	
//	@PostMapping(value = "/Me/CDAccounts")
//	@ResponseStatus(HttpStatus.CREATED)
//	public CDAccount[] addCDAccount(@PathVariable Long id, 
//			@RequestBody @Valid CDAccount  cdAccount)throws NoSuchResourceFoundException, ExceedsCombinedBalanceLimitException {
//		
//		
//		return ;
//	}
//	
//	@GetMapping(value = "/Me/CDAccounts")
//	@ResponseStatus(HttpStatus.OK)
//	public CDAccount[] getCDAccount(@PathVariable Long id) throws NoSuchResourceFoundException {
//		
//		return null;
//	
//	}
	
}
