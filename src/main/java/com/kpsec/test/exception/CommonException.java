package com.kpsec.test.exception;


import lombok.Getter;

@Getter
public abstract class CommonException extends RuntimeException {

  private ResponseCode responseCode;

  public CommonException(ResponseCode responseCode) {
    super(responseCode.getMessage());
    this.responseCode = responseCode;
  }
}
