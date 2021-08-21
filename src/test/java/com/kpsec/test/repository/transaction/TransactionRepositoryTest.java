package com.kpsec.test.repository.transaction;

import com.kpsec.test.repository.transaction.vo.TransactionYearlyAmountSumAccountVO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
class TransactionRepositoryTest {

    @Autowired
    TransactionRepository transactionRepository;

    @Test
    void getYearlyNetAmountSumByAccounts() {

        // given
        Integer year = 2018;
        String accountNo = "11111111";

        // when
        List<TransactionYearlyAmountSumAccountVO> list =
                transactionRepository.getYearlyNetAmountSumByAccounts();

        // then
        Assertions.assertThat(list).isNotEmpty();
        Long count = list.stream()
                .filter(vo -> vo.getYear().equals(year)
                        && vo.getAcctNo().equals(accountNo))
                .count();

        Assertions.assertThat(count).isGreaterThan(0);
    }
}