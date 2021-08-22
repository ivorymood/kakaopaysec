package com.kpsec.test.service.statistics.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class YearDTO {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy", timezone = "Asia/Seoul")
    private String year;
}
