package com.scrh.money.common.exception;

/**
 * @author ScrH0052
 * @date 2021/8/11
 */
public class BidException extends RuntimeException {

    public BidException(ExceptionEnum exceptionEnum) {
        super(exceptionEnum.getMessage());
    }
}
