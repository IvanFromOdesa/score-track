package com.teamk.scoretrack.module.security.auth.domain;

import com.teamk.scoretrack.module.core.entities.user.base.domain.Language;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.UUID;

/**
 * Contains essential info about specific instance of {@link AuthenticationBean}.
 * Might be useful if the domain object is not needed (db transactions), but we still
 * need to access its properties. Not bound to ORM context.
 * The main purpose of this class is that it can be created both from {@link AuthenticationBean} and {@link org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken}.
 */
public record AuthenticationWrapper(UUID externalId, String loginname, String email, Language preferredLanguage, Collection<? extends GrantedAuthority> authorities) {

}
