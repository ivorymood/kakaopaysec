package com.kpsec.test.repository.account;

import com.kpsec.test.domain.entity.Account;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class AccountRepositoryTest {

    @Autowired
    AccountRepository accountRepository;

    @Test
    void findByAccountNo() {

        // given
        String accountNo = "11111111";
        String accountName = "제이";
        String branchCode = "A";

        // when
        Account account = accountRepository.findByAccountNo(accountNo).get();

        // then
        Assertions.assertThat(account).isNotNull();
        Assertions.assertThat(account.getAccountNo()).isEqualTo(accountNo);
        Assertions.assertThat(account.getAccountName()).isEqualTo(accountName);
        Assertions.assertThat(account.getBranchCode()).isEqualTo(branchCode);
    }
}