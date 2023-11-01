package com.teamk.scoretrack.module.security.token.activation.service;

import com.teamk.scoretrack.module.commons.service.token.ITokenResolver;
import com.teamk.scoretrack.module.security.auth.domain.AuthenticationBean;
import com.teamk.scoretrack.module.security.token.util.UUIDUtils;
import org.springframework.security.core.token.Sha512DigestUtils;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UUIDTokenResolverService implements ITokenResolver<UUID, AuthenticationBean> {
    @Override
    public UUID generateToken(AuthenticationBean authenticationBean) {
        return UUIDUtils.v5(Sha512DigestUtils.shaHex(authenticationBean.toString()));
    }
}
