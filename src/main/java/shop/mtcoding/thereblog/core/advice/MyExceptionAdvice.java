package shop.mtcoding.thereblog.core.advice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import shop.mtcoding.thereblog.core.auth.MyUserDetails;
import shop.mtcoding.thereblog.core.exception.csr.*;
import shop.mtcoding.thereblog.core.exception.ssr.*;
import shop.mtcoding.thereblog.core.util.Script;
import shop.mtcoding.thereblog.dto.ResponseDTO;

@Slf4j
@RequiredArgsConstructor
@ControllerAdvice
public class MyExceptionAdvice {

    //private final ErrorLogRepository errorLogRepository;

    ////////////////////////////// VIEW
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception400.class)
    public @ResponseBody String badRequest(Exception400 e) {
        return Script.back(e.getKey() + " : " + e.getValue());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(Exception401.class)
    public @ResponseBody String unAuthorized(Exception401 e) {
        return Script.back(e.getMessage());
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(Exception403.class)
    public @ResponseBody String forbidden(Exception403 e) {
        return Script.back(e.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(Exception404.class)
    public @ResponseBody String notFound(Exception404 e) {
        return Script.back(e.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception500.class)
    public @ResponseBody String serverError(Exception500 e, MyUserDetails myUserDetails) {
        if(myUserDetails.getUser() != null){
            log.error("에러 발생 : "+e.getMessage());
        }
        return Script.back(e.getMessage());
    }

    ////////////////////////////// API
    @ExceptionHandler(ExceptionApi400.class)
    public ResponseEntity<?> badRequest(ExceptionApi400 e) {
        return new ResponseEntity<>(e.body(), e.status());
    }

    @ExceptionHandler(ExceptionApi401.class)
    public ResponseEntity<?> unAuthorized(ExceptionApi401 e) {
        return new ResponseEntity<>(e.body(), e.status());
    }

    @ExceptionHandler(ExceptionApi403.class)
    public ResponseEntity<?> forbidden(ExceptionApi403 e) {
        return new ResponseEntity<>(e.body(), e.status());
    }

    @ExceptionHandler(ExceptionApi404.class)
    public ResponseEntity<?> notFound(ExceptionApi404 e) {
        return new ResponseEntity<>(e.body(), e.status());
    }

    @ExceptionHandler(ExceptionApi500.class)
    public ResponseEntity<?> serverError(ExceptionApi500 e, MyUserDetails myUserDetails) {
        if(myUserDetails.getUser() != null){
            log.error("에러 발생 : "+e.getMessage());
        }
        return new ResponseEntity<>(e.body(), e.status());
    }

    //////////////////////////// COMMON
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> unknownServerError(Exception e, MyUserDetails myUserDetails) {
        if(myUserDetails.getUser() != null){
            log.error("에러 발생 : "+e.getMessage());
        }

        ResponseDTO<String> responseDto = new ResponseDTO<>();
        responseDto.fail(HttpStatus.INTERNAL_SERVER_ERROR, "unknownServerError", e.getMessage());
        return new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
