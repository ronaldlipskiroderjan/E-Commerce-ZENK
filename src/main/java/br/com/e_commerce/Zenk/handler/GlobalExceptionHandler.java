package br.com.e_commerce.Zenk.handler;

import br.com.e_commerce.Zenk.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionDTO> notFoundException(NotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionDTO(
                ex.getMessage(),
                HttpStatus.NOT_FOUND.value()
        ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDTO> exception(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ExceptionDTO(
                ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        ));
    }
}
