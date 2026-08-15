package br.com.e_commerce.Zenk.handler;

import br.com.e_commerce.Zenk.exception.NotFoundException;
import br.com.e_commerce.Zenk.exception.UsuarioInactivateException;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ExceptionDTO> badRequestException(BadRequestException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionDTO(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value()
        ));
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ExceptionDTO> userNotFoundException(UsernameNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionDTO(
                ex.getMessage(),
                HttpStatus.NOT_FOUND.value()
        ));
    }

    @ExceptionHandler(IllegalAccessException.class)
    public ResponseEntity<ExceptionDTO> ilegalAccessException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ExceptionDTO(
                ex.getMessage(),
                HttpStatus.UNAUTHORIZED.value()
        ));
    }

    @ExceptionHandler(UsuarioInactivateException.class)
    public ResponseEntity<ExceptionDTO> usuarioInactivateException(UsuarioInactivateException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ExceptionDTO(
                ex.getMessage(),
                HttpStatus.FORBIDDEN.value()
        ));
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ExceptionDTO> exception(AuthorizationDeniedException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ExceptionDTO(
                ex.getMessage(),
                HttpStatus.UNAUTHORIZED.value()
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
