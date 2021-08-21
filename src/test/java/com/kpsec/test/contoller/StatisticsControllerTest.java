package com.kpsec.test.contoller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kpsec.test.service.statistics.StatisticsService;
import com.kpsec.test.service.statistics.vo.TotalAmountSumBranchVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.inject.Inject;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@Import(StatisticsController.class)
@WebMvcTest(StatisticsController.class)
class StatisticsControllerTest {

    private final String URI_HEADER = "/test/statistics";

    @Inject
    WebApplicationContext wac;

    private MockMvc mockMvc;

    @MockBean
    private StatisticsService statisticsService;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(this.wac)
                .addFilter(new CharacterEncodingFilter("utf-8"))
                .build();
    }

    @Test
    @DisplayName("Q1) 2018년, 2019년 각 연도별 합계 금액이 가장 많은 고객을 추출하는 API")
    void getYearlyTopAmountAccounts() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders
                .get(URI_HEADER +"/yearly-top-amount-accounts"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(handler().handlerType(StatisticsController.class))
                .andExpect(handler().methodName("getYearlyTopAmountAccounts"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
    @DisplayName("Q2) 2018년 또는 2019년에 거래가 없는 고객을 추출하는 API")
    void getYearlyNonTransactionAccounts() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders
                .get(URI_HEADER +"/yearly-non-transaction-accounts"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(handler().handlerType(StatisticsController.class))
                .andExpect(handler().methodName("getYearlyNonTransactionAccounts"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
    @DisplayName("Q3) 연도별 관리점별 거래금액 합계를 구하고 합계금액이 큰 순서로 출력하는 API")
    void getYearlyAmountSumByBranch() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders
                .get(URI_HEADER +"/yearly-amount-sum-by-branch"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(handler().handlerType(StatisticsController.class))
                .andExpect(handler().methodName("getYearlyAmountSumByBranch"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
    @DisplayName("Q4) 지점명을 입력하면 해당지점의 거래금액 합계를 출력하는 API")
    void getTotalAmountSumByBranch() throws Exception {

        given(statisticsService.getTotalAmountSumByBranch(Mockito.anyString()))
        .willAnswer(invocationOnMock -> new TotalAmountSumBranchVO());

        this.mockMvc.perform(MockMvcRequestBuilders
                .get(URI_HEADER +"/total-amount-sum-by-branch")
                    .param("brName", "판교점")
                    .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(handler().handlerType(StatisticsController.class))
                .andExpect(handler().methodName("getTotalAmountSumByBranch"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }
}