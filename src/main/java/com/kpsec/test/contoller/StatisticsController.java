package com.kpsec.test.contoller;


import com.kpsec.test.service.statistics.StatisticsService;
import com.kpsec.test.service.statistics.dto.YearDTO;
import com.kpsec.test.repository.statistics.vo.StatisticsInterface;
import com.kpsec.test.service.statistics.vo.YearlyNonTransactionAccountVO;
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

@Api(tags = "Statistics")
@RestController
@RequestMapping("/test/statistics")
public class StatisticsController {

    @Autowired
    private StatisticsService statisticsService;

    /*
        Q1. 2018년, 2019년 각 연도별 합계 금액이 가장 많은 고객을 추출하는 API 개발.
     */
    @ApiOperation(value = "2018, 2019 yearly top amount accounts")
    @PostMapping(value = "/yearly-top-amount-accounts",
            // consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<StatisticsInterface> getYearlyTopAmountAccounts(
           // @RequestBody @Valid List<YearDTO> list
            ) {

        String[] testYears = {"2018", "2019"};
        List<YearDTO> list = new ArrayList<>();
        for (String year : testYears) {
            YearDTO dto = new YearDTO();
            dto.setYear(year);
            list.add(dto);
        }
        return statisticsService.getYearlyTopAmountAccounts(list);
    }

    /*
    Q2. 2018년 또는 2019년에 거래가 없는 고객을 추출하는 API 개발.
    */
    @ApiOperation(value = "2018, 2019 yearly non-transaction accounts")
    @PostMapping(value = "/yearly-non-transaction-accounts",
            // consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<YearlyNonTransactionAccountVO> getYearlyNonTransactionAccounts(
            // @RequestBody @Valid List<YearDTO> list
    ) {
        String[] testYears = {"2018", "2019"};
        List<YearDTO> list = new ArrayList<>();
        for (String year : testYears) {
            YearDTO dto = new YearDTO();
            dto.setYear(year);
            list.add(dto);
        }

        return statisticsService.getYearlyNonTransactionAccounts(list);
    }
}
