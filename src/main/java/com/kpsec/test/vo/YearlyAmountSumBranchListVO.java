package com.kpsec.test.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class YearlyAmountSumBranchListVO {

    private Integer year;
    private List<TotalAmountSumBranchVO> dataList;
}
