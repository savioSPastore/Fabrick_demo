package com.pastore.fabrick.bankaccount.exception;

import com.pastore.fabrick.bankaccount.contract.MoneyTransferExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalErrorHandler {

    @ExceptionHandler(value = MoneyTransferKoException.class)
    @ResponseBody
    public ResponseEntity<MoneyTransferExceptionResponse> mediaTypeNotSupported(Exception ex, WebRequest request) {
        return new ResponseEntity<>(MoneyTransferExceptionResponse.builder()
                .description("API000")
                .code("Errore tecnico. La condizione BP049 non e' prevista per il conto id 14537780")
                .build(), HttpStatus.CONFLICT);
    }
}
