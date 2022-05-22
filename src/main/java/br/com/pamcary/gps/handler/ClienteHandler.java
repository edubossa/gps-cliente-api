package br.com.pamcary.gps.handler;

import br.com.pamcary.gps.dto.ErrorDto;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.UnexpectedTypeException;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class ClienteHandler {

    @ResponseStatus(CONFLICT)
    @ResponseBody
    @ExceptionHandler(CpfDuplicadoHandlerException.class)
    public ErrorDto cpfDuplicadoHandlerException(CpfDuplicadoHandlerException ex) {
        return ErrorDto.of(CONFLICT.value(),ex.getMessage());
    }

    @ResponseStatus(CONFLICT)
    @ResponseBody
    @ExceptionHandler(UnexpectedTypeException.class)
    public ErrorDto unexpectedTypeException(UnexpectedTypeException ex) {
        return ErrorDto.of(BAD_REQUEST.value(), ex.getMessage());
    }

    @ResponseStatus(CONFLICT)
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorDto methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return new ErrorDto(BAD_REQUEST.value(), "Um ou mais campos estão invalidos!", ex.getBindingResult().getAllErrors());
    }

    @ResponseStatus(CONFLICT)
    @ResponseBody
    @ExceptionHandler(DataInvalidaHandlerException.class)
    public ErrorDto dataInvalidaHandlerException(DataInvalidaHandlerException ex) {
        return ErrorDto.of(BAD_REQUEST.value(), ex.getMessage());
    }

    @ResponseStatus(NO_CONTENT)
    @ResponseBody
    @ExceptionHandler(ClienteNaoLocalizadoHandlerException.class)
    public ErrorDto clienteNaoLocalizadoHandlerException(ClienteNaoLocalizadoHandlerException ex) {
        return ErrorDto.of(NO_CONTENT.value(), ex.getMessage());
    }

}
