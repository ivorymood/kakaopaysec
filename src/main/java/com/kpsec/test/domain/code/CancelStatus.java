package com.kpsec.test.domain.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CancelStatus {
    N("거래 완료"),
    Y("거래 취소");

    public final String description;
}
