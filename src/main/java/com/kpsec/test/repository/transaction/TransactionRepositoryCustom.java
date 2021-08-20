package com.kpsec.test.repository.transaction;

import com.kpsec.test.repository.transaction.vo.TransactionYearlyAmountSumByAccountVO;

import java.util.List;

public interface TransactionRepositoryCustom {

    List<TransactionYearlyAmountSumByAccountVO> getYearlyNetAmountSumByAccounts();
}
