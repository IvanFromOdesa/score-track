package com.teamk.scoretrack.module.security.service.acl;

import org.springframework.security.core.Authentication;

public interface IAclService {
    boolean checkAcl(Authentication authentication, String requiredAuthority);
    boolean checkAcl(Authentication authentication, int apiCode);
}
