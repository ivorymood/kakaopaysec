package com.kpsec.test.repository.statistics.vo;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@Getter
public class StatisticsYearlyAmountSumBranchVO {
    private Integer year;
    private String branchName;
    private String branchCode;
    private BigDecimal netAmountSum;

    @QueryProjection
    public StatisticsYearlyAmountSumBranchVO(Integer year, String branchName, String branchCode, BigDecimal netAmountSum) {
        this.year = year;
        this.branchName = branchName;
        this.branchCode = branchCode;
        this.netAmountSum = netAmountSum;
    }
}
