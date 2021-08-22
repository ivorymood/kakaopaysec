package com.kpsec.test.repository.transaction;

import com.kpsec.test.vo.YearlyAmountSumBranchAccountVO;

import java.util.List;

public interface TransactionRepositoryCustom {

    List<YearlyAmountSumBranchAccountVO> getYearlyNetAmountSumByAccounts();
}
