package com.kpsec.test.service.statistics.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AmountSumBranchVO {
    private String brName;
    private String brCode;
    private BigDecimal sumAmt;
}
