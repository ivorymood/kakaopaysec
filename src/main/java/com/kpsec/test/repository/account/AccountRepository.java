package com.kpsec.test.repository.account;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kpsec.test.domain.AccountResult;
import com.kpsec.test.domain.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query(value = "SELECT account_no as accountNo, account_name as accountName FROM account WHERE branch_code = :branchCode", nativeQuery = true)
    List<AccountResult> getAccountByBranchCode(@Param("branchCode") String branchCode);

    Optional<Account> findByAccountNo(String accountNo);
}
