package com.kpsec.test.repository.account;

import com.kpsec.test.domain.entity.Account;
import com.kpsec.test.domain.entity.Branch;
import com.kpsec.test.repository.branch.BranchRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
class AccountRepositoryTest {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    BranchRepository branchRepository;

    @Test
    @Transactional
    void findByAccountNo() {

        // given
        Branch givenBranch = Branch.builder()
                .branchCode("Q")
                .branchName("test점")
                .build();
        branchRepository.save(givenBranch);

        Account givenAccount = Account.builder()
                .accountNo("7777777")
                .accountName("test")
                .branchCode("Q")
                .branch(givenBranch)
                .build();
        accountRepository.save(givenAccount);

        // when
        Account account = accountRepository.findByAccountNo(givenAccount.getAccountNo()).get();

        // then
        Assertions.assertThat(account).isNotNull();
        Assertions.assertThat(account.getAccountNo()).isEqualTo(givenAccount.getAccountNo());
        Assertions.assertThat(account.getAccountName()).isEqualTo(givenAccount.getAccountName());
        Assertions.assertThat(account.getBranchCode()).isEqualTo(givenAccount.getBranchCode());
        Assertions.assertThat(account.getBranch()).isEqualTo(givenAccount.getBranch());
    }
}