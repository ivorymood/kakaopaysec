package com.kpsec.test.repository.transaction;

import com.kpsec.test.repository.transaction.vo.YearlyTransactionAccountVO;

import java.util.List;

public interface TransactionRepositoryCustom {

    List<YearlyTransactionAccountVO> getYearlyNetAmountSumByAccounts();
}
