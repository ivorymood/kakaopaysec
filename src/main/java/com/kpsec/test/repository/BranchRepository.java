package com.kpsec.test.repository;

import com.kpsec.test.domain.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BranchRepository extends JpaRepository<Branch, Long> {

    Optional<Branch> findByBranchCode(String branchCode);
}
