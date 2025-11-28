package io.metarpc.mt5.exceptions;

import mt5_term_api.MrpcMt5Error;

/**
 * Exception for MT5 API errors
 */
public class ApiExceptionMT5 extends Exception {
    private final MrpcMt5Error.Error error;

    public ApiExceptionMT5(MrpcMt5Error.Error error) {
        super(error.getErrorMessage());
        this.error = error;
    }

    /**
     * Constructor for creating exception with custom message
     */
    public ApiExceptionMT5(String message) {
        super(message);
        this.error = null;
    }

    public MrpcMt5Error.Error getError() {
        return error;
    }

    public String getErrorCode() {
        return error != null ? error.getErrorCode() : "UNKNOWN";
    }

    public String getErrorMessage() {
        return error != null ? error.getErrorMessage() : getMessage();
    }

    @Override
    public String toString() {
        return "ApiExceptionMT5{" +
                "errorCode='" + getErrorCode() + '\'' +
                ", errorMessage='" + getErrorMessage() + '\'' +
                '}';
    }
}
