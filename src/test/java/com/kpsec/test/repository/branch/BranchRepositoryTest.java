package com.kpsec.test.repository.branch;

import com.kpsec.test.domain.entity.Branch;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class BranchRepositoryTest {

    @Autowired
    private BranchRepository branchRepository;

    private Branch givenBranch;

    @BeforeEach
    void setUp() {
        // given
        givenBranch = Branch.builder()
                .branchCode("Q")
                .branchName("test점")
                .build();
        branchRepository.save(givenBranch);
    }

    @Test
    void findByBranchCodeTest() {

        // when
        Branch branch = branchRepository.findByBranchCode(givenBranch.getBranchCode()).get();

        // then
        Assertions.assertThat(branch).isNotNull();
        Assertions.assertThat(branch.getBranchCode()).isEqualTo(givenBranch.getBranchCode());
        Assertions.assertThat(branch.getBranchName()).isEqualTo(givenBranch.getBranchName());
    }

    @Test
    void findByBranchNameTest() {

        // when
        Branch branch = branchRepository.findByBranchName(givenBranch.getBranchName()).get();

        // then
        Assertions.assertThat(branch).isNotNull();
        Assertions.assertThat(branch.getBranchCode()).isEqualTo(givenBranch.getBranchCode());
        Assertions.assertThat(branch.getBranchName()).isEqualTo(givenBranch.getBranchName());
    }
}