package com.kpsec.test.repository.transaction;

import com.kpsec.test.vo.TransactionStatisticsVO;

import java.util.List;

public interface TransactionRepositoryCustom {

    List<TransactionStatisticsVO> getYearlyNetAmountSumByAccounts();
}
