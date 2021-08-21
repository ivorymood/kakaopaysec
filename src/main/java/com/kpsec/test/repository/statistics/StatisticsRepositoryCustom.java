package com.kpsec.test.repository.statistics;

import com.kpsec.test.repository.statistics.vo.StatisticsYearlyAmountSumBranchVO;

import java.math.BigDecimal;
import java.util.List;

public interface StatisticsRepositoryCustom {

    List<StatisticsYearlyAmountSumBranchVO> getYearlyNetAmountSumByBranch();

    BigDecimal getTotalSumByBranchName(String branchCode);
}
