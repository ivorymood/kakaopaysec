package com.kpsec.test.repository.statistics;

import com.kpsec.test.domain.entity.QBranch;
import com.kpsec.test.domain.entity.QStatistics;
import com.kpsec.test.domain.entity.Statistics;
import com.kpsec.test.repository.statistics.vo.QStatisticsYearlyAmountSumBranchVO;
import com.kpsec.test.repository.statistics.vo.StatisticsYearlyAmountSumBranchVO;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

public class StatisticsRepositoryCustomImpl extends QuerydslRepositorySupport implements StatisticsRepositoryCustom {

    private final JPAQueryFactory query;
    private final QStatistics statistics;
    private final QBranch branch;

    public StatisticsRepositoryCustomImpl(EntityManager entityManager) {
        super(Statistics.class);
        this.statistics = new QStatistics("statistics");
        this.branch = new QBranch("branch");
        this.query = new JPAQueryFactory(entityManager);
    }

    @Override
    public List<StatisticsYearlyAmountSumBranchVO> getYearlyNetAmountSumByBranch() {

        JPAQuery<StatisticsYearlyAmountSumBranchVO> jpaQuery = query.select(
                new QStatisticsYearlyAmountSumBranchVO(
                        statistics.year,
                        branch.branchName,
                        statistics.branchCode,
                        statistics.netAmountSum.sum()
                ))
                .from(statistics)
                .leftJoin(branch).on(statistics.branchCode.eq(branch.branchCode))
                .groupBy(statistics.year, statistics.branchCode, branch.branchName)
                .orderBy(statistics.year.asc(), statistics.netAmountSum.sum().desc());

        return jpaQuery.fetch();
    }

    @Override
    public BigDecimal getTotalSumByBranchName(String branchCode) {

        JPAQuery<BigDecimal> jpaQuery = query.select(statistics.netAmountSum.sum())
                .from(statistics)
                .where(statistics.branchCode
                        .in(query.select(branch.branchCode)
                                .from(branch)
                                .where(branch.branchCode.eq(branchCode)
                                        .or(branch.mergedTo.eq(branchCode)))));

        return jpaQuery.fetchFirst();
    }
}
