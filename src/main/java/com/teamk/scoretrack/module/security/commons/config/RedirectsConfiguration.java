package com.teamk.scoretrack.module.security.commons.config;

import com.teamk.scoretrack.module.security.commons.model.SessionRedirect;
import com.teamk.scoretrack.module.security.pwdreset.controller.PwdResetController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedirectsConfiguration {
    @Bean
    public SessionRedirect pwdResetSessionRedirect() {
        return new SessionRedirect(
                PwdResetController.PWD_RESET_CONFIRMED_URL_TOKEN,
                PwdResetController.NEW_PWD);
    }
}
