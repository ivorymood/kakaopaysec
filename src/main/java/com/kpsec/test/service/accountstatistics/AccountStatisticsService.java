package com.kpsec.test.service.accountstatistics;

import com.kpsec.test.repository.account.AccountRepository;
import com.kpsec.test.service.accountstatistics.dto.YearDTO;
import com.kpsec.test.vo.AccountAmountStatisticsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AccountStatisticsService {

    @Autowired
    private AccountRepository accountRepository;

    public List<AccountAmountStatisticsVO> getYearlyTopAmountAccounts(List<YearDTO> dtoList) {

        List<String> yearList = new ArrayList<>();
        for (YearDTO dto : dtoList) {
            yearList.add(dto.getYear());
        }

        return accountRepository.findAllYearlyAmountAccounts(yearList);
    }
}
