package com.kpsec.test.service.branch;

import com.kpsec.test.repository.branch.BranchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BranchService {

    @Autowired
    private BranchRepository branchRepository;
}
