package com.kpsec.test.repository.account;

import com.kpsec.test.domain.entity.Transaction;
import com.kpsec.test.vo.StatisticsVO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AccountRepositoryCustom {

    @Query(value =
            " select t.year, a.account_name name, t.account_no acctNo, t.net_amount_sum sumAmt " +
                    " from (select t1.* " +
                    " 	from (select t0.*, rank() over(partition by year order by net_amount_sum desc) as rank " +
                    " 		from (select substring(date, 0, 4) year, account_no, sum(amount - fee) as net_amount_sum " +
                    " 			from transaction " +
                    " 			where canceled = 'N' " +
                    " 			and substring(date, 0, 4) in (:years) " +
                    " 			group by year, account_no " +
                    " 		) t0 " +
                    " 	) t1 " +
                    " 	where rank = 1 " +
                    " ) t left join account a on t.account_no = a.account_no " +
                    " order by year ",
            nativeQuery = true
    )
    List<StatisticsVO> findAllYearlyAmountAccounts(@Param("years") List<String> years);

    @Query(value = "select t.account_name from transaction t", nativeQuery = true)
    List<Transaction> getTransactionList();

}
