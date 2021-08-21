package com.kpsec.test.repository.account;

import com.kpsec.test.domain.entity.Account;
import com.kpsec.test.domain.entity.Branch;
import com.kpsec.test.repository.branch.BranchRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private BranchRepository branchRepository;

    private Branch givenBranch;
    private Account givenAccount;

    @BeforeEach
    void setUp() {
        // given
        givenBranch = Branch.builder()
                .branchCode("Q")
                .branchName("test점")
                .build();
        branchRepository.save(givenBranch);

        givenAccount = Account.builder()
                .accountNo("7777777")
                .accountName("test")
                .branchCode(givenBranch.getBranchCode())
                .branch(givenBranch)
                .build();
        accountRepository.save(givenAccount);
    }

    @Test
    void findByAccountNo() {

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