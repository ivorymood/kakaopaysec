package com.kpsec.test.vo;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@Getter
public class YearlyAmountSumBranchVO {
    private Integer year;
    private String branchName;
    private String branchCode;
    private BigDecimal netAmountSum;

    @QueryProjection
    public YearlyAmountSumBranchVO(Integer year, String branchName, String branchCode, BigDecimal netAmountSum) {
        this.year = year;
        this.branchName = branchName;
        this.branchCode = branchCode;
        this.netAmountSum = netAmountSum;
    }
}
