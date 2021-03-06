package com.kpsec.test.contoller;


import com.kpsec.test.vo.YearlyAmountSumAccountVO;
import com.kpsec.test.service.statistics.StatisticsService;
import com.kpsec.test.service.statistics.dto.YearDTO;
import com.kpsec.test.vo.TotalAmountSumBranchVO;
import com.kpsec.test.vo.YearlyAccountVO;
import com.kpsec.test.vo.YearlyAmountSumBranchListVO;
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

        연도 리스트를 받아올수 있도록 하는 경우)
        어노테이션 변경 :
        @PostMapping(value = "/yearly-top-amount-accounts",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)

        파라미터 추가 :
        @RequestBody @Valid List<YearDTO> list
     */
    @ApiOperation(value = "2018, 2019 yearly top amount accounts")
    @GetMapping(value = "/yearly-top-amount-accounts",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<YearlyAmountSumAccountVO> getYearlyTopAmountAccounts() {

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

        연도 리스트를 받아올수 있도록 하는 경우)
        어노테이션 변경 :
        @PostMapping(value = "/yearly-non-transaction-accounts",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)

        파라미터 추가 :
        @RequestBody @Valid List<YearDTO> list
    */
    @ApiOperation(value = "2018, 2019 yearly non-transaction accounts")
    @GetMapping(value = "/yearly-non-transaction-accounts",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<YearlyAccountVO> getYearlyNonTransactionAccounts() {

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
    public List<YearlyAmountSumBranchListVO> getYearlyAmountSumByBranch() {

        return statisticsService.getYearlyAmountSumByBranch();
    }

    /*
        Q4. 분당점과 판교점을 통폐합하여 판교점으로 관리점 이관을 하였습니다. 지점명을 입력하면 해당지점의 거래금액 합계를 출력하는 API 개발
     */
    @ApiOperation(value = "total amount sum by branch")
    @GetMapping(value = "/total-amount-sum-by-branch", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public TotalAmountSumBranchVO getTotalAmountSumByBranch(@RequestParam("brName") String branchName) {

        return statisticsService.getTotalAmountSumByBranch(branchName);
    }
}
