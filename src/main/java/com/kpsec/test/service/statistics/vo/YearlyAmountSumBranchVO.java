package com.kpsec.test.service.statistics.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class YearlyAmountSumBranchVO {

    private Integer year;
    private List<TotalAmountSumBranchVO> dataList;
}
