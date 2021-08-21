package com.kpsec.test.repository.branch;

import com.kpsec.test.domain.entity.Branch;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class BranchRepositoryTest {

    @Autowired
    BranchRepository branchRepository;

    @Test
    void findByBranchCode() {

        // given
        Branch givenBranch = Branch.builder()
                .branchCode("Q")
                .branchName("test점")
                .build();
        branchRepository.save(givenBranch);

        // when
        Branch branch = branchRepository.findByBranchCode(givenBranch.getBranchCode()).get();

        // then
        Assertions.assertThat(branch).isNotNull();
        Assertions.assertThat(branch.getBranchCode()).isEqualTo(givenBranch.getBranchCode());
        Assertions.assertThat(branch.getBranchName()).isEqualTo(givenBranch.getBranchName());
    }

    @Test
    void findByBranchName() {

        // given
        Branch givenBranch = Branch.builder()
                .branchCode("Q")
                .branchName("test점")
                .build();
        branchRepository.save(givenBranch);

        // when
        Branch branch = branchRepository.findByBranchName(givenBranch.getBranchName()).get();

        // then
        Assertions.assertThat(branch).isNotNull();
        Assertions.assertThat(branch.getBranchCode()).isEqualTo(givenBranch.getBranchCode());
        Assertions.assertThat(branch.getBranchName()).isEqualTo(givenBranch.getBranchName());
    }
}