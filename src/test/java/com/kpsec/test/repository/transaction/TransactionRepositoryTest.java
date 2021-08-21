package com.kpsec.test.repository.transaction;

import com.kpsec.test.domain.code.CancelStatus;
import com.kpsec.test.domain.entity.Account;
import com.kpsec.test.domain.entity.Branch;
import com.kpsec.test.domain.entity.Transaction;
import com.kpsec.test.repository.account.AccountRepository;
import com.kpsec.test.repository.branch.BranchRepository;
import com.kpsec.test.repository.transaction.vo.TransactionYearlyAmountSumAccountVO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;

@DataJpaTest
class TransactionRepositoryTest {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private BranchRepository branchRepository;

    private Branch givenBranch;
    private Account givenAccount;
    private Transaction givenTransaction;
    private int givenYear;

    @BeforeEach
    void setUp() {
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

        givenTransaction = Transaction.builder()
                .date("20180101")
                .accountNo(givenAccount.getAccountNo())
                .transactionNo("1")
                .amount(new BigDecimal(0))
                .fee(new BigDecimal(0))
                .cancelStatus(CancelStatus.N)
                .account(givenAccount)
                .build();
        transactionRepository.save(givenTransaction);

        givenYear = Integer.parseInt(givenTransaction.getDate().substring(0, 4));
    }

    @Test
    void getYearlyNetAmountSumByAccounts() {

        // when
        List<TransactionYearlyAmountSumAccountVO> list =
                transactionRepository.getYearlyNetAmountSumByAccounts();

        // then
        Assertions.assertThat(list).isNotEmpty();
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