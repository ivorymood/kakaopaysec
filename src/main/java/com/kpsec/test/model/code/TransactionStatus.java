package com.kpsec.test.model.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TransactionStatus {
    COMPLETED("N"),
    CANCELED("Y");

    private final String value;
}
