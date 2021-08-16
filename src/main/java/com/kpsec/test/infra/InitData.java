package com.kpsec.test.infra;

import com.kpsec.test.model.code.TransactionStatus;
import com.kpsec.test.model.entity.Account;
import com.kpsec.test.model.entity.Branch;
import com.kpsec.test.model.entity.Transaction;
import com.kpsec.test.repository.AccountRepository;
import com.kpsec.test.repository.BranchRepository;
import com.kpsec.test.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class InitData {

    @Autowired
    BranchRepository branchRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TransactionRepository transactionRepository;

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
            branchRepository.saveAll(branchList);
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
                                .branch(branch)
                                .build();
                    }).collect(Collectors.toList());
            accountRepository.saveAll(accountList);
        }
    }

    @PostConstruct
    private void initTransactionHistory() throws IOException {
        if (transactionRepository.count() == 0) {
            Resource resource = new ClassPathResource("transaction.csv");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            List<Transaction> transactionList = Files.readAllLines(resource.getFile().toPath(), StandardCharsets.UTF_8)
                    .stream().skip(1).map(line -> {
                        String[] split = line.split(",");
                        Account account = accountRepository.findByAccountNo(split[1])
                                .orElseThrow(null);

                        return Transaction.builder()
                                .date(LocalDate.parse(split[0], formatter))
                                .account(account)
                                .transactionNo(Long.parseLong(split[2]))
                                .amount(new BigDecimal(split[3]))
                                .fee(new BigDecimal(split[4]))
                                .transactionStatus(
                                        split[5].equals("Y")
                                                ? TransactionStatus.CANCELED
                                                : TransactionStatus.COMPLETED)
                                .build();
                    }).collect(Collectors.toList());
            transactionRepository.saveAll(transactionList);
        }
    }
}
