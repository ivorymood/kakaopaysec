package com.kpsec.test.repository.statistics;

import com.kpsec.test.vo.YearlyAmountSumBranchVO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface StatisticsRepositoryCustom {

    List<YearlyAmountSumBranchVO> getYearlyNetAmountSumByBranch();

    Optional<BigDecimal> getTotalSumByBranchCode(String branchCode);
}
