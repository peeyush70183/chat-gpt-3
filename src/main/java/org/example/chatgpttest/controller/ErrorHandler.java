package org.example.chatgpttest.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.RequestDispatcher;

@RestController
public class ErrorHandler implements ErrorController {
	private static String BAD_REQUEST_MSG = "Bad request, please try again!";
	private static String INTERNAL_SERVER_ERROR_MSG = "Something went wrong, Our Engineers are on it!";
	private static String NOT_FOUND_MSG = "Sorry, we couldn't find the page you were looking for!";

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());
            if (statusCode == HttpStatus.BAD_REQUEST.value()) {
                return BAD_REQUEST_MSG;
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return INTERNAL_SERVER_ERROR_MSG;
            } else if (statusCode == HttpStatus.NOT_FOUND.value()) {
                return NOT_FOUND_MSG;
            }
        }
        return INTERNAL_SERVER_ERROR_MSG;
    }
}
