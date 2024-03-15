package com.teamk.scoretrack.module.security.recaptcha.service.external;

import com.teamk.scoretrack.module.security.recaptcha.RecaptchaKeyProperties;
import com.teamk.scoretrack.module.security.recaptcha.config.RecaptchaConfig;
import com.teamk.scoretrack.module.security.recaptcha.model.RecaptchaResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class RecaptchaExternalService {
    private final RestTemplate googleRecaptcha;
    private final RecaptchaKeyProperties recaptchaKeyProperties;

    @Autowired
    public RecaptchaExternalService(@Qualifier(RecaptchaConfig.NAME) RestTemplate googleRecaptcha, RecaptchaKeyProperties recaptchaKeyProperties) {
        this.googleRecaptcha = googleRecaptcha;
        this.recaptchaKeyProperties = recaptchaKeyProperties;
    }

    public Optional<RecaptchaResponse> callGoogleRecaptcha(String token) {
        return callGoogleRecaptcha(token, null);
    }

    public Optional<RecaptchaResponse> callGoogleRecaptcha(String token, String ip) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String,String> map = new LinkedMultiValueMap<>();
        map.add("secret", recaptchaKeyProperties.privateKey());
        map.add("response", token);
        ResponseEntity<RecaptchaResponse> response = googleRecaptcha.exchange(recaptchaKeyProperties.verifyUrl(), HttpMethod.POST, new HttpEntity<>(map, headers), RecaptchaResponse.class);
        return Optional.ofNullable(response.getBody());
    }
}
