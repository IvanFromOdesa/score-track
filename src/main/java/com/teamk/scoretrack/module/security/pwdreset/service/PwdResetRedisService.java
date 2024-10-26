package com.teamk.scoretrack.module.security.pwdreset.service;

import com.teamk.scoretrack.module.commons.cache.redis.service.BaseRedisService;
import com.teamk.scoretrack.module.security.pwdreset.dao.PwdResetTokenDao;
import com.teamk.scoretrack.module.security.pwdreset.domain.PwdResetToken;
import com.teamk.scoretrack.module.security.token.crypto.SecureRandomTokenInput;
import com.teamk.scoretrack.module.security.token.crypto.SecureRandomTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PwdResetRedisService extends BaseRedisService<SecureRandomTokenInput, PwdResetToken, String, PwdResetTokenDao> {
    private final SecureRandomTokenService secureRandomTokenService;

    @Autowired
    public PwdResetRedisService(SecureRandomTokenService secureRandomTokenService) {
        this.secureRandomTokenService = secureRandomTokenService;
    }

    public void markUsed(PwdResetToken token) {
        token.markUsed();
        dao.save(token);
    }

    @Override
    @Autowired
    protected void setDao(PwdResetTokenDao dao) {
        this.dao = dao;
    }

    @Override
    protected PwdResetToken fromContext(SecureRandomTokenInput ctx) {
        String token = secureRandomTokenService.generateToken(ctx);
        return new PwdResetToken(token, token, ctx.email());
    }
}
