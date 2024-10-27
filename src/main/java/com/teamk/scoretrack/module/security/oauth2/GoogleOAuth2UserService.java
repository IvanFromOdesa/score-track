package com.teamk.scoretrack.module.security.oauth2;

import com.teamk.scoretrack.module.security.oauth2.external.ExternalAuthentication;
import org.springframework.stereotype.Service;

@Service
public class GoogleOAuth2UserService extends ExtendedOAuth2UserService {
    @Override
    protected ExternalAuthentication.Type getType() {
        return ExternalAuthentication.Type.GOOGLE_OAUTH_2;
    }

    @Override
    protected String idPropertyName() {
        return "sub";
    }
}
