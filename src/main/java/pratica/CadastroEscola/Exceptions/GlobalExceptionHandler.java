package pratica.CadastroEscola.Exceptions;

import jakarta.validation.ConstraintViolationException;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.nio.file.AccessDeniedException;
import java.time.Instant;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //ERRO 500
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneric(Exception ex) {

        return ResponseEntity.status(500).body(
                Map.of(
                        "status", 500,
                        "error", "Internal Server Error",
                        "message", "Erro inesperado"
                )
        );
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> handleBadRequest(BadRequestException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                Map.of(
                        "timestamp", Instant.now(),
                        "status", 400,
                        "error", "Bad Request",
                        "message", ex.getMessage()
                )
        );
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFound(ResourceNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Map.of(
                        "timestamp", Instant.now(),
                        "status", 404,
                        "error", "Not Found",
                        "message", ex.getMessage()
                )
        );
    }

    // Handlers de Exceptions padrão do Spring ....
    //
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {

        return ResponseEntity.badRequest().body(
                Map.of(
                        "status", 400,
                        "error", "Bad Request",
                        "message", "ID inválido. Formato de UUID incorreto."
                )
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleMessageNotReadable(HttpMessageNotReadableException ex) {

        return ResponseEntity.badRequest().body(
                Map.of(
                        "status", 400,
                        "error", "Bad Request",
                        "message", "JSON inválido ou contém campos não permitidos"
                )
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidation(MethodArgumentNotValidException ex) {

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .toList();

        return ResponseEntity.badRequest().body(
                Map.of(
                        "status", 400,
                        "error", "Bad Request",
                        "message", errors
                )
        );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleConstraint(ConstraintViolationException ex) {

        List<String> errors = ex.getConstraintViolations()
                .stream()
                .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                .toList();

        return ResponseEntity.badRequest().body(
                Map.of(
                        "status", 400,
                        "error", "Bad Request",
                        "message", errors
                )
        );
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleDatabaseError(DataIntegrityViolationException ex) {

        return ResponseEntity.status(409).body(
                Map.of(
                        "status", 409,
                        "error", "Conflict",
                        "message", "Violação de integridade de dados"
                )
        );
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<?> handleMethodNotAllowed(HttpRequestMethodNotSupportedException ex) {

        return ResponseEntity.status(405).body(
                Map.of(
                        "status", 405,
                        "error", "Method Not Allowed",
                        "message", "Método HTTP não suportado"
                )
        );
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<?> handleMissingParam(MissingServletRequestParameterException ex) {

        return ResponseEntity.badRequest().body(
                Map.of(
                        "status", 400,
                        "error", "Bad Request",
                        "message", "Parâmetro obrigatório ausente: " + ex.getParameterName()
                )
        );
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<?> handleMediaType(HttpMediaTypeNotSupportedException ex) {

        return ResponseEntity.status(415).body(
                Map.of(
                        "status", 415,
                        "error", "Unsupported Media Type",
                        "message", "Tipo de conteúdo não suportado"
                )
        );
    }

    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public ResponseEntity<?> handleNotAcceptable(HttpMediaTypeNotAcceptableException ex) {

        return ResponseEntity.status(406).body(
                Map.of(
                        "status", 406,
                        "error", "Not Acceptable",
                        "message", "Formato de resposta não suportado"
                )
        );
    }

    @ExceptionHandler(ConversionFailedException.class)
    public ResponseEntity<?> handleConversion(ConversionFailedException ex) {

        return ResponseEntity.badRequest().body(
                Map.of(
                        "status", 400,
                        "error", "Bad Request",
                        "message", "Erro de conversão de tipo"
                )
        );
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDenied(AccessDeniedException ex) {

        return ResponseEntity.status(403).body(
                Map.of(
                        "status", 403,
                        "error", "Forbidden",
                        "message", "Acesso negado"
                )
        );
    }

}
