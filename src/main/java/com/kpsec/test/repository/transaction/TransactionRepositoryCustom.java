package com.kpsec.test.repository.transaction;

import com.kpsec.test.repository.transaction.vo.TransactionYearlyAmountSumAccountVO;

import java.util.List;

public interface TransactionRepositoryCustom {

    List<TransactionYearlyAmountSumAccountVO> getYearlyNetAmountSumByAccounts();
}
