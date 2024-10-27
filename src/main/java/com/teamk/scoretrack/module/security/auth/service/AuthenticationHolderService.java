package com.teamk.scoretrack.module.security.auth.service;

import com.teamk.scoretrack.module.core.entities.user.base.domain.Privileges;
import com.teamk.scoretrack.module.core.entities.user.base.domain.Role;
import com.teamk.scoretrack.module.core.entities.user.base.domain.UserGroup;
import com.teamk.scoretrack.module.core.entities.user.base.domain.UserPrivilege;
import com.teamk.scoretrack.module.security.auth.domain.AuthenticationBean;
import com.teamk.scoretrack.module.security.auth.domain.AuthenticationWrapper;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

import static com.teamk.scoretrack.module.security.token.jwt.model.AccessToken.Claims.*;

/**
 * Service to get authentication and related props either from the session or JWT
 */
@Service
public class AuthenticationHolderService {
    private static final String ANONYMOUS_AUTHENTICATION = "anonymousUser";

    public Optional<AuthenticationBean> getCurrentAuthentication() {
        return getAuthentication(a -> a instanceof JwtAuthenticationToken || a instanceof OAuth2AuthenticationToken, a -> (AuthenticationBean) a.getPrincipal());
    }

    public Optional<JwtAuthenticationToken> getCurrentAuthenticationToken() {
        return getAuthentication(a -> a instanceof UsernamePasswordAuthenticationToken || a instanceof OAuth2AuthenticationToken, a -> (JwtAuthenticationToken) a);
    }

    public Optional<OAuth2AuthenticationToken> getCurrentOAuth2Token() {
        return getAuthentication(a -> a instanceof UsernamePasswordAuthenticationToken || a instanceof JwtAuthenticationToken, a -> (OAuth2AuthenticationToken) a);
    }

    public void setAuthentication(Authentication authentication) {
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private <T> Optional<T> getAuthentication(Predicate<Authentication> authTypeCheck, Function<Authentication, T> authTransformer) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (isAnonymousAuthentication(authentication) || authTypeCheck.test(authentication)) {
            return Optional.empty();
        }
        return Optional.of(authTransformer.apply(authentication));
    }

    public Optional<AuthenticationWrapper> getAuthenticationWrapper() {
        Optional<AuthenticationBean> currentAuthentication = getCurrentAuthentication();
        if (currentAuthentication.isPresent()) {
            AuthenticationBean authenticationBean = currentAuthentication.get();
            return Optional.of(new AuthenticationWrapper(authenticationBean.getExternalId(), authenticationBean.getLoginname(), authenticationBean.getEmail(), authenticationBean.getUser().getPreferredLang(), authenticationBean.getAuthorities()));
        } else {
            Optional<JwtAuthenticationToken> currentAuthenticationToken = getCurrentAuthenticationToken();
            if (currentAuthenticationToken.isPresent()) {
                JwtAuthenticationToken jwtAuthenticationToken = currentAuthenticationToken.get();
                Map<String, Object> claims = jwtAuthenticationToken.getTokenAttributes();
                return Optional.of(new AuthenticationWrapper(EXTERNAL_ID.extractClaim(claims, String.class), jwtAuthenticationToken.getName(), EMAIL.extractClaim(claims, String.class), PREFERRED_LANGUAGE.extractClaim(claims, String.class), jwtAuthenticationToken.getAuthorities()));
            }
        }
        return Optional.empty();
    }

    public static boolean isAnonymousAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return isAnonymousAuthentication(authentication);
    }

    public static boolean isAnonymousAuthentication(Authentication authentication) {
        return authentication == null || authentication instanceof AnonymousAuthenticationToken || authentication.getName().equals(ANONYMOUS_AUTHENTICATION);
    }

    public UserGroup getUserGroup() {
        return getFromWrapperAuthorities(UserGroup.ANONYMOUS, a -> Role.isRoleAlias(a.getAuthority()), a -> UserGroup.byRoleAlias(a.getAuthority()));
    }

    public int[] getApiCodes() {
        return getFromWrapperAuthorities(new int[]{}, a -> a.getAuthority().equals(Privileges.API_ACCESS.privilege()), a -> ((UserPrivilege) a).getSubAuthorities());
    }

    public List<Integer> getApiCodesAsList() {
        return Arrays.stream(getApiCodes()).boxed().toList();
    }

    private <T> T getFromWrapperAuthorities(T defaultRes, Predicate<GrantedAuthority> filter, Function<GrantedAuthority, T> findCallback) {
        return getAuthenticationWrapper().map(wrapper -> wrapper.authorities().stream().filter(filter).findFirst().map(findCallback).orElse(defaultRes)).orElse(defaultRes);
    }
}
