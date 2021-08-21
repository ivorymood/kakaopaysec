package com.kpsec.test.repository.statistics;

import com.kpsec.test.repository.statistics.vo.StatisticsYearlyAmountSumBranchVO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface StatisticsRepositoryCustom {

    List<StatisticsYearlyAmountSumBranchVO> getYearlyNetAmountSumByBranch();

    Optional<BigDecimal> getTotalSumByBranchCode(String branchCode);
}
