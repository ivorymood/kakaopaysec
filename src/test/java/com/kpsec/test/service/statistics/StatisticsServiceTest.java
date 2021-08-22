package com.kpsec.test.service.statistics;

import com.kpsec.test.domain.entity.Account;
import com.kpsec.test.domain.entity.Branch;
import com.kpsec.test.domain.entity.Statistics;
import com.kpsec.test.infra.InitData;
import com.kpsec.test.repository.account.AccountRepository;
import com.kpsec.test.repository.branch.BranchRepository;
import com.kpsec.test.repository.statistics.StatisticsRepository;
import com.kpsec.test.vo.YearlyAmountSumAccountVO;
import com.kpsec.test.vo.YearlyAmountSumBranchVO;
import com.kpsec.test.service.statistics.dto.YearDTO;
import com.kpsec.test.vo.YearlyAccountVO;
import com.kpsec.test.vo.TotalAmountSumBranchVO;
import com.kpsec.test.vo.YearlyAmountSumBranchListVO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;

@SpringBootTest(classes = StatisticsService.class)
class StatisticsServiceTest {

    @Autowired
    private StatisticsService statisticsService;

    @MockBean
    private InitData initData;

    @MockBean
    private StatisticsRepository statisticsRepository;

    @MockBean
    private AccountRepository accountRepository;

    @MockBean
    private BranchRepository branchRepository;

    private Branch givenBranch;
    private Account givenAccount;
    private Statistics givenStatistics;

    @BeforeEach
    void setUp() {
        givenBranch = Branch.builder()
                .branchCode("Q")
                .branchName("test점")
                .build();

        givenAccount = Account.builder()
                .accountNo("7777777")
                .accountName("test")
                .branchCode(givenBranch.getBranchCode())
                .branch(givenBranch)
                .build();

        givenStatistics = Statistics.builder()
                .year(2018)
                .branchCode(givenBranch.getBranchCode())
                .accountNo(givenAccount.getAccountNo())
                .netAmountSum(new BigDecimal(0))
                .account(givenAccount)
                .branch(givenBranch)
                .build();
    }

    @Test
    void getYearlyTopAmountAccountsTest() {

        // given
        YearDTO dto = new YearDTO();
        dto.setYear(givenStatistics.getYear().toString());
        List<YearDTO> dtoList = new ArrayList<>();
        dtoList.add(dto);
        List<Integer> yearList = new ArrayList<>();
        yearList.add(givenStatistics.getYear());

        List<YearlyAmountSumAccountVO> givenList = new ArrayList<>();
        givenList.add(new YearlyAmountSumAccountVO() {
            @Override
            public Integer getYear() {
                return givenStatistics.getYear();
            }

            @Override
            public String getName() {
                return givenStatistics.getAccount().getAccountName();
            }

            @Override
            public String getAcctNo() {
                return givenAccount.getAccountNo();
            }

            @Override
            public BigDecimal getSumAmt() {
                return givenStatistics.getNetAmountSum();
            }
        });
        given(statisticsRepository.getYearlyTopAmountAccounts(yearList))
                .willReturn(givenList);

        // when
        List<YearlyAmountSumAccountVO> list = statisticsService.getYearlyTopAmountAccounts(dtoList);

        // then
        Assertions.assertThat(list).isEqualTo(givenList);
    }

    @Test
    void getYearlyNonTransactionAccountsTest() {

        // given
        YearDTO dto = new YearDTO();
        dto.setYear(givenStatistics.getYear().toString());
        List<YearDTO> dtoList = new ArrayList<>();
        dtoList.add(dto);
        List<Integer> yearList = new ArrayList<>();
        yearList.add(givenStatistics.getYear());

        List<Statistics> statisticsList = new ArrayList<>();
        statisticsList.add(givenStatistics);
        given(statisticsRepository.findAllByYearIsInOrderByYear(yearList)).willReturn(statisticsList);

        List<Account> accountList = new ArrayList<>();
        accountList.add(givenAccount);
        given(accountRepository.findAll()).willReturn(accountList);

        List<YearlyAccountVO> givenList = new ArrayList<>();
        givenList.add(new YearlyAccountVO(givenStatistics.getYear(),
                givenAccount.getAccountName(), givenAccount.getAccountNo()));

        // when
        List<YearlyAccountVO> list =
                statisticsService.getYearlyNonTransactionAccounts(dtoList);

        // then
        Assertions.assertThat(list).isEmpty();
    }

    @Test
    void getYearlyAmountSumByBranchTest() {

        // given
        List<YearlyAmountSumBranchVO> givenList = new ArrayList<>();
        givenList.add(new YearlyAmountSumBranchVO(
                givenStatistics.getYear(),
                givenStatistics.getBranch().getBranchName(),
                givenStatistics.getBranchCode(),
                givenStatistics.getNetAmountSum()
        ));

        given(statisticsRepository.getYearlyNetAmountSumByBranch()).willReturn(givenList);

        // when
        List<YearlyAmountSumBranchListVO> list = statisticsService.getYearlyAmountSumByBranch();

        // then
        Assertions.assertThat(list.get(0).getYear())
                .isEqualTo(givenList.get(0).getYear());
        Assertions.assertThat(list.get(0).getDataList().get(0).getBrName())
                .isEqualTo(givenList.get(0).getBranchName());
        Assertions.assertThat(list.get(0).getDataList().get(0).getBrCode())
                .isEqualTo(givenList.get(0).getBranchCode());
        Assertions.assertThat(list.get(0).getDataList().get(0).getSumAmt().longValue())
                .isEqualTo(givenList.get(0).getNetAmountSum().longValue());
    }

    @Test
    void getTotalAmountSumByBranchTest() {

        // given
        given(branchRepository.findByBranchName(givenBranch.getBranchName()))
                .willReturn(Optional.of(givenBranch));
        BigDecimal givenAmt = new BigDecimal(0);
        given(statisticsRepository.getTotalSumByBranchCode(givenBranch.getBranchCode()))
                .willReturn(Optional.of(givenAmt));

        // when
        TotalAmountSumBranchVO vo =
                statisticsService.getTotalAmountSumByBranch(givenBranch.getBranchName());

        // then
        Assertions.assertThat(vo.getSumAmt().longValue()).isEqualTo(givenAmt.longValue());
    }
}