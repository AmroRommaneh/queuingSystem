package com.amr.gradutionPro.gradutionPro.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class RestErrorHandler {
        @ExceptionHandler(Myexeption.class)
        @ResponseBody
        @ResponseStatus(HttpStatus.NOT_FOUND)
        public com.amr.gradutionPro.gradutionPro.exceptions.CustomResponse handleSecurityException(Myexeption se) {
            com.amr.gradutionPro.gradutionPro.exceptions.CustomResponse response = new com.amr.gradutionPro.gradutionPro.exceptions.CustomResponse(se.getErrorMessage());
            return response;
        }
    }


