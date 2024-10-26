package com.teamk.scoretrack.module.security.auth.service.valid;

import com.teamk.scoretrack.module.commons.other.ErrorMap;
import com.teamk.scoretrack.module.security.auth.service.AuthenticationEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationExistsValidator {
    public static final String LOGINNAME_EMAIL_ADDRESS_EXIST = "LOGINNAME_OR_EMAIL_ALREADY_REGISTERED";
    private final AuthenticationEntityService entityService;

    @Autowired
    public AuthenticationExistsValidator(AuthenticationEntityService entityService) {
        this.entityService = entityService;
    }

    public ErrorMap validate(String loginname, String email) {
        ErrorMap errors = new ErrorMap();
        if (entityService.existsByLoginnameOrEmail(loginname, email)) {
            errors.put(LOGINNAME_EMAIL_ADDRESS_EXIST, "");
        }
        return errors;
    }
}
