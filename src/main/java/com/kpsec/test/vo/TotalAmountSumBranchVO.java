package com.kpsec.test.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TotalAmountSumBranchVO {
    private String brName;
    private String brCode;
    private BigDecimal sumAmt;
}
