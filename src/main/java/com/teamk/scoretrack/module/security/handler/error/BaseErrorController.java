package com.teamk.scoretrack.module.security.handler.error;

import com.teamk.scoretrack.module.commons.exception.ResourceNotFoundException;
import com.teamk.scoretrack.module.commons.exception.ServerException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BaseErrorController implements ErrorController {
    private static final String URL = "/error";

    @GetMapping(URL)
    public void handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                throw new ResourceNotFoundException();
            } else if (statusCode ==  HttpStatus.FORBIDDEN.value()) {
                throw new AccessDeniedException("");
            } else {
                throw new ServerException();
            }
        }
        throw new ResourceNotFoundException();
    }
}
