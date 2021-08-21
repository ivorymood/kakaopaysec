package com.kpsec.test.service.statistics.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class YearDTO {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy", timezone = "Asia/Seoul")
    String year;
}
