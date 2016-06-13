package org.nh.rest.controllers;

import org.apache.log4j.Logger;

import org.nh.rest.exception.NotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public abstract class AbstractController {

    protected final Logger logger = Logger.getLogger(AbstractController.class);

    @ExceptionHandler({AccessDeniedException.class})
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public @ResponseBody String accessDeniedExceptionHandler(AccessDeniedException e) {
        logger.warn(e.getMessage());
        return e.getMessage();
    }

    @ExceptionHandler({NotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public @ResponseBody String notFoundExceptionHandler(NotFoundException e) {
        logger.info(e.getMessage());
        return e.getMessage();
    }

    @ExceptionHandler({RuntimeException.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody String runtimeExceptionHandler(RuntimeException e) {
        logger.error("INTERNAL_SERVER_ERROR", e);
        return e.getMessage();
    }
}
