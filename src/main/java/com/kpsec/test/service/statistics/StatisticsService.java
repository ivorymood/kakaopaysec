package com.kpsec.test.service.statistics;

import com.kpsec.test.domain.entity.Account;
import com.kpsec.test.domain.entity.Branch;
import com.kpsec.test.domain.entity.Statistics;
import com.kpsec.test.exception.NotFoundException;
import com.kpsec.test.exception.NotFoundException.ResourceNotFoundExceptionCode;
import com.kpsec.test.repository.account.AccountRepository;
import com.kpsec.test.repository.branch.BranchRepository;
import com.kpsec.test.repository.statistics.StatisticsRepository;
import com.kpsec.test.repository.statistics.vo.StatisticsVO;
import com.kpsec.test.repository.statistics.vo.StatisticsYearlyAmountSumBranchVO;
import com.kpsec.test.service.statistics.dto.YearDTO;
import com.kpsec.test.service.statistics.vo.TotalAmountSumBranchVO;
import com.kpsec.test.service.statistics.vo.NonTransactionAccountVO;
import com.kpsec.test.service.statistics.vo.YearlyAmountSumBranchVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class StatisticsService {

    @Autowired
    private StatisticsRepository statisticsRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private BranchRepository branchRepository;

    public List<StatisticsVO> getYearlyTopAmountAccounts(List<YearDTO> dtoList) {

        List<Integer> yearList = new ArrayList<>();
        for (YearDTO dto : dtoList) {
            yearList.add(Integer.parseInt(dto.getYear()));
        }
        return statisticsRepository.getYearlyTopAmountAccounts(yearList);
    }

    public List<NonTransactionAccountVO> getYearlyNonTransactionAccounts(List<YearDTO> dtoList) {

        List<Integer> yearList = new ArrayList<>();
        for (YearDTO dto : dtoList) {
            yearList.add(Integer.parseInt(dto.getYear()));
        }

        List<Statistics> statisticsList = statisticsRepository.findAllByYearIsInOrderByYear(yearList);
        List<Account> accountList = accountRepository.findAll();

        List<NonTransactionAccountVO> resultList = new ArrayList<>();
        for (int year : yearList) {

            for (Account account : accountList) {

                long count = statisticsList.stream().parallel()
                        .filter(s -> year == s.getYear()
                                && account.getAccountNo().equals(s.getAccount().getAccountNo()))
                        .count();
                if (count == 0) {
                    resultList.add(new NonTransactionAccountVO(year, account.getAccountName(), account.getAccountNo()));
                }
            }
        }
        return resultList;
    }

    public List<YearlyAmountSumBranchVO> getYearlyAmountSumByBranch() {

        List<StatisticsYearlyAmountSumBranchVO> statisticsList = statisticsRepository.getYearlyNetAmountSumByBranch();

        Map<Integer, List<TotalAmountSumBranchVO>> map = new HashMap<>();
        for (StatisticsYearlyAmountSumBranchVO vo : statisticsList) {
            if (!map.containsKey(vo.getYear())) {
                map.put(vo.getYear(), new ArrayList<>());
            }
            map.get(vo.getYear())
                    .add(new TotalAmountSumBranchVO(vo.getBranchName(), vo.getBranchCode(), vo.getNetAmountSum()));
        }

        List<YearlyAmountSumBranchVO> resultList = new ArrayList<>();
        for (Map.Entry<Integer, List<TotalAmountSumBranchVO>> entry : map.entrySet()) {
            resultList.add(new YearlyAmountSumBranchVO(entry.getKey(), entry.getValue()));
        }
        return resultList;
    }

    public TotalAmountSumBranchVO getTotalAmountSumByBranch(String branchName) {

        final String BRANCH_BUNDANG = "분당점";
        if (BRANCH_BUNDANG.equals(branchName)) {
            throw new NotFoundException(ResourceNotFoundExceptionCode.BRANCH_NOT_FOUND);
        }

        Branch branch = branchRepository.findByBranchName(branchName)
                .orElseThrow(() -> new NotFoundException(ResourceNotFoundExceptionCode.BRANCH_NOT_FOUND));
        BigDecimal sum = statisticsRepository.getTotalSumByBranchCode(branch.getBranchCode())
                .orElse(BigDecimal.ZERO);

        return new TotalAmountSumBranchVO(branch.getBranchName(), branch.getBranchCode(), sum);
    }
}
