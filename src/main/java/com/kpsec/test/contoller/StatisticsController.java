package com.kpsec.test.contoller;


import com.kpsec.test.repository.statistics.vo.StatisticsVO;
import com.kpsec.test.service.statistics.StatisticsService;
import com.kpsec.test.service.statistics.dto.YearDTO;
import com.kpsec.test.service.statistics.vo.AmountSumBranchVO;
import com.kpsec.test.service.statistics.vo.NonTransactionAccountVO;
import com.kpsec.test.service.statistics.vo.YearlyAmountSumBranchVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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
    public List<StatisticsVO> getYearlyTopAmountAccounts(
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
    public List<NonTransactionAccountVO> getYearlyNonTransactionAccounts(
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


    /*
    Q3. 연도별 관리점별 거래금액 합계를 구하고 합계금액이 큰 순서로 출력하는 API 개발.
     */
    @ApiOperation(value = "year amount sum by branch")
    @GetMapping(value = "/yearly-amount-sum-by-branch", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<YearlyAmountSumBranchVO> getYearlyAmountSumByBranch() {

        return statisticsService.getYearlyAmountSumByBranch();
    }

    /*
    Q4. 분당점과 판교점을 통폐합하여 판교점으로 관리점 이관을 하였습니다. 지점명을 입력하면 해당지점의 거래금액 합계를 출력하는 API 개발
     */
    @ApiOperation(value = "amount sum by branch")
    @GetMapping(value = "/amount-sum-by-branch", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public AmountSumBranchVO getAmountSumByBranch(@RequestParam("brName") String branchName) {

        return statisticsService.getAmountSumByBranch(branchName);
    }
}
