package com.teamk.scoretrack.module.security.auth.controller;

import com.teamk.scoretrack.module.commons.base.controller.BaseMvcController;
import com.teamk.scoretrack.module.commons.base.service.valid.form.FormValidationContext;
import com.teamk.scoretrack.module.commons.form.mvc.MvcForm;
import com.teamk.scoretrack.module.commons.layout.alert.UiAlertDisplayOptions;
import com.teamk.scoretrack.module.commons.other.ErrorMap;
import com.teamk.scoretrack.module.security.auth.dto.AuthenticationDto;
import com.teamk.scoretrack.module.security.auth.dto.SignUpForm;
import com.teamk.scoretrack.module.security.auth.dto.SignUpResponseDto;
import com.teamk.scoretrack.module.security.auth.service.AuthenticationSignUpService;
import com.teamk.scoretrack.module.security.auth.service.form.AuthFormOptionsService;
import com.teamk.scoretrack.module.security.auth.service.valid.AuthenticationExistsValidator;
import com.teamk.scoretrack.module.security.auth.service.valid.AuthenticationSignUpFormValidator;
import com.teamk.scoretrack.module.security.token.util.UUIDUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

import static com.teamk.scoretrack.module.commons.layout.alert.UiAlertDisplayOptionsUtils.addToHttpSession;

@Controller
public class AuthenticationController extends BaseMvcController {
    public static final String LOGIN = "/login";
    public static final String LOGOUT = "/logout";
    public static final String SIGN_UP = "/signup";
    public static final String ACTIVATE = "/activate";
    public static final String HOME = "/";
    private static final String AUTH_DIR = "auth";
    private static final String HOME_PAGE = "index";
    private static final String LOGIN_PAGE = AUTH_DIR + LOGIN;
    private static final String ACTIVATED_PAGE = AUTH_DIR + ACTIVATE;
    private final AuthFormOptionsService optionsPreparer;
    private final AuthenticationSignUpService authenticationSignUpService;
    private final AuthenticationSignUpFormValidator authenticationSignUpFormValidator;

    @Autowired
    public AuthenticationController(AuthFormOptionsService optionsPreparer,
                                    AuthenticationSignUpService authenticationSignUpService,
                                    AuthenticationSignUpFormValidator authenticationSignUpFormValidator) {
        this.optionsPreparer = optionsPreparer;
        this.authenticationSignUpService = authenticationSignUpService;
        this.authenticationSignUpFormValidator = authenticationSignUpFormValidator;
    }

    @GetMapping(LOGIN)
    public String login(Model model) {
        optionsPreparer.prepareFormOptions(new MvcForm(model, "login"));
        model.addAttribute("signUpForm", new SignUpForm());
        return LOGIN_PAGE;
    }

    @PostMapping(SIGN_UP)
    public ResponseEntity<SignUpResponseDto> signup(@ModelAttribute SignUpForm signUpForm) {
        ErrorMap errorMap = authenticationSignUpFormValidator.validate(new FormValidationContext<>(signUpForm));
        SignUpResponseDto responseDto = new SignUpResponseDto();
        if (!errorMap.isEmpty()) {
            // Prevent username or email address guessing (enumeration attack)
            Map<String, ErrorMap.Error> errors = errorMap.getErrors();
            ErrorMap.Error exists = errors.remove(AuthenticationExistsValidator.LOGINNAME_EMAIL_ADDRESS_EXIST);
            if (errors.isEmpty() && exists != null) {
                responseDto.setResult(authenticationSignUpService.mockSignUp());
            } else {
                responseDto.setErrors(errors);
            }
        } else {
            String result = authenticationSignUpService.processSignUp(new AuthenticationDto(signUpForm.getLoginname(), signUpForm.getPwdForm().getPassword(), signUpForm.getEmail()), getBaseUrl().concat(ACTIVATE));
            responseDto.setResult(result);
        }
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping(ACTIVATE + "/{encoded}")
    public String activate(HttpServletRequest request, @PathVariable String encoded, Model model) {
        authenticationSignUpService.activate(UUIDUtils.fromBase64Url(encoded), () -> addToHttpSession(request.getSession(), UiAlertDisplayOptions::setAccountActivated));
        optionsPreparer.prepareFormOptions(new MvcForm(model, "activation"));
        return ACTIVATED_PAGE;
    }

    @GetMapping(HOME)
    //@PreAuthorize("@aclService.checkAcl(#authentication, T(com.teamk.scoretrack.module.core.entities.Privileges).SUPPORT_MANAGEMENT)")
    public String home(Model model) {
        optionsPreparer.prepareFormOptions(new MvcForm(model, "home"));
        return HOME_PAGE;
    }
}
