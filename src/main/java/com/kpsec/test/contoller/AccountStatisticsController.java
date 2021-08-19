package com.kpsec.test.contoller;


import com.kpsec.test.service.accountstatistics.AccountStatisticsService;
import com.kpsec.test.service.accountstatistics.dto.YearDTO;
import com.kpsec.test.vo.StatisticsVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Api(tags = "Account Statistics")
@RestController
@RequestMapping("/test/account-statistics")
public class AccountStatisticsController {

    @Autowired
    private AccountStatisticsService accountStatisticsService;

    /*
        Q1. 2018년, 2019년 각 연도별 합계 금액이 가장 많은 고객을 추출하는 API 개발.
     */
    @ApiOperation(value = "2018, 2019 yearly top amount accounts")
    @PostMapping(value = "/20182019-yearly-top-amount-accounts",
            // consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<StatisticsVO> get1819YearlyTopAmountAccounts(
           // @RequestBody @Valid List<YearDTO> list
            ) {

        String[] testYears = {"2018", "2019"};
        List<YearDTO> list = new ArrayList<>();
        for (String year : testYears) {
            YearDTO dto = new YearDTO();
            dto.setYear(year);
            list.add(dto);
        }
        return accountStatisticsService.getYearlyTopAmountAccounts(list);
    }
}
