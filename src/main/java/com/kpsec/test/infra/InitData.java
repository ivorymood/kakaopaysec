package com.kpsec.test.infra;

import com.kpsec.test.domain.code.CancelStatus;
import com.kpsec.test.domain.entity.Account;
import com.kpsec.test.domain.entity.Branch;
import com.kpsec.test.domain.entity.Statistics;
import com.kpsec.test.domain.entity.Transaction;
import com.kpsec.test.exception.NotFoundException;
import com.kpsec.test.repository.account.AccountRepository;
import com.kpsec.test.repository.branch.BranchRepository;
import com.kpsec.test.repository.statistics.StatisticsRepository;
import com.kpsec.test.repository.transaction.TransactionRepository;
import com.kpsec.test.repository.transaction.vo.TransactionYearlyAmountSumAccountVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

import com.kpsec.test.exception.NotFoundException.ResourceNotFoundExceptionCode;

@Component
public class InitData {

    @Autowired
    BranchRepository branchRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    StatisticsRepository statisticsRepository;

    @PostConstruct
    private void initBranch() throws IOException {
        if (branchRepository.count() == 0) {
            Resource resource = new ClassPathResource("branch.csv");
            List<Branch> branchList = Files.readAllLines(resource.getFile().toPath(), StandardCharsets.UTF_8)
                    .stream().skip(1).map(line -> {
                        String[] split = line.split(",");
                        return Branch.builder()
                                .branchCode(split[0])
                                .branchName(split[1])
                                .build();
                    }).collect(Collectors.toList());

            // 판교점 분당점 통폐합
            branchRepository.saveAll(branchList);
            Branch branch_b = branchRepository.findByBranchName("분당점")
                    .orElseThrow(() -> new NotFoundException(ResourceNotFoundExceptionCode.BRANCH_NOT_FOUND));

            Branch branch_p = branchRepository.findByBranchName("판교점")
                    .orElseThrow(() -> new NotFoundException(ResourceNotFoundExceptionCode.BRANCH_NOT_FOUND));

            branch_b.setMergedTo(branch_p.getBranchCode());
            branchRepository.save(branch_b);
        }
    }

    @PostConstruct
    private void initAccount() throws IOException {
        if (accountRepository.count() == 0) {
            Resource resource = new ClassPathResource("account.csv");
            List<Account> accountList = Files.readAllLines(resource.getFile().toPath(), StandardCharsets.UTF_8)
                    .stream().skip(1).map(line -> {
                        String[] split = line.split(",");
                        Branch branch = branchRepository.findByBranchCode(split[2])
                                .orElseThrow(null);

                        return Account.builder()
                                .accountNo(split[0])
                                .accountName(split[1])
                                .branchCode(split[2])
                                .branch(branch)
                                .build();
                    }).collect(Collectors.toList());
            accountRepository.saveAll(accountList);
        }
    }

    @PostConstruct
    private void initTransaction() throws IOException {
        if (transactionRepository.count() == 0) {
            Resource resource = new ClassPathResource("transaction.csv");
            List<Transaction> transactionList = Files.readAllLines(resource.getFile().toPath(), StandardCharsets.UTF_8)
                    .stream().skip(1).map(line -> {
                        String[] split = line.split(",");
                        Account account = accountRepository.findByAccountNo(split[1])
                                .orElseThrow(null);

                        return Transaction.builder()
                                .date(split[0])
                                .accountNo(split[1])
                                .transactionNo(split[2])
                                .amount(new BigDecimal(split[3]))
                                .fee(new BigDecimal(split[4]))
                                .cancelStatus(split[5].equals("Y")
                                                ? CancelStatus.Y
                                                : CancelStatus.N)
                                .account(account)
                                .build();
                    }).collect(Collectors.toList());
            transactionRepository.saveAll(transactionList);
        }
    }

    @PostConstruct
    private void initStatistics() {
        List<TransactionYearlyAmountSumAccountVO> transactionStatisticsList = transactionRepository.getYearlyNetAmountSumByAccounts();

        List<Statistics> statisticsList = transactionStatisticsList.stream().map(vo -> {

            Account account = accountRepository.findByAccountNo(vo.getAcctNo())
                    .orElseThrow(null);
            Branch branch = branchRepository.findByBranchCode(vo.getBrCode())
                    .orElseThrow(() -> new NotFoundException(ResourceNotFoundExceptionCode.BRANCH_NOT_FOUND));

            return Statistics.builder()
                    .year(vo.getYear())
                    .branchCode(vo.getBrCode())
                    .accountNo(vo.getAcctNo())
                    .netAmountSum(vo.getSumAmt())
                    .account(account)
                    .branch(branch)
                    .build();
        }).collect(Collectors.toList());
        statisticsRepository.saveAll(statisticsList);
    }
}
