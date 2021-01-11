package com.meritamerica.bank2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.meritamerica.bank2.models.AccountHolder;
import com.meritamerica.bank2.models.CDAccount;
import com.meritamerica.bank2.models.CheckingAccount;
import com.meritamerica.bank2.models.SavingsAccount;
import com.meritamerica.bank2.models.User;



@Repository
public interface AccountRepository extends JpaRepository<AccountHolder, Long> {
	
	@Query("SELECT a FROM AccountHolder a where a.user.id = :userId")
    List<AccountHolder> findByUserIdIn(@Param("userId") Long userId);

    @Query("SELECT a FROM AccountHolder a where a.user.id = :userId")
    AccountHolder findByUserId(@Param("userId") Long userId);

	void save(SavingsAccount savingsAccount);
	void save(CheckingAccount checkingAccount);
	void save(CDAccount cdAccount);
	
//	  @Query("SELECT a.accountHolder.id FROM AccountHolder a WHERE a.user.id = :userId")
//	    Page<Long> findAccountHolderIdsByUserId(@Param("userId") Long userId, Pageable pageable);
	
}
