package com.teamk.scoretrack.module.security.token.jwt.convert;

import com.teamk.scoretrack.module.core.entities.user.base.domain.UserPrivilege;
import com.teamk.scoretrack.module.security.token.jwt.model.AccessToken;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JwtUserPrivilegeConverter implements Converter<Jwt, Collection<GrantedAuthority>> {
    private static final String AUTHORITY = "authority";
    private static final String SUB_AUTHORITIES = "subAuthorities";

    @Override
    @SuppressWarnings("unchecked")
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        List<Map<String, Object>> claim = jwt.getClaim(AccessToken.Claims.SCOPE_PREFIX);
        return claim.stream().map(c -> {
            Object o = c.get(SUB_AUTHORITIES);
            String authority = (String) c.get(AUTHORITY);
            if (o instanceof List<?>) {
                int[] subs = ((List<Long>) o).stream().mapToInt(n -> (int) n.longValue()).toArray();
                return new UserPrivilege(authority, subs);
            } else {
                return new UserPrivilege(authority);
            }
        }).collect(Collectors.toList());
    }
}
