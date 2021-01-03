package com.meritamerica.bank2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.meritamerica.bank2.models.AccountHolder;
import com.meritamerica.bank2.models.User;



@Repository
public interface AccountRepository extends JpaRepository<AccountHolder, Long> {
	
	//List<AccountHolder> findByUserIdAndAccountHolder(@Param("userId") Long userId, @Param("accountHolder") List<AccountHolder> accountHolder);
	//AccountHolder findByUserIdAndAccountHolderId(@Param("userId") Long userId, @Param("accountHolder") Long accountHolderId);
	
}
