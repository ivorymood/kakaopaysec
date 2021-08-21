package com.kpsec.test.repository.statistics;

import com.kpsec.test.domain.entity.Statistics;
import com.kpsec.test.repository.statistics.vo.StatisticsVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StatisticsRepository extends JpaRepository<Statistics, Long>, StatisticsRepositoryCustom {

    @Query(value = "select s.year, a.account_name name, s.account_no acctNo, s.net_amount_sum sumAmt " +
                    "from ( " +
                    "    select year, account_no, net_amount_sum, rank() over(partition by year order by net_amount_sum desc) rank " +
                    "    from statistics " +
                    "    where year in (:years) " +
                    ") s " +
                    "left join account a on s.account_no = a.account_no " +
                    "where rank = 1 " +
                    "order by year, rank ",
            nativeQuery = true)
    List<StatisticsVO> findAllYearlyAmountAccounts(@Param("years") List<String> years);

    List<Statistics> findAllByYearIsInOrderByYear(@Param("years") List<Integer> years);
}
