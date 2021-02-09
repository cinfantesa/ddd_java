package com.agiletv.user.infrastructure.rest;

import com.agiletv.user.domain.exception.GenderNotFound;
import com.agiletv.user.domain.exception.UserNotFound;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class RestExceptionHandler {
  
  @ExceptionHandler(GenderNotFound.class)
  public void handleBadRequest(HttpServletResponse response) throws IOException {
    response.sendError(BAD_REQUEST.value());
  }
  
  @ExceptionHandler(UserNotFound.class)
  public void handleNotFound(HttpServletResponse response) throws IOException {
    response.sendError(NOT_FOUND.value());
  }
}
