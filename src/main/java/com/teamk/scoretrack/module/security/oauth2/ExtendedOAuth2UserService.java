package com.teamk.scoretrack.module.security.oauth2;

import com.teamk.scoretrack.module.core.entities.user.base.event.UserProcessingEvent;
import com.teamk.scoretrack.module.core.entities.user.base.event.publisher.UserProcessingDelegator;
import com.teamk.scoretrack.module.core.entities.user.client.ctx.ClientUserProcessingContext;
import com.teamk.scoretrack.module.core.entities.user.fan.ctx.FanProcessingContext;
import com.teamk.scoretrack.module.core.entities.user.fan.event.FanProcessingEvent;
import com.teamk.scoretrack.module.security.auth.domain.AuthenticationBean;
import com.teamk.scoretrack.module.security.auth.service.AuthenticationEntityService;
import com.teamk.scoretrack.module.security.oauth2.external.ExternalAuthentication;
import com.teamk.scoretrack.module.security.oauth2.external.ExternalAuthenticationEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import java.util.Optional;

public abstract class ExtendedOAuth2UserService extends OidcUserService {
    @Autowired
    protected UserProcessingDelegator userProcessingDelegator;
    @Autowired
    protected AuthenticationEntityService authenticationEntityService;
    @Autowired
    protected ExternalAuthenticationEntityService externalAuthenticationEntityService;

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser user = super.loadUser(userRequest);

        String email = user.getAttribute("email");
        String externalId = user.getAttribute(idPropertyName());
        String picture = user.getAttribute("picture");

        Optional<AuthenticationBean> byEmail = authenticationEntityService.findByEmail(email);
        if (byEmail.isPresent()) {
            AuthenticationBean authenticationBean = byEmail.get();
            Optional<ExternalAuthentication> existing = externalAuthenticationEntityService.getByAuthenticationBeanAndExternalId(authenticationBean, externalId);
            if (existing.isEmpty()) {
                externalAuthenticationEntityService.save(getExternalAuthentication(authenticationBean, externalId));
                processUserEvent(picture, authenticationBean, UserProcessingEvent.OperationType.UPDATE);
            }
        } else {
            AuthenticationBean authentication = AuthenticationBean.getDefault(user.getAttribute("name"), email);
            processUserEvent(picture, authentication, UserProcessingEvent.OperationType.CREATE);
            externalAuthenticationEntityService.save(getExternalAuthentication(authentication, externalId));
        }

        return user;
    }

    private void processUserEvent(String picture, AuthenticationBean authentication, UserProcessingEvent.OperationType operationType) {
        ClientUserProcessingContext.ProfileCreationContext profileCreationContext = new ClientUserProcessingContext.ProfileCreationContext();
        profileCreationContext.setImageUrl(picture);
        userProcessingDelegator.processEvent(new FanProcessingEvent(FanProcessingContext.getDefault(authentication, profileCreationContext), operationType));
    }

    private ExternalAuthentication getExternalAuthentication(AuthenticationBean authenticationBean, String externalId) {
        ExternalAuthentication externalAuthentication = new ExternalAuthentication();
        ExternalAuthentication.ExternalAuthenticationId id = new ExternalAuthentication.ExternalAuthenticationId();
        id.setType(getType());
        id.setAuthenticationBeanId(authenticationBean.getId());
        externalAuthentication.setId(id);
        externalAuthentication.setAuthenticationBean(authenticationBean);
        externalAuthentication.setExternalId(externalId);
        return externalAuthentication;
    }

    protected abstract ExternalAuthentication.Type getType();
    protected abstract String idPropertyName();
}
