package br.com.pamcary.gps.handler;

public class CpfDuplicadoHandlerException extends RuntimeException {

    public CpfDuplicadoHandlerException() {
        super("Este CPF Ja foi cadastrado!");
    }

    public CpfDuplicadoHandlerException(String message) {
        super(message);
    }

    public CpfDuplicadoHandlerException(String message, Throwable cause) {
        super(message, cause);
    }

    public CpfDuplicadoHandlerException(Throwable cause) {
        super(cause);
    }

}
