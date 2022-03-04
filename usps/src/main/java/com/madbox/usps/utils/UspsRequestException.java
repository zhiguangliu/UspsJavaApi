/*******************************************************
 * Copyright (C) 2012-2019 Adam Dale adamdale2018@gmail.com
 *
 * This file is part of Madbox Solutions.
 *
 * USPS Java Api can not be copied and/or distributed without the express
 * permission of Madbox Solutions.
 *******************************************************/
package com.madbox.usps.utils;

/**
 * @author zhgliu
 */
public class UspsRequestException extends Exception {

    private Error error;


    private static final long serialVersionUID = -1290121043253989500L;

    public UspsRequestException() {
    }

    public UspsRequestException(Error error) {
        this.error = error;
    }

    public UspsRequestException(Throwable cause) {
        super(cause);
    }

    public UspsRequestException(String message, Error error) {
        super(message);
        this.error = error;
    }

    public UspsRequestException(String message, Throwable cause, Error error) {
        super(message, cause);
        this.error = error;
    }

    public UspsRequestException(Throwable cause, Error error) {
        super(cause);
        this.error = error;
    }

    public UspsRequestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Error error) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.error = error;
    }

    public Error getError() {
        return error;
    }

    public UspsRequestException setError(Error error) {
        this.error = error;
        return this;
    }
}
