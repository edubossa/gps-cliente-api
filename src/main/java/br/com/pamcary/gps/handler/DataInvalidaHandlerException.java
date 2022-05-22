package br.com.pamcary.gps.handler;

public class DataInvalidaHandlerException extends RuntimeException {

    public DataInvalidaHandlerException() {
        super("Data Invalida, o formato deve ser dd/MM/yyyy!");
    }

    public DataInvalidaHandlerException(String message) {
        super(message);
    }

    public DataInvalidaHandlerException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataInvalidaHandlerException(Throwable cause) {
        super(cause);
    }

}
