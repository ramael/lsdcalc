/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.aittam.lsdcalc.utils.exceptions;

import javax.servlet.http.HttpServletRequest;
import org.aittam.lsdcalc.contracts.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.RestClientResponseException;

/**
 *
 * @author mlanzoni
 */
@ControllerAdvice
public class ExceptionHandlerControllerAdvice {
    
    private final Logger logger = LoggerFactory.getLogger(ExceptionHandlerControllerAdvice.class);
    
    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody ErrorResponse handleBadRequestException(final Exception exception, final HttpServletRequest request) {
        logger.error("handleBadRequestException: " + request.getRequestURI() + " - " + exception.getLocalizedMessage(), exception);
        
        ErrorResponse er = new ErrorResponse();
        er.message = exception.getLocalizedMessage();
        return er;
    }
    
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody ErrorResponse handleException(final Exception exception, final HttpServletRequest request) {
        logger.error("handleException: " + request.getRequestURI() + " - " + exception.getLocalizedMessage(), exception);
        
        ErrorResponse er = new ErrorResponse();
        er.message = exception.getLocalizedMessage();
        return er;
    }
}
