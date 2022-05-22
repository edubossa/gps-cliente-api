package br.com.pamcary.gps.handler;

public class ClienteNaoLocalizadoHandlerException extends RuntimeException {

    public ClienteNaoLocalizadoHandlerException() {
        super("Cliente não localizado!");
    }

    public ClienteNaoLocalizadoHandlerException(String message) {
        super(message);
    }

    public ClienteNaoLocalizadoHandlerException(String message, Throwable cause) {
        super(message, cause);
    }

    public ClienteNaoLocalizadoHandlerException(Throwable cause) {
        super(cause);
    }

}
