package com.kpsec.test.repository.statistics;

import com.kpsec.test.repository.statistics.vo.StatisticsYearlyAmountSumByBranchVO;

import java.util.List;

public interface StatisticsRepositoryCustom {

    List<StatisticsYearlyAmountSumByBranchVO> getYearlyNetAmountSumByAccounts();
}
