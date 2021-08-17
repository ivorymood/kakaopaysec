package com.kpsec.test.contoller;

import com.kpsec.test.service.branch.BranchService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Branch")
@RestController
@RequestMapping("/test/branches")
public class BranchController {

    @Autowired
    private BranchService branchService;
}
