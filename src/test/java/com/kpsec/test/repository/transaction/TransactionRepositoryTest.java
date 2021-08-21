package com.kpsec.test.repository.transaction;

import com.kpsec.test.domain.code.CancelStatus;
import com.kpsec.test.domain.entity.Account;
import com.kpsec.test.domain.entity.Branch;
import com.kpsec.test.domain.entity.Transaction;
import com.kpsec.test.repository.account.AccountRepository;
import com.kpsec.test.repository.branch.BranchRepository;
import com.kpsec.test.repository.transaction.vo.TransactionYearlyAmountSumAccountVO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;

@DataJpaTest
class TransactionRepositoryTest {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    BranchRepository branchRepository;

    @Test
    void getYearlyNetAmountSumByAccounts() {

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

        Transaction givenTransaction = Transaction.builder()
                .date("20180101")
                .accountNo(givenAccount.getAccountNo())
                .transactionNo("1")
                .amount(new BigDecimal(0))
                .fee(new BigDecimal(0))
                .cancelStatus(CancelStatus.N)
                .account(givenAccount)
                .build();
        transactionRepository.save(givenTransaction);

        // when
        List<TransactionYearlyAmountSumAccountVO> list =
                transactionRepository.getYearlyNetAmountSumByAccounts();

        // then
        Assertions.assertThat(list).isNotEmpty();

        int givenYear = Integer.parseInt(givenTransaction.getDate().substring(0, 4));
        TransactionYearlyAmountSumAccountVO vo = list.stream()
                .filter(v ->
                        v.getYear().equals(givenYear)
                        && v.getAcctNo().equals(givenAccount.getAccountNo())).findAny().get();

        Assertions.assertThat(vo).isNotNull();
        Assertions.assertThat(vo.getYear()).isEqualTo(givenYear);
        Assertions.assertThat(vo.getBrCode()).isEqualTo(givenBranch.getBranchCode());
        Assertions.assertThat(vo.getAcctNo()).isEqualTo(givenAccount.getAccountNo());
        Assertions.assertThat(vo.getSumAmt().longValue()).isEqualTo(givenTransaction.getAmount().longValue());
    }
}