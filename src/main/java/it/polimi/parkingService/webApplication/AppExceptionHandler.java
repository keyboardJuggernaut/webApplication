package it.polimi.parkingService.webApplication;

import it.polimi.parkingService.webApplication.utils.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

@ControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler
    public String handleException(Exception exc) {
        exc.printStackTrace();
        // create a ErrorResponse
        ErrorResponse error = new ErrorResponse();

        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage(exc.getMessage());
        error.setTimeStamp(System.currentTimeMillis());

        // return ResponseEntity
        return "error";
    }
    @RequestMapping("/error")
    public String handleError() {
        //do something like logging
        return "error";
    }

//    @ExceptionHandler
//    public ResponseEntity<ErrorResponse> handleException(Exception exc) {
//        exc.printStackTrace();
//        // create a ErrorResponse
//        ErrorResponse error = new ErrorResponse();
//
//        error.setStatus(HttpStatus.BAD_REQUEST.value());
//        error.setMessage(exc.getMessage());
//        error.setTimeStamp(System.currentTimeMillis());
//
//        // return ResponseEntity
//        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
//    }

}
