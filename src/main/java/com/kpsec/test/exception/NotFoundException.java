package com.kpsec.test.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class NotFoundException extends CommonException {

    @AllArgsConstructor
    @Getter
    public enum ResourceNotFoundExceptionCode implements ResponseCode {

        BRANCH_NOT_FOUND("404", "br code not found error")
        ;

        private String code;
        private String message;
    }

    public NotFoundException(ResponseCode responseCode) {
        super(responseCode);
    }
}
