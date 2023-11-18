package com.teamk.scoretrack.module.security.handler.error;

import com.teamk.scoretrack.module.commons.exception.ResourceNotFoundException;
import com.teamk.scoretrack.module.commons.exception.ServerException;
import com.teamk.scoretrack.module.security.handler.error.i18n.ErrorStatusTranslatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Set;

@ControllerAdvice
public class AuthControllerAdvice extends ResponseEntityExceptionHandler {
    private static final String DIR = "status/";
    public static final String _403 = DIR + "403";
    public static final String _404 = DIR + "404";
    public static final String _500 = DIR + "500";
    public static final Set<String> PAGES_RESOURCES = Set.of("/layouts/styles/status/style.css", "/layouts/images/status/404.png", "/layouts/images/status/403.png");
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthControllerAdvice.class);
    private final ErrorStatusTranslatorService translatorService;

    @Autowired
    public AuthControllerAdvice(ErrorStatusTranslatorService translatorService) {
        this.translatorService = translatorService;
    }

    @ExceptionHandler({ AccessDeniedException.class })
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ModelAndView _403() {
        return setView(_403);
    }

    @ExceptionHandler({ ResourceNotFoundException.class })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView _404() {
        return setView(_404);
    }

    @ExceptionHandler({ ServerException.class, Exception.class })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView _500(Exception ex) {
        LOGGER.error(ex.getMessage());
        return setView(_500);
    }

    private ModelAndView setView(String path) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName(path);
        mv.addAllObjects(translatorService.getMessages());
        return mv;
    }
}
