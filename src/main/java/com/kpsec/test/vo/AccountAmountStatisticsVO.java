package com.kpsec.test.vo;

import java.math.BigDecimal;

public interface AccountAmountStatisticsVO {

    int getYear();
    String getName();
    String getAcctNo();
    BigDecimal getSumAmt();
}
