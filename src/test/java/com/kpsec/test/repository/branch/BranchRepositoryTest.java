package com.kpsec.test.repository.branch;

import com.kpsec.test.domain.entity.Branch;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class BranchRepositoryTest {

    @Autowired
    BranchRepository branchRepository;

    @Test
    void findByBranchCode() {

        // given
        String branchCode = "A";
        String branchName = "판교점";

        // when
        Branch branch = branchRepository.findByBranchCode(branchCode).get();

        // then
        Assertions.assertThat(branch).isNotNull();
        Assertions.assertThat(branch.getBranchCode()).isEqualTo(branchCode);
        Assertions.assertThat(branch.getBranchName()).isEqualTo(branchName);
    }

    @Test
    void findByBranchName() {

        // given
        String branchCode = "E";
        String branchName = "을지로점";

        // when
        Branch branch = branchRepository.findByBranchCode(branchCode).get();

        // then
        Assertions.assertThat(branch).isNotNull();
        Assertions.assertThat(branch.getBranchCode()).isEqualTo(branchCode);
        Assertions.assertThat(branch.getBranchName()).isEqualTo(branchName);
    }
}