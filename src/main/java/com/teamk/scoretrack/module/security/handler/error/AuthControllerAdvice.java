package com.teamk.scoretrack.module.security.handler.error;

import com.teamk.scoretrack.module.commons.exception.BaseErrorMapException;
import com.teamk.scoretrack.module.commons.exception.ResourceNotFoundException;
import com.teamk.scoretrack.module.commons.exception.ServerException;
import com.teamk.scoretrack.module.commons.other.ErrorMap;
import com.teamk.scoretrack.module.security.commons.ForbiddenResponseException;
import com.teamk.scoretrack.module.security.handler.error.i18n.AuthenticationErrorTranslatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;
import java.util.Map;
import java.util.Set;

@ControllerAdvice
public class AuthControllerAdvice extends ResponseEntityExceptionHandler {
    private static final String DIR = "status/";
    public static final String _403 = DIR + "403";
    public static final String _404 = DIR + "404";
    public static final String _500 = DIR + "500";
    public static final Set<String> PAGES_RESOURCES = Set.of("/layouts/styles/status/style.css", "/layouts/images/status/404.png", "/layouts/images/status/403.png");
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthControllerAdvice.class);
    @Value("${spring.servlet.multipart.max-file-size}")
    private String maxFileUploadSize;
    private final AuthenticationErrorTranslatorService translatorService;

    @Autowired
    public AuthControllerAdvice(AuthenticationErrorTranslatorService translatorService) {
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

    @ExceptionHandler({ ForbiddenResponseException.class })
    public ResponseEntity<?> forbiddenResponse() {
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({ BaseErrorMapException.class })
    public ResponseEntity<?> baseErrorMapException(BaseErrorMapException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(Instant.now(), e.getErrorMap().getErrors()));
    }

    @ExceptionHandler({ MaxUploadSizeExceededException.class })
    public ResponseEntity<?> maxFileUploadSizeExceededException(MaxUploadSizeExceededException e) {
        ErrorMap errors = new ErrorMap();
        errors.put("error.file.size", translatorService.getMessage("file.upload.size-exceeded", maxFileUploadSize));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(Instant.now(), errors.getErrors()));
    }

    @ExceptionHandler({ ServerException.class, Exception.class })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView _500(Exception ex) {
        LOGGER.error(ex.getMessage());
        ex.printStackTrace();
        return setView(_500);
    }

    private ModelAndView setView(String path) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName(path);
        mv.addAllObjects(translatorService.getMessages("status"));
        return mv;
    }

    record ErrorResponse(Instant timestamp, Map<String, ErrorMap.Error> errors) {

    }
}
