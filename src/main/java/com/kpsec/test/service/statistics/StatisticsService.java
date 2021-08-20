package com.kpsec.test.service.statistics;

import com.kpsec.test.domain.entity.Account;
import com.kpsec.test.domain.entity.Statistics;
import com.kpsec.test.repository.account.AccountRepository;
import com.kpsec.test.repository.statistics.StatisticsRepository;
import com.kpsec.test.repository.statistics.vo.StatisticsInterface;
import com.kpsec.test.repository.statistics.vo.StatisticsYearlyAmountSumByBranchVO;
import com.kpsec.test.service.statistics.dto.YearDTO;
import com.kpsec.test.service.statistics.vo.AmountSumBranchVO;
import com.kpsec.test.service.statistics.vo.NonTransactionAccountVO;
import com.kpsec.test.service.statistics.vo.YearlyAmountSumBranchVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class StatisticsService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private StatisticsRepository statisticsRepository;

    public List<StatisticsInterface> getYearlyTopAmountAccounts(List<YearDTO> dtoList) {

        List<String> yearList = new ArrayList<>();
        for (YearDTO dto : dtoList) {
            yearList.add(dto.getYear());
        }

        return statisticsRepository.findAllYearlyAmountAccounts(yearList);
    }

    public List<NonTransactionAccountVO> getYearlyNonTransactionAccounts(List<YearDTO> dtoList) {

        List<String> yearList = new ArrayList<>();
        for (YearDTO dto : dtoList) {
            yearList.add(dto.getYear());
        }

        List<Statistics> statisticsList = statisticsRepository.findAllByYearIsInOrderByYear(yearList);
        List<Account> accountList = accountRepository.findAll();

        List<NonTransactionAccountVO> resultList = new ArrayList<>();
        for (String strYear : yearList) {
            int year = Integer.parseInt(strYear);
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

        List<StatisticsYearlyAmountSumByBranchVO> statisticsList = statisticsRepository.getYearlyNetAmountSumByAccounts();

        Map<Integer, List<AmountSumBranchVO>> map = new HashMap<>();
        for (StatisticsYearlyAmountSumByBranchVO vo : statisticsList) {
            if (!map.containsKey(vo.getYear())) {
                map.put(vo.getYear(), new ArrayList<>());
            }
            map.get(vo.getYear())
                    .add(new AmountSumBranchVO(vo.getBranchName(), vo.getBranchCode(), vo.getNetAmountSum()));
        }

        List<YearlyAmountSumBranchVO> resultList = new ArrayList<>();
        for (Map.Entry<Integer, List<AmountSumBranchVO>> entry : map.entrySet()) {
            resultList.add(new YearlyAmountSumBranchVO(entry.getKey(), entry.getValue()));
        }

        return resultList;
    }
}
