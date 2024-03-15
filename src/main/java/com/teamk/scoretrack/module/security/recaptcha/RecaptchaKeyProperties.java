package com.teamk.scoretrack.module.security.recaptcha;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "recaptcha")
public record RecaptchaKeyProperties(String verifyUrl, String publicKey, String privateKey) {
}
