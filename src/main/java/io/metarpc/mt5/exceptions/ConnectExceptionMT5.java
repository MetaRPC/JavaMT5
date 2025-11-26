package io.metarpc.mt5.exceptions;

/**
 * Exception for MT5 connection errors
 */
public class ConnectExceptionMT5 extends RuntimeException {
    public ConnectExceptionMT5(String message) {
        super(message);
    }
}
