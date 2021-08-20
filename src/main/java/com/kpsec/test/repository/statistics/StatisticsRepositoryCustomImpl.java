package com.kpsec.test.repository.statistics;

import com.kpsec.test.domain.entity.QBranch;
import com.kpsec.test.domain.entity.QStatistics;
import com.kpsec.test.domain.entity.Statistics;
import com.kpsec.test.repository.statistics.vo.QStatisticsYearlyAmountSumByBranchVO;
import com.kpsec.test.repository.statistics.vo.StatisticsYearlyAmountSumByBranchVO;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class StatisticsRepositoryCustomImpl extends QuerydslRepositorySupport implements StatisticsRepositoryCustom {

    private final QStatistics statistics;
    private final QBranch branch;

    public StatisticsRepositoryCustomImpl() {
        super(Statistics.class);
        statistics = new QStatistics("statistics");
        branch = new QBranch("branch");
    }

    @Override
    public List<StatisticsYearlyAmountSumByBranchVO> getYearlyNetAmountSumByAccounts() {

        JPAQueryFactory query = new JPAQueryFactory(this.getEntityManager());

        JPAQuery<StatisticsYearlyAmountSumByBranchVO> jpaQuery = query.select(
                new QStatisticsYearlyAmountSumByBranchVO(
                        statistics.year,
                        branch.branchName,
                        statistics.branchCode,
                        statistics.netAmountSum.sum()
                ))
                .from(statistics)
                .groupBy(statistics.year, statistics.branchCode, branch.branchName)
                .orderBy(statistics.year.asc(), statistics.netAmountSum.sum().desc());

        return jpaQuery.fetch();
    }
}
