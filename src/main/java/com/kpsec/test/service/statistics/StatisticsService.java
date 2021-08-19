package com.kpsec.test.service.statistics;

import com.kpsec.test.repository.statistics.StatisticsRepository;
import com.kpsec.test.service.statistics.dto.YearDTO;
import com.kpsec.test.vo.StatisticsInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class StatisticsService {

    @Autowired
    private StatisticsRepository statisticsRepository;

    public List<StatisticsInterface> getYearlyTopAmountAccounts(List<YearDTO> dtoList) {

        List<String> yearList = new ArrayList<>();
        for (YearDTO dto : dtoList) {
            yearList.add(dto.getYear());
        }

        return statisticsRepository.findAllYearlyAmountAccounts(yearList);
    }
}
