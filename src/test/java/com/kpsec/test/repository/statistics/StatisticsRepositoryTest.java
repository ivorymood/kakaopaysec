package com.kpsec.test.repository.statistics;

import com.kpsec.test.domain.entity.Account;
import com.kpsec.test.domain.entity.Branch;
import com.kpsec.test.domain.entity.Statistics;
import com.kpsec.test.repository.account.AccountRepository;
import com.kpsec.test.repository.branch.BranchRepository;
import com.kpsec.test.vo.YearlyAmountSumAccountVO;
import com.kpsec.test.vo.YearlyAmountSumBranchVO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@DataJpaTest
class StatisticsRepositoryTest {

    @Autowired
    private StatisticsRepository statisticsRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private BranchRepository branchRepository;

    private Branch givenBranch;
    private Account givenAccount;
    private Statistics givenStatistics;

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

        givenStatistics = Statistics.builder()
                .year(2018)
                .branchCode(givenBranch.getBranchCode())
                .accountNo(givenAccount.getAccountNo())
                .netAmountSum(new BigDecimal(0))
                .account(givenAccount)
                .branch(givenBranch)
                .build();
        statisticsRepository.save(givenStatistics);
    }

    @Test
    void getYearlyTopAmountAccountsTest() {

        // when
        List<Integer> yearList = new ArrayList<>();
        yearList.add(givenStatistics.getYear());
        List<YearlyAmountSumAccountVO> list =
                statisticsRepository.getYearlyTopAmountAccounts(yearList);

        // then
        Assertions.assertThat(list).isNotEmpty();
        YearlyAmountSumAccountVO vo = list.get(0);
        Assertions.assertThat(vo.getAcctNo())
                .isEqualTo(givenStatistics.getAccount().getAccountNo());
        Assertions.assertThat(vo.getSumAmt().longValue())
                .isEqualTo(givenStatistics.getNetAmountSum().longValue());
    }

    @Test
    void findAllByYearIsInOrderByYearTest() {

        // when
        List<Integer> yearList = new ArrayList<>();
        yearList.add(givenStatistics.getYear());
        List<Statistics> list =
                statisticsRepository.findAllByYearIsInOrderByYear(yearList);

        // then
        Assertions.assertThat(list).isNotEmpty();
        Statistics statistics = list.stream()
                .filter(s -> s.getYear().equals(givenStatistics.getYear())).findAny().get();
        Assertions.assertThat(statistics.getYear()).isEqualTo(givenStatistics.getYear());
        Assertions.assertThat(statistics.getBranchCode()).isEqualTo(givenStatistics.getBranchCode());
        Assertions.assertThat(statistics.getAccountNo()).isEqualTo(givenStatistics.getAccountNo());
        Assertions.assertThat(statistics.getNetAmountSum()).isEqualTo(givenStatistics.getNetAmountSum());
        Assertions.assertThat(statistics.getAccount()).isEqualTo(givenStatistics.getAccount());
    }

    @Test
    void getYearlyNetAmountSumByBranchTest() {

        // when
        List<YearlyAmountSumBranchVO> list =
                statisticsRepository.getYearlyNetAmountSumByBranch();

        // then
        Assertions.assertThat(list).isNotEmpty();
        YearlyAmountSumBranchVO vo = list.get(0);
        Assertions.assertThat(vo.getYear()).isEqualTo(givenStatistics.getYear());
        Assertions.assertThat(vo.getBranchName()).isEqualTo(givenStatistics.getBranch().getBranchName());
        Assertions.assertThat(vo.getBranchCode()).isEqualTo(givenStatistics.getBranchCode());
        Assertions.assertThat(vo.getNetAmountSum().longValue())
                .isEqualTo(givenStatistics.getNetAmountSum().longValue());
    }

    @Test
    void getTotalSumByBranchNameTest() {

        // when
        BigDecimal totalSum = statisticsRepository
                .getTotalSumByBranchCode(givenBranch.getBranchCode()).get();

        // then
        Assertions.assertThat(totalSum).isNotNull();
        Assertions.assertThat(totalSum.longValue())
                .isEqualTo(givenStatistics.getNetAmountSum().longValue());
    }
}