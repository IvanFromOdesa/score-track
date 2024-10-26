package com.teamk.scoretrack.module.security.auth.service;

import com.teamk.scoretrack.module.security.auth.domain.AuthenticationBean;
import com.teamk.scoretrack.module.security.auth.domain.PasswordStatus;
import com.teamk.scoretrack.module.security.commons.config.HashingConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Consumer;

@Service
public class AuthenticationChangeCredentialsService {
    private final AuthenticationEntityService authenticationEntityService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationChangeCredentialsService(AuthenticationEntityService authenticationEntityService,
                                                  @Qualifier(HashingConfiguration.BCRYPT) PasswordEncoder passwordEncoder) {
        this.authenticationEntityService = authenticationEntityService;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean resetPasswordStatus(String email) {
        return findByEmail(email, a -> {
            a.setPs(PasswordStatus.RESET);
            authenticationEntityService.save(a);
        });
    }

    public boolean changePassword(String newPassword, String email) {
        return findByEmail(email, a -> {
            a.setPassword(passwordEncoder.encode(newPassword));
            a.setPs(PasswordStatus.CHANGED);
            authenticationEntityService.save(a);
        });
    }

    private boolean findByEmail(String email, Consumer<AuthenticationBean> foundCallback) {
        Optional<AuthenticationBean> byEmail = authenticationEntityService.findByEmail(email);
        if (byEmail.isPresent()) {
            foundCallback.accept(byEmail.get());
            return true;
        }
        return false;
    }
}
