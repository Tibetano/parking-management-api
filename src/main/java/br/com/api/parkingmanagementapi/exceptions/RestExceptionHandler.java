package br.com.api.parkingmanagementapi.exceptions;

import br.com.api.parkingmanagementapi.dtos.CommonResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> runTimeExceptionHandler(RuntimeException runtimeException){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new CommonResponseDTO(runtimeException.getMessage()));
    }
}
