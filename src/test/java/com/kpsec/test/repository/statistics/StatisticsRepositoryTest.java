package com.kpsec.test.repository.statistics;

import com.kpsec.test.domain.entity.Statistics;
import com.kpsec.test.repository.statistics.vo.StatisticsVO;
import com.kpsec.test.repository.statistics.vo.StatisticsYearlyAmountSumBranchVO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
class StatisticsRepositoryTest {

    @Autowired
    StatisticsRepository statisticsRepository;

    @Test
    void getYearlyTopAmountAccounts() {

        // given
        List<Integer> yearList = new ArrayList<>();
        yearList.add(2018);
        String accountNo = "11111114";
        BigDecimal netAmountSum = new BigDecimal(28992000);

        // when
        List<StatisticsVO> list =
                statisticsRepository.getYearlyTopAmountAccounts(yearList);

        // then
        Assertions.assertThat(list).isNotEmpty();
        StatisticsVO vo = list.get(0);
        Assertions.assertThat(vo.getAcctNo()).isEqualTo(accountNo);
        Assertions.assertThat(vo.getSumAmt().longValue()).isEqualTo(netAmountSum.longValue());
    }

    @Test
    void findAllByYearIsInOrderByYear() {

        // given
        List<Integer> yearList = new ArrayList<>();
        yearList.add(2018);
        yearList.add(2019);
        String accountNo = "11111111";

        // when
        List<Statistics> list =
                statisticsRepository.findAllByYearIsInOrderByYear(yearList);

        // then
        Assertions.assertThat(list).isNotEmpty();
        Long count = list.stream()
                .filter(s -> s.getAccountNo().equals(accountNo))
                .count();
        Assertions.assertThat(count).isEqualTo(2);
    }

    @Test
    void getYearlyNetAmountSumByBranch() {

        // given
        Integer year = 2018;
        String branchName = "잠실점";
        String branchCode = "D";
        BigDecimal netAmountSum = new BigDecimal(14000000);
        int order = 3;

        // when
        List<StatisticsYearlyAmountSumBranchVO> list =
                statisticsRepository.getYearlyNetAmountSumByBranch();

        // then
        Assertions.assertThat(list).isNotEmpty();
        StatisticsYearlyAmountSumBranchVO vo = list.get(order);
        Assertions.assertThat(vo.getYear()).isEqualTo(year);
        Assertions.assertThat(vo.getBranchName()).isEqualTo(branchName);
        Assertions.assertThat(vo.getBranchCode()).isEqualTo(branchCode);
        Assertions.assertThat(vo.getNetAmountSum().longValue()).isEqualTo(netAmountSum.longValue());
    }

    @Test
    void getTotalSumByBranchName() {

        // given
        String branchName = "강남점";
        BigDecimal netAmountSum = new BigDecimal(39732867);

        // when
        BigDecimal totalSum = statisticsRepository.getTotalSumByBranchName(branchName);

        // then
        Assertions.assertThat(totalSum).isNotNull();
        Assertions.assertThat(totalSum.longValue()).isEqualTo(netAmountSum.longValue());
    }
}