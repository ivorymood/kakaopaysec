package com.kpsec.test.repository.transaction.vo;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@Getter
public class TransactionYearlyAmountSumAccountVO {
    private Integer year;
    private String brCode;
    private String acctNo;
    private BigDecimal sumAmt;

    @QueryProjection
    public TransactionYearlyAmountSumAccountVO(Integer year, String brCode, String acctNo, BigDecimal sumAmt) {
        this.year = year;
        this.brCode = brCode;
        this.acctNo = acctNo;
        this.sumAmt = sumAmt;
    }
}
