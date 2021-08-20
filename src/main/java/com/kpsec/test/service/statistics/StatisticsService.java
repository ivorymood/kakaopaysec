package com.kpsec.test.service.statistics;

import com.kpsec.test.domain.entity.Account;
import com.kpsec.test.domain.entity.Statistics;
import com.kpsec.test.repository.account.AccountRepository;
import com.kpsec.test.repository.statistics.StatisticsRepository;
import com.kpsec.test.service.statistics.dto.YearDTO;
import com.kpsec.test.service.statistics.vo.YearlyNonTransactionAccountVO;
import com.kpsec.test.repository.statistics.vo.StatisticsInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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

    public List<YearlyNonTransactionAccountVO> getYearlyNonTransactionAccounts(List<YearDTO> dtoList) {

        List<String> yearList = new ArrayList<>();
        for (YearDTO dto : dtoList) {
            yearList.add(dto.getYear());
        }

        List<Statistics> statisticsList = statisticsRepository.findAllByYearIsInOrderByYear(yearList);
        List<Account> accountList = accountRepository.findAll();

        List<YearlyNonTransactionAccountVO> resultList = new ArrayList<>();
        for (String year : yearList) {
            for (Account account : accountList) {

                long count = statisticsList.stream().parallel()
                        .filter(s -> year.equals(s.getYear())
                                && account.getAccountNo().equals(s.getAccount().getAccountNo()))
                        .count();

                if (count == 0) {
                    resultList.add(new YearlyNonTransactionAccountVO(year, account.getAccountName(), account.getAccountNo()));
                }
            }
        }
        return resultList;
    }
}
