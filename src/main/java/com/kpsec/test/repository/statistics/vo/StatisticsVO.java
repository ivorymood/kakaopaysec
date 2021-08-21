package com.kpsec.test.repository.statistics.vo;

import java.math.BigDecimal;

public interface StatisticsVO {

    Integer getYear();
    String getName();
    String getAcctNo();
    BigDecimal getSumAmt();
}
