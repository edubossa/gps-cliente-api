package br.com.pamcary.gps.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor(staticName = "of")
public class ErrorDto {
    private final int status;
    private final String message;
    private List<ObjectError> errors = new ArrayList<>();
}
