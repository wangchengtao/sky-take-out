package com.summer.execption;

public class AccountNotFoundException extends BaseException {

    public AccountNotFoundException(String message) {
        super(message);
    }

    public AccountNotFoundException() {
    }
}
