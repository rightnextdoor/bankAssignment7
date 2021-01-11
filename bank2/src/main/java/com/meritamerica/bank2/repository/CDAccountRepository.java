package com.meritamerica.bank2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.meritamerica.bank2.models.CDAccount;


@Repository
public interface CDAccountRepository extends JpaRepository<CDAccount, Long>{

}
