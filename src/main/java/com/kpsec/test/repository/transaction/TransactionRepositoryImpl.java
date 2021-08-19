package com.kpsec.test.repository.transaction;

import com.kpsec.test.domain.code.CancelStatus;
import com.kpsec.test.domain.entity.QAccount;
import com.kpsec.test.domain.entity.QBranch;
import com.kpsec.test.domain.entity.QTransaction;
import com.kpsec.test.domain.entity.Transaction;
import com.kpsec.test.vo.QTransactionStatisticsVO;
import com.kpsec.test.vo.TransactionStatisticsVO;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class TransactionRepositoryImpl extends QuerydslRepositorySupport implements TransactionRepositoryCustom {

    private final QTransaction transaction;
    private final QAccount account;
    private final QBranch branch;

    public TransactionRepositoryImpl() {
        super(Transaction.class);
        transaction = new QTransaction("transaction");
        account = new QAccount("account");
        branch = new QBranch("branch");
    }

    @Override
    public List<TransactionStatisticsVO> getYearlyNetAmountSumByAccounts() {

        JPAQueryFactory query = new JPAQueryFactory(this.getEntityManager());

        JPAQuery<TransactionStatisticsVO> jpaQuery = query.select(
                new QTransactionStatisticsVO(
                        transaction.date.substring(0, 4),
                        branch.branchCode,
                        account.accountNo,
                        transaction.amount.subtract(transaction.fee).sum()
                ))
                .from(transaction)
                .where(transaction.cancelStatus.eq(CancelStatus.N))
                .leftJoin(account).on(account.accountNo.eq(transaction.account.accountNo))
                .leftJoin(branch).on(branch.branchCode.eq(account.branch.branchCode))
                .groupBy(transaction.date.substring(0, 4), transaction.account);

        return jpaQuery.fetch();
    }
}
